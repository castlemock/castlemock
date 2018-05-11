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
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.service.message.input.SearchSoapProjectInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.SearchSoapProjectOutput;
import com.castlemock.web.mock.soap.model.project.SoapProjectGenerator;
import com.castlemock.web.mock.soap.model.project.repository.*;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
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
        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void testProcess(){
        SoapProject soapProject = SoapProjectGenerator.generateSoapProject();

        Mockito.when(repository.search(Mockito.any(SearchQuery.class))).thenReturn(Arrays.asList(soapProject));
        Mockito.when(portRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());
        Mockito.when(operationRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());
        Mockito.when(resourceRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());
        Mockito.when(mockResponseRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());

        Mockito.when(messageSource.getMessage("general.type.project", null, LocaleContextHolder.getLocale())).thenReturn("Project");

        final String query = "Query";
        final SearchQuery searchQuery = new SearchQuery();
        searchQuery.setQuery(query);

        final SearchSoapProjectInput input = new SearchSoapProjectInput(searchQuery);
        final ServiceTask<SearchSoapProjectInput> serviceTask = new ServiceTask<SearchSoapProjectInput>();
        serviceTask.setInput(input);

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
