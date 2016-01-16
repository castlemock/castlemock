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

import com.fortmocks.core.basis.model.http.domain.HttpMethod;
import com.fortmocks.core.basis.model.http.dto.HttpHeaderDto;
import com.fortmocks.core.basis.model.http.dto.HttpParameterDto;
import com.fortmocks.core.mock.rest.model.event.dto.RestEventDto;
import com.fortmocks.core.mock.rest.model.event.dto.RestRequestDto;
import com.fortmocks.core.mock.rest.model.event.dto.RestResponseDto;
import com.fortmocks.core.mock.rest.model.event.service.message.input.CreateRestEventInput;
import com.fortmocks.core.mock.rest.model.project.domain.RestMethodStatus;
import com.fortmocks.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.fortmocks.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.fortmocks.core.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestMockResponseDto;
import com.fortmocks.core.mock.rest.model.project.service.message.input.CreateRecordedRestMockResponseInput;
import com.fortmocks.core.mock.rest.model.project.service.message.input.IdentifyRestMethodInput;
import com.fortmocks.core.mock.rest.model.project.service.message.input.UpdateCurrentRestMockResponseSequenceIndexInput;
import com.fortmocks.core.mock.rest.model.project.service.message.input.UpdateRestMethodInput;
import com.fortmocks.core.mock.rest.model.project.service.message.output.IdentifyRestMethodOutput;
import com.fortmocks.web.basis.web.mvc.controller.AbstractController;
import com.fortmocks.web.mock.rest.model.RestException;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;

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
     * @param httpMethod The request method
     * @param httpServletRequest The incoming request
     * @param httpServletResponse The outgoing response
     * @return Returns the response as an String
     */
    protected String process(final String projectId, final String applicationId, final HttpMethod httpMethod, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){
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
     * @param httpServletRequest The incoming request
     * @return A new created project
     */
    protected RestRequestDto prepareRequest(final String projectId, final String applicationId, final HttpMethod httpMethod, final HttpServletRequest httpServletRequest) {
        final RestRequestDto request = new RestRequestDto();
        final String body = RestMessageSupport.getBody(httpServletRequest);
        final String incomingRequestUri = httpServletRequest.getRequestURI().toLowerCase();
        final String restResourceUri = incomingRequestUri.replace(getContext() + SLASH + MOCK + SLASH + REST + SLASH + PROJECT + SLASH + projectId.toLowerCase() + SLASH + APPLICATION + SLASH + applicationId.toLowerCase(), EMPTY);
        final List<HttpParameterDto> httpParameters = extractParameters(httpServletRequest);
        final List<HttpHeaderDto> httpHeaders = extractHttpHeaders(httpServletRequest);

        request.setHttpMethod(httpMethod);
        request.setBody(body);
        request.setUri(restResourceUri);
        request.setHttpParameters(httpParameters);
        request.setHttpHeaders(httpHeaders);
        return request;
    }

    /**
     * Extract all the incoming parameters and stores them in a Map. The parameter name will
     * act as the key and the parameter value will be the Map value
     * @param httpServletRequest The incoming request which contains all the parameters
     * @return A map with the extracted parameters
     */
    protected List<HttpParameterDto> extractParameters(final HttpServletRequest httpServletRequest){
        final List<HttpParameterDto> httpParameters = new ArrayList<HttpParameterDto>();

        final Enumeration<String> enumeration = httpServletRequest.getParameterNames();
        while(enumeration.hasMoreElements()){
            final HttpParameterDto httpParameter = new HttpParameterDto();
            final String parameterName = enumeration.nextElement();
            final String parameterValue = httpServletRequest.getParameter(parameterName);
            httpParameter.setName(parameterName);
            httpParameter.setValue(parameterValue);
        }
        return httpParameters;
    }

    /**
     * Extract HTTP headers from provided Http Servlet Request
     * @param httpServletRequest Incoming Http Servlet Request that contains the headers which will be extracted
     * @return A list of HTTP headers extracted from the provided httpServletRequest
     */
    public static List<HttpHeaderDto> extractHttpHeaders(final HttpServletRequest httpServletRequest){
        final List<HttpHeaderDto> httpHeaders = new ArrayList<HttpHeaderDto>();
        final Enumeration<String> headers = httpServletRequest.getHeaderNames();
        while(headers.hasMoreElements()){
            final String headerName = headers.nextElement();
            final String headerValue = httpServletRequest.getHeader(headerName);
            final HttpHeaderDto httpHeader = new HttpHeaderDto();
            httpHeader.setName(headerName);
            httpHeader.setValue(headerValue);
            httpHeaders.add(httpHeader);
        }
        return httpHeaders;
    }


    /**
     * Builds a parameter URL string passed on the provided parameter map.
     * Example on the output: ?name1=value1&name2=value2
     * @param httpParameters The Map of parameters that will be used to build the parameter URI
     * @return A URI that contains the parameters from the provided Map
     */
    protected String buildParameterUri(List<HttpParameterDto> httpParameters){
        if(httpParameters.isEmpty()){
            return EMPTY;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?");
        for(int index = 0; index < httpParameters.size(); index++){
            HttpParameterDto httpParameter = httpParameters.get(index);
            String parameterName = httpParameter.getName();
            String parameterValue = httpParameter.getValue();
            stringBuilder.append(parameterName + "=" + parameterValue);

            // Add a & (and) character if the Http parameter is not the last one
            if(index < httpParameters.size() - 1){
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
            if (RestMethodStatus.DISABLED.equals(restMethod.getStatus())) {
                throw new RestException("The requested REST method, " + restMethod.getName() + ", is disabled");
            } else if (RestMethodStatus.FORWARDED.equals(restMethod.getStatus())) {
                response = forwardRequest(restRequest, restMethod);
            } else if (RestMethodStatus.RECORDING.equals(restMethod.getStatus())) {
                response = forwardRequestAndRecordResponse(restRequest, restMethod);
            } else if (RestMethodStatus.RECORD_ONCE.equals(restMethod.getStatus())) {
                response = forwardRequestAndRecordResponseOnce(restRequest, projectId, applicationId, resourceId, restMethod);
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
            final String parameterUri = buildParameterUri(restRequest.getHttpParameters());
            final URL url = new URL(restMethod.getForwardedEndpoint() + restRequest.getUri() + parameterUri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(restRequest.getHttpMethod().name());
            for(HttpHeaderDto httpHeader : restRequest.getHttpHeaders()){
                connection.addRequestProperty(httpHeader.getName(), httpHeader.getValue());
            }

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

            final List<HttpHeaderDto> responseHttpHeaders = new ArrayList<HttpHeaderDto>();
            for(String headerName : connection.getHeaderFields().keySet()){
                final String headerValue = connection.getHeaderField(headerName);
                final HttpHeaderDto responseHttpHeader = new HttpHeaderDto();
                responseHttpHeader.setName(headerName);
                responseHttpHeader.setValue(headerValue);
                responseHttpHeaders.add(responseHttpHeader);
            }

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
    protected RestResponseDto forwardRequestAndRecordResponse(final RestRequestDto restRequest, final RestMethodDto restMethod){
        final RestResponseDto response = forwardRequest(restRequest, restMethod);
        final RestMockResponseDto mockResponse = new RestMockResponseDto();
        final Date date = new Date();
        mockResponse.setBody(response.getBody());
        mockResponse.setStatus(RestMockResponseStatus.ENABLED);
        mockResponse.setHttpHeaders(response.getHttpHeaders());
        mockResponse.setName(RECORDED_RESPONSE_NAME + SPACE + DATE_FORMAT.format(date));
        mockResponse.setHttpStatusCode(response.getHttpStatusCode());
        serviceProcessor.process(new CreateRecordedRestMockResponseInput(restMethod.getId(), mockResponse));
        return response;
    }

    /**
     * The method provides the functionality to forward a request to another endpoint. The response
     * will be recorded and can later be used as a mocked response. The REST method status will be updated
     * to mocked.
     * @param restRequest The incoming request
     * @param restMethod The REST method which the incoming request belongs to
     * @return The response received from the external endpoint
     */
    protected RestResponseDto forwardRequestAndRecordResponseOnce(final RestRequestDto restRequest, final String projectId, final String applicationId, final String resourceId, final RestMethodDto restMethod){
        final RestResponseDto response = forwardRequestAndRecordResponse(restRequest, restMethod);
        restMethod.setStatus(RestMethodStatus.MOCKED);
        serviceProcessor.process(new UpdateRestMethodInput(projectId, applicationId, resourceId, restMethod.getId(), restMethod));
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
            serviceProcessor.process(new UpdateCurrentRestMockResponseSequenceIndexInput(restMethod.getId(), currentSequenceNumber + 1));
        }

        if(mockResponse == null){
            throw new RestException("No mocked response created for operation " + restMethod.getName());
        }

        final RestResponseDto response = new RestResponseDto();
        response.setBody(mockResponse.getBody());
        response.setMockResponseName(mockResponse.getName());
        response.setHttpStatusCode(mockResponse.getHttpStatusCode());
        response.setHttpHeaders(mockResponse.getHttpHeaders());
        httpServletResponse.setStatus(mockResponse.getHttpStatusCode());
        for(HttpHeaderDto httpHeader : mockResponse.getHttpHeaders()){
            httpServletResponse.addHeader(httpHeader.getName(), httpHeader.getValue());
        }

        return response;
    }
}
