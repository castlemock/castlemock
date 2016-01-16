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

package com.fortmocks.web.mock.soap.web.soap.controller;

import com.fortmocks.core.basis.model.HttpMethod;
import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.event.dto.SoapRequestDto;
import com.fortmocks.core.mock.soap.model.event.dto.SoapResponseDto;
import com.fortmocks.core.mock.soap.model.event.service.message.input.CreateSoapEventInput;
import com.fortmocks.core.mock.soap.model.project.domain.*;
import com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.core.mock.soap.model.project.service.message.input.CreateRecordedSoapMockResponseInput;
import com.fortmocks.core.mock.soap.model.project.service.message.input.IdentifySoapOperationInput;
import com.fortmocks.core.mock.soap.model.project.service.message.input.UpdateCurrentMockResponseSequenceIndexInput;
import com.fortmocks.core.mock.soap.model.project.service.message.input.UpdateSoapOperationInput;
import com.fortmocks.core.mock.soap.model.project.service.message.output.IdentifySoapOperationOutput;
import com.fortmocks.web.basis.web.mvc.controller.AbstractController;
import com.fortmocks.web.mock.soap.model.SoapException;
import com.fortmocks.web.mock.soap.support.SoapMessageSupport;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * The AbstractSoapServiceController provides functionality that are shared for all the SOAP controllers
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
public abstract class AbstractSoapServiceController extends AbstractController{

    private static final String RECORDED_RESPONSE_NAME = "Recorded response";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final String FORWARDED_RESPONSE_NAME = "Forwarded response";
    private static final Random RANDOM = new Random();
    private static final String SOAP = "soap";
    private static final Logger LOGGER = Logger.getLogger(AbstractSoapServiceController.class);


    /**
     * Process the incoming message by forwarding it to the main process method in
     * the AbstractServiceController class. However, the Protocol value SOAP is being
     * sent to the AbstractServiceController. This is used by AbstractServiceController
     * to indicate the type of the incoming request
     * @param projectId The id of the project which the incoming request and mocked response belongs to
     * @param httpServletRequest The incoming request
     * @return Returns the response as an String
     */
    protected String process(final String projectId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){
        try{
            Preconditions.checkNotNull(projectId, "THe project id cannot be null");
            Preconditions.checkNotNull(httpServletRequest, "The HTTP Servlet Request cannot be null");
            final SoapRequestDto request = prepareRequest(projectId, httpServletRequest);
            final IdentifySoapOperationOutput output = serviceProcessor.process(new IdentifySoapOperationInput(projectId, request.getOperationIdentifier(), request.getUri(), request.getHttpMethod(), request.getSoapVersion()));
            final SoapOperationDto operation = output.getSoapOperation();
            request.setOperationName(operation.getName());
            return process(projectId, output.getSoapPortId(), operation, request, httpServletResponse);
        }catch(Exception exception){
            LOGGER.debug("SOAP service exception: " + exception.getMessage(), exception);
            throw new SoapException(exception.getMessage());
        }
    }

    /**
     * The method prepares an request
     * @param projectId The id of the project that the incoming request belongs to
     * @param httpServletRequest The incoming request
     * @return A new created project
     */
    protected SoapRequestDto prepareRequest(final String projectId, final HttpServletRequest httpServletRequest) {
        SoapRequestDto request = new SoapRequestDto();
        final String body = SoapMessageSupport.getBody(httpServletRequest);
        final String identifier = SoapMessageSupport.extractSoapRequestName(body);
        final String serviceUri = httpServletRequest.getRequestURI().replace(getContext() + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT + SLASH + projectId + SLASH, EMPTY);

        SoapVersion type = SoapVersion.SOAP11;
        if(SoapVersion.SOAP12.getContextPath().equalsIgnoreCase(httpServletRequest.getContextPath())){
            type = SoapVersion.SOAP12;
        }

        request.setSoapVersion(type);
        request.setContentType(httpServletRequest.getContentType());
        request.setUri(serviceUri);
        request.setHttpMethod(HttpMethod.valueOf(httpServletRequest.getMethod()));
        request.setBody(body);
        request.setOperationIdentifier(identifier);
        return request;
    }


