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

import java.util.Set;

public final class SoapRequestTestBuilder {

    public static SoapRequest.Builder builder(){
        final String body = """
                <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:web="http://www.castlemock.com/">
                   <soap:Header/>
                   <soap:Body>
                      <web:ServiceName>
                         <web:value>Input</web:value>
                      </web:ServiceName>
                   </soap:Body>
                </soap:Envelope>""";

        return SoapRequest.builder()
                .body(body)
                .envelope(body)
                .contentType("application/json")
                .httpHeaders(Set.of())
                .httpMethod(HttpMethod.POST)
                .operationIdentifier(SoapOperationIdentifier.builder()
                        .name("")
                        .namespace("")
                        .build())
                .operationName("ServiceName")
                .soapVersion(SoapVersion.SOAP11)
                .uri("uri");
    }


    public static SoapRequest build() {
        return builder().build();
    }
}
