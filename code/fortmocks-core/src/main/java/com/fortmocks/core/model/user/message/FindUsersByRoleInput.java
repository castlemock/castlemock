package com.fortmocks.core.model.user.message;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.user.Role;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class FindUsersByRoleInput implements Input {

    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
