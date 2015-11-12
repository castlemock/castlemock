package com.fortmocks.core.model.user.message;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadUserByUsernameInput implements Input {

    @NotNull
    private String username;

    public ReadUserByUsernameInput() {
    }

    public ReadUserByUsernameInput(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
