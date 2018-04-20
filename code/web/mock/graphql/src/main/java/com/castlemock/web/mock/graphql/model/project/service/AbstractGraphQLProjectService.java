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

import com.castlemock.core.mock.graphql.model.project.domain.GraphQLProject;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLApplicationDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLObjectTypeDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLProjectDto;
import com.castlemock.web.basis.model.AbstractService;
import com.castlemock.web.mock.graphql.model.project.repository.*;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public abstract class AbstractGraphQLProjectService extends AbstractService<GraphQLProject, GraphQLProjectDto, String, GraphQLProjectRepository> {

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

    protected GraphQLProjectDto deleteProject(final String projectId){
        final List<GraphQLApplicationDto> applications = applicationRepository.findWithProjectId(projectId);

        final GraphQLProjectDto project = this.repository.delete(projectId);

        for(GraphQLApplicationDto application : applications){
            this.deleteApplication(application.getId());
        }

        return project;
    }

    protected GraphQLApplicationDto deleteApplication(final String applicationId){
        final GraphQLApplicationDto application = this.applicationRepository.delete(applicationId);

        final List<GraphQLObjectTypeDto> objectTypes = this.objectTypeRepository.findWithApplicationId(applicationId);

        this.queryRepository.deleteWithApplicationId(applicationId);
        this.mutationRepository.deleteWithApplicationId(applicationId);
        this.subscriptionRepository.deleteWithApplicationId(applicationId);
        this.objectTypeRepository.deleteWithApplicationId(applicationId);
        this.enumTypeRepository.deleteWithApplicationId(applicationId);

        for(GraphQLObjectTypeDto objectType : objectTypes){
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
    public GraphQLProjectDto update(final String graphQLProjectId, final GraphQLProjectDto updatedProject){
        Preconditions.checkNotNull(graphQLProjectId, "Project id be null");
        Preconditions.checkNotNull(updatedProject, "Project cannot be null");
        Preconditions.checkArgument(!updatedProject.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final GraphQLProjectDto projectWithNameDto = repository.findGraphQLProjectWithName(updatedProject.getName());
        Preconditions.checkArgument(projectWithNameDto == null || projectWithNameDto.getId().equals(graphQLProjectId), "Project name is already taken");
        final GraphQLProjectDto project = find(graphQLProjectId);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return super.save(project);
    }
    
}
