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

import com.castlemock.core.mock.graphql.model.project.domain.GraphQLProject;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLApplication;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLObjectType;
import com.castlemock.web.basis.service.AbstractService;
import com.castlemock.web.mock.graphql.repository.project.*;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public abstract class AbstractGraphQLProjectService extends AbstractService<GraphQLProject, String, GraphQLProjectRepository> {

    @Autowired
    protected GraphQLApplicationRepository applicationRepository;
    @Autowired
    protected GraphQLQueryRepository queryRepository;
    @Autowired
    protected GraphQLMutationRepository mutationRepository;
    @Autowired
    protected GraphQLSubscriptionRepository subscriptionRepository;
    @Autowired
    protected GraphQLObjectTypeRepository objectTypeRepository;
    @Autowired
    protected GraphQLEnumTypeRepository enumTypeRepository;
    @Autowired
    protected GraphQLAttributeRepository attributeRepository;

    protected GraphQLProject deleteProject(final String projectId){
        final List<GraphQLApplication> applications = applicationRepository.findWithProjectId(projectId);

        final GraphQLProject project = this.repository.delete(projectId);

        for(GraphQLApplication application : applications){
            this.deleteApplication(application.getId());
        }

        return project;
    }

    protected GraphQLApplication deleteApplication(final String applicationId){
        final GraphQLApplication application = this.applicationRepository.delete(applicationId);

        final List<GraphQLObjectType> objectTypes = this.objectTypeRepository.findWithApplicationId(applicationId);

        this.queryRepository.deleteWithApplicationId(applicationId);
        this.mutationRepository.deleteWithApplicationId(applicationId);
        this.subscriptionRepository.deleteWithApplicationId(applicationId);
        this.objectTypeRepository.deleteWithApplicationId(applicationId);
        this.enumTypeRepository.deleteWithApplicationId(applicationId);

        for(GraphQLObjectType objectType : objectTypes){
            this.attributeRepository.deleteWithObjectTypeId(objectType.getId());
        }

        return application;
    }

    /**
     * Updates a project with new information
     * @param graphQLProjectId The id of the project that will be updated
     * @param updatedProject The updated version of the project
     * @return The updated version project
     */
    @Override
    public GraphQLProject update(final String graphQLProjectId, final GraphQLProject updatedProject){
        Preconditions.checkNotNull(graphQLProjectId, "Project id be null");
        Preconditions.checkNotNull(updatedProject, "Project cannot be null");
        Preconditions.checkArgument(!updatedProject.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final GraphQLProject projectWithName = repository.findGraphQLProjectWithName(updatedProject.getName());
        Preconditions.checkArgument(projectWithName == null || projectWithName.getId().equals(graphQLProjectId), "Project name is already taken");
        final GraphQLProject project = find(graphQLProjectId);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return super.save(project);
    }
    
}
