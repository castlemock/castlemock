package com.castlemock.core.mock.soap.model.project.domain;

import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SoapMockResponseTestBuilder {

    private String id;
    private String name;
    private String body;
    private String operationId;
    private SoapMockResponseStatus status;
    private Integer httpStatusCode;
    private Boolean usingExpressions;
    private String xpathExpression;
    private List<HttpHeader> httpHeaders;
    private List<ContentEncoding> contentEncodings;
    private List<SoapXPathExpression> xpathExpressions;

    private SoapMockResponseTestBuilder() {
        this.id = "SOAP MOCK RESPONSE";
        this.name = "Soap mock response name";
        this.body = "Soap mock response body";
        this.operationId = "OperationId";
        this.status = SoapMockResponseStatus.ENABLED;
        this.httpStatusCode = 200;
        this.usingExpressions = Boolean.TRUE;
        this.httpHeaders = ImmutableList.of();
        this.contentEncodings = ImmutableList.of();
        this.xpathExpressions = ImmutableList.of();

        this.httpHeaders = new CopyOnWriteArrayList<HttpHeader>();
        this.contentEncodings = new CopyOnWriteArrayList<ContentEncoding>();
        this.xpathExpressions = new CopyOnWriteArrayList<SoapXPathExpression>();

        this.xpathExpressions.add(SoapXPathExpression.builder()
                .expression("//Request/Name[text()='Input1']")
                .build());
        this.xpathExpressions.add(SoapXPathExpression.builder()
                .expression("//Request/Name[text()='Input2']")
                .build());
    }

    public static SoapMockResponseTestBuilder builder(){
        return new SoapMockResponseTestBuilder();
    }

    public SoapMockResponseTestBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public SoapMockResponseTestBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public SoapMockResponseTestBuilder body(final String body) {
        this.body = body;
        return this;
    }

    public SoapMockResponseTestBuilder operationId(final String operationId) {
        this.operationId = operationId;
        return this;
    }

    public SoapMockResponseTestBuilder status(final SoapMockResponseStatus status) {
        this.status = status;
        return this;
    }

    public SoapMockResponseTestBuilder httpStatusCode(final Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        return this;
    }

    public SoapMockResponseTestBuilder usingExpressions(final Boolean usingExpressions) {
        this.usingExpressions = usingExpressions;
        return this;
    }

    public SoapMockResponseTestBuilder xpathExpression(final String xpathExpression) {
        this.xpathExpression = xpathExpression;
        return this;
    }

    public SoapMockResponseTestBuilder httpHeaders(final List<HttpHeader> httpHeaders) {
        this.httpHeaders = httpHeaders;
        return this;
    }

    public SoapMockResponseTestBuilder contentEncodings(final List<ContentEncoding> contentEncodings) {
        this.contentEncodings = contentEncodings;
        return this;
    }

    public SoapMockResponseTestBuilder xpathExpressions(final List<SoapXPathExpression> xpathExpressions) {
        this.xpathExpressions = xpathExpressions;
        return this;
    }

    public SoapMockResponse build() {
        return SoapMockResponse.builder()
                .body(body)
                .contentEncodings(contentEncodings)
                .httpHeaders(httpHeaders)
                .httpStatusCode(httpStatusCode)
                .id(id)
                .name(name)
                .operationId(operationId)
                .status(status)
                .usingExpressions(usingExpressions)
                .xpathExpression(xpathExpression)
                .xpathExpressions(xpathExpressions)
                .build();
    }

}
