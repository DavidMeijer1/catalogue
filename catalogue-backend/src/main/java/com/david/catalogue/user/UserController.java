package com.david.catalogue.user;

import com.david.catalogue.user.userRequests.AccountRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserRepository userRepository;
    private final AccountRequestRepository accountRequestRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> getUserById(@PathVariable UUID uuid){
        Optional<User> possibleUser = userRepository.findById(uuid);
        return possibleUser.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User currentUser) {
        try {
            return ResponseEntity.ok(currentUser);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/new/{id}")
    public ResponseEntity<User> createNewUser(@PathVariable("id") Long accountRequestId,
                                              @RequestHeader(name = "ROLE", defaultValue = "USER") String role) {
        try {
            var accountRequest = accountRequestRepository.findById(accountRequestId).orElseThrow();
            var user = User.builder()
                    .username(accountRequest.getUsername())
                    .userRole(UserRole.valueOf(role))
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .build();
            var returnVal = userRepository.save(user);
            accountRequestRepository.delete(accountRequest);
            return new ResponseEntity<>(returnVal, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<String> patchUsername(@Validated @RequestBody UpdateUserRequestDTO request,
                                              @AuthenticationPrincipal User currentUser) {
        if (currentUser == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        String newPassword = request.getNewPassword();
        if(newPassword == null || newPassword.isBlank()){
            return new ResponseEntity<>("Please provide a password", HttpStatus.BAD_REQUEST);
        }

        try {
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(currentUser);
            return new ResponseEntity<>("You have successfully updated the password.", HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@PathVariable UUID uuid){
        if(userRepository.existsById(uuid)){
            userRepository.deleteById(uuid);
            return ResponseEntity.ok("User deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
}