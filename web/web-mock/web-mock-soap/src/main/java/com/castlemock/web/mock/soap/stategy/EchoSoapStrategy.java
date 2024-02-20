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

package com.castlemock.web.mock.soap.stategy;

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.model.mock.soap.domain.SoapResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;

public final class EchoSoapStrategy implements SoapStrategy {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final int DEFAULT_ECHO_RESPONSE_CODE = 200;

    public EchoSoapStrategy() {
    }

    @Override
    public SoapStrategyResult process(final SoapRequest request, final String projectId,
                                final String portId, final SoapOperation operation,
                                final HttpServletRequest httpServletRequest) {
        final List<HttpHeader> headers = List.of(HttpHeader.builder()
                .name(CONTENT_TYPE)
                .value(request.getContentType())
                .build());

        return SoapStrategyResult.builder()
                .response(SoapResponse.builder()
                        .body(request.getBody())
                        .contentType(request.getContentType())
                        .httpHeaders(headers)
                        .httpStatusCode(DEFAULT_ECHO_RESPONSE_CODE)
                        .contentEncodings(Collections.emptyList())
                        .build())
                .build();
    }

}
