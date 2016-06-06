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

package com.castlemock.web.basis.model.user.service;

import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.model.user.domain.Role;
import com.castlemock.core.basis.model.user.domain.Status;
import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.model.user.dto.UserDto;
import com.castlemock.core.basis.model.user.service.message.input.CreateUserInput;
import com.castlemock.core.basis.model.user.service.message.output.CreateUserOutput;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateUserServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private CreateUserService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        UserDto user = new UserDto();
        user.setUsername("Username");
        user.setPassword("Password");
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.ADMIN);
        user.setEmail("email@email.com");

        UserDto createdUser = new UserDto();
        createdUser.setId(new String());
        createdUser.setPassword("Password");
        createdUser.setUsername("Username");
        createdUser.setStatus(Status.ACTIVE);
        createdUser.setRole(Role.ADMIN);
        createdUser.setEmail("email@email.com");

        Mockito.when(repository.save(Mockito.any(UserDto.class))).thenReturn(createdUser);
        final CreateUserInput input = new CreateUserInput(user);
        final ServiceTask<CreateUserInput> serviceTask = new ServiceTask<CreateUserInput>();
        serviceTask.setInput(input);
        final ServiceResult<CreateUserOutput> serviceResult = service.process(serviceTask);
        final CreateUserOutput output = serviceResult.getOutput();

        final UserDto returnedUser = output.getSavedUser();
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(returnedUser.getId(), createdUser.getId());
        Assert.assertNotEquals(user.getPassword(), returnedUser.getPassword());
        Assert.assertEquals(user.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(user.getRole(), returnedUser.getRole());
        Assert.assertEquals(user.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(user.getUsername(), returnedUser.getUsername());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(User.class));
    }


}
