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
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.utility.parser.TextParser;
import com.castlemock.core.mock.soap.model.event.domain.SoapEvent;
import com.castlemock.core.mock.soap.model.event.domain.SoapRequest;
import com.castlemock.core.mock.soap.model.event.domain.SoapResponse;
import com.castlemock.core.mock.soap.model.project.domain.*;
import com.castlemock.core.mock.soap.service.event.input.CreateSoapEventInput;
import com.castlemock.core.mock.soap.service.project.input.*;
import com.castlemock.core.mock.soap.service.project.output.IdentifySoapOperationOutput;
import com.castlemock.core.mock.soap.service.project.output.LoadSoapResourceOutput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapProjectOutput;
import com.castlemock.web.basis.support.CharsetUtility;
import com.castlemock.web.basis.support.HttpMessageSupport;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.soap.model.SoapException;
import com.castlemock.web.mock.soap.support.MtomUtility;
import com.castlemock.web.mock.soap.support.SoapUtility;
import com.castlemock.web.mock.soap.utility.compare.SoapMockResponseNameComparator;
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
    private static final int ERROR_CODE = 500;
    private static final Logger LOGGER = Logger.getLogger(AbstractSoapServiceController.class);

    private static final SoapMockResponseNameComparator MOCK_RESPONSE_NAME_COMPARATOR =
            new SoapMockResponseNameComparator();

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
            final SoapRequest request = prepareRequest(projectId, httpServletRequest);
            final IdentifySoapOperationOutput output = serviceProcessor.process(new IdentifySoapOperationInput(projectId, request.getOperationIdentifier(), request.getUri(), request.getHttpMethod(), request.getSoapVersion()));
            final SoapOperation operation = output.getSoapOperation();
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
        final SoapProject soapProject = projectOutput.getSoapProject();

        for(SoapResource soapResource : soapProject.getResources()){
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
    protected SoapRequest prepareRequest(final String projectId, final HttpServletRequest httpServletRequest) {
        final SoapRequest request = new SoapRequest();
        final String body = HttpMessageSupport.getBody(httpServletRequest);

        final SoapOperationIdentifier identifier;
        if(httpServletRequest instanceof MultipartHttpServletRequest){
            // Check if the request is a Multipart request. If so, interpret  the incoming request
            // as a MTOM request and extract the main body (Exclude the attachment).
            // MTOM request mixes both the attachments and the body in the HTTP request body.
            String mainBody = MtomUtility.extractMtomBody(body, httpServletRequest.getContentType());

            // Use the main body to identify
            identifier = SoapUtility.extractSoapRequestName(mainBody);
        } else {
            // The incoming request is a regular SOAP request. Parse the body as it is.
            identifier = SoapUtility.extractSoapRequestName(body);
        }

        final String serviceUri = httpServletRequest.getRequestURI().replace(getContext() + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT + SLASH + projectId + SLASH, EMPTY);
        final List<HttpHeader> httpHeaders = HttpMessageSupport.extractHttpHeaders(httpServletRequest);

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
     * @param soapOperation The operation that contain the appropriate mocked response
     * @param request The incoming request
     * @param httpServletResponse The outgoing HTTP servlet response
     * @return Returns the response as an String
     */
    protected ResponseEntity process(final String soapProjectId, final String soapPortId, final SoapOperation soapOperation, final SoapRequest request, final HttpServletResponse httpServletResponse){
        Preconditions.checkNotNull(request, "Request cannot be null");
        if(soapOperation == null){
            throw new SoapException("Soap operation could not be found");
        }
        SoapEvent event = null;
        SoapResponse response = null;
        try {
            event = new SoapEvent(soapOperation.getName(), request, soapProjectId, soapPortId, soapOperation.getId());
            if (SoapOperationStatus.DISABLED.equals(soapOperation.getStatus())) {
                throw new SoapException("The requested soap operation, " + soapOperation.getName() + ", is disabled");
            } else if (SoapOperationStatus.FORWARDED.equals(soapOperation.getStatus()) ||
                    SoapOperationStatus.RECORDING.equals(soapOperation.getStatus()) ||
                    SoapOperationStatus.RECORD_ONCE.equals(soapOperation.getStatus())) {
                response = forwardRequest(request, soapProjectId, soapPortId, soapOperation);
            } else if (SoapOperationStatus.ECHO.equals(soapOperation.getStatus())) {
                response = echoResponse(request);
            } else { // Status.MOCKED
                response = mockResponse(request, soapProjectId, soapPortId, soapOperation);
            }

            HttpHeaders responseHeaders = new HttpHeaders();
            for(HttpHeader httpHeader : response.getHttpHeaders()){
                List<String> headerValues = new LinkedList<String>();
                headerValues.add(httpHeader.getValue());
                responseHeaders.put(httpHeader.getName(), headerValues);
            }

            if(soapOperation.getSimulateNetworkDelay() &&
                    soapOperation.getNetworkDelay() >= 0){
                try {
                        Thread.sleep(soapOperation.getNetworkDelay());
                } catch (InterruptedException e) {
                    LOGGER.error("Unable to simulate network delay", e);
                }
            }

            return new ResponseEntity<String>(response.getBody(), responseHeaders, HttpStatus.valueOf(response.getHttpStatusCode()));
        } finally{
            if(event != null){
                event.finish(response);
                serviceProcessor.processAsync(CreateSoapEventInput.builder()
                        .soapEvent(event)
                        .build());
            }
        }
    }

    /**
     * The method will echo the incoming {@link SoapRequest} and create a {@link SoapResponse}
     * with the same body, content type and headers.
     *
     * @param request The incoming {@link SoapRequest} that will be echoed back to the
     *                service consumer.
     * @return A {@link SoapResponse} based on the provided {@link SoapRequest}.
     * @since 1.14
     */
    private SoapResponse echoResponse(final SoapRequest request) {
        final List<HttpHeader> headers = new ArrayList<HttpHeader>();
        final HttpHeader contentTypeHeader = new HttpHeader();
        contentTypeHeader.setName(CONTENT_TYPE);
        contentTypeHeader.setValue(request.getContentType());
        headers.add(contentTypeHeader);

        final SoapResponse response = new SoapResponse();
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
     * @param soapOperation The SOAP operation that is being executed. The response is based on
     *                         the provided SOAP operation.
     * @return A mocked response based on the provided SOAP operation
     */
    private SoapResponse mockResponse(SoapRequest request, final String soapProjectId, final String soapPortId, final SoapOperation soapOperation){
        final List<SoapMockResponse> mockResponses = new ArrayList<SoapMockResponse>();
        for(SoapMockResponse mockResponse : soapOperation.getMockResponses()){
            if(mockResponse.getStatus().equals(SoapMockResponseStatus.ENABLED)){
                mockResponses.add(mockResponse);
            }
        }

        mockResponses.sort(MOCK_RESPONSE_NAME_COMPARATOR);

        SoapMockResponse mockResponse = null;
        if(mockResponses.isEmpty()){
            throw new SoapException("No mocked response created for operation " + soapOperation.getName());
        } else if(soapOperation.getResponseStrategy().equals(SoapResponseStrategy.RANDOM)){
            final Integer responseIndex = RANDOM.nextInt(mockResponses.size());
            mockResponse = mockResponses.get(responseIndex);
        } else if(soapOperation.getResponseStrategy().equals(SoapResponseStrategy.SEQUENCE)){
            Integer currentSequenceNumber = soapOperation.getCurrentResponseSequenceIndex();
            if(currentSequenceNumber >= mockResponses.size()){
                currentSequenceNumber = 0;
            }
            mockResponse = mockResponses.get(currentSequenceNumber);
            serviceProcessor.process(new UpdateCurrentMockResponseSequenceIndexInput(soapProjectId, soapPortId, soapOperation.getId(), currentSequenceNumber + 1));
        } else if (soapOperation.getResponseStrategy().equals(SoapResponseStrategy.XPATH_INPUT)) {
            for (SoapMockResponse testedMockResponse : mockResponses) {
                for(SoapXPathExpression xPathExpression : testedMockResponse.getXpathExpressions()){
                    if (SoapUtility.isValidXPathExpr(request.getBody(), xPathExpression.getExpression())) {
                        mockResponse = testedMockResponse;
                        break;
                    }
                }
            }

            if(mockResponse == null){
                LOGGER.info("Unable to match the input XPath to a response");
                final String defaultXPathResponseId = soapOperation.getDefaultXPathMockResponseId();

                if(defaultXPathResponseId != null && !defaultXPathResponseId.isEmpty()){
                    LOGGER.info("Use the default XPath response");
                    for (SoapMockResponse tmpMockResponse : mockResponses) {
                        if(defaultXPathResponseId.equals(mockResponse.getId())){
                            mockResponse = tmpMockResponse;
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
            throw new SoapException("No mocked response created for operation " + soapOperation.getName());
        }

        String body = mockResponse.getBody();
        if(mockResponse.isUsingExpressions()){
            // Parse the text and apply expression functionality if
            // the mock response is configured to use expressions
            body = TextParser.parse(body);
        }
        final SoapResponse response = new SoapResponse();
        response.setBody(body);
        response.setMockResponseName(mockResponse.getName());
        response.setHttpHeaders(mockResponse.getHttpHeaders());
        response.setHttpStatusCode(mockResponse.getHttpStatusCode());
        response.setContentEncodings(mockResponse.getContentEncodings());
        return response;

    }

    /**
     * The method provides the functionality to forward request to a forwarded endpoint.
     * @param request The request that will be forwarded
     * @param soapOperation The SOAP operation that is being executed
     * @return The response from the system that the request was forwarded to.
     */
    private SoapResponse forwardRequest(final SoapRequest request, final String soapProjectId,
                                        final String soapPortId, final SoapOperation soapOperation){
        if(demoMode){
            // If the application is configured to run in demo mode, then use mocked response instead
            return mockResponse(request, soapProjectId, soapPortId, soapOperation);
        }


        HttpURLConnection connection = null;
        try {
            connection = HttpMessageSupport.establishConnection(
                    soapOperation.getForwardedEndpoint(),
                    request.getHttpMethod(),
                    request.getBody(),
                    request.getHttpHeaders());

            final Integer responseCode = connection.getResponseCode();
            final List<ContentEncoding> encodings = HttpMessageSupport.extractContentEncoding(connection);
            final List<HttpHeader> responseHttpHeaders = HttpMessageSupport.extractHttpHeaders(connection);
            final String characterEncoding = CharsetUtility.parseHttpHeaders(responseHttpHeaders);
            final String responseBody = HttpMessageSupport.extractHttpBody(connection, encodings, characterEncoding);
            final SoapResponse response = new SoapResponse();
            response.setMockResponseName(FORWARDED_RESPONSE_NAME);
            response.setBody(responseBody);
            response.setHttpHeaders(responseHttpHeaders);
            response.setHttpStatusCode(responseCode);
            response.setContentEncodings(encodings);
            
            if(response.getHttpStatusCode() >= ERROR_CODE){
                // Check if the response code is an error code
                // If so, then we should check if we should mock
                // instead.
                if(soapOperation.getMockOnFailure()){
                    // Instead of using the forwarded
                    LOGGER.debug("SOAP Operation with the following id has been configured" +
                            " to mock response upon error: " + soapOperation.getId());
                    return this.mockResponse(request, soapProjectId, soapPortId, soapOperation);
                }
            }

            if(SoapOperationStatus.RECORDING.equals(soapOperation.getStatus()) ||
                    SoapOperationStatus.RECORD_ONCE.equals(soapOperation.getStatus())){
                final SoapMockResponse mockResponse = new SoapMockResponse();
                final Date date = new Date();
                mockResponse.setBody(response.getBody());
                mockResponse.setStatus(SoapMockResponseStatus.ENABLED);
                mockResponse.setName(RECORDED_RESPONSE_NAME + SPACE + DATE_FORMAT.format(date));
                mockResponse.setHttpStatusCode(response.getHttpStatusCode());
                mockResponse.setHttpHeaders(response.getHttpHeaders());
                serviceProcessor.processAsync(new CreateSoapMockResponseInput(soapProjectId,
                        soapPortId, soapOperation.getId(), mockResponse));


                if(SoapOperationStatus.RECORD_ONCE.equals(soapOperation.getStatus())){
                    // Change the operation status to mocked if the
                    // operation has been configured to only record once.
                    soapOperation.setStatus(SoapOperationStatus.MOCKED);
                    serviceProcessor.process(new UpdateSoapOperationInput(soapProjectId,
                            soapPortId, soapOperation.getId(), soapOperation));
                }
            }

            return response;
        }catch (IOException exception){
            LOGGER.error("Unable to forward request", exception);

            if(soapOperation.getMockOnFailure()){
                LOGGER.debug("SOAP Operation with the following id has been configured" +
                        " to mock response upon error: " + soapOperation.getId());
                return this.mockResponse(request, soapProjectId, soapPortId, soapOperation);
            }

            throw new SoapException("Unable to forward request to configured endpoint");
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }

}
