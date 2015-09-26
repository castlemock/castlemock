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

package com.fortmocks.war.mock.soap.web.mvc.controller.port;

import com.fortmocks.core.mock.soap.model.project.SoapOperationStatus;
import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.war.mock.soap.model.project.service.SoapProjectService;
import com.fortmocks.war.mock.soap.web.mvc.command.operation.SoapOperationModifierCommand;
import com.fortmocks.war.mock.soap.web.mvc.command.operation.UpdateSoapOperationsEndpointCommand;
import com.fortmocks.war.mock.soap.web.mvc.controller.AbstractSoapViewController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The SoapPortController provides functionality to retrieve a specific port for a project
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/soap/project")
public class SoapPortController extends AbstractSoapViewController {

    private static final String PAGE = "mock/soap/port/soapPort";
    private static final String UPDATE_STATUS = "update";
    private static final String SOAP_OPERATION_MODIFIER_COMMAND = "soapOperationModifierCommand";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String UPDATE_SOAP_OPERATIONS_ENDPOINT_COMMAND = "updateSoapOperationsEndpointCommand";
    private static final String UPDATE_SOAP_OPERATIONS_ENDPOINT_PAGE = "mock/soap/operation/updateSoapOperationsEndpoint";
    private static final Logger LOGGER = Logger.getLogger(SoapPortController.class);

    @Autowired
    private SoapProjectService soapProjectService;

    /**
     * Returns an port that matches the incoming parameters (soapProjectId and soapPortId)
     * @param soapPortId The id of the project which the port belongs to
     * @param soapPortId The id of the port that should be returned
     * @return Returns an port that matches the incoming parameters (projectId and soapPortId)
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}", method = RequestMethod.GET)
    public ModelAndView getSoapPort(@PathVariable final Long soapProjectId, @PathVariable final Long soapPortId) {
        final SoapPortDto soapPortDto = soapProjectService.findSoapPort(soapProjectId, soapPortId);

        final Map<SoapOperationStatus, Integer> statusCount = soapProjectService.getOperationStatusCount(soapPortDto);
        soapPortDto.setStatusCount(statusCount);
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(SOAP_PROJECT_ID,soapProjectId);
        model.addObject(SOAP_PORT,soapPortDto);
        model.addObject(SOAP_OPERATION_STATUSES, SoapOperationStatus.values());
        model.addObject(SOAP_OPERATION_MODIFIER_COMMAND, new SoapOperationModifierCommand());
        return model;
    }

    /**
     * The method portFunctionality provides functionality to update status for multiple
     * operations
     * @param soapProjectId The id of the project that the operations belongs to
     * @param soapPortId The id of the port that the operations belongs to
     * @param action The requested action
     * @param soapOperationModifierCommand The operation modifier command
     * @return Redirects the user to the port page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}", method = RequestMethod.POST)
    public ModelAndView portFunctionality(@PathVariable final Long soapProjectId, @PathVariable final Long soapPortId, @RequestParam final String action, @ModelAttribute final SoapOperationModifierCommand soapOperationModifierCommand) {
        LOGGER.debug("Requested SOAP port action: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final SoapOperationStatus soapOperationStatus = SoapOperationStatus.valueOf(soapOperationModifierCommand.getSoapOperationStatus());
            for(Long operationId : soapOperationModifierCommand.getSoapOperationIds()){
                soapProjectService.updateStatus(soapProjectId, soapPortId, operationId, soapOperationStatus);
            }
        } else if(UPDATE_ENDPOINTS.equalsIgnoreCase(action)){
            final List<SoapOperationDto> soapOperations = new ArrayList<SoapOperationDto>();
            for(Long operationId : soapOperationModifierCommand.getSoapOperationIds()){
                final SoapOperationDto soapOperationDto = soapProjectService.findSoapOperation(soapProjectId, soapPortId, operationId);
                soapOperations.add(soapOperationDto);
            }
            final ModelAndView model = createPartialModelAndView(UPDATE_SOAP_OPERATIONS_ENDPOINT_PAGE);
            model.addObject(SOAP_PROJECT_ID, soapProjectId);
            model.addObject(SOAP_PORT_ID, soapPortId);
            model.addObject(SOAP_OPERATIONS, soapOperations);
            model.addObject(UPDATE_SOAP_OPERATIONS_ENDPOINT_COMMAND, new UpdateSoapOperationsEndpointCommand());
            return model;
        }
        return redirect("/soap/project/" + soapProjectId + "/port/" + soapPortId);
    }

}