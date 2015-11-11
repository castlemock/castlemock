package com.fortmocks.core.model.user.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.validation.NotNull;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class FindUsersByRoleOutput implements Output{

    @NotNull
    private List<UserDto> users;

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
