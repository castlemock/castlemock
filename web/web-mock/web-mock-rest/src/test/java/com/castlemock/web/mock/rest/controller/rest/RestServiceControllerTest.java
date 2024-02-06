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

package com.castlemock.web.mock.rest.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.rest.domain.RestJsonPathExpression;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestMethodTestBuilder;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;
import com.castlemock.model.mock.rest.domain.RestMockResponseTestBuilder;
import com.castlemock.model.mock.rest.domain.RestParameterQuery;
import com.castlemock.model.mock.rest.domain.RestParameterQueryTestBuilder;
import com.castlemock.model.mock.rest.domain.RestRequest;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;
import com.castlemock.model.mock.rest.domain.RestResponseTestBuilder;
import com.castlemock.model.mock.rest.domain.RestXPathExpression;
import com.castlemock.service.mock.rest.project.input.IdentifyRestMethodInput;
import com.castlemock.service.mock.rest.project.output.IdentifyRestMethodOutput;
import com.castlemock.web.core.controller.AbstractController;
import com.castlemock.web.mock.rest.utility.RestClient;
import com.castlemock.web.mock.rest.controller.mock.RestServiceController;
import com.castlemock.web.mock.rest.model.RestException;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    @Mock
    private RestClient restClient;

    private static final String PROJECT_ID = "ProjectId";
    private static final String APPLICATION_ID = "ApplicationId";
    private static final String RESOURCE_ID = "ResourceId";
    private static final String METHOD_ID = "MethodId";
    private static final String FORWARD_ENDPOINT = "http://localhost:8080";
    private static final String APPLICATION_XML = "application/xml";
    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE_HEADER = "Content-type";
    private static final String ACCEPT_HEADER = "Accept";
    private static final Map<String, Set<String>> PATH_PARAMETERS = Map.of("Path", Set.of("Value"));
    private static final Map<String, Set<String>> NO_MATCHING_PATH_PARAMETERS = Map.of("Path", Set.of("OtherValue"));

    private static final String XML_REQUEST_BODY = """
            <request>
            \t<variable>Value 1</variable>
            </request>""";

    private static final String XML_RESPONSE_BODY = """
            <response>
            \t<variable>Value 1</variable>
            </response>""";

    private static final String QUERY_DEFAULT_RESPONSE_BODY = """
            <response>
            \t<variable>Default value 1</variable>
            </response>""";

    private static final String JSON_REQUEST_BODY = """
            {
            \t"request": {
            \t\t"variable": "Value 1"
            \t}
            }""";

    @Test
    @DisplayName("Get - Mock sequence")
    public void testGetMockedSequence() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getMockedRestMethod()
                .responseStrategy(RestResponseStrategy.RANDOM)
                .build();

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .projectId(PROJECT_ID)
                .applicationId(APPLICATION_ID)
                .resourceId(RESOURCE_ID)
                .methodId(METHOD_ID)
                .method(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();


        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + REST + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + APPLICATION + SLASH + APPLICATION_ID + "/method/test");

        final ResponseEntity<?> responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assertions.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).getFirst());
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).getFirst());
    }


    @Test
    @DisplayName("Get - Mock random")
    public void testMockedRandom() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getMockedRestMethod()
                .responseStrategy(RestResponseStrategy.SEQUENCE)
                .build();

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .projectId(PROJECT_ID)
                .applicationId(APPLICATION_ID)
                .resourceId(RESOURCE_ID)
                .methodId(METHOD_ID)
                .method(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);

        final ResponseEntity<?> responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assertions.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).getFirst());
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).getFirst());
    }

    @Test
    @DisplayName("Get - Mock query")
    public void testMockedQuery() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getQueryRestMethod()
                .responseStrategy(RestResponseStrategy.QUERY_MATCH)
                .build();

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .projectId(PROJECT_ID)
                .applicationId(APPLICATION_ID)
                .resourceId(RESOURCE_ID)
                .methodId(METHOD_ID)
                .method(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);

        final ResponseEntity<?> responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assertions.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).getFirst());
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).getFirst());
    }

    @Test
    @DisplayName("Get - Mock query - Default mock response")
    public void testMockedQueryDefaultMockResponse() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getQueryRestMethod()
                .responseStrategy(RestResponseStrategy.QUERY_MATCH)
                .build();

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .projectId(PROJECT_ID)
                .applicationId(APPLICATION_ID)
                .resourceId(RESOURCE_ID)
                .methodId(METHOD_ID)
                .method(restMethod)
                .pathParameters(NO_MATCHING_PATH_PARAMETERS)
                .build();

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);

        final ResponseEntity<?> responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assertions.assertEquals(QUERY_DEFAULT_RESPONSE_BODY, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).getFirst());
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).getFirst());
    }

    @Test
    @DisplayName("Get - Forwarding strategy")
    public void testForwardingStrategy() {
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getForwardingRestMethod();
        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .projectId(PROJECT_ID)
                .applicationId(APPLICATION_ID)
                .resourceId(RESOURCE_ID)
                .methodId(METHOD_ID)
                .method(restMethod)
                .build();
        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);
        when(restClient.getResponse(any(), any())).thenReturn(Optional.of(RestResponseTestBuilder.builder().build()));

        restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);

        verify(restClient, times(1)).getResponse(any(RestRequest.class), any(RestMethod.class));
    }

    @Test
    @DisplayName("Get - Mock query - No match, no default response and forwarding URL without automatic forward")
    public void testMockedQueryNoMatchNoDefaultResponseAndForwardingUrlWithoutAutomaticForward() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getQueryNotMatchingNotDefaultResponseRestMethod()
                .automaticForward(false)
                .responseStrategy(RestResponseStrategy.QUERY_MATCH)
                .build();

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .projectId(PROJECT_ID)
                .applicationId(APPLICATION_ID)
                .resourceId(RESOURCE_ID)
                .methodId(METHOD_ID)
                .method(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();
        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);

        Assertions.assertThrows(RestException.class, () ->
                restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse));

        verify(restClient, times(0)).getResponse(any(RestRequest.class), any(RestMethod.class));
    }

    @Test
    @DisplayName("Get - Mock query - No match, no default response and forwarding URL with automatic forward")
    public void testMockedQueryNoMatchNoDefaultResponseAndForwardingUrlWithAutomaticForward() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getQueryNotMatchingNotDefaultResponseRestMethod()
                .responseStrategy(RestResponseStrategy.QUERY_MATCH)
                .build();

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .projectId(PROJECT_ID)
                .applicationId(APPLICATION_ID)
                .resourceId(RESOURCE_ID)
                .methodId(METHOD_ID)
                .method(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();
        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);
        when(restClient.getResponse(any(), any())).thenReturn(Optional.of(RestResponseTestBuilder.builder().build()));

        restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);

        verify(restClient, times(1)).getResponse(any(RestRequest.class), any(RestMethod.class));
    }

    @Test
    @DisplayName("Get - Mock query - No match, no default response and no forwarding url")
    public void testMockedQueryNoMatchAndDefaultResponseAndForwardingUrl() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest("");
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getQueryNotMatchingAndDefaultResponseRestMethod()
                .responseStrategy(RestResponseStrategy.QUERY_MATCH)
                .build();

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .projectId(PROJECT_ID)
                .applicationId(APPLICATION_ID)
                .resourceId(RESOURCE_ID)
                .methodId(METHOD_ID)
                .method(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();
        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);

        restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);

        verify(restClient, times(0)).getResponse(any(RestRequest.class), any(RestMethod.class));
    }

    @Test
    @DisplayName("Get - Echo")
    public void testEcho() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(XML_REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestMethod restMethod = getMockedRestMethod()
                .status(RestMethodStatus.ECHO)
                .build();

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .projectId(PROJECT_ID)
                .applicationId(APPLICATION_ID)
                .resourceId(RESOURCE_ID)
                .methodId(METHOD_ID)
                .method(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + REST + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + APPLICATION + SLASH + APPLICATION_ID + "/method/test");

        final ResponseEntity<?> responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assertions.assertEquals(XML_REQUEST_BODY, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assertions.assertEquals(APPLICATION_JSON, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).getFirst());
    }

    @Test
    @DisplayName("Get - Mock XPath - Match")
    public void testMockedXpathMatch() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(XML_REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestXPathExpression xPathExpression = RestXPathExpression.builder()
                .expression("//request/variable[text()='Value 1']")
                .build();

        final HttpHeader contentTypeHeader = HttpHeader.builder()
                .name(CONTENT_TYPE_HEADER)
                .value(APPLICATION_XML)
                .build();

        final HttpHeader acceptHeader = HttpHeader.builder()
                .name(ACCEPT_HEADER)
                .value(APPLICATION_XML)
                .build();

        final RestMethod restMethod = getMockedRestMethod()
                .responseStrategy(RestResponseStrategy.XPATH)
                .mockResponses(List.of(RestMockResponseTestBuilder
                        .builder()
                        .body(XML_RESPONSE_BODY)
                        .status(RestMockResponseStatus.ENABLED)
                        .usingExpressions(Boolean.FALSE)
                        .httpHeaders(Arrays.asList(contentTypeHeader, acceptHeader))
                        .xpathExpressions(List.of(xPathExpression))
                        .build()))
                .build();

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .projectId(PROJECT_ID)
                .applicationId(APPLICATION_ID)
                .resourceId(RESOURCE_ID)
                .methodId(METHOD_ID)
                .method(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + REST + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + APPLICATION + SLASH + APPLICATION_ID + "/method/test");

        final ResponseEntity<?> responseEntity = restServiceController.postMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assertions.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).getFirst());
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).getFirst());
    }

    @Test
    @DisplayName("Get - Mock JSON path - Match")
    public void testMockedJsonPathMatch() {
        // Input
        final HttpServletRequest httpServletRequest = getMockedHttpServletRequest(JSON_REQUEST_BODY);
        final HttpServletResponse httpServletResponse = getHttpServletResponse();

        final RestJsonPathExpression restJsonPathExpression = RestJsonPathExpression.builder()
                .expression("$.request[?(@.variable == 'Value 1')]")
                .build();

        final HttpHeader contentTypeHeader = HttpHeader.builder()
                .name(CONTENT_TYPE_HEADER)
                .value(APPLICATION_XML)
                .build();

        final HttpHeader acceptHeader = HttpHeader.builder()
                .name(ACCEPT_HEADER)
                .value(APPLICATION_XML)
                .build();

        final RestMethod restMethod = getMockedRestMethod()
                .responseStrategy(RestResponseStrategy.JSON_PATH)
                .mockResponses(List.of(RestMockResponseTestBuilder
                        .builder()
                        .body(XML_RESPONSE_BODY)
                        .status(RestMockResponseStatus.ENABLED)
                        .usingExpressions(Boolean.FALSE)
                        .httpHeaders(Arrays.asList(contentTypeHeader, acceptHeader))
                        .jsonPathExpressions(List.of(restJsonPathExpression))
                        .build()))
                .build();

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .projectId(PROJECT_ID)
                .applicationId(APPLICATION_ID)
                .resourceId(RESOURCE_ID)
                .methodId(METHOD_ID)
                .method(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + REST + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + APPLICATION + SLASH + APPLICATION_ID + "/method/test");

        final ResponseEntity<?> responseEntity = restServiceController.postMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assertions.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assertions.assertTrue(responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assertions.assertNotNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER));
        Assertions.assertNotNull(responseEntity.getHeaders().get(ACCEPT_HEADER));
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(CONTENT_TYPE_HEADER)).getFirst());
        Assertions.assertEquals(APPLICATION_XML, Objects.requireNonNull(responseEntity.getHeaders().get(ACCEPT_HEADER)).getFirst());
    }

    @Override
    protected AbstractController getController() {
        return restServiceController;
    }


    private static class HttpServletRequestTest extends HttpServletRequestWrapper {

        private final byte[] bytes;

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

    @SuppressWarnings("unchecked")
    private HttpServletRequest getMockedHttpServletRequest(final String body) {
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        final HttpServletRequest httpServletRequestWrapper = new HttpServletRequestTest(httpServletRequest, body);
        when(httpServletRequest.getRequestURI()).thenReturn(CONTEXT + SLASH + MOCK + SLASH + REST + SLASH + PROJECT +
                SLASH + PROJECT_ID + SLASH + APPLICATION + SLASH + APPLICATION_ID + "/method/test");

        when(httpServletRequest.getContentType()).thenReturn(APPLICATION_JSON);

        Enumeration<String> parameterName = Mockito.mock(Enumeration.class);
        Enumeration<String> headerNames = Collections.enumeration(Arrays.asList(CONTENT_TYPE_HEADER, ACCEPT_HEADER));
        when(httpServletRequest.getParameterNames()).thenReturn(parameterName);
        when(httpServletRequest.getHeaderNames()).thenReturn(headerNames);
        when(httpServletRequest.getHeader(CONTENT_TYPE_HEADER)).thenReturn(APPLICATION_JSON);
        when(httpServletRequest.getHeader(ACCEPT_HEADER)).thenReturn(APPLICATION_JSON);

        return httpServletRequestWrapper;

    }

    private HttpServletResponse getHttpServletResponse() {
        return Mockito.mock(HttpServletResponse.class);
    }

    private RestMethod.Builder getMockedRestMethod() {
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
                .mockResponses(Collections.singletonList(restMockResponse));
    }

    private RestMethod.Builder getQueryRestMethod() {
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
                .defaultMockResponseId("MockResponseId2");
    }

    private RestMethod.Builder getQueryNotMatchingAndDefaultResponseRestMethod() {
        final HttpHeader contentTypeHeader = HttpHeader.builder()
                .name(CONTENT_TYPE_HEADER)
                .value(APPLICATION_XML)
                .build();

        final HttpHeader acceptHeader = HttpHeader.builder()
                .name(ACCEPT_HEADER)
                .value(APPLICATION_XML)
                .build();

        // Mock
        final String mockResponseId = "MockResponseId1";
        final String mockResponseName = "Mocked response 1";
        final RestMockResponse restMockResponse1 = RestMockResponseTestBuilder.builder()
                .body(XML_RESPONSE_BODY)
                .contentEncodings(new ArrayList<>())
                .httpHeaders(Arrays.asList(contentTypeHeader, acceptHeader))
                .httpStatusCode(200)
                .id(mockResponseId)
                .name(mockResponseName)
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
                .mockResponses(List.of(restMockResponse1))
                .defaultMockResponseId(mockResponseId)
                .defaultResponseName(mockResponseName);
    }

    private RestMethod.Builder getQueryNotMatchingNotDefaultResponseRestMethod() {
        final HttpHeader contentTypeHeader = HttpHeader.builder()
                .name(CONTENT_TYPE_HEADER)
                .value(APPLICATION_XML)
                .build();

        final HttpHeader acceptHeader = HttpHeader.builder()
                .name(ACCEPT_HEADER)
                .value(APPLICATION_XML)
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
                .parameterQueries(Collections.emptyList())
                .build();

        return RestMethodTestBuilder.builder()
                .currentResponseSequenceIndex(0)
                .forwardedEndpoint(FORWARD_ENDPOINT)
                .automaticForward(true)
                .httpMethod(HttpMethod.GET)
                .id(METHOD_ID)
                .uri("/method/{variable}")
                .name("Method name")
                .networkDelay(0L)
                .responseStrategy(RestResponseStrategy.SEQUENCE)
                .simulateNetworkDelay(Boolean.FALSE)
                .status(RestMethodStatus.MOCKED)
                .mockResponses(List.of(restMockResponse1));
    }

    private RestMethod getForwardingRestMethod() {
        return RestMethodTestBuilder.builder()
                .forwardedEndpoint(FORWARD_ENDPOINT)
                .httpMethod(HttpMethod.GET)
                .id(METHOD_ID)
                .uri("/method/{variable}")
                .name("Method name")
                .networkDelay(0L)
                .simulateNetworkDelay(Boolean.FALSE)
                .status(RestMethodStatus.FORWARDED)
                .build();
    }

}
