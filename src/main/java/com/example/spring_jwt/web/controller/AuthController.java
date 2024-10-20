package com.example.spring_jwt.web.controller;

import com.example.spring_jwt.exception.AlreadyExitsException;
import com.example.spring_jwt.repository.UserRepository;
import com.example.spring_jwt.security.SecurityService;
import com.example.spring_jwt.web.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final SecurityService securityService;
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(securityService.authenticateUser(loginRequest));
    }
    @PostMapping("/register")
    public ResponseEntity<SimpleResponse> registerUser(@RequestBody CreateUserRequest createUserRequest){
        if (userRepository.existsByUsername(createUserRequest.getUsername())){
            throw new AlreadyExitsException("Username already exist!");
        }
        if (userRepository.existsByEmail(createUserRequest.getEmail())){
            throw new AlreadyExitsException("Email already exist!");
        }
        securityService.register(createUserRequest);

        return ResponseEntity.ok(new SimpleResponse("User created!"));

    }
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> registerToken(@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(securityService.refreshTokenResponse(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<SimpleResponse> logoutUser(@AuthenticationPrincipal UserDetails userDetails){
        securityService.logout();

        return ResponseEntity.ok(new SimpleResponse("User logout. Usernames is: " + userDetails.getUsername()));
    }
}
