package com.castlemock.web.mock.soap.converter;

import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapResponse;

public final class SoapMockResponseConverter {

    private SoapMockResponseConverter() {

    }

    public static SoapResponse toSoapResponse(final SoapMockResponse mockResponse, final String body) {
        return SoapResponse.builder()
                .body(body)
                .mockResponseName(mockResponse.getName())
                .httpHeaders(mockResponse.getHttpHeaders())
                .httpStatusCode(mockResponse.getHttpStatusCode())
                .contentEncodings(mockResponse.getContentEncodings())
                .build();
    }

}
