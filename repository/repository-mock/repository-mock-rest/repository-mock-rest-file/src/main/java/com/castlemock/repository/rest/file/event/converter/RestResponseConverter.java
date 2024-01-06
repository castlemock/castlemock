package com.castlemock.repository.rest.file.event.converter;

import com.castlemock.model.mock.rest.domain.RestResponse;
import com.castlemock.repository.core.file.http.converter.HttpHeaderConverter;
import com.castlemock.repository.rest.file.event.model.RestResponseFile;

import java.util.List;
import java.util.stream.Collectors;

public final class RestResponseConverter {

    private RestResponseConverter() {

    }

    public static RestResponseFile toRestResponseFile(final RestResponse restResponse) {
        return RestResponseFile.builder()
                .httpStatusCode(restResponse.getHttpStatusCode())
                .body(restResponse.getBody()
                        .orElse(null))
                .contentType(restResponse.getContentType()
                        .orElse(null))
                .mockResponseName(restResponse.getMockResponseName()
                        .orElse(null))
                .contentEncodings(List.copyOf(restResponse.getContentEncodings()))
                .httpHeaders(restResponse.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderConverter::toHttpHeaderFile)
                        .collect(Collectors.toList()))
                .build();
    }

}
