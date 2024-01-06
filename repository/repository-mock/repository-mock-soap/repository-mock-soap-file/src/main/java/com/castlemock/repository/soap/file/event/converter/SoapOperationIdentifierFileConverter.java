package com.castlemock.repository.soap.file.event.converter;

import com.castlemock.model.mock.soap.domain.SoapOperationIdentifier;
import com.castlemock.repository.soap.file.event.model.SoapOperationIdentifierFile;

public final class SoapOperationIdentifierFileConverter {

    private SoapOperationIdentifierFileConverter() {

    }

    public static SoapOperationIdentifier toSoapOperationIdentifier(final SoapOperationIdentifierFile soapOperationIdentifierFile) {
        return SoapOperationIdentifier.builder()
                .name(soapOperationIdentifierFile.getName())
                .namespace(soapOperationIdentifierFile.getNamespace())
                .build();
    }

}
