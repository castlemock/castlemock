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

import com.castlemock.model.core.model.ServiceProcessor;
import com.castlemock.model.core.model.http.domain.HttpHeader;
import com.castlemock.model.core.model.http.domain.HttpMethod;
import com.castlemock.model.mock.rest.domain.RestJsonPathExpression;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestMethodTestBuilder;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;
import com.castlemock.model.mock.rest.domain.RestMockResponseTestBuilder;
import com.castlemock.model.mock.rest.domain.RestParameterQuery;
import com.castlemock.model.mock.rest.domain.RestParameterQueryTestBuilder;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;
import com.castlemock.model.mock.rest.domain.RestXPathExpression;
import com.castlemock.service.mock.rest.project.input.IdentifyRestMethodInput;
import com.castlemock.service.mock.rest.project.output.IdentifyRestMethodOutput;
import com.castlemock.web.core.controller.AbstractController;
import com.castlemock.web.mock.rest.web.AbstractControllerTest;
import com.castlemock.web.mock.rest.controller.mock.RestServiceController;
import com.google.common.collect.ImmutableMap;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
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
    private static final Map<String, String> PATH_PARAMETERS = ImmutableMap.of("Path", "Value");
    private static final Map<String, String> NO_MATCHING_PATH_PARAMETERS = ImmutableMap.of("Path", "OtherValue");


    private static final String XML_REQUEST_BODY = "<request>\n" +
            "\t<variable>Value 1</variable>\n" +
            "</request>";

    private static final String XML_RESPONSE_BODY = "<response>\n" +
            "\t<variable>Value 1</variable>\n" +
            "</response>";

    private static final String QUERY_DEFAULT_RESPONSE_BODY = "<response>\n" +
            "\t<variable>Default value 1</variable>\n" +
            "</response>";

    private static final String JSON_REQUEST_BODY = "{\n" +
            "\t\"request\": {\n" +
            "\t\t\"variable\": \"Value 1\"\n" +
            "\t}\n" +
            "}";

    @Test
    public void testMockedSequence() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getMockedRestMethod();

        restMethod.setResponseStrategy(RestResponseStrategy.RANDOM);

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .restProjectId(PROJECT_ID)
                .restApplicationId(APPLICATION_ID)
                .restResourceId(RESOURCE_ID)
                .restMethodId(METHOD_ID)
                .restMethod(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();


        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + REST + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + APPLICATION + SLASH + APPLICATION_ID + "/method/test");

        final ResponseEntity<?> responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(ACCEPT_HEADER).get(0));
    }


    @Test
    public void testMockedRandom() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getMockedRestMethod();

        restMethod.setResponseStrategy(RestResponseStrategy.SEQUENCE);

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .restProjectId(PROJECT_ID)
                .restApplicationId(APPLICATION_ID)
                .restResourceId(RESOURCE_ID)
                .restMethodId(METHOD_ID)
                .restMethod(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);

        final ResponseEntity<?> responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(ACCEPT_HEADER).get(0));
    }

    @Test
    public void testMockedQuery() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getQueryRestMethod();

        restMethod.setResponseStrategy(RestResponseStrategy.QUERY_MATCH);

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .restProjectId(PROJECT_ID)
                .restApplicationId(APPLICATION_ID)
                .restResourceId(RESOURCE_ID)
                .restMethodId(METHOD_ID)
                .restMethod(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);

        final ResponseEntity<?> responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(ACCEPT_HEADER).get(0));
    }

    @Test
    public void testMockedQueryDefaultMockResponse() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getQueryRestMethod();

        restMethod.setResponseStrategy(RestResponseStrategy.QUERY_MATCH);

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .restProjectId(PROJECT_ID)
                .restApplicationId(APPLICATION_ID)
                .restResourceId(RESOURCE_ID)
                .restMethodId(METHOD_ID)
                .restMethod(restMethod)
                .pathParameters(NO_MATCHING_PATH_PARAMETERS)
                .build();

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);

        final ResponseEntity<?> responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(QUERY_DEFAULT_RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(ACCEPT_HEADER).get(0));
    }


    @Test
    public void testEcho() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(XML_REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getMockedRestMethod();

        restMethod.setStatus(RestMethodStatus.ECHO);

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .restProjectId(PROJECT_ID)
                .restApplicationId(APPLICATION_ID)
                .restResourceId(RESOURCE_ID)
                .restMethodId(METHOD_ID)
                .restMethod(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + REST + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + APPLICATION + SLASH + APPLICATION_ID + "/method/test");

        final ResponseEntity<?> responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(XML_REQUEST_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertEquals(APPLICATION_JSON, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
    }

    @Test
    public void testMockedXpathMatch() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(XML_REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestXPathExpression xPathExpression = new RestXPathExpression();
        xPathExpression.setExpression("//request/variable[text()='Value 1']");

        final RestMethod restMethod = getMockedRestMethod();
        restMethod.getMockResponses().get(0).getXpathExpressions().add(xPathExpression);

        restMethod.setResponseStrategy(RestResponseStrategy.XPATH);

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .restProjectId(PROJECT_ID)
                .restApplicationId(APPLICATION_ID)
                .restResourceId(RESOURCE_ID)
                .restMethodId(METHOD_ID)
                .restMethod(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + REST + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + APPLICATION + SLASH + APPLICATION_ID + "/method/test");

        final ResponseEntity<?> responseEntity = restServiceController.postMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(ACCEPT_HEADER).get(0));
    }

    @Test
    public void testMockedJsonPathMatch() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(JSON_REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestJsonPathExpression restJsonPathExpression = new RestJsonPathExpression();
        restJsonPathExpression.setExpression("$.request[?(@.variable == 'Value 1')]");

        final RestMethod restMethod = getMockedRestMethod();
        restMethod.getMockResponses().get(0).getJsonPathExpressions().add(restJsonPathExpression);

        restMethod.setResponseStrategy(RestResponseStrategy.JSON_PATH);

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .restProjectId(PROJECT_ID)
                .restApplicationId(APPLICATION_ID)
                .restResourceId(RESOURCE_ID)
                .restMethodId(METHOD_ID)
                .restMethod(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + REST + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + APPLICATION + SLASH + APPLICATION_ID + "/method/test");

        final ResponseEntity<?> responseEntity = restServiceController.postMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(ACCEPT_HEADER).get(0));
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

    @SuppressWarnings("unchecked")
    private HttpServletRequest getMockedHttpServletRequest(final String body) {
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

    private HttpServletResponse getHttpServletResponse() {
        return Mockito.mock(HttpServletResponse.class);
    }

    private RestMethod getMockedRestMethod() {
        final HttpHeader contentTypeHeader = HttpHeader.builder()
                .name(CONTENT_TYPE_HEADER)
                .value(APPLICATION_XML)
                .build();

        final HttpHeader acceptHeader = HttpHeader.builder()
                .name(ACCEPT_HEADER)
                .value(APPLICATION_XML)
                .build();

        // Mock
        final RestMockResponse restMockResponse = RestMockResponseTestBuilder.builder()
                .body(XML_RESPONSE_BODY)
                .contentEncodings(new ArrayList<>())
                .httpHeaders(Arrays.asList(contentTypeHeader, acceptHeader))
                .httpStatusCode(200)
                .id("MockResponseId")
                .name("Mocked response")
                .status(RestMockResponseStatus.ENABLED)
                .usingExpressions(Boolean.FALSE)
                .build();

        return RestMethodTestBuilder.builder()
                .currentResponseSequenceIndex(0)
                .forwardedEndpoint(FORWARD_ENDPOINT)
                .httpMethod(HttpMethod.GET)
                .id(METHOD_ID)
                .uri("/method/test")
                .name("Method name")
                .networkDelay(0L)
                .responseStrategy(RestResponseStrategy.SEQUENCE)
                .simulateNetworkDelay(Boolean.FALSE)
                .status(RestMethodStatus.MOCKED)
                .mockResponses(Collections.singletonList(restMockResponse))
                .build();
    }

    private RestMethod getQueryRestMethod() {
        final HttpHeader contentTypeHeader = HttpHeader.builder()
                .name(CONTENT_TYPE_HEADER)
                .value(APPLICATION_XML)
                .build();

        final HttpHeader acceptHeader = HttpHeader.builder()
                .name(ACCEPT_HEADER)
                .value(APPLICATION_XML)
                .build();

        final RestParameterQuery parameterQuery = RestParameterQueryTestBuilder.builder()
                .parameter("Path")
                .query("Value")
                .matchAny(Boolean.FALSE)
                .matchCase(Boolean.FALSE)
                .matchRegex(Boolean.FALSE)
                .build();

        // Mock
        final RestMockResponse restMockResponse1 = RestMockResponseTestBuilder.builder()
                .body(XML_RESPONSE_BODY)
                .contentEncodings(new ArrayList<>())
                .httpHeaders(Arrays.asList(contentTypeHeader, acceptHeader))
                .httpStatusCode(200)
                .id("MockResponseId1")
                .name("Mocked response 1")
                .usingExpressions(Boolean.FALSE)
                .parameterQueries(Collections.singletonList(parameterQuery))
                .build();

        final RestMockResponse restMockResponse2 = RestMockResponseTestBuilder.builder()
                .body(QUERY_DEFAULT_RESPONSE_BODY)
                .contentEncodings(new ArrayList<>())
                .httpHeaders(Arrays.asList(contentTypeHeader, acceptHeader))
                .httpStatusCode(200)
                .id("MockResponseId2")
                .name("Mocked response 2")
                .status(RestMockResponseStatus.ENABLED)
                .usingExpressions(Boolean.FALSE)
                .parameterQueries(Collections.emptyList())
                .build();

        return RestMethodTestBuilder.builder()
                .currentResponseSequenceIndex(0)
                .forwardedEndpoint(FORWARD_ENDPOINT)
                .httpMethod(HttpMethod.GET)
                .id(METHOD_ID)
                .uri("/method/{variable}")
                .name("Method name")
                .networkDelay(0L)
                .responseStrategy(RestResponseStrategy.SEQUENCE)
                .simulateNetworkDelay(Boolean.FALSE)
                .status(RestMethodStatus.MOCKED)
                .mockResponses(Arrays.asList(restMockResponse1, restMockResponse2))
                .defaultResponseName("Mocked response 2")
                .defaultMockResponseId("MockResponseId2")
                .build();
    }

}
