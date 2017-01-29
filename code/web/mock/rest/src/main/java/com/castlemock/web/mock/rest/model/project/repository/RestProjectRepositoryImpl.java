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

package com.castlemock.web.mock.rest.model.project.repository;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.mock.rest.model.project.domain.*;
import com.castlemock.core.mock.rest.model.project.dto.*;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving REST project from the file system. Each REST project is stored as
 * a separate file. The class also contains the directory and the filename extension for the REST project.
 * @author Karl Dahlgren
 * @since 1.0
 * @see RestProjectRepository
 * @see RepositoryImpl
 * @see RestProject
 */
@Repository
public class RestProjectRepositoryImpl extends RepositoryImpl<RestProject, RestProjectDto, String> implements RestProjectRepository {

    @Autowired
    private MessageSource messageSource;

    private static final String SLASH = "/";
    private static final String REST = "rest";
    private static final String PROJECT = "project";
    private static final String APPLICATION = "application";
    private static final String RESOURCE = "resource";
    private static final String METHOD = "method";
    private static final String RESPONSE = "response";
    private static final String COMMA = ", ";
    private static final String REST_TYPE = "REST";

    @Value(value = "${rest.project.file.directory}")
    private String restProjectFileDirectory;
    @Value(value = "${rest.project.file.extension}")
    private String restProjectFileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return restProjectFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return restProjectFileExtension;
    }

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful.
     *
     * The method is responsible to validate the imported types and make certain that all the collections are
     * initialized.
     * @see #initialize
     * @see RestProject
     * @since 1.4
     */
    @Override
    protected void postInitiate() {
        for(RestProject restProject : collection.values()){
            List<RestApplication> restApplications = new CopyOnWriteArrayList<RestApplication>();
            if(restProject.getApplications() != null){
                restApplications.addAll(restProject.getApplications());
            }
            restProject.setApplications(restApplications);

            for(RestApplication restApplication : restProject.getApplications()){
                List<RestResource> restResources = new CopyOnWriteArrayList<RestResource>();
                if(restApplication.getResources() != null){
                    restResources.addAll(restApplication.getResources());
                }
                restApplication.setResources(restResources);

                for(RestResource restResource : restApplication.getResources()){
                    List<RestMethod> restMethods = new CopyOnWriteArrayList<RestMethod>();
                    if(restResource.getMethods() != null){
                        restMethods.addAll(restResource.getMethods());
                    }
                    restResource.setMethods(restMethods);

                    for(RestMethod restMethod : restResource.getMethods()){
                        List<RestMockResponse> restMockResponses = new CopyOnWriteArrayList<RestMockResponse>();
                        if(restMethod.getMockResponses() != null){
                            restMockResponses.addAll(restMethod.getMockResponses());
                        }
                        restMethod.setMockResponses(restMockResponses);

                        for(RestMockResponse restMockResponse : restMethod.getMockResponses()){
                            List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<HttpHeader>();
                            if(restMockResponse.getHttpHeaders() != null){
                                httpHeaders.addAll(restMockResponse.getHttpHeaders());
                            }
                            restMockResponse.setHttpHeaders(httpHeaders);
                        }
                    }
                }

            }
        }
    }


    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param restProject The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     * @see RestProject
     */
    @Override
    protected void checkType(RestProject restProject) {

    }

    /**
     * The save method provides the functionality to save an instance to the file system.
     * @param dto The type that will be saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    @Override
    public RestProjectDto save(final RestProject dto) {
        for(RestApplication restApplication : dto.getApplications()){
            if(restApplication.getId() == null){
                String restApplicationId = generateId();
                restApplication.setId(restApplicationId);
            }
            for(RestResource restResource : restApplication.getResources()){
                if(restResource.getId() == null){
                    String restResourceId = generateId();
                    restResource.setId(restResourceId);
                }
                for(RestMethod restMethod : restResource.getMethods()){
                    if(restMethod.getId() == null){
                        String restMethodId = generateId();
                        restMethod.setId(restMethodId);
                    }
                    for(RestMockResponse restMockResponse : restMethod.getMockResponses()){
                        if(restMockResponse.getId() == null){
                            String restMockResponseId = generateId();
                            restMockResponse.setId(restMockResponseId);
                        }
                    }
                }
            }
        }
        return super.save(dto);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SearchResult> search(SearchQuery query) {
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        for(RestProject restProject : collection.values()){
            List<SearchResult> restProjectSearchResult = searchRestProject(restProject, query);
            searchResults.addAll(restProjectSearchResult);
        }
        return searchResults;
    }

    /*
     * FIND OPERATIONS
     */

    /**
     * Finds a {@link RestProjectDto} with a provided REST project name.
     * @param restProjectName The name of the REST project that will be retrieved.
     * @return A {@link RestProjectDto} that matches the provided name.
     * @see RestProject
     * @see RestProjectDto
     */
    @Override
    public RestProjectDto findRestProjectWithName(final String restProjectName) {
        Preconditions.checkNotNull(restProjectName, "Project name cannot be null");
        Preconditions.checkArgument(!restProjectName.isEmpty(), "Project name cannot be empty");
        for(RestProject restProject : collection.values()){
            if(restProject.getName().equalsIgnoreCase(restProjectName)) {
                return mapper.map(restProject, RestProjectDto.class);
            }
        }
        return null;
    }

    /**
     * Finds a {@link RestApplicationDto} with the provided ids.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @return A {@link RestApplicationDto} that matches the search criteria.
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     */
    @Override
    public RestApplicationDto findRestApplication(final String restProjectId, final String restApplicationId) {
        final RestApplication restApplication = findRestApplicationType(restProjectId, restApplicationId);
        return mapper.map(restApplication, RestApplicationDto.class);
    }

    /**
     * Finds a {@link RestResourceDto} with the provided ids.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @return A {@link RestResourceDto} that matches the search criteria.
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     */
    @Override
    public RestResourceDto findRestResource(final String restProjectId, final String restApplicationId, final String restResourceId) {
        final RestResource restResource = findRestResourceType(restProjectId, restApplicationId, restResourceId);
        return mapper.map(restResource, RestResourceDto.class);
    }

    /**
     * Finds a {@link RestResourceDto} with the provided ids.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod}
     * @return A {@link RestMethod} that matches the search criteria.
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     */
    @Override
    public RestMethodDto findRestMethod(final String restProjectId, final String restApplicationId, final String restResourceId, final String restMethodId) {
        final RestMethod restMethod = findRestMethodType(restProjectId, restApplicationId, restResourceId, restMethodId);
        return mapper.map(restMethod, RestMethodDto.class);
    }

    /**
     * Finds a {@link RestMockResponseDto} with the provided ids.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod}
     * @param restMockResponseId The id of the {@link RestMockResponse}
     * @return A {@link RestMockResponseDto} that matches the search criteria.
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     * @see RestMockResponse
     * @see RestMockResponseDto
     */
    @Override
    public RestMockResponseDto findRestMockResponse(final String restProjectId, final String restApplicationId, final String restResourceId, final String restMethodId, final String restMockResponseId) {
        final RestMockResponse restMockResponse = findRestMockResponseType(restProjectId, restApplicationId, restResourceId, restMethodId, restMockResponseId);
        return mapper.map(restMockResponse, RestMockResponseDto.class);
    }

    /**
     * Finds a {@link RestResourceDto} with a URI
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceUri The URI of a {@link RestResource}
     * @return A {@link RestResourceDto} that matches the search criteria.
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     */
    @Override
    public RestResourceDto findRestResourceByUri(final String restProjectId, final String restApplicationId, final String restResourceUri) {
        RestApplication restApplication = findRestApplicationType(restProjectId, restApplicationId);
        RestResourceDto restResourceDto = null;
        for(RestResource restResource : restApplication.getResources()){
            if(restResourceUri.equalsIgnoreCase(restResource.getUri())){
                restResourceDto = mapper.map(restResource, RestResourceDto.class);
                break;
            }
        }
        return restResourceDto;
    }

    /*
     * SAVE OPERATIONS
     */

    /**
     * The method adds a new {@link RestApplication} to a {@link RestProject} and then saves
     * the {@link RestProject} to the file system.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationDto The dto instance of {@link RestApplication} that will be added to the {@link RestProject}
     * @return The saved {@link RestApplicationDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     */
    @Override
    public RestApplicationDto saveRestApplication(String restProjectId, RestApplicationDto restApplicationDto) {
        RestProject restProject = collection.get(restProjectId);
        RestApplication restApplication = mapper.map(restApplicationDto, RestApplication.class);
        restProject.getApplications().add(restApplication);
        save(restProjectId);
        return mapper.map(restApplication, RestApplicationDto.class);
    }

    /**
     * The method adds a new {@link RestResource} to a {@link RestApplication} and then saves
     * the {@link RestProject} to the file system.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceDto The dto instance of {@link RestApplication} that will be added to the {@link RestApplication}
     * @return The saved {@link RestResourceDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     */
    @Override
    public RestResourceDto saveRestResource(String restProjectId, String restApplicationId, RestResourceDto restResourceDto) {
        RestApplication restApplication = findRestApplicationType(restProjectId, restApplicationId);
        RestResource restResource = mapper.map(restResourceDto, RestResource.class);
        restApplication.getResources().add(restResource);
        save(restProjectId);
        return mapper.map(restResource, RestResourceDto.class);
    }

    /**
     * The method adds a new {@link RestMethod} to a {@link RestResource} and then saves
     * the {@link RestProject} to the file system.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodDto The dto instance of {@link RestMethod} that will be added to the {@link RestResource}
     * @return The saved {@link RestMethodDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     */
    @Override
    public RestMethodDto saveRestMethod(String restProjectId, String restApplicationId, String restResourceId, RestMethodDto restMethodDto) {
        RestResource restResource = findRestResourceType(restProjectId, restApplicationId, restResourceId);
        RestMethod restMethod = mapper.map(restMethodDto, RestMethod.class);
        restResource.getMethods().add(restMethod);
        save(restProjectId);
        return mapper.map(restMethod, RestMethodDto.class);
    }

    /**
     * The method adds a new {@link RestMockResponse} to a {@link RestMethod} and then saves
     * the {@link RestProject} to the file system.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod}
     * @param restMockResponseDto The dto instance of {@link RestMockResponse} that will be added to the {@link RestMethod}
     * @return The saved {@link RestMockResponseDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     * @see RestMockResponse
     * @see RestMockResponseDto
     */
    @Override
    public RestMockResponseDto saveRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, RestMockResponseDto restMockResponseDto) {
        RestMethod restMethod = findRestMethodType(restProjectId, restApplicationId, restResourceId, restMethodId);
        RestMockResponse restMockResponse = mapper.map(restMockResponseDto, RestMockResponse.class);
        restMethod.getMockResponses().add(restMockResponse);
        save(restProjectId);
        return mapper.map(restMockResponse, RestMockResponseDto.class);
    }

    /*
     * UPDATE OPERATIONS
     */

    /**
     * The method updates an already existing {@link RestApplication}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication} that will be updated
     * @param restApplicationDto The updated {@link RestApplicationDto}
     * @return The updated version of the {@link RestApplicationDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     */
    @Override
    public RestApplicationDto updateRestApplication(String restProjectId, String restApplicationId, RestApplicationDto restApplicationDto) {
        RestApplication restApplication = findRestApplicationType(restProjectId, restApplicationId);
        restApplication.setName(restApplicationDto.getName());
        save(restProjectId);
        return mapper.map(restApplication, RestApplicationDto.class);
    }

    /**
     * The method updates an already existing {@link RestResource}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource} that will be updated
     * @param restResourceDto The updated {@link RestResourceDto}
     * @return The updated version of the {@link RestResourceDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     */
    @Override
    public RestResourceDto updateRestResource(String restProjectId, String restApplicationId, String restResourceId, RestResourceDto restResourceDto) {
        RestResource restResource = findRestResourceType(restProjectId, restApplicationId, restResourceId);
        restResource.setName(restResourceDto.getName());
        restResource.setUri(restResourceDto.getUri());
        save(restProjectId);
        return mapper.map(restResource, RestResourceDto.class);
    }

    /**
     * The method updates an already existing {@link RestMethod}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod} that will be updated
     * @param restMethodDto The updated {@link RestMethodDto}
     * @return The updated version of the {@link RestMethodDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     */
    @Override
    public RestMethodDto updateRestMethod(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, RestMethodDto restMethodDto) {
        RestMethod restMethod = findRestMethodType(restProjectId, restApplicationId, restResourceId, restMethodId);
        restMethod.setCurrentResponseSequenceIndex(restMethodDto.getCurrentResponseSequenceIndex());
        restMethod.setName(restMethodDto.getName());
        restMethod.setHttpMethod(restMethodDto.getHttpMethod());
        restMethod.setResponseStrategy(restMethodDto.getResponseStrategy());
        restMethod.setStatus(restMethodDto.getStatus());
        restMethod.setForwardedEndpoint(restMethodDto.getForwardedEndpoint());
        restMethod.setNetworkDelay(restMethodDto.getNetworkDelay());
        restMethod.setSimulateNetworkDelay(restMethodDto.getSimulateNetworkDelay());
        save(restProjectId);
        return mapper.map(restMethod, RestMethodDto.class);
    }

    /**
     * The method updates an already existing {@link RestMockResponse}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod}
     * @param restMockResponseId The id of the {@link RestMockResponse} that will be updated
     * @param restMockResponseDto The updated {@link RestMockResponseDto}
     * @return The updated version of the {@link RestMockResponseDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     */
    @Override
    public RestMockResponseDto updateRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, String restMockResponseId, RestMockResponseDto restMockResponseDto) {
        RestMockResponse restMockResponse = findRestMockResponseType(restProjectId, restApplicationId, restResourceId, restMethodId, restMockResponseId);
        final List<HttpHeader> headers = toDtoList(restMockResponseDto.getHttpHeaders(), HttpHeader.class);
        restMockResponse.setName(restMockResponseDto.getName());
        restMockResponse.setBody(restMockResponseDto.getBody());
        restMockResponse.setHttpStatusCode(restMockResponseDto.getHttpStatusCode());
        restMockResponse.setHttpHeaders(headers);
        restMockResponse.setStatus(restMockResponseDto.getStatus());
        restMockResponse.setUsingExpressions(restMockResponseDto.isUsingExpressions());
        save(restProjectId);
        return mapper.map(restMockResponse, RestMockResponseDto.class);
    }

    /*
     * DELETE OPERATIONS
     */

    /**
     * The method deletes a {@link RestApplication}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication} that will be deleted
     * @return The deleted {@link RestApplicationDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     */
    @Override
    public RestApplicationDto deleteRestApplication(final String restProjectId, final String restApplicationId) {
        RestProject restProject = collection.get(restProjectId);
        Iterator<RestApplication> restApplicationIterator = restProject.getApplications().iterator();
        RestApplication deletedRestApplication = null;
        while(restApplicationIterator.hasNext()){
            RestApplication tempRestApplication = restApplicationIterator.next();
            if(restApplicationId.equals(tempRestApplication.getId())){
                deletedRestApplication = tempRestApplication;
                break;
            }
        }

        if(deletedRestApplication != null){
            restProject.getApplications().remove(deletedRestApplication);
            save(restProjectId);
        }
        return deletedRestApplication != null ? mapper.map(deletedRestApplication, RestApplicationDto.class) : null;
    }

    /**
     * The method deletes a {@link RestResource}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource} that will be deleted
     * @return The deleted {@link RestResourceDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     */
    @Override
    public RestResourceDto deleteRestResource(String restProjectId, String restApplicationId, String restResourceId) {
        RestApplication restApplication = findRestApplicationType(restProjectId, restApplicationId);
        Iterator<RestResource> restResourceIterator = restApplication.getResources().iterator();
        RestResource deletedRestResource = null;
        while(restResourceIterator.hasNext()){
            RestResource tempRestResource = restResourceIterator.next();
            if(restResourceId.equals(tempRestResource.getId())){
                deletedRestResource = tempRestResource;
                break;
            }
        }

        if(deletedRestResource != null){
            restApplication.getResources().remove(deletedRestResource);
            save(restProjectId);
        }
        return deletedRestResource != null ? mapper.map(deletedRestResource, RestResourceDto.class) : null;
    }

    /**
     * The method deletes a {@link RestMethod}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod} that will be deleted
     * @return The deleted {@link RestMethodDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     */
    @Override
    public RestMethodDto deleteRestMethod(String restProjectId, String restApplicationId, String restResourceId, String restMethodId) {
        RestResource restResource = findRestResourceType(restProjectId, restApplicationId, restResourceId);
        Iterator<RestMethod> restMethodIterator = restResource.getMethods().iterator();
        RestMethod deletedRestMethod = null;
        while(restMethodIterator.hasNext()){
            RestMethod tempRestMethod = restMethodIterator.next();
            if(restMethodId.equals(tempRestMethod.getId())){
                deletedRestMethod = tempRestMethod;
                break;
            }
        }

        if(deletedRestMethod != null){
            restResource.getMethods().remove(deletedRestMethod);
            save(restProjectId);
        }
        return deletedRestMethod != null ? mapper.map(deletedRestMethod, RestMethodDto.class) : null;
    }

    /**
     * The method deletes a {@link RestMockResponse}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod}
     * @param restMockResponseId The id of the {@link RestMockResponse} that will be deleted
     * @return The deleted {@link RestMockResponseDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     * @see RestMockResponse
     * @see RestMockResponseDto
     */
    @Override
    public RestMockResponseDto deleteRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, String restMockResponseId) {
        RestMethod restMethod = findRestMethodType(restProjectId, restApplicationId, restResourceId, restMethodId);
        Iterator<RestMockResponse> restMockResponseIterator = restMethod.getMockResponses().iterator();
        RestMockResponse deletedRestMockResponse = null;
        while(restMockResponseIterator.hasNext()){
            RestMockResponse tempRestMockResponse = restMockResponseIterator.next();
            if(restMockResponseId.equals(tempRestMockResponse.getId())){
                deletedRestMockResponse = tempRestMockResponse;
                break;
            }
        }

        if(deletedRestMockResponse != null){
            restMethod.getMockResponses().remove(deletedRestMockResponse);
            save(restProjectId);
        }
        return deletedRestMockResponse != null ? mapper.map(deletedRestMockResponse, RestMockResponseDto.class) : null;
    }


    /**
     * Finds a {@link RestApplication} with the provided ids.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @return A {@link RestApplication} that matches the search criteria.
     * @see RestProject
     * @see RestApplication
     */
    private RestApplication findRestApplicationType(final String restProjectId, final String restApplicationId) {
        Preconditions.checkNotNull(restProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(restApplicationId, "Application id cannot be null");
        final RestProject restProject = collection.get(restProjectId);

        if(restProject == null){
            throw new IllegalArgumentException("Unable to find a REST project with id " + restProjectId);
        }

        for(RestApplication restApplication : restProject.getApplications()){
            if(restApplication.getId().equals(restApplicationId)){
                return restApplication;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST application with id " + restApplicationId);
    }

    /**
     * Finds a {@link RestResource} with the provided ids.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @return A {@link RestResource} that matches the search criteria.
     * @see RestProject
     * @see RestApplication
     * @see RestResource
     */
    private RestResource findRestResourceType(final String restProjectId, final String restApplicationId, final String restResourceId){
        Preconditions.checkNotNull(restResourceId, "Resource id cannot be null");
        final RestApplication restApplication = findRestApplicationType(restProjectId, restApplicationId);
        for(RestResource restResource : restApplication.getResources()){
            if(restResource.getId().equals(restResourceId)){
                return restResource;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST resource with id " + restResourceId);
    }

    /**
     * Finds a {@link RestMethod} with the provided ids.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod}
     * @return A {@link RestMethod} that matches the search criteria.
     * @see RestProject
     * @see RestApplication
     * @see RestResource
     * @see RestMethod
     */
    private RestMethod findRestMethodType(final String restProjectId, final String restApplicationId, final String restResourceId, final String restMethodId){
        Preconditions.checkNotNull(restMethodId, "Method id cannot be null");
        final RestResource restResource = findRestResourceType(restProjectId, restApplicationId, restResourceId);
        for(RestMethod restMethod : restResource.getMethods()){
            if(restMethod.getId().equals(restMethodId)){
                return restMethod;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST method with id " + restMethodId);
    }

    /**
     * Finds a {@link RestMockResponse} with the provided ids.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod}
     * @param restMockResponseId The id of the {@link RestMockResponse}
     * @return A {@link RestMockResponse} that matches the search criteria.
     * @see RestProject
     * @see RestApplication
     * @see RestResource
     * @see RestMethod
     * @see RestMockResponse
     */
    private RestMockResponse findRestMockResponseType(final String restProjectId, final String restApplicationId, final String restResourceId, final String restMethodId, final String restMockResponseId){
        Preconditions.checkNotNull(restMockResponseId, "Mock response id cannot be null");
        final RestMethod restMethod = findRestMethodType(restProjectId, restApplicationId, restResourceId, restMethodId);
        for(RestMockResponse restMockResponse : restMethod.getMockResponses()) {
            if(restMockResponse.getId().equals(restMockResponseId)){
                return restMockResponse;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST mock response with id " + restMockResponseId);
    }


    /**
     * Search through a REST project and all its resources
     * @param restProject The REST project which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchRestProject(final RestProject restProject, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(restProject.getName(), query.getQuery())){
            final String projectType = messageSource.getMessage("general.type.project", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(restProject.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + restProject.getId());
            searchResult.setDescription(REST_TYPE + COMMA + projectType);
            searchResults.add(searchResult);
        }

        for(RestApplication restApplication : restProject.getApplications()){
            List<SearchResult> restApplicationSearchResult = searchRestApplication(restProject, restApplication, query);
            searchResults.addAll(restApplicationSearchResult);
        }
        return searchResults;
    }

    /**
     * Search through a REST application and all its resources
     * @param restProject The REST project which will be searched
     * @param restApplication The REST application which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchRestApplication(final RestProject restProject, final RestApplication restApplication, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(restApplication.getName(), query.getQuery())){
            final String applicationType = messageSource.getMessage("rest.type.application", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(restApplication.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId());
            searchResult.setDescription(REST_TYPE + COMMA + applicationType);
            searchResults.add(searchResult);
        }

        for(RestResource restResource : restApplication.getResources()){
            List<SearchResult> restResourceSearchResult = searchRestResource(restProject, restApplication, restResource, query);
            searchResults.addAll(restResourceSearchResult);
        }
        return searchResults;
    }

    /**
     * Search through a REST resource and all its resources
     * @param restProject The REST project which will be searched
     * @param restApplication The REST port which will be searched
     * @param restResource The REST resource which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchRestResource(final RestProject restProject, final RestApplication restApplication, final RestResource restResource, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();

        if(SearchValidator.validate(restResource.getName(), query.getQuery())){
            final String resourceType = messageSource.getMessage("rest.type.resource", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(restResource.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() + SLASH + RESOURCE + SLASH + restResource.getId());
            searchResult.setDescription(REST_TYPE + COMMA + resourceType);
            searchResults.add(searchResult);
        }

        for(RestMethod restMethod : restResource.getMethods()){
            List<SearchResult> restMethodSearchResult = searchRestMethod(restProject, restApplication, restResource, restMethod, query);
            searchResults.addAll(restMethodSearchResult);
        }
        return searchResults;
    }

    /**
     * Search through a REST operation and all its resources
     * @param restProject The REST project which will be searched
     * @param restApplication The REST application which will be searched
     * @param restResource The REST resource which will be searched
     * @param restMethod The REST method which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchRestMethod(final RestProject restProject, final RestApplication restApplication, final RestResource restResource, final RestMethod restMethod, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();

        if(SearchValidator.validate(restMethod.getName(), query.getQuery())){
            final String methodType = messageSource.getMessage("rest.type.method", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(restMethod.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() + SLASH + RESOURCE + SLASH + restResource.getId() + SLASH + METHOD + SLASH + restMethod.getId());
            searchResult.setDescription(REST_TYPE + COMMA + methodType);
            searchResults.add(searchResult);
        }

        for(RestMockResponse restMockResponse : restMethod.getMockResponses()){
            SearchResult restMockResponseSearchResult = searchRestMockResponse(restProject, restApplication, restResource, restMethod, restMockResponse, query);
            if(restMockResponseSearchResult != null){
                searchResults.add(restMockResponseSearchResult);
            }
        }
        return searchResults;
    }



    /**
     * Search through a REST operation and all its resources
     * @param restProject The REST project which will be searched
     * @param restApplication The REST application which will be searched
     * @param restResource The REST resource which will be searched
     * @param restMethod The REST method which will be searched
     * @param restMockResponse The REST response will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private SearchResult searchRestMockResponse(final RestProject restProject, final RestApplication restApplication, final RestResource restResource, final RestMethod restMethod, final RestMockResponse restMockResponse, final SearchQuery query){
        SearchResult searchResult = null;
        if(SearchValidator.validate(restMockResponse.getName(), query.getQuery())){
            final String mockResponseType = messageSource.getMessage("rest.type.mockresponse", null , LocaleContextHolder.getLocale());
            searchResult = new SearchResult();
            searchResult.setTitle(restMockResponse.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() + SLASH + RESOURCE + SLASH + restResource.getId() + SLASH + METHOD + SLASH + restMethod.getId() + SLASH + RESPONSE + SLASH + restMockResponse.getId());
            searchResult.setDescription(REST_TYPE + COMMA + mockResponseType);
        }

        return searchResult;
    }

}
