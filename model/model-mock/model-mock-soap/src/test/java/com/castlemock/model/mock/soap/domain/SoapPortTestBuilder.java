package com.castlemock.model.mock.soap.domain;

import java.util.List;
import java.util.Map;

public final class SoapPortTestBuilder {


    private SoapPortTestBuilder() {

    }

    public static SoapPort.Builder builder(){
        return SoapPort.builder()
                .id("SOAP PORT")
                .invokeAddress("soapproject")
                .name("Soap port name")
                .operations(List.of())
                .projectId("SOAP PROJECT")
                .statusCount(Map.of())
                .uri( "UrlPath");
    }

    public static SoapPort build() {
        return builder().build();
    }

}
