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
import com.castlemock.service.core.user.input.UpdateUserInput;
import com.castlemock.service.core.user.output.UpdateUserOutput;
import com.castlemock.repository.token.SessionTokenRepository;
import com.castlemock.repository.user.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateUserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private SessionTokenRepository sessionTokenRepository;

    @InjectMocks
    private UpdateUserService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final User user = UserTestBuilder.builder().build();
        final User updatedUser = UserTestBuilder.builder()
                .id(user.getId())
                .password("UpdatedPassword")
                .username("UpdatedUsername")
                .status(Status.ACTIVE)
                .role(Role.ADMIN)
                .email("email@email.com")
                .build();

        Mockito.when(repository.findOne(Mockito.anyString())).thenReturn(user);
        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
        final UpdateUserInput input = UpdateUserInput.builder()
                .userId(user.getId())
                .user(updatedUser)
                .build();
        final ServiceTask<UpdateUserInput> serviceTask = new ServiceTask<UpdateUserInput>();
        serviceTask.setInput(input);
        final ServiceResult<UpdateUserOutput> serviceResult = service.process(serviceTask);
        final UpdateUserOutput output = serviceResult.getOutput();

        final User returnedUser = output.getUpdatedUser();
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(updatedUser.getId(), returnedUser.getId());
        Assert.assertEquals(updatedUser.getPassword(), returnedUser.getPassword());
        Assert.assertEquals(updatedUser.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(updatedUser.getRole(), returnedUser.getRole());
        Assert.assertEquals(updatedUser.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(updatedUser.getUsername(), returnedUser.getUsername());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(User.class));
    }


}
