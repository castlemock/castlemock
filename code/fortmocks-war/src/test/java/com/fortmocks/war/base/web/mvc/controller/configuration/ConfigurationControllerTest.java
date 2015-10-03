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

package com.fortmocks.war.base.web.mvc.controller.configuration;

import com.fortmocks.core.base.model.configuration.dto.ConfigurationGroupDto;
import com.fortmocks.war.base.config.Application;
import com.fortmocks.war.base.model.configuration.dto.ConfigurationGroupDtoGenerator;
import com.fortmocks.core.base.model.configuration.service.ConfigurationGroupService;
import com.fortmocks.war.base.web.mvc.controller.AbstractController;
import com.fortmocks.war.base.web.mvc.controller.AbstractControllerTest;
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

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ConfigurationControllerTest extends AbstractControllerTest {

    private static final String SERVICE_URL = "/web/configuration";
    private static final String PAGE = "partial/core/configuration/configuration.jsp";

    @InjectMocks
    private ConfigurationController configurationController;

    @Mock
    private ConfigurationGroupService configurationGroupService;

    @Override
    protected AbstractController getController() {
        return configurationController;
    }

    @Test
    public void testConfigurationValid() throws Exception {
        final List<ConfigurationGroupDto> configurationGroups = new ArrayList<ConfigurationGroupDto>();
        final ConfigurationGroupDto configurationGroupDto = ConfigurationGroupDtoGenerator.generateConfigurationGroup();
        configurationGroups.add(configurationGroupDto);

        Mockito.when(configurationGroupService.findAll()).thenReturn(configurationGroups);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE));
    }
}
