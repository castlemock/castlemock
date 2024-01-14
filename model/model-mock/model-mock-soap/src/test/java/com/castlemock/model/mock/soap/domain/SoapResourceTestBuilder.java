package com.castlemock.model.mock.soap.domain;

public class SoapResourceTestBuilder {

    private SoapResourceTestBuilder() {
    }

    public static SoapResource.Builder builder(){
        return SoapResource.builder()
                .content("Content")
                .id("SOAP RESOURCE")
                .name("Soap resource name")
                .projectId("Project id")
                .type(SoapResourceType.WSDL);
    }

    public static SoapResource build() {
        return builder().build();
    }

}
