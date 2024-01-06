package com.castlemock.repository.soap.file.event.converter;

import com.castlemock.model.mock.soap.domain.SoapEvent;
import com.castlemock.repository.core.file.event.converter.EventConverter;
import com.castlemock.repository.soap.file.event.model.SoapEventFile;

public final class SoapEventConverter {

    private SoapEventConverter() {

    }

    public static SoapEventFile toSoapEventFile(final SoapEvent soapEvent) {
        return EventConverter.toEventFile(soapEvent, SoapEventFile.builder())
                .projectId(soapEvent.getProjectId())
                .portId(soapEvent.getPortId())
                .operationId(soapEvent.getOperationId())
                .request(SoapRequestConverter.toSoapRequestFile(soapEvent.getRequest()))
                .response(soapEvent.getResponse()
                        .map(SoapResponseConverter::toSoapResponseFile)
                        .orElse(null))
                .build();
    }
}
