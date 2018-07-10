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

package com.castlemock.web.mock.rest.web.mock.controller;

import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.domain.HttpParameter;
import com.castlemock.core.basis.utility.parser.TextParser;
import com.castlemock.core.basis.utility.parser.expression.PathParameterExpression;
import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgumentMap;
import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgumentString;
import com.castlemock.core.mock.rest.model.event.domain.RestEvent;
import com.castlemock.core.mock.rest.model.event.domain.RestRequest;
import com.castlemock.core.mock.rest.model.event.domain.RestResponse;
import com.castlemock.core.mock.rest.service.event.input.CreateRestEventInput;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.service.project.input.CreateRestMockResponseInput;
import com.castlemock.core.mock.rest.service.project.input.IdentifyRestMethodInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateCurrentRestMockResponseSequenceIndexInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.IdentifyRestMethodOutput;
import com.castlemock.web.basis.support.CharsetUtility;
import com.castlemock.web.basis.support.HttpMessageSupport;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.rest.model.RestException;
import com.castlemock.web.mock.rest.utility.compare.RestMockResponseNameComparator;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The abstract REST controller is a base class shared among all the REST service classes.
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractRestServiceController extends AbstractController {

    private static final String REST = "rest";
    private static final String APPLICATION = "application";
    private static final String FORWARDED_RESPONSE_NAME = "Forwarded response";
    private static final String RECORDED_RESPONSE_NAME = "Recorded response";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = Logger.getLogger(AbstractRestServiceController.class);

    private static final RestMockResponseNameComparator MOCK_RESPONSE_NAME_COMPARATOR =
            new RestMockResponseNameComparator();

    /**
     *
     * @param projectId The id of the project which the incoming request and mocked response belongs to
     * @param applicationId The id of the application which the incoming and mocked response belongs to
     * @param httpMethod The request method
     * @param httpServletRequest The incoming request
     * @param httpServletResponse The outgoing response
     * @return Returns the response as an String
     */
    protected ResponseEntity<String> process(final String projectId, final String applicationId, final HttpMethod httpMethod, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){
        try{
            Preconditions.checkNotNull(projectId, "The project id cannot be null");
            Preconditions.checkNotNull(applicationId, "The application id cannot be null");
            Preconditions.checkNotNull(httpMethod, "The REST method cannot be null");
            Preconditions.checkNotNull(httpServletRequest, "The HTTP Servlet Request cannot be null");
            Preconditions.checkNotNull(httpServletResponse, "The HTTP Servlet Response cannot be null");
            final RestRequest restRequest = prepareRequest(projectId, applicationId, httpMethod, httpServletRequest);
            final IdentifyRestMethodOutput output = serviceProcessor.process(new IdentifyRestMethodInput(projectId, applicationId, restRequest.getUri(), httpMethod));
            final String resourceId = output.getRestResourceId();

            return process(restRequest, projectId, applicationId, resourceId,
                    output.getRestMethod(), output.getPathParameters(), httpServletResponse);
        }

        catch (Exception exception){
            LOGGER.debug("REST service exception: " + exception.getMessage(), exception);
            throw new RestException(exception.getMessage());
        }
    }

    /**
     * The method prepares an request
     * @param projectId The id of the project that the incoming request belongs to
     * @param applicationId The id of the application that the incoming request belongs to
     * @param httpMethod The HTTP method that should be mocked, such as GET or POST
     * @param httpServletRequest The incoming request
     * @return A new created project
     */
    protected RestRequest prepareRequest(final String projectId, final String applicationId,
                                         final HttpMethod httpMethod, final HttpServletRequest httpServletRequest) {
        final RestRequest request = new RestRequest();
        final String body = HttpMessageSupport.getBody(httpServletRequest);
        final String incomingRequestUri = httpServletRequest.getRequestURI();
        final String restResourceUri = incomingRequestUri.replace(getContext() + SLASH + MOCK + SLASH + REST + SLASH + PROJECT + SLASH + projectId + SLASH + APPLICATION + SLASH + applicationId, EMPTY);
        final List<HttpParameter> httpParameters = HttpMessageSupport.extractParameters(httpServletRequest);
        final List<HttpHeader> httpHeaders = HttpMessageSupport.extractHttpHeaders(httpServletRequest);

        request.setHttpMethod(httpMethod);
        request.setBody(body);
        request.setUri(restResourceUri);
        request.setHttpParameters(httpParameters);
        request.setHttpHeaders(httpHeaders);
        request.setContentType(httpServletRequest.getContentType());
        return request;
    }

    /**
     * The process method provides the functionality to process an incoming request. The request will be identified
     * and a corresponding action will be applied for the request. The following actions are support:
     * Forward, record, mock or disable.
     * @param restRequest The incoming request
     * @param projectId The id of the project that the incoming request belongs to
     * @param applicationId The id of the application that the incoming request belongs to
     * @param resourceId The id of the resource that the incoming request belongs to
     * @param restMethod The REST method which the incoming request belongs to
     * @param httpServletResponse The HTTP servlet response
     * @return A response in String format
     */
    protected ResponseEntity<String> process(final RestRequest restRequest, final String projectId,
                                             final String applicationId, final String resourceId,
                                             final RestMethod restMethod, final Map<String, String> pathParameters,
                                             final HttpServletResponse httpServletResponse){
        Preconditions.checkNotNull(restRequest, "Rest request cannot be null");
        RestEvent event = null;
        RestResponse response = null;
        try {
            event = new RestEvent(restMethod.getName(), restRequest, projectId, applicationId, resourceId, restMethod.getId());
            if (RestMethodStatus.DISABLED.equals(restMethod.getStatus())) {
                throw new RestException("The requested REST method, " + restMethod.getName() + ", is disabled");
            } else if (RestMethodStatus.FORWARDED.equals(restMethod.getStatus())) {
                response = forwardRequest(restRequest, projectId, applicationId, resourceId, restMethod, pathParameters);
            } else if (RestMethodStatus.RECORDING.equals(restMethod.getStatus())) {
                response = forwardRequestAndRecordResponse(restRequest, projectId, applicationId, resourceId, restMethod, pathParameters);
            } else if (RestMethodStatus.RECORD_ONCE.equals(restMethod.getStatus())) {
                response = forwardRequestAndRecordResponseOnce(restRequest, projectId, applicationId, resourceId, restMethod, pathParameters);
            } else if (RestMethodStatus.ECHO.equals(restMethod.getStatus())) {
                response = echoResponse(restRequest);
            } else { // Status.MOCKED
                response = mockResponse(restRequest, projectId, applicationId, resourceId, restMethod, pathParameters);
            }

            HttpHeaders responseHeaders = new HttpHeaders();
            for(HttpHeader httpHeader : response.getHttpHeaders()){
                List<String> headerValues = new LinkedList<String>();
                headerValues.add(httpHeader.getValue());
                responseHeaders.put(httpHeader.getName(), headerValues);
            }

            if(restMethod.getSimulateNetworkDelay() &&
                    restMethod.getNetworkDelay() >= 0){
                try {
                    Thread.sleep(restMethod.getNetworkDelay());
                } catch (InterruptedException e) {
                    LOGGER.error("Unable to simulate network delay", e);
                }
            }

            return new ResponseEntity<String>(response.getBody(), responseHeaders, HttpStatus.valueOf(response.getHttpStatusCode()));
        } finally{
            if(event != null){
                event.finish(response);
                serviceProcessor.processAsync(new CreateRestEventInput(event));
            }
        }
    }

    /**
     * The method provides the functionality to forward a request to another endpoint
     * @param request The incoming request
     * @param restMethod The REST method which the incoming request belongs to
     * @return The response received from the external endpoint
     */
    protected RestResponse forwardRequest(final RestRequest request, final String projectId,
                                          final String applicationId, final String resourceId,
                                          final RestMethod restMethod, final Map<String, String> pathParameters){
        if(demoMode){
            // If the application is configured to run in demo mode, then use mocked response instead
            return mockResponse(request, projectId, applicationId, resourceId, restMethod, pathParameters);
        }


        final RestResponse response = new RestResponse();
        HttpURLConnection connection = null;
        try {

            String requestBody = null;

            if(HttpMethod.POST.equals(request.getHttpMethod()) ||
                    HttpMethod.PUT.equals(request.getHttpMethod()) ||
                    HttpMethod.DELETE.equals(request.getHttpMethod())){
                requestBody = request.getBody();
            }

            final String parameterUri = HttpMessageSupport.buildParameterUri(request.getHttpParameters());
            final String endpoint = restMethod.getForwardedEndpoint() + request.getUri() + parameterUri;

            connection = HttpMessageSupport.establishConnection(
                    endpoint,
                    request.getHttpMethod(),
                    requestBody,
                    request.getHttpHeaders());

            final List<ContentEncoding> encodings = HttpMessageSupport.extractContentEncoding(connection);
            final List<HttpHeader> responseHttpHeaders = HttpMessageSupport.extractHttpHeaders(connection);
            final String characterEncoding = CharsetUtility.parseHttpHeaders(responseHttpHeaders);
            final String responseBody = HttpMessageSupport.extractHttpBody(connection, encodings, characterEncoding);
            response.setBody(responseBody);
            response.setMockResponseName(FORWARDED_RESPONSE_NAME);
            response.setHttpHeaders(responseHttpHeaders);
            response.setHttpStatusCode(connection.getResponseCode());
            response.setContentEncodings(encodings);
            return response;
        } catch (IOException exception) {
            LOGGER.error("Unable to forward request", exception);
            throw new RestException("Unable to forward request to configured endpoint");
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }

    /**
     * The method provides the functionality to forward a request to another endpoint. The response
     * will be recorded and can later be used as a mocked response
     * @param restRequest The incoming request
     * @param restMethod The REST method which the incoming request belongs to
     * @return The response received from the external endpoint
     */
    protected RestResponse forwardRequestAndRecordResponse(final RestRequest restRequest, final String projectId,
                                                           final String applicationId, final String resourceId,
                                                           final RestMethod restMethod, final Map<String, String> pathParameters){
        final RestResponse response = forwardRequest(restRequest, projectId, applicationId, resourceId, restMethod, pathParameters);
        final RestMockResponse mockResponse = new RestMockResponse();
        final Date date = new Date();
        mockResponse.setBody(response.getBody());
        mockResponse.setStatus(RestMockResponseStatus.ENABLED);
        mockResponse.setHttpHeaders(response.getHttpHeaders());
        mockResponse.setName(RECORDED_RESPONSE_NAME + SPACE + DATE_FORMAT.format(date));
        mockResponse.setHttpStatusCode(response.getHttpStatusCode());
        serviceProcessor.processAsync(new CreateRestMockResponseInput(projectId, applicationId, resourceId, restMethod.getId(), mockResponse));
        return response;
    }

    /**
     * The method provides the functionality to forward a request to another endpoint. The response
     * will be recorded and can later be used as a mocked response. The REST method status will be updated
     * to mocked.
     * @param restRequest The incoming request
     * @param projectId The id of the project that the incoming request belongs to
     * @param applicationId The id of the application that the incoming request belongs to
     * @param resourceId The id of the resource that the incoming request belongs to
     * @param restMethod The REST method which the incoming request belongs to
     * @return The response received from the external endpoint
     */
    protected RestResponse forwardRequestAndRecordResponseOnce(final RestRequest restRequest, final String projectId,
                                                               final String applicationId, final String resourceId,
                                                               final RestMethod restMethod, final Map<String, String> pathParameters){
        final RestResponse response =
                forwardRequestAndRecordResponse(restRequest, projectId,
                        applicationId, resourceId, restMethod, pathParameters);
        restMethod.setStatus(RestMethodStatus.MOCKED);
        serviceProcessor.process(new UpdateRestMethodInput(projectId, applicationId, resourceId, restMethod.getId(), restMethod));
        return response;
    }

    /**
     * The method will echo the incoming {@link RestRequest} and create a {@link RestResponse}
     * with the same body, content type and headers.
     *
     * @param request The incoming {@link RestRequest} that will be echoed back to the
     *                service consumer.
     * @return A {@link RestResponse} based on the provided {@link RestRequest}.
     * @since 1.14
     */
    private RestResponse echoResponse(final RestRequest request) {
        final List<HttpHeader> headers = new ArrayList<HttpHeader>();
        final HttpHeader contentTypeHeader = new HttpHeader();
        contentTypeHeader.setName(CONTENT_TYPE);
        contentTypeHeader.setValue(request.getContentType());
        headers.add(contentTypeHeader);

        final RestResponse response = new RestResponse();
        response.setBody(request.getBody());
        response.setContentType(request.getContentType());
        response.setHttpHeaders(headers);
        response.setHttpStatusCode(DEFAULT_ECHO_RESPONSE_CODE);
        return response;
    }

    /**
     * The method is responsible for mocking a REST service. The method will identify which mocked response
     * will be returned.
     * @param restMethod The REST method which the incoming request belongs to
     * @return Returns a selected mocked response which will be returned to the service consumer
     */
    protected RestResponse mockResponse(final RestRequest restRequest, final String projectId,
                                        final String applicationId, final String resourceId,
                                        final RestMethod restMethod, final Map<String, String> pathParameters){
        // Extract the accept header value.
        final Collection<String> acceptHeaderValues = getHeaderValues(ACCEPT_HEADER, restRequest.getHttpHeaders());


        // Iterate through all mocked responses and extract the ones
        // that are active.
        final List<RestMockResponse> enabledMockResponses = new ArrayList<RestMockResponse>();
        for(RestMockResponse mockResponse : restMethod.getMockResponses()){
            if(mockResponse.getStatus().equals(RestMockResponseStatus.ENABLED)){
                enabledMockResponses.add(mockResponse);
            }
        }

        enabledMockResponses.sort(MOCK_RESPONSE_NAME_COMPARATOR);

        List<RestMockResponse> mockResponses = new ArrayList<>();

        if(acceptHeaderValues != null){
            // Find request that matches the accept header.
            for(RestMockResponse mockResponse : enabledMockResponses){
                // The accept header has to match the content type.
                final Collection<String> mockResponseContentTypeValues = getHeaderValues(CONTENT_TYPE, mockResponse.getHttpHeaders());
                mockResponseContentTypeValues.retainAll(acceptHeaderValues);
                if(!mockResponseContentTypeValues.isEmpty()){
                    mockResponses.add(mockResponse);
                }
            }
        }

        // If no mock responses are matching the accept header, then
        // any mock response will do.
        if(mockResponses.isEmpty()){
            mockResponses.addAll(enabledMockResponses);
        }

        RestMockResponse mockResponse = null;
        if(mockResponses.isEmpty()){
            throw new RestException("No mocked response created for operation " + restMethod.getName());
        } else if(restMethod.getResponseStrategy().equals(RestResponseStrategy.RANDOM)){
            final Integer responseIndex = RANDOM.nextInt(mockResponses.size());
            mockResponse = mockResponses.get(responseIndex);
        } else if(restMethod.getResponseStrategy().equals(RestResponseStrategy.SEQUENCE)){
            Integer currentSequenceNumber = restMethod.getCurrentResponseSequenceIndex();
            if(currentSequenceNumber >= mockResponses.size()){
                currentSequenceNumber = 0;
            }
            mockResponse = mockResponses.get(currentSequenceNumber);
            serviceProcessor.process(new UpdateCurrentRestMockResponseSequenceIndexInput(projectId,
                    applicationId, resourceId, restMethod.getId(), currentSequenceNumber + 1));
        }

        if(mockResponse == null){
            throw new RestException("No mocked response created for operation " + restMethod.getName());
        }

        String body = mockResponse.getBody();
        if(mockResponse.isUsingExpressions()){
            final ExpressionArgumentMap pathParametersArgument = new ExpressionArgumentMap();

            for(Map.Entry<String, String> pathParameter : pathParameters.entrySet()){
                ExpressionArgument pathParameterArgument = new ExpressionArgumentString(pathParameter.getValue());
                pathParametersArgument.addArgument(pathParameter.getKey(), pathParameterArgument);
            }

            final Map<String, ExpressionArgument<?>> externalInput =
                    Collections.singletonMap(PathParameterExpression.PATH_PARAMETERS, pathParametersArgument);


            // Parse the text and apply expression functionality if
            // the mock response is configured to use expressions
            body = TextParser.parse(body, externalInput);
        }
        final RestResponse response = new RestResponse();
        response.setBody(body);
        response.setMockResponseName(mockResponse.getName());
        response.setHttpStatusCode(mockResponse.getHttpStatusCode());
        response.setHttpHeaders(mockResponse.getHttpHeaders());
        response.setContentEncodings(mockResponse.getContentEncodings());
        return response;
    }

    /**
     * The method returns a list of values for a header ({@link HttpHeader}).
     * @param headerName The name of the header which value will be extracted and returned.
     * @param httpHeaders A list of {@link HttpHeader}s
     * @return A list of HTTP header values
     * @since 1.13
     */
    private Collection<String> getHeaderValues(final String headerName, final List<HttpHeader> httpHeaders){
        HttpHeader httpHeader = null;
        for(HttpHeader tmpHeader : httpHeaders){
            if(headerName.equalsIgnoreCase(tmpHeader.getName())){
                httpHeader = tmpHeader;
            }
        }

        List<String> result = new ArrayList<>();

        if(httpHeader != null){
            String value = httpHeader.getValue();
            String[] headerValues = value.split(",");
            for(String headerValue : headerValues){
                result.add(headerValue.toLowerCase());
            }

        }

        return result;
    }
}
