package com.zegasoftware.eventremainder.service;

import com.zegasoftware.eventremainder.model.dto.UserDto;
import com.zegasoftware.eventremainder.model.entity.User;
import com.zegasoftware.eventremainder.model.enums.UserRole;
import com.zegasoftware.eventremainder.model.enums.UserStatus;
import com.zegasoftware.eventremainder.model.mapper.UserMapper;
import com.zegasoftware.eventremainder.model.payload.UserRegistrationRequest;
import com.zegasoftware.eventremainder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

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
        Optional<UserRegistrationRequest> optionalUser = Optional.of(user);
        return optionalUser.map(u -> {
            User newUser = userMapper.registrationRequestToUser(u);
            newUser.setPassword(passwordEncoder.encode(u.getPassword()));
            newUser.setRole(UserRole.USER.name());
            newUser.setStatus(UserStatus.ACTIVE);
            User usr = userRepository.save(newUser);
            return usr.getId() != null;
        }).orElse(false);
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

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::map)
                .collect(Collectors.toList());
    }
}
