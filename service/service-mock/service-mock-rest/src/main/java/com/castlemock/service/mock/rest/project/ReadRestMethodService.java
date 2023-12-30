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

package com.castlemock.service.mock.rest.project;

import com.castlemock.model.core.Service;
import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.service.mock.rest.project.input.ReadRestMethodInput;
import com.castlemock.service.mock.rest.project.output.ReadRestMethodOutput;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadRestMethodService extends AbstractRestProjectService implements Service<ReadRestMethodInput, ReadRestMethodOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ReadRestMethodOutput> process(final ServiceTask<ReadRestMethodInput> serviceTask) {
        final ReadRestMethodInput input = serviceTask.getInput();
        final RestResource restResource = this.resourceRepository.findOne(input.getRestResourceId());
        final RestMethod restMethod = this.methodRepository.findOne(input.getRestMethodId());
        final List<RestMockResponse> mockResponses = this.mockResponseRepository.findWithMethodId(input.getRestMethodId());

        final RestMethod.Builder builder = restMethod.toBuilder()
                .uri(restResource.getUri())
                .mockResponses(mockResponses);
        if(restMethod.getDefaultMockResponseId() != null){
            // Iterate through all the mocked responses to identify
            // which has been set to be the default XPath mock response.
            mockResponses
                    .stream()
                    .filter(mockResponse -> mockResponse.getId().equals(restMethod.getDefaultMockResponseId()))
                    .findFirst()
                    .ifPresent(mockResponse -> builder.defaultResponseName(mockResponse.getName()));
        }

        return createServiceResult(ReadRestMethodOutput.builder()
                .restMethod(builder.build())
                .build());
    }
}
