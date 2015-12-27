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

package com.fortmocks.web.mock.rest.web.rest.controller;

import com.fortmocks.core.mock.rest.model.event.dto.RestEventDto;
import com.fortmocks.core.mock.rest.model.event.dto.RestRequestDto;
import com.fortmocks.core.mock.rest.model.event.dto.RestResponseDto;
import com.fortmocks.core.mock.rest.model.event.service.message.input.CreateRestEventInput;
import com.fortmocks.core.mock.rest.model.project.domain.*;
import com.fortmocks.core.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestMockResponseDto;
import com.fortmocks.core.mock.rest.model.project.service.message.input.CreateRecordedRestMockResponseInput;
import com.fortmocks.core.mock.rest.model.project.service.message.input.IdentifyRestMethodInput;
import com.fortmocks.core.mock.rest.model.project.service.message.input.UpdateCurrentRestMockResponseSequenceIndexInput;
import com.fortmocks.core.mock.rest.model.project.service.message.output.IdentifyRestMethodOutput;
import com.fortmocks.web.basis.web.mvc.controller.AbstractController;
import com.fortmocks.web.mock.rest.model.RestException;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.iterators.IteratorEnumeration;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
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
     * @param restMethodType The request method
     * @param httpServletRequest The incoming request
     * @param httpServletResponse The outgoing response
     * @return Returns the response as an String
     */
    protected String process(final String projectId, final String applicationId, final RestMethodType restMethodType, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){
        try{
            Preconditions.checkNotNull(projectId, "The project id cannot be null");
            Preconditions.checkNotNull(applicationId, "The application id cannot be null");
            Preconditions.checkNotNull(restMethodType, "The REST method cannot be null");
            Preconditions.checkNotNull(httpServletRequest, "The HTTP Servlet Request cannot be null");
            Preconditions.checkNotNull(httpServletResponse, "The HTTP Servlet Response cannot be null");

            final RestRequestDto restRequest = prepareRequest(projectId, applicationId, restMethodType, httpServletRequest);
            final IdentifyRestMethodOutput output = serviceProcessor.process(new IdentifyRestMethodInput(projectId, applicationId, restRequest.getUri(), restMethodType));
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
     * @param httpServletRequest The incoming request
     * @return A new created project
     */
    protected RestRequestDto prepareRequest(final String projectId, final String applicationId, final RestMethodType restMethodType, final HttpServletRequest httpServletRequest) {
        final RestRequestDto request = new RestRequestDto();
        final String body = RestMessageSupport.getBody(httpServletRequest);
        final String incomingRequestUri = httpServletRequest.getRequestURI().toLowerCase();
        final String restResourceUri = incomingRequestUri.replace(getContext() + SLASH + MOCK + SLASH + REST + SLASH + PROJECT + SLASH + projectId.toLowerCase() + SLASH + APPLICATION + SLASH + applicationId.toLowerCase(), EMPTY);
        final Map<String, String> parameters = extractParameters(httpServletRequest);

        request.setContentType(httpServletRequest.getContentType());
        request.setRestMethodType(restMethodType);
        request.setBody(body);
        request.setUri(restResourceUri);
        request.setParameters(parameters);
        return request;
    }

    /**
     * Extract all the incoming parameters and stores them in a Map. The parameter name will
     * act as the key and the parameter value will be the Map value
     * @param httpServletRequest The incoming request which contains all the parameters
     * @return A map with the extracted parameters
     */
    protected Map<String, String> extractParameters(final HttpServletRequest httpServletRequest){
        final Map<String, String> parameters = new HashMap<>();

        final Enumeration<String> enumeration = httpServletRequest.getParameterNames();
        while(enumeration.hasMoreElements()){
            String parameterName = enumeration.nextElement();
            String parameterValue = httpServletRequest.getParameter(parameterName);
            parameters.put(parameterName, parameterValue);
        }
        return parameters;
    }

    /**
     * Builds a parameter URL string passed on the provided parameter map.
     * Example on the output: ?name1=value1&name2=value2
     * @param parameters The Map of parameters that will be used to build the parameter URI
     * @return A URI that contains the parameters from the provided Map
     */
    protected String buildParameterUri(Map<String, String> parameters){
        if(parameters.isEmpty()){
            return EMPTY;
        }
        final Enumeration<String> enumeration = new IteratorEnumeration(parameters.keySet().iterator());
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?");
        while(enumeration.hasMoreElements()){
            String parameterName = enumeration.nextElement();
            String parameterValue = parameters.get(parameterName);
            stringBuilder.append(parameterName + "=" + parameterValue);

            if(enumeration.hasMoreElements()){
                stringBuilder.append("&");
            }
        }
        return stringBuilder.toString();
    }


    /**
     * The process method provides the functionality to process an incoming request. The request will be identified
     * and a corresponding action will be applied for the request. The following actions are support:
     * Forward, record, mock or disable.
     * @param restRequest The incoming request
     * @param restMethod The REST method which the incoming request belongs to
     * @param httpServletResponse The HTTP servlet response
     * @return A response in String format
     */
    protected String process(final RestRequestDto restRequest, final String projectId, final String applicationId, final String resourceId, final RestMethodDto restMethod, final HttpServletResponse httpServletResponse){
        Preconditions.checkNotNull(restRequest, "Rest request cannot be null");
        RestEventDto event = null;
        RestResponseDto response = null;
        try {
            event = new RestEventDto(restMethod.getName(), restRequest, projectId, applicationId, resourceId, restMethod.getId());
            if (RestMethodStatus.DISABLED.equals(restMethod.getRestMethodStatus())) {
                throw new RestException("The requested REST method, " + restMethod.getName() + ", is disabled");
            } else if (RestMethodStatus.FORWARDED.equals(restMethod.getRestMethodStatus())) {
                response = forwardRequest(restRequest, restMethod);
            } else if (RestMethodStatus.RECORDING.equals(restMethod.getRestMethodStatus())) {
                response = forwardRequestAndRecordResponse(restRequest, restMethod);
            } else { // Status.MOCKED
                response = mockResponse(restMethod, httpServletResponse);
            }
            return response.getBody();
        } finally{
            if(event != null){
                event.finish(response);
                serviceProcessor.process(new CreateRestEventInput(event));
            }
        }
    }

    /**
     * The method provides the functionality to forward a request to another endpoint
     * @param restRequest The incoming request
     * @param restMethod The REST method which the incoming request belongs to
     * @return The response received from the external endpoint
     */
    protected RestResponseDto forwardRequest(final RestRequestDto restRequest, final RestMethodDto restMethod){
        final RestResponseDto response = new RestResponseDto();
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        try {
            final String parameterUri = buildParameterUri(restRequest.getParameters());
            final URL url = new URL(restMethod.getForwardedEndpoint() + restRequest.getUri() + parameterUri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(restRequest.getRestMethodType().name());
            connection.setRequestProperty(CONTENT_TYPE, restRequest.getContentType());
            outputStream = connection.getOutputStream();
            outputStream.write(restRequest.getBody().getBytes());
            outputStream.flush();
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
            response.setBody(stringBuilder.toString());
            response.setMockResponseName(FORWARDED_RESPONSE_NAME);
            response.setHttpStatusCode(connection.getResponseCode());
            String contentType = connection.getHeaderField(CONTENT_TYPE);
            if(contentType != null){
                RestContentType restContentType = parseContentType(contentType);
                response.setRestContentType(restContentType);
            }

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
     * Parse a raw content type and match it with a REST content type.
     * @param rawContentType The raw content type
     * @return The matched REST content type. Returns null if no REST content type is matched
     */
    protected RestContentType parseContentType(String rawContentType){
        for(RestContentType restContentType : RestContentType.values()){
            if(rawContentType.contains(restContentType.getContentType())){
                return restContentType;
            }
        }
        return null;
    }

    /**
     * The method provides the functionality to forward a request to another endpoint. The response
     * will be recorded and can later be used as a mocked response
     * @param restRequest The incoming request
     * @param restMethod The REST method which the incoming request belongs to
     * @return The response received from the external endpoint
     */
    protected RestResponseDto forwardRequestAndRecordResponse(final RestRequestDto restRequest, final RestMethodDto restMethod){
        final RestResponseDto response = forwardRequest(restRequest, restMethod);
        final RestMockResponseDto mockResponse = new RestMockResponseDto();
        final Date date = new Date();
        mockResponse.setBody(response.getBody());
        mockResponse.setRestMockResponseStatus(RestMockResponseStatus.ENABLED);
        mockResponse.setName(RECORDED_RESPONSE_NAME + SPACE + DATE_FORMAT.format(date));
        mockResponse.setHttpStatusCode(response.getHttpStatusCode());
        mockResponse.setRestContentType(response.getRestContentType());
        serviceProcessor.process(new CreateRecordedRestMockResponseInput(restMethod.getId(), mockResponse));
        return response;
    }

    /**
     * The method is responsible for mocking a REST service. The method will identify which mocked response
     * will be returned.
     * @param restMethod The REST method which the incoming request belongs to
     * @param httpServletResponse The HTTP servlet response
     * @return Returns a selected mocked response which will be returned to the service consumer
     */
    protected RestResponseDto mockResponse(final RestMethodDto restMethod, final HttpServletResponse httpServletResponse){
        final List<RestMockResponseDto> mockResponses = new ArrayList<RestMockResponseDto>();
        for(RestMockResponseDto mockResponse : restMethod.getRestMockResponses()){
            if(mockResponse.getRestMockResponseStatus().equals(RestMockResponseStatus.ENABLED)){
                mockResponses.add(mockResponse);
            }
        }

        RestMockResponseDto mockResponse = null;
        if(mockResponses.isEmpty()){
            throw new RestException("No mocked response created for operation " + restMethod.getName());
        } else if(restMethod.getRestResponseStrategy().equals(RestResponseStrategy.RANDOM)){
            final Integer responseIndex = RANDOM.nextInt(mockResponses.size());
            mockResponse = mockResponses.get(responseIndex);
        } else if(restMethod.getRestResponseStrategy().equals(RestResponseStrategy.SEQUENCE)){
            Integer currentSequenceNumber = restMethod.getCurrentResponseSequenceIndex();
            if(currentSequenceNumber >= mockResponses.size()){
                currentSequenceNumber = 0;
            }
            mockResponse = mockResponses.get(currentSequenceNumber);
            serviceProcessor.process(new UpdateCurrentRestMockResponseSequenceIndexInput(restMethod.getId(), currentSequenceNumber + 1));
        }

        if(mockResponse == null){
            throw new RestException("No mocked response created for operation " + restMethod.getName());
        }

        final RestResponseDto response = new RestResponseDto();
        response.setBody(mockResponse.getBody());
        response.setMockResponseName(mockResponse.getName());
        response.setHttpStatusCode(mockResponse.getHttpStatusCode());
        response.setRestContentType(mockResponse.getRestContentType());
        httpServletResponse.setStatus(mockResponse.getHttpStatusCode());
        httpServletResponse.setContentType(mockResponse.getRestContentType().getContentType());
        httpServletResponse.setHeader(CONTENT_TYPE, mockResponse.getRestContentType().getContentType());
        return response;
    }
}
