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

package com.fortmocks.web.mock.soap.model.project.service;

import com.fortmocks.core.basis.model.*;
import com.fortmocks.core.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.core.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.core.mock.soap.model.project.domain.SoapPort;
import com.fortmocks.core.mock.soap.model.project.domain.SoapProject;
import com.fortmocks.core.mock.soap.model.project.service.message.input.SearchSoapProjectInput;
import com.fortmocks.core.mock.soap.model.project.service.message.output.SearchSoapProjectOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * The service provides the functionality to search for SOAP resources.
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class SearchSoapProjectService extends AbstractSoapProjectService implements Service<SearchSoapProjectInput, SearchSoapProjectOutput> {

    @Autowired
    private MessageSource messageSource;

    private static final String SLASH = "/";
    private static final String SOAP = "soap";
    private static final String PROJECT = "project";
    private static final String PORT = "port";
    private static final String OPERATION = "operation";
    private static final String RESPONSE = "response";
    private static final String COMMA = ", ";
    private static final String SOAP_TYPE = "SOAP";

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
        final String query = searchQuery.getQuery();
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        for(SoapProject soapProject : findAllTypes()){
            List<SearchResult> soapProjectSearchResult = searchSoapProject(soapProject, query);
            searchResults.addAll(soapProjectSearchResult);
        }

        return createServiceResult(new SearchSoapProjectOutput(searchResults));
    }

    /**
     * Search through a SOAP project and all its resources
     * @param soapProject The SOAP project which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchSoapProject(final SoapProject soapProject, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(soapProject.getName(), query)){
            final String projectType = messageSource.getMessage("general.type.project", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(soapProject.getName());
            searchResult.setLink(SOAP + SLASH + PROJECT + SLASH + soapProject.getId());
            searchResult.setDescription(SOAP_TYPE + COMMA + projectType);
            searchResults.add(searchResult);
        }


        for(SoapPort soapPort : soapProject.getSoapPorts()){
            List<SearchResult> soapOperationSearchResult = searchSoapPort(soapProject, soapPort, query);
            searchResults.addAll(soapOperationSearchResult);
        }
        return searchResults;
    }

    /**
     * Search through a SOAP port and all its resources
     * @param soapProject The SOAP project which will be searched
     * @param soapPort The SOAP port which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchSoapPort(final SoapProject soapProject, final SoapPort soapPort, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(soapPort.getName(), query)){
            final String portType = messageSource.getMessage("soap.type.port", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(soapPort.getName());
            searchResult.setLink(SOAP + SLASH + PROJECT + SLASH + soapProject.getId() + SLASH + PORT + SLASH + soapPort.getId());
            searchResult.setDescription(SOAP_TYPE + COMMA + portType);
            searchResults.add(searchResult);
        }

        for(SoapOperation soapOperation : soapPort.getSoapOperations()){
            List<SearchResult> soapOperationSearchResult = searchSoapOperation(soapProject, soapPort, soapOperation, query);
            searchResults.addAll(soapOperationSearchResult);
        }
        return searchResults;
    }

    /**
     * Search through a SOAP operation and all its resources
     * @param soapProject The SOAP project which will be searched
     * @param soapPort The SOAP port which will be searched
     * @param soapOperation The SOAP operation which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchSoapOperation(final SoapProject soapProject, final SoapPort soapPort, final SoapOperation soapOperation, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(soapOperation.getName(), query)){
            final String operationType = messageSource.getMessage("soap.type.operation", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(soapOperation.getName());
            searchResult.setLink(SOAP + SLASH + PROJECT + SLASH + soapProject.getId() + SLASH + PORT + SLASH + soapPort.getId() + SLASH + OPERATION + SLASH + soapOperation.getId());
            searchResult.setDescription(SOAP_TYPE + COMMA + operationType);
            searchResults.add(searchResult);
        }

        for(SoapMockResponse soapMockResponse : soapOperation.getSoapMockResponses()){
            SearchResult soapMockResponseSearchResult = searchSoapMockResponse(soapProject, soapPort, soapOperation, soapMockResponse, query);
            if(soapMockResponseSearchResult != null){
                searchResults.add(soapMockResponseSearchResult);
            }
        }
        return searchResults;
    }

    /**
     * Search through a SOAP response
     * @param soapProject The SOAP project which will be searched
     * @param soapPort The SOAP port which will be searched
     * @param soapOperation The SOAP operation which will be searched
     * @param soapMockResponse The SOAP response that will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private SearchResult searchSoapMockResponse(final SoapProject soapProject, final SoapPort soapPort, final SoapOperation soapOperation, final SoapMockResponse soapMockResponse, final String query){
        SearchResult searchResult = null;

        if(SearchValidator.validate(soapMockResponse.getName(), query)){
            final String mockResponseType = messageSource.getMessage("soap.type.mockresponse", null , LocaleContextHolder.getLocale());
            searchResult = new SearchResult();
            searchResult.setTitle(soapMockResponse.getName());
            searchResult.setLink(SOAP + SLASH + PROJECT + SLASH + soapProject.getId() + SLASH + PORT + SLASH + soapPort.getId() + SLASH + OPERATION + SLASH + soapOperation.getId() + SLASH + RESPONSE + SLASH + soapMockResponse.getId());
            searchResult.setDescription(SOAP_TYPE + COMMA + mockResponseType);
        }

        return searchResult;
    }

}
