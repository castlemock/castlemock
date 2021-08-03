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

package com.castlemock.service.mock.rest.project;

import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.repository.rest.project.RestApplicationRepository;
import com.castlemock.repository.rest.project.RestMethodRepository;
import com.castlemock.repository.rest.project.RestMockResponseRepository;
import com.castlemock.repository.rest.project.RestProjectRepository;
import com.castlemock.repository.rest.project.RestResourceRepository;
import com.castlemock.service.core.AbstractService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

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

        applications.stream()
                .map(RestApplication::getId)
                .forEach(this::deleteApplication);

        return this.repository.delete(projectId);
    }

    protected RestApplication deleteApplication(final String applicationId){
        final List<RestResource> resources = this.resourceRepository.findWithApplicationId(applicationId);

        resources.stream()
                .map(RestResource::getId)
                .forEach(this::deleteResource);

        return this.applicationRepository.delete(applicationId);
    }

    protected RestResource deleteResource(final String resourceId){
        final List<RestMethod> methods = this.methodRepository.findWithResourceId(resourceId);

        methods.stream()
                .map(RestMethod::getId)
                .forEach(this::deleteMethod);
        return this.resourceRepository.delete(resourceId);
    }

    protected RestMethod deleteMethod(final String methodId){
        final List<RestMockResponse> responses = this.mockResponseRepository.findWithMethodId(methodId);

        responses.stream()
                .map(RestMockResponse::getId)
                .forEach(this::deleteMockResponse);

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
        final Map<RestMethodStatus, Integer> statuses = Arrays.stream(RestMethodStatus.values())
                .collect(toMap(status -> status, status -> 0));
        final List<RestMethod> methods = this.methodRepository.findWithResourceId(restResource.getId());
        methods.stream()
                .map(RestMethod::getStatus)
                .forEach(status -> statuses.put(status, statuses.get(status)+1));

        return statuses;
    }

}
