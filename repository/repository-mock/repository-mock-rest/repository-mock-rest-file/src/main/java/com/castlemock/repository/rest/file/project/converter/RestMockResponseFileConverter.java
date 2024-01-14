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

package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.repository.core.file.http.converter.HttpHeaderFileConverter;
import com.castlemock.repository.rest.file.project.model.RestMockResponseFile;

import java.util.ArrayList;
import java.util.stream.Collectors;

public final class RestMockResponseFileConverter {

    private RestMockResponseFileConverter() {

    }

    public static RestMockResponse toRestMockResponse(final RestMockResponseFile restMockResponseFile) {
        return RestMockResponse.builder()
                .id(restMockResponseFile.getId())
                .methodId(restMockResponseFile.getMethodId())
                .name(restMockResponseFile.getName())
                .status(restMockResponseFile.getStatus())
                .httpStatusCode(restMockResponseFile.getHttpStatusCode())
                .body(restMockResponseFile.getBody())
                .usingExpressions(restMockResponseFile.getUsingExpressions())
                .headerQueries(restMockResponseFile.getHeaderQueries()
                        .stream()
                        .map(RestHeaderQueryFileConverter::toRestHeaderQuery)
                        .collect(Collectors.toList()))
                .parameterQueries(restMockResponseFile.getParameterQueries()
                        .stream()
                        .map(RestParameterQueryFileConverter::toRestParameterQuery)
                        .collect(Collectors.toList()))
                .jsonPathExpressions(restMockResponseFile.getJsonPathExpressions()
                        .stream()
                        .map(RestJsonPathExpressionFileConverter::toRestJsonPathExpression)
                        .collect(Collectors.toList()))
                .xpathExpressions(restMockResponseFile.getXpathExpressions()
                        .stream()
                        .map(RestXPathExpressionFileConverter::toRestXPathExpression)
                        .collect(Collectors.toList()))
                .httpHeaders(restMockResponseFile.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderFileConverter::toHttpHeader)
                        .collect(Collectors.toList()))
                .contentEncodings(new ArrayList<>(restMockResponseFile.getContentEncodings()))
                .build();
    }

}
