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

package com.castlemock.web.mock.graphql.model.project.service;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.graphql.model.project.dto.*;
import com.castlemock.core.mock.graphql.model.project.service.message.input.ReadGraphQLApplicationInput;
import com.castlemock.core.mock.graphql.model.project.service.message.output.ReadGraphQLApplicationOutput;

import java.util.List;


/**
 * @author Karl Dahlgren
 * @since 1.19
 */
@org.springframework.stereotype.Service
public class ReadGraphQLApplicationService extends AbstractGraphQLProjectService implements Service<ReadGraphQLApplicationInput, ReadGraphQLApplicationOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ReadGraphQLApplicationOutput> process(final ServiceTask<ReadGraphQLApplicationInput> serviceTask) {
        final ReadGraphQLApplicationInput input = serviceTask.getInput();
        final GraphQLApplicationDto application =
                this.applicationRepository.findOne(input.getGraphQLApplicationId());

        final List<GraphQLQueryDto> queries = this.queryRepository.findWithApplicationId(application.getId());
        final List<GraphQLMutationDto> mutations = this.mutationRepository.findWithApplicationId(application.getId());
        final List<GraphQLSubscriptionDto> subscriptions = this.subscriptionRepository.findWithApplicationId(application.getId());
        final List<GraphQLObjectTypeDto> objectTypes = this.objectTypeRepository.findWithApplicationId(application.getId());
        final List<GraphQLEnumTypeDto> enumTypes = this.enumTypeRepository.findWithApplicationId(application.getId());

        application.setQueries(queries);
        application.setMutations(mutations);
        application.setSubscriptions(subscriptions);
        application.setObjects(objectTypes);
        application.setEnums(enumTypes);

        return createServiceResult(new ReadGraphQLApplicationOutput(application));
    }
}
