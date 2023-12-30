package com.castlemock.web.core.model.user;

import com.castlemock.model.core.user.Role;
import com.castlemock.model.core.user.Status;

public final class CreateUserRequestTestBuilder {

    private CreateUserRequestTestBuilder() {

    }

    public static CreateUserRequest.Builder builder() {
        return CreateUserRequest.builder()
                .role(Role.ADMIN)
                .status(Status.ACTIVE)
                .email("test@test.com")
                .password("password")
                .username("admin");
    }

    public static CreateUserRequest build() {
        return builder().build();
    }

}
