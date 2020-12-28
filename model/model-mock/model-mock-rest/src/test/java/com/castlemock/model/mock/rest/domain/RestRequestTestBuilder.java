package com.castlemock.model.mock.rest.domain;

import com.castlemock.model.core.model.http.domain.HttpHeader;
import com.castlemock.model.core.model.http.domain.HttpMethod;
import com.castlemock.model.core.model.http.domain.HttpParameter;
import com.google.common.collect.ImmutableList;

import java.util.List;

public final class RestRequestTestBuilder {

    private String body;
    private String contentType;
    private String uri;
    private HttpMethod httpMethod;
    private List<HttpHeader> httpHeaders;
    private List<HttpParameter> httpParameters;

    private RestRequestTestBuilder() {
        this.uri = "/test";
        this.body = "Rest request body";
        this.contentType = "application/json";
        this.httpMethod = HttpMethod.POST;
        this.httpHeaders = ImmutableList.of();
        this.httpParameters = ImmutableList.of();
    }

    public static RestRequestTestBuilder builder(){
        return new RestRequestTestBuilder();
    }

    public RestRequestTestBuilder body(final String body) {
        this.body = body;
        return this;
    }

    public RestRequestTestBuilder contentType(final String contentType) {
        this.contentType = contentType;
        return this;
    }

    public RestRequestTestBuilder uri(final String uri) {
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

    public RestRequestTestBuilder httpParameters(final List<HttpParameter> httpParameters) {
        this.httpParameters = httpParameters;
        return this;
    }

    public RestRequest build() {
        return RestRequest.builder()
                .uri(uri)
                .body(body)
                .contentType(contentType)
                .httpHeaders(httpHeaders)
                .httpMethod(httpMethod)
                .httpParameters(httpParameters)
                .build();
    }
}
