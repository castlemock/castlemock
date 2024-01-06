package com.castlemock.repository.rest.file.event.converter;

import com.castlemock.model.mock.rest.domain.RestRequest;
import com.castlemock.repository.core.file.http.converter.HttpHeaderConverter;
import com.castlemock.repository.core.file.http.converter.HttpParameterConverter;
import com.castlemock.repository.rest.file.event.model.RestRequestFile;

import java.util.stream.Collectors;

public final class RestRequestConverter {

    private RestRequestConverter() {

    }

    public static RestRequestFile toRestRequestFile(final RestRequest restRequest) {
        return RestRequestFile.builder()
                .uri(restRequest.getUri())
                .httpMethod(restRequest.getHttpMethod())
                .httpHeaders(restRequest.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderConverter::toHttpHeaderFile)
                        .collect(Collectors.toList()))
                .httpParameters(restRequest.getHttpParameters()
                        .stream()
                        .map(HttpParameterConverter::toHttpParameterFile)
                        .collect(Collectors.toList()))
                .contentType(restRequest.getContentType()
                        .orElse(null))
                .body(restRequest.getBody()
                        .orElse(null))
                .build();
    }

}