    /**
     * The process method is responsible for processing the incoming request and
     * finding the appropriate response. The method is also responsible for creating
     * events and storing them.
     * @param soapProjectId The id of the project that the incoming request belong to
     * @param soapPortId The id of the port that the incoming request belong to
     * @param soapOperationDto The operation that contain the appropriate mocked response
     * @param request The incoming request
     * @return Returns the response as an String
     */
    protected String process(final String soapProjectId, final String soapPortId, final SoapOperationDto soapOperationDto, final SoapRequestDto request, final HttpServletResponse httpServletResponse){
        Preconditions.checkNotNull(request, "Request cannot be null");
        if(soapOperationDto == null){
            throw new SoapException("Soap operation could not be found");
        }
        SoapEventDto event = null;
        SoapResponseDto response = null;
        try {
            event = new SoapEventDto(soapOperationDto.getName(), request, soapProjectId, soapPortId, soapOperationDto.getId());
            if (SoapOperationStatus.DISABLED.equals(soapOperationDto.getStatus())) {
                throw new SoapException("The requested soap operation, " + soapOperationDto.getName() + ", is disabled");
            } else if (SoapOperationStatus.FORWARDED.equals(soapOperationDto.getStatus())) {
                response = forwardRequest(request, soapOperationDto);
            } else if (SoapOperationStatus.RECORDING.equals(soapOperationDto.getStatus())) {
                response = forwardRequestAndRecordResponse(request, soapOperationDto);
            } else if (SoapOperationStatus.RECORD_ONCE.equals(soapOperationDto.getStatus())) {
                response = forwardRequestAndRecordResponseOnce(request, soapProjectId, soapPortId, soapOperationDto);
            } else { // Status.MOCKED
                response = mockResponse(soapOperationDto, httpServletResponse);
            }
            return response.getBody();
        } finally{
            if(event != null){
                event.finish(response);
                serviceProcessor.process(new CreateSoapEventInput(event));
            }
        }
    }

    /**
     * The method is responsible for retrieving and returning a mocked response for the provided operation
     * @param soapOperationDto The SOAP operation that is being executed. The response is based on
     *                         the provided SOAP operation.
     * @return A mocked response based on the provided SOAP operation
     */
    private SoapResponseDto mockResponse(final SoapOperationDto soapOperationDto, final HttpServletResponse httpServletResponse){
        final List<SoapMockResponseDto> mockResponses = new ArrayList<SoapMockResponseDto>();
        for(SoapMockResponseDto mockResponse : soapOperationDto.getMockResponses()){
            if(mockResponse.getStatus().equals(SoapMockResponseStatus.ENABLED)){
                mockResponses.add(mockResponse);
            }
        }

        SoapMockResponseDto mockResponse = null;
        if(mockResponses.isEmpty()){
            throw new SoapException("No mocked response created for operation " + soapOperationDto.getName());
        } else if(soapOperationDto.getResponseStrategy().equals(SoapResponseStrategy.RANDOM)){
            final Integer responseIndex = RANDOM.nextInt(mockResponses.size());
            mockResponse = mockResponses.get(responseIndex);
        } else if(soapOperationDto.getResponseStrategy().equals(SoapResponseStrategy.SEQUENCE)){
            Integer currentSequenceNumber = soapOperationDto.getCurrentResponseSequenceIndex();
            if(currentSequenceNumber >= mockResponses.size()){
                currentSequenceNumber = 0;
            }
            mockResponse = mockResponses.get(currentSequenceNumber);
            serviceProcessor.process(new UpdateCurrentMockResponseSequenceIndexInput(soapOperationDto.getId(), currentSequenceNumber + 1));
        }

        if(mockResponse == null){
            throw new SoapException("No mocked response created for operation " + soapOperationDto.getName());
        }

        final SoapResponseDto response = new SoapResponseDto();
        response.setBody(mockResponse.getBody());
        response.setMockResponseName(mockResponse.getName());
        response.setHttpStatusCode(mockResponse.getHttpStatusCode());
        httpServletResponse.setStatus(mockResponse.getHttpStatusCode());
        httpServletResponse.setContentType(mockResponse.getContentType());
        return response;

    }

