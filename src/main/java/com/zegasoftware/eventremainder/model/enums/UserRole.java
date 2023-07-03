package com.zegasoftware.eventremainder.model.enums;

import java.util.Optional;

public enum UserRole {
    ADMIN, USER, GUEST;

    public Optional<UserRole> fromString(String name) {
        for (UserRole ur : UserRole.values()) {
            if (ur.name().equals(name)) {
                return Optional.of(ur);
            }
        }
        return Optional.empty();
    }
}
