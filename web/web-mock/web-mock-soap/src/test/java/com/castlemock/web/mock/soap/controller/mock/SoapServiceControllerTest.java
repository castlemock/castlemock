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

package com.castlemock.web.mock.soap.controller.mock;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.mock.soap.domain.*;
import com.castlemock.service.mock.soap.project.input.IdentifySoapOperationInput;
import com.castlemock.service.mock.soap.project.input.LoadSoapResourceInput;
import com.castlemock.service.mock.soap.project.input.ReadSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.IdentifySoapOperationOutput;
import com.castlemock.service.mock.soap.project.output.LoadSoapResourceOutput;
import com.castlemock.service.mock.soap.project.output.ReadSoapProjectOutput;
import com.castlemock.web.mock.soap.factory.SoapStrategyFactory;
import com.castlemock.web.mock.soap.stategy.SoapStrategy;
import com.castlemock.web.mock.soap.stategy.SoapStrategyResult;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.18
 */
class SoapServiceControllerTest {

    private static final String PROJECT_ID = "ProjectId";
    private static final String SOAP_PORT_ID = "SoapPortId";
    private static final String SOAP_OPERATION_ID = "SoapOperationId";
    protected static final String CONTEXT = "/castlemock";
    protected static final String SLASH = "/";
    protected static final String PROJECT = "project";
    protected static final String MOCK = "mock";
    protected static final String SOAP = "soap";
    private static final String APPLICATION_XML = "application/xml";
    private static final String CONTENT_TYPE_HEADER = "Content-type";
    private static final String ACCEPT_HEADER = "Accept";

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
    public void testMTOM() throws IOException {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final ServletContext servletContext = Mockito.mock(ServletContext.class);
        final SoapStrategyFactory strategyFactory = Mockito.mock(SoapStrategyFactory.class);
        final SoapStrategy strategy = Mockito.mock(SoapStrategy.class);

        // Input
        final HttpServletRequest httpServletRequest = getMockedMultipartHttpServletRequest(REQUEST_MTOM_BODY);
        final SoapOperation soapOperation = SoapOperationTestBuilder.build();
        final IdentifySoapOperationOutput identifySoapOperationOutput = IdentifySoapOperationOutput.builder()
                .projectId(PROJECT_ID)
                .portId(SOAP_PORT_ID)
                .operationId(SOAP_OPERATION_ID)
                .operation(soapOperation)
                .build();
        final SoapResponse soapResponse = SoapResponseTestBuilder.builder()
                .body(RESPONSE_BODY)
                .httpHeaders(List.of(
                        HttpHeader.builder().name(ACCEPT_HEADER).value(APPLICATION_XML).build(),
                        HttpHeader.builder().name(CONTENT_TYPE_HEADER).value(APPLICATION_XML).build()))
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class)))
                .thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn("http://localhost:8080/");
        when(servletContext.getContextPath()).thenReturn(CONTEXT);
        when(strategyFactory.getStrategy(any())).thenReturn(strategy);
        when(strategy.process(any(), any(), any(), any(), any())).thenReturn(SoapStrategyResult.builder()
                .response(soapResponse)
                .build());

        final SoapServiceController controller = new SoapServiceController(serviceProcessor, servletContext , strategyFactory);
        final ResponseEntity<?> responseEntity = controller.postMethod(PROJECT_ID, httpServletRequest);
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
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final ServletContext servletContext = Mockito.mock(ServletContext.class);
        final SoapStrategyFactory strategyFactory = Mockito.mock(SoapStrategyFactory.class);
        final SoapStrategy strategy = Mockito.mock(SoapStrategy.class);

        // Input
        final HttpServletRequest httpServletRequest = getMockedMultipartHttpServletRequest(REQUEST_MTOM_BODY);
        

        final SoapXPathExpression xPathExpression = SoapXPathExpression.builder()
                .expression("//TestService/Variable1[text()='Input1']")
                .build();

        final SoapOperation soapOperation = SoapOperationTestBuilder
                .builder()
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

        final SoapResponse soapResponse = SoapResponseTestBuilder.builder()
                .body(RESPONSE_BODY)
                .httpHeaders(List.of(
                        HttpHeader.builder().name(ACCEPT_HEADER).value(APPLICATION_XML).build(),
                        HttpHeader.builder().name(CONTENT_TYPE_HEADER).value(APPLICATION_XML).build()))
                .build();

        when(serviceProcessor.process(any(IdentifySoapOperationInput.class))).thenReturn(identifySoapOperationOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID);
        when(servletContext.getContextPath()).thenReturn(CONTEXT);
        when(strategyFactory.getStrategy(any())).thenReturn(strategy);
        when(strategy.process(any(), any(), any(), any(), any())).thenReturn(SoapStrategyResult.builder()
                .response(soapResponse)
                .build());


        final SoapServiceController controller = new SoapServiceController(serviceProcessor, servletContext , strategyFactory);
        final ResponseEntity<?> responseEntity = controller.postMethod(PROJECT_ID, httpServletRequest);
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
    public void testGetWsdl() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final ServletContext servletContext = Mockito.mock(ServletContext.class);
        final SoapStrategyFactory strategyFactory = Mockito.mock(SoapStrategyFactory.class);
        final SoapStrategy strategy = Mockito.mock(SoapStrategy.class);

        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final SoapResponse soapResponse = SoapResponseTestBuilder.build();

        when(httpServletRequest.getParameterNames()).thenReturn(Collections.enumeration(Collections.singletonList("wsdl")));
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080" + CONTEXT + SLASH + MOCK + SLASH + SOAP +
                SLASH + PROJECT + SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID));
        when(servletContext.getContextPath()).thenReturn(CONTEXT);
        when(strategyFactory.getStrategy(any())).thenReturn(strategy);
        when(strategy.process(any(), any(), any(), any(), any())).thenReturn(SoapStrategyResult.builder()
                .response(soapResponse)
                .build());

        final SoapProject soapProject = SoapProjectTestBuilder.builder()
                .resources(List.of(SoapResourceTestBuilder.builder()
                    .type(SoapResourceType.WSDL)
                    .build()))
                .build();
        final ReadSoapProjectOutput readSoapProjectOutput = ReadSoapProjectOutput.builder()
                .project(soapProject)
                .build();

        final LoadSoapResourceOutput loadSoapResourceOutput = LoadSoapResourceOutput.builder()
                .resource(WSDL)
                .build();

        when(serviceProcessor.process(isA(ReadSoapProjectInput.class)))
                .thenReturn(readSoapProjectOutput);
        when(serviceProcessor.process(isA(LoadSoapResourceInput.class)))
                .thenReturn(loadSoapResourceOutput);

        final SoapServiceController controller = new SoapServiceController(serviceProcessor, servletContext , strategyFactory);
        final ResponseEntity<?> responseEntity = controller.getMethod(PROJECT_ID, httpServletRequest);
        Assertions.assertEquals(WSDL, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetWsdlWildcard() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final ServletContext servletContext = Mockito.mock(ServletContext.class);
        final SoapStrategyFactory strategyFactory = Mockito.mock(SoapStrategyFactory.class);
        final SoapStrategy strategy = Mockito.mock(SoapStrategy.class);

        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final SoapResponse soapResponse = SoapResponseTestBuilder.build();

        when(httpServletRequest.getParameterNames()).thenReturn(Collections.enumeration(Collections.singletonList("wsdl")));
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080" + CONTEXT + SLASH + MOCK + SLASH + SOAP +
                SLASH + PROJECT + SLASH + PROJECT_ID + SLASH + SOAP_PORT_ID));
        when(servletContext.getContextPath()).thenReturn(CONTEXT);
        when(strategyFactory.getStrategy(any())).thenReturn(strategy);
        when(strategy.process(any(), any(), any(), any(), any())).thenReturn(SoapStrategyResult.builder()
                .response(soapResponse)
                .build());

        final SoapProject soapProject = SoapProjectTestBuilder.builder()
                .resources(List.of(SoapResourceTestBuilder.builder()
                        .type(SoapResourceType.WSDL)
                        .build()))
                .build();
        final ReadSoapProjectOutput readSoapProjectOutput = ReadSoapProjectOutput.builder()
                .project(soapProject)
                .build();

        final LoadSoapResourceOutput loadSoapResourceOutput = LoadSoapResourceOutput.builder()
                .resource(WSDL)
                .build();

        when(serviceProcessor.process(isA(ReadSoapProjectInput.class))).thenReturn(readSoapProjectOutput);
        when(serviceProcessor.process(isA(LoadSoapResourceInput.class))).thenReturn(loadSoapResourceOutput);
        when(servletContext.getContextPath()).thenReturn(CONTEXT);

        final SoapServiceController controller = new SoapServiceController(serviceProcessor, servletContext , strategyFactory);
        final ResponseEntity<?> responseEntity = controller.getWildcardMethod(PROJECT_ID, httpServletRequest);
        Assertions.assertEquals(WSDL, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
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
