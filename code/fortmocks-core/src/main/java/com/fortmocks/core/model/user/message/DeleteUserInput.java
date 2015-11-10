package com.fortmocks.core.model.user.message;

import com.fortmocks.core.model.Input;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteUserInput implements Input{

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
