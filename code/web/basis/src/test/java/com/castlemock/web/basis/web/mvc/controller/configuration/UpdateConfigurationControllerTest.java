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

package com.castlemock.web.basis.web.mvc.controller.configuration;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.configuration.dto.ConfigurationGroupDto;
import com.castlemock.core.basis.model.configuration.service.message.input.UpdateAllConfigurationGroupsInput;
import com.castlemock.core.basis.model.configuration.service.message.output.ReadAllConfigurationGroupsOutput;
import com.castlemock.web.basis.config.TestApplication;
import com.castlemock.web.basis.web.mvc.command.configuration.ConfigurationUpdateCommand;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.basis.web.mvc.controller.AbstractControllerTest;
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

import java.util.ArrayList;

import static org.mockito.Matchers.any;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class UpdateConfigurationControllerTest extends AbstractControllerTest {

    private static final String SERVICE_URL = "/web/configuration/update";

    @InjectMocks
    private UpdateConfigurationController updateConfigurationController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return updateConfigurationController;
    }

    @Test
    public void testCUpdate() throws Exception {
        final ConfigurationUpdateCommand configurationUpdateCommand = new ConfigurationUpdateCommand();
        configurationUpdateCommand.setConfigurationGroups(new ArrayList<ConfigurationGroupDto>());

        Mockito.when(serviceProcessor.process(any(UpdateAllConfigurationGroupsInput.class))).thenReturn(new ReadAllConfigurationGroupsOutput());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL, configurationUpdateCommand);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }
}
