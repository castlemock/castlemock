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

package com.castlemock.web.mock.rest.service.project;

import com.castlemock.core.basis.model.*;
import com.castlemock.core.mock.rest.model.project.domain.*;
import com.castlemock.core.mock.rest.service.project.input.SearchRestProjectInput;
import com.castlemock.core.mock.rest.service.project.output.SearchRestProjectOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ArrayList;
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
        final List<SearchResult> searchResults = new ArrayList<>();

        final List<RestProject> projects = this.repository.search(searchQuery);
        final List<RestApplication> applications = this.applicationRepository.search(searchQuery);
        final List<RestResource> resources = this.resourceRepository.search(searchQuery);
        final List<RestMethod> methods = this.methodRepository.search(searchQuery);
        final List<RestMockResponse> mockResponses = this.mockResponseRepository.search(searchQuery);

        final String projectType = messageSource.getMessage("general.type.project", null , LocaleContextHolder.getLocale());
        final String applicationType = messageSource.getMessage("rest.type.application", null , LocaleContextHolder.getLocale());
        final String resourceType = messageSource.getMessage("rest.type.resource", null , LocaleContextHolder.getLocale());
        final String methodType = messageSource.getMessage("rest.type.method", null , LocaleContextHolder.getLocale());
        final String responseType = messageSource.getMessage("rest.type.mockresponse", null , LocaleContextHolder.getLocale());

        projects.forEach(project -> {
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(project.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + project.getId());
            searchResult.setDescription(REST_TYPE + COMMA + projectType);
            searchResults.add(searchResult);
        });

        applications.forEach(application -> {
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(application.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + application.getProjectId()
                    + SLASH + APPLICATION + SLASH + application.getId());
            searchResult.setDescription(REST_TYPE + COMMA + applicationType);
            searchResults.add(searchResult);
        });

        resources.forEach(resource -> {
            final String projectId = this.applicationRepository.getProjectId(resource.getApplicationId());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(resource.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + projectId
                    + SLASH + APPLICATION + SLASH + resource.getApplicationId()
                    + SLASH + RESOURCE + SLASH + resource.getId());
            searchResult.setDescription(REST_TYPE + COMMA + resourceType);
            searchResults.add(searchResult);
        });

        methods.forEach(method -> {
            final String applicationId = this.resourceRepository.getApplicationId(method.getResourceId());
            final String projectId = this.applicationRepository.getProjectId(applicationId);
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(method.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + projectId
                    + SLASH + APPLICATION + SLASH + applicationId
                    + SLASH + RESOURCE + SLASH + method.getResourceId()
                    + SLASH + METHOD + SLASH + method.getId());
            searchResult.setDescription(REST_TYPE + COMMA + methodType);
            searchResults.add(searchResult);
        });

        mockResponses.forEach(mockResponse -> {
            final String resourceId = this.methodRepository.getResourceId(mockResponse.getMethodId());
            final String applicationId = this.resourceRepository.getApplicationId(resourceId);
            final String projectId = this.applicationRepository.getProjectId(applicationId);
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(mockResponse.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + projectId
                    + SLASH + APPLICATION + SLASH + applicationId
                    + SLASH + RESOURCE + SLASH + resourceId
                    + SLASH + METHOD + SLASH + mockResponse.getMethodId()
                    + SLASH + RESPONSE + SLASH + mockResponse.getId());
            searchResult.setDescription(REST_TYPE + COMMA + responseType);
            searchResults.add(searchResult);
        });

        return createServiceResult(SearchRestProjectOutput.builder()
                .searchResults(searchResults)
                .build());
    }

}
