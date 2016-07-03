/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.core.basis.model.user.dto;

import com.castlemock.core.basis.model.user.domain.Role;
import com.castlemock.core.basis.model.user.domain.Status;
import com.castlemock.core.basis.model.user.domain.User;
import org.dozer.Mapping;

import java.util.Date;

/**
 * The User DTO class is a DTO (Data transfer object) for the user class
 * @author Karl Dahlgren
 * @since 1.0
 * @see User
 */
public class UserDto {

    @Mapping("id")
    private String id;

    @Mapping("username")
    private String username;

    @Mapping("password")
    private String password;

    @Mapping("email")
    private String email;

    @Mapping("updated")
    private Date updated;

    @Mapping("created")
    private Date created;

    @Mapping("status")
    private Status status;

    @Mapping("role")
    private Role role;

    /**
     * Get the user id
     * @return User id
     */
    public String getId() {
        return id;
    }

    /**
     * Set a new value to user id
     * @param id New user id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the user username
     * @return User username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set a new value to the user username
     * @param username New username value
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the user password
     * @return Returns the user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set a new password for the user
     * @param password New password value
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get user email
     * @return Returns user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set a new value to user email
     * @param email New user email value
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return the timestamp for when the user was updated
     * @return Updated timestamp
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * Sets a new updated timestamp
     * @param updated New updated timestamp value
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     * Returns the timestamp of when the user was created
     * @return Created timestamp
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Sets a new created timestamp
     * @param created New created timestamp
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * Get the current status of user
     * @return User status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set a new status to the user
     * @param status New status value
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns the users current role
     * @return User role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Set a new value to the role value
     * @param role New role value
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Compare the user with another user
     * @param o The compared object
     * @return True if the two users are equal towards each other
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDto)) {
            return false;
        }

        UserDto userDto = (UserDto) o;

        if (created != null ? !created.equals(userDto.created) : userDto.created != null) {
            return false;
        }
        if (email != null ? !email.equals(userDto.email) : userDto.email != null) {
            return false;
        }
        if (id != null ? !id.equals(userDto.id) : userDto.id != null) {
            return false;
        }
        if (password != null ? !password.equals(userDto.password) : userDto.password != null) {
            return false;
        }
        if (role != userDto.role) {
            return false;
        }
        if (status != userDto.status) {
            return false;
        }
        if (updated != null ? !updated.equals(userDto.updated) : userDto.updated != null) {
            return false;
        }
        if (username != null ? !username.equals(userDto.username) : userDto.username != null) {
            return false;
        }

        return true;
    }

    /**
     * Generates a hashcode for user
     * @return Hashcode for the user
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
