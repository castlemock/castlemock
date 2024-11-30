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

import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.model.mock.soap.domain.SoapResponse;
import com.castlemock.web.mock.soap.factory.SoapMockStrategyResultFactory;
import com.castlemock.web.mock.soap.utility.SoapClient;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;
import java.util.Optional;

public final class ForwardSoapStrategy implements SoapStrategy {

    private static final int ERROR_CODE = 500;
    private final SoapClient soapHttpClient;
    private final SoapMockStrategyResultFactory soapMockStrategyResultFactory;

    public ForwardSoapStrategy(final SoapClient soapHttpClient,
                               final SoapMockStrategyResultFactory soapMockStrategyResultFactory) {
        this.soapHttpClient = Objects.requireNonNull(soapHttpClient, "soapHttpClient");
        this.soapMockStrategyResultFactory = Objects.requireNonNull(soapMockStrategyResultFactory, "soapResponseStrategyManager");
    }

    @Override
    public SoapStrategyResult process(final SoapRequest request, final String projectId,
                                final String portId, final SoapOperation operation,
                                final HttpServletRequest httpServletRequest) {
        final Optional<SoapResponse> optionalSoapResponse = soapHttpClient.getResponse(request, operation);
        if (optionalSoapResponse.isPresent()) {
            final SoapResponse response = optionalSoapResponse.get();
            if (response.getHttpStatusCode() >= ERROR_CODE) {
                if (operation.getMockOnFailure().orElse(false)) {
                    return this.soapMockStrategyResultFactory.getResponse(request, projectId,
                            portId, operation, httpServletRequest);
                }
            }
        }

        return SoapStrategyResult.builder()
                .response(optionalSoapResponse.orElse(null))
                .build();
    }
}
