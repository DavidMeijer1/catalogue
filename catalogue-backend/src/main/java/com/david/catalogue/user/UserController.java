package com.david.catalogue.user;

import com.david.catalogue.book.Book;
import com.david.catalogue.user.userRequests.AccountRequestRepository;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

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

    @PatchMapping("/update/username")
    public ResponseEntity<User> patchUsername(@Validated @RequestBody String newUsername,
                                              @AuthenticationPrincipal User currentUser) {
        if (newUsername.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            currentUser.setUsername(newUsername);
            return new ResponseEntity<>(userRepository.save(currentUser), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@PathVariable long id){
        userRepository.deleteAll();
        return ResponseEntity.ok("Deleted");
    }
}