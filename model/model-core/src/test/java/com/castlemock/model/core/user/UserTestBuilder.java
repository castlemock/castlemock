package com.castlemock.model.core.user;

import java.sql.Timestamp;
import java.util.Date;

public final class UserTestBuilder {

    private UserTestBuilder() {

    }

    public static User.Builder builder() {
        return User.builder()
                .id("User")
                .role(Role.ADMIN)
                .status(Status.ACTIVE)
                .email("test@test.com")
                .password("password")
                .username("admin")
                .created(new Timestamp(new Date().getTime()))
                .updated(new Timestamp(new Date().getTime()));
    }

    public static User build() {
        return builder().build();
    }

}
