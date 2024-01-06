package com.castlemock.repository.rest.file.event.converter;

import com.castlemock.model.mock.rest.domain.RestResponse;
import com.castlemock.repository.core.file.http.converter.HttpHeaderFileConverter;
import com.castlemock.repository.rest.file.event.model.RestResponseFile;

import java.util.List;
import java.util.stream.Collectors;

public final class RestResponseFileConverter {

    private RestResponseFileConverter() {

    }

    public static RestResponse toRestResponse(final RestResponseFile restResponseFile) {
        return RestResponse.builder()
                .httpStatusCode(restResponseFile.getHttpStatusCode())
                .body(restResponseFile.getBody())
                .contentType(restResponseFile.getContentType())
                .mockResponseName(restResponseFile.getMockResponseName())
                .contentEncodings(List.copyOf(restResponseFile.getContentEncodings()))
                .httpHeaders(restResponseFile.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderFileConverter::toHttpHeader)
                        .collect(Collectors.toList()))
                .build();
    }

}
