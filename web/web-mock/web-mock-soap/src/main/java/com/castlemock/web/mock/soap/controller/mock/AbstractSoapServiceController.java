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

package com.castlemock.web.mock.soap.controller.mock;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.core.utility.IdUtility;
import com.castlemock.model.core.utility.XPathUtility;
import com.castlemock.model.core.utility.parser.ExternalInputBuilder;
import com.castlemock.model.core.utility.parser.TextParser;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.model.mock.soap.domain.SoapEvent;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationIdentifier;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.model.mock.soap.domain.SoapResourceType;
import com.castlemock.model.mock.soap.domain.SoapResponse;
import com.castlemock.model.mock.soap.domain.SoapResponseStrategy;
import com.castlemock.model.mock.soap.domain.SoapVersion;
import com.castlemock.model.mock.soap.domain.SoapXPathExpression;
import com.castlemock.service.mock.soap.event.input.CreateSoapEventInput;
import com.castlemock.service.mock.soap.project.input.CreateSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.input.IdentifySoapOperationInput;
import com.castlemock.service.mock.soap.project.input.LoadSoapResourceInput;
import com.castlemock.service.mock.soap.project.input.ReadSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.UpdateCurrentMockResponseSequenceIndexInput;
import com.castlemock.service.mock.soap.project.input.UpdateSoapOperationsStatusInput;
import com.castlemock.service.mock.soap.project.output.IdentifySoapOperationOutput;
import com.castlemock.service.mock.soap.project.output.LoadSoapResourceOutput;
import com.castlemock.service.mock.soap.project.output.ReadSoapProjectOutput;
import com.castlemock.service.mock.soap.utility.SoapUtility;
import com.castlemock.web.core.controller.AbstractController;
import com.castlemock.web.core.utility.HttpMessageSupport;
import com.castlemock.web.mock.soap.model.SoapException;
import com.castlemock.web.mock.soap.utility.MtomUtility;
import com.castlemock.web.mock.soap.utility.compare.SoapMockResponseNameComparator;
import com.castlemock.web.mock.soap.utility.config.AddressLocationConfigurer;
import com.google.common.base.Preconditions;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * The AbstractSoapServiceController provides functionality that are shared for all the SOAP controllers
 *
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
public abstract class AbstractSoapServiceController extends AbstractController {

