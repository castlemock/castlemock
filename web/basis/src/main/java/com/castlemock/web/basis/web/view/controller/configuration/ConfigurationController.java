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

import com.castlemock.core.basis.model.configuration.domain.ConfigurationGroup;
import com.castlemock.core.basis.service.configuration.input.ReadAllConfigurationGroupsInput;
import com.castlemock.core.basis.service.configuration.output.ReadAllConfigurationGroupsOutput;
import com.castlemock.web.basis.web.view.command.configuration.ConfigurationUpdateCommand;
import com.castlemock.web.basis.web.view.controller.AbstractViewController;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * The controller ConfigurationController provides the functionality to retrieve all configurations and
 * display them to the user
 * @author Karl Dahlgren
 * @since 1.0
 * @see ConfigurationGroup
 * @see ConfigurationGroup
 */
@Controller
@Scope("request")
@RequestMapping("/web/configuration")
public class ConfigurationController extends AbstractViewController {

    private static final String PAGE = "basis/configuration/configuration";
    private static final String CONFIGURATION_UPDATE_COMMAND = "configurationUpdateCommand";

    /**
     * Retrieves all configurations and configurations groups and display them to the user
     * @return A view that displays all the configurations groups and their corresponding configurations
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        final ReadAllConfigurationGroupsOutput output = serviceProcessor.process(new ReadAllConfigurationGroupsInput());
        final List<ConfigurationGroup> configurationGroupDtos = output.getConfigurationGroups();
        final ConfigurationUpdateCommand configurationUpdateCommand = new ConfigurationUpdateCommand();
        configurationUpdateCommand.setConfigurationGroups(configurationGroupDtos);
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(CONFIGURATION_UPDATE_COMMAND, configurationUpdateCommand);
        return model;
    }

}