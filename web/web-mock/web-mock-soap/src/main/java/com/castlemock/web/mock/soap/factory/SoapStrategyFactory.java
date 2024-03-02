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

import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.web.mock.soap.utility.SoapClient;
import com.castlemock.web.mock.soap.stategy.DisabledSoapStrategy;
import com.castlemock.web.mock.soap.stategy.EchoSoapStrategy;
import com.castlemock.web.mock.soap.stategy.ForwardSoapStrategy;
import com.castlemock.web.mock.soap.stategy.MockSoapStrategy;
import com.castlemock.web.mock.soap.stategy.RecordOnceSoapStrategy;
import com.castlemock.web.mock.soap.stategy.RecordingSoapStrategy;
import com.castlemock.web.mock.soap.stategy.SoapStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class SoapStrategyFactory {

    private final Map<SoapOperationStatus, SoapStrategy> strategies;

    public SoapStrategyFactory(final SoapClient soapClient,
                               final SoapMockStrategyResultFactory soapMockStrategyResultFactory) {
        this.strategies = Map.of(
                SoapOperationStatus.DISABLED, new DisabledSoapStrategy(),
                SoapOperationStatus.MOCKED, new MockSoapStrategy(soapClient, soapMockStrategyResultFactory),
                SoapOperationStatus.RECORDING, new RecordingSoapStrategy(soapClient, soapMockStrategyResultFactory),
                SoapOperationStatus.RECORD_ONCE, new RecordOnceSoapStrategy(soapClient, soapMockStrategyResultFactory),
                SoapOperationStatus.FORWARDED, new ForwardSoapStrategy(soapClient, soapMockStrategyResultFactory),
                SoapOperationStatus.ECHO, new EchoSoapStrategy());
    }

    public SoapStrategy getStrategy(final SoapOperation operation) {
        return Optional.ofNullable(this.strategies.get(operation.getStatus()))
                .orElseThrow(() -> new IllegalArgumentException("Unable to map the following operation status to a response strategy: " + operation.getStatus()));
    }

}
