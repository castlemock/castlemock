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

import com.castlemock.model.core.model.ServiceResult;
import com.castlemock.model.core.model.ServiceTask;
import com.castlemock.model.core.model.user.domain.User;
import com.castlemock.model.core.model.user.domain.UserTestBuilder;
import com.castlemock.service.core.user.input.ReadUserInput;
import com.castlemock.service.core.user.output.ReadUserOutput;
import com.castlemock.repository.Repository;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadUserServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository<User, String> repository;

    @InjectMocks
    private ReadUserService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final User user = UserTestBuilder.builder().build();
        Mockito.when(repository.findOne(Mockito.anyString())).thenReturn(user);
        final ReadUserInput input = ReadUserInput.builder().userId(user.getId()).build();
        final ServiceTask<ReadUserInput> serviceTask = new ServiceTask<ReadUserInput>();
        serviceTask.setInput(input);
        final ServiceResult<ReadUserOutput> serviceResult = service.process(serviceTask);
        final ReadUserOutput output = serviceResult.getOutput();

        final User returnedUser = output.getUser();
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(user.getId(), returnedUser.getId());
        Assert.assertEquals(user.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(user.getRole(), returnedUser.getRole());
        Assert.assertEquals(user.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(user.getUsername(), returnedUser.getUsername());
    }


}
