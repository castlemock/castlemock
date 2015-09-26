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

package com.fortmocks.war.mock.soap.web.mvc.controller.operation;

import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.project.SoapMockResponseStatus;
import com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.war.mock.soap.model.event.service.SoapEventService;
import com.fortmocks.war.mock.soap.model.project.service.SoapProjectService;
import com.fortmocks.war.mock.soap.web.mvc.command.mockresponse.DeleteSoapMockResponsesCommand;
import com.fortmocks.war.mock.soap.web.mvc.command.mockresponse.SoapMockResponseModifierCommand;
import com.fortmocks.war.mock.soap.web.mvc.controller.AbstractSoapViewController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
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
    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";
    private static final String DELETE_SOAP_MOCK_RESPONSES_COMMAND = "deleteSoapMockResponsesCommand";
    private static final String SOAP_MOCK_RESPONSE_MODIFIER_COMMAND = "soapMockResponseModifierCommand";
    private static final String DELETE_MOCK_RESPONSES_PAGE = "mock/soap/mockresponse/deleteSoapMockResponses";
    private static final String UPDATE_STATUS = "update";
    private static final String DELETE_MOCK_RESPONSES = "delete";
    private static final String LOCAL_ADDRESS = "127.0.0.1";
    private static final Logger LOGGER = Logger.getLogger(SoapOperationController.class);

    @Autowired
    private SoapProjectService soapProjectService;

    @Autowired
    private SoapEventService soapEventService;

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
    public ModelAndView defaultPage(@PathVariable final Long soapProjectId, @PathVariable final Long soapPortId, @PathVariable final Long soapOperationId, final ServletRequest request) {
        final SoapOperationDto soapOperation = soapProjectService.findSoapOperation(soapProjectId, soapPortId, soapOperationId);
        final List<SoapEventDto> events = soapEventService.findEventsByOperationId(soapOperationId);
        soapOperation.setEvents(events);

        String requestProtocol = HTTP;
        if(request.isSecure()){
            requestProtocol = HTTPS;
        }

        try {
            final String hostAddress = getHostAddress();
            soapOperation.setInvokeAddress(requestProtocol + hostAddress + ":" + request.getServerPort() + getContext() + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT + SLASH + soapProjectId + SLASH + soapOperation.getUri());
        } catch (Exception exception) {
            LOGGER.error("Unable to generate invoke URL", exception);
            throw new IllegalStateException("Unable to generate invoke URL for " + soapOperation.getName());
        }

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(SOAP_OPERATION, soapOperation);
        model.addObject(SOAP_PROJECT_ID, soapProjectId);
        model.addObject(SOAP_PORT_ID, soapPortId);
        model.addObject(SOAP_MOCK_RESPONSE_STATUSES, SoapMockResponseStatus.values());
        model.addObject(SOAP_MOCK_RESPONSE_MODIFIER_COMMAND, new SoapMockResponseModifierCommand());
        return model;
    }

    /**
     *
     * @param soapProjectId The id of the project that the mocked response(s) belongs to
     * @param soapPortId The id of the port that the mocked response(s) belongs to
     * @param soapOperationId The id of the operation that the mocked response(s) belongs to
     * @param action The name of the action that should be executed (delete or update).
     * @param soapMockResponseModifierCommand The command object that contains the list of mocked responses that get affected by the executed action.
     * @return Redirects the user back to the operation page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/operation/{soapOperationId}", method = RequestMethod.POST)
    public ModelAndView serviceFunctionality(@PathVariable final Long soapProjectId, @PathVariable final Long soapPortId, @PathVariable final Long soapOperationId, @RequestParam final String action, @ModelAttribute final SoapMockResponseModifierCommand soapMockResponseModifierCommand) {
        LOGGER.debug("SOAP operation action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final SoapMockResponseStatus status = SoapMockResponseStatus.valueOf(soapMockResponseModifierCommand.getSoapMockResponseStatus());
            for(Long mockResponseId : soapMockResponseModifierCommand.getSoapMockResponseIds()){
                soapProjectService.updateStatus(soapProjectId, soapPortId, soapOperationId, mockResponseId, status);
            }
        } else if(DELETE_MOCK_RESPONSES.equalsIgnoreCase(action)) {
            final List<SoapMockResponseDto> mockResponses = new ArrayList<SoapMockResponseDto>();
            for(Long mockResponseId : soapMockResponseModifierCommand.getSoapMockResponseIds()){
                final SoapMockResponseDto soapMockResponseDto = soapProjectService.findSoapMockResponse(soapProjectId, soapPortId, soapOperationId, mockResponseId);
                mockResponses.add(soapMockResponseDto);
            }
            final ModelAndView model = createPartialModelAndView(DELETE_MOCK_RESPONSES_PAGE);
            model.addObject(SOAP_PROJECT_ID, soapProjectId);
            model.addObject(SOAP_PORT_ID, soapPortId);
            model.addObject(SOAP_OPERATION_ID, soapOperationId);
            model.addObject(SOAP_MOCK_RESPONSES, mockResponses);
            model.addObject(DELETE_SOAP_MOCK_RESPONSES_COMMAND, new DeleteSoapMockResponsesCommand());
            return model;
        }

        return redirect("/soap/project/" + soapProjectId + "/port/" + soapPortId + "/operation/" + soapOperationId);
    }

    /**
     * The method returns the local address (Not link local address, not loopback address) which the server is deployed on.
     * If the method does not find any INet4Address that is neither link local address and loopback address, the method
     * will return the the address 127.0.0.1
     * @return Returns the local address or 127.0.0.1 if no address was found
     * @throws SocketException
     */
    public String getHostAddress() throws SocketException {

        final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements())
        {
            final NetworkInterface networkInterface = networkInterfaces.nextElement();
            final Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements())
            {
                final InetAddress address = addresses.nextElement();
                if (!address.isLinkLocalAddress() && !address.isLoopbackAddress() && address instanceof Inet4Address){
                    return address.getHostAddress();
                }
            }
        }

        return LOCAL_ADDRESS;
    }

}