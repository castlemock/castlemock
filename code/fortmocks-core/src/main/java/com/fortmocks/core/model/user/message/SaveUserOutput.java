package com.fortmocks.core.model.user.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.core.model.user.dto.UserDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SaveUserOutput implements Output {

    private UserDto savedUser;

    public UserDto getSavedUser() {
        return savedUser;
    }

    public void setSavedUser(UserDto savedUser) {
        this.savedUser = savedUser;
    }
}
