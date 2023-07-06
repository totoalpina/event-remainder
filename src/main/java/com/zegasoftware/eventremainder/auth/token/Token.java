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
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_generator")
    @SequenceGenerator(name = "token_generator", sequenceName = "token_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, name = "token_value")
    private String tokenValue;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked = false;

    private boolean expired = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof Token token) {
            return Objects.equals(getId(), token.getId()) && Objects.equals(getTokenValue(), token.getTokenValue()) &&
                    Objects.equals(getTokenType(), token.getTokenType()) &&
                    Objects.equals(isRevoked(), token.isRevoked()) && Objects.equals(isExpired(), token.isExpired()) &&
                    Objects.equals(getUser(), token.getUser());
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId(), getTokenValue(), getTokenType(), isRevoked(), isExpired(), getUser());
    }
}
