package com.castlemock.repository.soap.file.event.converter;

import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.repository.core.file.http.converter.HttpHeaderConverter;
import com.castlemock.repository.soap.file.event.model.SoapRequestFile;

import java.util.stream.Collectors;

public final class SoapRequestConverter {

    private SoapRequestConverter() {

    }

    public static SoapRequestFile toSoapRequestFile(final SoapRequest soapRequest) {
        return SoapRequestFile.builder()
                .body(soapRequest.getBody())
                .envelope(soapRequest.getEnvelope())
                .contentType(soapRequest.getContentType())
                .uri(soapRequest.getUri())
                .httpMethod(soapRequest.getHttpMethod())
                .operationName(soapRequest.getOperationName())
                .soapVersion(soapRequest.getSoapVersion())
                .httpHeaders(soapRequest.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderConverter::toHttpHeaderFile)
                        .collect(Collectors.toList()))
                .operationIdentifier(SoapOperationIdentifierConverter
                        .toSoapOperationIdentifier(soapRequest.getOperationIdentifier()))
                .build();
    }

}
