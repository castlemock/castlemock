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

package com.castlemock.web.mock.soap.web.soap.controller;

import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.model.http.dto.HttpHeaderDto;
import com.castlemock.core.basis.utility.parser.TextParser;
import com.castlemock.core.mock.soap.model.event.dto.SoapEventDto;
import com.castlemock.core.mock.soap.model.event.dto.SoapRequestDto;
import com.castlemock.core.mock.soap.model.event.dto.SoapResponseDto;
import com.castlemock.core.mock.soap.model.event.service.message.input.CreateSoapEventInput;
import com.castlemock.core.mock.soap.model.project.domain.*;
import com.castlemock.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapOperationDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapProjectDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapResourceDto;
import com.castlemock.core.mock.soap.model.project.service.message.input.*;
import com.castlemock.core.mock.soap.model.project.service.message.output.IdentifySoapOperationOutput;
import com.castlemock.core.mock.soap.model.project.service.message.output.LoadSoapResourceOutput;
import com.castlemock.core.mock.soap.model.project.service.message.output.ReadSoapProjectOutput;
import com.castlemock.web.basis.support.CharsetUtility;
import com.castlemock.web.basis.support.HttpMessageSupport;
import com.castlemock.web.basis.web.mvc.controller.AbstractController;
import com.castlemock.web.mock.soap.model.SoapException;
import com.castlemock.web.mock.soap.support.MtomUtility;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
     * @param httpServletResponse The outgoing response
     * @return Returns the response as an String
     */
    protected ResponseEntity process(final String projectId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){
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

    protected ResponseEntity processGet(final String projectId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){
        try{
            Preconditions.checkNotNull(projectId, "THe project id cannot be null");
            Preconditions.checkNotNull(httpServletRequest, "The HTTP Servlet Request cannot be null");
            Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
            while(parameterNames.hasMoreElements()){
                String parameterName = parameterNames.nextElement();
                if(parameterName.equalsIgnoreCase("wsdl")){
                    final String wsdl = getWsdl(projectId);
                    return new ResponseEntity<String>(wsdl, HttpStatus.OK);
                }
            }

            throw new IllegalArgumentException("Unable to parse incoming message");
        }catch(Exception exception){
            LOGGER.debug("SOAP service exception: " + exception.getMessage(), exception);
            throw new SoapException(exception.getMessage());
        }
    }

    private String getWsdl(final String projectId){
        final ReadSoapProjectOutput projectOutput = this.serviceProcessor.process(new ReadSoapProjectInput(projectId));
        final SoapProjectDto soapProject = projectOutput.getSoapProject();

        for(SoapResourceDto soapResource : soapProject.getResources()){
            if(SoapResourceType.WSDL.equals(soapResource.getType())){
                final LoadSoapResourceOutput loadOutput =
                        this.serviceProcessor.process(new LoadSoapResourceInput(projectId, soapResource.getId()));
                return loadOutput.getResource();
            }
        }
        throw new IllegalArgumentException("Unable to find a WSDL file for the following project: " + projectId);
    }

    /**
     * The method prepares an request
     * @param projectId The id of the project that the incoming request belongs to
     * @param httpServletRequest The incoming request
     * @return A new created project
     */
    protected SoapRequestDto prepareRequest(final String projectId, final HttpServletRequest httpServletRequest) {
        final SoapRequestDto request = new SoapRequestDto();
        final String body = HttpMessageSupport.getBody(httpServletRequest);

        final String identifier;
        if(httpServletRequest instanceof MultipartHttpServletRequest){
            // Check if the request is a Multipart request. If so, interpret  the incoming request
            // as a MTOM request and extract the main body (Exclude the attachment).
            // MTOM request mixes both the attachments and the body in the HTTP request body.
            String mainBody = MtomUtility.extractMtomBody(body, httpServletRequest.getContentType());

            // Use the main body to identify
            identifier = HttpMessageSupport.extractSoapRequestName(mainBody);
        } else {
            // The incoming request is a regular SOAP request. Parse the body as it is.
            identifier = HttpMessageSupport.extractSoapRequestName(body);
        }

        final String serviceUri = httpServletRequest.getRequestURI().replace(getContext() + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT + SLASH + projectId + SLASH, EMPTY);
        final List<HttpHeaderDto> httpHeaders = HttpMessageSupport.extractHttpHeaders(httpServletRequest);

        SoapVersion type = SoapVersion.convert(httpServletRequest.getContentType());
        request.setSoapVersion(type);
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
     * @param soapProjectId The id of the project that the incoming request belong to
     * @param soapPortId The id of the port that the incoming request belong to
     * @param soapOperationDto The operation that contain the appropriate mocked response
     * @param request The incoming request
     * @param httpServletResponse The outgoing HTTP servlet response
     * @return Returns the response as an String
     */
    protected ResponseEntity process(final String soapProjectId, final String soapPortId, final SoapOperationDto soapOperationDto, final SoapRequestDto request, final HttpServletResponse httpServletResponse){
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
                response = forwardRequest(request, soapProjectId, soapPortId, soapOperationDto);
            } else if (SoapOperationStatus.RECORDING.equals(soapOperationDto.getStatus())) {
                response = forwardRequestAndRecordResponse(request, soapProjectId, soapPortId, soapOperationDto);
            } else if (SoapOperationStatus.RECORD_ONCE.equals(soapOperationDto.getStatus())) {
                response = forwardRequestAndRecordResponseOnce(request, soapProjectId, soapPortId, soapOperationDto);
            } else if (SoapOperationStatus.ECHO.equals(soapOperationDto.getStatus())) {
                response = echoResponse(request);
            } else { // Status.MOCKED
                response = mockResponse(request, soapProjectId, soapPortId, soapOperationDto);
            }

            HttpHeaders responseHeaders = new HttpHeaders();
            for(HttpHeaderDto httpHeader : response.getHttpHeaders()){
                List<String> headerValues = new LinkedList<String>();
                headerValues.add(httpHeader.getValue());
                responseHeaders.put(httpHeader.getName(), headerValues);
            }

            if(soapOperationDto.getSimulateNetworkDelay() &&
                    soapOperationDto.getNetworkDelay() >= 0){
                try {
                        Thread.sleep(soapOperationDto.getNetworkDelay());
                } catch (InterruptedException e) {
                    LOGGER.error("Unable to simulate network delay", e);
                }
            }

            return new ResponseEntity<String>(response.getBody(), responseHeaders, HttpStatus.valueOf(response.getHttpStatusCode()));
        } finally{
            if(event != null){
                event.finish(response);
                serviceProcessor.processAsync(new CreateSoapEventInput(event));
            }
        }
    }

    /**
     * The method will echo the incoming {@link SoapRequestDto} and create a {@link SoapResponseDto}
     * with the same body, content type and headers.
     *
     * @param request The incoming {@link SoapRequestDto} that will be echoed back to the
     *                service consumer.
     * @return A {@link SoapResponseDto} based on the provided {@link SoapRequestDto}.
     * @since 1.14
     */
    private SoapResponseDto echoResponse(final SoapRequestDto request) {
        final List<HttpHeaderDto> headers = new ArrayList<HttpHeaderDto>();
        final HttpHeaderDto contentTypeHeader = new HttpHeaderDto();
        contentTypeHeader.setName(CONTENT_TYPE);
        contentTypeHeader.setValue(request.getContentType());
        headers.add(contentTypeHeader);

        final SoapResponseDto response = new SoapResponseDto();
        response.setBody(request.getBody());
        response.setContentType(request.getContentType());
        response.setHttpHeaders(headers);
        response.setHttpStatusCode(DEFAULT_ECHO_RESPONSE_CODE);
        return response;
    }

    /**
     * The method is responsible for retrieving and returning a mocked response for the provided operation
     *
     * @param request
     * @param soapOperationDto The SOAP operation that is being executed. The response is based on
     *                         the provided SOAP operation.
     * @return A mocked response based on the provided SOAP operation
     */
    private SoapResponseDto mockResponse(SoapRequestDto request, final String soapProjectId, final String soapPortId, final SoapOperationDto soapOperationDto){
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
            serviceProcessor.process(new UpdateCurrentMockResponseSequenceIndexInput(soapProjectId, soapPortId, soapOperationDto.getId(), currentSequenceNumber + 1));
        } else if (soapOperationDto.getResponseStrategy().equals(SoapResponseStrategy.XPATH_INPUT)) {
            for (SoapMockResponseDto testedMockResponse : mockResponses) {
                if (HttpMessageSupport.isValidXPathExpr(request.getBody(), testedMockResponse.getXpathExpressionDto())) {
                    mockResponse = testedMockResponse;
                    break;
                }
            }

            if(mockResponse == null){
                LOGGER.info("Unable to match the input XPath to a response");
                final String defaultXPathResponseId = soapOperationDto.getDefaultXPathMockResponseId();

                if(defaultXPathResponseId != null && !defaultXPathResponseId.isEmpty()){
                    LOGGER.info("Use the default XPath response");
                    for (SoapMockResponseDto mockResponseDto : mockResponses) {
                        if(defaultXPathResponseId.equals(mockResponseDto.getId())){
                            mockResponse = mockResponseDto;
                            break;
                        }
                    }

                    if(mockResponse == null){
                        LOGGER.error("Unable to find the default XPath response");
                    }
                }
            }

        }

        if(mockResponse == null){
            throw new SoapException("No mocked response created for operation " + soapOperationDto.getName());
        }

        String body = mockResponse.getBody();
        if(mockResponse.isUsingExpressions()){
            // Parse the text and apply expression functionality if
            // the mock response is configured to use expressions
            body = TextParser.parse(body);
        }
        final SoapResponseDto response = new SoapResponseDto();
        response.setBody(body);
        response.setMockResponseName(mockResponse.getName());
        response.setHttpHeaders(mockResponse.getHttpHeaders());
        response.setHttpStatusCode(mockResponse.getHttpStatusCode());
        response.setContentEncodings(mockResponse.getContentEncodings());
        return response;

    }

    /**
     * The method provides the functionality to forward the incoming request, store the returned the response and
     * return the response to the service consumer.
     * @param request The incoming request that will be forwarded
     * @param soapOperationDto The SOAP operation that is being executed
     * @return The response from the system that the request was forwarded to.
     */
    private SoapResponseDto forwardRequestAndRecordResponse(final SoapRequestDto request, final String soapProjectId,
                                                            final String soapPortId, final SoapOperationDto soapOperationDto){
        final SoapResponseDto response = forwardRequest(request, soapProjectId, soapPortId, soapOperationDto);
        final SoapMockResponseDto mockResponse = new SoapMockResponseDto();
        final Date date = new Date();
        mockResponse.setBody(response.getBody());
        mockResponse.setStatus(SoapMockResponseStatus.ENABLED);
        mockResponse.setName(RECORDED_RESPONSE_NAME + SPACE + DATE_FORMAT.format(date));
        mockResponse.setHttpStatusCode(response.getHttpStatusCode());
        mockResponse.setHttpHeaders(response.getHttpHeaders());
        serviceProcessor.processAsync(new CreateSoapMockResponseInput(soapProjectId, soapPortId, soapOperationDto.getId(), mockResponse));
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
        final SoapResponseDto response = forwardRequestAndRecordResponse(request, soapProjectId, soapPortId, soapOperationDto);
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
    private SoapResponseDto forwardRequest(final SoapRequestDto request, final String soapProjectId, final String soapPortId, final SoapOperationDto soapOperationDto){
        if(demoMode){
            // If the application is configured to run in demo mode, then use mocked response instead
            return mockResponse(request, soapProjectId, soapPortId, soapOperationDto);
        }

        final SoapResponseDto response = new SoapResponseDto();
        HttpURLConnection connection = null;
        try {
            connection = HttpMessageSupport.establishConnection(
                    soapOperationDto.getForwardedEndpoint(),
                    request.getHttpMethod(),
                    request.getBody(),
                    request.getHttpHeaders());


            final List<ContentEncoding> encodings = HttpMessageSupport.extractContentEncoding(connection);
            final List<HttpHeaderDto> responseHttpHeaders = HttpMessageSupport.extractHttpHeaders(connection);
            final String characterEncoding = CharsetUtility.parseHttpHeaders(responseHttpHeaders);
            final String responseBody = HttpMessageSupport.extractHttpBody(connection, encodings, characterEncoding);
            response.setMockResponseName(FORWARDED_RESPONSE_NAME);
            response.setBody(responseBody);
            response.setHttpHeaders(responseHttpHeaders);
            response.setHttpStatusCode(connection.getResponseCode());
            response.setContentEncodings(encodings);
            return response;
        } catch (IOException exception) {
            LOGGER.error("Unable to forward request", exception);
            throw new SoapException("Unable to forward request to configured endpoint");
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }

}
