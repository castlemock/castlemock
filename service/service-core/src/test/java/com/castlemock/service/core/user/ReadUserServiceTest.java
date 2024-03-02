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
import com.castlemock.model.core.user.User;
import com.castlemock.model.core.user.UserTestBuilder;
import com.castlemock.repository.Repository;
import com.castlemock.service.core.user.input.ReadUserInput;
import com.castlemock.service.core.user.output.ReadUserOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadUserServiceTest {

    @Mock
    private Repository<User, String> repository;

    @InjectMocks
    private ReadUserService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final User user = UserTestBuilder.builder().build();
        Mockito.when(repository.findOne(Mockito.anyString())).thenReturn(Optional.of(user));
        final ReadUserInput input = ReadUserInput.builder().userId(user.getId()).build();
        final ServiceTask<ReadUserInput> serviceTask = ServiceTask.of(input, "user");
        final ServiceResult<ReadUserOutput> serviceResult = service.process(serviceTask);
        final ReadUserOutput output = serviceResult.getOutput();

        final User returnedUser = output.getUser()
                .orElse(null);
        Assertions.assertNotNull(returnedUser);
        Assertions.assertEquals(user.getId(), returnedUser.getId());
        Assertions.assertEquals(user.getEmail(), returnedUser.getEmail());
        Assertions.assertEquals(user.getRole(), returnedUser.getRole());
        Assertions.assertEquals(user.getStatus(), returnedUser.getStatus());
        Assertions.assertEquals(user.getUsername(), returnedUser.getUsername());
    }


}
