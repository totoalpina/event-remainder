package com.zegasoftware.eventremainder.service;

import com.zegasoftware.eventremainder.model.payload.UserRegistrationRequest;
import com.zegasoftware.eventremainder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment env;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean existsByEmail(final String email) {
        return false;
    }

    // TODO: Implement all methods in this class
    @Override
    public boolean createUser(final UserRegistrationRequest user) {
        return false;
    }

    @Override
    public boolean deleteUser(final Long id) {
        return false;
    }

    @Override
    public boolean activateUser(final Long id) {
        return false;
    }

    @Override
    public boolean updateUser(final UserRegistrationRequest user, final Long id) {
        return false;
    }
}
