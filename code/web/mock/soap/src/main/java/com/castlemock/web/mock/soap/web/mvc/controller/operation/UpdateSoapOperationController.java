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

package com.castlemock.web.mock.soap.web.mvc.controller.operation;

import com.castlemock.core.mock.soap.model.project.domain.SoapResponseStrategy;
import com.castlemock.core.mock.soap.model.project.dto.SoapOperationDto;
import com.castlemock.core.mock.soap.model.project.service.message.input.ReadSoapOperationInput;
import com.castlemock.core.mock.soap.model.project.service.message.input.UpdateSoapOperationInput;
import com.castlemock.core.mock.soap.model.project.service.message.input.UpdateSoapOperationsForwardedEndpointInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.ReadSoapOperationOutput;
import com.castlemock.web.mock.soap.web.mvc.command.operation.UpdateSoapOperationsEndpointCommand;
import com.castlemock.web.mock.soap.web.mvc.controller.AbstractSoapViewController;
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
 * The UpdateServiceController provides functionality to update a specific operation
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/soap/project")
public class UpdateSoapOperationController extends AbstractSoapViewController {

    private static final String PAGE = "mock/soap/operation/updateSoapOperation";

    /**
     * The method retrieves a specific operation and returns a view that displays the retrieved operation.
     * @param soapProjectId The id of the project that the operation belongs to
     * @param soapPortId The id of the port that the operation belongs to
     * @param soapOperationId The id of the operation that will be retrieved
     * @return A view that displays the retrieved operation
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/operation/{soapOperationId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String soapProjectId, @PathVariable final String soapPortId, @PathVariable final String soapOperationId) {
        final ReadSoapOperationOutput output = serviceProcessor.process(new ReadSoapOperationInput(soapProjectId, soapPortId, soapOperationId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(COMMAND, output.getSoapOperation());
        model.addObject(SOAP_PROJECT_ID, soapProjectId);
        model.addObject(SOAP_PORT_ID, soapPortId);
        model.addObject(SOAP_OPERATION_ID, soapOperationId);
        model.addObject(SOAP_MOCK_RESPONSE_STRATEGIES, SoapResponseStrategy.values());
        model.addObject(SOAP_OPERATION_STATUSES, getSoapOperationStatuses());
        return model;
    }

    /**
     * The update method is responsible for updating a specific operation
     * @param soapProjectId The id of the project that the operation belongs to
     * @param soapPortId The id of the port that the operation belongs to
     * @param soapOperationId The id of the operation that will be updated
     * @param soapOperationDto The updated version of the operation that will be updated
     * @return Redirects the user to the SOAP operation page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/operation/{soapOperationId}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable final String soapProjectId, @PathVariable final String soapPortId, @PathVariable final String soapOperationId, @ModelAttribute final SoapOperationDto soapOperationDto) {
        serviceProcessor.process(new UpdateSoapOperationInput(soapProjectId, soapPortId, soapOperationId, soapOperationDto));
        return redirect("/soap/project/" + soapProjectId + "/port/" + soapPortId + "/operation/" + soapOperationId);
    }

    /**
     * The method provide the functionality to update the endpoint for one or more operations
     * @param soapProjectId The id of project that the operations belongs to
     * @param soapPortId The id of the port that the operations belongs to
     * @param updateSoapOperationsEndpointCommand The command object contains the operations that will be updated.
     *                                      The command also contains the updated forwarded address.
     * @return Redirect the user to the SOAP port page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/operation/update/confirm", method = RequestMethod.POST)
    public ModelAndView updateEndpoint(@PathVariable final String soapProjectId, @PathVariable final String soapPortId, @ModelAttribute final UpdateSoapOperationsEndpointCommand updateSoapOperationsEndpointCommand) {
        Preconditions.checkNotNull(updateSoapOperationsEndpointCommand, "The update operation endpoint command cannot be null");
        serviceProcessor.process(new UpdateSoapOperationsForwardedEndpointInput(soapProjectId, soapPortId, updateSoapOperationsEndpointCommand.getSoapOperations(), updateSoapOperationsEndpointCommand.getForwardedEndpoint()));
        return redirect("/soap/project/" + soapProjectId + "/port/" + soapPortId);
    }

}