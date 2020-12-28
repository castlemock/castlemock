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
import com.castlemock.service.core.user.input.ReadUsersByRoleInput;
import com.castlemock.service.core.user.output.ReadUsersByRoleOutput;
import com.castlemock.repository.user.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadUsersByRoleServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private ReadUsersByRoleService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        List<User> users = new ArrayList<User>();
        User user = new User();
        user.setId("123");
        user.setUsername("Username");
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.ADMIN);
        user.setEmail("email@email.com");
        users.add(user);


        Mockito.when(repository.findAll()).thenReturn(users);
        final ReadUsersByRoleInput input = new ReadUsersByRoleInput(Role.ADMIN);
        final ServiceTask<ReadUsersByRoleInput> serviceTask = new ServiceTask<ReadUsersByRoleInput>();
        serviceTask.setInput(input);
        final ServiceResult<ReadUsersByRoleOutput> serviceResult = service.process(serviceTask);
        final ReadUsersByRoleOutput output = serviceResult.getOutput();

        final List<User> returnedUsers = output.getUsers();
        Assert.assertNotNull(returnedUsers);
        Assert.assertEquals(users.size(), returnedUsers.size());
        final User returnedUser = returnedUsers.get(0);
        Assert.assertEquals(user.getId(), returnedUser.getId());
        Assert.assertEquals(user.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(user.getRole(), returnedUser.getRole());
        Assert.assertEquals(user.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(user.getUsername(), returnedUser.getUsername());
    }


}
