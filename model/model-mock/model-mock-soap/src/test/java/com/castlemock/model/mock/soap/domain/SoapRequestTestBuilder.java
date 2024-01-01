package com.castlemock.model.mock.soap.domain;

import com.castlemock.model.core.http.HttpMethod;

import java.util.List;

public final class SoapRequestTestBuilder {

    public static SoapRequest.Builder builder(){
        final String body = """
                <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:web="http://www.castlemock.com/">
                   <soap:Header/>
                   <soap:Body>
                      <web:ServiceName>
                         <web:value>Input</web:value>
                      </web:ServiceName>
                   </soap:Body>
                </soap:Envelope>""";

        return SoapRequest.builder()
                .body(body)
                .envelope(body)
                .contentType("application/json")
                .httpHeaders(List.of())
                .httpMethod(HttpMethod.POST)
                .operationIdentifier(SoapOperationIdentifier.builder()
                        .name("")
                        .namespace("")
                        .build())
                .operationName("ServiceName")
                .soapVersion(SoapVersion.SOAP11)
                .uri("uri");
    }


    public SoapRequest build() {
        return builder().build();
    }
}
