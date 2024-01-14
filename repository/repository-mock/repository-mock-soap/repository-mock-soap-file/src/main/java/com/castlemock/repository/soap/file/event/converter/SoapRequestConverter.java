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

package com.castlemock.repository.soap.file.event.converter;

import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.repository.core.file.http.converter.HttpHeaderConverter;
import com.castlemock.repository.soap.file.event.model.SoapRequestFile;

import java.util.stream.Collectors;

public final class SoapRequestConverter {

    private SoapRequestConverter() {

    }

    public static SoapRequestFile toSoapRequestFile(final SoapRequest soapRequest) {
        return SoapRequestFile.builder()
                .body(soapRequest.getBody())
                .envelope(soapRequest.getEnvelope())
                .contentType(soapRequest.getContentType())
                .uri(soapRequest.getUri())
                .httpMethod(soapRequest.getHttpMethod())
                .operationName(soapRequest.getOperationName())
                .soapVersion(soapRequest.getSoapVersion())
                .httpHeaders(soapRequest.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderConverter::toHttpHeaderFile)
                        .collect(Collectors.toList()))
                .operationIdentifier(SoapOperationIdentifierConverter
                        .toSoapOperationIdentifier(soapRequest.getOperationIdentifier()))
                .build();
    }

}
