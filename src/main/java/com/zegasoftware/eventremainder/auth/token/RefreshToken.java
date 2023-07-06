package com.zegasoftware.eventremainder.auth.token;

import com.zegasoftware.eventremainder.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_generator")
    @SequenceGenerator(name = "refresh_token_generator", sequenceName = "refresh_token_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, name = "refresh_token_value")
    private String refreshTokenValue;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof RefreshToken refreshToken) {
            return Objects.equals(getId(), refreshToken.getId()) && Objects.equals(getRefreshTokenValue(),
                    refreshToken.getRefreshTokenValue()) &&
                    Objects.equals(getTokenType(), refreshToken.getTokenType())  &&
                    Objects.equals(getUser(), refreshToken.getUser());
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId(), getRefreshTokenValue(), getTokenType(), getUser());
    }
}
