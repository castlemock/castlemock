package com.castlemock.model.mock.soap.domain;

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.http.HttpMethod;
import com.google.common.collect.ImmutableList;

import java.util.List;

public final class SoapRequestTestBuilder {

    private String body;
    private String envelope;
    private String contentType;
    private String uri;
    private HttpMethod httpMethod;
    private String operationName;
    private SoapVersion soapVersion;
    private List<HttpHeader> httpHeaders;
    private SoapOperationIdentifier operationIdentifier;

    private SoapRequestTestBuilder() {
        this.body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:web=\"http://www.castlemock.com/\">\n" +
                "   <soap:Header/>\n" +
                "   <soap:Body>\n" +
                "      <web:ServiceName>\n" +
                "         <web:value>Input</web:value>\n" +
                "      </web:ServiceName>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>";
        this.envelope = this.body;
        this.contentType = "application/json";
        this.operationName = "ServiceName";
        this.httpMethod = HttpMethod.POST;
        this.soapVersion = SoapVersion.SOAP11;
        this.httpHeaders = ImmutableList.of();
        this.uri = "";
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
    public SoapRequestTestBuilder envelope(String envelope) {
        this.envelope = envelope;
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
                .envelope(envelope)
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
