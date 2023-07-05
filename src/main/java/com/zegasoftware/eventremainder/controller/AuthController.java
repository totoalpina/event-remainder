package com.zegasoftware.eventremainder.controller;

import com.zegasoftware.eventremainder.auth.core.AuthenticationRequest;
import com.zegasoftware.eventremainder.auth.core.AuthenticationResponse;
import com.zegasoftware.eventremainder.auth.core.AuthenticationService;
import com.zegasoftware.eventremainder.model.payload.UserRegistrationRequest;
import com.zegasoftware.eventremainder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserRegistrationRequest newUser, BindingResult result) {
        if (result.hasErrors() || newUser == null) {
            return ResponseEntity.badRequest().body(result.getAllErrors().get(0).getDefaultMessage());
        }
        return userService.createUser(newUser)
                ? ResponseEntity.ok("User registered successfully")
                : ResponseEntity.badRequest().body("User registration failed");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = authenticationService.authenticate(request);
            if (response == null || response.getToken() == null || response.getToken().isEmpty()) {
                return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
            }
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }
}
