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
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.rest.model.project.domain.*;
import com.castlemock.core.mock.rest.service.project.input.IdentifyRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.IdentifyRestMethodOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.rest.web.AbstractControllerTest;
import com.castlemock.web.mock.rest.web.mock.controller.RestServiceController;
import com.google.common.collect.ImmutableList;
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
    public void testMockedSequence(){
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

        final ResponseEntity responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
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

        final IdentifyRestMethodOutput identifyRestMethodOutput = IdentifyRestMethodOutput.builder()
                .restProjectId(PROJECT_ID)
                .restApplicationId(APPLICATION_ID)
                .restResourceId(RESOURCE_ID)
                .restMethodId(METHOD_ID)
                .restMethod(restMethod)
                .pathParameters(PATH_PARAMETERS)
                .build();

        when(serviceProcessor.process(any(IdentifyRestMethodInput.class))).thenReturn(identifyRestMethodOutput);

        final ResponseEntity responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(ACCEPT_HEADER).get(0));
    }

    @Test
    public void testMockedQuery(){
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

        final ResponseEntity responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(ACCEPT_HEADER).get(0));
    }

    @Test
    public void testMockedQueryDefaultMockResponse(){
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

        final ResponseEntity responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(QUERY_DEFAULT_RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(ACCEPT_HEADER).get(0));
    }


    @Test
    public void testEcho(){
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

        final ResponseEntity responseEntity = restServiceController.getMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(XML_REQUEST_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertEquals(APPLICATION_JSON, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
    }

    @Test
    public void testMockedXpathMatch(){
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

        final ResponseEntity responseEntity = restServiceController.postMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(CONTENT_TYPE_HEADER).get(0));
        Assert.assertEquals(APPLICATION_XML, responseEntity.getHeaders().get(ACCEPT_HEADER).get(0));
    }

    @Test
    public void testMockedJsonPathMatch(){
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

        final ResponseEntity responseEntity = restServiceController.postMethod(PROJECT_ID, APPLICATION_ID, httpServletRequest, httpServletResponse);
        Assert.assertEquals(XML_RESPONSE_BODY, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(CONTENT_TYPE_HEADER));
        Assert.assertEquals(true, responseEntity.getHeaders().containsKey(ACCEPT_HEADER));
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
        restMockResponse.setBody(XML_RESPONSE_BODY);
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

    private RestMethod getQueryRestMethod(){

        final HttpHeader contentTypeHeader = new HttpHeader();
        contentTypeHeader.setName(CONTENT_TYPE_HEADER);
        contentTypeHeader.setValue(APPLICATION_XML);

        final HttpHeader acceptHeader = new HttpHeader();
        acceptHeader.setName(ACCEPT_HEADER);
        acceptHeader.setValue(APPLICATION_XML);

        final RestParameterQuery parameterQuery = new RestParameterQuery();
        parameterQuery.setParameter("Path");
        parameterQuery.setQuery("Value");
        parameterQuery.setMatchAny(false);
        parameterQuery.setMatchCase(false);
        parameterQuery.setMatchRegex(false);

        // Mock
        final RestMockResponse restMockResponse1 = new RestMockResponse();
        restMockResponse1.setBody(XML_RESPONSE_BODY);
        restMockResponse1.setContentEncodings(new ArrayList<>());
        restMockResponse1.setHttpHeaders(Arrays.asList(contentTypeHeader, acceptHeader));
        restMockResponse1.setHttpStatusCode(200);
        restMockResponse1.setId("MockResponseId1");
        restMockResponse1.setName("Mocked response 1");
        restMockResponse1.setStatus(RestMockResponseStatus.ENABLED);
        restMockResponse1.setUsingExpressions(false);
        restMockResponse1.setParameterQueries(ImmutableList.of(parameterQuery));

        final RestMockResponse restMockResponse2 = new RestMockResponse();
        restMockResponse2.setBody(QUERY_DEFAULT_RESPONSE_BODY);
        restMockResponse2.setContentEncodings(new ArrayList<>());
        restMockResponse2.setHttpHeaders(Arrays.asList(contentTypeHeader, acceptHeader));
        restMockResponse2.setHttpStatusCode(200);
        restMockResponse2.setId("MockResponseId2");
        restMockResponse2.setName("Mocked response 2");
        restMockResponse2.setStatus(RestMockResponseStatus.ENABLED);
        restMockResponse2.setUsingExpressions(false);
        restMockResponse2.setParameterQueries(ImmutableList.of());


        final RestMethod restMethod = new RestMethod();
        restMethod.setCurrentResponseSequenceIndex(0);
        restMethod.setForwardedEndpoint(FORWARD_ENDPOINT);
        restMethod.setHttpMethod(HttpMethod.GET);
        restMethod.setId(METHOD_ID);
        restMethod.setInvokeAddress("http://localhost:8080" + CONTEXT + SLASH + MOCK + SLASH + REST + SLASH +
                PROJECT + SLASH + PROJECT_ID + SLASH + APPLICATION + SLASH + APPLICATION_ID + "/method/{variable}");
        restMethod.setName("Method name");
        restMethod.setNetworkDelay(0);
        restMethod.setResponseStrategy(RestResponseStrategy.SEQUENCE);
        restMethod.setSimulateNetworkDelay(false);
        restMethod.setStatus(RestMethodStatus.MOCKED);
        restMethod.setMockResponses(Arrays.asList(restMockResponse1, restMockResponse2));
        restMethod.setDefaultResponseName("Mocked response 2");
        restMethod.setDefaultMockResponseId("MockResponseId2");

        return restMethod;
    }

}
