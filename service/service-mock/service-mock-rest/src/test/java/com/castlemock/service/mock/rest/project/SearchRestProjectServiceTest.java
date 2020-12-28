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

package com.castlemock.service.mock.rest.project;

import com.castlemock.model.core.model.SearchQuery;
import com.castlemock.model.core.model.SearchResult;
import com.castlemock.model.core.model.ServiceResult;
import com.castlemock.model.core.model.ServiceTask;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.model.mock.rest.domain.RestProjectTestBuilder;
import com.castlemock.service.mock.rest.project.input.SearchRestProjectInput;
import com.castlemock.service.mock.rest.project.output.SearchRestProjectOutput;
import com.castlemock.repository.rest.project.RestApplicationRepository;
import com.castlemock.repository.rest.project.RestMethodRepository;
import com.castlemock.repository.rest.project.RestMockResponseRepository;
import com.castlemock.repository.rest.project.RestProjectRepository;
import com.castlemock.repository.rest.project.RestResourceRepository;
import org.junit.Assert;
import org.junit.Before;
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
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testProcess(){
        RestProject restProject = RestProjectTestBuilder.builder().build();

        Mockito.when(repository.search(Mockito.any(SearchQuery.class))).thenReturn(Arrays.asList(restProject));
        Mockito.when(applicationRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());
        Mockito.when(resourceRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());
        Mockito.when(methodRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());
        Mockito.when(mockResponseRepository.search(Mockito.any(SearchQuery.class))).thenReturn(Collections.emptyList());

        Mockito.when(messageSource.getMessage("general.type.project", null, LocaleContextHolder.getLocale())).thenReturn("Project");

        final String query = "Query";
        final SearchQuery searchQuery = new SearchQuery();
        searchQuery.setQuery(query);

        final SearchRestProjectInput input = SearchRestProjectInput.builder().searchQuery(searchQuery).build();
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
