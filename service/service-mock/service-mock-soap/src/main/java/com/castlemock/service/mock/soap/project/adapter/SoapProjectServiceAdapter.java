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

package com.castlemock.service.mock.soap.project.adapter;

import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.service.project.ProjectServiceAdapter;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.service.mock.soap.project.input.ReadAllSoapProjectsInput;
import com.castlemock.service.mock.soap.project.input.SearchSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.ReadAllSoapProjectsOutput;
import com.castlemock.service.mock.soap.project.output.SearchSoapProjectOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The SOAP project service adapter is responsible for providing the basic functionality for all the
 * project services.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SoapProject
 */
@Service
public class SoapProjectServiceAdapter implements ProjectServiceAdapter<SoapProject> {

    @Autowired
    private ServiceProcessor serviceProcessor;

    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    @Override
    public List<SoapProject> readAll() {
        final ReadAllSoapProjectsOutput output = serviceProcessor.process(ReadAllSoapProjectsInput.builder().build());
        return output.getProjects();
    }

    @Override
    public String getType() {
        return "soap";
    }

    /**
     * Searches for resources that matches the provided query. The matching resources will
     * be returned as a collection of {@link SearchResult}
     * @param searchQuery The search query that will be used to identify the resources
     * @return A list of search results
     */
    @Override
    public List<SearchResult> search(final SearchQuery searchQuery) {
        final SearchSoapProjectOutput output = serviceProcessor.process(SearchSoapProjectInput.builder()
                .searchQuery(searchQuery)
                .build());
        return output.getSearchResults();
    }
}
