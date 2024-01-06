package com.castlemock.repository.soap.file.event.converter;

import com.castlemock.model.mock.soap.domain.SoapEvent;
import com.castlemock.repository.core.file.event.converter.EventFileConverter;
import com.castlemock.repository.soap.file.event.model.SoapEventFile;

import java.util.Optional;

public final class SoapEventFileConverter {

    private SoapEventFileConverter() {

    }

    public static SoapEvent toSoapEvent(final SoapEventFile soapEventFile) {
        return EventFileConverter.toEvent(soapEventFile, SoapEvent.builder())
                .projectId(soapEventFile.getProjectId())
                .portId(soapEventFile.getPortId())
                .operationId(soapEventFile.getOperationId())
                .request(SoapRequestFileConverter.toSoapRequest(soapEventFile.getRequest()))
                .response(Optional.ofNullable(soapEventFile.getResponse())
                        .map(SoapResponseFileConverter::toSoapResponse)
                        .orElse(null))
                .build();
    }
}
