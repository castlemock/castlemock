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
import com.castlemock.repository.user.UserRepository;
import com.castlemock.service.core.user.input.ReadUserByUsernameInput;
import com.castlemock.service.core.user.output.ReadUserByUsernameOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadUserByUsernameServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private ReadUserByUsernameService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final User user = UserTestBuilder.builder()
                .id("123")
                .username("Username")
                .password("Password")
                .status(Status.ACTIVE)
                .role(Role.ADMIN)
                .email("email@email.com")
                .build();

        Mockito.when(repository.findAll()).thenReturn(List.of(user));
        final ReadUserByUsernameInput input = ReadUserByUsernameInput.builder()
                .username("Username")
                .build();
        final ServiceTask<ReadUserByUsernameInput> serviceTask = ServiceTask.of(input, "user");
        final ServiceResult<ReadUserByUsernameOutput> serviceResult = service.process(serviceTask);
        final ReadUserByUsernameOutput output = serviceResult.getOutput();

        final User returnedUser = output.getUser().orElse(null);
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(user.getId(), returnedUser.getId());
        Assert.assertEquals(user.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(user.getRole(), returnedUser.getRole());
        Assert.assertEquals(user.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(user.getUsername(), returnedUser.getUsername());
    }


}
