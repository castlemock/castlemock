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

package com.castlemock.model.mock.soap.domain;

import com.castlemock.model.core.http.HttpMethod;

import java.util.List;

public final class SoapOperationTestBuilder {

    private SoapOperationTestBuilder() {

    }

    public static SoapOperation.Builder builder(){
        return SoapOperation.builder()
                .currentResponseSequenceIndex(0)
                .defaultBody("Default body")
                .defaultMockResponseId(null)
                .defaultResponseName(null)
                .forwardedEndpoint("Forwarded event")
                .httpMethod(HttpMethod.POST)
                .id("SOAP OPERATION")
                .identifier("soapoperation")
                .identifyStrategy(SoapOperationIdentifyStrategy.ELEMENT_NAMESPACE)
                .invokeAddress("Invoke address")
                .mockOnFailure(Boolean.FALSE)
                .mockResponses(List.of())
                .name("Soap operation name")
                .networkDelay(1000L)
                .operationIdentifier( SoapOperationIdentifierTestBuilder.builder().build())
                .originalEndpoint("Original endpoint")
                .portId("port id")
                .responseStrategy(SoapResponseStrategy.SEQUENCE)
                .simulateNetworkDelay(Boolean.FALSE)
                .soapVersion(SoapVersion.SOAP11)
                .status(SoapOperationStatus.MOCKED)
                .automaticForward(Boolean.FALSE);
    }

    public static SoapOperation build() {
        return builder().build();

    }

}
