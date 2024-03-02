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
import com.castlemock.model.core.utility.IdUtility;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;
import com.castlemock.service.mock.rest.project.input.CreateRestMethodInput;
import com.castlemock.service.mock.rest.project.output.CreateRestMethodOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateRestMethodService extends AbstractRestProjectService implements Service<CreateRestMethodInput, CreateRestMethodOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<CreateRestMethodOutput> process(final ServiceTask<CreateRestMethodInput> serviceTask) {
        final CreateRestMethodInput input = serviceTask.getInput();
        final RestMethod restMethod = RestMethod.builder()
                .id(IdUtility.generateId())
                .name(input.getName())
                .httpMethod(input.getHttpMethod())
                .resourceId(input.getResourceId())
                .status(RestMethodStatus.MOCKED)
                .responseStrategy(RestResponseStrategy.RANDOM)
                .build();
        final RestMethod createdRestMethod = this.methodRepository.save(restMethod);
        return createServiceResult(CreateRestMethodOutput.builder()
                .method(createdRestMethod)
                .build());
    }
}
