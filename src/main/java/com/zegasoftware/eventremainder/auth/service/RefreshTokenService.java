package com.zegasoftware.eventremainder.auth.service;

import com.zegasoftware.eventremainder.auth.token.RefreshToken;
import com.zegasoftware.eventremainder.model.entity.User;

public interface RefreshTokenService {


    int deleteByUser(User user);

    boolean save(RefreshToken refreshToken);

}
