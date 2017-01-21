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

package com.castlemock.web.mock.soap.web.mvc.controller.project;

import com.castlemock.core.mock.soap.model.project.domain.SoapOperationStatus;
import com.castlemock.core.mock.soap.model.project.dto.SoapPortDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapProjectDto;
import com.castlemock.core.mock.soap.model.project.service.message.input.ReadSoapPortInput;
import com.castlemock.core.mock.soap.model.project.service.message.input.ReadSoapProjectInput;
import com.castlemock.core.mock.soap.model.project.service.message.input.UpdateSoapPortsStatusInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.ReadSoapPortOutput;
import com.castlemock.core.mock.soap.model.project.service.message.output.ReadSoapProjectOutput;
import com.castlemock.web.mock.soap.web.mvc.command.port.DeleteSoapPortsCommand;
import com.castlemock.web.mock.soap.web.mvc.command.port.SoapPortModifierCommand;
import com.castlemock.web.mock.soap.web.mvc.command.port.UpdateSoapPortsEndpointCommand;
import com.castlemock.web.mock.soap.web.mvc.controller.AbstractSoapViewController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * The project controller provides functionality to retrieve a specific project
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/web/soap/project")
public class SoapProjectController extends AbstractSoapViewController {

    private static final String PAGE = "mock/soap/project/soapProject";
    private static final String DELETE_SOAP_PORTS_PAGE = "mock/soap/port/deleteSoapPorts";
    private static final String UPDATE_SOAP_PORTS_ENDPOINT_PAGE = "mock/soap/port/updateSoapPortsEndpoint";
    private static final String UPDATE_STATUS = "update";
    private static final String DELETE_SOAP_PORTS = "delete";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String DELETE_SOAP_PORTS_COMMAND = "deleteSoapPortsCommand";
    private static final String SOAP_PORT_MODIFIER_COMMAND = "soapPortModifierCommand";
    private static final String UPDATE_SOAP_PORTS_ENDPOINT_COMMAND = "updateSoapPortsEndpointCommand";
    private static final Logger LOGGER = Logger.getLogger(SoapProjectController.class);

    /**
     * Retrieves a specific project with a project id
     * @param projectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ModelAndView getProject(@PathVariable final String projectId) {
        final ReadSoapProjectOutput readSoapProjectOutput = serviceProcessor.process(new ReadSoapProjectInput(projectId));
        final SoapProjectDto project = readSoapProjectOutput.getSoapProject();
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(SOAP_PROJECT, project);
        model.addObject(SOAP_OPERATION_STATUSES, getSoapOperationStatuses());
        model.addObject(SOAP_PORT_MODIFIER_COMMAND, new SoapPortModifierCommand());
        return model;
    }

    /**
     * The method projectFunctionality provides multiple functionalities: Update ports' status
     * and delete ports. Both of the functionalities involves a set of ports that belongs
     * to a specific project
     * @param projectId The id of the project that the ports belong to
     * @param action The name of the action that should be executed (delete or update).
     * @param soapPortModifierCommand The command object that contains the list of ports that get affected by the executed action.
     * @return Redirects the user back to the main page for the project with the provided id.
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.POST)
    public ModelAndView projectFunctionality(@PathVariable final String projectId, @RequestParam final String action, @ModelAttribute final SoapPortModifierCommand soapPortModifierCommand) {
        LOGGER.debug("Requested SOAP project action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final SoapOperationStatus soapOperationStatus = SoapOperationStatus.valueOf(soapPortModifierCommand.getSoapPortStatus());
            for(String soapPortId : soapPortModifierCommand.getSoapPortIds()){
                serviceProcessor.process(new UpdateSoapPortsStatusInput(projectId, soapPortId, soapOperationStatus));
            }
        } else if(DELETE_SOAP_PORTS.equalsIgnoreCase(action)) {
            final List<SoapPortDto> soapPortDtos = new ArrayList<SoapPortDto>();
            for(String soapPortId : soapPortModifierCommand.getSoapPortIds()){
                final ReadSoapPortOutput output = serviceProcessor.process(new ReadSoapPortInput(projectId, soapPortId));
                soapPortDtos.add(output.getSoapPort());
            }
            final ModelAndView model = createPartialModelAndView(DELETE_SOAP_PORTS_PAGE);
            model.addObject(SOAP_PROJECT_ID, projectId);
            model.addObject(SOAP_PORTS, soapPortDtos);
            model.addObject(DELETE_SOAP_PORTS_COMMAND, new DeleteSoapPortsCommand());
            return model;
        } else if(UPDATE_ENDPOINTS.equalsIgnoreCase(action)){
            final List<SoapPortDto> soapPortDtos = new ArrayList<SoapPortDto>();
            for(String soapPortId : soapPortModifierCommand.getSoapPortIds()){
                final ReadSoapPortOutput output = serviceProcessor.process(new ReadSoapPortInput(projectId, soapPortId));
                soapPortDtos.add(output.getSoapPort());
            }
            final ModelAndView model = createPartialModelAndView(UPDATE_SOAP_PORTS_ENDPOINT_PAGE);
            model.addObject(SOAP_PROJECT_ID, projectId);
            model.addObject(SOAP_PORTS, soapPortDtos);
            model.addObject(UPDATE_SOAP_PORTS_ENDPOINT_COMMAND, new UpdateSoapPortsEndpointCommand());
            return model;
        }

        return redirect("/soap/project/" + projectId);
    }


}

