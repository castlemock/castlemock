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

import com.castlemock.model.mock.soap.domain.SoapResponse;
import com.castlemock.repository.core.file.http.converter.HttpHeaderConverter;
import com.castlemock.repository.soap.file.event.model.SoapResponseFile;

import java.util.List;
import java.util.stream.Collectors;

public final class SoapResponseConverter {

    private SoapResponseConverter() {

    }

    public static SoapResponseFile toSoapResponseFile(final SoapResponse soapResponse) {
        return SoapResponseFile.builder()
                .body(soapResponse.getBody())
                .httpStatusCode(soapResponse.getHttpStatusCode())
                .contentEncodings(List.copyOf(soapResponse.getContentEncodings()))
                .httpHeaders(soapResponse.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderConverter::toHttpHeaderFile)
                        .collect(Collectors.toList()))
                .mockResponseName(soapResponse.getMockResponseName()
                        .orElse(null))
                .contentType(soapResponse.getContentType()
                        .orElse(null))
                .build();
    }
}
