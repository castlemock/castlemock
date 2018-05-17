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

package com.castlemock.web.mock.rest.web.rest.controller;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.model.project.service.message.input.IdentifyRestMethodInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.IdentifyRestMethodOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.rest.web.AbstractControllerTest;
import com.castlemock.web.mock.rest.web.mock.controller.RestServiceController;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.18
 */
public class RestServiceControllerTest extends AbstractControllerTest {

    @InjectMocks
    private RestServiceController restServiceController;
    @Mock
    private ServiceProcessor serviceProcessor;

    private static final String PROJECT_ID = "ProjectId";
    private static final String APPLICATION_ID = "ApplicationId";
    private static final String RESOURCE_ID = "ResourceId";
    private static final String METHOD_ID = "MethodId";
    private static final String FORWARD_ENDPOINT = "http://localhost:8080";
    private static final String APPLICATION_XML = "application/xml";
    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE_HEADER = "Content-type";
    private static final String ACCEPT_HEADER = "Accept";
    private static final Map<String, String> PATH_PARAMETERS = new HashMap<>();

    static {
        PATH_PARAMETERS.put("Path", "Valud");
    }

    private static final String REQUEST_BODY = "<request>\n" +
            "\t<variable>Value 1</variable>\n" +
            "</request>";

    private static final String RESPONSE_BODY = "<response>\n" +
            "\t<variable>Value 1</variable>\n" +
            "</response>";


    @Test
    public void testMockedSequence(){
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getMockedRestMethod();

        restMethod.setResponseStrategy(RestResponseStrategy.RANDOM);

        final IdentifyRestMethodOutput identifyRestMethodOutput = new IdentifyRestMethodOutput(PROJECT_ID, APPLICATION_ID, RESOURCE_ID, METHOD_ID, restMethod, PATH_PARAMETERS);


        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + REST + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + APPLICATION + SLASH + APPLICATION_ID + "/method/test");

        final ResponseEntity responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(ACCEPT_HEADER).get(0));
    }


    @Test
    public void testMockedRandom(){
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getMockedRestMethod();

        restMethod.setResponseStrategy(RestResponseStrategy.SEQUENCE);

        final IdentifyRestMethodOutput identifyRestMethodOutput = new IdentifyRestMethodOutput(PROJECT_ID, APPLICATION_ID, RESOURCE_ID, METHOD_ID, restMethod, PATH_PARAMETERS);

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);

        final ResponseEntity responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(ACCEPT_HEADER).get(0));
    }

    @Test
    public void testEcho(){
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getMockedRestMethod();

        restMethod.setStatus(RestMethodStatus.ECHO);

        final IdentifyRestMethodOutput identifyRestMethodOutput = new IdentifyRestMethodOutput(PROJECT_ID, APPLICATION_ID, RESOURCE_ID, METHOD_ID, restMethod, PATH_PARAMETERS);

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + REST + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + APPLICATION + SLASH + APPLICATION_ID + "/method/test");

        final ResponseEntity responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(REQUEST_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertEquals(APPLICATION_JSON, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
    }

    @Override
    protected AbstractController getController() {
        return restServiceController;
    }


    private static class HttpServletRequestTest extends HttpServletRequestWrapper {

        private byte[] bytes;

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request The request to wrap
         * @throws IllegalArgumentException if the request is null
         */
        public HttpServletRequestTest(HttpServletRequest request, String body) {
            super(request);
            this.bytes = body.getBytes();
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {

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
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }
    }

    private HttpServletRequest getMockedHttpServletRequest(final String body){
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        final HttpServletRequest httpServletRequestWrapper = new HttpServletRequestTest(httpServletRequest, body);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + REST + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + APPLICATION + SLASH + APPLICATION_ID + "/method/test");

        when(httpServletRequest.getContentType()).thenReturn(APPLICATION_JSON);

        Enumeration<String> parameterName = Mockito.mock(Enumeration.class);
        Enumeration<String> headerNames = Collections.enumeration(Arrays.asList("Content-Type", "Accept"));
        when(httpServletRequest.getParameterNames()).thenReturn(parameterName);
        when(httpServletRequest.getHeaderNames()).thenReturn(headerNames);
        when(httpServletRequest.getHeader(CONTENT_TYPE_HEADER)).thenReturn(APPLICATION_JSON);
        when(httpServletRequest.getHeader(ACCEPT_HEADER)).thenReturn(APPLICATION_JSON);

        return httpServletRequestWrapper;

    }

    private HttpServletResponse getHttpServletResponse(){
        final HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        return httpServletResponse;
    }

    private RestMethod getMockedRestMethod(){

        final HttpHeader contentTypeHeader = new HttpHeader();
        contentTypeHeader.setName(CONTENT_TYPE_HEADER);
        contentTypeHeader.setValue(APPLICATION_XML);

        final HttpHeader acceptHeader = new HttpHeader();
        acceptHeader.setName(ACCEPT_HEADER);
        acceptHeader.setValue(APPLICATION_XML);

        // Mock
        final RestMockResponse restMockResponse = new RestMockResponse();
        restMockResponse.setBody(RESPONSE_BODY);
        restMockResponse.setContentEncodings(new ArrayList<>());
        restMockResponse.setHttpHeaders(Arrays.asList(contentTypeHeader, acceptHeader));
        restMockResponse.setHttpStatusCode(200);
        restMockResponse.setId("MockResponseId");
        restMockResponse.setName("Mocked response");
        restMockResponse.setStatus(RestMockResponseStatus.ENABLED);
        restMockResponse.setUsingExpressions(false);


        final RestMethod restMethod = new RestMethod();
        restMethod.setCurrentResponseSequenceIndex(0);
        restMethod.setForwardedEndpoint(FORWARD_ENDPOINT);
        restMethod.setHttpMethod(HttpMethod.GET);
        restMethod.setId(METHOD_ID);
        restMethod.setInvokeAddress("http://localhost:8080" + CONTEXT + SLASH + MOCK + SLASH + REST + SLASH +
                PROJECT + SLASH + PROJECT_ID + SLASH + APPLICATION + SLASH + APPLICATION_ID + "/method/test");
        restMethod.setName("Method name");
        restMethod.setNetworkDelay(0);
        restMethod.setResponseStrategy(RestResponseStrategy.SEQUENCE);
        restMethod.setSimulateNetworkDelay(false);
        restMethod.setStatus(RestMethodStatus.MOCKED);
        restMethod.setMockResponses(Arrays.asList(restMockResponse));

        return restMethod;
    }

}
