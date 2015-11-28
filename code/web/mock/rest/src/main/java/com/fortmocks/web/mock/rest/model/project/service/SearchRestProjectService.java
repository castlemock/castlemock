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

import com.fortmocks.core.basis.model.*;
import com.fortmocks.core.mock.rest.model.project.domain.*;
import com.fortmocks.core.mock.rest.model.project.service.message.input.SearchRestProjectInput;
import com.fortmocks.core.mock.rest.model.project.service.message.output.SearchRestProjectOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * The service provides the functionality to search for REST resources.
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class SearchRestProjectService extends AbstractRestProjectService implements Service<SearchRestProjectInput, SearchRestProjectOutput> {

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

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<SearchRestProjectOutput> process(final ServiceTask<SearchRestProjectInput> serviceTask) {
        final SearchRestProjectInput input = serviceTask.getInput();
        final SearchQuery searchQuery = input.getSearchQuery();
        final String query = searchQuery.getQuery();
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        for(RestProject restProject : findAllTypes()){
            List<SearchResult> restProjectSearchResult = searchRestProject(restProject, query);
            searchResults.addAll(restProjectSearchResult);
        }

        return createServiceResult(new SearchRestProjectOutput(searchResults));
    }

    /**
     * Search through a REST project and all its resources
     * @param restProject The REST project which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchRestProject(final RestProject restProject, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(restProject.getName(), query)){
            final String projectType = messageSource.getMessage("general.type.project", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(restProject.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + restProject.getId());
            searchResult.setDescription(REST_TYPE + COMMA + projectType);
            searchResults.add(searchResult);
        }

        for(RestApplication restApplication : restProject.getRestApplications()){
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
    private List<SearchResult> searchRestApplication(final RestProject restProject, final RestApplication restApplication, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(restApplication.getName(), query)){
            final String applicationType = messageSource.getMessage("rest.type.application", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(restApplication.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId());
            searchResult.setDescription(REST_TYPE + COMMA + applicationType);
            searchResults.add(searchResult);
        }

        for(RestResource restResource : restApplication.getRestResources()){
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
    private List<SearchResult> searchRestResource(final RestProject restProject, final RestApplication restApplication, final RestResource restResource, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();

        if(SearchValidator.validate(restResource.getName(), query)){
            final String resourceType = messageSource.getMessage("rest.type.resource", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(restResource.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() + SLASH + RESOURCE + SLASH + restResource.getId());
            searchResult.setDescription(REST_TYPE + COMMA + resourceType);
            searchResults.add(searchResult);
        }

        for(RestMethod restMethod : restResource.getRestMethods()){
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
    private List<SearchResult> searchRestMethod(final RestProject restProject, final RestApplication restApplication, final RestResource restResource, final RestMethod restMethod, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();

        if(SearchValidator.validate(restMethod.getName(), query)){
            final String methodType = messageSource.getMessage("rest.type.method", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(restMethod.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() + SLASH + RESOURCE + SLASH + restResource.getId() + SLASH + METHOD + SLASH + restMethod.getId());
            searchResult.setDescription(REST_TYPE + COMMA + methodType);
            searchResults.add(searchResult);
        }

        for(RestMockResponse restMockResponse : restMethod.getRestMockResponses()){
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
    private SearchResult searchRestMockResponse(final RestProject restProject, final RestApplication restApplication, final RestResource restResource, final RestMethod restMethod, final RestMockResponse restMockResponse, final String query){
        SearchResult searchResult = null;
        if(SearchValidator.validate(restMockResponse.getName(), query)){
            final String mockResponseType = messageSource.getMessage("rest.type.mockresponse", null , LocaleContextHolder.getLocale());
            searchResult = new SearchResult();
            searchResult.setTitle(restMockResponse.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() + SLASH + RESOURCE + SLASH + restResource.getId() + SLASH + METHOD + SLASH + restMethod.getId() + SLASH + RESPONSE + SLASH + restMockResponse.getId());
            searchResult.setDescription(REST_TYPE + COMMA + mockResponseType);
        }

        return searchResult;
    }
}
