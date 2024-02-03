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

import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.core.user.Role;
import com.castlemock.model.core.user.User;
import com.castlemock.model.core.user.UserTestBuilder;
import com.castlemock.repository.token.SessionTokenRepository;
import com.castlemock.repository.user.UserRepository;
import com.castlemock.service.core.user.input.DeleteUserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteUserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private SessionTokenRepository sessionTokenRepository;

    @InjectMocks
    private DeleteUserService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final User user = UserTestBuilder.builder()
                .role(Role.MODIFIER)
                .build();

        Mockito.when(repository.findOne(Mockito.anyString())).thenReturn(Optional.of(user));

        final DeleteUserInput input = DeleteUserInput.builder()
                .userId("")
                .build();
        final ServiceTask<DeleteUserInput> serviceTask = ServiceTask.of(input, "user");
        service.process(serviceTask);
        Mockito.verify(repository, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    public void testProcessDeleteLastAdmin(){
        final User user = UserTestBuilder
                .builder()
                .role(Role.ADMIN)
                .build();

        final List<User> users = new ArrayList<>();
        users.add(user);

        Mockito.when(repository.findOne(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(repository.findAll()).thenReturn(users);

        final DeleteUserInput input = DeleteUserInput.builder()
                .userId("")
                .build();
        final ServiceTask<DeleteUserInput> serviceTask = ServiceTask.of(input, "user");
        assertThrows(IllegalArgumentException.class, () -> service.process(serviceTask));
        Mockito.verify(repository, Mockito.times(1)).delete(Mockito.anyString());
    }


}
