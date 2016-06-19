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

import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponseStatus;
import com.castlemock.core.mock.soap.model.project.service.message.input.ReadSoapMockResponseInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.ReadSoapMockResponseOutput;
import com.castlemock.web.mock.soap.web.mvc.controller.AbstractSoapViewController;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The SoapMockResponseController controller provides functionality to retrieve specific SOAP mock responses
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/soap/project")
public class SoapMockResponseController extends AbstractSoapViewController {

    private static final String PAGE = "mock/soap/mockresponse/soapMockResponse";

    /**
     * The method is responsible for retrieving a specific mocked response with a provided id
     * @param soapProjectId The id of the project which the operation belongs to
     * @param soapPortId The id of the port which the operation belongs to
     * @param soapOperationId The id of the operation that the mocked response belongs to
     * @param soapMockResponseId The id of the mocked response that should be retrieved
     * @return A view that displays the retrieved mocked response
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/operation/{soapOperationId}/response/{soapMockResponseId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String soapProjectId, @PathVariable final String soapPortId, @PathVariable final String soapOperationId, @PathVariable final String soapMockResponseId) {
        final ReadSoapMockResponseOutput output = serviceProcessor.process(new ReadSoapMockResponseInput(soapProjectId, soapPortId, soapOperationId, soapMockResponseId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(SOAP_MOCK_RESPONSE, output.getSoapMockResponse());
        model.addObject(SOAP_PROJECT_ID, soapProjectId);
        model.addObject(SOAP_PORT_ID, soapPortId);
        model.addObject(SOAP_OPERATION_ID, soapOperationId);
        model.addObject(SOAP_MOCK_RESPONSE_STATUSES, SoapMockResponseStatus.values());
        return model;
    }
}