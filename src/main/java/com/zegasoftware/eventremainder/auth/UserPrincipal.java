package com.zegasoftware.eventremainder.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class UserPrincipal implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private String status;
    private String rolesOfUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String[] roles = rolesOfUser.split("#");
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !"EXPIRED".equals(status);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !"LOCKED".equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !"EXPIRED".equals(status);
    }

    @Override
    public boolean isEnabled() {
        return !"INACTIVE".equals(status);
    }
}