    /**
     * The method provides the functionality to forward the incoming request, store the returned the response and
     * return the response to the service consumer.
     * @param request The incoming request that will be forwarded
     * @param soapOperationDto The SOAP operation that is being executed
     * @return The response from the system that the request was forwarded to.
     */
    private SoapResponseDto forwardRequestAndRecordResponse(final SoapRequestDto request, final SoapOperationDto soapOperationDto){
        final SoapResponseDto response = forwardRequest(request, soapOperationDto);
        final SoapMockResponseDto mockResponse = new SoapMockResponseDto();
        final Date date = new Date();
        mockResponse.setBody(response.getBody());
        mockResponse.setStatus(SoapMockResponseStatus.ENABLED);
        mockResponse.setName(RECORDED_RESPONSE_NAME + SPACE + DATE_FORMAT.format(date));
        mockResponse.setHttpStatusCode(response.getHttpStatusCode());
        mockResponse.setContentType(response.getContentType());
        serviceProcessor.process(new CreateRecordedSoapMockResponseInput(soapOperationDto.getId(), mockResponse));
        return response;
    }

    /**
     * The method provides the functionality to forward the incoming request, store the returned the response and
     * return the response to the service consumer. The SOAP operation status will be updated
     * to mocked.
     * @param request The incoming request that will be forwarded
     * @param soapOperationDto The SOAP operation that is being executed
     * @return The response from the system that the request was forwarded to.
     */
    private SoapResponseDto forwardRequestAndRecordResponseOnce(final SoapRequestDto request, final String soapProjectId, final String soapPortId, final SoapOperationDto soapOperationDto){
        final SoapResponseDto response = forwardRequestAndRecordResponse(request, soapOperationDto);
        soapOperationDto.setStatus(SoapOperationStatus.MOCKED);
        serviceProcessor.process(new UpdateSoapOperationInput(soapProjectId, soapPortId, soapOperationDto.getId(), soapOperationDto));
        return response;
    }

    /**
     * The method provides the functionality to forward request to a forwarded endpoint.
     * @param request The request that will be forwarded
     * @param soapOperationDto The SOAP operation that is being executed
     * @return The response from the system that the request was forwarded to.
     */
    private SoapResponseDto forwardRequest(final SoapRequestDto request, final SoapOperationDto soapOperationDto){
        final SoapResponseDto response = new SoapResponseDto();
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        try {
            final URL url = new URL(soapOperationDto.getForwardedEndpoint());
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(request.getHttpMethod().name());
            connection.setRequestProperty(CONTENT_TYPE, request.getContentType());
            outputStream = connection.getOutputStream();
            outputStream.write(request.getBody().getBytes());
            outputStream.flush();

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            final StringBuilder stringBuilder = new StringBuilder();
            String buffer;
            while ((buffer = bufferedReader.readLine()) != null) {
                stringBuilder.append(buffer);
                stringBuilder.append(NEW_LINE);
            }
            response.setMockResponseName(FORWARDED_RESPONSE_NAME);
            response.setBody(stringBuilder.toString());
            response.setHttpStatusCode(connection.getResponseCode());
            response.setContentType(connection.getContentType());

            return response;
        } catch (IOException exception) {
            LOGGER.error("Unable to forward request", exception);
            throw new SoapException("Unable to forward request to configured endpoint");
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

}
