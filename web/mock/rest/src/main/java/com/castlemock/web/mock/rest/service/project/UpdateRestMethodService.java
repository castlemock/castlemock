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

package com.castlemock.web.mock.rest.service.project;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.UpdateRestMethodOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateRestMethodService extends AbstractRestProjectService implements Service<UpdateRestMethodInput, UpdateRestMethodOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<UpdateRestMethodOutput> process(final ServiceTask<UpdateRestMethodInput> serviceTask) {
        final UpdateRestMethodInput input = serviceTask.getInput();
        final RestMethod updatedRestMethod = input.getRestMethod();
        final RestMethod existing = this.methodRepository.findOne(input.getRestMethodId());
        
        existing.setName(updatedRestMethod.getName());
        existing.setHttpMethod(updatedRestMethod.getHttpMethod());
        existing.setResponseStrategy(updatedRestMethod.getResponseStrategy());
        existing.setStatus(updatedRestMethod.getStatus());
        existing.setForwardedEndpoint(updatedRestMethod.getForwardedEndpoint());
        existing.setNetworkDelay(updatedRestMethod.getNetworkDelay());
        existing.setSimulateNetworkDelay(updatedRestMethod.getSimulateNetworkDelay());
        existing.setDefaultMockResponseId(updatedRestMethod.getDefaultMockResponseId());


        this.methodRepository.update(input.getRestMethodId(), existing);
        return createServiceResult(UpdateRestMethodOutput.builder()
                .restMethod(updatedRestMethod)
                .build());
    }
}
