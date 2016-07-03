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

import java.util.List;

/**
 * The service provides the functionality to search for SOAP resources.
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class SearchSoapProjectService extends AbstractSoapProjectService implements Service<SearchSoapProjectInput, SearchSoapProjectOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<SearchSoapProjectOutput> process(final ServiceTask<SearchSoapProjectInput> serviceTask) {
        final SearchSoapProjectInput input = serviceTask.getInput();
        final SearchQuery searchQuery = input.getSearchQuery();
        final List<SearchResult> searchResults = repository.search(searchQuery);
        return createServiceResult(new SearchSoapProjectOutput(searchResults));
    }


}
