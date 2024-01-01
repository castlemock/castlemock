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

package com.castlemock.service.core.user;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.core.user.Role;
import com.castlemock.model.core.user.Status;
import com.castlemock.model.core.user.User;
import com.castlemock.model.core.user.UserTestBuilder;
import com.castlemock.repository.token.SessionTokenRepository;
import com.castlemock.repository.user.UserRepository;
import com.castlemock.service.core.user.input.UpdateCurrentUserInput;
import com.castlemock.service.core.user.output.UpdateCurrentUserOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateCurrentUserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private SessionTokenRepository sessionTokenRepository;

    @InjectMocks
    private UpdateCurrentUserService service;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final List<User> users = new ArrayList<>();
        final User user = UserTestBuilder.builder()
                .id("123")
                .username("Username")
                .password("Password")
                .status(Status.ACTIVE)
                .role(Role.ADMIN)
                .email("email@email.com")
                .build();
        users.add(user);

        final User updatedUser = UserTestBuilder.builder()
                .id("123")
                .username("UpdatedUsername")
                .password("UpdatedPassword")
                .status(Status.ACTIVE)
                .role(Role.ADMIN)
                .email("email@email.com")
                .build();

        Mockito.when(repository.findOne(Mockito.anyString())).thenReturn(user);
        Mockito.when(repository.findAll()).thenReturn(users);
        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
        final UpdateCurrentUserInput input = UpdateCurrentUserInput.builder()
                .fullName(updatedUser.getFullName()
                        .orElse(null))
                .password(updatedUser.getPassword())
                .email(updatedUser.getEmail()
                        .orElse(null))
                .username(updatedUser.getUsername())
                .build();
        final ServiceTask<UpdateCurrentUserInput> serviceTask = ServiceTask.of(input, "Username");
        final ServiceResult<UpdateCurrentUserOutput> serviceResult = service.process(serviceTask);
        final UpdateCurrentUserOutput output = serviceResult.getOutput();

        final String encodedPassword = PASSWORD_ENCODER.encode(user.getPassword());
        final User returnedUser = output.getUpdatedUser();
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(updatedUser.getId(), returnedUser.getId());
        Assert.assertNotEquals(updatedUser.getPassword(), returnedUser.getPassword());
        Assert.assertNotEquals(encodedPassword, returnedUser.getPassword());
        Assert.assertEquals(updatedUser.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(updatedUser.getRole(), returnedUser.getRole());
        Assert.assertEquals(updatedUser.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(updatedUser.getUsername(), returnedUser.getUsername());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(User.class));
    }


}
