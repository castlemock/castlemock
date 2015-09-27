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

package com.fortmocks.war.mock.rest.model.project.service;

import com.fortmocks.core.base.model.TypeIdentifier;
import com.fortmocks.core.base.model.project.dto.ProjectDto;
import com.fortmocks.core.mock.rest.model.RestTypeIdentifier;
import com.fortmocks.core.mock.rest.model.project.*;
import com.fortmocks.core.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.war.base.model.project.service.ProjectServiceImpl;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * The class is the project operation layer class responsible for communicating
 * with the project repository and managing projects.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class RestProjectServiceImpl extends ProjectServiceImpl<RestProject, RestProjectDto> implements RestProjectService {

    private static final RestTypeIdentifier REST_TYPE_IDENTIFIER = new RestTypeIdentifier();

    /**
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     */
    @Override
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

    @Override
    public RestApplicationDto findRestApplication(final Long restProjectId, final Long restApplicationId) {
        final RestApplication restApplication = findRestApplicationByRestProjectIdAndRestApplicationId(restProjectId, restApplicationId);
        return restApplication != null ? mapper.map(restApplication, RestApplicationDto.class) : null;
    }

    @Override
    public RestApplicationDto saveRestApplication(final Long restProjectId, final RestApplicationDto restApplicationDto) {
        final RestProject restProject = findOneType(restProjectId);
        final Long restApplicationId = getNextRestApplicationId();
        restApplicationDto.setId(restApplicationId);

        final RestApplication restApplication = mapper.map(restApplicationDto, RestApplication.class);
        restProject.getRestApplications().add(restApplication);
        save(restProjectId);
        return restApplicationDto;
    }

    /**
     * Updates a project with new information
     * @param restProjectId The id of the project that will be updated
     * @param updatedProject The updated version of the project
     * @return The updated version project
     */
    @Override
    public RestProjectDto update(final Long restProjectId, final RestProjectDto updatedProject){
        Preconditions.checkNotNull(restProjectId, "Project id be null");
        Preconditions.checkArgument(restProjectId >= 0, "Project id cannot be negative");
        Preconditions.checkNotNull(updatedProject, "Project cannot be null");
        Preconditions.checkArgument(!updatedProject.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final RestProjectDto projectWithNameDto = findRestProject(updatedProject.getName());
        Preconditions.checkArgument(projectWithNameDto == null || projectWithNameDto.getId().equals(restProjectId), "Project name is already taken");
        final RestProjectDto project = findOne(restProjectId);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return super.save(project);
    }



    /**
     * Returns the REST type identifier.
     * @return The REST identifier
     */
    @Override
    public TypeIdentifier getTypeIdentifier() {
        return REST_TYPE_IDENTIFIER;
    }

    /**
     * The method is responsible for converting a project dto instance into a project dto subclass.
     * This is used when the {@link com.fortmocks.war.base.model.project.service.ProjectServiceFacadeImpl} needs
     * to manage base project class, but wants to be able to convert it into a specific subclass, for example when
     * creating or updating a project instance.
     * @param projectDto The project dto instance that will be converted into a project dto subclass
     * @return The converted project dto subclass
     * @throws NullPointerException Throws NullPointerException in case if the provided project dto instance is null.
     * @see com.fortmocks.war.base.model.project.service.ProjectServiceFacade
     */
    @Override
    public RestProjectDto convertType(ProjectDto projectDto) {
        Preconditions.checkNotNull(projectDto, "Project cannot be null");
        return new RestProjectDto(projectDto);
    }

    /**
     * Deletes an application from a specific REST project. Both the project and the application are identified with provided
     * identifiers.
     * @param restProjectId The identifier of the REST project that the port belongs to
     * @param restApplicationId The identifier of the application that will be removed
     */
    @Override
    public void deleteRestApplication(final Long restProjectId, final Long restApplicationId) {
        final RestProject restProject = findOneType(restProjectId);
        final RestApplication restApplication = findRestApplicationByRestProjectIdAndRestApplicationId(restProjectId, restApplicationId);
        restProject.getRestApplications().remove(restApplication);
        save(restProjectId);
    }

    /**
     * Takes a list of applications and delete them from a project
     * @param restProjectId The id of the project that the application belong to
     * @param restApplications The list of applications that will be deleted
     */
    @Override
    public void deleteRestApplications(final Long restProjectId, List<RestApplicationDto> restApplications) {
        for(final RestApplicationDto restApplication : restApplications){
            deleteRestApplication(restProjectId, restApplication.getId());
        }
    }

    /**
     * The method provides the functionality to update an existing application
     * @param restProjectId The id of the project that the application belongs to
     * @param restApplicationId The id of the application that will be updated
     * @param restApplicationDto The new application values
     */
    @Override
    public void updateRestApplication(final Long restProjectId, final Long restApplicationId, final RestApplicationDto restApplicationDto) {
        Preconditions.checkNotNull(restApplicationDto, "REST application cannot be null");
        final RestApplication restApplication = findRestApplicationByRestProjectIdAndRestApplicationId(restProjectId, restApplicationId);
        restApplication.setName(restApplicationDto.getName());
        save(restProjectId);
    }


    /**
     * The method calculates the next REST application id
     * @return The new generated REST application id
     */
    private Long getNextRestApplicationId(){
        Long nextRestApplicationId = 0L;
        for(RestProject restProject : findAllTypes()){
            for(RestApplication restApplication : restProject.getRestApplications()){
                if(restApplication.getId() >= nextRestApplicationId){
                    nextRestApplicationId = restApplication.getId() + 1;
                }
            }
        }
        return nextRestApplicationId;
    }

    /**
     * The method calculates the next REST resource id
     * @return The new generated REST resource id
     */
    private Long getNextRestResourceId(){
        Long nextRestResourceId = 0L;
        for(RestProject restProject : findAllTypes()){
            for(RestApplication restApplication : restProject.getRestApplications()){
                for(RestResource restResource : restApplication.getRestResources()){
                    if(restResource.getId() >= nextRestResourceId){
                        nextRestResourceId = restResource.getId() + 1;
                    }
                }
            }
        }
        return nextRestResourceId;
    }

    /**
     * The method calculates the next REST method id
     * @return The new generated REST method id
     */
    private Long getNextRestMethodId(){
        Long nextRestMethodId = 0L;
        for(RestProject restProject : findAllTypes()){
            for(RestApplication restApplication : restProject.getRestApplications()){
                for(RestResource restResource : restApplication.getRestResources()){
                    for(RestMethod restMethod : restResource.getRestMethods()){
                        if(restMethod.getId() >= nextRestMethodId){
                            nextRestMethodId = restMethod.getId() + 1;
                        }
                    }
                }
            }
        }
        return nextRestMethodId;
    }

    /**
     * The method calculates the next REST mock response id
     * @return The new generated REST mock response id
     */
    private Long getNextRestMockResponseId(){
        Long nextRestMockResponseId = 0L;
        for(RestProject restProject : findAllTypes()){
            for(RestApplication restApplication : restProject.getRestApplications()){
                for(RestResource restResource : restApplication.getRestResources()){
                    for(RestMethod restMethod : restResource.getRestMethods()){
                        for(RestMockResponse restMockResponse : restMethod.getRestMockResponses()) {
                            if (restMockResponse.getId() >= nextRestMockResponseId) {
                                nextRestMockResponseId = restMockResponse.getId() + 1;
                            }
                        }
                    }
                }
            }
        }
        return nextRestMockResponseId;
    }




    private RestApplication findRestApplicationByRestProjectIdAndRestApplicationId(final Long restProjectId, final Long restApplicationId) {
        Preconditions.checkNotNull(restProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(restApplicationId, "Application id cannot be null");
        final RestProject soapProject = findOneType(restProjectId);
        for(RestApplication restApplication : soapProject.getRestApplications()){
            if(restApplication.getId().equals(restApplicationId)){
                return restApplication;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST application with id " + restApplicationId);
    }

    private RestResource findRestResourceByRestProjectIdAndRestApplicationIdAndRestResourceId(final Long restProjectId, final Long restApplicationId, final Long restResourceId){
        Preconditions.checkNotNull(restResourceId, "Resource id cannot be null");
        final RestApplication restApplication = findRestApplicationByRestProjectIdAndRestApplicationId(restProjectId, restApplicationId);
        for(RestResource restResource : restApplication.getRestResources()){
            if(restResource.getId().equals(restResourceId)){
                return restResource;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST resource with id " + restResourceId);
    }

    private RestMethod findRestMethodByRestProjectIdAndRestApplicationIdAndRestResourceIdAndRestMethodId(final Long restProjectId, final Long restApplicationId, final Long restResourceId, final Long restMethodId){
        Preconditions.checkNotNull(restMethodId, "Method id cannot be null");
        final RestResource restResource = findRestResourceByRestProjectIdAndRestApplicationIdAndRestResourceId(restProjectId, restApplicationId, restResourceId);
        for(RestMethod restMethod : restResource.getRestMethods()){
            if(restMethod.getId().equals(restMethodId)){
                return restMethod;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST method with id " + restMethodId);
    }

    private RestMethod findRestMethodByRestProjectIdAndRestApplicationIdAndRestResourceIdAndRestMethodIdAndMockResponseId(final Long restProjectId, final Long restApplicationId, final Long restResourceId, final Long restMethodId, final Long mockResponseId){
        Preconditions.checkNotNull(mockResponseId, "Mock response id cannot be null");
        final RestMethod restMethod = findRestMethodByRestProjectIdAndRestApplicationIdAndRestResourceIdAndRestMethodId(restProjectId, restApplicationId, restResourceId, restMethodId);
        for(RestMockResponse restMockResponse : restMethod.getRestMockResponses()) {
            if(restMockResponse.getId().equals(mockResponseId)){
                return restMethod;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST mock response with id " + mockResponseId);
    }
}
