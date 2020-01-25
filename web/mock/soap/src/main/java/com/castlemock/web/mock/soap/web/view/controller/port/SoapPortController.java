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
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapOperationInput;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapPortInput;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapOperationsStatusInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapOperationOutput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapPortOutput;
import com.castlemock.web.mock.soap.web.view.command.operation.SoapOperationModifierCommand;
import com.castlemock.web.mock.soap.web.view.command.operation.UpdateSoapOperationsEndpointCommand;
import com.castlemock.web.mock.soap.web.view.controller.AbstractSoapViewController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

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
    private static final String SOAP_OPERATION_MODIFIER_COMMAND = "command";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String UPDATE_SOAP_OPERATIONS_ENDPOINT_COMMAND = "command";
    private static final String UPDATE_SOAP_OPERATIONS_ENDPOINT_PAGE = "mock/soap/operation/updateSoapOperationsEndpoint";
    private static final Logger LOGGER = LoggerFactory.getLogger(SoapPortController.class);

    /**
     * Returns an port that matches the incoming parameters (soapProjectId and soapPortId)
     * @param soapProjectId The id of the project which the port belongs to
     * @param soapPortId The id of the port that should be returned
     * @param request The incoming servlet request. Used to extract the address used to invoke the SOAP operation
     * @return Returns an port that matches the incoming parameters (projectId and soapPortId)
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}", method = RequestMethod.GET)
    public ModelAndView getSoapPort(@PathVariable final String soapProjectId,
                                    @PathVariable final String soapPortId,
                                    final ServletRequest request) {
        final ReadSoapPortOutput readSoapPortOutput = serviceProcessor.process(ReadSoapPortInput.builder()
                .projectId(soapProjectId)
                .portId(soapPortId)
                .build());
        final SoapPort soapPort = readSoapPortOutput.getPort();
        final String protocol = getProtocol(request);
        final String invokeAddress = getSoapInvokeAddress(protocol, request.getServerPort(), soapProjectId, soapPort.getUri());

        soapPort.setInvokeAddress(invokeAddress);
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(SOAP_PROJECT_ID,soapProjectId);
        model.addObject(SOAP_PORT,soapPort);
        model.addObject(SOAP_OPERATION_STATUSES, getSoapOperationStatuses());
        model.addObject(SOAP_OPERATION_MODIFIER_COMMAND, new SoapOperationModifierCommand());
        return model;
    }

    /**
     * The method portFunctionality provides functionality to update status for multiple
     * operations
     * @param soapProjectId The id of the project that the operations belongs to
     * @param soapPortId The id of the port that the operations belongs to
     * @param action The requested action
     * @param command The operation modifier command
     * @return Redirects the user to the port page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}", method = RequestMethod.POST)
    public ModelAndView portFunctionality(@PathVariable final String soapProjectId,
                                          @PathVariable final String soapPortId,
                                          @RequestParam final String action,
                                          @ModelAttribute(name = "command") final SoapOperationModifierCommand command) {
        LOGGER.debug("Requested SOAP port action: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final SoapOperationStatus soapOperationStatus = SoapOperationStatus.valueOf(command.getSoapOperationStatus());
            for(String operationId : command.getSoapOperationIds()){
                serviceProcessor.process(UpdateSoapOperationsStatusInput.builder()
                        .projectId(soapProjectId)
                        .portId(soapPortId)
                        .operationId(operationId)
                        .operationStatus(soapOperationStatus)
                        .build());
            }
        } else if(UPDATE_ENDPOINTS.equalsIgnoreCase(action)){
            final List<SoapOperation> soapOperations = new ArrayList<SoapOperation>();
            for(String soapOperationId : command.getSoapOperationIds()){
                final ReadSoapOperationOutput output = serviceProcessor.process(ReadSoapOperationInput.builder()
                        .projectId(soapProjectId)
                        .portId(soapPortId)
                        .operationId(soapOperationId)
                        .build());
                soapOperations.add(output.getOperation());
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