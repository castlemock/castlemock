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

package com.castlemock.web.mock.soap.strategy;

import com.castlemock.model.mock.soap.domain.*;
import com.castlemock.web.mock.soap.factory.SoapMockStrategyResultFactory;
import com.castlemock.web.mock.soap.stategy.MockSoapStrategy;
import com.castlemock.web.mock.soap.stategy.SoapStrategyResult;
import com.castlemock.web.mock.soap.utility.SoapClient;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class MockSoapStrategyTest {

    @Test
    @DisplayName("Process - Mock")
    void testProcess() {
        final SoapClient soapClient = Mockito.mock(SoapClient.class);
        final SoapMockStrategyResultFactory mockStrategyResultFactory = Mockito.mock(SoapMockStrategyResultFactory.class);
        final MockSoapStrategy strategy = new MockSoapStrategy(soapClient, mockStrategyResultFactory);

        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapRequest request = SoapRequestTestBuilder.build();
        final SoapOperation operation = SoapOperationTestBuilder.build();
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        final SoapResponse mockResponse = SoapResponseTestBuilder.build();

        Mockito.when(mockStrategyResultFactory.getResponse(any(), any(), any(), any(), any()))
                .thenReturn(SoapStrategyResult.builder()
                        .response(mockResponse)
                        .build());

        final SoapStrategyResult result = strategy.process(request, projectId, portId, operation, httpServletRequest);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getResponse().isPresent());
        Assertions.assertTrue(result.getPostServiceRequests().isEmpty());

        final SoapResponse response = result.getResponse().orElse(null);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(mockResponse, response);

        Mockito.verify(mockStrategyResultFactory, Mockito.times(1))
                .getResponse(eq(request), eq(projectId), eq(portId), eq(operation), eq(httpServletRequest));
        Mockito.verifyNoMoreInteractions(mockStrategyResultFactory);
        Mockito.verifyNoInteractions(soapClient);
    }

    @Test
    @DisplayName("Process - No mock, forward request")
    void testProcessNoMockForward() {
        final SoapClient soapClient = Mockito.mock(SoapClient.class);
        final SoapMockStrategyResultFactory mockStrategyResultFactory = Mockito.mock(SoapMockStrategyResultFactory.class);
        final MockSoapStrategy strategy = new MockSoapStrategy(soapClient, mockStrategyResultFactory);

        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapRequest request = SoapRequestTestBuilder.build();
        final SoapOperation operation = SoapOperationTestBuilder.builder()
                .automaticForward(true)
                .forwardedEndpoint("localhost")
                .build();
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        final SoapResponse soapClientResponse = SoapResponseTestBuilder.builder()
                .httpStatusCode(200)
                .build();

        Mockito.when(soapClient.getResponse(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.of(soapClientResponse));
        Mockito.when(mockStrategyResultFactory.getResponse(any(), any(), any(), any(), any()))
                .thenReturn(SoapStrategyResult.builder()
                        .response(null)
                        .build());

        final SoapStrategyResult result = strategy.process(request, projectId, portId, operation, httpServletRequest);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getResponse().isPresent());
        Assertions.assertTrue(result.getPostServiceRequests().isEmpty());

        final SoapResponse response = result.getResponse().orElse(null);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(soapClientResponse, response);

        Mockito.verify(soapClient, Mockito.times(1)).getResponse(eq(request), eq(operation));
        Mockito.verify(mockStrategyResultFactory, Mockito.times(1))
                .getResponse(eq(request), eq(projectId), eq(portId), eq(operation), eq(httpServletRequest));
        Mockito.verifyNoMoreInteractions(soapClient, mockStrategyResultFactory);
    }

    @Test
    @DisplayName("Process - No mock and not automatic forwarding")
    void testProcessNoMockAndNotAutomaticForwarding() {
        final SoapClient soapClient = Mockito.mock(SoapClient.class);
        final SoapMockStrategyResultFactory mockStrategyResultFactory = Mockito.mock(SoapMockStrategyResultFactory.class);
        final MockSoapStrategy strategy = new MockSoapStrategy(soapClient, mockStrategyResultFactory);

        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapRequest request = SoapRequestTestBuilder.build();
        final SoapOperation operation = SoapOperationTestBuilder.builder()
                .automaticForward(false)
                .forwardedEndpoint("localhost")
                .build();
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        Mockito.when(mockStrategyResultFactory.getResponse(any(), any(), any(), any(), any()))
                .thenReturn(SoapStrategyResult.builder()
                        .response(null)
                        .build());

        final SoapStrategyResult result = strategy.process(request, projectId, portId, operation, httpServletRequest);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getResponse().isEmpty());
        Assertions.assertTrue(result.getPostServiceRequests().isEmpty());

        Mockito.verify(mockStrategyResultFactory, Mockito.times(1))
                .getResponse(eq(request), eq(projectId), eq(portId), eq(operation), eq(httpServletRequest));
        Mockito.verifyNoMoreInteractions(mockStrategyResultFactory);
        Mockito.verifyNoInteractions(soapClient);
    }

    @Test
    @DisplayName("Process - No mock and no forwarding address")
    void testProcessNoMockAndNoForwardingAddress() {
        final SoapClient soapClient = Mockito.mock(SoapClient.class);
        final SoapMockStrategyResultFactory mockStrategyResultFactory = Mockito.mock(SoapMockStrategyResultFactory.class);
        final MockSoapStrategy strategy = new MockSoapStrategy(soapClient, mockStrategyResultFactory);

        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapRequest request = SoapRequestTestBuilder.build();
        final SoapOperation operation = SoapOperationTestBuilder.builder()
                .automaticForward(true)
                .forwardedEndpoint(null)
                .build();
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        Mockito.when(mockStrategyResultFactory.getResponse(any(), any(), any(), any(), any()))
                .thenReturn(SoapStrategyResult.builder()
                        .response(null)
                        .build());

        final SoapStrategyResult result = strategy.process(request, projectId, portId, operation, httpServletRequest);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getResponse().isEmpty());
        Assertions.assertTrue(result.getPostServiceRequests().isEmpty());

        Mockito.verify(mockStrategyResultFactory, Mockito.times(1))
                .getResponse(eq(request), eq(projectId), eq(portId), eq(operation), eq(httpServletRequest));
        Mockito.verifyNoMoreInteractions(mockStrategyResultFactory);
        Mockito.verifyNoInteractions(soapClient);
    }

}
