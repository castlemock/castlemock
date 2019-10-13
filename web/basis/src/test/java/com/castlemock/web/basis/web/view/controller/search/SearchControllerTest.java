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

package com.castlemock.web.basis.web.view.controller.search;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.service.project.ProjectServiceFacade;
import com.castlemock.web.basis.config.TestApplication;
import com.castlemock.web.basis.web.view.command.search.SearchCommand;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.basis.web.AbstractControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class SearchControllerTest extends AbstractControllerTest {

    private static final String PAGE = "partial/basis/search/search.jsp";
    private static final String SERVICE_URL = "/web/search";
    private static final String SEARCH_RESULTS = "searchResults";

    @InjectMocks
    private SearchController searchController;

    @Mock
    private ProjectServiceFacade projectServiceFacade;

    @Override
    protected AbstractController getController() {
        return searchController;
    }

    @Test
    public void testCreateUser() throws Exception {
        final List<SearchResult> searchResults = new ArrayList<SearchResult>();
        final SearchResult searchResult = new SearchResult();
        searchResult.setDescription("Description");
        searchResult.setLink("Link");
        searchResult.setTitle("Title");

        final SearchCommand searchCommand = new SearchCommand();
        searchCommand.setQuery("Query");

        when(projectServiceFacade.search(any(SearchQuery.class))).thenReturn(searchResults);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL, searchCommand);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SEARCH_RESULTS, searchResults));
    }



}