    private static final String RECORDED_RESPONSE_NAME = "Recorded response";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final String CONTENT_ENCODING = "Content-Encoding";
    private static final Random RANDOM = new Random();
    private static final String SOAP = "soap";
    private static final int ERROR_CODE = 500;
    private static final String DEFAULT_CHAR_SET = "charset=\"utf-8\"";
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSoapServiceController.class);

    private static final SoapMockResponseNameComparator MOCK_RESPONSE_NAME_COMPARATOR =
            new SoapMockResponseNameComparator();

    private final ServletContext servletContext;

    private final SoapClient soapHttpClient;

    protected AbstractSoapServiceController(final ServiceProcessor serviceProcessor,
                                            final ServletContext servletContext,
                                            SoapClient soapClient) {
        super(serviceProcessor);
        this.servletContext = Objects.requireNonNull(servletContext);
        this.soapHttpClient = soapClient;
    }

    /**
     * Process the incoming message by forwarding it to the main process method in
     * the AbstractServiceController class. However, the Protocol value SOAP is being
     * sent to the AbstractServiceController. This is used by AbstractServiceController
     * to indicate the type of the incoming request
     *
     * @param projectId           The id of the project which the incoming request and mocked response belongs to
     * @param httpServletRequest  The incoming request
     * @return Returns the response as a String
     */
    protected ResponseEntity<?> process(final String projectId,
                                        final HttpServletRequest httpServletRequest) {
        try {
            Preconditions.checkNotNull(projectId, "THe project id cannot be null");
            Preconditions.checkNotNull(httpServletRequest, "The HTTP Servlet Request cannot be null");
            final SoapRequest request = prepareRequest(projectId, httpServletRequest);
            final IdentifySoapOperationOutput output = serviceProcessor.process(IdentifySoapOperationInput.builder()
                    .projectId(projectId)
                    .operationIdentifier(request.getOperationIdentifier())
                    .uri(request.getUri())
                    .httpMethod(request.getHttpMethod())
                    .type(request.getSoapVersion())
                    .build());
            final SoapOperation operation = output.getOperation();
            return process(projectId, output.getPortId(), operation, request, httpServletRequest);
        } catch (Exception exception) {
            LOGGER.debug("SOAP service exception: " + exception.getMessage(), exception);
            throw new SoapException(exception);
        }
    }

    protected ResponseEntity<?> processGet(final String projectId, final HttpServletRequest httpServletRequest) {
        try {
            Preconditions.checkNotNull(projectId, "THe project id cannot be null");
            Preconditions.checkNotNull(httpServletRequest, "The HTTP Servlet Request cannot be null");
            Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                if (parameterName.equalsIgnoreCase("wsdl")) {

                    return getWsdl(projectId)
                            .map(wsdl -> AddressLocationConfigurer.configureAddressLocation(wsdl, httpServletRequest.getRequestURL().toString()))
                            .map(wsdl -> {
                                final HttpHeaders responseHeaders = new HttpHeaders();
                                responseHeaders.put(CONTENT_TYPE, List.of("text/xml; " + DEFAULT_CHAR_SET));
                                return new ResponseEntity<>(wsdl, responseHeaders, HttpStatus.OK);
                            })
                            .orElseGet(() -> ResponseEntity.notFound().build());
                }
            }

            throw new IllegalArgumentException("Unable to parse incoming message");
        } catch (Exception exception) {
            LOGGER.debug("SOAP service exception: " + exception.getMessage(), exception);
            throw new SoapException(exception);
        }
    }

    private Optional<String> getWsdl(final String projectId) {
        final ReadSoapProjectOutput projectOutput = this.serviceProcessor.process(ReadSoapProjectInput.builder()
                .projectId(projectId)
                .build());
        return projectOutput.getProject()
                .flatMap(soapProject -> soapProject.getResources()
                        .stream()
                        .filter(soapResource -> SoapResourceType.WSDL.equals(soapResource.getType()))
                        .findFirst()
                        .flatMap(soapResource -> {
                    final LoadSoapResourceOutput loadOutput =
                            this.serviceProcessor.process(LoadSoapResourceInput.builder()
                                    .projectId(projectId)
                                    .resourceId(soapResource.getId())
                                    .build());
                    return loadOutput.getResource();
                        }));
    }

    /**
     * The method prepares an request
     *
     * @param projectId          The id of the project that the incoming request belongs to
     * @param httpServletRequest The incoming request
     * @return A new created project
     */
    private SoapRequest prepareRequest(final String projectId, final HttpServletRequest httpServletRequest) {
        final String body = HttpMessageSupport.getBody(httpServletRequest);

        final SoapOperationIdentifier identifier;
        final String envelope;
        if (httpServletRequest instanceof MultipartHttpServletRequest) {
            // Check if the request is a Multipart request. If so, interpret  the incoming request
            // as a MTOM request and extract the main body (Exclude the attachment).
            // MTOM request mixes both the attachments and the body in the HTTP request body.
            envelope = MtomUtility.extractMtomBody(body, httpServletRequest.getContentType());

            // Use the main body to identify
            identifier = SoapUtility.extractSoapRequestName(envelope);
        } else {
            // The incoming request is a regular SOAP request. Parse the body as it is.
            envelope = body;
            identifier = SoapUtility.extractSoapRequestName(body);
        }

        final String serviceUri = httpServletRequest.getRequestURI().replace(servletContext.getContextPath() +
                SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT + SLASH + projectId + SLASH, EMPTY);
        final List<HttpHeader> httpHeaders = HttpMessageSupport.extractHttpHeaders(httpServletRequest);

        final SoapVersion type = SoapVersion.convert(httpServletRequest.getContentType());
        return SoapRequest.builder()
                .soapVersion(type)
                .httpHeaders(httpHeaders)
                .uri(serviceUri)
                .httpMethod(HttpMethod.valueOf(httpServletRequest.getMethod()))
                .body(body)
                .envelope(envelope)
                .operationIdentifier(identifier)
                .contentType(httpServletRequest.getContentType())
                .build();
    }

    /**
     * The process method is responsible for processing the incoming request and
     * finding the appropriate response. The method is also responsible for creating
     * events and storing them.
     *
     * @param soapProjectId       The id of the project that the incoming request belong to
     * @param soapPortId          The id of the port that the incoming request belong to
     * @param soapOperation       The operation that contain the appropriate mocked response
     * @param request             The incoming request
     * @return Returns the response as a String
     */
    protected ResponseEntity<?> process(final String soapProjectId,
                                        final String soapPortId,
                                        final SoapOperation soapOperation,
                                        final SoapRequest request,
                                        final HttpServletRequest httpServletRequest) {
        Preconditions.checkNotNull(request, "Request cannot be null");
        if (soapOperation == null) {
            throw new SoapException("Soap operation could not be found");
        }
        final Date startDate = new Date();
        SoapResponse response = null;
        try {
            if (SoapOperationStatus.DISABLED.equals(soapOperation.getStatus())) {
                throw new SoapException("The requested soap operation, " + soapOperation.getName() + ", is disabled");
            } else if (SoapOperationStatus.FORWARDED.equals(soapOperation.getStatus()) ||
                    SoapOperationStatus.RECORDING.equals(soapOperation.getStatus()) ||
                    SoapOperationStatus.RECORD_ONCE.equals(soapOperation.getStatus())) {
                response = forwardRequest(request, soapProjectId, soapPortId, soapOperation, httpServletRequest);
            } else if (SoapOperationStatus.ECHO.equals(soapOperation.getStatus())) {
                response = echoResponse(request);
            } else { // Status.MOCKED
                response = mockResponse(request, soapProjectId, soapPortId, soapOperation, httpServletRequest);
            }

            final HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.put(CONTENT_TYPE,
                    List.of(request.getSoapVersion().getContextType() + "; " + DEFAULT_CHAR_SET));
            for (HttpHeader httpHeader : response.getHttpHeaders()) {
                responseHeaders.put(httpHeader.getName(), List.of(httpHeader.getValue()));
            }

            response.getHttpHeaders()
                    .stream()
                    .filter(httpHeader -> !httpHeader.getName().equalsIgnoreCase(CONTENT_ENCODING))
                    .forEach(httpHeader -> responseHeaders.put(httpHeader.getName(), List.of(httpHeader.getValue())));

            if (soapOperation.getSimulateNetworkDelay().orElse(false) &&
                    soapOperation.getNetworkDelay().orElse(0L) >= 0L) {
                try {
                    Thread.sleep(soapOperation.getNetworkDelay().get());
                } catch (InterruptedException e) {
                    LOGGER.error("Unable to simulate network delay", e);
                }
            }

            return new ResponseEntity<>(response.getBody(), responseHeaders,
                    HttpStatus.valueOf(response.getHttpStatusCode()));
        } finally {
            final SoapEvent event = SoapEvent.builder()
                    .id(IdUtility.generateId())
                    .startDate(startDate)
                    .endDate(new Date())
                    .resourceName(soapOperation.getName())
                    .request(request)
                    .response(response)
                    .projectId(soapProjectId)
                    .portId(soapPortId)
                    .operationId(soapOperation.getId())
                    .build();
            serviceProcessor.processAsync(CreateSoapEventInput.builder()
                    .soapEvent(event)
                    .build());
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
        final List<HttpHeader> headers = List.of(HttpHeader.builder()
                .name(CONTENT_TYPE)
                .value(request.getContentType())
                .build());

        return SoapResponse.builder()
                .body(request.getBody())
                .contentType(request.getContentType())
                .httpHeaders(headers)
                .httpStatusCode(DEFAULT_ECHO_RESPONSE_CODE)
                .contentEncodings(Collections.emptyList())
                .build();
    }

    /**
     * The method is responsible for retrieving and returning a mocked response for the provided operation
     *
     * @param soapOperation The SOAP operation that is being executed. The response is based on
     *                      the provided SOAP operation.
     * @return A mocked response based on the provided SOAP operation
     */
    private SoapResponse mockResponse(final SoapRequest request,
                                      final String soapProjectId,
                                      final String soapPortId,
                                      final SoapOperation soapOperation,
                                      final HttpServletRequest httpServletRequest) {
        final List<SoapMockResponse> mockResponses = new ArrayList<>();
        for (SoapMockResponse mockResponse : soapOperation.getMockResponses()) {
            if (mockResponse.getStatus().equals(SoapMockResponseStatus.ENABLED)) {
                mockResponses.add(mockResponse);
            }
        }

        mockResponses.sort(MOCK_RESPONSE_NAME_COMPARATOR);

        SoapMockResponse mockResponse = null;
        if (mockResponses.isEmpty()) {
            if (soapOperation.getAutomaticForward().orElse(false) && soapOperation.getForwardedEndpoint().isPresent()) {
                return forwardRequest(request, soapProjectId, soapPortId, soapOperation, httpServletRequest);
            }
        } else if (soapOperation.getResponseStrategy().equals(SoapResponseStrategy.RANDOM)) {
            final int responseIndex = RANDOM.nextInt(mockResponses.size());
            mockResponse = mockResponses.get(responseIndex);
        } else if (soapOperation.getResponseStrategy().equals(SoapResponseStrategy.SEQUENCE)) {
            Integer currentSequenceNumber = soapOperation.getCurrentResponseSequenceIndex();
            if (currentSequenceNumber >= mockResponses.size()) {
                currentSequenceNumber = 0;
            }
            mockResponse = mockResponses.get(currentSequenceNumber);
            serviceProcessor.process(UpdateCurrentMockResponseSequenceIndexInput.builder()
                    .projectId(soapProjectId)
                    .portId(soapPortId)
                    .operationId(soapOperation.getId())
                    .currentResponseSequenceIndex(currentSequenceNumber + 1)
                    .build());
        } else if (soapOperation.getResponseStrategy().equals(SoapResponseStrategy.XPATH_INPUT)) {
            for (SoapMockResponse testedMockResponse : mockResponses) {
                for (SoapXPathExpression xPathExpression : testedMockResponse.getXpathExpressions()) {
                    if (XPathUtility.isValidXPathExpr(request.getEnvelope(), xPathExpression.getExpression())) {
                        mockResponse = testedMockResponse;
                        break;
                    }
                }
            }


            if (mockResponse == null) {
                LOGGER.info("Unable to match the input XPath to a response");
                mockResponse = this.getDefaultMockResponse(soapOperation, mockResponses).orElse(null);

                if (mockResponse == null && soapOperation.getAutomaticForward().orElse(false
                ) && soapOperation.getForwardedEndpoint().isPresent()) {
                    return forwardRequest(request, soapProjectId, soapPortId, soapOperation, httpServletRequest);
                }
            }
        }

        if (mockResponse == null) {
            throw new SoapException("No mocked response created for operation " + soapOperation.getName());
        }

        String body = mockResponse.getBody();
        if (mockResponse.getUsingExpressions().orElse(false)) {

            final Map<String, ExpressionArgument<?>> externalInput = new ExternalInputBuilder()
                    .requestUrl(httpServletRequest.getRequestURL().toString())
                    .requestBody(request.getBody())
                    .build();

            body = new TextParser().parse(body, externalInput)
                    .orElse("");
        }
        return SoapResponse.builder()
                .body(body)
                .mockResponseName(mockResponse.getName())
                .httpHeaders(mockResponse.getHttpHeaders())
                .httpStatusCode(mockResponse.getHttpStatusCode())
                .contentEncodings(mockResponse.getContentEncodings())
                .build();
    }

    private Optional<SoapMockResponse> getDefaultMockResponse(final SoapOperation soapOperation,
                                                              final List<SoapMockResponse> mockResponses) {
        final String defaultResponseId = soapOperation.getDefaultMockResponseId()
                .orElse(null);

        if (defaultResponseId != null && !defaultResponseId.isEmpty()) {
            LOGGER.info("Use the default response");
            for (SoapMockResponse tmpMockResponse : mockResponses) {
                if (defaultResponseId.equals(tmpMockResponse.getId())) {
                    return Optional.of(tmpMockResponse);
                }
            }
            LOGGER.error("Unable to find the default response");
        }
        return Optional.empty();
    }

    /**
     * The method provides the functionality to forward request to a forwarded endpoint.
     *
     * @param request       The request that will be forwarded
     * @param soapOperation The SOAP operation that is being executed
     * @return The response from the system that the request was forwarded to.
     */
    private SoapResponse forwardRequest(final SoapRequest request,
                                        final String soapProjectId,
                                        final String soapPortId,
                                        final SoapOperation soapOperation,
                                        final HttpServletRequest httpServletRequest) {
        Optional<SoapResponse> optionalSoapResponse = soapHttpClient.getResponse(request, soapOperation);

        if (optionalSoapResponse.isPresent()) {
            SoapResponse response = optionalSoapResponse.get();
            if (response.getHttpStatusCode() >= ERROR_CODE) {
                // Check if the response code is an error code
                // If so, then we should check if we should mock instead.
                if (soapOperation.getMockOnFailure().orElse(false)) {
                    // Instead of using the forwarded
                    LOGGER.debug("SOAP Operation with the following id has been configured" +
                            " to mock response upon error: " + soapOperation.getId());
                    return this.mockResponse(request, soapProjectId, soapPortId, soapOperation, httpServletRequest);
                }
            }

            if (SoapOperationStatus.RECORDING.equals(soapOperation.getStatus()) ||
                    SoapOperationStatus.RECORD_ONCE.equals(soapOperation.getStatus())) {

                serviceProcessor.processAsync(CreateSoapMockResponseInput.builder()
                        .projectId(soapProjectId)
                        .portId(soapPortId)
                        .operationId(soapOperation.getId())
                        .body(response.getBody())
                        .status(SoapMockResponseStatus.ENABLED)
                        .name(RECORDED_RESPONSE_NAME + SPACE + DATE_FORMAT.format(new Date()))
                        .httpHeaders(response.getHttpHeaders())
                        .httpStatusCode(response.getHttpStatusCode())
                        .usingExpressions(Boolean.FALSE)
                        .build());


                if (SoapOperationStatus.RECORD_ONCE.equals(soapOperation.getStatus())) {
                    // Change the operation status to mocked if the
                    // operation has been configured to only record once.
                    serviceProcessor.process(UpdateSoapOperationsStatusInput.builder()
                            .projectId(soapProjectId)
                            .portId(soapPortId)
                            .operationId(soapOperation.getId())
                            .operationStatus(SoapOperationStatus.MOCKED)
                            .build());
                }
            }

            return response;
        } else {
            if (soapOperation.getMockOnFailure().orElse(false)) {
                LOGGER.debug("SOAP Operation with the following id has been configured" +
                        " to mock response upon error: " + soapOperation.getId());
                return this.mockResponse(request, soapProjectId, soapPortId, soapOperation, httpServletRequest);
            }

            throw new SoapException("Unable to forward request to configured endpoint");
        }
    }

}
