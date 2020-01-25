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

package com.castlemock.web.mock.soap.web.view.controller.operation;

import com.castlemock.core.mock.soap.service.event.input.ReadSoapEventsByOperationIdInput;
import com.castlemock.core.mock.soap.service.event.output.ReadSoapEventsByOperationIdOutput;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponseStatus;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.service.project.input.*;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapMockResponseOutput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapOperationOutput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapPortOutput;
import com.castlemock.web.mock.soap.web.view.command.mockresponse.DeleteSoapMockResponsesCommand;
import com.castlemock.web.mock.soap.web.view.command.mockresponse.SoapMockResponseModifierCommand;
import com.castlemock.web.mock.soap.web.view.controller.AbstractSoapViewController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * The ServiceController provides functionality to retrieve a specific operation
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/soap/project")
public class SoapOperationController extends AbstractSoapViewController {

    private static final String PAGE = "mock/soap/operation/soapOperation";
    private static final String DELETE_SOAP_MOCK_RESPONSES_COMMAND = "command";
    private static final String SOAP_MOCK_RESPONSE_MODIFIER_COMMAND = "command";
    private static final String DELETE_MOCK_RESPONSES_PAGE = "mock/soap/mockresponse/deleteSoapMockResponses";
    private static final String UPDATE_STATUS = "update";
    private static final String DELETE_MOCK_RESPONSES = "delete";
    private static final String DUPLICATE_MOCK_RESPONSE = "duplicate";
    private static final Logger LOGGER = LoggerFactory.getLogger(SoapOperationController.class);

