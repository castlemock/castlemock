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

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.dto.*;
import com.castlemock.core.mock.rest.model.project.service.message.input.SearchRestProjectInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.SearchRestProjectOutput;
import com.castlemock.web.mock.rest.model.project.repository.RestProjectRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SearchRestProjectServiceTest {

    @Mock
    private MessageSource messageSource;

    @Mock
    private RestProjectRepository repository;

    @InjectMocks
    private SearchRestProjectService service;

    private static final String PROJECT_LANGUAGE = "Project";
    private static final String APPLICATION_LANGUAGE = "Application";
    private static final String RESOURCE_LANGUAGE = "Resource";
    private static final String METHOD_LANGUAGE = "Method";
    private static final String MOCK_RESPONSE_LANGUAGE = "Mock response";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(messageSource.getMessage("general.type.project", null, LocaleContextHolder.getLocale())).thenReturn(PROJECT_LANGUAGE);
        Mockito.when(messageSource.getMessage("rest.type.application", null, LocaleContextHolder.getLocale())).thenReturn(APPLICATION_LANGUAGE);
        Mockito.when(messageSource.getMessage("rest.type.resource", null, LocaleContextHolder.getLocale())).thenReturn(RESOURCE_LANGUAGE);
        Mockito.when(messageSource.getMessage("rest.type.method", null, LocaleContextHolder.getLocale())).thenReturn(METHOD_LANGUAGE);
        Mockito.when(messageSource.getMessage("rest.type.mockresponse", null, LocaleContextHolder.getLocale())).thenReturn(MOCK_RESPONSE_LANGUAGE);

        final List<RestProjectDto> restProjects = new ArrayList<>();
        final RestProjectDto restProject = new RestProjectDto();
        restProject.setDescription("Project Description");
        restProject.setName("Rest project");
        restProject.setId("1");
        restProject.setApplications(new ArrayList<RestApplicationDto>());

        final RestApplicationDto restApplication = new RestApplicationDto();
        restApplication.setName("Rest application");
        restApplication.setId("2");
        restApplication.setResources(new ArrayList<RestResourceDto>());

        final RestResourceDto restResource = new RestResourceDto();
        restResource.setName("Rest resource");
        restResource.setId("3");
        restResource.setMethods(new ArrayList<RestMethodDto>());

        final RestMethodDto restMethod = new RestMethodDto();
        restMethod.setName("Rest method");
        restMethod.setId("4");
        restMethod.setMockResponses(new ArrayList<RestMockResponseDto>());

        final RestMockResponseDto restMockResponse = new RestMockResponseDto();
        restMockResponse.setName("Rest mock response");
        restMockResponse.setId("5");

        restProject.getApplications().add(restApplication);
        restApplication.getResources().add(restResource);
        restResource.getMethods().add(restMethod);
        restMethod.getMockResponses().add(restMockResponse);

        restProjects.add(restProject);

        Mockito.when(repository.findAll()).thenReturn(restProjects);
    }



    @Test
    @Ignore
    public void testProcess(){
        final SearchQuery searchQuery = new SearchQuery();
        searchQuery.setQuery("Rest");
        final SearchRestProjectInput input = new SearchRestProjectInput(searchQuery);
        final ServiceTask<SearchRestProjectInput> serviceTask = new ServiceTask<SearchRestProjectInput>();
        serviceTask.setInput(input);
        final ServiceResult<SearchRestProjectOutput> serviceResult = service.process(serviceTask);
        final SearchRestProjectOutput searchRestProjectOutput = serviceResult.getOutput();
        final List<SearchResult> searchResults = searchRestProjectOutput.getSearchResults();
        Assert.assertEquals(searchResults.size(), 5); // All should match the resource
    }

    @Test
    @Ignore
    public void testProcessMatchProject(){
        final List<SearchResult> searchResults = search("Project");
        Assert.assertEquals(searchResults.size(), 1);
        final SearchResult searchResult = searchResults.get(0);
        Assert.assertEquals(searchResult.getTitle(), "Rest project");
        Assert.assertEquals(searchResult.getLink(), "rest/project/1");
    }

    @Test
    @Ignore
    public void testProcessMatchApplication(){
        final List<SearchResult> searchResults = search("Application");
        Assert.assertEquals(searchResults.size(), 1);
        final SearchResult searchResult = searchResults.get(0);
        Assert.assertEquals(searchResult.getTitle(), "Rest application");
        Assert.assertEquals(searchResult.getLink(), "rest/project/1/application/2");
    }

    @Test
    @Ignore
    public void testProcessMatchResource(){
        final List<SearchResult> searchResults = search("Resource");
        Assert.assertEquals(searchResults.size(), 1);
        final SearchResult searchResult = searchResults.get(0);
        Assert.assertEquals(searchResult.getTitle(), "Rest resource");
        Assert.assertEquals(searchResult.getLink(), "rest/project/1/application/2/resource/3");
    }

    @Test
    @Ignore
    public void testProcessMatchMethod(){
        final List<SearchResult> searchResults = search("Method");
        Assert.assertEquals(searchResults.size(), 1);
        final SearchResult searchResult = searchResults.get(0);
        Assert.assertEquals(searchResult.getTitle(), "Rest method");
        Assert.assertEquals(searchResult.getLink(), "rest/project/1/application/2/resource/3/method/4");
    }

    @Test
    @Ignore
    public void testProcessMatchMockResponse(){
        final List<SearchResult> searchResults = search("response");
        Assert.assertEquals(searchResults.size(), 1);
        final SearchResult searchResult = searchResults.get(0);
        Assert.assertEquals(searchResult.getTitle(), "Rest mock response");
        Assert.assertEquals(searchResult.getLink(), "rest/project/1/application/2/resource/3/method/4/response/5");
    }


    private List<SearchResult> search(String query){
        final SearchQuery searchQuery = new SearchQuery();
        searchQuery.setQuery(query);
        final SearchRestProjectInput input = new SearchRestProjectInput(searchQuery);
        final ServiceTask<SearchRestProjectInput> serviceTask = new ServiceTask<SearchRestProjectInput>();
        serviceTask.setInput(input);
        final ServiceResult<SearchRestProjectOutput> serviceResult = service.process(serviceTask);
        final SearchRestProjectOutput searchRestProjectOutput = serviceResult.getOutput();
        return searchRestProjectOutput.getSearchResults();
    }

}
