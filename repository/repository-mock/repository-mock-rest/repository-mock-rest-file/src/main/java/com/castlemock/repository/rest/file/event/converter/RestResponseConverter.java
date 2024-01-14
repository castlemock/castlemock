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

package com.castlemock.repository.rest.file.event.converter;

import com.castlemock.model.mock.rest.domain.RestResponse;
import com.castlemock.repository.core.file.http.converter.HttpHeaderConverter;
import com.castlemock.repository.rest.file.event.model.RestResponseFile;

import java.util.List;
import java.util.stream.Collectors;

public final class RestResponseConverter {

    private RestResponseConverter() {

    }

    public static RestResponseFile toRestResponseFile(final RestResponse restResponse) {
        return RestResponseFile.builder()
                .httpStatusCode(restResponse.getHttpStatusCode())
                .body(restResponse.getBody()
                        .orElse(null))
                .contentType(restResponse.getContentType()
                        .orElse(null))
                .mockResponseName(restResponse.getMockResponseName()
                        .orElse(null))
                .contentEncodings(List.copyOf(restResponse.getContentEncodings()))
                .httpHeaders(restResponse.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderConverter::toHttpHeaderFile)
                        .collect(Collectors.toList()))
                .build();
    }

}
