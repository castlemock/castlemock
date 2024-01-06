package com.castlemock.repository.soap.file.project.converter;

import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.repository.soap.file.project.model.SoapResourceFile;

public final class SoapResourceConverter {

    private SoapResourceConverter() {

    }

    public static SoapResourceFile toSoapResourceFile(final SoapResource soapResource) {
        return SoapResourceFile.builder()
                .id(soapResource.getId())
                .name(soapResource.getName())
                .projectId(soapResource.getProjectId())
                .type(soapResource.getType())
                .build();
    }

}
