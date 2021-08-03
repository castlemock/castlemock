package com.castlemock.model.mock.rest.domain;

import com.castlemock.model.core.http.ContentEncoding;
import com.castlemock.model.core.http.HttpHeader;

import java.util.List;

public final class RestResponseTestBuilder {

    private String body;
    private String mockResponseName;
    private Integer httpStatusCode;
    private String contentType;
    private List<HttpHeader> httpHeaders;
    private List<ContentEncoding> contentEncodings;

    private RestResponseTestBuilder() {
        this.body = "";
        this.mockResponseName = "";
        this.httpStatusCode = 200;
        this.contentType = "";
        this.httpHeaders = List.of();
        this.contentEncodings = List.of();
    }

    public static RestResponseTestBuilder builder() {
        return new RestResponseTestBuilder();
    }

    public RestResponseTestBuilder body(final String body) {
        this.body = body;
        return this;
    }

    public RestResponseTestBuilder mockResponseName(final String mockResponseName) {
        this.mockResponseName = mockResponseName;
        return this;
    }

    public RestResponseTestBuilder httpStatusCode(final Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        return this;
    }

    public RestResponseTestBuilder contentType(final String contentType) {
        this.contentType = contentType;
        return this;
    }

    public RestResponseTestBuilder httpHeaders(final List<HttpHeader> httpHeaders) {
        this.httpHeaders = httpHeaders;
        return this;
    }

    public RestResponseTestBuilder contentEncodings(final List<ContentEncoding> contentEncodings) {
        this.contentEncodings = contentEncodings;
        return this;
    }

    public RestResponse build() {
        return RestResponse.builder()
                .body(body)
                .contentEncodings(contentEncodings)
                .contentType(contentType)
                .httpHeaders(httpHeaders)
                .httpStatusCode(httpStatusCode)
                .mockResponseName(mockResponseName)
                .build();
    }

}
