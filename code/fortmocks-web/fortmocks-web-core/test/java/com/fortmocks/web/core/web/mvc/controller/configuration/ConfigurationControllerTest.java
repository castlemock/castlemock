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

package com.fortmocks.web.core.web.mvc.controller.configuration;

import com.fortmocks.core.model.configuration.dto.ConfigurationGroupDto;
import com.fortmocks.core.model.configuration.processor.message.input.ReadAllConfigurationGroupsInput;
import com.fortmocks.core.model.configuration.processor.message.output.ReadAllConfigurationGroupsOutput;
import com.fortmocks.web.core.config.TestApplication;
import com.fortmocks.web.core.model.configuration.dto.ConfigurationGroupDtoGenerator;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class ConfigurationControllerTest extends AbstractControllerTest {

    private static final String SERVICE_URL = "/web/configuration";
    private static final String PAGE = "partial/core/configuration/configuration.jsp";

    @InjectMocks
    private ConfigurationController configurationController;

    @Mock
    private ProcessorMainframe processorMainframe;

    @Override
    protected AbstractController getController() {
        return configurationController;
    }

    @Test
    public void testConfigurationValid() throws Exception {
        final List<ConfigurationGroupDto> configurationGroups = new ArrayList<ConfigurationGroupDto>();
        final ConfigurationGroupDto configurationGroupDto = ConfigurationGroupDtoGenerator.generateConfigurationGroup();
        configurationGroups.add(configurationGroupDto);

        final ReadAllConfigurationGroupsOutput readAllConfigurationGroupsOutput = new ReadAllConfigurationGroupsOutput();
        readAllConfigurationGroupsOutput.setConfigurationGroups(configurationGroups);

        Mockito.when(processorMainframe.process(any(ReadAllConfigurationGroupsInput.class))).thenReturn(readAllConfigurationGroupsOutput);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE));
    }
}
