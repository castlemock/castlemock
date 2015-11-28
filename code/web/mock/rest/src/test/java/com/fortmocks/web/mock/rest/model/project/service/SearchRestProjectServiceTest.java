package com.fortmocks.web.mock.rest.model.project.service;

import com.fortmocks.core.basis.model.*;
import com.fortmocks.core.mock.rest.model.project.domain.*;
import com.fortmocks.core.mock.rest.model.project.service.message.input.SearchRestProjectInput;
import com.fortmocks.core.mock.rest.model.project.service.message.output.SearchRestProjectOutput;
import org.junit.Assert;
import org.junit.Before;
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
    private Repository repository;

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

        final List<RestProject> restProjects = new ArrayList<>();
        final RestProject restProject = new RestProject();
        restProject.setDescription("Project Description");
        restProject.setName("Rest project");
        restProject.setId(1L);
        restProject.setRestApplications(new ArrayList<RestApplication>());

        final RestApplication restApplication = new RestApplication();
        restApplication.setName("Rest application");
        restApplication.setId(2L);
        restApplication.setRestResources(new ArrayList<RestResource>());

        final RestResource restResource = new RestResource();
        restResource.setName("Rest resource");
        restResource.setId(3L);
        restResource.setRestMethods(new ArrayList<RestMethod>());

        final RestMethod restMethod = new RestMethod();
        restMethod.setName("Rest method");
        restMethod.setId(4L);
        restMethod.setRestMockResponses(new ArrayList<RestMockResponse>());

        final RestMockResponse restMockResponse = new RestMockResponse();
        restMockResponse.setName("Rest mock response");
        restMockResponse.setId(5L);

        restProject.getRestApplications().add(restApplication);
        restApplication.getRestResources().add(restResource);
        restResource.getRestMethods().add(restMethod);
        restMethod.getRestMockResponses().add(restMockResponse);

        restProjects.add(restProject);

        Mockito.when(repository.findAll()).thenReturn(restProjects);
    }



    @Test
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
    public void testProcessMatchProject(){
        final List<SearchResult> searchResults = search("Project");
        Assert.assertEquals(searchResults.size(), 1);
        final SearchResult searchResult = searchResults.get(0);
        Assert.assertEquals(searchResult.getTitle(), "Rest project");
        Assert.assertEquals(searchResult.getLink(), "rest/project/1");
    }

    @Test
    public void testProcessMatchApplication(){
        final List<SearchResult> searchResults = search("Application");
        Assert.assertEquals(searchResults.size(), 1);
        final SearchResult searchResult = searchResults.get(0);
        Assert.assertEquals(searchResult.getTitle(), "Rest application");
        Assert.assertEquals(searchResult.getLink(), "rest/project/1/application/2");
    }

    @Test
    public void testProcessMatchResource(){
        final List<SearchResult> searchResults = search("Resource");
        Assert.assertEquals(searchResults.size(), 1);
        final SearchResult searchResult = searchResults.get(0);
        Assert.assertEquals(searchResult.getTitle(), "Rest resource");
        Assert.assertEquals(searchResult.getLink(), "rest/project/1/application/2/resource/3");
    }

    @Test
    public void testProcessMatchMethod(){
        final List<SearchResult> searchResults = search("Method");
        Assert.assertEquals(searchResults.size(), 1);
        final SearchResult searchResult = searchResults.get(0);
        Assert.assertEquals(searchResult.getTitle(), "Rest method");
        Assert.assertEquals(searchResult.getLink(), "rest/project/1/application/2/resource/3/method/4");
    }

    @Test
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
