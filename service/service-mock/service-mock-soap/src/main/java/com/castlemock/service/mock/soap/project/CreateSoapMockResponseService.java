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
import com.castlemock.model.core.utility.IdUtility;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.service.mock.soap.project.input.CreateSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.output.CreateSoapMockResponseOutput;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateSoapMockResponseService extends AbstractSoapProjectService implements Service<CreateSoapMockResponseInput, CreateSoapMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<CreateSoapMockResponseOutput> process(final ServiceTask<CreateSoapMockResponseInput> serviceTask) {
        final CreateSoapMockResponseInput input = serviceTask.getInput();
        final SoapMockResponse mockResponse = SoapMockResponse.builder()
                .id(IdUtility.generateId())
                .name(input.getName())
                .body(input.getBody().orElse(""))
                .httpStatusCode(input.getHttpStatusCode())
                .usingExpressions(input.getUsingExpressions().orElse(false))
                .status(input.getStatus())
                .operationId(input.getOperationId())
                .httpHeaders(input.getHttpHeaders())
                .contentEncodings(List.of())
                .xpathExpressions(input.getXpathExpressions())
                .build();
        final SoapMockResponse createdSoapMockResponse = this.mockResponseRepository.save(mockResponse);
        return createServiceResult(CreateSoapMockResponseOutput.builder()
                .mockResponse(createdSoapMockResponse)
                .build());
    }
}
