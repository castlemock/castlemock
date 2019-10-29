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
import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.model.user.domain.UserTestBuilder;
import com.castlemock.core.basis.service.user.input.ReadUserInput;
import com.castlemock.core.basis.service.user.output.ReadUserOutput;
import com.castlemock.web.basis.config.TestApplication;
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
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class DeleteUserControllerTest extends AbstractControllerTest {

    private static final String SERVICE_URL = "/web/user/";
    private static final String PAGE = "partial/basis/user/deleteUser.jsp";
    private static final String DELETE = "/delete";
    private static final String DELETE_CONFIRM = "/delete/confirm";

    @InjectMocks
    private DeleteUserController deleteUserController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return deleteUserController;
    }

    @Test
    public void testDeleteUserWithValidId() throws Exception {
        final User userDto = UserTestBuilder.builder().build();
        final ReadUserOutput readUserOutput = new ReadUserOutput(userDto);

        when(serviceProcessor.process(any(ReadUserInput.class))).thenReturn(readUserOutput);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + userDto.getId() + DELETE);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE));
    }


    @Test
    public void testDeleteConfirmUserWithValidId() throws Exception {
        final User userDto = UserTestBuilder.builder().build();
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + userDto.getId() + DELETE_CONFIRM);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0));
    }

}
