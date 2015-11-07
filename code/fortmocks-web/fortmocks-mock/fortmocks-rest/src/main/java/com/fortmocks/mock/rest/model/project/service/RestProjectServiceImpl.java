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

package com.fortmocks.mock.rest.model.project.service;

import com.fortmocks.core.model.TypeIdentifier;
import com.fortmocks.core.model.project.dto.ProjectDto;
import com.fortmocks.mock.rest.model.RestTypeIdentifier;
import com.fortmocks.mock.rest.model.project.*;
import com.fortmocks.mock.rest.model.project.dto.*;
import com.fortmocks.web.core.model.project.service.ProjectServiceFacadeImpl;
import com.fortmocks.web.core.model.project.service.ProjectServiceImpl;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.List;


/**
 * The class is the project operation layer class responsible for communicating
 * with the project repository and managing projects.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class RestProjectServiceImpl extends ProjectServiceImpl<RestProject, RestProjectDto> implements RestProjectService {

    private static final String SLASH = "/";
    private static final String START_BRACKET = "{";
    private static final String END_BRACKET = "}";
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
    public RestResourceDto findRestResource(Long restProjectId, Long restApplicationId, Long restResourceId) {
        final RestResource restResource = findRestResourceByRestProjectIdAndRestApplicationIdAndRestResourceId(restProjectId, restApplicationId, restResourceId);
        return restResource != null ? mapper.map(restResource, RestResourceDto.class) : null;
    }

    @Override
    public RestResourceDto findRestResource(final Long restProjectId, final Long restApplicationId, final String restResourceUri) {
        Preconditions.checkNotNull(restResourceUri, "The REST resource URI cannot be null");
        final RestApplication restApplication = findRestApplicationByRestProjectIdAndRestApplicationId(restProjectId, restApplicationId);
        for(RestResource restResource : restApplication.getRestResources()){
            if(restResourceUri.equalsIgnoreCase(restResource.getUri())){
                return mapper.map(restResource, RestResourceDto.class);
            }
        }
        return null;
    }

    @Override
    public RestMethodDto findRestMethod(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId) {
        final RestMethod restMethod = findRestMethodByRestProjectIdAndRestApplicationIdAndRestResourceIdAndRestMethodId(restProjectId, restApplicationId, restResourceId, restMethodId);
        return restMethod != null ? mapper.map(restMethod, RestMethodDto.class) : null;
    }

    @Override
    public RestMethodDto findRestMethod(final Long restProjectId, final Long restApplicationId, final String restResourceUri, final RestMethodType restMethodType) {
        Preconditions.checkNotNull(restResourceUri, "The REST resource URI cannot be null");

        final String[] restResourceUriParts = restResourceUri.split(SLASH);


        final RestResource restResource = findRestResourceByRestProjectIdAndRestApplicationIdAndRestResourceUriParts(restProjectId, restApplicationId, restResourceUriParts);
        if(restResource != null){
            for(RestMethod restMethod : restResource.getRestMethods()){
                if(restMethodType.equals(restMethod.getRestMethodType())) {
                    return mapper.map(restMethod, RestMethodDto.class);
                }
            }
        }
        return null;

    }

    /**
     * The method is responsible for updating the current response sequence index for
     * a specific method
     * @param restMethodId The id of the method that will be updated
     * @param currentResponseSequenceIndex The new current response sequence index
     * @see RestMethod
     * @see RestMethodDto
     */
    @Override
    public void updateCurrentResponseSequenceIndex(final Long restMethodId, final Integer currentResponseSequenceIndex){
        final RestMethod restMethod = findRestMethodByRestMethodId(restMethodId);
        final Long soapProjectId = findRestProjectIdForRestMethod(restMethodId);
        restMethod.setCurrentResponseSequenceIndex(currentResponseSequenceIndex);
        save(soapProjectId);
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
     * This is used when the {@link ProjectServiceFacadeImpl} needs
     * to manage base project class, but wants to be able to convert it into a specific subclass, for example when
     * creating or updating a project instance.
     * @param projectDto The project dto instance that will be converted into a project dto subclass
     * @return The converted project dto subclass
     * @throws NullPointerException Throws NullPointerException in case if the provided project dto instance is null.
     * @see com.fortmocks.core.model.project.service.ProjectServiceFacade
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

    @Override
    public RestResourceDto saveRestResource(final Long restProjectId, final Long restApplicationId, final RestResourceDto restResourceDto) {
        Preconditions.checkNotNull(restResourceDto, "REST resource cannot be null");
        final RestApplication restApplication = findRestApplicationByRestProjectIdAndRestApplicationId(restProjectId, restApplicationId);
        final Long restResourceIdId = getNextRestResourceId();
        restResourceDto.setId(restResourceIdId);
        final RestResource restResource = mapper.map(restResourceDto, RestResource.class);
        restApplication.getRestResources().add(restResource);
        save(restProjectId);
        return restResourceDto;
    }

    @Override
    public void deleteRestResource(final Long restProjectId, final Long restApplicationId, final Long restResourceId) {
        final RestApplication restApplication = findRestApplicationByRestProjectIdAndRestApplicationId(restProjectId, restApplicationId);
        final RestResource restResource = findRestResourceByRestProjectIdAndRestApplicationIdAndRestResourceId(restProjectId, restApplicationId, restResourceId);
        restApplication.getRestResources().remove(restResource);
        save(restProjectId);
    }

    @Override
    public void deleteRestResources(Long restProjectId, Long restApplicationId, List<RestResourceDto> restResources) {
        for(final RestResourceDto restResource : restResources){
            deleteRestResource(restProjectId, restApplicationId, restResource.getId());
        }
    }

    @Override
    public void updateRestResource(final Long restProjectId, final Long restApplicationId, final Long restResourceId, final RestResourceDto restResourceDto) {
        Preconditions.checkNotNull(restResourceDto, "REST resource cannot be null");
        final RestResource restResource = findRestResourceByRestProjectIdAndRestApplicationIdAndRestResourceId(restProjectId, restApplicationId, restResourceId);
        restResource.setName(restResourceDto.getName());
        restResource.setUri(restResourceDto.getUri());
        save(restProjectId);
    }

    @Override
    public RestMethodDto saveRestMethod(final Long restProjectId, final Long restApplicationId, final Long restResourceId, final RestMethodDto restMethodDto) {
        Preconditions.checkNotNull(restMethodDto, "REST method cannot be null");

        if(restMethodDto.getRestMethodStatus() == null){
            restMethodDto.setRestMethodStatus(RestMethodStatus.MOCKED);
        }
        if(restMethodDto.getRestResponseStrategy() == null){
            restMethodDto.setRestResponseStrategy(RestResponseStrategy.RANDOM);
        }

        final RestResource restResource = findRestResourceByRestProjectIdAndRestApplicationIdAndRestResourceId(restProjectId, restApplicationId, restResourceId);
        final Long restMethodId = getNextRestMethodId();
        restMethodDto.setId(restMethodId);
        final RestMethod restMethod = mapper.map(restMethodDto, RestMethod.class);
        restResource.getRestMethods().add(restMethod);
        save(restProjectId);
        return restMethodDto;
    }

    @Override
    public void deleteRestMethod(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId) {
        final RestResource restResource = findRestResourceByRestProjectIdAndRestApplicationIdAndRestResourceId(restProjectId, restApplicationId, restResourceId);
        final RestMethod restMethod = findRestMethodByRestProjectIdAndRestApplicationIdAndRestResourceIdAndRestMethodId(restProjectId, restApplicationId, restResourceId, restMethodId);
        restResource.getRestMethods().remove(restMethod);
        save(restProjectId);
    }

    @Override
    public void deleteRestMethods(Long restProjectId, Long restApplicationId, Long restResourceId, List<RestMethodDto> restMethods) {
        for(final RestMethodDto restMethod : restMethods){
            deleteRestMethod(restProjectId, restApplicationId, restResourceId, restMethod.getId());
        }
    }

    @Override
    public void updateRestMethod(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId, RestMethodDto restMethodDto) {
        Preconditions.checkNotNull(restMethodDto, "REST method cannot be null");
        final RestMethod restMethod = findRestMethodByRestProjectIdAndRestApplicationIdAndRestResourceIdAndRestMethodId(restProjectId, restApplicationId, restResourceId, restMethodId);
        restMethod.setName(restMethodDto.getName());
        restMethod.setRestMethodType(restMethodDto.getRestMethodType());
        restMethod.setRestResponseStrategy(restMethodDto.getRestResponseStrategy());
        restMethod.setRestMethodStatus(restMethodDto.getRestMethodStatus());
        restMethod.setForwardedEndpoint(restMethodDto.getForwardedEndpoint());
        save(restProjectId);
    }

    @Override
    public void saveRestMockResponse(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId, RestMockResponseDto restMockResponseDto) {
        Preconditions.checkNotNull(restMockResponseDto, "Mock response cannot be null");
        final RestMethod restMethod = findRestMethodByRestProjectIdAndRestApplicationIdAndRestResourceIdAndRestMethodId(restProjectId, restApplicationId, restResourceId, restMethodId);
        final RestMockResponse restMockResponse = mapper.map(restMockResponseDto, RestMockResponse.class);
        final Long restMockResponseId = getNextRestMockResponseId();
        restMockResponse.setId(restMockResponseId);
        restMethod.getRestMockResponses().add(restMockResponse);
        save(restProjectId);
    }

    @Override
    public RestMockResponseDto findRestMockResponse(final Long restProjectId, final Long restApplicationId, final Long restResourceId, final Long restMethodId, final Long restMockResponseId) {
        final RestMockResponse restMockResponse = findRestMockResponseByRestProjectIdAndRestApplicationIdAndRestResourceIdAndRestMethodIdAndRestMockResponseId(restProjectId, restApplicationId, restResourceId, restMethodId, restMockResponseId);
        return restMockResponse != null ? mapper.map(restMockResponse, RestMockResponseDto.class) : null;
    }

    @Override
    public void updateRestMockResponse(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId, Long restMockResponseId, RestMockResponseDto updatedRestMockResponseDto) {
        Preconditions.checkNotNull(updatedRestMockResponseDto, "REST mock response cannot be null");
        final RestMockResponse restMockResponse = findRestMockResponseByRestProjectIdAndRestApplicationIdAndRestResourceIdAndRestMethodIdAndRestMockResponseId(restProjectId, restApplicationId, restResourceId, restMethodId, restMockResponseId);
        restMockResponse.setName(updatedRestMockResponseDto.getName());
        restMockResponse.setBody(updatedRestMockResponseDto.getBody());
        restMockResponse.setHttpResponseCode(updatedRestMockResponseDto.getHttpResponseCode());
        save(restProjectId);
    }

    @Override
    public void deleteRestMockResponse(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId, Long restMockResponseId) {
        final RestMockResponse restMockResponse = findRestMockResponseByRestProjectIdAndRestApplicationIdAndRestResourceIdAndRestMethodIdAndRestMockResponseId(restProjectId, restApplicationId, restResourceId, restMethodId, restMockResponseId);
        final RestMethod restMethod = findRestMethodByRestProjectIdAndRestApplicationIdAndRestResourceIdAndRestMethodId(restProjectId, restApplicationId, restResourceId, restMethodId);
        restMethod.getRestMockResponses().remove(restMockResponse);
        save(restProjectId);
    }

    @Override
    public void deleteRestMockResponses(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId, List<RestMockResponseDto> restMockResponses) {
        for(final RestMockResponseDto restMockResponse : restMockResponses){
            deleteRestMockResponse(restProjectId, restApplicationId, restResourceId, restMethodId, restMockResponse.getId());
        }
    }

    @Override
    public void updateStatus(final Long restProjectId, final Long restApplicationId, final Long restResourceId, final Long restMethodId, final Long restMockResponseId, final RestMockResponseStatus status) {
        final RestMockResponse restMockResponse = findRestMockResponseByRestProjectIdAndRestApplicationIdAndRestResourceIdAndRestMethodIdAndRestMockResponseId(restProjectId, restApplicationId, restResourceId, restMethodId, restMockResponseId);
        restMockResponse.setRestMockResponseStatus(status);
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
        final RestProject restProject = findOneType(restProjectId);
        for(RestApplication restApplication : restProject.getRestApplications()){
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

    private RestMockResponse findRestMockResponseByRestProjectIdAndRestApplicationIdAndRestResourceIdAndRestMethodIdAndRestMockResponseId(final Long restProjectId, final Long restApplicationId, final Long restResourceId, final Long restMethodId, final Long restMockResponseId){
        Preconditions.checkNotNull(restMockResponseId, "Mock response id cannot be null");
        final RestMethod restMethod = findRestMethodByRestProjectIdAndRestApplicationIdAndRestResourceIdAndRestMethodId(restProjectId, restApplicationId, restResourceId, restMethodId);
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
    private RestResource findRestResourceByRestProjectIdAndRestApplicationIdAndRestResourceUriParts(final Long restProjectId, final Long restApplicationId, final String[] otherRestResourceUriParts) {
        final RestApplication restApplication = findRestApplicationByRestProjectIdAndRestApplicationId(restProjectId, restApplicationId);

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
    private boolean compareRestResourceUri(final String[] restResourceUriParts, final String[] otherRestResourceUriParts){
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
    private RestMethod findRestMethodByRestMethodId(final Long restMethodId) {
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
    private Long findRestProjectIdForRestMethod(final Long restMethodId) {
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
