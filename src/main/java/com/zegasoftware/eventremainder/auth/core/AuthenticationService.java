package com.zegasoftware.eventremainder.auth.core;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
