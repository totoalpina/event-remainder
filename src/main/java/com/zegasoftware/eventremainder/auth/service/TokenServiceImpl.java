package com.zegasoftware.eventremainder.auth.service;

import com.zegasoftware.eventremainder.auth.token.Token;
import com.zegasoftware.eventremainder.auth.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void revokeToken(String token) {
        Optional<Token> optionalToken = tokenRepository.findByTokenAndNotExpiredAndNotRevoked(token);
        optionalToken.ifPresent(t -> {
            t.setExpired(true);
            t.setRevoked(true);
            tokenRepository.save(t);
        });
    }
}
