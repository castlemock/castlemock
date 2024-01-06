package com.castlemock.repository.core.file.user.converter;

import com.castlemock.model.core.user.User;
import com.castlemock.repository.core.file.user.model.UserFile;

public final class UserFileConverter {

    private UserFileConverter() {

    }

    public static User toUser(final UserFile user) {
        return User.builder()
                .id(user.getId())
                .created(user.getCreated())
                .role(user.getRole())
                .status(user.getStatus())
                .updated(user.getUpdated())
                .password(user.getPassword())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .build();
    }

}
