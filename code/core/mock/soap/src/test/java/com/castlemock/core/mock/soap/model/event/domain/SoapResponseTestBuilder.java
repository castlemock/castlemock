package com.castlemock.core.mock.soap.model.event.domain;

import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;

import java.util.List;

public final class SoapResponseTestBuilder {

    private String body;
    private String mockResponseName;
    private Integer httpStatusCode;
    private String contentType;
    private List<HttpHeader> httpHeaders;
    private List<ContentEncoding> contentEncodings;

    private SoapResponseTestBuilder() {
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

    public SoapResponseTestBuilder contentEncodings(final List<ContentEncoding> contentEncodings) {
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
