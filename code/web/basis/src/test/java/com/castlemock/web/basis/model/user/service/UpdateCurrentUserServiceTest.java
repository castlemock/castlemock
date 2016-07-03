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
import com.castlemock.core.basis.model.user.dto.UserDto;
import com.castlemock.core.basis.model.user.service.message.input.UpdateCurrentUserInput;
import com.castlemock.core.basis.model.user.service.message.output.UpdateCurrentUserOutput;
import com.castlemock.web.basis.model.session.token.repository.SessionTokenRepository;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateCurrentUserServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @Mock
    private SessionTokenRepository sessionTokenRepository;

    @InjectMocks
    private UpdateCurrentUserService service;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        List<UserDto> users = new ArrayList<UserDto>();
        UserDto user = new UserDto();
        user.setId(new String());
        user.setPassword("Password");
        user.setUsername("Username");
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.ADMIN);
        user.setEmail("email@email.com");
        users.add(user);

        UserDto updatedUser = new UserDto();
        updatedUser.setId(new String());
        updatedUser.setPassword("UpdatedPassword");
        updatedUser.setUsername("UpdatedUsername");
        updatedUser.setStatus(Status.ACTIVE);
        updatedUser.setRole(Role.ADMIN);
        updatedUser.setEmail("email@email.com");

        Mockito.when(repository.findOne(Mockito.anyString())).thenReturn(user);
        Mockito.when(repository.findAll()).thenReturn(users);
        Mockito.when(repository.save(Mockito.any(UserDto.class))).thenReturn(user);
        final UpdateCurrentUserInput input = new UpdateCurrentUserInput(updatedUser);
        final ServiceTask<UpdateCurrentUserInput> serviceTask = new ServiceTask<UpdateCurrentUserInput>();
        serviceTask.setServiceConsumer("Username");
        serviceTask.setInput(input);
        final ServiceResult<UpdateCurrentUserOutput> serviceResult = service.process(serviceTask);
        final UpdateCurrentUserOutput output = serviceResult.getOutput();

        final String encodedPassword = PASSWORD_ENCODER.encode(user.getPassword());
        final UserDto returnedUser = output.getUpdatedUser();
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(updatedUser.getId(), returnedUser.getId());
        Assert.assertNotEquals(updatedUser.getPassword(), returnedUser.getPassword());
        Assert.assertNotEquals(encodedPassword, returnedUser.getPassword());
        Assert.assertEquals(updatedUser.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(updatedUser.getRole(), returnedUser.getRole());
        Assert.assertEquals(updatedUser.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(updatedUser.getUsername(), returnedUser.getUsername());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(UserDto.class));
    }


}
