package com.castlemock.web.core.model.user;

import com.castlemock.model.core.user.Role;
import com.castlemock.model.core.user.Status;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Optional;

@XmlRootElement
public class UpdateUserRequest {

    private String username;
    private String password;
    private String email;
    private String fullName;
    private Status status;
    private Role role;

    private UpdateUserRequest(){

    }

    private UpdateUserRequest(final Builder builder){
        this.username = Objects.requireNonNull(builder.username, "username");
        this.password = builder.password;
        this.email = builder.email;
        this.fullName = builder.fullName;
        this.status = Objects.requireNonNull(builder.status, "status");
        this.role = Objects.requireNonNull(builder.role, "role");
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
     * Get the user password
     * @return Returns the user password
     */
    @XmlElement
    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    /**
     * Get user email
     * @return Returns user email
     */
    @XmlElement
    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    @XmlElement
    public Optional<String> getFullName() {
        return Optional.ofNullable(fullName);
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
     * Returns the users current role
     * @return User role
     */
    @XmlElement
    public Role getRole() {
        return role;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String username;
        private String password;
        private String email;
        private String fullName;
        private Status status;
        private Role role;

        private Builder() {
        }

        public Builder username(final String username) {
            this.username = username;
            return this;
        }

        public Builder password(final String password) {
            this.password = password;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public Builder fullName(final String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder status(final Status status) {
            this.status = status;
            return this;
        }

        public Builder role(final Role role) {
            this.role = role;
            return this;
        }


        public UpdateUserRequest build() {
            return new UpdateUserRequest(this);
        }
    }
}
