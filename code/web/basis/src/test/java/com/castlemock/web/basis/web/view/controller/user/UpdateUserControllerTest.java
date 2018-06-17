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

package com.castlemock.web.basis.web.view.controller.user;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.user.domain.Role;
import com.castlemock.core.basis.model.user.domain.Status;
import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.service.user.input.ReadUserByUsernameInput;
import com.castlemock.core.basis.service.user.input.ReadUserInput;
import com.castlemock.core.basis.service.user.input.UpdateUserInput;
import com.castlemock.core.basis.service.user.output.ReadUserByUsernameOutput;
import com.castlemock.core.basis.service.user.output.ReadUserOutput;
import com.castlemock.core.basis.service.user.output.UpdateUserOutput;
import com.castlemock.web.basis.config.TestApplication;
import com.castlemock.web.basis.model.user.dto.UserDtoGenerator;
import com.castlemock.web.basis.service.user.UserDetailSecurityService;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.basis.web.AbstractControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class UpdateUserControllerTest extends AbstractControllerTest {

    private static final String SERVICE_URL = "/web/user/";
    private static final String PAGE = "partial/basis/user/updateUser.jsp";
    private static final String UPDATE = "/update";
    private static final String ROLES = "roles";
    protected static final String USER_STATUSES = "userStatuses";
    private static final String COMMAND = "command";

    @InjectMocks
    private UpdateUserController updateUserController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Mock
    private UserDetailSecurityService userDetailSecurityService;

    @Override
    protected AbstractController getController() {
        return updateUserController;
    }

    @Test
    public void testUpdateUserWithValidId() throws Exception {
        final User userDto = UserDtoGenerator.generateUserDto();
        final ReadUserOutput readUserOutput = new ReadUserOutput(userDto);

        when(serviceProcessor.process(any(ReadUserInput.class))).thenReturn(readUserOutput);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + userDto.getId() + UPDATE);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(ROLES, Role.values()))
                .andExpect(MockMvcResultMatchers.model().attribute(USER_STATUSES, Status.values()))
                .andExpect(MockMvcResultMatchers.model().attribute(COMMAND, userDto));
    }

    @Test
    public void testUpdateUserConfirmWithValidId() throws Exception {
        final User userDto = UserDtoGenerator.generateUserDto();
        final ReadUserByUsernameOutput readUserByUsernameOutput = new ReadUserByUsernameOutput(userDto);
        final UpdateUserOutput updateUserOutput = new UpdateUserOutput(userDto);
        when(serviceProcessor.process(isA(ReadUserByUsernameInput.class))).thenReturn(readUserByUsernameOutput);
        when(serviceProcessor.process(isA(UpdateUserInput.class))).thenReturn(updateUserOutput);

        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + userDto.getId() + UPDATE);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }
}
