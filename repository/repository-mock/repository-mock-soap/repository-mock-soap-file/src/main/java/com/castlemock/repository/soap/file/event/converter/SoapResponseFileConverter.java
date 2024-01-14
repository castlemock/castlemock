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
import com.castlemock.repository.core.file.http.converter.HttpHeaderFileConverter;
import com.castlemock.repository.soap.file.event.model.SoapResponseFile;

import java.util.List;
import java.util.stream.Collectors;

public final class SoapResponseFileConverter {

    private SoapResponseFileConverter() {

    }

    public static SoapResponse toSoapResponse(final SoapResponseFile soapResponseFile) {
        return SoapResponse.builder()
                .body(soapResponseFile.getBody())
                .contentType(soapResponseFile.getContentType())
                .httpStatusCode(soapResponseFile.getHttpStatusCode())
                .mockResponseName(soapResponseFile.getMockResponseName())
                .contentEncodings(List.copyOf(soapResponseFile.getContentEncodings()))
                .httpHeaders(soapResponseFile.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderFileConverter::toHttpHeader)
                        .collect(Collectors.toList()))
                .build();
    }
}
