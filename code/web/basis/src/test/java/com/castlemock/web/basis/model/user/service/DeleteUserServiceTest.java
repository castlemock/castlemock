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
import com.castlemock.core.basis.model.user.dto.UserDto;
import com.castlemock.core.basis.model.user.service.message.input.DeleteUserInput;
import com.castlemock.core.basis.model.user.service.message.output.DeleteUserOutput;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteUserServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private DeleteUserService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        UserDto user = new UserDto();
        user.setRole(Role.MODIFIER);

        Mockito.when(repository.findOne(Mockito.anyString())).thenReturn(user);

        final DeleteUserInput input = new DeleteUserInput(new String());
        final ServiceTask<DeleteUserInput> serviceTask = new ServiceTask<DeleteUserInput>();
        serviceTask.setInput(input);
        final ServiceResult<DeleteUserOutput> serviceResult = service.process(serviceTask);
        serviceResult.getOutput();
        Mockito.verify(repository, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessDeleteLastAdmin(){
        UserDto user = new UserDto();
        user.setRole(Role.ADMIN);

        List<UserDto> users = new ArrayList<>();
        users.add(user);

        Mockito.when(repository.findOne(Mockito.anyString())).thenReturn(user);
        Mockito.when(repository.findAll()).thenReturn(users);

        final DeleteUserInput input = new DeleteUserInput(new String());
        final ServiceTask<DeleteUserInput> serviceTask = new ServiceTask<DeleteUserInput>();
        serviceTask.setInput(input);
        final ServiceResult<DeleteUserOutput> serviceResult = service.process(serviceTask);
        serviceResult.getOutput();
        Mockito.verify(repository, Mockito.times(1)).delete(Mockito.anyString());
    }


}
