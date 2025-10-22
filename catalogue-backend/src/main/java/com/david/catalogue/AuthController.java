package com.david.catalogue;

import com.david.catalogue.user.User;
import com.david.catalogue.user.UserRole;
import com.david.catalogue.user.UserRepository;
import com.david.catalogue.user.userRequests.AccountRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        String token = tokenProvider.generate(auth);
        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AccountRequest accountRequest) {
        User user = new User();
        user.setUsername(accountRequest.getUsername());
        user.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        user.setUserRole(UserRole.USER);
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    public record AuthResponse(String accessToken) {
    }
}