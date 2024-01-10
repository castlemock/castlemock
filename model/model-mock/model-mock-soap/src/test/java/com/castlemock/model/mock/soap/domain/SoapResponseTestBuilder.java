package com.castlemock.model.mock.soap.domain;

import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.model.core.http.HttpHeader;

import java.util.List;

public final class SoapResponseTestBuilder {

    private String body;
    private String mockResponseName;
    private Integer httpStatusCode;
    private String contentType;
    private List<HttpHeader> httpHeaders;
    private List<HttpContentEncoding> contentEncodings;

    private SoapResponseTestBuilder() {
        this.body = "";
        this.mockResponseName = "";
        this.httpStatusCode = 200;
        this.contentType = "application/json";
        this.httpHeaders = List.of();
        this.contentEncodings = List.of();
    }

    public static SoapResponseTestBuilder builder() {
        return new SoapResponseTestBuilder();
    }

    public SoapResponseTestBuilder body(final String body) {
        this.body = body;
        return this;
    }

    public SoapResponseTestBuilder mockResponseName(final String mockResponseName) {
        this.mockResponseName = mockResponseName;
        return this;
    }

    public SoapResponseTestBuilder httpStatusCode(final Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        return this;
    }

    public SoapResponseTestBuilder contentType(final String contentType) {
        this.contentType = contentType;
        return this;
    }

    public SoapResponseTestBuilder httpHeaders(final List<HttpHeader> httpHeaders) {
        this.httpHeaders = httpHeaders;
        return this;
    }

    public SoapResponseTestBuilder contentEncodings(final List<HttpContentEncoding> contentEncodings) {
        this.contentEncodings = contentEncodings;
        return this;
    }

    public SoapResponse build() {
        return SoapResponse.builder()
                .body(body)
                .contentEncodings(contentEncodings)
                .contentType(contentType)
                .httpHeaders(httpHeaders)
                .httpStatusCode(httpStatusCode)
                .mockResponseName(mockResponseName)
                .build();
    }

}
