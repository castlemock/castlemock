package com.castlemock.web.core.model.user;

import com.castlemock.model.core.user.Role;
import com.castlemock.model.core.user.Status;

public final class UpdateUserRequestTestBuilder {

    private UpdateUserRequestTestBuilder() {

    }

    public static UpdateUserRequest.Builder builder() {
        return UpdateUserRequest.builder()
                .role(Role.ADMIN)
                .status(Status.ACTIVE)
                .email("test@test.com")
                .password("password")
                .username("admin");
    }

    public static UpdateUserRequest build() {
        return builder().build();
    }

}
