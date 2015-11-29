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

package com.fortmocks.web.basis.web.mvc.controller.search;

import com.fortmocks.core.basis.model.SearchQuery;
import com.fortmocks.core.basis.model.SearchResult;
import com.fortmocks.core.basis.model.project.service.ProjectServiceFacade;
import com.fortmocks.web.basis.web.mvc.command.search.SearchCommand;
import com.fortmocks.web.basis.web.mvc.controller.AbstractViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/search")
public class SearchController extends AbstractViewController {

    private static final String PAGE = "basis/search/search";
    private static final String SEARCH_RESULTS = "searchResults";

    @Autowired
    private ProjectServiceFacade projectServiceFacade;


    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView search(@ModelAttribute SearchCommand searchCommand) {
        final String query = searchCommand.getQuery();
        final SearchQuery searchQuery = new SearchQuery();
        searchQuery.setQuery(query);
        final List<SearchResult> searchResults = projectServiceFacade.search(searchQuery);
        ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(SEARCH_RESULTS, searchResults);
        return model;
    }

}
