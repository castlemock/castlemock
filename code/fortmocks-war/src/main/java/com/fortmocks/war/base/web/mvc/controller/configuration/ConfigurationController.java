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
import com.fortmocks.core.base.model.configuration.service.ConfigurationGroupService;
import com.fortmocks.war.base.web.mvc.command.configuration.ConfigurationUpdateCommand;
import com.fortmocks.war.base.web.mvc.controller.AbstractViewController;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @see com.fortmocks.core.base.model.configuration.ConfigurationGroup
 * @see com.fortmocks.core.base.model.configuration.dto.ConfigurationGroupDto
 */
@Controller
@Scope("request")
@RequestMapping("/web/configuration")
public class ConfigurationController extends AbstractViewController {

    private static final String PAGE = "core/configuration/configuration";
    private static final String CONFIGURATION_UPDATE_COMMAND = "configurationUpdateCommand";

    @Autowired
    private ConfigurationGroupService configurationGroupService;

    /**
     * Retrieves all configurations and configurations groups and display them to the user
     * @return A view that displays all the configurations groups and their corresponding configurations
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        final List<ConfigurationGroupDto> configurationGroupDtos = configurationGroupService.findAll();
        final ConfigurationUpdateCommand configurationUpdateCommand = new ConfigurationUpdateCommand();
        configurationUpdateCommand.setConfigurationGroups(configurationGroupDtos);
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(CONFIGURATION_UPDATE_COMMAND, configurationUpdateCommand);
        return model;
    }

}