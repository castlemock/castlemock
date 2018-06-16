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

package com.castlemock.web.mock.rest.service.project.test;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.service.project.input.SearchRestProjectInput;
import com.castlemock.core.mock.rest.service.project.output.SearchRestProjectOutput;
import com.castlemock.web.mock.rest.model.project.RestProjectGenerator;
import com.castlemock.web.mock.rest.repository.project.*;
import com.castlemock.web.mock.rest.service.project.SearchRestProjectService;
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

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SearchRestProjectServiceTest {

    @Mock
    private MessageSource messageSource;

    @Mock
    private RestProjectRepository repository;
    @Mock
    private RestApplicationRepository applicationRepository;
    @Mock
    private RestResourceRepository resourceRepository;
    @Mock
    private RestMethodRepository methodRepository;
    @Mock
    private RestMockResponseRepository mockResponseRepository;

    @InjectMocks
    private SearchRestProjectService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @Ignore
    public void testProcess(){
        RestProject restProject = RestProjectGenerator.generateRestProject();

        Mockito.when(repository.search(Mockito.any(SearchQuery.class))).thenReturn(Arrays.asList(restProject));
        Mockito.when(applicationRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());
        Mockito.when(resourceRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());
        Mockito.when(methodRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());
        Mockito.when(mockResponseRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());

        Mockito.when(messageSource.getMessage("general.type.project", null, LocaleContextHolder.getLocale())).thenReturn("Project");

        final String query = "Query";
        final SearchQuery searchQuery = new SearchQuery();
        searchQuery.setQuery(query);

        final SearchRestProjectInput input = new SearchRestProjectInput(searchQuery);
        final ServiceTask<SearchRestProjectInput> serviceTask = new ServiceTask<SearchRestProjectInput>();
        serviceTask.setInput(input);

        final ServiceResult<SearchRestProjectOutput> serviceResult = service.process(serviceTask);
        final SearchRestProjectOutput searchRestProjectOutput = serviceResult.getOutput();

        Assert.assertNotNull(searchRestProjectOutput.getSearchResults());
        Assert.assertEquals(1, searchRestProjectOutput.getSearchResults().size());

        SearchResult returnedSearchResult = searchRestProjectOutput.getSearchResults().get(0);

        Assert.assertEquals("REST, Project", returnedSearchResult.getDescription());
        Assert.assertEquals("rest/project/" + restProject.getId(), returnedSearchResult.getLink());
        Assert.assertEquals(restProject.getName(), returnedSearchResult.getTitle());
    }
}
