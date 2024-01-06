package com.castlemock.repository.soap.file.project.converter;

import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.repository.soap.file.project.model.SoapResourceFile;

public final class SoapResourceFileConverter {

    private SoapResourceFileConverter() {

    }

    public static SoapResource toSoapResource(final SoapResourceFile soapResourceFile) {
        return SoapResource.builder()
                .id(soapResourceFile.getId())
                .name(soapResourceFile.getName())
                .projectId(soapResourceFile.getProjectId())
                .type(soapResourceFile.getType())
                .build();
    }

}
