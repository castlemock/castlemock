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
import com.castlemock.core.basis.utility.serializer.ExportContainerSerializer;
import com.castlemock.core.mock.graphql.model.GraphQLExportContainer;
import com.castlemock.core.mock.graphql.model.project.domain.*;
import com.castlemock.core.mock.graphql.model.project.service.message.input.ImportGraphQLProjectInput;
import com.castlemock.core.mock.graphql.model.project.service.message.output.ImportGraphQLProjectOutput;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
@org.springframework.stereotype.Service
public class ImportGraphQLProjectService extends AbstractGraphQLProjectService implements Service<ImportGraphQLProjectInput, ImportGraphQLProjectOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ImportGraphQLProjectOutput> process(final ServiceTask<ImportGraphQLProjectInput> serviceTask) {
        final ImportGraphQLProjectInput input = serviceTask.getInput();

        GraphQLExportContainer exportContainer = ExportContainerSerializer.deserialize(input.getProjectRaw(), GraphQLExportContainer.class);

        GraphQLProject project = exportContainer.getProject();

        if(this.repository.exists(project.getId())){
            throw new IllegalArgumentException("A project with the following key already exists: " + project.getId());
        }

        this.repository.save(project);

        for(GraphQLApplication application : exportContainer.getApplications()){
            if(this.applicationRepository.exists(application.getId())){
                throw new IllegalArgumentException("An application with the following key already exists: " + application.getId());
            }

            this.applicationRepository.save(application);
        }

        for(GraphQLQuery query : exportContainer.getQueries()){
            if(this.queryRepository.exists(query.getId())){
                throw new IllegalArgumentException("A query with the following key already exists: " + query.getId());
            }

            this.queryRepository.save(query);
        }

        for(GraphQLSubscription subscription : exportContainer.getSubscriptions()){
            if(this.subscriptionRepository.exists(subscription.getId())){
                throw new IllegalArgumentException("A subscription with the following key already exists: " + subscription.getId());
            }

            this.subscriptionRepository.save(subscription);
        }

        for(GraphQLMutation mutation : exportContainer.getMutations()){
            if(this.mutationRepository.exists(mutation.getId())){
                throw new IllegalArgumentException("A mutation with the following key already exists: " + mutation.getId());
            }

            this.mutationRepository.save(mutation);
        }

        for(GraphQLObjectType objectType : exportContainer.getObjectTypes()){
            if(this.objectTypeRepository.exists(objectType.getId())){
                throw new IllegalArgumentException("An object with the following key already exists: " + objectType.getId());
            }

            this.objectTypeRepository.save(objectType);
        }

        for(GraphQLEnumType enumType : exportContainer.getEnumTypes()){
            if(this.enumTypeRepository.exists(enumType.getId())){
                throw new IllegalArgumentException("An enum with the following key already exists: " + enumType.getId());
            }

            this.enumTypeRepository.save(enumType);
        }

        return createServiceResult(new ImportGraphQLProjectOutput(project));
    }
}
