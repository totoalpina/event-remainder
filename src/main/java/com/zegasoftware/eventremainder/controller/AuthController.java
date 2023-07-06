package com.zegasoftware.eventremainder.controller;

import com.zegasoftware.eventremainder.auth.core.AuthenticationRequest;
import com.zegasoftware.eventremainder.auth.core.AuthenticationResponse;
import com.zegasoftware.eventremainder.auth.core.AuthenticationService;
import com.zegasoftware.eventremainder.auth.jwt.JwtProvider;
import com.zegasoftware.eventremainder.auth.service.RefreshTokenService;
import com.zegasoftware.eventremainder.auth.token.RefreshToken;
import com.zegasoftware.eventremainder.auth.token.TokenType;
import com.zegasoftware.eventremainder.model.mapper.UserMapper;
import com.zegasoftware.eventremainder.model.payload.RefreshTokenRequest;
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

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtProvider jwtService;
    @Autowired
    private UserMapper userMapper;

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

    @PostMapping("/token/refresh")
    public ResponseEntity<Object> refreshToken(@RequestBody RefreshTokenRequest request) {
        if (request == null || request.refreshToken() == null || request.refreshToken().isEmpty()) {
            return new ResponseEntity<>("Refresh token is required", HttpStatus.BAD_REQUEST);
        }

        String username = jwtService.extractUsername(request.refreshToken());
        String token = jwtService.generateRefreshToken(username);
        var user = userService.findByEmail(username).map(userMapper::map);
        if (!user.isEmpty()) {
            refreshTokenService.deleteByUser(user.get());
            refreshTokenService.save(new RefreshToken().builder()
                    .refreshTokenValue(token)
                    .user(user.get())
                    .tokenType(TokenType.BEARER)
                    .build());
            return ResponseEntity.ok(
                    new RefreshToken().builder()
                            .refreshTokenValue(token)
                            .user(user.get())
                            .build());
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}
