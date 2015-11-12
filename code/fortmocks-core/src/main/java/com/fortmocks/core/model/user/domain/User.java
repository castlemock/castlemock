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

package com.fortmocks.core.model.user.domain;


import com.fortmocks.core.model.Saveable;
import com.fortmocks.core.model.user.dto.UserDto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The User class represent the Fort Mocks user and contains all the information about the user, such as username
 * and password. The user class will be used upon authentication towards Fort Mocks
 * @author Karl Dahlgren
 * @since 1.0
 * @see UserDto
 */
@XmlRootElement
public class User implements Saveable<Long> {

    private Long id = 0L;
    private String username;
    private String password;
    private String email;
    private Date updated;
    private Date created;
    private Status status;
    private Role role;

    /**
     * Default constructor for the User class. The constructor will set the current time to both the created
     * and updated variables.
     */
    public User() {
        this.created = new Timestamp(new Date().getTime());
        this.updated = new Timestamp(new Date().getTime());
    }

    /**
     * Get the user id
     * @return User id
     */
    @XmlElement
    @Override
    public Long getId() {
        return id;
    }


    /**
     * Set a new value to user id
     * @param id New user id
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the user username
     * @return User username
     */
    @XmlElement
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
     * Get user email
     * @return Returns user email
     */
    @XmlElement
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
     * Get the user password
     * @return Returns the user password
     */
    @XmlElement
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
     * Return the timestamp for when the user was updated
     * @return Updated timestamp
     */
    @XmlElement
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
    @XmlElement
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
    @XmlElement
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
    @XmlElement
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
}