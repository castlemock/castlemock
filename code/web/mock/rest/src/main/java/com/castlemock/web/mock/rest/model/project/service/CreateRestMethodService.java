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

package com.castlemock.web.mock.rest.model.project.service;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.castlemock.core.mock.rest.model.project.dto.RestMethodDto;
import com.castlemock.core.mock.rest.model.project.service.message.input.CreateRestMethodInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.CreateRestMethodOutput;

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
        final RestMethodDto restMethod = input.getRestMethod();
        if(restMethod.getStatus() == null){
            restMethod.setStatus(RestMethodStatus.MOCKED);
        }
        if(restMethod.getResponseStrategy() == null){
            restMethod.setResponseStrategy(RestResponseStrategy.RANDOM);
        }

        final RestMethodDto createdRestMethod = repository.saveRestMethod(input.getRestProjectId(),
                input.getRestApplicationId(), input.getRestResourceId(), input.getRestMethod());
        return createServiceResult(new CreateRestMethodOutput(createdRestMethod));
    }
}
