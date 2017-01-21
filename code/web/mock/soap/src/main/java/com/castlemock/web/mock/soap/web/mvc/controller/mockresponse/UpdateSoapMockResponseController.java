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

import com.castlemock.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.castlemock.core.mock.soap.model.project.service.message.input.UpdateSoapMockResponseInput;
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
 * The UpdateSoapMockResponseController controller provides functionality to update a specific SOAP mocked response
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/soap/project")
@ConditionalOnExpression("${server.mode.demo} == false")
public class UpdateSoapMockResponseController extends AbstractSoapViewController {

    /**
     * The method provides the functionality to mock a specific mocked response
     * @param soapProjectId The id of the project which the operation belongs to
     * @param soapPortId The id of the application which the operation belongs to
     * @param soapOperationId The id of the operation that the mocked response belongs to
     * @param soapMockResponseId The id of the mocked response that will be updated
     * @param soapMockResponseDto Contains all the new information regarding the mocked response with the provided mock response id
     * @return A view and model that redirects the user to the operation main page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/operation/{soapOperationId}/response/{soapMockResponseId}/update", method = RequestMethod.POST)
    public ModelAndView updateSoapMockResponse(@PathVariable final String soapProjectId, @PathVariable final String soapPortId, @PathVariable final String soapOperationId, @PathVariable final String soapMockResponseId,  @ModelAttribute final SoapMockResponseDto soapMockResponseDto) {
        serviceProcessor.process(new UpdateSoapMockResponseInput(soapProjectId, soapPortId, soapOperationId, soapMockResponseId, soapMockResponseDto));
        return redirect("/soap/project/" + soapProjectId + "/port/" + soapPortId + "/operation/" + soapOperationId);
    }

}
