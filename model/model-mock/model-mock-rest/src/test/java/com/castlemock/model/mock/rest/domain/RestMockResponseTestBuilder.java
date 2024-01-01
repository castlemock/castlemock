package com.castlemock.model.mock.rest.domain;

import com.castlemock.model.core.http.ContentEncoding;
import com.castlemock.model.core.http.HttpHeader;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class RestMockResponseTestBuilder {

    private String id;
    private String name;
    private String body;
    private String methodId;
    private Integer httpStatusCode;
    private RestMockResponseStatus status;
    private Boolean usingExpressions;
    private List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<>();
    private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<>();
    private List<RestParameterQuery> parameterQueries = new CopyOnWriteArrayList<>();
    private List<RestXPathExpression> xpathExpressions = new CopyOnWriteArrayList<>();
    private List<RestJsonPathExpression> jsonPathExpressions = new CopyOnWriteArrayList<>();
    private List<RestHeaderQuery> headerQueries = new CopyOnWriteArrayList<>();

    private RestMockResponseTestBuilder() {
        this.id = "r1eXT3";
        this.name = "Response: successful operation (application/xml)\n";
        this.body = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><User><id>${RANDOM_LONG()}</id><username>${RANDOM_STRING()}</username><firstName>${RANDOM_STRING()}</firstName><lastName>${RANDOM_STRING()}</lastName><email>${RANDOM_STRING()}</email><password>${RANDOM_STRING()}</password><phone>${RANDOM_STRING()}</phone><userStatus>${RANDOM_INTEGER()}</userStatus></User>";
        this.methodId = "OSIFJL";
        this.httpStatusCode = 200;
        this.status = RestMockResponseStatus.ENABLED;
        this.usingExpressions = Boolean.TRUE;
        this.httpHeaders = new CopyOnWriteArrayList<>();
        this.contentEncodings = new CopyOnWriteArrayList<>();
        this.parameterQueries = new CopyOnWriteArrayList<>();
        this.xpathExpressions = new CopyOnWriteArrayList<>();
        this.jsonPathExpressions = new CopyOnWriteArrayList<>();
        this.headerQueries = new CopyOnWriteArrayList<>();
    }

    public RestMockResponseTestBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public RestMockResponseTestBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public RestMockResponseTestBuilder body(final String body) {
        this.body = body;
        return this;
    }

    public RestMockResponseTestBuilder methodId(final String methodId) {
        this.methodId = methodId;
        return this;
    }

    public RestMockResponseTestBuilder httpStatusCode(final Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        return this;
    }

    public RestMockResponseTestBuilder status(final RestMockResponseStatus status) {
        this.status = status;
        return this;
    }

    public RestMockResponseTestBuilder usingExpressions(final Boolean usingExpressions) {
        this.usingExpressions = usingExpressions;
        return this;
    }

    public RestMockResponseTestBuilder httpHeaders(final List<HttpHeader> httpHeaders) {
        this.httpHeaders = httpHeaders;
        return this;
    }

    public RestMockResponseTestBuilder contentEncodings(final List<ContentEncoding> contentEncodings) {
        this.contentEncodings = contentEncodings;
        return this;
    }

    public RestMockResponseTestBuilder parameterQueries(final List<RestParameterQuery> parameterQueries) {
        this.parameterQueries = parameterQueries;
        return this;
    }

    public RestMockResponseTestBuilder xpathExpressions(final List<RestXPathExpression> xpathExpressions) {
        this.xpathExpressions = xpathExpressions;
        return this;
    }

    public RestMockResponseTestBuilder jsonPathExpressions(final List<RestJsonPathExpression> jsonPathExpressions) {
        this.jsonPathExpressions = jsonPathExpressions;
        return this;
    }

    public RestMockResponseTestBuilder headerQueries(final List<RestHeaderQuery> headerQueries) {
        this.headerQueries = headerQueries;
        return this;
    }

    public static RestMockResponseTestBuilder builder(){
        return new RestMockResponseTestBuilder();
    }

    public RestMockResponse build(){
        return RestMockResponse
                .builder()
                .id(id)
                .name(name)
                .body(body)
                .methodId(methodId)
                .status(status)
                .usingExpressions(usingExpressions)
                .httpStatusCode(httpStatusCode)
                .httpHeaders(httpHeaders)
                .contentEncodings(contentEncodings)
                .parameterQueries(parameterQueries)
                .xpathExpressions(xpathExpressions)
                .jsonPathExpressions(jsonPathExpressions)
                .headerQueries(headerQueries)
                .build();
    }

}
