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

package com.castlemock.web.basis.web.view.controller.configuration;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.configuration.domain.ConfigurationGroup;
import com.castlemock.core.basis.model.configuration.domain.ConfigurationGroupTestBuilder;
import com.castlemock.core.basis.service.configuration.input.ReadAllConfigurationGroupsInput;
import com.castlemock.core.basis.service.configuration.output.ReadAllConfigurationGroupsOutput;
import com.castlemock.web.basis.config.TestApplication;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.basis.web.AbstractControllerTest;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class ConfigurationControllerTest extends AbstractControllerTest {

    private static final String SERVICE_URL = "/web/configuration";
    private static final String PAGE = "partial/basis/configuration/configuration.jsp";

    @InjectMocks
    private ConfigurationController configurationController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return configurationController;
    }

    @Test
    public void testConfigurationValid() throws Exception {
        final List<ConfigurationGroup> configurationGroups = new ArrayList<ConfigurationGroup>();
        final ConfigurationGroup configurationGroupDto = ConfigurationGroupTestBuilder.builder().build();
        configurationGroups.add(configurationGroupDto);

        final ReadAllConfigurationGroupsOutput readAllConfigurationGroupsOutput = new ReadAllConfigurationGroupsOutput(configurationGroups);

        Mockito.when(serviceProcessor.process(any(ReadAllConfigurationGroupsInput.class))).thenReturn(readAllConfigurationGroupsOutput);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().size(1 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE));
    }
}
