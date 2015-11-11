package com.fortmocks.core.model.user.message;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.validation.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateUserInput implements Input {

    @NotNull
    private Long userId;
    @NotNull
    private UserDto user;

    public UpdateUserInput() {
    }

    public UpdateUserInput(Long userId, UserDto user) {
        this.userId = userId;
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
