package com.zegasoftware.eventremainder.auth.core;

import com.zegasoftware.eventremainder.auth.jwt.JwtProvider;
import com.zegasoftware.eventremainder.auth.token.Token;
import com.zegasoftware.eventremainder.auth.token.TokenRepository;
import com.zegasoftware.eventremainder.auth.token.TokenType;
import com.zegasoftware.eventremainder.model.entity.User;
import com.zegasoftware.eventremainder.model.enums.UserStatus;
import com.zegasoftware.eventremainder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtProvider jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = userRepository.findByEmail(request.email()).orElseThrow();
        if (UserStatus.ACTIVE.equals(user.getStatus())) {
            var userDetails = userDetailsService.loadUserByUsername(request.email());
            String listRoles = user.getRole();
            var jwtToken = jwtService.generateToken(listRoles, userDetails);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            return AuthenticationResponse.builder().token(jwtToken).build();
        }
        return AuthenticationResponse.builder().token("").build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .tokenValue(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findValidTokensByUserId(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
