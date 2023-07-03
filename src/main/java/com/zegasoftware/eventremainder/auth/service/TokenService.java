package com.zegasoftware.eventremainder.auth.service;

public interface TokenService {

    void revokeToken(String token);
}
