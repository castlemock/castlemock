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

package com.castlemock.web.mock.rest.web.rest.controller;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.model.http.dto.HttpHeaderDto;
import com.castlemock.core.basis.model.http.dto.HttpParameterDto;
import com.castlemock.core.basis.utility.parser.TextParser;
import com.castlemock.core.mock.rest.model.event.dto.RestEventDto;
import com.castlemock.core.mock.rest.model.event.dto.RestRequestDto;
import com.castlemock.core.mock.rest.model.event.dto.RestResponseDto;
import com.castlemock.core.mock.rest.model.event.service.message.input.CreateRestEventInput;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.castlemock.core.mock.rest.model.project.dto.RestMethodDto;
import com.castlemock.core.mock.rest.model.project.dto.RestMockResponseDto;
import com.castlemock.core.mock.rest.model.project.service.message.input.CreateRestMockResponseInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.IdentifyRestMethodInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.UpdateCurrentRestMockResponseSequenceIndexInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.UpdateRestMethodInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.IdentifyRestMethodOutput;
import com.castlemock.web.basis.support.HttpMessageSupport;
import com.castlemock.web.basis.web.mvc.controller.AbstractController;
import com.castlemock.web.mock.rest.model.RestException;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private static final Integer OK_RESPONSE = 200;


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

            final RestRequestDto restRequest = prepareRequest(projectId, applicationId, httpMethod, httpServletRequest);
            final IdentifyRestMethodOutput output = serviceProcessor.process(new IdentifyRestMethodInput(projectId, applicationId, restRequest.getUri(), httpMethod));
            final String resourceId = output.getRestResourceId();

            return process(restRequest, projectId, applicationId, resourceId, output.getRestMethod(), httpServletResponse);
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
    protected RestRequestDto prepareRequest(final String projectId, final String applicationId, final HttpMethod httpMethod, final HttpServletRequest httpServletRequest) {
        final RestRequestDto request = new RestRequestDto();
        final String body = RestMessageSupport.getBody(httpServletRequest);
        final String incomingRequestUri = httpServletRequest.getRequestURI().toLowerCase();
        final String restResourceUri = incomingRequestUri.replace(getContext() + SLASH + MOCK + SLASH + REST + SLASH + PROJECT + SLASH + projectId.toLowerCase() + SLASH + APPLICATION + SLASH + applicationId.toLowerCase(), EMPTY);
        final List<HttpParameterDto> httpParameters = HttpMessageSupport.extractParameters(httpServletRequest);
        final List<HttpHeaderDto> httpHeaders = HttpMessageSupport.extractHttpHeaders(httpServletRequest);

        request.setHttpMethod(httpMethod);
        request.setBody(body);
        request.setUri(restResourceUri);
        request.setHttpParameters(httpParameters);
        request.setHttpHeaders(httpHeaders);
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
    protected ResponseEntity<String> process(final RestRequestDto restRequest, final String projectId, final String applicationId, final String resourceId, final RestMethodDto restMethod, final HttpServletResponse httpServletResponse){
        Preconditions.checkNotNull(restRequest, "Rest request cannot be null");
        RestEventDto event = null;
        RestResponseDto response = null;
        try {
            event = new RestEventDto(restMethod.getName(), restRequest, projectId, applicationId, resourceId, restMethod.getId());
            if (RestMethodStatus.DISABLED.equals(restMethod.getStatus())) {
                throw new RestException("The requested REST method, " + restMethod.getName() + ", is disabled");
            } else if (RestMethodStatus.FORWARDED.equals(restMethod.getStatus())) {
                response = forwardRequest(restRequest, projectId, applicationId, resourceId, restMethod);
            } else if (RestMethodStatus.RECORDING.equals(restMethod.getStatus())) {
                response = forwardRequestAndRecordResponse(restRequest, projectId, applicationId, resourceId, restMethod);
            } else if (RestMethodStatus.RECORD_ONCE.equals(restMethod.getStatus())) {
                response = forwardRequestAndRecordResponseOnce(restRequest, projectId, applicationId, resourceId, restMethod);
            } else { // Status.MOCKED
                response = mockResponse(projectId, applicationId, resourceId, restMethod);
            }

            HttpHeaders responseHeaders = new HttpHeaders();
            for(HttpHeaderDto httpHeader : response.getHttpHeaders()){
                List<String> headerValues = new LinkedList<String>();
                headerValues.add(httpHeader.getValue());
                responseHeaders.put(httpHeader.getName(), headerValues);
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
     * @param restRequest The incoming request
     * @param restMethod The REST method which the incoming request belongs to
     * @return The response received from the external endpoint
     */
    protected RestResponseDto forwardRequest(final RestRequestDto restRequest, final String projectId, final String applicationId, final String resourceId, final RestMethodDto restMethod){
        if(demoMode){
            // If the application is configured to run in demo mode, then use mocked response instead
            return mockResponse(projectId, applicationId, resourceId, restMethod);
        }


        final RestResponseDto response = new RestResponseDto();
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        try {
            final String parameterUri = HttpMessageSupport.buildParameterUri(restRequest.getHttpParameters());
            final URL url = new URL(restMethod.getForwardedEndpoint() + restRequest.getUri() + parameterUri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(restRequest.getHttpMethod().name());
            for(HttpHeaderDto httpHeader : restRequest.getHttpHeaders()){
                connection.addRequestProperty(httpHeader.getName(), httpHeader.getValue());
            }

            if(HttpMethod.POST.equals(restRequest.getHttpMethod()) || HttpMethod.PUT.equals(restRequest.getHttpMethod()) || HttpMethod.DELETE.equals(restRequest.getHttpMethod())){
                outputStream = connection.getOutputStream();
                outputStream.write(restRequest.getBody().getBytes());
                outputStream.flush();
            }

            InputStreamReader inputStreamReader = null;
            if(connection.getResponseCode() == OK_RESPONSE){
                inputStreamReader = new InputStreamReader(connection.getInputStream());
            } else {
                inputStreamReader = new InputStreamReader(connection.getErrorStream());
            }

            bufferedReader = new BufferedReader(inputStreamReader);

            final StringBuilder stringBuilder = new StringBuilder();
            String buffer;
            while ((buffer = bufferedReader.readLine()) != null) {
                stringBuilder.append(buffer);
                stringBuilder.append(NEW_LINE);
            }

            final List<HttpHeaderDto> responseHttpHeaders = HttpMessageSupport.extractHttpHeaders(connection);
            response.setBody(stringBuilder.toString());
            response.setMockResponseName(FORWARDED_RESPONSE_NAME);
            response.setHttpHeaders(responseHttpHeaders);
            response.setHttpStatusCode(connection.getResponseCode());
            return response;
        } catch (IOException exception) {
            LOGGER.error("Unable to forward request", exception);
            throw new RestException("Unable to forward request to configured endpoint");
        } finally {
            if(connection != null){
                connection.disconnect();
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException exception) {
                    LOGGER.error("Unable to close output stream", exception);
                }
            }
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException exception) {
                    LOGGER.error("Unable to close buffered reader", exception);
                }
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
    protected RestResponseDto forwardRequestAndRecordResponse(final RestRequestDto restRequest, final String projectId, final String applicationId, final String resourceId, final RestMethodDto restMethod){
        final RestResponseDto response = forwardRequest(restRequest, projectId, applicationId, resourceId, restMethod);
        final RestMockResponseDto mockResponse = new RestMockResponseDto();
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
    protected RestResponseDto forwardRequestAndRecordResponseOnce(final RestRequestDto restRequest, final String projectId, final String applicationId, final String resourceId, final RestMethodDto restMethod){
        final RestResponseDto response = forwardRequestAndRecordResponse(restRequest, projectId, applicationId, resourceId, restMethod);
        restMethod.setStatus(RestMethodStatus.MOCKED);
        serviceProcessor.process(new UpdateRestMethodInput(projectId, applicationId, resourceId, restMethod.getId(), restMethod));
        return response;
    }

    /**
     * The method is responsible for mocking a REST service. The method will identify which mocked response
     * will be returned.
     * @param restMethod The REST method which the incoming request belongs to
     * @return Returns a selected mocked response which will be returned to the service consumer
     */
    protected RestResponseDto mockResponse(final String projectId, final String applicationId, final String resourceId, final RestMethodDto restMethod){
        final List<RestMockResponseDto> mockResponses = new ArrayList<RestMockResponseDto>();
        for(RestMockResponseDto mockResponse : restMethod.getMockResponses()){
            if(mockResponse.getStatus().equals(RestMockResponseStatus.ENABLED)){
                mockResponses.add(mockResponse);
            }
        }

        RestMockResponseDto mockResponse = null;
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
            serviceProcessor.process(new UpdateCurrentRestMockResponseSequenceIndexInput(projectId, applicationId, resourceId, restMethod.getId(), currentSequenceNumber + 1));
        }

        if(mockResponse == null){
            throw new RestException("No mocked response created for operation " + restMethod.getName());
        }

        String body = mockResponse.getBody();
        if(mockResponse.isUsingExpressions()){
            // Parse the text and apply expression functionality if
            // the mock response is configured to use expressions
            body = TextParser.parse(body);
        }
        final RestResponseDto response = new RestResponseDto();
        response.setBody(body);
        response.setMockResponseName(mockResponse.getName());
        response.setHttpStatusCode(mockResponse.getHttpStatusCode());
        response.setHttpHeaders(mockResponse.getHttpHeaders());
        return response;
    }
}
