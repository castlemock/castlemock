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
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.service.mock.soap.project.input.UpdateSoapOperationInput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapOperationOutput;

import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateSoapOperationService extends AbstractSoapProjectService implements Service<UpdateSoapOperationInput, UpdateSoapOperationOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<UpdateSoapOperationOutput> process(final ServiceTask<UpdateSoapOperationInput> serviceTask) {
        final UpdateSoapOperationInput input = serviceTask.getInput();
        final Optional<SoapOperation> updatedSoapOperation = this.operationRepository.findOne(input.getOperationId())
                .map(soapOperation -> soapOperation.toBuilder()
                        .status(input.getStatus())
                        .responseStrategy(input.getResponseStrategy())
                        .identifyStrategy(input.getIdentifyStrategy())
                        .simulateNetworkDelay(input.getSimulateNetworkDelay()
                                .orElse(false))
                        .mockOnFailure(input.getMockOnFailure()
                                .orElse(false))
                        .automaticForward(input.getAutomaticForward()
                                .orElse(false))
                        .forwardedEndpoint(input.getForwardedEndpoint()
                                .orElse(null))
                        .networkDelay(input.getNetworkDelay()
                                .orElse(null))
                        .defaultMockResponseId(input.getDefaultMockResponseId()
                                .orElse(null))
                        .build())
                .map(soapOperation -> this.operationRepository.update(input.getOperationId(), soapOperation));
        return createServiceResult(UpdateSoapOperationOutput.builder()
                .operation(updatedSoapOperation.orElse(null))
                .build());
    }
}
