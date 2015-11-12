package com.fortmocks.core.model.user.message;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.user.domain.Role;
import com.fortmocks.core.model.validation.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadUsersByRoleInput implements Input {

    @NotNull
    private Role role;

    public ReadUsersByRoleInput() {
    }

    public ReadUsersByRoleInput(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
