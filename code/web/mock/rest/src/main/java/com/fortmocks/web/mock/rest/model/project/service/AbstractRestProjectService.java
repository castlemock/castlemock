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

package com.fortmocks.web.mock.rest.model.project.service;

import com.fortmocks.core.mock.rest.model.project.domain.*;
import com.fortmocks.core.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.web.basis.model.AbstractService;
import com.google.common.base.Preconditions;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractRestProjectService extends AbstractService<RestProject, RestProjectDto, String> {

    protected static final String SLASH = "/";
    protected static final String START_BRACKET = "{";
    protected static final String END_BRACKET = "}";


    /**
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     */
    public RestProjectDto findRestProject(final String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");
        for(RestProject restProject : findAllTypes()){
            if(restProject.getName().equalsIgnoreCase(name)) {
                return toDto(restProject);
            }
        }
        return null;
    }

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
        final RestProjectDto projectWithNameDto = findRestProject(updatedProject.getName());
        Preconditions.checkArgument(projectWithNameDto == null || projectWithNameDto.getId().equals(restProjectId), "Project name is already taken");
        final RestProjectDto project = find(restProjectId);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return super.save(project);
    }

    protected RestApplication findRestApplicationType(final String restProjectId, final String restApplicationId) {
        Preconditions.checkNotNull(restProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(restApplicationId, "Application id cannot be null");
        final RestProject restProject = findType(restProjectId);
        for(RestApplication restApplication : restProject.getRestApplications()){
            if(restApplication.getId().equals(restApplicationId)){
                return restApplication;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST application with id " + restApplicationId);
    }

    protected RestResource findRestResourceType(final String restProjectId, final String restApplicationId, final String restResourceId){
        Preconditions.checkNotNull(restResourceId, "Resource id cannot be null");
        final RestApplication restApplication = findRestApplicationType(restProjectId, restApplicationId);
        for(RestResource restResource : restApplication.getRestResources()){
            if(restResource.getId().equals(restResourceId)){
                return restResource;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST resource with id " + restResourceId);
    }

    protected RestMethod findRestMethodType(final String restProjectId, final String restApplicationId, final String restResourceId, final String restMethodId){
        Preconditions.checkNotNull(restMethodId, "Method id cannot be null");
        final RestResource restResource = findRestResourceType(restProjectId, restApplicationId, restResourceId);
        for(RestMethod restMethod : restResource.getRestMethods()){
            if(restMethod.getId().equals(restMethodId)){
                return restMethod;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST method with id " + restMethodId);
    }

    protected RestMockResponse findRestMockResponseType(final String restProjectId, final String restApplicationId, final String restResourceId, final String restMethodId, final String restMockResponseId){
        Preconditions.checkNotNull(restMockResponseId, "Mock response id cannot be null");
        final RestMethod restMethod = findRestMethodType(restProjectId, restApplicationId, restResourceId, restMethodId);
        for(RestMockResponse restMockResponse : restMethod.getRestMockResponses()) {
            if(restMockResponse.getId().equals(restMockResponseId)){
                return restMockResponse;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST mock response with id " + restMockResponseId);
    }


    /**
     * Find a REST resource with a project id, application id and a set of resource parts
     * @param restProjectId The id of the project that the resource belongs to
     * @param restApplicationId The id of the application that the resource belongs to
     * @param otherRestResourceUriParts The set of resources that will be used to identify the REST resource
     * @return A REST resource that matches the search criteria. Null otherwise
     */
    protected RestResource findRestResourceType(final String restProjectId, final String restApplicationId, final String[] otherRestResourceUriParts) {
        final RestApplication restApplication = findRestApplicationType(restProjectId, restApplicationId);

        for(RestResource restResource : restApplication.getRestResources()){
            final String[] restResourceUriParts = restResource.getUri().split(SLASH);

            if(compareRestResourceUri(restResourceUriParts, otherRestResourceUriParts)){
                return restResource;
            }
        }

        return null;
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
     * Finds a method with the provided method id
     * @param restMethodId The id of the method that should be retrieved
     * @return A method with the provided id. Null will be returned if no method has the matching value
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching REST method was found
     * @see RestMethod
     * @see RestMethodDto
     */
    protected RestMethod findRestMethodByRestMethodId(final String restMethodId) {
        Preconditions.checkNotNull(restMethodId, "REST method id cannot be null");
        for(RestProject restProject : findAllTypes()){
            for(RestApplication restApplication : restProject.getRestApplications()){
                for(RestResource restResource : restApplication.getRestResources()){
                    for(RestMethod restMethod : restResource.getRestMethods())
                        if(restMethod.getId().equals(restMethodId)){
                            return restMethod;
                        }
                }
            }
        }
        throw new IllegalArgumentException("Unable to find a REST method with id " + restMethodId);
    }

    /**
     * The method provides the functionality to find a REST method with a specific id
     * @param restMethodId The identifier for the REST method
     * @return A REST method with a matching identifier
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching REST method was found
     * @see RestMethod
     * @see RestMethodDto
     */
    protected String findRestProjectIdForRestMethod(final String restMethodId) {
        Preconditions.checkNotNull(restMethodId, "Method id cannot be null");
        for(RestProject restProject : findAllTypes()){
            for(RestApplication restApplication : restProject.getRestApplications()){
                for(RestResource restResource : restApplication.getRestResources()){
                    for(RestMethod restMethod : restResource.getRestMethods())
                        if(restMethod.getId().equals(restMethodId)){
                            return restProject.getId();
                        }
                }
            }
        }
        throw new IllegalArgumentException("Unable to find an method with id " + restMethodId);
    }

}
