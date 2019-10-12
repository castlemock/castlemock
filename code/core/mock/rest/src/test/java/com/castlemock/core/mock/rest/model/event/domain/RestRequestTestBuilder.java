package com.castlemock.core.mock.rest.model.event.domain;

import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.google.common.collect.ImmutableList;

import java.util.List;

public final class RestRequestTestBuilder {

    private String body;
    private String contentType;
    private String uri;
    private HttpMethod httpMethod;
    private List<HttpHeader> httpHeaders;

    private RestRequestTestBuilder() {
        this.body = "Rest request body";
        this.contentType = "application/json";
        this.httpMethod = HttpMethod.POST;
        this.httpHeaders = ImmutableList.of();
    }

    public static RestRequestTestBuilder builder(){
        return new RestRequestTestBuilder();
    }

    public RestRequestTestBuilder body(String body) {
        this.body = body;
        return this;
    }

    public RestRequestTestBuilder contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public RestRequestTestBuilder uri(String uri) {
        this.uri = uri;
        return this;
    }

    public RestRequestTestBuilder httpMethod(final HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public RestRequestTestBuilder httpHeaders(final List<HttpHeader> httpHeaders) {
        this.httpHeaders = httpHeaders;
        return this;
    }

    public RestRequest build() {
        return RestRequest.builder()
                .body(body)
                .contentType(contentType)
                .httpHeaders(httpHeaders)
                .httpMethod(httpMethod)
                .uri(uri)
                .build();
    }
}
