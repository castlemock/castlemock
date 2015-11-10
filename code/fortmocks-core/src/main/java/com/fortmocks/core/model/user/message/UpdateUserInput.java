package com.fortmocks.core.model.user.message;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.user.dto.UserDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateUserInput implements Input {

    private Long userId;
    private UserDto user;

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
