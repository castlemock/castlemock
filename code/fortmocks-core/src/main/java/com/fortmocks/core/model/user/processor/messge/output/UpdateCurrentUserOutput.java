package com.fortmocks.core.model.user.processor.messge.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.validation.NotNull;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateCurrentUserOutput implements Output{

    @NotNull
    private UserDto updatedUser;

    public UserDto getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(UserDto updatedUser) {
        this.updatedUser = updatedUser;
    }
}
