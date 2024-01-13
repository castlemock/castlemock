package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.repository.core.file.http.converter.HttpHeaderConverter;
import com.castlemock.repository.rest.file.project.model.RestMockResponseFile;

import java.util.ArrayList;
import java.util.stream.Collectors;

public final class RestMockResponseConverter {

    private RestMockResponseConverter() {

    }

    public static RestMockResponseFile toRestMockResponse(final RestMockResponse restMockResponse) {
        return RestMockResponseFile.builder()
                .id(restMockResponse.getId())
                .methodId(restMockResponse.getMethodId())
                .name(restMockResponse.getName())
                .status(restMockResponse.getStatus())
                .httpStatusCode(restMockResponse.getHttpStatusCode())
                .body(restMockResponse.getBody().orElse(null))
                .usingExpressions(restMockResponse.getUsingExpressions()
                        .orElse(null))
                .headerQueries(restMockResponse.getHeaderQueries()
                        .stream()
                        .map(RestHeaderQueryConverter::toRestHeaderQuery)
                        .collect(Collectors.toList()))
                .parameterQueries(restMockResponse.getParameterQueries()
                        .stream()
                        .map(RestParameterQueryConverter::toRestParameterQuery)
                        .collect(Collectors.toList()))
                .jsonPathExpressions(restMockResponse.getJsonPathExpressions()
                        .stream()
                        .map(RestJsonPathExpressionConverter::toRestJsonPathExpressionFile)
                        .collect(Collectors.toList()))
                .xpathExpressions(restMockResponse.getXpathExpressions()
                        .stream()
                        .map(RestXPathExpressionConverter::toRestXPathExpressionFile)
                        .collect(Collectors.toList()))
                .httpHeaders(restMockResponse.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderConverter::toHttpHeaderFile)
                        .collect(Collectors.toList()))
                .contentEncodings(new ArrayList<>(restMockResponse.getContentEncodings()))
                .build();
    }

}
