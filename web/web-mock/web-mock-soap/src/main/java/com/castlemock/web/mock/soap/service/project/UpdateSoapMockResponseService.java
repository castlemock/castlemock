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

package com.castlemock.web.mock.soap.service.project;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapMockResponseInput;
import com.castlemock.core.mock.soap.service.project.output.UpdateSoapMockResponseOutput;

/**
 * The service provides functionality to update a specific SOAP mock response.
 * @author Karl Dahlgren
 * @since 1.0
 * @see UpdateSoapMockResponseInput
 * @see UpdateSoapMockResponseOutput
 */
@org.springframework.stereotype.Service
public class UpdateSoapMockResponseService extends AbstractSoapProjectService implements Service<UpdateSoapMockResponseInput, UpdateSoapMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<UpdateSoapMockResponseOutput> process(final ServiceTask<UpdateSoapMockResponseInput> serviceTask) {
        final UpdateSoapMockResponseInput input = serviceTask.getInput();
        final SoapMockResponse mockResponse = this.mockResponseRepository.findOne(input.getMockResponseId());

        mockResponse.setName(input.getName());
        mockResponse.setBody(input.getBody());
        mockResponse.setHttpStatusCode(input.getHttpStatusCode());
        mockResponse.setStatus(input.getStatus());
        mockResponse.setHttpHeaders(input.getHttpHeaders());
        mockResponse.setUsingExpressions(input.isUsingExpressions());
        mockResponse.setXpathExpressions(input.getXpathExpressions());

        final SoapMockResponse updatedSoapMockResponse = mockResponseRepository.update(input.getMockResponseId(), mockResponse);
        return createServiceResult(UpdateSoapMockResponseOutput.builder()
                .mockResponse(updatedSoapMockResponse)
                .build());
    }
}
