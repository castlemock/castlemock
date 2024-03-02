/*
 * Copyright 2024 Karl Dahlgren
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

package com.castlemock.web.mock.soap.factory;

import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.model.mock.soap.domain.SoapMockResponseTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.model.mock.soap.domain.SoapRequestTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapResponseStrategy;
import com.castlemock.model.mock.soap.domain.SoapXPathExpression;
import com.castlemock.service.mock.soap.project.input.UpdateCurrentMockResponseSequenceIndexInput;
import com.castlemock.web.mock.soap.converter.SoapMockResponseConverter;
import com.castlemock.web.mock.soap.stategy.SoapStrategyResult;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class SoapMockStrategyResultFactoryTest {

    private static final String XPATH_REQUEST_BODY = """
            <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:web="http://www.castlemock.com/">
               <soap:Header/>
               <soap:Body>
                  <web:ServiceName>
                     <web:value>Input</web:value>
                  </web:ServiceName>
               </soap:Body>
            </soap:Envelope>""";

    @Test
    @DisplayName("Get Response - No mocked responses")
    void testGetResponse() {
        final SoapMockStrategyResultFactory factory = new SoapMockStrategyResultFactory();

        final SoapRequest request = SoapRequestTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapOperation operation = SoapOperationTestBuilder.builder()
                .mockResponses(List.of())
                .build();
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        final SoapStrategyResult response = factory.getResponse(request, projectId, portId, operation, httpServletRequest);

        assertNotNull(response);
        assertTrue(response.getResponse().isEmpty());
        assertTrue(response.getPostServiceRequests().isEmpty());
    }

    @Test
    @DisplayName("Get Response - No enabled mocked responses")
    void testGetResponseNoEnabledMockResponses() {
        final SoapMockStrategyResultFactory factory = new SoapMockStrategyResultFactory();

        final SoapRequest request = SoapRequestTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapOperation operation = SoapOperationTestBuilder.builder()
                .mockResponses(List.of(SoapMockResponseTestBuilder.builder()
                        .status(SoapMockResponseStatus.DISABLED)
                        .build()))
                .build();
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        final SoapStrategyResult response = factory.getResponse(request, projectId, portId, operation, httpServletRequest);

        assertNotNull(response);
        assertTrue(response.getResponse().isEmpty());
        assertTrue(response.getPostServiceRequests().isEmpty());
    }

    @Test
    @DisplayName("Get Response - Random")
    void testGetResponseRandom() {
        final SoapMockStrategyResultFactory factory = new SoapMockStrategyResultFactory();

        final SoapRequest request = SoapRequestTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.builder()
                .usingExpressions(false)
                .build();
        final SoapOperation operation = SoapOperationTestBuilder.builder()
                .responseStrategy(SoapResponseStrategy.RANDOM)
                .mockResponses(List.of(mockResponse))
                .build();
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        final SoapStrategyResult response = factory.getResponse(request, projectId, portId, operation, httpServletRequest);

        assertNotNull(response);
        assertNotNull(response.getResponse());
        assertTrue(response.getPostServiceRequests().isEmpty());
        assertTrue(response.getResponse().isPresent());
        assertEquals(SoapMockResponseConverter.toSoapResponse(mockResponse, mockResponse.getBody()),
                response.getResponse().orElse(null));
    }

    @Test
    @DisplayName("Get Response - Sequence")
    void testGetResponseSequence() {
        final SoapMockStrategyResultFactory factory = new SoapMockStrategyResultFactory();

        final SoapRequest request = SoapRequestTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapMockResponse mockResponse1 = SoapMockResponseTestBuilder.builder()
                .id("1")
                .usingExpressions(false)
                .build();
        final SoapMockResponse mockResponse2 = SoapMockResponseTestBuilder.builder()
                .id("2")
                .usingExpressions(false)
                .build();
        final SoapOperation operation = SoapOperationTestBuilder.builder()
                .currentResponseSequenceIndex(0)
                .responseStrategy(SoapResponseStrategy.SEQUENCE)
                .mockResponses(List.of(mockResponse1,mockResponse2))
                .build();

        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        final SoapStrategyResult response1 = factory.getResponse(request, projectId, portId, operation, httpServletRequest);

        assertNotNull(response1);
        assertNotNull(response1.getResponse());
        assertTrue(response1.getResponse().isPresent());
        assertEquals(1, response1.getPostServiceRequests().size());
        assertEquals(UpdateCurrentMockResponseSequenceIndexInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operation.getId())
                .currentResponseSequenceIndex(1)
                .build(), response1.getPostServiceRequests().getFirst());
        assertEquals(SoapMockResponseConverter.toSoapResponse(mockResponse1, mockResponse1.getBody()),
                response1.getResponse().orElse(null));

        final SoapStrategyResult response2 = factory.getResponse(request, projectId, portId, operation.toBuilder()
                .currentResponseSequenceIndex(1)
                .build(), httpServletRequest);

        assertNotNull(response2);
        assertNotNull(response2.getResponse());
        assertTrue(response2.getResponse().isPresent());
        assertEquals(1, response1.getPostServiceRequests().size());
        assertEquals(UpdateCurrentMockResponseSequenceIndexInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operation.getId())
                .currentResponseSequenceIndex(2)
                .build(), response2.getPostServiceRequests().getFirst());
        assertEquals(SoapMockResponseConverter.toSoapResponse(mockResponse2, mockResponse2.getBody()),
                response2.getResponse().orElse(null));
    }

    @Test
    @DisplayName("Get Response - Sequence - Index too high")
    void testGetResponseSequenceIndexTooHigh() {
        final SoapMockStrategyResultFactory factory = new SoapMockStrategyResultFactory();

        final SoapRequest request = SoapRequestTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.builder()
                .usingExpressions(false)
                .build();
        final SoapOperation operation = SoapOperationTestBuilder.builder()
                .currentResponseSequenceIndex(1)
                .responseStrategy(SoapResponseStrategy.SEQUENCE)
                .mockResponses(List.of(mockResponse))
                .build();

        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        final SoapStrategyResult response1 = factory.getResponse(request, projectId, portId, operation, httpServletRequest);

        assertNotNull(response1);
        assertNotNull(response1.getResponse());
        assertTrue(response1.getResponse().isPresent());
        assertEquals(1, response1.getPostServiceRequests().size());
        assertEquals(UpdateCurrentMockResponseSequenceIndexInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operation.getId())
                .currentResponseSequenceIndex(1)
                .build(), response1.getPostServiceRequests().getFirst());
        assertEquals(SoapMockResponseConverter.toSoapResponse(mockResponse, mockResponse.getBody()),
                response1.getResponse().orElse(null));
    }

    @Test
    @DisplayName("Get Response - XPath no match")
    void testGetResponseXPathNoMatch() {
        final SoapMockStrategyResultFactory factory = new SoapMockStrategyResultFactory();

        final SoapRequest request = SoapRequestTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.builder()
                .usingExpressions(false)
                .xpathExpressions(List.of())
                .build();
        final SoapOperation operation = SoapOperationTestBuilder.builder()
                .responseStrategy(SoapResponseStrategy.XPATH_INPUT)
                .mockResponses(List.of(mockResponse))
                .build();
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        final SoapStrategyResult response = factory.getResponse(request, projectId, portId, operation, httpServletRequest);

        assertNotNull(response);
        assertNotNull(response.getResponse());
        assertTrue(response.getPostServiceRequests().isEmpty());
        assertTrue(response.getResponse().isEmpty());
    }

    @Test
    @DisplayName("Get Response - XPath no match - Default response")
    void testGetResponseXPathNoMatchDefaultResponse() {
        final SoapMockStrategyResultFactory factory = new SoapMockStrategyResultFactory();

        final SoapRequest request = SoapRequestTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.builder()
                .id("1")
                .usingExpressions(false)
                .xpathExpressions(List.of())
                .build();
        final SoapOperation operation = SoapOperationTestBuilder.builder()
                .responseStrategy(SoapResponseStrategy.XPATH_INPUT)
                .mockResponses(List.of(mockResponse))
                .defaultMockResponseId("1")
                .build();
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        final SoapStrategyResult response = factory.getResponse(request, projectId, portId, operation, httpServletRequest);

        assertNotNull(response);
        assertNotNull(response.getResponse());
        assertTrue(response.getPostServiceRequests().isEmpty());
        assertTrue(response.getResponse().isPresent());
        assertEquals(SoapMockResponseConverter.toSoapResponse(mockResponse, mockResponse.getBody()),
                response.getResponse().orElse(null));
    }

    @Test
    @DisplayName("Get Response - XPath match")
    void testGetResponseXPathMatch() {
        final SoapMockStrategyResultFactory factory = new SoapMockStrategyResultFactory();

        final SoapRequest request = SoapRequestTestBuilder.builder()
                .body(XPATH_REQUEST_BODY)
                .build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.builder()
                .usingExpressions(false)
                .xpathExpressions(List.of(SoapXPathExpression.builder()
                        .expression("//ServiceName/value[text()='Input']")
                        .build()))
                .build();
        final SoapOperation operation = SoapOperationTestBuilder.builder()
                .responseStrategy(SoapResponseStrategy.XPATH_INPUT)
                .mockResponses(List.of(mockResponse))
                .build();
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        final SoapStrategyResult response = factory.getResponse(request, projectId, portId, operation, httpServletRequest);

        assertNotNull(response);
        assertNotNull(response.getResponse());
        assertTrue(response.getPostServiceRequests().isEmpty());
        assertTrue(response.getResponse().isPresent());
        assertEquals(SoapMockResponseConverter.toSoapResponse(mockResponse, mockResponse.getBody()),
                response.getResponse().orElse(null));
    }

    @Test
    @DisplayName("Get Response - Using expression")
    void testGetResponseUsingExpression() {
        final SoapMockStrategyResultFactory factory = new SoapMockStrategyResultFactory();

        final SoapRequest request = SoapRequestTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.builder()
                .usingExpressions(true)
                .build();
        final SoapOperation operation = SoapOperationTestBuilder.builder()
                .responseStrategy(SoapResponseStrategy.RANDOM)
                .mockResponses(List.of(mockResponse))
                .build();
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080"));

        final SoapStrategyResult response = factory.getResponse(request, projectId, portId, operation, httpServletRequest);

        assertNotNull(response);
        assertNotNull(response.getResponse());
        assertTrue(response.getPostServiceRequests().isEmpty());
        assertTrue(response.getResponse().isPresent());
        assertEquals(SoapMockResponseConverter.toSoapResponse(mockResponse, mockResponse.getBody()),
                response.getResponse().orElse(null));
    }

}
