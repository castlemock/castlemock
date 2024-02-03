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

package com.castlemock.web.mock.soap.web.soap.controller;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.model.mock.soap.domain.SoapMockResponseTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapProjectTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.model.mock.soap.domain.SoapResourceTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapResourceType;
import com.castlemock.model.mock.soap.domain.SoapResponseStrategy;
import com.castlemock.model.mock.soap.domain.SoapXPathExpression;
import com.castlemock.service.mock.soap.project.input.IdentifySoapOperationInput;
import com.castlemock.service.mock.soap.project.input.LoadSoapResourceInput;
import com.castlemock.service.mock.soap.project.input.ReadSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.IdentifySoapOperationOutput;
import com.castlemock.service.mock.soap.project.output.LoadSoapResourceOutput;
import com.castlemock.service.mock.soap.project.output.ReadSoapProjectOutput;
import com.castlemock.web.core.controller.AbstractController;
import com.castlemock.web.mock.soap.controller.mock.SoapClient;
import com.castlemock.web.mock.soap.controller.mock.SoapServiceController;
import com.castlemock.web.mock.soap.model.SoapException;
import com.castlemock.web.mock.soap.web.AbstractControllerTest;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.18
 */
public class SoapServiceControllerTest extends AbstractControllerTest {

    @InjectMocks
    private SoapServiceController soapServiceController;
    @Mock
    private ServiceProcessor serviceProcessor;
    @Mock
    private SoapClient soapClient;

    private static final String PROJECT_ID = "ProjectId";
    private static final String SOAP_PORT_ID = "SoapPortId";
    private static final String SOAP_OPERATION_ID = "SoapOeprationId";
    private static final String FORWARD_ENDPOINT = "http://localhost:8080";
    private static final String APPLICATION_XML = "application/xml";
    private static final String CONTENT_TYPE_HEADER = "Content-type";
    private static final String ACCEPT_HEADER = "Accept";

    private static final String REQUEST_BODY = """
            <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:web="http://www.castlemock.com/">
               <soap:Header/>
               <soap:Body>
                  <web:ServiceName>
                     <web:value>Input</web:value>
                  </web:ServiceName>
               </soap:Body>
            </soap:Envelope>""";

    private static final String REQUEST_MTOM_BODY = """
            ------=_Part_64_1526053806.1517665317492
            Content-Type: text/xml; charset=UTF-8
            Content-Transfer-Encoding: 8bit
            Content-ID: <test@castlemock.org>

            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cas="http://castlemock.com/">
               <soapenv:Header/>
               <soapenv:Body>
                  <cas:TestService>
                     <Variable1>Input1</Variable1>
                     <Variable2>
                        <Variable1>Input2</Variable1>
                        <Variable2>Input3</Variable2>
                        <files/>
                     </Variable2>
                  </cas:TestService>
               </soapenv:Body>
            </soapenv:Envelope>
            ------=_Part_64_1526053806.1517665317492
            Content-Type: text/plain; charset=us-ascii; name="example"
            Content-ID: <example>
            Content-Disposition: attachment; name="example.txt"; filename="example.txt"

            This is an example
            ------=_Part_24_1742827313.1517654770545--""";

    private static final String RESPONSE_BODY = """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://www.castlemock.com/">
               <soapenv:Header/>
               <soapenv:Body>
                  <web:response>
                     <web:value>Value</web:value>
                  </web:response>
               </soapenv:Body>
            </soapenv:Envelope>""";

    private static final String WSDL = """
            <wsdl:definitions>
              <wsdl:service name="Service">
                <wsdl:port name="ServiceHttpPost" binding="tns:ServiceHttpPost">
                  <http:address location="http://www.castlemock.com" />
                </wsdl:port>
              </wsdl:service>
            </wsdl:definitions>""";

