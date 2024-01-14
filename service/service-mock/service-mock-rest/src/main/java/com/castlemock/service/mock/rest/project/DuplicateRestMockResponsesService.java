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

package com.castlemock.service.mock.rest.project;

import com.castlemock.model.core.Service;
import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.service.mock.rest.project.input.DuplicateRestMockResponsesInput;
import com.castlemock.service.mock.rest.project.output.DuplicateRestMockResponsesOutput;

import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
@org.springframework.stereotype.Service
public class DuplicateRestMockResponsesService extends AbstractRestProjectService implements Service<DuplicateRestMockResponsesInput, DuplicateRestMockResponsesOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<DuplicateRestMockResponsesOutput> process(final ServiceTask<DuplicateRestMockResponsesInput> serviceTask) {
        serviceTask.getInput()
                .getMockResponseIds()
                .stream()
                .map(this.mockResponseRepository::findOne)
                .flatMap(Optional::stream)
                .map(RestMockResponse::toBuilder)
                .map(builder -> builder.id(null))
                .map(RestMockResponse.Builder::build)
                .forEach(this.mockResponseRepository::save);

        return createServiceResult(DuplicateRestMockResponsesOutput.builder().build());
    }
}
