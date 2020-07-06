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

package com.castlemock.repository.core.dynamodb.user;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.user.domain.Role;
import com.castlemock.core.basis.model.user.domain.Status;
import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.dynamodb.DynamoRepository;
import com.castlemock.repository.user.UserRepository;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * The class is an implementation of the dynamodb repository and provides the functionality to interact with the dynamodb.
 * The repository is responsible for loading and saving users to the dynamodb.
 *
 * @author Tiago Santos
 * @see UserRepository
 * @see DynamoRepository
 * @see UserDocument
 * @see User
 * @since 1.51
 */
@Repository
@Profile(Profiles.DYNAMODB)
public class UserDynamoRepository extends DynamoRepository<UserDynamoRepository.UserDocument, User, String> implements UserRepository {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Autowired
    public UserDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB,
                                DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public UserDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful. The method is responsible for creating an administrator
     * and saving it to dynamodb in case of no user is registered on application startup. This is
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
            user.setFullName("Admin Admin");
            user.setPassword(PASSWORD_ENCODER.encode("admin"));
            user.setStatus(Status.ACTIVE);
            user.setRole(Role.ADMIN);
            user.setCreated(new Date());
            user.setUpdated(new Date());
            user.setEmail("");
            save(user);
        }
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to dynamodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to dynamodb, but also loaded from the
     * dynamodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param user The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             dynamodb.
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

    @DynamoDBTable(tableName = "user")
    @Getter
    @Setter
    public static class UserDocument implements Saveable<String> {

        @DynamoDBHashKey(attributeName = "id")
        @DynamoDBAutoGeneratedKey
        @Mapping("id")
        private String id;
        @DynamoDBAttribute(attributeName = "username")
        @Mapping("username")
        private String username;
        @DynamoDBAttribute(attributeName = "password")
        @Mapping("password")
        private String password;
        @DynamoDBAttribute(attributeName = "email")
        @Mapping("email")
        private String email;
        @DynamoDBAttribute(attributeName = "fullName")
        @Mapping("fullName")
        private String fullName;
        @DynamoDBAttribute(attributeName = "updated")
        @Mapping("updated")
        private Date updated;
        @DynamoDBAttribute(attributeName = "created")
        @Mapping("created")
        private Date created;
        @DynamoDBAttribute(attributeName = "status")
        @DynamoDBTypeConvertedEnum
        @Mapping("status")
        private Status status;
        @DynamoDBAttribute(attributeName = "role")
        @DynamoDBTypeConvertedEnum
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
    }

}
