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

import com.castlemock.core.basis.model.configuration.domain.ConfigurationGroup;
import com.castlemock.core.basis.model.configuration.dto.ConfigurationGroupDto;
import com.castlemock.core.basis.model.configuration.service.message.input.UpdateAllConfigurationGroupsInput;
import com.castlemock.web.basis.web.mvc.command.configuration.ConfigurationUpdateCommand;
import com.castlemock.web.basis.web.mvc.controller.AbstractViewController;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The controller UpdateConfigurationController provides functionality to update configurations with new values that has
 * been set by the user.
 * @author Karl Dahlgren
 * @since 1.0
 * @see ConfigurationGroup
 * @see ConfigurationGroupDto
 */
@Controller
@Scope("request")
@RequestMapping("/web/configuration")
public class UpdateConfigurationController extends AbstractViewController {

    /**
     * The method updates all the configurations with new value that has been set by the user.
     * @param configurationUpdateCommand The configuration command contains all configurations and their new value (Or old if they haven't been changed)
     * @return Redirect the user to the root page
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateConfiguration(@ModelAttribute final ConfigurationUpdateCommand configurationUpdateCommand) {
        final UpdateAllConfigurationGroupsInput input = new UpdateAllConfigurationGroupsInput(configurationUpdateCommand.getConfigurationGroups());
        serviceProcessor.process(input);
        return redirect();
    }

}