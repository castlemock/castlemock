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


package com.castlemock.web.mock.graphql.service.project;


import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLOperation;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLRequestQuery;
import com.castlemock.core.mock.graphql.service.project.input.IdentifyGraphQLOperationInput;
import com.castlemock.core.mock.graphql.service.project.output.IdentifyGraphQLOperationOutput;

import java.util.List;
import java.util.Map;


/**
 * @author Karl Dahlgren
 * @since 1.19
 */
@org.springframework.stereotype.Service
public class IdentifyGraphQLOperationService extends AbstractGraphQLProjectService implements Service<IdentifyGraphQLOperationInput, IdentifyGraphQLOperationOutput> {

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
    public ServiceResult<IdentifyGraphQLOperationOutput> process(ServiceTask<IdentifyGraphQLOperationInput> serviceTask) {
        final IdentifyGraphQLOperationInput input = serviceTask.getInput();
        /*
        final GraphQLApplication application =
                repository.findGraphQLApplication(input.getGraphQLProjectId(), input.getGraphQLApplicationId());
        final List<GraphQLRequestQuery> requestQueries = input.getQueries();
        final Map<GraphQLRequestQuery, GraphQLOperation> mapping = new HashMap<>();

        mapOperations(requestQueries, application.getQueries(), mapping);
        mapOperations(requestQueries, application.getMutations(), mapping);
        mapOperations(requestQueries, application.getSubscriptions(), mapping);

        final IdentifyGraphQLOperationOutput output = new IdentifyGraphQLOperationOutput(application, mapping);
        return createServiceResult(output);
        */
        return null;
    }

    private void mapOperations(final List<GraphQLRequestQuery> requestQueries,
                               final List<? extends GraphQLOperation> operations,
                               final Map<GraphQLRequestQuery, GraphQLOperation> mapping){
        for(GraphQLRequestQuery requestQuery : requestQueries){
            for(GraphQLOperation operation : operations){
                if(requestQuery.getOperationName().equals(operation.getName())){
                    mapping.put(requestQuery, operation);
                    break;
                }
            }
        }
    }
}