    /**
     * The method provides functionality to retrieve a specific operation
     * @param soapProjectId The id of the project that the operation belongs to
     * @param soapPortId The id of the port that the operation belongs to
     * @param soapOperationId The id of the operation that will be retrieved
     * @param request The request is used to retrieve with port was used to communicate with the project
     * @return A view that displays the retrieved operation
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/operation/{soapOperationId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String soapProjectId,
                                    @PathVariable final String soapPortId,
                                    @PathVariable final String soapOperationId,
                                    final ServletRequest request) {
        final ReadSoapPortOutput readSoapPortOutput = serviceProcessor.process(ReadSoapPortInput.builder()
                .projectId(soapProjectId)
                .portId(soapPortId)
                .build());
        final SoapPort soapPort = readSoapPortOutput.getPort();

        final ReadSoapOperationOutput readSoapOperationOutput = serviceProcessor.process(ReadSoapOperationInput.builder()
                .projectId(soapProjectId)
                .portId(soapPortId)
                .operationId(soapOperationId)
                .build());
        final SoapOperation soapOperation = readSoapOperationOutput.getOperation();
        final ReadSoapEventsByOperationIdOutput readSoapEventsByOperationIdOutput = serviceProcessor.process(ReadSoapEventsByOperationIdInput.builder()
                .operationId(soapOperationId)
                .build());

        final String protocol = getProtocol(request);
        final String invokeAddress = getSoapInvokeAddress(protocol, request.getServerPort(), soapProjectId, soapPort.getUri());
        soapOperation.setInvokeAddress(invokeAddress);

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(SOAP_OPERATION, soapOperation);
        model.addObject(SOAP_PROJECT_ID, soapProjectId);
        model.addObject(SOAP_PORT_ID, soapPortId);
        model.addObject(SOAP_MOCK_RESPONSE_STATUSES, SoapMockResponseStatus.values());
        model.addObject(SOAP_EVENTS, readSoapEventsByOperationIdOutput.getSoapEvents());
        model.addObject(SOAP_MOCK_RESPONSE_MODIFIER_COMMAND, new SoapMockResponseModifierCommand());
        return model;
    }

    /**
     *
     * @param soapProjectId The id of the project that the mocked response(s) belongs to
     * @param soapPortId The id of the port that the mocked response(s) belongs to
     * @param soapOperationId The id of the operation that the mocked response(s) belongs to
     * @param action The name of the action that should be executed (delete or update).
     * @param command The command object that contains the list of mocked responses that get affected by the executed action.
     * @return Redirects the user back to the operation page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/operation/{soapOperationId}", method = RequestMethod.POST)
    public ModelAndView serviceFunctionality(@PathVariable final String soapProjectId,
                                             @PathVariable final String soapPortId,
                                             @PathVariable final String soapOperationId,
                                             @RequestParam final String action,
                                             @ModelAttribute(name = "command") final SoapMockResponseModifierCommand command) {
        LOGGER.debug("SOAP operation action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final SoapMockResponseStatus status = SoapMockResponseStatus.valueOf(command.getSoapMockResponseStatus());
            for(String mockResponseId : command.getSoapMockResponseIds()){
                ReadSoapMockResponseOutput readSoapMockResponseOutput = serviceProcessor.process(ReadSoapMockResponseInput.builder()
                        .projectId(soapProjectId)
                        .portId(soapPortId)
                        .operationId(soapOperationId)
                        .mockResponseId(mockResponseId)
                        .build());
                SoapMockResponse soapMockResponse = readSoapMockResponseOutput.getMockResponse();
                soapMockResponse.setStatus(status);
                serviceProcessor.process(UpdateSoapMockResponseInput.builder()
                        .projectId(soapProjectId)
                        .portId(soapPortId)
                        .operationId(soapOperationId)
                        .mockResponseId(mockResponseId)
                        .mockResponse(soapMockResponse)
                        .build());
            }
        } else if(DELETE_MOCK_RESPONSES.equalsIgnoreCase(action)) {
            final List<SoapMockResponse> mockResponses = new ArrayList<SoapMockResponse>();
            for(String mockResponseId : command.getSoapMockResponseIds()){
                final ReadSoapMockResponseOutput output = serviceProcessor.process(ReadSoapMockResponseInput.builder()
                        .projectId(soapProjectId)
                        .portId(soapPortId)
                        .operationId(soapOperationId)
                        .mockResponseId(mockResponseId)
                        .build());
                mockResponses.add(output.getMockResponse());
            }
            final ModelAndView model = createPartialModelAndView(DELETE_MOCK_RESPONSES_PAGE);
            model.addObject(SOAP_PROJECT_ID, soapProjectId);
            model.addObject(SOAP_PORT_ID, soapPortId);
            model.addObject(SOAP_OPERATION_ID, soapOperationId);
            model.addObject(SOAP_MOCK_RESPONSES, mockResponses);
            model.addObject(DELETE_SOAP_MOCK_RESPONSES_COMMAND, new DeleteSoapMockResponsesCommand());
            return model;
        } else if (DUPLICATE_MOCK_RESPONSE.equalsIgnoreCase(action)) {
            String copyOfLabel = messageSource.getMessage("soap.soapoperation.label.copyOf", null, LocaleContextHolder.getLocale());
            for (String mockResponseId : command.getSoapMockResponseIds()) {
                ReadSoapMockResponseOutput readSoapMockResponseOutput = serviceProcessor.process(ReadSoapMockResponseInput.builder()
                        .projectId(soapProjectId)
                        .portId(soapPortId)
                        .operationId(soapOperationId)
                        .mockResponseId(mockResponseId)
                        .build());
                SoapMockResponse soapMockResponse = readSoapMockResponseOutput.getMockResponse();
                soapMockResponse.setId(null);
                soapMockResponse.setName(String.format("%s %s", copyOfLabel, soapMockResponse.getName()));
                serviceProcessor.process(CreateSoapMockResponseInput.builder()
                        .projectId(soapProjectId)
                        .portId(soapPortId)
                        .operationId(soapOperationId)
                        .mockResponse(soapMockResponse)
                        .build());
            }
        }

        return redirect("/soap/project/" + soapProjectId + "/port/" + soapPortId + "/operation/" + soapOperationId);
    }
}
