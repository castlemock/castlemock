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

package com.castlemock.web.mock.soap.model.project.service;

import com.castlemock.core.basis.model.*;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.service.message.input.SearchSoapProjectInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.SearchSoapProjectOutput;
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
public class SearchSoapProjectServiceTest {

    @Mock
    private MessageSource messageSource;

    @Mock
    private Repository repository;

    @InjectMocks
    private SearchSoapProjectService service;

    private static final String PROJECT_LANGUAGE = "Project";
    private static final String PORT_LANGUAGE = "Port";
    private static final String OPERATION_LANGUAGE = "Operation";
    private static final String MOCK_RESPONSE_LANGUAGE = "Mock response";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(messageSource.getMessage("general.type.project", null, LocaleContextHolder.getLocale())).thenReturn(PROJECT_LANGUAGE);
        Mockito.when(messageSource.getMessage("soap.type.port", null, LocaleContextHolder.getLocale())).thenReturn(PORT_LANGUAGE);
        Mockito.when(messageSource.getMessage("soap.type.operation", null, LocaleContextHolder.getLocale())).thenReturn(OPERATION_LANGUAGE);
        Mockito.when(messageSource.getMessage("soap.type.mockresponse", null, LocaleContextHolder.getLocale())).thenReturn(MOCK_RESPONSE_LANGUAGE);

        final List<SoapProject> soapProjects = new ArrayList<>();
        final SoapProject soapProject = new SoapProject();
        soapProject.setDescription("Project Description");
        soapProject.setName("Soap project");
        soapProject.setId("1");
        soapProject.setPorts(new ArrayList<SoapPort>());

        final SoapPort soapPort = new SoapPort();
        soapPort.setName("Soap port");
        soapPort.setId("2");
        soapPort.setOperations(new ArrayList<SoapOperation>());

        final SoapOperation soapOperation = new SoapOperation();
        soapOperation.setName("Soap operation");
        soapOperation.setId("3");
        soapOperation.setMockResponses(new ArrayList<SoapMockResponse>());

        final SoapMockResponse soapMockResponse = new SoapMockResponse();
        soapMockResponse.setName("Soap mock response");
        soapMockResponse.setId("4");


        soapProject.getPorts().add(soapPort);
        soapPort.getOperations().add(soapOperation);
        soapOperation.getMockResponses().add(soapMockResponse);

        soapProjects.add(soapProject);

        Mockito.when(repository.findAll()).thenReturn(soapProjects);
    }



    @Test
    @Ignore
    public void testProcess(){
        final List<SearchResult> searchResults = search("Soap");
        Assert.assertEquals(searchResults.size(), 4); // All should match the resource
    }

    @Test
    @Ignore
    public void testProcessMatchProject(){
        final List<SearchResult> searchResults = search("Project");
        Assert.assertEquals(searchResults.size(), 1);
        final SearchResult searchResult = searchResults.get(0);
        Assert.assertEquals(searchResult.getTitle(), "Soap project");
        Assert.assertEquals(searchResult.getLink(), "soap/project/1");
    }

    @Test
    @Ignore
    public void testProcessMatchPort(){
        final List<SearchResult> searchResults = search("Port");
        Assert.assertEquals(searchResults.size(), 1);
        final SearchResult searchResult = searchResults.get(0);
        Assert.assertEquals(searchResult.getTitle(), "Soap port");
        Assert.assertEquals(searchResult.getLink(), "soap/project/1/port/2");
    }

    @Test
    @Ignore
    public void testProcessMatchOperation(){
        final List<SearchResult> searchResults = search("Operation");
        Assert.assertEquals(searchResults.size(), 1);
        final SearchResult searchResult = searchResults.get(0);
        Assert.assertEquals(searchResult.getTitle(), "Soap operation");
        Assert.assertEquals(searchResult.getLink(), "soap/project/1/port/2/operation/3");
    }

    @Test
    @Ignore
    public void testProcessMatchMockResponse(){
        final List<SearchResult> searchResults = search("response");
        Assert.assertEquals(searchResults.size(), 1);
        final SearchResult searchResult = searchResults.get(0);
        Assert.assertEquals(searchResult.getTitle(), "Soap mock response");
        Assert.assertEquals(searchResult.getLink(), "soap/project/1/port/2/operation/3/response/4");
    }


    private List<SearchResult> search(String query){
        final SearchQuery searchQuery = new SearchQuery();
        searchQuery.setQuery(query);
        final SearchSoapProjectInput input = new SearchSoapProjectInput(searchQuery);
        final ServiceTask<SearchSoapProjectInput> serviceTask = new ServiceTask<SearchSoapProjectInput>();
        serviceTask.setInput(input);
        final ServiceResult<SearchSoapProjectOutput> serviceResult = service.process(serviceTask);
        final SearchSoapProjectOutput searchRestProjectOutput = serviceResult.getOutput();
        return searchRestProjectOutput.getSearchResults();
    }

}
