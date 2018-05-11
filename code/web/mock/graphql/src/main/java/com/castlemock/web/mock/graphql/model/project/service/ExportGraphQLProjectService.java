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
import com.castlemock.core.basis.utility.serializer.ExportContainerSerializer;
import com.castlemock.core.mock.graphql.model.GraphQLExportContainer;
import com.castlemock.core.mock.graphql.model.project.domain.*;
import com.castlemock.core.mock.graphql.model.project.service.message.input.ExportGraphQLProjectInput;
import com.castlemock.core.mock.graphql.model.project.service.message.output.ExportGraphQLProjectOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
@org.springframework.stereotype.Service
public class ExportGraphQLProjectService extends AbstractGraphQLProjectService implements Service<ExportGraphQLProjectInput, ExportGraphQLProjectOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ExportGraphQLProjectOutput> process(final ServiceTask<ExportGraphQLProjectInput> serviceTask) {
        final ExportGraphQLProjectInput input = serviceTask.getInput();
        final GraphQLProject project = repository.findOne(input.getGraphQLProjectId());
        final List<GraphQLApplication> applications = this.applicationRepository.findWithProjectId(input.getGraphQLProjectId());

        final List<GraphQLObjectType> objectTypes = new ArrayList<>();
        final List<GraphQLEnumType> enumTypes = new ArrayList<>();
        final List<GraphQLQuery> queries = new ArrayList<>();
        final List<GraphQLMutation> mutations = new ArrayList<>();
        final List<GraphQLSubscription> subscriptions = new ArrayList<>();



        for(GraphQLApplication application : applications){
            List<GraphQLObjectType> tempObjectTypes = this.objectTypeRepository.findWithApplicationId(application.getId());
            List<GraphQLEnumType> tempEnumTypes = this.enumTypeRepository.findWithApplicationId(application.getId());
            List<GraphQLQuery> tempQueries = this.queryRepository.findWithApplicationId(application.getId());
            List<GraphQLMutation> tempMutations = this.mutationRepository.findWithApplicationId(application.getId());
            List<GraphQLSubscription> tempSubscriptions = this.subscriptionRepository.findWithApplicationId(application.getId());

            objectTypes.addAll(tempObjectTypes);
            enumTypes.addAll(tempEnumTypes);
            queries.addAll(tempQueries);
            mutations.addAll(tempMutations);
            subscriptions.addAll(tempSubscriptions);
        }

        final GraphQLExportContainer exportContainer = new GraphQLExportContainer();
        exportContainer.setProject(project);
        exportContainer.setApplications(applications);
        exportContainer.setObjectTypes(objectTypes);
        exportContainer.setEnumTypes(enumTypes);
        exportContainer.setQueries(queries);
        exportContainer.setMutations(mutations);
        exportContainer.setSubscriptions(subscriptions);

        final String serialized = ExportContainerSerializer.serialize(exportContainer);
        return createServiceResult(new ExportGraphQLProjectOutput(serialized));
    }
}
