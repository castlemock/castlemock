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
import com.castlemock.service.mock.rest.project.input.UpdateRestMethodInput;
import com.castlemock.service.mock.rest.project.output.UpdateRestMethodOutput;

import java.util.Optional;

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
        final Optional<RestMethod> updated = this.methodRepository.findOne(input.getMethodId())
                .map(method -> method.toBuilder()
                        .name(input.getName())
                        .httpMethod(input.getHttpMethod())
                        .responseStrategy(input.getResponseStrategy())
                        .status(input.getStatus())
                        .forwardedEndpoint(input.getForwardedEndpoint()
                                .orElse(null))
                        .networkDelay(input.getNetworkDelay()
                                .orElse(null))
                        .simulateNetworkDelay(input.getSimulateNetworkDelay()
                                .orElse(null))
                        .automaticForward(input.getAutomaticForward()
                                .orElse(null))
                        .defaultMockResponseId(input.getDefaultMockResponseId()
                                .orElse(null))
                        .build())
                .map(method -> this.methodRepository.update(input.getMethodId(), method));

        return createServiceResult(UpdateRestMethodOutput.builder()
                .method(updated.orElse(null))
                .build());
    }
}
