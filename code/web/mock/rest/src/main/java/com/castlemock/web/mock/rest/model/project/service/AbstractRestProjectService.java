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
import com.castlemock.core.mock.rest.model.project.dto.RestApplicationDto;
import com.castlemock.core.mock.rest.model.project.dto.RestMethodDto;
import com.castlemock.core.mock.rest.model.project.dto.RestProjectDto;
import com.castlemock.core.mock.rest.model.project.dto.RestResourceDto;
import com.castlemock.web.basis.model.AbstractService;
import com.castlemock.web.mock.rest.model.project.repository.RestProjectRepository;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractRestProjectService extends AbstractService<RestProject, RestProjectDto, String, RestProjectRepository> {

    protected static final String SLASH = "/";
    protected static final String START_BRACKET = "{";
    protected static final String END_BRACKET = "}";




    /**
     * Updates a project with new information
     * @param restProjectId The id of the project that will be updated
     * @param updatedProject The updated version of the project
     * @return The updated version project
     */
    @Override
    public RestProjectDto update(final String restProjectId, final RestProjectDto updatedProject){
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
     * The method provides the functionality to compare two sets of REST resource URI parts.
     * @param restResourceUriParts THe first set of resource URI parts
     * @param otherRestResourceUriParts The second set of resource URI parts
     * @return True if the provided URIs are matching. False otherwise
     */
    protected boolean compareRestResourceUri(final String[] restResourceUriParts, final String[] otherRestResourceUriParts){
        if(restResourceUriParts.length != otherRestResourceUriParts.length){
            return false;
        }

        for(int index = 0; index < restResourceUriParts.length; index++){
            final String restResourceUriPart = restResourceUriParts[index];
            final String otherRestResourceUriPart = otherRestResourceUriParts[index];

            if(restResourceUriPart.startsWith(START_BRACKET) && restResourceUriPart.endsWith(END_BRACKET)){
                continue;
            }

            if(!restResourceUriPart.equalsIgnoreCase(otherRestResourceUriPart)){
                return false;
            }
        }
        return true;
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
