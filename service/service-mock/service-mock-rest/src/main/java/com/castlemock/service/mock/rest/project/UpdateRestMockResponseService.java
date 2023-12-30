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
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.service.mock.rest.project.input.UpdateRestMockResponseInput;
import com.castlemock.service.mock.rest.project.output.UpdateRestMockResponseOutput;

/**
 * The service provides the functionality to update an already existing REST mock response.
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateRestMockResponseService extends AbstractRestProjectService implements Service<UpdateRestMockResponseInput, UpdateRestMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<UpdateRestMockResponseOutput> process(final ServiceTask<UpdateRestMockResponseInput> serviceTask) {
        final UpdateRestMockResponseInput input = serviceTask.getInput();
        final RestMockResponse existing = this.mockResponseRepository.findOne(input.getRestMockResponseId()).toBuilder()
                .name(input.getName())
                .body(input.getBody())
                .httpStatusCode(input.getHttpStatusCode())
                .httpHeaders(input.getHttpHeaders())
                .status(input.getStatus())
                .usingExpressions(input.isUsingExpressions())
                .parameterQueries(input.getParameterQueries())
                .xpathExpressions(input.getXpathExpressions())
                .jsonPathExpressions(input.getJsonPathExpressions())
                .headerQueries(input.getHeaderQueries())
                .build();

        final RestMockResponse updated = this.mockResponseRepository.update(input.getRestMockResponseId(), existing);
        return createServiceResult(UpdateRestMockResponseOutput.builder()
                .updatedRestMockResponse(updated)
                .build());
    }
}
