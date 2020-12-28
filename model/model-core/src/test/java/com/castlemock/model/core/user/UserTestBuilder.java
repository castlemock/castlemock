package com.castlemock.model.core.user;

import java.sql.Timestamp;
import java.util.Date;

public class UserTestBuilder {

    private String id;
    private String username;
    private String password;
    private String email;
    private Date updated;
    private Date created;
    private Status status;
    private Role role;

    private UserTestBuilder() {
        this.id = "User";
        this.role = Role.ADMIN;
        this.status = Status.ACTIVE;
        this.email = "test@test.com";
        this.password = "password";
        this.username = "admin";
        this.created = new Timestamp(new Date().getTime());
        this.updated = new Timestamp(new Date().getTime());
    }

    public static UserTestBuilder builder() {
        return new UserTestBuilder();
    }

    public UserTestBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public UserTestBuilder username(final String username) {
        this.username = username;
        return this;
    }

    public UserTestBuilder password(final String password) {
        this.password = password;
        return this;
    }

    public UserTestBuilder email(final String email) {
        this.email = email;
        return this;
    }

    public UserTestBuilder updated(final Date updated) {
        this.updated = updated;
        return this;
    }

    public UserTestBuilder created(final Date created) {
        this.created = created;
        return this;
    }

    public UserTestBuilder status(final Status status) {
        this.status = status;
        return this;
    }

    public UserTestBuilder role(final Role role) {
        this.role = role;
        return this;
    }

    public User build() {
        return User.builder()
                .created(created)
                .email(email)
                .id(id)
                .password(password)
                .role(role)
                .status(status)
                .updated(updated)
                .username(username)
                .build();
    }

}
