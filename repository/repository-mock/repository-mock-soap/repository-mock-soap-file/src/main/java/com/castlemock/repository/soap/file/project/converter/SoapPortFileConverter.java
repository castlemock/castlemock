package com.castlemock.repository.soap.file.project.converter;

import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.repository.soap.file.project.model.SoapPortFile;

public final class SoapPortFileConverter {

    private SoapPortFileConverter() {

    }

    public static SoapPort toSoapPortFile(final SoapPortFile soapPortFile) {
        return SoapPort.builder()
                .id(soapPortFile.getId())
                .projectId(soapPortFile.getProjectId())
                .name(soapPortFile.getName())
                .uri(soapPortFile.getUri())
                .build();
    }

}
