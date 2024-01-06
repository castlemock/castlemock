package com.castlemock.repository.core.file.user.converter;

import com.castlemock.model.core.user.User;
import com.castlemock.repository.core.file.user.model.UserFile;

public final class UserConverter {

    private UserConverter() {

    }

    public static UserFile toUserFile(final User user) {
        return UserFile.builder()
                .id(user.getId())
                .created(user.getCreated())
                .role(user.getRole())
                .status(user.getStatus())
                .updated(user.getUpdated())
                .password(user.getPassword())
                .username(user.getUsername())
                .email(user.getEmail()
                        .orElse(null))
                .fullName(user.getFullName()
                        .orElse(null))
                .build();
    }

}
