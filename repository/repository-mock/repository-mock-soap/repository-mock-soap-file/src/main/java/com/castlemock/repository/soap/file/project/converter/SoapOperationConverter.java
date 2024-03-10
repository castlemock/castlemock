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

package com.castlemock.repository.soap.file.project.converter;

import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.repository.soap.file.project.model.SoapOperationFile;

public final class SoapOperationConverter {

    private SoapOperationConverter() {

    }

    public static SoapOperationFile toSoapOperationFile(final SoapOperation soapOperation) {
        return SoapOperationFile.builder()
                .id(soapOperation.getId())
                .name(soapOperation.getName())
                .portId(soapOperation.getPortId())
                .responseStrategy(soapOperation.getResponseStrategy())
                .operationIdentifier(SoapOperationIdentifierConverter
                        .toSoapOperationIdentifierFile(soapOperation.getOperationIdentifier()))
                .status(soapOperation.getStatus())
                .httpMethod(soapOperation.getHttpMethod())
                .soapVersion(soapOperation.getSoapVersion())
                .defaultBody(soapOperation.getDefaultBody()
                        .orElse(null))
                .currentResponseSequenceIndex(soapOperation.getCurrentResponseSequenceIndex())
                .forwardedEndpoint(soapOperation.getForwardedEndpoint()
                        .orElse(null))
                .originalEndpoint(soapOperation.getOriginalEndpoint()
                        .orElse(null))
                .defaultMockResponseId(soapOperation.getDefaultMockResponseId()
                        .orElse(null))
                .simulateNetworkDelay(soapOperation.getSimulateNetworkDelay()
                        .orElse(null))
                .networkDelay(soapOperation.getNetworkDelay()
                        .orElse(null))
                .mockOnFailure(soapOperation.getMockOnFailure()
                        .orElse(null))
                .identifyStrategy(soapOperation.getIdentifyStrategy())
                .automaticForward(soapOperation.getAutomaticForward()
                        .orElse(null))
                .build();
    }

}
