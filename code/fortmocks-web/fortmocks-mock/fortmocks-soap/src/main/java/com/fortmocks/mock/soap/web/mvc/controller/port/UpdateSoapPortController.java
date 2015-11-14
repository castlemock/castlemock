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

package com.fortmocks.mock.soap.web.mvc.controller.port;

import com.fortmocks.mock.soap.model.project.processor.message.input.UpdateSoapPortsForwardedEndpointInput;
import com.fortmocks.mock.soap.web.mvc.command.port.UpdateSoapPortsEndpointCommand;
import com.fortmocks.mock.soap.web.mvc.controller.AbstractSoapViewController;
import com.google.common.base.Preconditions;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The controller UpdateSoapPortController provides the functionality to update a specific
 * port with the information
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/soap/project")
public class UpdateSoapPortController extends AbstractSoapViewController {

    /**
     * The method provides the functionality to update the endpoint address for multiple
     * ports at once
     * @param projectId The id of the project that the ports belongs to
     * @param updateSoapPortsEndpointCommand The command object contains both the port that
     *                                          will be updated and the new forwarded address
     * @return Redirects the user to the project page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/port/update/confirm", method = RequestMethod.POST)
    public ModelAndView updateEndpoint(@PathVariable final Long projectId, @ModelAttribute final UpdateSoapPortsEndpointCommand updateSoapPortsEndpointCommand) {
        Preconditions.checkNotNull(updateSoapPortsEndpointCommand, "The update port endpoint command cannot be null");
        processorMainframe.process(new UpdateSoapPortsForwardedEndpointInput(projectId, updateSoapPortsEndpointCommand.getSoapPorts(), updateSoapPortsEndpointCommand.getForwardedEndpoint()));
        return redirect("/soap/project/" + projectId);
    }

}