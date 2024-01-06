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
                .identifier(soapOperation.getIdentifier())
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
