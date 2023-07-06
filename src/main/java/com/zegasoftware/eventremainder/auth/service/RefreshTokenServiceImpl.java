package com.zegasoftware.eventremainder.auth.service;

import com.zegasoftware.eventremainder.auth.token.RefreshToken;
import com.zegasoftware.eventremainder.auth.token.RefreshTokenRepository;
import com.zegasoftware.eventremainder.model.entity.User;
import com.zegasoftware.eventremainder.model.mapper.UserMapper;
import com.zegasoftware.eventremainder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public int deleteByUser(User user) {
        return refreshTokenRepository.deleteByUser(user.getId());
    }

    @Override
    public boolean save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken).getId() != null;
    }

    public Optional<RefreshToken> findByRefreshTokenValue(String token) {
        return refreshTokenRepository.findByRefreshTokenValue(token);
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userId);
    }
}
