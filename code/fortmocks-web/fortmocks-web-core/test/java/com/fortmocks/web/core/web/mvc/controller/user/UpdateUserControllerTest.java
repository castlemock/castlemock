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

package com.fortmocks.web.core.web.mvc.controller.user;

import com.fortmocks.core.model.user.Role;
import com.fortmocks.core.model.user.Status;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.message.*;
import com.fortmocks.web.core.config.TestApplication;
import com.fortmocks.web.core.model.user.dto.UserDtoGenerator;
import com.fortmocks.web.core.model.user.service.UserDetailSecurityService;
import com.fortmocks.web.core.processor.ProcessorMainframe;
import com.fortmocks.web.core.web.mvc.controller.AbstractController;
import com.fortmocks.web.core.web.mvc.controller.AbstractControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class UpdateUserControllerTest extends AbstractControllerTest {

    private static final String SERVICE_URL = "/web/user/";
    private static final String PAGE = "partial/core/user/updateUser.jsp";
    private static final String UPDATE = "/update";
    private static final String ROLES = "roles";
    protected static final String USER_STATUSES = "userStatuses";
    private static final String COMMAND = "command";

    @InjectMocks
    private UpdateUserController updateUserController;

    @Mock
    private ProcessorMainframe processorMainframe;

    @Mock
    private UserDetailSecurityService userDetailSecurityService;

    @Override
    protected AbstractController getController() {
        return updateUserController;
    }

    @Test
    public void testUpdateUserWithValidId() throws Exception {
        final FindUserOutput findUserOutput = new FindUserOutput();
        final UserDto userDto = UserDtoGenerator.generateUserDto();
        findUserOutput.setUser(userDto);
        when(processorMainframe.process(any(FindUserInput.class))).thenReturn(findUserOutput);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + userDto.getId() + UPDATE);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(6))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(ROLES, Role.values()))
                .andExpect(MockMvcResultMatchers.model().attribute(USER_STATUSES, Status.values()))
                .andExpect(MockMvcResultMatchers.model().attribute(COMMAND, userDto));
    }

    @Test
    public void testUpdateUserConfirmWithValidId() throws Exception {
        final FindUserByUsernameOutput findUserByUsernameOutput = new FindUserByUsernameOutput();
        final UserDto userDto = UserDtoGenerator.generateUserDto();
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + userDto.getId() + UPDATE);
        findUserByUsernameOutput.setUser(userDto);

        when(processorMainframe.process(any(FindUserByUsernameInput.class))).thenReturn(findUserByUsernameOutput);
        when(processorMainframe.process(any(UpdateUserInput.class))).thenReturn(findUserByUsernameOutput);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }
}
