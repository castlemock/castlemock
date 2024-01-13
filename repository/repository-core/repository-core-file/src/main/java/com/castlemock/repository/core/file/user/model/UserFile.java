package com.castlemock.repository.core.file.user.model;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.core.user.Role;
import com.castlemock.model.core.user.Status;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.NONE)
public class UserFile implements Saveable<String> {

    @XmlElement
    private String id;
    @XmlElement
    private String username;
    @XmlElement
    private String password;

    @XmlElement
    private String email;
    @XmlElement
    private String fullName;
    @XmlElement
    private Date updated;
    @XmlElement
    private Date created;
    @XmlElement
    private Status status;
    @XmlElement
    private Role role;

    /**
     * Default constructor for the User class. The constructor will set the current time to both the created
     * and updated variables.
     */
    private UserFile() {
    }

    private UserFile(final Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.username = Objects.requireNonNull(builder.username, "username");
        this.password = Objects.requireNonNull(builder.password, "password");
        this.email = builder.email;
        this.fullName = builder.fullName;
        this.updated = Objects.requireNonNull(builder.updated, "updated");
        this.created = Objects.requireNonNull(builder.created, "created");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.role = Objects.requireNonNull(builder.role, "role");
    }

    /**
     * Get the user id
     * @return User id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Get the user username
     * @return User username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get user email
     * @return Returns user email
     */
    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    /**
     * Get the user password
     * @return Returns the user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Return the timestamp for when the user was updated
     * @return Updated timestamp
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * Returns the timestamp of when the user was created
     * @return Created timestamp
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Get the current status of user
     * @return User status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Returns the users current role
     * @return User role
     */
    public Role getRole() {
        return role;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String username;
        private String password;
        private String email;
        private String fullName;
        private Date updated;
        private Date created;
        private Status status;
        private Role role;

        private Builder() {
        }

        public Builder id(final String id) {
            this.id = id;
            return this;
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

        public Builder updated(final Date updated) {
            this.updated = updated;
            return this;
        }

        public Builder created(final Date created) {
            this.created = created;
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

        public UserFile build() {
            return new UserFile(this);
        }
    }
}