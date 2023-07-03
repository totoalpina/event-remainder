package com.zegasoftware.eventremainder.service;

import com.zegasoftware.eventremainder.model.payload.UserRegistrationRequest;

public interface UserService {

    boolean existsByEmail(String email);

    boolean createUser(UserRegistrationRequest user);

    boolean deleteUser(Long id);

    boolean activateUser(Long id);

    boolean updateUser(UserRegistrationRequest user, final Long id);
}
