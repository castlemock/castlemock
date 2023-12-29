package com.castlemock.model.mock.rest.domain;

import java.util.Date;

public final class RestEventTestBuilder {

    private RestEventTestBuilder() {

    }

    public static RestEvent.Builder builder() {
        return RestEvent.builder()
                .endDate(new Date())
                .id("REST EVENT")
                .projectId("Project id")
                .request(RestRequestTestBuilder.builder().build())
                .resourceLink("Resource link")
                .response(RestResponseTestBuilder.builder().build())
                .applicationId("Application id")
                .resourceId("Resource id")
                .methodId("Method id")
                .startDate(new Date())
                .resourceName("Resource");
    }

    public static RestEvent build() {
        return builder().build();
    }

}
