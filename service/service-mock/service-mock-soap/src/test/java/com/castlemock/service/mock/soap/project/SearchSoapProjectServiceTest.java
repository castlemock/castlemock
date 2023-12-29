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

package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapProjectTestBuilder;
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.repository.soap.project.SoapPortRepository;
import com.castlemock.repository.soap.project.SoapProjectRepository;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import com.castlemock.service.mock.soap.project.input.SearchSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.SearchSoapProjectOutput;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SearchSoapProjectServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private MessageSource messageSource;

    @Mock
    private SoapProjectRepository repository;

    @Mock
    private SoapPortRepository portRepository;

    @Mock
    private SoapOperationRepository operationRepository;

    @Mock
    private SoapResourceRepository resourceRepository;

    @Mock
    private SoapMockResponseRepository mockResponseRepository;

    @InjectMocks
    private SearchSoapProjectService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testProcess(){
        SoapProject soapProject = SoapProjectTestBuilder.builder().build();

        Mockito.when(repository.search(Mockito.any(SearchQuery.class))).thenReturn(Arrays.asList(soapProject));
        Mockito.when(portRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());
        Mockito.when(operationRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());
        Mockito.when(resourceRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());
        Mockito.when(mockResponseRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());

        Mockito.when(messageSource.getMessage("general.type.project", null, LocaleContextHolder.getLocale())).thenReturn("Project");

        final String query = "Query";
        final SearchQuery searchQuery = new SearchQuery();
        searchQuery.setQuery(query);

        final SearchSoapProjectInput input = SearchSoapProjectInput.builder()
                .searchQuery(searchQuery)
                .build();
        final ServiceTask<SearchSoapProjectInput> serviceTask = ServiceTask.of(input, "user");

        final ServiceResult<SearchSoapProjectOutput> serviceResult = service.process(serviceTask);
        final SearchSoapProjectOutput searchSoapProjectOutput = serviceResult.getOutput();

        Assert.assertNotNull(searchSoapProjectOutput.getSearchResults());
        Assert.assertEquals(1, searchSoapProjectOutput.getSearchResults().size());

        SearchResult returnedSearchResult = searchSoapProjectOutput.getSearchResults().get(0);

        Assert.assertEquals("SOAP, Project", returnedSearchResult.getDescription());
        Assert.assertEquals("soap/project/" + soapProject.getId(), returnedSearchResult.getLink());
        Assert.assertEquals(soapProject.getName(), returnedSearchResult.getTitle());
    }



}
