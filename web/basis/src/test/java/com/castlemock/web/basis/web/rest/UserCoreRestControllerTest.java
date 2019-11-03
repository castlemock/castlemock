/*
 * Copyright 2019 Karl Dahlgren
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

package com.castlemock.web.basis.web.rest;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.model.user.domain.UserTestBuilder;
import com.castlemock.core.basis.service.user.input.DeleteUserInput;
import com.castlemock.core.basis.service.user.input.ReadAllUsersInput;
import com.castlemock.core.basis.service.user.input.ReadUserInput;
import com.castlemock.core.basis.service.user.output.DeleteUserOutput;
import com.castlemock.core.basis.service.user.output.ReadAllUsersOutput;
import com.castlemock.core.basis.service.user.output.ReadUserOutput;
import com.castlemock.web.basis.config.TestApplication;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.basis.web.AbstractControllerTest;
import com.castlemock.web.basis.web.rest.controller.UserCoreRestController;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class UserCoreRestControllerTest extends AbstractControllerTest {

    @InjectMocks
    private UserCoreRestController userCoreRestController;

    @Mock
    private ServiceProcessor serviceProcessor;

    private static final String BASE_SERVICE_URL = "/api/rest/core";

    @Test
    public void getUser() throws Exception {
        final User user = UserTestBuilder.builder().build();
        final String serviceUrl = BASE_SERVICE_URL + "/user/1";

        when(serviceProcessor.process(any(ReadUserInput.class))).thenReturn(new ReadUserOutput(user));

        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(serviceUrl);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(any(ReadUserInput.class));
    }

    @Test
    public void getUsers() throws Exception {
        final User user = UserTestBuilder.builder().build();
        final String serviceUrl = BASE_SERVICE_URL + "/user";

        when(serviceProcessor.process(any(ReadAllUsersInput.class)))
                .thenReturn(new ReadAllUsersOutput(ImmutableList.of(user)));

        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(serviceUrl);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(any(ReadAllUsersInput.class));
    }

    @Test
    public void deleteUser() throws Exception {
        final String serviceUrl = BASE_SERVICE_URL + "/user/1";

        when(serviceProcessor.process(any(DeleteUserInput.class))).thenReturn(new DeleteUserOutput());

        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.delete(serviceUrl);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(any(DeleteUserInput.class));
    }

    @Override
    protected AbstractController getController() {
        return userCoreRestController;
    }
}
