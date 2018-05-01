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

package com.castlemock.web.basis.web.mvc.controller.user;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.model.user.service.message.input.CreateUserInput;
import com.castlemock.core.basis.model.user.service.message.output.CreateUserOutput;
import com.castlemock.web.basis.config.TestApplication;
import com.castlemock.web.basis.model.user.dto.UserDtoGenerator;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.basis.web.mvc.controller.AbstractControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class CreateUserControllerTest extends AbstractControllerTest {


    private static final String SERVICE_URL = "/web/user/create";
    private static final String USER = "user";

    @InjectMocks
    private CreateUserController createUserController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return createUserController;
    }

    @Test
    public void testCreateUser() throws Exception {
        final CreateUserOutput createUserOutput = new CreateUserOutput();
        final User userDto = UserDtoGenerator.generateUserDto();
        createUserOutput.setSavedUser(userDto);
        when(serviceProcessor.process(any(CreateUserInput.class))).thenReturn(createUserOutput);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }



}
