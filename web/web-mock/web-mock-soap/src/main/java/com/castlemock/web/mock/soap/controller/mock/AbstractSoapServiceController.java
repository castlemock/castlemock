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
import com.castlemock.model.core.utility.IdUtility;
import com.castlemock.model.mock.soap.domain.*;
import com.castlemock.service.mock.soap.event.input.CreateSoapEventInput;
import com.castlemock.service.mock.soap.project.input.IdentifySoapOperationInput;
import com.castlemock.service.mock.soap.project.input.LoadSoapResourceInput;
import com.castlemock.service.mock.soap.project.input.ReadSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.IdentifySoapOperationOutput;
import com.castlemock.service.mock.soap.project.output.LoadSoapResourceOutput;
import com.castlemock.service.mock.soap.project.output.ReadSoapProjectOutput;
import com.castlemock.web.core.controller.AbstractController;
import com.castlemock.web.mock.soap.converter.HttpServletRequestConverter;
import com.castlemock.web.mock.soap.factory.SoapStrategyFactory;
import com.castlemock.web.mock.soap.model.SoapException;
import com.castlemock.web.mock.soap.stategy.SoapStrategy;
import com.castlemock.web.mock.soap.stategy.SoapStrategyResult;
import com.castlemock.web.mock.soap.utility.SoapUtility;
import com.google.common.base.Preconditions;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * The AbstractSoapServiceController provides functionality that are shared for all the SOAP controllers
 *
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
public abstract class AbstractSoapServiceController extends AbstractController {
    private static final String CONTENT_ENCODING = "Content-Encoding";
    private static final String DEFAULT_CHAR_SET = "charset=\"utf-8\"";
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSoapServiceController.class);
    private final String contextPath;
    private final SoapStrategyFactory strategyFactory;

    protected AbstractSoapServiceController(final ServiceProcessor serviceProcessor,
                                            final ServletContext servletContext,
                                            final SoapStrategyFactory strategyFactory) {
        super(serviceProcessor);
        this.contextPath = Objects.requireNonNull(servletContext.getContextPath(), "contextPath");
        this.strategyFactory = Objects.requireNonNull(strategyFactory, "strategyFactory");
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
            final SoapRequest request = HttpServletRequestConverter.toSoapRequest(httpServletRequest, projectId, contextPath);
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
            LOGGER.debug("SOAP service exception: {}", exception.getMessage(), exception);
            throw new SoapException(exception);
        }
    }

    protected ResponseEntity<?> processGet(final String projectId, final HttpServletRequest httpServletRequest) {
        try {
            Preconditions.checkNotNull(projectId, "The project id cannot be null");
            Preconditions.checkNotNull(httpServletRequest, "The HTTP Servlet Request cannot be null");
            Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                if (parameterName.equalsIgnoreCase("wsdl")) {

                    return getWsdl(projectId)
                            .map(wsdl -> SoapUtility.getWsdlAddress(wsdl, httpServletRequest.getRequestURL().toString()))
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
            LOGGER.debug("SOAP service exception: {}", exception.getMessage(), exception);
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
            final SoapStrategy soapStrategy = this.strategyFactory.getStrategy(soapOperation);
            final SoapStrategyResult result =
                    soapStrategy.process(request, soapProjectId, soapPortId, soapOperation, httpServletRequest);
            response = result.getResponse()
                    .orElseThrow(() -> new IllegalStateException("Unable to process request: " + request));
            result.getPostServiceRequests()
                    .forEach(this.serviceProcessor::processAsync);

            final HttpHeaders headers = getHttpHeaders(request, response);
            simulateNetworkDelay(soapOperation);
            return new ResponseEntity<>(response.getBody(), headers,
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
            this.serviceProcessor.processAsync(CreateSoapEventInput.builder()
                    .soapEvent(event)
                    .build());
        }
    }

    private HttpHeaders getHttpHeaders(final SoapRequest request, final SoapResponse response) {
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

        return responseHeaders;
    }

    private void simulateNetworkDelay(final SoapOperation operation) {
        if (operation.getSimulateNetworkDelay().orElse(false) &&
                operation.getNetworkDelay().orElse(0L) >= 0L) {
            try {
                Thread.sleep(operation.getNetworkDelay().get());
            } catch (InterruptedException e) {
                LOGGER.error("Unable to simulate network delay", e);
            }
        }
    }

}
