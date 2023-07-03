package com.zegasoftware.eventremainder.auth.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = """
            select t from Token t
            inner join t.user u
            where u.id = :id and (t.expired = false and t.revoked = false)
            """)
    List<Token> findValidTokensByUserId(@Param("id") Long id);

    @Query("select t from Token t where t.tokenValue = :token and t.expired = false and t.revoked = false")
    Optional<Token> findByTokenAndNotExpiredAndNotRevoked(@Param("token") String token);
}
