package com.castlemock.repository.soap.file.event.converter;

import com.castlemock.model.mock.soap.domain.SoapOperationIdentifier;
import com.castlemock.repository.soap.file.event.model.SoapOperationIdentifierFile;

public final class SoapOperationIdentifierConverter {

    private SoapOperationIdentifierConverter() {

    }

    public static SoapOperationIdentifierFile toSoapOperationIdentifier(final SoapOperationIdentifier soapOperationIdentifier) {
        return SoapOperationIdentifierFile.builder()
                .name(soapOperationIdentifier.getName())
                .namespace(soapOperationIdentifier.getNamespace()
                        .orElse(null))
                .build();
    }

}
