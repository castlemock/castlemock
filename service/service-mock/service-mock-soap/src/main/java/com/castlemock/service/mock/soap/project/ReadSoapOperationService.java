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
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.service.mock.soap.project.input.ReadSoapOperationInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapOperationOutput;

import java.util.List;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapOperationService extends AbstractSoapProjectService implements Service<ReadSoapOperationInput, ReadSoapOperationOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ReadSoapOperationOutput> process(final ServiceTask<ReadSoapOperationInput> serviceTask) {
        final ReadSoapOperationInput input = serviceTask.getInput();
        final Optional<SoapOperation> soapOperation = this.operationRepository.findOne(input.getOperationId())
                .map(this::prepareSoapOperation);
        return createServiceResult(ReadSoapOperationOutput.builder()
                .operation(soapOperation.orElse(null))
                .build());
    }

    private SoapOperation prepareSoapOperation(final SoapOperation soapOperation) {
        final List<SoapMockResponse> mockResponses = this.mockResponseRepository.findWithOperationId(soapOperation.getId());
        return soapOperation.toBuilder()
                .mockResponses(mockResponses)
                .defaultResponseName(soapOperation.getDefaultMockResponseId()
                        .flatMap(defaultMockResponseId -> mockResponses.stream()
                                .filter(mockResponse -> mockResponse.getId().equals(defaultMockResponseId))
                                .findFirst())
                        .map(SoapMockResponse::getName)
                        .orElse(null))
                .build();
    }
}
