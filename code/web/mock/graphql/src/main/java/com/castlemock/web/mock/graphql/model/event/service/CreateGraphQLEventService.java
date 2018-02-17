/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.mock.graphql.model.event.service;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.graphql.model.event.dto.GraphQLEventDto;
import com.castlemock.core.mock.graphql.model.event.service.message.input.CreateGraphQLEventInput;
import com.castlemock.core.mock.graphql.model.event.service.message.output.CreateGraphQLEventOutput;
import org.springframework.beans.factory.annotation.Value;

public class CreateGraphQLEventService extends AbstractGraphQLEventService implements Service<CreateGraphQLEventInput, CreateGraphQLEventOutput> {

    @Value("${graphql.event.max}")
    private Integer graphQLMaxEventCount;
   
    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     *
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<CreateGraphQLEventOutput> process(ServiceTask<CreateGraphQLEventInput> serviceTask) {
        final CreateGraphQLEventInput input = serviceTask.getInput();
        final GraphQLEventDto soapEventDto = input.getGraphQLEvent();
        if(count() >= graphQLMaxEventCount){
            repository.deleteOldestEvent();
        }
        final GraphQLEventDto createdGraphQLEvent = save(soapEventDto);
        return createServiceResult(new CreateGraphQLEventOutput(createdGraphQLEvent));
    }
}
