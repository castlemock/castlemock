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

package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.Service;
import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.service.mock.soap.project.input.UpdateSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapMockResponseOutput;

import java.util.Optional;

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
        final Optional<SoapMockResponse> soapMockResponse = this.mockResponseRepository.findOne(input.getMockResponseId())
                .map(mockResponse -> mockResponse.toBuilder()
                        .name(input.getName())
                        .body(input.getBody())
                        .httpStatusCode(input.getHttpStatusCode())
                        .status(input.getStatus())
                        .httpHeaders(input.getHttpHeaders())
                        .usingExpressions(input.getUsingExpressions()
                                .orElse(false))
                        .xpathExpressions(input.getXpathExpressions())
                        .build())
                .map(mockResponse -> mockResponseRepository.update(input.getMockResponseId(), mockResponse));
        return createServiceResult(UpdateSoapMockResponseOutput.builder()
                .mockResponse(soapMockResponse.orElse(null))
                .build());
    }
}
