package com.castlemock.repository.soap.file.event.converter;

import com.castlemock.model.mock.soap.domain.SoapResponse;
import com.castlemock.repository.core.file.http.converter.HttpHeaderConverter;
import com.castlemock.repository.soap.file.event.model.SoapResponseFile;

import java.util.List;
import java.util.stream.Collectors;

public final class SoapResponseConverter {

    private SoapResponseConverter() {

    }

    public static SoapResponseFile toSoapResponseFile(final SoapResponse soapResponse) {
        return SoapResponseFile.builder()
                .body(soapResponse.getBody())
                .httpStatusCode(soapResponse.getHttpStatusCode())
                .contentEncodings(List.copyOf(soapResponse.getContentEncodings()))
                .httpHeaders(soapResponse.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderConverter::toHttpHeaderFile)
                        .collect(Collectors.toList()))
                .mockResponseName(soapResponse.getMockResponseName()
                        .orElse(null))
                .contentType(soapResponse.getContentType()
                        .orElse(null))
                .build();
    }
}
