package com.castlemock.repository.soap.file.project.converter;

import com.castlemock.model.mock.soap.domain.SoapOperationIdentifier;
import com.castlemock.repository.soap.file.project.model.SoapOperationIdentifierFile;

public final class SoapOperationIdentifierConverter {

    private SoapOperationIdentifierConverter() {

    }

    public static SoapOperationIdentifierFile toSoapOperationIdentifierFile(final SoapOperationIdentifier soapOperationIdentifier) {
        return SoapOperationIdentifierFile.builder()
                .name(soapOperationIdentifier.getName())
                .namespace(soapOperationIdentifier.getNamespace().orElse(null))
                .build();
    }

}
