package com.castlemock.core.mock.soap.model.event.domain;

import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationIdentifier;
import com.castlemock.core.mock.soap.model.project.domain.SoapVersion;
import com.google.common.collect.ImmutableList;

import java.util.List;

public final class SoapRequestTestBuilder {

    private String body;
    private String contentType;
    private String uri;
    private HttpMethod httpMethod;
    private String operationName;
    private SoapVersion soapVersion;
    private List<HttpHeader> httpHeaders;
    private SoapOperationIdentifier operationIdentifier;

    private SoapRequestTestBuilder() {
        this.body = "Soap request body";
        this.contentType = "application/json";
        this.operationName = "ServiceName";
        this.httpMethod = HttpMethod.POST;
        this.soapVersion = SoapVersion.SOAP11;
        this.httpHeaders = ImmutableList.of();
        this.operationIdentifier = SoapOperationIdentifier.builder()
                .name("")
                .namespace("")
                .build();
    }

    public static SoapRequestTestBuilder builder(){
        return new SoapRequestTestBuilder();
    }

    public SoapRequestTestBuilder body(String body) {
        this.body = body;
        return this;
    }

    public SoapRequestTestBuilder contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public SoapRequestTestBuilder uri(String uri) {
        this.uri = uri;
        return this;
    }

    public SoapRequestTestBuilder httpMethod(final HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public SoapRequestTestBuilder operationName(final String operationName) {
        this.operationName = operationName;
        return this;
    }

    public SoapRequestTestBuilder soapVersion(final SoapVersion soapVersion) {
        this.soapVersion = soapVersion;
        return this;
    }

    public SoapRequestTestBuilder httpHeaders(final List<HttpHeader> httpHeaders) {
        this.httpHeaders = httpHeaders;
        return this;
    }

    public SoapRequestTestBuilder operationIdentifier(final SoapOperationIdentifier operationIdentifier) {
        this.operationIdentifier = operationIdentifier;
        return this;
    }

    public SoapRequest build() {
        return SoapRequest.builder()
                .body(body)
                .contentType(contentType)
                .httpHeaders(httpHeaders)
                .httpMethod(httpMethod)
                .operationIdentifier(operationIdentifier)
                .operationName(operationName)
                .soapVersion(soapVersion)
                .uri(uri)
                .build();
    }
}
