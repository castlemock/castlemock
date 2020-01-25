/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.mock.graphql.web.graphql.controller;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.mock.graphql.model.event.domain.GraphQLEvent;
import com.castlemock.core.mock.graphql.model.event.domain.GraphQLRequest;
import com.castlemock.core.mock.graphql.model.event.domain.GraphQLResponse;
import com.castlemock.core.mock.graphql.service.event.input.CreateGraphQLEventInput;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLApplication;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLOperation;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLProject;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLRequestQuery;
import com.castlemock.core.mock.graphql.service.project.input.IdentifyGraphQLOperationInput;
import com.castlemock.core.mock.graphql.service.project.output.IdentifyGraphQLOperationOutput;
import com.castlemock.web.basis.support.HttpMessageSupport;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.graphql.converter.query.QueryGraphQLConverter;
import com.castlemock.web.mock.graphql.model.GraphQLException;
import com.google.common.base.Preconditions;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public abstract class AbstractGraphQLServiceController extends AbstractController {

    private static final String GRAPHQL = "graphql";
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGraphQLServiceController.class);

    private final QueryGraphQLConverter queryConverter = new QueryGraphQLConverter();
    private final GraphQLResponseGenerator generator = new GraphQLResponseGenerator();
    
    protected ResponseEntity<?> process(final String projectId,
                                     final String applicationId,
                                     final HttpServletRequest httpServletRequest,
                                     final HttpServletResponse httpServletResponse){
        try{
            Preconditions.checkNotNull(projectId, "THe project id cannot be null");
            Preconditions.checkNotNull(httpServletRequest, "The HTTP Servlet Request cannot be null");
            final GraphQLRequest request = prepareRequest(projectId, httpServletRequest);

            final IdentifyGraphQLOperationOutput output =
                    serviceProcessor.process(new IdentifyGraphQLOperationInput(projectId, applicationId, request.getQueries()));
            final GraphQLApplication application = output.getGraphQLApplication();
            final Map<GraphQLRequestQuery, GraphQLOperation> operations = output.getOperation();
            return process(projectId, application, operations, request, httpServletResponse);
        }catch(Exception exception){
            LOGGER.debug("GraphQL service exception: " + exception.getMessage(), exception);
            throw new GraphQLException(exception.getMessage());
        }
    }


    /**
     * The method prepares an request
     * @param projectId The id of the project that the incoming request belongs to
     * @param httpServletRequest The incoming request
     * @return A new created project
     */
    private GraphQLRequest prepareRequest(final String projectId, final HttpServletRequest httpServletRequest) {
        final GraphQLRequest request = new GraphQLRequest();
        final String body = HttpMessageSupport.getBody(httpServletRequest);

        final List<GraphQLRequestQuery> queries = queryConverter.parseQuery(body);
        final String serviceUri = httpServletRequest
                .getRequestURI()
                .replace(getContext() + SLASH + MOCK + SLASH + GRAPHQL +
                        SLASH + PROJECT + SLASH + projectId + SLASH, EMPTY);
        final List<HttpHeader> httpHeaders = HttpMessageSupport.extractHttpHeaders(httpServletRequest);

        request.setHttpHeaders(httpHeaders);
        request.setUri(serviceUri);
        request.setHttpMethod(HttpMethod.valueOf(httpServletRequest.getMethod()));
        request.setBody(body);
        request.setQueries(queries);
        request.setContentType(httpServletRequest.getContentType());
        return request;
    }

    /**
     * The process method is responsible for processing the incoming request and
     * finding the appropriate response. The method is also responsible for creating
     * events and storing them.
     * @param application The operation that contain the appropriate mocked response
     * @param request The incoming request
     * @param httpServletResponse The outgoing HTTP servlet response
     * @return Returns the response as an String
     */
    private ResponseEntity<?> process(final String projectId,
                                     final GraphQLApplication application,
                                     final Map<GraphQLRequestQuery, GraphQLOperation> operations,
                                     final GraphQLRequest request,
                                     final HttpServletResponse httpServletResponse){
        Preconditions.checkNotNull(request, "Request cannot be null");
        if(application == null){
            throw new GraphQLException("GraphQL project could not be found");
        }
        GraphQLEvent event = null;
        GraphQLResponse response = null;
        try {
            /*
            for(Map.Entry<GraphQLRequestQuery, GraphQLOperation> operationEntry : operations.entrySet()){
                GraphQLOperation operation = operationEntry.getValue();
                if(operation.getStatus().equals(GraphQLOperationStatus.MOCKED)){

                }
            }
            */

            response = mockResponse(request, application, operations);
            event = new GraphQLEvent(request, projectId, application.getId());

            HttpHeaders responseHeaders = new HttpHeaders();
            for(HttpHeader httpHeader : response.getHttpHeaders()){
                List<String> headerValues = new LinkedList<String>();
                headerValues.add(httpHeader.getValue());
                responseHeaders.put(httpHeader.getName(), headerValues);
            }

            return new ResponseEntity<String>(response.getBody(), responseHeaders,
                    HttpStatus.valueOf(response.getHttpStatusCode()));
        } finally{
            if(event != null){
                event.finish(response);
                serviceProcessor.processAsync(new CreateGraphQLEventInput(event));
            }
        }
    }


    /**
     * The method provides the functionality to forward request to a forwarded endpoint.
     * @param request The request that will be forwarded
     * @return The response from the system that the request was forwarded to.
     */
    private GraphQLResponse forwardRequest(final GraphQLRequest request, final String graphQLProjectId, final String graphQLPortId, final GraphQLProject graphQLProject){
        return null;
    }


    /**
     * The method is responsible for retrieving and returning a mocked response for the provided operation
     *
     * @param request
     * @param application The GraphQL application that is being executed. The response is based on
     *                         the provided GraphQL operation.
     * @return A mocked response based on the provided GraphQL operation
     */
    private GraphQLResponse mockResponse(final GraphQLRequest request,
                                         final GraphQLApplication application,
                                         final Map<GraphQLRequestQuery, GraphQLOperation> operations){
        final String output = generator.getResponse(application, new ArrayList<>(operations.keySet()));

        final GraphQLResponse response = new GraphQLResponse();
        response.setBody(output);
        response.setContentType("application/json");
        response.setHttpStatusCode(200);
        response.setHttpHeaders(new ArrayList<>());
        return response;

    }
}
