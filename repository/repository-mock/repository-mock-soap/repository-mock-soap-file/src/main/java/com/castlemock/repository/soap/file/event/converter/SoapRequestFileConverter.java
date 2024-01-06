package com.castlemock.repository.soap.file.event.converter;

import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.repository.core.file.http.converter.HttpHeaderFileConverter;
import com.castlemock.repository.soap.file.event.model.SoapRequestFile;

import java.util.stream.Collectors;

public final class SoapRequestFileConverter {

    private SoapRequestFileConverter() {

    }

    public static SoapRequest toSoapRequest(final SoapRequestFile soapRequestFile) {
        return SoapRequest.builder()
                .body(soapRequestFile.getBody())
                .envelope(soapRequestFile.getEnvelope())
                .contentType(soapRequestFile.getContentType())
                .uri(soapRequestFile.getUri())
                .httpMethod(soapRequestFile.getHttpMethod())
                .operationName(soapRequestFile.getOperationName())
                .soapVersion(soapRequestFile.getSoapVersion())
                .httpHeaders(soapRequestFile.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderFileConverter::toHttpHeader)
                        .collect(Collectors.toList()))
                .operationIdentifier(SoapOperationIdentifierFileConverter
                        .toSoapOperationIdentifier(soapRequestFile.getOperationIdentifier()))
                .build();
    }

}
