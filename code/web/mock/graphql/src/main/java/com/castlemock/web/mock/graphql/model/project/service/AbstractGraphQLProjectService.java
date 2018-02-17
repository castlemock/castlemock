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

import com.castlemock.core.mock.graphql.model.project.domain.GraphQLOperationStatus;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLProject;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLOperationDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLProjectDto;
import com.castlemock.web.basis.model.AbstractService;
import com.castlemock.web.mock.graphql.model.project.repository.GraphQLProjectRepository;
import com.google.common.base.Preconditions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public abstract class AbstractGraphQLProjectService extends AbstractService<GraphQLProject, GraphQLProjectDto, String, GraphQLProjectRepository> {

    /**
     * Count the operation statuses
     * @param soapOperations The list of operations, which status will be counted
     * @return The result of the status count
     */
    protected Map<GraphQLOperationStatus, Integer> getGraphQLOperationStatusCount(final List<GraphQLOperationDto> soapOperations){
        Preconditions.checkNotNull(soapOperations, "The operation list cannot be null");
        final Map<GraphQLOperationStatus, Integer> statuses = new HashMap<GraphQLOperationStatus, Integer>();

        for(GraphQLOperationStatus soapOperationStatus : GraphQLOperationStatus.values()){
            statuses.put(soapOperationStatus, 0);
        }
        for(GraphQLOperationDto soapOperation : soapOperations){
            GraphQLOperationStatus soapOperationStatus = soapOperation.getStatus();
            statuses.put(soapOperationStatus, statuses.get(soapOperationStatus)+1);
        }
        return statuses;
    }



    /**
     * The save method saves a project to the database
     * @param project Project that will be saved to the database
     * @return The saved project
     */
    @Override
    public GraphQLProjectDto save(final GraphQLProjectDto project){
        Preconditions.checkNotNull(project, "Project cannot be null");
        Preconditions.checkArgument(!project.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final GraphQLProjectDto projectInDatebase = repository.findGraphQLProjectWithName(project.getName());
        Preconditions.checkArgument(projectInDatebase == null, "Project name is already taken");
        project.setUpdated(new Date());
        project.setCreated(new Date());
        return super.save(project);
    }

    /**
     * Updates a project with new information
     * @param soapProjectId The id of the project that will be updated
     * @param updatedProject The updated version of the project
     * @return The updated version project
     */
    @Override
    public GraphQLProjectDto update(final String soapProjectId, final GraphQLProjectDto updatedProject){
        Preconditions.checkNotNull(soapProjectId, "Project id be null");
        Preconditions.checkNotNull(updatedProject, "Project cannot be null");
        Preconditions.checkArgument(!updatedProject.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final GraphQLProjectDto projectWithNameDto = repository.findGraphQLProjectWithName(updatedProject.getName());
        Preconditions.checkArgument(projectWithNameDto == null || projectWithNameDto.getId().equals(soapProjectId), "Project name is already taken");
        final GraphQLProjectDto project = find(soapProjectId);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return super.save(project);
    }

}
