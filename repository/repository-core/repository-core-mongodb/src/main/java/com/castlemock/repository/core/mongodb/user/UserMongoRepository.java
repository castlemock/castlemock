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

package com.castlemock.repository.core.mongodb.user;

import com.castlemock.model.core.model.Saveable;
import com.castlemock.model.core.model.SearchQuery;
import com.castlemock.model.core.model.user.domain.Role;
import com.castlemock.model.core.model.user.domain.Status;
import com.castlemock.model.core.model.user.domain.User;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.castlemock.repository.user.UserRepository;
import com.google.common.base.Preconditions;
import org.dozer.Mapping;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * The class is an implementation of the mongo repository and provides the functionality to interact with the mongodb.
 * The repository is responsible for loading and saving users to the mongodb.
 *
 * @author Mohammad hewedy
 * @see UserRepository
 * @see MongoRepository
 * @see UserDocument
 * @see User
 * @since 1.35
 */
@Repository
@Profile(Profiles.MONGODB)
public class UserMongoRepository extends MongoRepository<UserMongoRepository.UserDocument, User, String> implements UserRepository {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful. The method is responsible for creating an administrator
     * and saving it to mongodb in case of no user is registered on application startup. This is
     * a typical scenario for when users are using Castle Mock for the first time after the installation.
     *
     * @see #initialize
     * @see UserDocument
     */
    @Override
    protected void postInitiate() {
        if (count() == 0) {
            final User user = new User();
            user.setUsername("admin");
            user.setPassword(PASSWORD_ENCODER.encode("admin"));
            user.setStatus(Status.ACTIVE);
            user.setRole(Role.ADMIN);
            user.setCreated(new Date());
            user.setUpdated(new Date());
            user.setEmail(new String());
            save(user);
        }
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to mongodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to mongodb, but also loaded from the
     * mongodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param user The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             mongodb.
     * @see #save
     * @see UserDocument
     */
    @Override
    protected void checkType(final UserDocument user) {
        Preconditions.checkNotNull(user, "User cannot be null");
        Preconditions.checkNotNull(user.getCreated());
        Preconditions.checkNotNull(user.getEmail());
        Preconditions.checkNotNull(user.getPassword());
        Preconditions.checkNotNull(user.getRole());
        Preconditions.checkNotNull(user.getStatus());
        Preconditions.checkNotNull(user.getUpdated());
        Preconditions.checkNotNull(user.getUsername());
        Preconditions.checkArgument(!user.getPassword().isEmpty(), "User password cannot be empty");
        Preconditions.checkArgument(!user.getUsername().isEmpty(), "User username cannot be empty");
    }

    @Override
    public List<User> search(SearchQuery query) {
        throw new UnsupportedOperationException("Search method is not supported in the User repository");
    }


    @Document(collection = "user")
    protected static class UserDocument implements Saveable<String> {

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
         * Default constructor for the User class. The constructor will set the current time to both the created
         * and updated variables.
         */
        public UserDocument() {
            this.created = new Timestamp(new Date().getTime());
            this.updated = new Timestamp(new Date().getTime());
        }

        /**
         * Get the user id
         *
         * @return User id
         */
        @Override
        public String getId() {
            return id;
        }


        /**
         * Set a new value to user id
         *
         * @param id New user id
         */
        @Override
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Get the user username
         *
         * @return User username
         */
        public String getUsername() {
            return username;
        }

        /**
         * Set a new value to the user username
         *
         * @param username New username value
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * Get user email
         *
         * @return Returns user email
         */
        public String getEmail() {
            return email;
        }

        /**
         * Set a new value to user email
         *
         * @param email New user email value
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * Get the user password
         *
         * @return Returns the user password
         */
        public String getPassword() {
            return password;
        }

        /**
         * Set a new password for the user
         *
         * @param password New password value
         */
        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * Return the timestamp for when the user was updated
         *
         * @return Updated timestamp
         */
        public Date getUpdated() {
            return updated;
        }

        /**
         * Sets a new updated timestamp
         *
         * @param updated New updated timestamp value
         */
        public void setUpdated(Date updated) {
            this.updated = updated;
        }

        /**
         * Returns the timestamp of when the user was created
         *
         * @return Created timestamp
         */
        public Date getCreated() {
            return created;
        }

        /**
         * Sets a new created timestamp
         *
         * @param created New created timestamp
         */
        public void setCreated(Date created) {
            this.created = created;
        }

        /**
         * Get the current status of user
         *
         * @return User status
         */
        public Status getStatus() {
            return status;
        }

        /**
         * Set a new status to the user
         *
         * @param status New status value
         */
        public void setStatus(Status status) {
            this.status = status;
        }

        /**
         * Returns the users current role
         *
         * @return User role
         */
        public Role getRole() {
            return role;
        }

        /**
         * Set a new value to the role value
         *
         * @param role New role value
         */
        public void setRole(Role role) {
            this.role = role;
        }

    }

}
