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
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.model.mock.soap.domain.SoapResource;
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
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
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

    private static final String REQUEST_BODY = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:web=\"http://www.castlemock.com/\">\n" +
            "   <soap:Header/>\n" +
            "   <soap:Body>\n" +
            "      <web:ServiceName>\n" +
            "         <web:value>Input</web:value>\n" +
            "      </web:ServiceName>\n" +
            "   </soap:Body>\n" +
            "</soap:Envelope>";

    private static final String REQUEST_MTOM_BODY = "------=_Part_64_1526053806.1517665317492\n" +
            "Content-Type: text/xml; charset=UTF-8\n" +
            "Content-Transfer-Encoding: 8bit\n" +
            "Content-ID: <test@castlemock.org>\n" +
            "\n" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cas=\"http://castlemock.com/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <cas:TestService>\n" +
            "         <Variable1>Input1</Variable1>\n" +
            "         <Variable2>\n" +
            "            <Variable1>Input2</Variable1>\n" +
            "            <Variable2>Input3</Variable2>\n" +
            "            <files/>\n" +
            "         </Variable2>\n" +
            "      </cas:TestService>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>\n" +
            "------=_Part_64_1526053806.1517665317492\n" +
            "Content-Type: text/plain; charset=us-ascii; name=\"example\"\n" +
            "Content-ID: <example>\n" +
            "Content-Disposition: attachment; name=\"example.txt\"; filename=\"example.txt\"\n" +
            "\n" +
            "This is an example\n" +
            "------=_Part_24_1742827313.1517654770545--";

    private static final String RESPONSE_BODY = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://www.castlemock.com/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <web:response>\n" +
            "         <web:value>Value</web:value>\n" +
            "      </web:response>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

    private static final String WSDL = "<wsdl:definitions>\n" +
            "  <wsdl:service name=\"Service\">\n" +
            "    <wsdl:port name=\"ServiceHttpPost\" binding=\"tns:ServiceHttpPost\">\n" +
            "      <http:address location=\"http://www.castlemock.com\" />\n" +
            "    </wsdl:port>\n" +
            "  </wsdl:service>\n" +
            "</wsdl:definitions>";

    @Test
    public void testMockedAutomaticForwardNoMockedResponseAndForwardURLIsDefined() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final SoapOperation soapOperation = getSoapOperationWithNoMockedResponses();
        soapOperation.setAutomaticForward(true);
        soapOperation.setResponseStrategy(SoapResponseStrategy.SEQUENCE);

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
            soapServiceController.postMethod(PROJECT_ID, httpServletRequest, httpServletResponse);
        } catch (SoapException ignored) {
            // This exception is excepted since the forwarded request cannot be fullfilled in this test due to a connection refused error
        }

        // if getResponse is called it means we are actually forwarding the request
        verify(soapClient, times(1)).getResponse(any(SoapRequest.class), any(SoapOperation.class));
    }

    @Test(expected = SoapException.class)
    public void testMockedAutomaticForwardNoMockedResponseAndNoForwardURLIsDefined() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final SoapOperation soapOperation = getSoapOperationWithNoMockedResponses();
        soapOperation.setResponseStrategy(SoapResponseStrategy.SEQUENCE);
        soapOperation.setForwardedEndpoint(null);
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

        soapServiceController.postMethod(PROJECT_ID, httpServletRequest, httpServletResponse);
    }

    @Test
    public void testMockedAutomaticForwardXPathMockedResponseNotMatchingAndForwardURLIsDefined() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final SoapOperation soapOperation = getSoapOperation();
        soapOperation.setAutomaticForward(true);
        soapOperation.setResponseStrategy(SoapResponseStrategy.XPATH_INPUT);

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
            soapServiceController.postMethod(PROJECT_ID, httpServletRequest, httpServletResponse);
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
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final SoapOperation soapOperation = getSoapOperation();
        soapOperation.setResponseStrategy(SoapResponseStrategy.SEQUENCE);

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();


        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        final ResponseEntity<?> responseEntity = soapServiceController.postMethod(PROJECT_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).get(0));
        Assert.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).get(0));
    }


    @Test
    public void testMockedRandom() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final SoapOperation soapOperation = getSoapOperation();
        soapOperation.setResponseStrategy(SoapResponseStrategy.RANDOM);

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        final ResponseEntity<?> responseEntity = soapServiceController.postMethod(PROJECT_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).get(0));
        Assert.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).get(0));
    }

    @Test
    public void testMockedXpathDefaultResponse() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final SoapOperation soapOperation = getSoapOperation();

        soapOperation.setDefaultResponseName("Mocked response");
        soapOperation.setDefaultMockResponseId("MockResponseId");
        soapOperation.setResponseStrategy(SoapResponseStrategy.XPATH_INPUT);

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        final ResponseEntity<?> responseEntity = soapServiceController.postMethod(PROJECT_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).get(0));
        Assert.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).get(0));
    }

    @Test
    public void testMockedXpathMatch() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final SoapXPathExpression xPathExpression = new SoapXPathExpression();
        xPathExpression.setExpression("//ServiceName/value[text()='Input']");

        final SoapOperation soapOperation = getSoapOperation();
        soapOperation.getMockResponses().get(0).getXpathExpressions().add(xPathExpression);

        soapOperation.setResponseStrategy(SoapResponseStrategy.XPATH_INPUT);

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        final ResponseEntity<?> responseEntity = soapServiceController.postMethod(PROJECT_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).get(0));
        Assert.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).get(0));
    }

    @Test
    public void testMockedXpathNoMatchAndNoDefaultResponseAndNoAutomaticForward() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final SoapOperation soapOperation = getSoapOperation();
        soapOperation.setAutomaticForward(false);
        soapOperation.setResponseStrategy(SoapResponseStrategy.XPATH_INPUT);

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        Assert.assertThrows(SoapException.class, () -> {
            soapServiceController.postMethod(PROJECT_ID, httpServletRequest, httpServletResponse);
        });
        verify(soapClient, times(0)).getResponse(any(SoapRequest.class), any(SoapOperation.class));
    }


    @Test
    public void testMTOM() throws IOException {
        // Input
        final HttpServletRequest httpServletRequest = getMockedMultipartHttpServletRequest(REQUEST_MTOM_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final SoapXPathExpression xPathExpression = new SoapXPathExpression();
        xPathExpression.setExpression("//ServiceName/Variable1[text()='?']");


        final SoapOperation soapOperation = getSoapOperation();
        soapOperation.setResponseStrategy(SoapResponseStrategy.SEQUENCE);

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        final ResponseEntity<?> responseEntity = soapServiceController.postMethod(PROJECT_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertNotNull(responseEntity.getHeaders());
        Assert.assertNotNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER));
        Assert.assertNotNull(responseEntity.getHeaders().get(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).get(0));
        Assert.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).get(0));
    }


    @Test
    public void testMTOMWithXPath() throws IOException {
        // Input
        final HttpServletRequest httpServletRequest = getMockedMultipartHttpServletRequest(REQUEST_MTOM_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final SoapXPathExpression xPathExpression = new SoapXPathExpression();
        xPathExpression.setExpression("//TestService/Variable1[text()='Input1']");

        final SoapOperation soapOperation = getSoapOperation();
        soapOperation.getMockResponses().get(0).getXpathExpressions().add(xPathExpression);

        soapOperation.setResponseStrategy(SoapResponseStrategy.XPATH_INPUT);

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        final ResponseEntity<?> responseEntity = soapServiceController.postMethod(PROJECT_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertNotNull(responseEntity.getHeaders());
        Assert.assertNotNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER));
        Assert.assertNotNull(responseEntity.getHeaders().get(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).get(0));
        Assert.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).get(0));
    }

    @Test
    public void testEcho() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final SoapOperation soapOperation = getSoapOperation();
        soapOperation.setResponseStrategy(SoapResponseStrategy.SEQUENCE);
        soapOperation.setStatus(SoapOperationStatus.ECHO);

        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);

        final ResponseEntity<?> responseEntity = soapServiceController.postMethod(PROJECT_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(REQUEST_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertFalse(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).get(0));
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

        final HttpServletResponse httpServletResponse = getHttpServletResponse();
        final LoadSoapResourceOutput loadSoapResourceOutput = LoadSoapResourceOutput.builder()
                .resource(WSDL)
                .build();

        when(serviceProcessor.process(isA(ReadSoapProjectInput.class))).thenReturn(readSoapProjectOutput);
        when(serviceProcessor.process(isA(LoadSoapResourceInput.class))).thenReturn(loadSoapResourceOutput);

        final ResponseEntity<?> responseEntity = soapServiceController.getMethod(PROJECT_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(WSDL, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
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

        final HttpServletResponse httpServletResponse = getHttpServletResponse();
        final LoadSoapResourceOutput loadSoapResourceOutput = LoadSoapResourceOutput.builder()
                .resource(WSDL)
                .build();

        when(serviceProcessor.process(isA(ReadSoapProjectInput.class))).thenReturn(readSoapProjectOutput);
        when(serviceProcessor.process(isA(LoadSoapResourceInput.class))).thenReturn(loadSoapResourceOutput);

        final ResponseEntity<?> responseEntity = soapServiceController.getWildcardMethod(PROJECT_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(WSDL, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
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
        Enumeration<String> headerNames = Collections.enumeration(Arrays.asList("Content-Type", "Accept"));
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
        Enumeration<String> headerNames = Collections.enumeration(Arrays.asList("Content-Type", "Accept"));
        when(multipartHttpServletRequest.getParameterNames()).thenReturn(parameterName);
        when(multipartHttpServletRequest.getHeaderNames()).thenReturn(headerNames);
        when(multipartHttpServletRequest.getHeader(contextType)).thenReturn(APPLICATION_XML);
        when(multipartHttpServletRequest.getHeader(ACCEPT_HEADER)).thenReturn(APPLICATION_XML);
        when(multipartHttpServletRequest.getMethod()).thenReturn("POST");


        return multipartHttpServletRequest;
    }

    private HttpServletResponse getHttpServletResponse() {
        return Mockito.mock(HttpServletResponse.class);
    }

    private SoapOperation getSoapOperationWithNoMockedResponses() {
        final HttpHeader contentTypeHeader = new HttpHeader();
        contentTypeHeader.setName(CONTENT_TYPE_HEADER);
        contentTypeHeader.setValue(APPLICATION_XML);

        final HttpHeader acceptHeader = new HttpHeader();
        acceptHeader.setName(ACCEPT_HEADER);
        acceptHeader.setValue(APPLICATION_XML);

        final SoapOperation soapOperation = new SoapOperation();
        soapOperation.setCurrentResponseSequenceIndex(0);
        soapOperation.setForwardedEndpoint(FORWARD_ENDPOINT);
        soapOperation.setHttpMethod(HttpMethod.GET);
        soapOperation.setId(SOAP_OPERATION_ID);
        soapOperation.setInvokeAddress("http://localhost:8080" + CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH +
                PROJECT + SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);
        soapOperation.setName("SOAP operation name");
        soapOperation.setNetworkDelay(0L);
        soapOperation.setResponseStrategy(SoapResponseStrategy.SEQUENCE);
        soapOperation.setSimulateNetworkDelay(false);
        soapOperation.setStatus(SoapOperationStatus.MOCKED);
        soapOperation.setMockResponses(Collections.emptyList());
        soapOperation.setMockOnFailure(false);

        return soapOperation;
    }

    private SoapOperation getSoapOperation() {

        final HttpHeader contentTypeHeader = new HttpHeader();
        contentTypeHeader.setName(CONTENT_TYPE_HEADER);
        contentTypeHeader.setValue(APPLICATION_XML);

        final HttpHeader acceptHeader = new HttpHeader();
        acceptHeader.setName(ACCEPT_HEADER);
        acceptHeader.setValue(APPLICATION_XML);

        // Mock
        final SoapMockResponse soapMockResponse = new SoapMockResponse();
        soapMockResponse.setBody(RESPONSE_BODY);
        soapMockResponse.setContentEncodings(new ArrayList<>());
        soapMockResponse.setHttpHeaders(Arrays.asList(contentTypeHeader, acceptHeader));
        soapMockResponse.setHttpStatusCode(200);
        soapMockResponse.setId("MockResponseId");
        soapMockResponse.setName("Mocked response");
        soapMockResponse.setStatus(SoapMockResponseStatus.ENABLED);
        soapMockResponse.setUsingExpressions(false);


        final SoapOperation soapOperation = new SoapOperation();
        soapOperation.setCurrentResponseSequenceIndex(0);
        soapOperation.setForwardedEndpoint(FORWARD_ENDPOINT);
        soapOperation.setHttpMethod(HttpMethod.GET);
        soapOperation.setId(SOAP_OPERATION_ID);
        soapOperation.setInvokeAddress("http://localhost:8080" + CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH +
                PROJECT + SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);
        soapOperation.setName("SOAP operation name");
        soapOperation.setNetworkDelay(0L);
        soapOperation.setResponseStrategy(SoapResponseStrategy.SEQUENCE);
        soapOperation.setSimulateNetworkDelay(false);
        soapOperation.setStatus(SoapOperationStatus.MOCKED);
        soapOperation.setMockResponses(Collections.singletonList(soapMockResponse));

        return soapOperation;
    }

    private SoapProject getSoapProject() {
        final SoapProject soapProject = new SoapProject();
        final SoapResource soapResource = new SoapResource();

        soapResource.setId("Resource id");
        soapResource.setName("wsdl");
        soapResource.setType(SoapResourceType.WSDL);
        soapProject.setResources(Collections.singletonList(soapResource));
        return soapProject;
    }


    private static class HttpServletRequestTest extends HttpServletRequestWrapper {

        private byte[] bytes;

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
