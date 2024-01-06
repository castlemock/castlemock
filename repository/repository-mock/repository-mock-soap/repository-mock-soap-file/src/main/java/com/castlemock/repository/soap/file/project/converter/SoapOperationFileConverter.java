package com.castlemock.repository.soap.file.project.converter;

import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.repository.soap.file.project.model.SoapOperationFile;

public final class SoapOperationFileConverter {

    private SoapOperationFileConverter() {

    }

    public static SoapOperation toSoapOperation(final SoapOperationFile soapOperationFile) {
        return SoapOperation.builder()
                .id(soapOperationFile.getId())
                .name(soapOperationFile.getName())
                .portId(soapOperationFile.getPortId())
                .responseStrategy(soapOperationFile.getResponseStrategy())
                .identifier(soapOperationFile.getIdentifier())
                .operationIdentifier(SoapOperationIdentifierFileConverter
                        .toSoapOperationIdentifier(soapOperationFile.getOperationIdentifier()))
                .status(soapOperationFile.getStatus())
                .httpMethod(soapOperationFile.getHttpMethod())
                .soapVersion(soapOperationFile.getSoapVersion())
                .defaultBody(soapOperationFile.getDefaultBody())
                .currentResponseSequenceIndex(soapOperationFile.getCurrentResponseSequenceIndex())
                .forwardedEndpoint(soapOperationFile.getForwardedEndpoint())
                .originalEndpoint(soapOperationFile.getOriginalEndpoint())
                .defaultMockResponseId(soapOperationFile.getDefaultMockResponseId())
                .simulateNetworkDelay(soapOperationFile.getSimulateNetworkDelay())
                .networkDelay(soapOperationFile.getNetworkDelay())
                .mockOnFailure(soapOperationFile.getMockOnFailure())
                .identifyStrategy(soapOperationFile.getIdentifyStrategy())
                .automaticForward(soapOperationFile.getAutomaticForward())
                .build();
    }

}
