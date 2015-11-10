package com.fortmocks.core.model.user.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.core.model.user.dto.UserDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class FindAllUsersOutput implements Output {

    private List<UserDto> users;

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
