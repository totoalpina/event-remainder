package com.zegasoftware.eventremainder.auth.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshTokenValue(String token);

    @Modifying
    @Transactional
    @Query("delete from RefreshToken rt where rt.user.id = :userId")
    int deleteByUser(long userId);


}
