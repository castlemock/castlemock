package com.fortmocks.core.model.user.message;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.validation.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateCurrentUserInput implements Input {

    @NotNull
    private UserDto user;

    public UpdateCurrentUserInput() {
    }

    public UpdateCurrentUserInput(UserDto user) {
        this.user = user;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
