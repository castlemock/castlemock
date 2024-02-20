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

package com.castlemock.web.mock.soap.factory;

import com.castlemock.model.core.Input;
import com.castlemock.model.core.utility.XPathUtility;
import com.castlemock.model.core.utility.parser.ExternalInputBuilder;
import com.castlemock.model.core.utility.parser.TextParser;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.model.mock.soap.domain.SoapResponse;
import com.castlemock.model.mock.soap.domain.SoapResponseStrategy;
import com.castlemock.service.mock.soap.project.input.UpdateCurrentMockResponseSequenceIndexInput;
import com.castlemock.web.mock.soap.converter.SoapMockResponseConverter;
import com.castlemock.web.mock.soap.stategy.SoapStrategyResult;
import com.castlemock.web.mock.soap.utility.compare.SoapMockResponseNameComparator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

@Component
public class SoapMockStrategyResultFactory {

    private static final Random RANDOM = new Random();
    private static final SoapMockResponseNameComparator MOCK_RESPONSE_NAME_COMPARATOR =
            new SoapMockResponseNameComparator();

    public SoapMockStrategyResultFactory() {
    }

    public SoapStrategyResult getResponse(final SoapRequest request, final String projectId,
                                          final String portId, final SoapOperation operation,
                                          final HttpServletRequest httpServletRequest) {
        final List<SoapMockResponse> mockResponses = new ArrayList<>();
        for (SoapMockResponse mockResponse : operation.getMockResponses()) {
            if (mockResponse.getStatus().equals(SoapMockResponseStatus.ENABLED)) {
                mockResponses.add(mockResponse);
            }
        }

        mockResponses.sort(MOCK_RESPONSE_NAME_COMPARATOR);

        SoapMockResponse mockResponse = null;
        final List<Input> postServiceRequests = new ArrayList<>();
        if (mockResponses.isEmpty()) {
            return SoapStrategyResult.builder().build();
        } else if (operation.getResponseStrategy().equals(SoapResponseStrategy.RANDOM)) {
            final int responseIndex = RANDOM.nextInt(mockResponses.size());
            mockResponse = mockResponses.get(responseIndex);
        } else if (operation.getResponseStrategy().equals(SoapResponseStrategy.SEQUENCE)) {
            Integer currentSequenceNumber = operation.getCurrentResponseSequenceIndex();
            if (currentSequenceNumber >= mockResponses.size()) {
                currentSequenceNumber = 0;
            }
            mockResponse = mockResponses.get(currentSequenceNumber);
            postServiceRequests.add(UpdateCurrentMockResponseSequenceIndexInput.builder()
                    .projectId(projectId)
                    .portId(portId)
                    .operationId(operation.getId())
                    .currentResponseSequenceIndex(currentSequenceNumber + 1)
                    .build());
        } else if (operation.getResponseStrategy().equals(SoapResponseStrategy.XPATH_INPUT)) {
            mockResponse = mockResponses
                    .stream()
                    .filter(testedMockResponse -> testedMockResponse.getXpathExpressions()
                            .stream()
                            .anyMatch(xPathExpression -> XPathUtility.isValidXPathExpr(request.getEnvelope(), xPathExpression.getExpression())))
                    .findFirst()
                    .orElseGet(() -> getDefaultMockResponse(operation, mockResponses).orElse(null));
        }

        if (mockResponse == null) {
            return SoapStrategyResult.builder().build();
        }

        final String body = getBody(mockResponse, request, httpServletRequest);
        final SoapResponse response = SoapMockResponseConverter.toSoapResponse(mockResponse, body);
        return SoapStrategyResult.builder()
                .response(response)
                .postServiceRequests(postServiceRequests)
                .build();
    }

    private String getBody(final SoapMockResponse mockResponse,
                           final SoapRequest request,
                           final HttpServletRequest httpServletRequest) {
        if (mockResponse.getUsingExpressions().orElse(false)) {
            final Map<String, ExpressionArgument<?>> externalInput = new ExternalInputBuilder()
                    .requestUrl(httpServletRequest.getRequestURL().toString())
                    .requestBody(request.getBody())
                    .build();

            return new TextParser().parse(mockResponse.getBody(), externalInput)
                    .orElse("");
        }

        return mockResponse.getBody();
    }

    private Optional<SoapMockResponse> getDefaultMockResponse(final SoapOperation soapOperation,
                                                              final List<SoapMockResponse> mockResponses) {
        return soapOperation.getDefaultMockResponseId()
                .filter(Predicate.not(String::isEmpty))
                .flatMap(defaultResponseId -> mockResponses.stream()
                        .filter(mockResponse -> mockResponse.getId().equals(defaultResponseId))
                        .findFirst());
    }

}
