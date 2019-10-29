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

package com.castlemock.web.mock.soap.web.view.controller.port;

import com.castlemock.core.mock.soap.model.project.domain.SoapOperationStatus;
import com.castlemock.core.mock.soap.model.project.domain.SoapResponseStrategy;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapPortInput;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapPortInput;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapPortsForwardedEndpointInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapPortOutput;
import com.castlemock.web.mock.soap.web.view.command.port.UpdateSoapPortsEndpointCommand;
import com.castlemock.web.mock.soap.web.view.controller.AbstractSoapViewController;
import com.google.common.base.Preconditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
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
@ConditionalOnExpression("${server.mode.demo} == false")
public class UpdateSoapPortController extends AbstractSoapViewController {



    private static final String PAGE = "mock/soap/port/updateSoapPort";

    /**
     * The method retrieves a specific port and returns a view that displays the retrieved operation.
     * @param soapProjectId The id of the project that the port belongs to
     * @param soapPortId The id of the port that will be retrieved
     * @return A view that displays the retrieved port
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String soapProjectId, @PathVariable final String soapPortId) {
        final ReadSoapPortOutput output = serviceProcessor.process(ReadSoapPortInput.builder()
                .projectId(soapProjectId)
                .portId(soapPortId)
                .build());
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(SOAP_PORT, output.getPort());
        model.addObject(SOAP_PROJECT_ID, soapProjectId);
        model.addObject(SOAP_PORT_ID, soapPortId);
        model.addObject(SOAP_MOCK_RESPONSE_STRATEGIES, SoapResponseStrategy.values());
        model.addObject(SOAP_OPERATION_STATUSES, SoapOperationStatus.values());
        return model;
    }

    /**
     * The update method is responsible for updating a specific port
     * @param soapProjectId The id of the project that the port belongs to
     * @param soapPortId The id of the port that will be updated
     * @param soapPort The updated version of the port that will be updated
     * @return Redirects the user to the main page of the port that was updated
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable final String soapProjectId,
                               @PathVariable final String soapPortId,
                               @ModelAttribute(name = "soapPort") final SoapPort soapPort) {
        serviceProcessor.process(UpdateSoapPortInput.builder()
                .projectId(soapProjectId)
                .portId(soapPortId)
                .port(soapPort)
                .build());
        return redirect("/soap/project/" + soapProjectId + "/port/" + soapPortId);
    }

    /**
     * The method provides the functionality to update the endpoint address for multiple
     * ports at once
     * @param projectId The id of the project that the ports belongs to
     * @param command The command object contains both the port that
     *                                          will be updated and the new forwarded address
     * @return Redirects the user to the project page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/port/update/confirm", method = RequestMethod.POST)
    public ModelAndView updateEndpoint(@PathVariable final String projectId,
                                       @ModelAttribute(name = "command") final UpdateSoapPortsEndpointCommand command) {
        Preconditions.checkNotNull(command, "The update port endpoint command cannot be null");
        serviceProcessor.process(UpdateSoapPortsForwardedEndpointInput.builder().projectId(projectId)
                .ports(command.getSoapPorts())
                .forwardedEndpoint(command.getForwardedEndpoint())
                .build());
        return redirect("/soap/project/" + projectId);
    }

}