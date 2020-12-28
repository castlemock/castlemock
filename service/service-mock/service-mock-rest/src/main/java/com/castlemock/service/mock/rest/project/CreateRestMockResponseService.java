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
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;
import com.castlemock.service.mock.rest.project.input.CreateRestMockResponseInput;
import com.castlemock.service.mock.rest.project.output.CreateRestMockResponseOutput;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateRestMockResponseService extends AbstractRestProjectService implements Service<CreateRestMockResponseInput, CreateRestMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<CreateRestMockResponseOutput> process(final ServiceTask<CreateRestMockResponseInput> serviceTask) {
        final CreateRestMockResponseInput input = serviceTask.getInput();
        final RestMockResponse mockResponse = RestMockResponse.builder()
                .id(RandomStringUtils.random(6, true, true))
                .body(input.getBody().orElse(""))
                .contentEncodings(input.getContentEncodings().orElseGet(CopyOnWriteArrayList::new))
                .headerQueries(input.getHeaderQueries().orElseGet(CopyOnWriteArrayList::new))
                .httpHeaders(input.getHttpHeaders().orElseGet(CopyOnWriteArrayList::new))
                .jsonPathExpressions(input.getJsonPathExpressions().orElseGet(CopyOnWriteArrayList::new))
                .parameterQueries(input.getParameterQueries().orElseGet(CopyOnWriteArrayList::new))
                .status(input.getStatus().orElse(RestMockResponseStatus.ENABLED))
                .xpathExpressions(input.getXpathExpressions().orElseGet(CopyOnWriteArrayList::new))
                .httpStatusCode(input.getHttpStatusCode().orElse(200))
                .methodId(input.getMethodId())
                .name(input.getName())
                .usingExpressions(input.getUsingExpressions().orElse(false))
                .build();
        final RestMockResponse createdMockResponse = mockResponseRepository.save(mockResponse);
        return createServiceResult(CreateRestMockResponseOutput.builder()
                .restMockResponse(createdMockResponse)
                .build());
    }
}
