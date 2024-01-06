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

package com.castlemock.repository.core.file.user;

import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.user.Role;
import com.castlemock.model.core.user.Status;
import com.castlemock.model.core.user.User;
import com.castlemock.model.core.utility.IdUtility;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.core.file.user.converter.UserConverter;
import com.castlemock.repository.core.file.user.converter.UserFileConverter;
import com.castlemock.repository.core.file.user.model.UserFile;
import com.castlemock.repository.user.UserRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving users to the file system. Each user is stored as a separate file.
 * The class also contains the directory and the filename extension for the user.
 * @author Karl Dahlgren
 * @since 1.0
 * @see UserRepository
 * @see FileRepository
 * @see UserFile
 * @see User
 */
@Repository
@Profile(Profiles.FILE)
public class UserFileRepository extends FileRepository<UserFile, User, String> implements UserRepository {

    @Value(value = "${user.file.directory}")
    private String userFileDirectory;
    @Value(value = "${user.file.extension}")
    private String userFileExtension;
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public UserFileRepository() {
        super(UserFileConverter::toUser, UserConverter::toUserFile);
    }

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful. The method is responsible for creating an administrator
     * and saving it to the file system in case of no user is registered on application startup. This is
     * a typical scenario for when users are using Castle Mock for the first time after the installation.
     * @see #initialize
     * @see UserFile
     */
    @Override
    protected void postInitiate() {
        if(collection.isEmpty()){
            final User user = User.builder()
                    .id(IdUtility.generateId())
                    .username("admin")
                    .fullName("Admin Admin")
                    .password(PASSWORD_ENCODER.encode("admin"))
                    .status(Status.ACTIVE)
                    .role(Role.ADMIN)
                    .created(new Date())
                    .updated(new Date())
                    .email("")
                    .build();
            save(user);
        }
    }

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return userFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return userFileExtension;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param user The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     * @see UserFile
     */
    @Override
    protected void checkType(final UserFile user) {
        Preconditions.checkNotNull(user, "User cannot be null");
        Preconditions.checkNotNull(user.getId());
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




}
