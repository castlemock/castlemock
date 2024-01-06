package com.castlemock.repository.core.file.user.model;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.core.user.Role;
import com.castlemock.model.core.user.Status;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;

@XmlRootElement(name = "user")
public class UserFile implements Saveable<String> {

    @Mapping("id")
    private String id;
    @Mapping("username")
    private String username;
    @Mapping("password")
    private String password;
    @Mapping("email")
    private String email;
    @Mapping("fullName")
    private String fullName;
    @Mapping("updated")
    private Date updated;
    @Mapping("created")
    private Date created;
    @Mapping("status")
    private Status status;
    @Mapping("role")
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
    @XmlElement
    @Override
    public String getId() {
        return id;
    }


    /**
     * Set a new value to user id
     * @param id New user id
     */
    @Override
    public void setId(String id) {
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

    @XmlElement
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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