    @Test
    public void testMockedAutomaticForwardNoMockedResponseAndForwardURLIsDefined() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);

        final SoapOperation soapOperation = getSoapOperationWithNoMockedResponses()
                .automaticForward(true)
                .responseStrategy(SoapResponseStrategy.SEQUENCE)
                .build();

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        try {
            soapServiceController.postMethod(PROJECT_ID, httpServletRequest);
        } catch (SoapException ignored) {
            // This exception is excepted since the forwarded request cannot be fullfilled in this test due to a connection refused error
        }

        // if getResponse is called it means we are actually forwarding the request
        verify(soapClient, times(1)).getResponse(any(SoapRequest.class), any(SoapOperation.class));
    }

    @Test
    public void testMockedAutomaticForwardNoMockedResponseAndNoForwardURLIsDefined() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        

        final SoapOperation soapOperation = getSoapOperationWithNoMockedResponses()
                .responseStrategy(SoapResponseStrategy.SEQUENCE)
                .forwardedEndpoint(null)
                .build();

        SoapOperation spySoapOperation = spy(soapOperation);

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(spySoapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);
        Assertions.assertThrows(SoapException.class, () -> soapServiceController.postMethod(PROJECT_ID, httpServletRequest));
    }

    @Test
    public void testMockedAutomaticForwardXPathMockedResponseNotMatchingAndForwardURLIsDefined() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        

        final SoapOperation soapOperation = getSoapOperation()
                .automaticForward(true)
                .responseStrategy(SoapResponseStrategy.XPATH_INPUT)
                .build();

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        try {
            soapServiceController.postMethod(PROJECT_ID, httpServletRequest);
        } catch (SoapException ignored) {
            // This exception is excepted since the forwarded request cannot be fullfilled in this test due to a connection refused error
        }

        // if getResponse is called it means we are actually forwarding the request
        verify(soapClient, times(1)).getResponse(any(SoapRequest.class), any(SoapOperation.class));
    }

    @Test
    public void testMockedSequence() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        

        final SoapOperation soapOperation = getSoapOperation()
                .responseStrategy(SoapResponseStrategy.SEQUENCE)
                .build();

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();


        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        final ResponseEntity<?> responseEntity = soapServiceController.postMethod(PROJECT_ID, httpServletRequest);
        Assertions.assertEquals(RESPONSE_BODY, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).getFirst());
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).getFirst());
    }


    @Test
    public void testMockedRandom() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        

        final SoapOperation soapOperation = getSoapOperation()
                .responseStrategy(SoapResponseStrategy.RANDOM)
                .build();

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        final ResponseEntity<?> responseEntity = soapServiceController.postMethod(PROJECT_ID, httpServletRequest);
        Assertions.assertEquals(RESPONSE_BODY, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).getFirst());
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).getFirst());
    }

    @Test
    public void testMockedXpathDefaultResponse() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        

        final SoapOperation soapOperation = getSoapOperation()
                .defaultMockResponseId("MockResponseId")
                .defaultResponseName("Mocked response")
                .responseStrategy(SoapResponseStrategy.XPATH_INPUT)
                .build();

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        final ResponseEntity<?> responseEntity = soapServiceController.postMethod(PROJECT_ID, httpServletRequest);
        Assertions.assertEquals(RESPONSE_BODY, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).getFirst());
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).getFirst());
    }

    @Test
    public void testMockedXpathMatch() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        

        final SoapXPathExpression xPathExpression = SoapXPathExpression.builder()
                .expression("//ServiceName/value[text()='Input']")
                .build();

        final SoapOperation soapOperation = getSoapOperation()
                .responseStrategy(SoapResponseStrategy.XPATH_INPUT)
                .mockResponses(List.of(SoapMockResponseTestBuilder
                        .builder()
                        .body(RESPONSE_BODY)
                        .xpathExpressions(List.of(xPathExpression))
                        .usingExpressions(false)
                        .httpHeaders(List.of(
                                HttpHeader.builder()
                                        .name(CONTENT_TYPE_HEADER)
                                        .value(APPLICATION_XML)
                                        .build(),
                                HttpHeader.builder()
                                        .name(ACCEPT_HEADER)
                                        .value(APPLICATION_XML)
                                        .build()))
                        .build()))
                .build();

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        final ResponseEntity<?> responseEntity = soapServiceController.postMethod(PROJECT_ID, httpServletRequest);
        Assertions.assertEquals(RESPONSE_BODY, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).getFirst());
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).getFirst());
    }

    @Test
    public void testMockedXpathNoMatchAndNoDefaultResponseAndNoAutomaticForward() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        

        final SoapOperation soapOperation = getSoapOperation()
                .automaticForward(false)
                .responseStrategy(SoapResponseStrategy.XPATH_INPUT)
                .build();

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        Assertions.assertThrows(SoapException.class, () -> soapServiceController.postMethod(PROJECT_ID, httpServletRequest));
        verify(soapClient, times(0)).getResponse(any(SoapRequest.class), any(SoapOperation.class));
    }


    @Test
    public void testMTOM() throws IOException {
        // Input
        final HttpServletRequest httpServletRequest = getMockedMultipartHttpServletRequest(REQUEST_MTOM_BODY);
        

        final SoapOperation soapOperation = getSoapOperation()
                .responseStrategy(SoapResponseStrategy.SEQUENCE)
                .build();

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        final ResponseEntity<?> responseEntity = soapServiceController.postMethod(PROJECT_ID, httpServletRequest);
        Assertions.assertEquals(RESPONSE_BODY, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assertions.assertNotNull(responseEntity.getHeaders());
        Assertions.assertNotNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER));
        Assertions.assertNotNull(responseEntity.getHeaders().get(ACCEPT_HEADER));
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).getFirst());
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).getFirst());
    }


    @Test
    public void testMTOMWithXPath() throws IOException {
        // Input
        final HttpServletRequest httpServletRequest = getMockedMultipartHttpServletRequest(REQUEST_MTOM_BODY);
        

        final SoapXPathExpression xPathExpression = SoapXPathExpression.builder()
                .expression("//TestService/Variable1[text()='Input1']")
                .build();

        final SoapOperation soapOperation = getSoapOperation()
                .responseStrategy(SoapResponseStrategy.XPATH_INPUT)
                .mockResponses(List.of(SoapMockResponseTestBuilder
                        .builder()
                        .body(RESPONSE_BODY)
                        .xpathExpressions(List.of(xPathExpression))
                        .usingExpressions(false)
                        .httpHeaders(List.of(
                                HttpHeader.builder()
                                        .name(CONTENT_TYPE_HEADER)
                                        .value(APPLICATION_XML)
                                        .build(),
                                HttpHeader.builder()
                                        .name(ACCEPT_HEADER)
                                        .value(APPLICATION_XML)
                                        .build()))
                        .build()))
                .build();

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        final ResponseEntity<?> responseEntity = soapServiceController.postMethod(PROJECT_ID, httpServletRequest);
        Assertions.assertEquals(RESPONSE_BODY, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assertions.assertNotNull(responseEntity.getHeaders());
        Assertions.assertNotNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER));
        Assertions.assertNotNull(responseEntity.getHeaders().get(ACCEPT_HEADER));
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).getFirst());
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).getFirst());
    }

    @Test
    public void testEcho() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        

        final SoapOperation soapOperation = getSoapOperation()
                .responseStrategy(SoapResponseStrategy.SEQUENCE)
                .status(SoapOperationStatus.ECHO)
                .build();

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        final ResponseEntity<?> responseEntity = soapServiceController.postMethod(PROJECT_ID, httpServletRequest);
        Assertions.assertEquals(REQUEST_BODY, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assertions.assertFalse(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).getFirst());
    }

    @Test
    public void testGetWsdl() {
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        when(httpServletRequest.getParameterNames()).thenReturn(Collections.enumeration(Collections.singletonList("wsdl")));
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080" + CONTEXT + SLASH + MOCK + SLASH + SOAP +
                SLASH + PROJECT + SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID));

        final SoapProject soapProject = getSoapProject();
        final ReadSoapProjectOutput readSoapProjectOutput = ReadSoapProjectOutput.builder()
                .project(soapProject)
                .build();

        
        final LoadSoapResourceOutput loadSoapResourceOutput = LoadSoapResourceOutput.builder()
                .resource(WSDL)
                .build();

        when(serviceProcessor.process(isA(ReadSoapProjectInput.class))).thenReturn(readSoapProjectOutput);
        when(serviceProcessor.process(isA(LoadSoapResourceInput.class))).thenReturn(loadSoapResourceOutput);

        final ResponseEntity<?> responseEntity = soapServiceController.getMethod(PROJECT_ID, httpServletRequest);
        Assertions.assertEquals(WSDL, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetWsdlWildcard() {
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        when(httpServletRequest.getParameterNames()).thenReturn(Collections.enumeration(Collections.singletonList("wsdl")));
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080" + CONTEXT + SLASH + MOCK + SLASH + SOAP +
                SLASH + PROJECT + SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID));

        final SoapProject soapProject = getSoapProject();
        final ReadSoapProjectOutput readSoapProjectOutput = ReadSoapProjectOutput.builder()
                .project(soapProject)
                .build();

        
        final LoadSoapResourceOutput loadSoapResourceOutput = LoadSoapResourceOutput.builder()
                .resource(WSDL)
                .build();

        when(serviceProcessor.process(isA(ReadSoapProjectInput.class))).thenReturn(readSoapProjectOutput);
        when(serviceProcessor.process(isA(LoadSoapResourceInput.class))).thenReturn(loadSoapResourceOutput);

        final ResponseEntity<?> responseEntity = soapServiceController.getWildcardMethod(PROJECT_ID, httpServletRequest);
        Assertions.assertEquals(WSDL, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Override
    protected AbstractController getController() {
        return soapServiceController;
    }

    @SuppressWarnings("unchecked")
    private HttpServletRequest getMockedHttpServletRequest(final String body) {
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        final HttpServletRequest httpServletRequestWrapper = new HttpServletRequestTest(httpServletRequest, body);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        when(httpServletRequest.getContentType()).thenReturn(APPLICATION_XML);

        Enumeration<String> parameterName = Mockito.mock(Enumeration.class);
        Enumeration<String> headerNames = Collections.enumeration(Arrays.asList(CONTENT_TYPE_HEADER, ACCEPT_HEADER));
        when(httpServletRequest.getParameterNames()).thenReturn(parameterName);
        when(httpServletRequest.getHeaderNames()).thenReturn(headerNames);
        when(httpServletRequest.getHeader(CONTENT_TYPE_HEADER)).thenReturn(APPLICATION_XML);
        when(httpServletRequest.getHeader(ACCEPT_HEADER)).thenReturn(APPLICATION_XML);
        when(httpServletRequest.getMethod()).thenReturn("POST");

        return httpServletRequestWrapper;

    }

    @SuppressWarnings("unchecked")
    private MultipartHttpServletRequest getMockedMultipartHttpServletRequest(final String body) throws IOException {
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        final HttpServletRequest httpServletRequestWrapper = new HttpServletRequestTest(httpServletRequest, body);
        final MultipartHttpServletRequest multipartHttpServletRequest = Mockito.mock(MultipartHttpServletRequest.class);

        when(multipartHttpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        String contextType = "multipart/related; type=\"text/xml\"; start=\"<test@castlemock.org>\"; boundary=\"----=_Part_64_1526053806.1517665317492\"";
        when(multipartHttpServletRequest.getContentType()).thenReturn(contextType);

        when(multipartHttpServletRequest.getReader()).thenReturn(httpServletRequestWrapper.getReader());
        Enumeration<String> parameterName = Mockito.mock(Enumeration.class);
        Enumeration<String> headerNames = Collections.enumeration(Arrays.asList(CONTENT_TYPE_HEADER, ACCEPT_HEADER));
        when(multipartHttpServletRequest.getParameterNames()).thenReturn(parameterName);
        when(multipartHttpServletRequest.getHeaderNames()).thenReturn(headerNames);
        when(multipartHttpServletRequest.getHeader(CONTENT_TYPE_HEADER)).thenReturn(contextType);
        when(multipartHttpServletRequest.getHeader(ACCEPT_HEADER)).thenReturn(APPLICATION_XML);
        when(multipartHttpServletRequest.getMethod()).thenReturn("POST");


        return multipartHttpServletRequest;
    }

    private SoapOperation.Builder getSoapOperationWithNoMockedResponses() {
        return SoapOperationTestBuilder
                .builder()
                .currentResponseSequenceIndex(0)
                .forwardedEndpoint(FORWARD_ENDPOINT)
                .httpMethod(HttpMethod.GET)
                .id(SOAP_OPERATION_ID)
                .invokeAddress("http://localhost:8080" + CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH +
                        PROJECT + SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID)
                .name("SOAP operation name")
                .networkDelay(0L)
                .responseStrategy(SoapResponseStrategy.SEQUENCE)
                .simulateNetworkDelay(false)
                .status(SoapOperationStatus.MOCKED)
                .mockResponses(Collections.emptyList());
    }

    private SoapOperation.Builder getSoapOperation() {
        final HttpHeader contentTypeHeader = HttpHeader.builder()
                .name(CONTENT_TYPE_HEADER)
                .value(APPLICATION_XML)
                .build();

        final HttpHeader acceptHeader = HttpHeader.builder()
                .name(ACCEPT_HEADER)
                .value(APPLICATION_XML)
                .build();

        // Mock
        final SoapMockResponse soapMockResponse = SoapMockResponseTestBuilder.builder()
                .id("MockResponseId")
                .name("Mocked response")
                .status(SoapMockResponseStatus.ENABLED)
                .body(RESPONSE_BODY)
                .contentEncodings(new ArrayList<>())
                .httpHeaders(Arrays.asList(contentTypeHeader, acceptHeader))
                .httpStatusCode(200)
                .usingExpressions(false)
                .build();


        return SoapOperationTestBuilder
                .builder()
                    .currentResponseSequenceIndex(0)
                    .forwardedEndpoint(FORWARD_ENDPOINT)
                    .httpMethod(HttpMethod.GET)
                    .id(SOAP_OPERATION_ID)
                    .invokeAddress("http://localhost:8080" + CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH +
                                                            PROJECT + SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID)
                    .name("SOAP operation name")
                    .networkDelay(0L)
                    .responseStrategy(SoapResponseStrategy.SEQUENCE)
                    .simulateNetworkDelay(false)
                    .status(SoapOperationStatus.MOCKED)
                    .mockResponses(Collections.singletonList(soapMockResponse));
    }

    private SoapProject getSoapProject() {
        return SoapProjectTestBuilder.builder()
                .resources(List.of(SoapResourceTestBuilder.builder()
                        .id("Resource id")
                        .name("wsdl")
                        .type(SoapResourceType.WSDL)
                        .build()))
                .build();}


    private static class HttpServletRequestTest extends HttpServletRequestWrapper {

        private final byte[] bytes;

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request The request to wrap
         * @throws IllegalArgumentException if the request is null
         */
        HttpServletRequestTest(final HttpServletRequest request, final String body) {
            super(request);
            this.bytes = body.getBytes();
        }

        @Override
        public ServletInputStream getInputStream() {

            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            return new ServletInputStream() {

                @Override
                public boolean isFinished() {
                    return true;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener listener) {
                    // Not implemented
                }

                @Override
                public int read() {
                    return byteArrayInputStream.read();
                }
            };
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }
    }
}
