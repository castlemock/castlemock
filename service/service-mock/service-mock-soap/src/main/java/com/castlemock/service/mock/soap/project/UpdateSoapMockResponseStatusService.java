/*
 * Copyright 2020 Karl Dahlgren
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
import com.castlemock.service.mock.soap.project.input.UpdateSoapMockResponseStatusInput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapMockResponseStatusOutput;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
@org.springframework.stereotype.Service
public class UpdateSoapMockResponseStatusService extends AbstractSoapProjectService implements Service<UpdateSoapMockResponseStatusInput, UpdateSoapMockResponseStatusOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<UpdateSoapMockResponseStatusOutput> process(final ServiceTask<UpdateSoapMockResponseStatusInput> serviceTask) {
        final UpdateSoapMockResponseStatusInput input = serviceTask.getInput();
        this.mockResponseRepository.findOne(input.getMockResponseId())
                .map(mockResponse -> mockResponse.toBuilder()
                        .status(input.getMockResponseStatus())
                        .build())
                .ifPresent(mockResponse -> this.mockResponseRepository.update(input.getMockResponseId(), mockResponse));
        return createServiceResult(UpdateSoapMockResponseStatusOutput.builder()
                .build());
    }
}
