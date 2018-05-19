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

import com.castlemock.core.mock.rest.model.project.domain.*;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
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
public abstract class AbstractRestProjectService extends AbstractService<RestProject, String, RestProjectRepository> {

    @Autowired
    protected RestApplicationRepository applicationRepository;
    @Autowired
    protected RestResourceRepository resourceRepository;
    @Autowired
    protected RestMethodRepository methodRepository;
    @Autowired
    protected RestMockResponseRepository mockResponseRepository;


    protected RestProject deleteProject(final String projectId){
        final List<RestApplication> applications = this.applicationRepository.findWithProjectId(projectId);

        for(RestApplication application : applications){
            this.deleteApplication(application.getId());
        }

        return this.repository.delete(projectId);
    }

    protected RestApplication deleteApplication(final String applicationId){
        final List<RestResource> resources = this.resourceRepository.findWithApplicationId(applicationId);

        for(RestResource resource : resources){
            this.deleteResource(resource.getId());
        }

        return this.applicationRepository.delete(applicationId);
    }

    protected RestResource deleteResource(final String resourceId){
        final List<RestMethod> methods = this.methodRepository.findWithResourceId(resourceId);

        for(RestMethod method : methods){
            this.deleteMethod(method.getId());
        }

        return this.resourceRepository.delete(resourceId);
    }

    protected RestMethod deleteMethod(final String methodId){
        final List<RestMockResponse> responses = this.mockResponseRepository.findWithMethodId(methodId);

        for(RestMockResponse response : responses){
            this.deleteMockResponse(response.getId());
        }

        return this.methodRepository.delete(methodId);
    }

    protected RestMockResponse deleteMockResponse(final String mockReponseId){
        return this.mockResponseRepository.delete(mockReponseId);
    }


    /**
     * Updates a project with new information
     * @param restProjectId The id of the project that will be updated
     * @param updatedProject The updated version of the project
     * @return The updated version project
     */
    @Override
    protected RestProject update(final String restProjectId, final RestProject updatedProject){
        Preconditions.checkNotNull(restProjectId, "Project id be null");
        Preconditions.checkNotNull(updatedProject, "Project cannot be null");
        Preconditions.checkArgument(!updatedProject.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final RestProject projectWithName = repository.findRestProjectWithName(updatedProject.getName());
        Preconditions.checkArgument(projectWithName == null || projectWithName.getId().equals(restProjectId), "Project name is already taken");
        final RestProject project = find(restProjectId);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return super.save(project);
    }


    /**
     * Count the method statuses for {@link RestApplication}
     * @param restApplication The application which statuses will be counted
     * @return The result of the status count
     */
    protected Map<RestMethodStatus, Integer> getRestMethodStatusCount(final RestApplication restApplication){
        Preconditions.checkNotNull(restApplication, "The REST application cannot be null");
        final Map<RestMethodStatus, Integer> statuses = new HashMap<RestMethodStatus, Integer>();

        for(RestMethodStatus restMethodStatus : RestMethodStatus.values()){
            statuses.put(restMethodStatus, 0);
        }

        final List<String> resourceIds = this.resourceRepository.findIdsWithApplicationId(restApplication.getId());

        for(String resourceId : resourceIds){
            final List<RestMethod> methods = this.methodRepository.findWithResourceId(resourceId);
            for(RestMethod restMethod : methods){
                RestMethodStatus restMethodStatus = restMethod.getStatus();
                statuses.put(restMethodStatus, statuses.get(restMethodStatus)+1);
            }

        }
        return statuses;
    }

    /**
     * Count the method statuses for a {@link RestResource}
     * @param restResource The resource which statuses will be counted
     * @return The result of the status count
     */
    protected Map<RestMethodStatus, Integer> getRestMethodStatusCount(final RestResource restResource){
        Preconditions.checkNotNull(restResource, "The REST resource cannot be null");
        final Map<RestMethodStatus, Integer> statuses = new HashMap<RestMethodStatus, Integer>();

        for(RestMethodStatus restMethodStatus : RestMethodStatus.values()){
            statuses.put(restMethodStatus, 0);
        }
        final List<RestMethod> methods = this.methodRepository.findWithResourceId(restResource.getId());
        for(RestMethod restMethod : methods){
            RestMethodStatus restMethodStatus = restMethod.getStatus();
            statuses.put(restMethodStatus, statuses.get(restMethodStatus)+1);
        }
        return statuses;
    }

}
