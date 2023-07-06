package com.zegasoftware.eventremainder.service;

import com.zegasoftware.eventremainder.model.dto.UserDto;
import com.zegasoftware.eventremainder.model.entity.User;
import com.zegasoftware.eventremainder.model.payload.UserRegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean existsByEmail(String email);

    boolean createUser(UserRegistrationRequest user);

    boolean deleteUser(Long id);

    Optional<UserDto> findByEmail(String email);

    boolean updateUser(UserRegistrationRequest user, final Long id);

    List<UserDto> getAllUsers();
}
