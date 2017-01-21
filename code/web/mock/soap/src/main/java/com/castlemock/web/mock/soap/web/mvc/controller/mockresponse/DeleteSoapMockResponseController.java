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

package com.castlemock.web.mock.soap.web.mvc.controller.mockresponse;

import com.castlemock.core.mock.soap.model.project.service.message.input.DeleteSoapMockResponseInput;
import com.castlemock.core.mock.soap.model.project.service.message.input.DeleteSoapMockResponsesInput;
import com.castlemock.core.mock.soap.model.project.service.message.input.ReadSoapMockResponseInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.ReadSoapMockResponseOutput;
import com.castlemock.web.mock.soap.web.mvc.command.mockresponse.DeleteSoapMockResponsesCommand;
import com.castlemock.web.mock.soap.web.mvc.controller.AbstractSoapViewController;
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
 * The DeleteMockResponseController is the controller class responsible for deleting mocked responses
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/soap/project")
@ConditionalOnExpression("${server.mode.demo} == false")
public class DeleteSoapMockResponseController extends AbstractSoapViewController {

    private static final String PAGE = "mock/soap/mockresponse/deleteSoapMockResponse";

    /**
     * Retrieves the mocked response that should be deleted and return the matching response
     * and displays it on the delete confirmation page.
     * @param soapProjectId The id of the project that the mocked response belongs to
     * @param soapPortId The id of the port that the mocked response belongs to
     * @param soapOperationId  The id of the operation that the mocked response belongs to
     * @param soapMockResponseId  The id of the mocked response that will be deleted from the server
     * @return Return the mocked response that matches the provided ids and displays the mocked
     * response on the delete confirmation page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/operation/{soapOperationId}/response/{soapMockResponseId}/delete", method = RequestMethod.GET)
    public ModelAndView showConfirmationPage(@PathVariable final String soapProjectId, @PathVariable final String soapPortId, @PathVariable final String soapOperationId, @PathVariable final String soapMockResponseId) {
        final ReadSoapMockResponseOutput output = serviceProcessor.process(new ReadSoapMockResponseInput(soapProjectId, soapPortId, soapOperationId, soapMockResponseId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(SOAP_PROJECT_ID, soapProjectId);
        model.addObject(SOAP_PORT_ID, soapPortId);
        model.addObject(SOAP_OPERATION_ID, soapOperationId);
        model.addObject(SOAP_MOCK_RESPONSE, output.getSoapMockResponse());
        return model;
    }

    /**
     * The method deletes the mocked response with the given id and redirects the user to the operation page
     * @param soapProjectId The id of the project that the mocked response belongs to
     * @param soapPortId The id of the port that the mocked response belongs to
     * @param soapOperationId  The id of the operation that the mocked response belongs to
     * @param soapMockResponseId  The id of the mocked response that will be deleted from the server
     * @return Redirects the user to the operation page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/operation/{soapOperationId}/response/{soapMockResponseId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable final String soapProjectId, @PathVariable final String soapPortId, @PathVariable final String soapOperationId, @PathVariable final String soapMockResponseId) {
        serviceProcessor.process(new DeleteSoapMockResponseInput(soapProjectId, soapPortId, soapOperationId, soapMockResponseId));
        return redirect("/soap/project/" + soapProjectId + "/port/" + soapPortId + "/operation/" + soapOperationId);
    }

    /**
     * The method provides the functionality to remove a list of mocked responses
     * @param soapProjectId The id of the project which the mocked response belongs to
     * @param soapPortId The id of the port which the mocked response belongs to
     * @param soapOperationId The id of the operation that the mocked response belongs to
     * @param deleteSoapMockResponsesCommand The command instance contains the list of mock responses that should be deleted.
     * @return Redirect to the operation page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/operation/{soapOperationId}/response/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultipleMockResponses(@PathVariable final String soapProjectId, @PathVariable final String soapPortId, @PathVariable final String soapOperationId, @ModelAttribute final DeleteSoapMockResponsesCommand deleteSoapMockResponsesCommand) {
        serviceProcessor.process(new DeleteSoapMockResponsesInput(soapProjectId, soapPortId, soapOperationId, deleteSoapMockResponsesCommand.getSoapMockResponses()));
        return redirect("/soap/project/" + soapProjectId + "/port/" + soapPortId + "/operation/" + soapOperationId);
    }

}
