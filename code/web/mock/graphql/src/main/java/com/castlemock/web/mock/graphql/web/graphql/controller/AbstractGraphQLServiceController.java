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

import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.model.http.dto.HttpHeaderDto;
import com.castlemock.core.mock.graphql.model.event.dto.GraphQLEventDto;
import com.castlemock.core.mock.graphql.model.event.dto.GraphQLRequestDto;
import com.castlemock.core.mock.graphql.model.event.dto.GraphQLResponseDto;
import com.castlemock.core.mock.graphql.model.event.service.message.input.CreateGraphQLEventInput;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLOperationStatus;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLOperationDto;
import com.castlemock.core.mock.graphql.model.project.service.message.input.IdentifyGraphQLOperationInput;
import com.castlemock.core.mock.graphql.model.project.service.message.output.IdentifyGraphQLOperationOutput;
import com.castlemock.web.basis.support.CharsetUtility;
import com.castlemock.web.basis.support.HttpMessageSupport;
import com.castlemock.web.basis.web.mvc.controller.AbstractController;
import com.castlemock.web.mock.graphql.model.GraphQLException;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public abstract class AbstractGraphQLServiceController extends AbstractController {


    private static final String GRAPHQL = "graphql";
    private static final Logger LOGGER = Logger.getLogger(AbstractGraphQLServiceController.class);
    
    protected ResponseEntity process(final String projectId, final String applicationId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){
        try{
            Preconditions.checkNotNull(projectId, "THe project id cannot be null");
            Preconditions.checkNotNull(httpServletRequest, "The HTTP Servlet Request cannot be null");
            final GraphQLRequestDto request = prepareRequest(projectId, httpServletRequest);
            final IdentifyGraphQLOperationOutput output = serviceProcessor.process(new IdentifyGraphQLOperationInput(projectId, applicationId,
                    request.getOperationIdentifier(), request.getUri(), request.getHttpMethod()));
            final GraphQLOperationDto operation = output.getGraphQLOperation();
            request.setOperationName(operation.getName());
            return process(projectId, output.getGraphQLApplicationId(), operation, request, httpServletResponse);
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
     * @param graphQLApplicationId The id of the port that the incoming request belong to
     * @param graphQLOperationDto The operation that contain the appropriate mocked response
     * @param request The incoming request
     * @param httpServletResponse The outgoing HTTP servlet response
     * @return Returns the response as an String
     */
    protected ResponseEntity process(final String graphQLProjectId, final String graphQLApplicationId, final GraphQLOperationDto graphQLOperationDto, final GraphQLRequestDto request, final HttpServletResponse httpServletResponse){
        Preconditions.checkNotNull(request, "Request cannot be null");
        if(graphQLOperationDto == null){
            throw new GraphQLException("Soap operation could not be found");
        }
        GraphQLEventDto event = null;
        GraphQLResponseDto response = null;
        try {
            event = new GraphQLEventDto(graphQLOperationDto.getName(), request, graphQLProjectId, graphQLApplicationId, graphQLOperationDto.getId());
            if (GraphQLOperationStatus.DISABLED.equals(graphQLOperationDto.getStatus())) {
                throw new GraphQLException("The requested graphQL operation, " + graphQLOperationDto.getName() + ", is disabled");
            } else if (GraphQLOperationStatus.FORWARDED.equals(graphQLOperationDto.getStatus())) {
                response = forwardRequest(request, graphQLProjectId, graphQLApplicationId, graphQLOperationDto);
            } else if (GraphQLOperationStatus.ECHO.equals(graphQLOperationDto.getStatus())) {
                response = echoResponse(request);
            } else { // Status.MOCKED
                response = mockResponse(request, graphQLProjectId, graphQLApplicationId, graphQLOperationDto);
            }

            HttpHeaders responseHeaders = new HttpHeaders();
            for(HttpHeaderDto httpHeader : response.getHttpHeaders()){
                List<String> headerValues = new LinkedList<String>();
                headerValues.add(httpHeader.getValue());
                responseHeaders.put(httpHeader.getName(), headerValues);
            }

            if(graphQLOperationDto.getSimulateNetworkDelay() &&
                    graphQLOperationDto.getNetworkDelay() >= 0){
                try {
                    Thread.sleep(graphQLOperationDto.getNetworkDelay());
                } catch (InterruptedException e) {
                    LOGGER.error("Unable to simulate network delay", e);
                }
            }

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
     * @param graphQLOperationDto The SOAP operation that is being executed
     * @return The response from the system that the request was forwarded to.
     */
    private GraphQLResponseDto forwardRequest(final GraphQLRequestDto request, final String graphQLProjectId, final String graphQLPortId, final GraphQLOperationDto graphQLOperationDto){
        if(demoMode){
            // If the application is configured to run in demo mode, then use mocked response instead
            return mockResponse(request, graphQLProjectId, graphQLPortId, graphQLOperationDto);
        }

        final GraphQLResponseDto response = new GraphQLResponseDto();
        HttpURLConnection connection = null;
        try {
            connection = HttpMessageSupport.establishConnection(
                    graphQLOperationDto.getForwardedEndpoint(),
                    request.getHttpMethod(),
                    request.getBody(),
                    request.getHttpHeaders());


            final List<ContentEncoding> encodings = HttpMessageSupport.extractContentEncoding(connection);
            final List<HttpHeaderDto> responseHttpHeaders = HttpMessageSupport.extractHttpHeaders(connection);
            final String characterEncoding = CharsetUtility.parseHttpHeaders(responseHttpHeaders);
            final String responseBody = HttpMessageSupport.extractHttpBody(connection, encodings, characterEncoding);
            response.setBody(responseBody);
            response.setHttpHeaders(responseHttpHeaders);
            response.setHttpStatusCode(connection.getResponseCode());
            response.setContentEncodings(encodings);
            return response;
        } catch (IOException exception) {
            LOGGER.error("Unable to forward request", exception);
            throw new GraphQLException("Unable to forward request to configured endpoint");
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }


    /**
     * The method is responsible for retrieving and returning a mocked response for the provided operation
     *
     * @param request
     * @param graphQLOperationDto The SOAP operation that is being executed. The response is based on
     *                         the provided SOAP operation.
     * @return A mocked response based on the provided SOAP operation
     */
    private GraphQLResponseDto mockResponse(GraphQLRequestDto request, final String graphQLProjectId, final String graphQLPortId, final GraphQLOperationDto graphQLOperationDto){
       
        final GraphQLResponseDto response = new GraphQLResponseDto();
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
