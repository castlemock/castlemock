package com.castlemock.model.mock.soap.domain;

import java.util.Date;

public final class SoapEventTestBuilder {

    private SoapEventTestBuilder() {

    }

    public static SoapEvent.Builder builder() {
        return SoapEvent.builder()
                .endDate(new Date())
                .id("SOAP EVENT")
                .operationId("OperationId")
                .portId("PortId")
                .projectId("Project id")
                .request(SoapRequestTestBuilder.builder().build())
                .response(SoapResponseTestBuilder.builder().build())
                .resourceName("resource name")
                .startDate(new Date());
    }

    public static SoapEvent build() {
        return builder().build();
    }

}
