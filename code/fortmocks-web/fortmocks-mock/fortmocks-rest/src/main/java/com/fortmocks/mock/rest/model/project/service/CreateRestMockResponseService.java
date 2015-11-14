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

package com.fortmocks.mock.rest.model.project.service;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.*;
import com.fortmocks.mock.rest.model.project.dto.RestMockResponseDto;
import com.fortmocks.mock.rest.model.project.service.message.input.CreateRestMockResponseInput;
import com.fortmocks.mock.rest.model.project.service.message.output.CreateRestMockResponseOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateRestMockResponseService extends AbstractRestProjectProcessor implements Service<CreateRestMockResponseInput, CreateRestMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<CreateRestMockResponseOutput> process(final Task<CreateRestMockResponseInput> task) {
        final CreateRestMockResponseInput input = task.getInput();
        final RestMethod restMethod = findRestMethodType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId(), input.getRestMethodId());
        final RestMockResponse restMockResponse = mapper.map(input.getRestMockResponse(), RestMockResponse.class);
        final Long restMockResponseId = getNextRestMockResponseId();
        restMockResponse.setId(restMockResponseId);
        restMethod.getRestMockResponses().add(restMockResponse);
        save(input.getRestProjectId());
        return createResult(new CreateRestMockResponseOutput(mapper.map(restMockResponse, RestMockResponseDto.class)));
    }
}
