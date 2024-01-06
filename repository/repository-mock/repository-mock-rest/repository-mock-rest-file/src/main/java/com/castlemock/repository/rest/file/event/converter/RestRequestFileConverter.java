package com.castlemock.repository.rest.file.event.converter;

import com.castlemock.model.mock.rest.domain.RestRequest;
import com.castlemock.repository.core.file.http.converter.HttpHeaderFileConverter;
import com.castlemock.repository.core.file.http.converter.HttpParameterFileConverter;
import com.castlemock.repository.rest.file.event.model.RestRequestFile;

import java.util.stream.Collectors;

public final class RestRequestFileConverter {

    private RestRequestFileConverter() {

    }

    public static RestRequest toRestRequest(final RestRequestFile restRequest) {
        return RestRequest.builder()
                .body(restRequest.getBody())
                .contentType(restRequest.getContentType())
                .uri(restRequest.getUri())
                .httpMethod(restRequest.getHttpMethod())
                .httpHeaders(restRequest.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderFileConverter::toHttpHeader)
                        .collect(Collectors.toList()))
                .httpParameters(restRequest.getHttpParameters()
                        .stream()
                        .map(HttpParameterFileConverter::toHttpParameter)
                        .collect(Collectors.toList()))
                .build();
    }

}
