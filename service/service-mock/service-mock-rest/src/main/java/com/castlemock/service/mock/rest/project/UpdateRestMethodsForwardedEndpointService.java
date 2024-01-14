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
import com.castlemock.service.mock.rest.project.input.UpdateRestMethodsForwardedEndpointInput;
import com.castlemock.service.mock.rest.project.output.UpdateRestMethodsForwardedEndpointOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateRestMethodsForwardedEndpointService extends AbstractRestProjectService implements Service<UpdateRestMethodsForwardedEndpointInput, UpdateRestMethodsForwardedEndpointOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<UpdateRestMethodsForwardedEndpointOutput> process(final ServiceTask<UpdateRestMethodsForwardedEndpointInput> serviceTask) {
        final UpdateRestMethodsForwardedEndpointInput input = serviceTask.getInput();
        for(String methodId : input.getMethodIds()){
            this.methodRepository.findOne(methodId)
                    .ifPresent(method -> this.methodRepository.update(method.getId(), method.toBuilder()
                            .forwardedEndpoint(input.getForwardedEndpoint())
                            .build()));
        }
        return createServiceResult(UpdateRestMethodsForwardedEndpointOutput.builder().build());
    }
}
