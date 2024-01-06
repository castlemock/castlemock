package com.castlemock.repository.soap.file.project.converter;

import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.repository.soap.file.project.model.SoapPortFile;

public final class SoapPortConverter {

    private SoapPortConverter() {

    }

    public static SoapPortFile toSoapPort(final SoapPort soapPort) {
        return SoapPortFile.builder()
                .id(soapPort.getId())
                .projectId(soapPort.getProjectId())
                .name(soapPort.getName())
                .uri(soapPort.getUri())
                .build();
    }

}
