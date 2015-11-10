package com.fortmocks.core.model.user.message;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.user.dto.UserDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateCurrentUserInput implements Input {

    private UserDto user;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
