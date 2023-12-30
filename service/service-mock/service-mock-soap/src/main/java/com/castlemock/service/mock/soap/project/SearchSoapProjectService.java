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
import com.castlemock.model.core.Service;
import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.service.mock.soap.project.input.SearchSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.SearchSoapProjectOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * The service provides the functionality to search for SOAP resources.
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class SearchSoapProjectService extends AbstractSoapProjectService implements Service<SearchSoapProjectInput, SearchSoapProjectOutput> {


    private static final String SLASH = "/";
    private static final String SOAP = "soap";
    private static final String PROJECT = "project";
    private static final String PORT = "port";
    private static final String OPERATION = "operation";
    private static final String RESPONSE = "response";
    private static final String RESOURCE = "resource";
    private static final String COMMA = ", ";
    private static final String SOAP_TYPE = "SOAP";
    private static final String WSDL_DIRECTORY = "wsdl";
    private static final String SCHEMA_DIRECTORY = "wsdl";

    @Autowired
    private MessageSource messageSource;

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
        final List<SearchResult> searchResults = new ArrayList<>();

        final List<SoapProject> projects = this.repository.search(searchQuery);
        final List<SoapPort> ports = this.portRepository.search(searchQuery);
        final List<SoapResource> resources = this.resourceRepository.search(searchQuery);
        final List<SoapOperation> operations = this.operationRepository.search(searchQuery);
        final List<SoapMockResponse> mockResponses = this.mockResponseRepository.search(searchQuery);

        final String projectType = messageSource.getMessage("general.type.project", null , LocaleContextHolder.getLocale());
        final String portType = messageSource.getMessage("soap.type.port", null , LocaleContextHolder.getLocale());
        final String operationType = messageSource.getMessage("soap.type.operation", null , LocaleContextHolder.getLocale());
        final String resourceType = messageSource.getMessage("soap.type.resource", null , LocaleContextHolder.getLocale());
        final String responseType = messageSource.getMessage("soap.type.mockresponse", null , LocaleContextHolder.getLocale());

        projects.forEach(project -> {
            final SearchResult searchResult = SearchResult.builder()
                    .title(project.getName())
                    .link(SOAP + SLASH + PROJECT + SLASH + project.getId())
                    .description(SOAP_TYPE + COMMA + projectType)
                    .build();
            searchResults.add(searchResult);
        });

        ports.forEach(port -> {
            final SearchResult searchResult = SearchResult.builder()
                    .title(port.getName())
                    .link(SOAP + SLASH + PROJECT + SLASH + port.getProjectId() + SLASH + PORT + SLASH + port.getId())
                    .description(SOAP_TYPE + COMMA + portType)
                    .build();
            searchResults.add(searchResult);
        });

        resources.forEach(resource -> {
            final SearchResult searchResult = SearchResult.builder()
                    .title(resource.getName())
                    .link(SOAP + SLASH + PROJECT + SLASH + resource.getProjectId() + SLASH + RESOURCE + SLASH + resource.getId())
                    .description(SOAP_TYPE + COMMA + resourceType)
                    .build();
            searchResults.add(searchResult);
        });

        operations.forEach(operation -> {
            final String projectId = this.portRepository.getProjectId(operation.getPortId());
            final SearchResult searchResult = SearchResult.builder()
                    .title(operation.getName())
                    .link(SOAP + SLASH + PROJECT + SLASH + projectId + SLASH + PORT +
                            SLASH + operation.getPortId() + SLASH + OPERATION + SLASH + operation.getId())
                    .description(SOAP_TYPE + COMMA + operationType)
                    .build();
            searchResults.add(searchResult);
        });

        mockResponses.forEach(mockResponse -> {
            final String portId = this.operationRepository.getPortId(mockResponse.getOperationId());
            final String projectId = this.portRepository.getProjectId(portId);
            final SearchResult searchResult = SearchResult.builder()
                    .title(mockResponse.getName())
                    .link(SOAP + SLASH + PROJECT + SLASH + projectId + SLASH + PORT +
                            SLASH + portId + SLASH + OPERATION + SLASH + mockResponse.getOperationId() +
                            SLASH + RESPONSE + SLASH + mockResponse.getId())
                    .description(SOAP_TYPE + COMMA + responseType)
                    .build();
            searchResults.add(searchResult);
        });

        return createServiceResult(SearchSoapProjectOutput.builder()
                .searchResults(searchResults)
                .build());
    }


}
