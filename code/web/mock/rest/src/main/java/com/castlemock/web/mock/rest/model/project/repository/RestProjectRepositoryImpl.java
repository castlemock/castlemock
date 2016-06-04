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

import java.util.LinkedList;
import java.util.List;

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
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     */
    @Override
    public RestProjectDto findRestProjectWithName(final String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");
        for(RestProject restProject : collection.values()){
            if(restProject.getName().equalsIgnoreCase(name)) {
                return mapper.map(restProject, RestProjectDto.class);
            }
        }
        return null;
    }

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
            if(restProject.getApplications() == null){
                restProject.setApplications(new LinkedList<RestApplication>());
            }

            for(RestApplication restApplication : restProject.getApplications()){
                if(restApplication.getResources() == null){
                    restApplication.setResources(new LinkedList<RestResource>());
                }

                for(RestResource restResource : restApplication.getResources()){
                    if(restResource.getMethods() == null){
                        restResource.setMethods(new LinkedList<RestMethod>());
                    }

                    for(RestMethod restMethod : restResource.getMethods()){
                        if(restMethod.getMockResponses() == null){
                            restMethod.setMockResponses(new LinkedList<RestMockResponse>());
                        }

                        for(RestMockResponse restMockResponse : restMethod.getMockResponses()){
                            if(restMockResponse.getHttpHeaders() == null){
                                restMockResponse.setHttpHeaders(new LinkedList<HttpHeader>());
                            }
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
    public RestProjectDto save(final RestProjectDto dto) {
        for(RestApplicationDto restApplication : dto.getApplications()){
            if(restApplication.getId() == null){
                String restApplicationId = generateId();
                restApplication.setId(restApplicationId);
            }
            for(RestResourceDto restResource : restApplication.getResources()){
                if(restResource.getId() == null){
                    String restResourceId = generateId();
                    restResource.setId(restResourceId);
                }
                for(RestMethodDto restMethod : restResource.getMethods()){
                    if(restMethod.getId() == null){
                        String restMethodId = generateId();
                        restMethod.setId(restMethodId);
                    }
                    for(RestMockResponseDto restMockResponse : restMethod.getMockResponses()){
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

    @Override
    public RestApplicationDto findRestApplication(String restProjectId, String restApplicationId) {
        return null;
    }

    @Override
    public RestResourceDto findRestResource(String restProjectId, String restApplicationId, String restResourceId) {
        return null;
    }

    @Override
    public RestMethodDto findRestMethod(String restProjectId, String restApplicationId, String restResourceId, String restMethodId) {
        return null;
    }

    @Override
    public RestMockResponseDto findRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, String restMockResponseId) {
        return null;
    }

    @Override
    public RestResourceDto findRestResourceByUri(String restProjectId, String restApplicationId, String restResourceUri) {
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

    @Override
    public RestApplicationDto saveRestApplication(String restProjectId, RestApplicationDto restApplicationDto) {
        return null;
    }

    @Override
    public RestResourceDto saveRestResource(String restProjectId, String restApplicationId, RestResourceDto restResourceDto) {
        return null;
    }

    @Override
    public RestMethodDto saveRestMethod(String restProjectId, String restApplicationId, String restResourceId, RestMethodDto restMethodDto) {
        return null;
    }

    @Override
    public RestMockResponseDto saveRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, RestMockResponseDto restMockResponseDto) {
        return null;
    }

    @Override
    public RestApplicationDto updateRestApplication(String restProjectId, String restApplicationId, RestApplicationDto restApplicationDto) {
        RestApplication restApplication = findRestApplicationType(restProjectId, restApplicationId);
        restApplication.setName(restApplicationDto.getName());
        return mapper.map(restApplication, RestApplicationDto.class);
    }

    @Override
    public RestResourceDto updateRestResource(String restProjectId, String restApplicationId, String restResourceId, RestResourceDto updatedRestResource) {
        RestResource restResource = findRestResourceType(restProjectId, restApplicationId, restResourceId);
        restResource.setName(updatedRestResource.getName());
        restResource.setUri(updatedRestResource.getUri());
        return mapper.map(restResource, RestResourceDto.class);
    }

    @Override
    public RestMethodDto updateRestMethod(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, RestMethodDto updatedRestMethod) {
        RestMethod restMethod = findRestMethodType(restProjectId, restApplicationId, restResourceId, restMethodId);
        restMethod.setCurrentResponseSequenceIndex(updatedRestMethod.getCurrentResponseSequenceIndex());
        restMethod.setName(updatedRestMethod.getName());
        restMethod.setHttpMethod(updatedRestMethod.getHttpMethod());
        restMethod.setResponseStrategy(updatedRestMethod.getResponseStrategy());
        restMethod.setStatus(updatedRestMethod.getStatus());
        restMethod.setForwardedEndpoint(updatedRestMethod.getForwardedEndpoint());
        return mapper.map(restMethod, RestMethodDto.class);
    }

    @Override
    public RestMockResponseDto updateRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, String restMockResponseId, RestMockResponseDto updatedRestMockResponseDto) {
        RestMockResponse restMockResponse = findRestMockResponseType(restProjectId, restApplicationId, restResourceId, restMethodId, restMockResponseId);
        final List<HttpHeader> headers = toDtoList(updatedRestMockResponseDto.getHttpHeaders(), HttpHeader.class);
        restMockResponse.setName(updatedRestMockResponseDto.getName());
        restMockResponse.setBody(updatedRestMockResponseDto.getBody());
        restMockResponse.setHttpStatusCode(updatedRestMockResponseDto.getHttpStatusCode());
        restMockResponse.setHttpHeaders(headers);
        return mapper.map(restMockResponse, RestMockResponseDto.class);
    }

    @Override
    public RestApplicationDto deleteRestApplication(String restProjectId, String restApplicationId) {
        return null;
    }

    @Override
    public RestResourceDto deleteRestResource(String restProjectId, String restApplicationId, String restResourceId) {
        return null;
    }

    @Override
    public RestMethodDto deleteRestMethod(String restProjectId, String restApplicationId, String restResourceId, String restMethodId) {
        return null;
    }

    @Override
    public RestMockResponseDto deleteRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, String restMockResponseId) {
        return null;
    }



    protected RestApplication findRestApplicationType(final String restProjectId, final String restApplicationId) {
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

    protected RestResource findRestResourceType(final String restProjectId, final String restApplicationId, final String restResourceId){
        Preconditions.checkNotNull(restResourceId, "Resource id cannot be null");
        final RestApplication restApplication = findRestApplicationType(restProjectId, restApplicationId);
        for(RestResource restResource : restApplication.getResources()){
            if(restResource.getId().equals(restResourceId)){
                return restResource;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST resource with id " + restResourceId);
    }

    protected RestMethod findRestMethodType(final String restProjectId, final String restApplicationId, final String restResourceId, final String restMethodId){
        Preconditions.checkNotNull(restMethodId, "Method id cannot be null");
        final RestResource restResource = findRestResourceType(restProjectId, restApplicationId, restResourceId);
        for(RestMethod restMethod : restResource.getMethods()){
            if(restMethod.getId().equals(restMethodId)){
                return restMethod;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST method with id " + restMethodId);
    }

    protected RestMockResponse findRestMockResponseType(final String restProjectId, final String restApplicationId, final String restResourceId, final String restMethodId, final String restMockResponseId){
        Preconditions.checkNotNull(restMockResponseId, "Mock response id cannot be null");
        final RestMethod restMethod = findRestMethodType(restProjectId, restApplicationId, restResourceId, restMethodId);
        for(RestMockResponse restMockResponse : restMethod.getMockResponses()) {
            if(restMockResponse.getId().equals(restMockResponseId)){
                return restMockResponse;
            }
        }
        throw new IllegalArgumentException("Unable to find a REST mock response with id " + restMockResponseId);
    }

}
