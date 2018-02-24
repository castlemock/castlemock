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
import com.castlemock.core.basis.model.http.dto.HttpHeaderDto;
import com.castlemock.core.mock.graphql.model.event.dto.GraphQLEventDto;
import com.castlemock.core.mock.graphql.model.event.dto.GraphQLRequestDto;
import com.castlemock.core.mock.graphql.model.event.dto.GraphQLResponseDto;
import com.castlemock.core.mock.graphql.model.event.service.message.input.CreateGraphQLEventInput;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLProjectDto;
import com.castlemock.core.mock.graphql.model.project.service.message.input.ReadGraphQLProjectInput;
import com.castlemock.core.mock.graphql.model.project.service.message.output.ReadGraphQLProjectOutput;
import com.castlemock.web.basis.support.HttpMessageSupport;
import com.castlemock.web.basis.web.mvc.controller.AbstractController;
import com.castlemock.web.mock.graphql.converter.query.GraphQLRequestQuery;
import com.castlemock.web.mock.graphql.converter.query.QueryGraphQLConverter;
import com.castlemock.web.mock.graphql.model.GraphQLException;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public abstract class AbstractGraphQLServiceController extends AbstractController {


    private static final String GRAPHQL = "graphql";
    private static final Logger LOGGER = Logger.getLogger(AbstractGraphQLServiceController.class);

    private final QueryGraphQLConverter queryConverter = new QueryGraphQLConverter();
    private final GraphQLResponseGenerator generator = new GraphQLResponseGenerator();
    
    protected ResponseEntity process(final String projectId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){
        try{
            Preconditions.checkNotNull(projectId, "THe project id cannot be null");
            Preconditions.checkNotNull(httpServletRequest, "The HTTP Servlet Request cannot be null");
            final GraphQLRequestDto request = prepareRequest(projectId, httpServletRequest);
            final ReadGraphQLProjectOutput output = serviceProcessor.process(new ReadGraphQLProjectInput(projectId));
            final GraphQLProjectDto project = output.getGraphQLProject();
            request.setOperationName(project.getName());
            return process(projectId, project, request, httpServletResponse);
        }catch(Exception exception){
            LOGGER.debug("SOAP service exception: " + exception.getMessage(), exception);
            throw new GraphQLException(exception.getMessage());
        }
    }


    /**
     * The method prepares an request
     * @param projectId The id of the project that the incoming request belongs to
     * @param httpServletRequest The incoming request
     * @return A new created project
     */
    protected GraphQLRequestDto prepareRequest(final String projectId, final HttpServletRequest httpServletRequest) {
        final GraphQLRequestDto request = new GraphQLRequestDto();
        final String body = HttpMessageSupport.getBody(httpServletRequest);

        final String identifier = "";
        final String serviceUri = httpServletRequest.getRequestURI().replace(getContext() + SLASH + MOCK + SLASH + GRAPHQL + SLASH + PROJECT + SLASH + projectId + SLASH, EMPTY);
        final List<HttpHeaderDto> httpHeaders = HttpMessageSupport.extractHttpHeaders(httpServletRequest);

        request.setHttpHeaders(httpHeaders);
        request.setUri(serviceUri);
        request.setHttpMethod(HttpMethod.valueOf(httpServletRequest.getMethod()));
        request.setBody(body);
        request.setOperationIdentifier(identifier);
        request.setContentType(httpServletRequest.getContentType());
        return request;
    }

    /**
     * The process method is responsible for processing the incoming request and
     * finding the appropriate response. The method is also responsible for creating
     * events and storing them.
     * @param graphQLProjectId The id of the project that the incoming request belong to
     * @param graphQLProject The operation that contain the appropriate mocked response
     * @param request The incoming request
     * @param httpServletResponse The outgoing HTTP servlet response
     * @return Returns the response as an String
     */
    protected ResponseEntity process(final String graphQLProjectId, final GraphQLProjectDto graphQLProject, final GraphQLRequestDto request, final HttpServletResponse httpServletResponse){
        Preconditions.checkNotNull(request, "Request cannot be null");
        if(graphQLProject == null){
            throw new GraphQLException("GraphQL project could not be found");
        }
        GraphQLEventDto event = null;
        GraphQLResponseDto response = null;
        try {
            response = mockResponse(request, graphQLProjectId, graphQLProject);
            event = new GraphQLEventDto("", request, graphQLProjectId, "", "");
            /*
            event = new GraphQLEventDto(graphQLOperationDto.getName(), request, graphQLProjectId, graphQLApplicationId, graphQLOperationDto.getId());

            if (GraphQLOperationStatus.DISABLED.equals(graphQLOperationDto.getStatus())) {
                throw new GraphQLException("The requested graphQL operation, " + graphQLOperationDto.getName() + ", is disabled");
            } else if (GraphQLOperationStatus.FORWARDED.equals(graphQLOperationDto.getStatus())) {
                response = forwardRequest(request, graphQLProjectId, graphQLApplicationId, graphQLOperationDto);
            } else if (GraphQLOperationStatus.ECHO.equals(graphQLOperationDto.getStatus())) {
                response = echoResponse(request);
            } else { // Status.MOCKED
                response = mockResponse(request, graphQLProjectId, graphQLProject);
            }
            */

            HttpHeaders responseHeaders = new HttpHeaders();
            for(HttpHeaderDto httpHeader : response.getHttpHeaders()){
                List<String> headerValues = new LinkedList<String>();
                headerValues.add(httpHeader.getValue());
                responseHeaders.put(httpHeader.getName(), headerValues);
            }

            /*
            if(graphQLOperationDto.getSimulateNetworkDelay() &&
                    graphQLOperationDto.getNetworkDelay() >= 0){
                try {
                    Thread.sleep(graphQLOperationDto.getNetworkDelay());
                } catch (InterruptedException e) {
                    LOGGER.error("Unable to simulate network delay", e);
                }
            }
            */

            return new ResponseEntity<String>(response.getBody(), responseHeaders, HttpStatus.valueOf(response.getHttpStatusCode()));
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
    private GraphQLResponseDto forwardRequest(final GraphQLRequestDto request, final String graphQLProjectId, final String graphQLPortId, final GraphQLProjectDto graphQLProject){
        return null;
    }


    /**
     * The method is responsible for retrieving and returning a mocked response for the provided operation
     *
     * @param request
     * @param graphQLProject The SOAP operation that is being executed. The response is based on
     *                         the provided SOAP operation.
     * @return A mocked response based on the provided SOAP operation
     */
    private GraphQLResponseDto mockResponse(GraphQLRequestDto request, final String graphQLProjectId, final GraphQLProjectDto graphQLProject){
        final List<GraphQLRequestQuery> queries = queryConverter.parseQuery(request.getBody());
        final String output = generator.getResponse(graphQLProject, queries);

        final GraphQLResponseDto response = new GraphQLResponseDto();
        response.setBody(output);
        response.setContentType("application/json");
        response.setHttpStatusCode(200);
        response.setHttpHeaders(new ArrayList<>());
        return response;

    }

    /**
     * The method will echo the incoming {@link GraphQLRequestDto} and create a {@link GraphQLResponseDto}
     * with the same body, content type and headers.
     *
     * @param request The incoming {@link GraphQLRequestDto} that will be echoed back to the
     *                service consumer.
     * @return A {@link GraphQLResponseDto} based on the provided {@link GraphQLRequestDto}.
     * @since 1.14
     */
    private GraphQLResponseDto echoResponse(final GraphQLRequestDto request) {
        final List<HttpHeaderDto> headers = new ArrayList<HttpHeaderDto>();
        final HttpHeaderDto contentTypeHeader = new HttpHeaderDto();
        contentTypeHeader.setName(CONTENT_TYPE);
        contentTypeHeader.setValue(request.getContentType());
        headers.add(contentTypeHeader);

        final GraphQLResponseDto response = new GraphQLResponseDto();
        response.setBody(request.getBody());
        response.setContentType(request.getContentType());
        response.setHttpHeaders(headers);
        response.setHttpStatusCode(DEFAULT_ECHO_RESPONSE_CODE);
        return response;
    }

}
