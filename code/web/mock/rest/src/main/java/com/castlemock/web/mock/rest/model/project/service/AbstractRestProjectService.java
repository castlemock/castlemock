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

package com.castlemock.web.mock.rest.model.project.service;

import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.dto.*;
import com.castlemock.web.basis.model.AbstractService;
import com.castlemock.web.mock.rest.model.project.repository.*;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractRestProjectService extends AbstractService<RestProject, RestProjectDto, String, RestProjectRepository> {

    @Autowired
    protected RestApplicationRepository applicationRepository;
    @Autowired
    protected RestResourceRepository resourceRepository;
    @Autowired
    protected RestMethodRepository methodRepository;
    @Autowired
    protected RestMockResponseRepository mockResponseRepository;


    protected RestProjectDto deleteProject(final String projectId){
        final List<RestApplicationDto> applications = this.applicationRepository.findWithProjectId(projectId);

        for(RestApplicationDto application : applications){
            this.deleteApplication(application.getId());
        }

        return this.repository.delete(projectId);
    }

    protected RestApplicationDto deleteApplication(final String applicationId){
        final List<RestResourceDto> resources = this.resourceRepository.findWithApplicationId(applicationId);

        for(RestResourceDto resource : resources){
            this.deleteResource(resource.getId());
        }

        return this.applicationRepository.delete(applicationId);
    }

    protected RestResourceDto deleteResource(final String resourceId){
        final List<RestMethodDto> methods = this.methodRepository.findWithResourceId(resourceId);

        for(RestMethodDto method : methods){
            this.deleteMethod(method.getId());
        }

        return this.resourceRepository.delete(resourceId);
    }

    protected RestMethodDto deleteMethod(final String methodId){
        final List<RestMockResponseDto> responses = this.mockResponseRepository.findWithMethodId(methodId);

        for(RestMockResponseDto response : responses){
            this.deleteMockResponse(response.getId());
        }

        return this.methodRepository.delete(methodId);
    }

    protected RestMockResponseDto deleteMockResponse(final String mockReponseId){
        return this.mockResponseRepository.delete(mockReponseId);
    }


    /**
     * Updates a project with new information
     * @param restProjectId The id of the project that will be updated
     * @param updatedProject The updated version of the project
     * @return The updated version project
     */
    @Override
    protected RestProjectDto update(final String restProjectId, final RestProjectDto updatedProject){
        Preconditions.checkNotNull(restProjectId, "Project id be null");
        Preconditions.checkNotNull(updatedProject, "Project cannot be null");
        Preconditions.checkArgument(!updatedProject.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final RestProjectDto projectWithNameDto = repository.findRestProjectWithName(updatedProject.getName());
        Preconditions.checkArgument(projectWithNameDto == null || projectWithNameDto.getId().equals(restProjectId), "Project name is already taken");
        final RestProjectDto project = find(restProjectId);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return super.save(project);
    }


    /**
     * Count the method statuses for {@link RestApplicationDto}
     * @param restApplication The application which statuses will be counted
     * @return The result of the status count
     */
    protected Map<RestMethodStatus, Integer> getRestMethodStatusCount(final RestApplicationDto restApplication){
        Preconditions.checkNotNull(restApplication, "The REST application cannot be null");
        final Map<RestMethodStatus, Integer> statuses = new HashMap<RestMethodStatus, Integer>();

        for(RestMethodStatus restMethodStatus : RestMethodStatus.values()){
            statuses.put(restMethodStatus, 0);
        }
        for(RestResourceDto restResource : restApplication.getResources()){
            for(RestMethodDto restMethod : restResource.getMethods()){
                RestMethodStatus restMethodStatus = restMethod.getStatus();
                statuses.put(restMethodStatus, statuses.get(restMethodStatus)+1);
            }

        }
        return statuses;
    }

    /**
     * Count the method statuses for a {@link RestResourceDto}
     * @param restResource The resource which statuses will be counted
     * @return The result of the status count
     */
    protected Map<RestMethodStatus, Integer> getRestMethodStatusCount(final RestResourceDto restResource){
        Preconditions.checkNotNull(restResource, "The REST resource cannot be null");
        final Map<RestMethodStatus, Integer> statuses = new HashMap<RestMethodStatus, Integer>();

        for(RestMethodStatus restMethodStatus : RestMethodStatus.values()){
            statuses.put(restMethodStatus, 0);
        }
        for(RestMethodDto restMethod : restResource.getMethods()){
            RestMethodStatus restMethodStatus = restMethod.getStatus();
            statuses.put(restMethodStatus, statuses.get(restMethodStatus)+1);
        }
        return statuses;
    }

}
