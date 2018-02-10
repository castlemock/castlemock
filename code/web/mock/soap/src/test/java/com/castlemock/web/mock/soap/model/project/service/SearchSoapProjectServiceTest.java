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
import com.castlemock.core.mock.soap.model.project.service.message.input.SearchSoapProjectInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.SearchSoapProjectOutput;
import com.castlemock.web.mock.soap.model.project.repository.SoapProjectRepository;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Arrays;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SearchSoapProjectServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapProjectRepository repository;

    @InjectMocks
    private SearchSoapProjectService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void testProcess(){
        SearchResult searchResult = new SearchResult();
        searchResult.setDescription("Description");
        searchResult.setLink("Link");
        searchResult.setTitle("Title");

        Mockito.when(repository.search(Mockito.any(SearchQuery.class))).thenReturn(Arrays.asList(searchResult));

        final String query = "Query";
        final SearchQuery searchQuery = new SearchQuery();
        searchQuery.setQuery(query);

        final SearchSoapProjectInput input = new SearchSoapProjectInput(searchQuery);
        final ServiceTask<SearchSoapProjectInput> serviceTask = new ServiceTask<SearchSoapProjectInput>();
        serviceTask.setInput(input);

        final ServiceResult<SearchSoapProjectOutput> serviceResult = service.process(serviceTask);
        final SearchSoapProjectOutput searchRestProjectOutput = serviceResult.getOutput();

        Assert.assertNotNull(searchRestProjectOutput.getSearchResults());
        Assert.assertEquals(1, searchRestProjectOutput.getSearchResults().size());

        SearchResult returnedSearchResult = searchRestProjectOutput.getSearchResults().get(0);

        Assert.assertEquals("Description", returnedSearchResult.getDescription());
        Assert.assertEquals("Link", returnedSearchResult.getLink());
        Assert.assertEquals("Title", returnedSearchResult.getTitle());
    }



}
