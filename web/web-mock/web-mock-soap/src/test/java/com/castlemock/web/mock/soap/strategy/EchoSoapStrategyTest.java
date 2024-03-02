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

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.model.mock.soap.domain.SoapRequestTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapResponse;
import com.castlemock.web.mock.soap.stategy.EchoSoapStrategy;
import com.castlemock.web.mock.soap.stategy.SoapStrategyResult;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

class EchoSoapStrategyTest {

    @Test
    @DisplayName("Process")
    void testProcess() {
        final EchoSoapStrategy strategy = new EchoSoapStrategy();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapRequest request = SoapRequestTestBuilder.build();
        final SoapOperation operation = SoapOperationTestBuilder.build();
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        final SoapStrategyResult result = strategy.process(request, projectId, portId, operation, httpServletRequest);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getResponse().isPresent());
        Assertions.assertTrue(result.getPostServiceRequests().isEmpty());

        final SoapResponse response = result.getResponse().orElse(null);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(request.getBody(), response.getBody());
        Assertions.assertEquals(200, response.getHttpStatusCode());
        Assertions.assertEquals(request.getContentType(), response.getContentType().orElse(null));
        Assertions.assertTrue(response.getContentEncodings().isEmpty());
        Assertions.assertEquals(List.of(
                HttpHeader.builder()
                        .name("Content-Type")
                        .value(request.getContentType())
                        .build()),
                response.getHttpHeaders());
    }
}
