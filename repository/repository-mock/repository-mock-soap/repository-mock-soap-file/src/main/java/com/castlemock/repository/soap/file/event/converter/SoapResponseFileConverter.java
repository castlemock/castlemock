package com.castlemock.repository.soap.file.event.converter;

import com.castlemock.model.mock.soap.domain.SoapResponse;
import com.castlemock.repository.core.file.http.converter.HttpHeaderFileConverter;
import com.castlemock.repository.soap.file.event.model.SoapResponseFile;

import java.util.List;
import java.util.stream.Collectors;

public final class SoapResponseFileConverter {

    private SoapResponseFileConverter() {

    }

    public static SoapResponse toSoapResponse(final SoapResponseFile soapResponseFile) {
        return SoapResponse.builder()
                .body(soapResponseFile.getBody())
                .contentType(soapResponseFile.getContentType())
                .httpStatusCode(soapResponseFile.getHttpStatusCode())
                .mockResponseName(soapResponseFile.getMockResponseName())
                .contentEncodings(List.copyOf(soapResponseFile.getContentEncodings()))
                .httpHeaders(soapResponseFile.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderFileConverter::toHttpHeader)
                        .collect(Collectors.toList()))
                .build();
    }
}
