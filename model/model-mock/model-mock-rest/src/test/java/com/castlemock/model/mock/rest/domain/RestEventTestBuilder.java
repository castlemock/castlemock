package com.castlemock.model.mock.rest.domain;

import com.castlemock.model.core.TypeIdentifier;

import java.util.Date;

public class RestEventTestBuilder {

    private String id;
    private Date startDate;
    private Date endDate;
    private TypeIdentifier typeIdentifier;
    private String resourceLink;
    private String resourceName;
    private RestRequest request;
    private RestResponse response;
    private String projectId;
    private String applicationId;
    private String resourceId;
    private String methodId;

    private RestEventTestBuilder() {
        this.id = "REST EVENT";
        this.startDate = new Date();
        this.endDate = new Date();
        this.projectId = "Project id";
        this.applicationId = "Application id";
        this.resourceId = "Resource id";
        this.methodId = "Method id";
        this.resourceName = "Resource";
        this.resourceLink = "Resource link";
        this.request = RestRequestTestBuilder.builder().build();
        this.response = RestResponseTestBuilder.builder().build();

        this.typeIdentifier = new TypeIdentifier() {
            @Override
            public String getType() {
                return "REST";
            }

            @Override
            public String getTypeUrl() {
                return "rest";
            }
        };
    }

    public static RestEventTestBuilder builder() {
        return new RestEventTestBuilder();
    }

    public RestEventTestBuilder request(RestRequest request) {
        this.request = request;
        return this;
    }

    public RestEventTestBuilder response(RestResponse response) {
        this.response = response;
        return this;
    }

    public RestEventTestBuilder projectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public RestEventTestBuilder id(String id) {
        this.id = id;
        return this;
    }

    public RestEventTestBuilder startDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public RestEventTestBuilder endDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public RestEventTestBuilder typeIdentifier(TypeIdentifier typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
        return this;
    }

    public RestEventTestBuilder resourceLink(String resourceLink) {
        this.resourceLink = resourceLink;
        return this;
    }

    public RestEvent build() {
        return RestEvent.builder()
                .endDate(endDate)
                .id(id)
                .projectId(projectId)
                .request(request)
                .resourceLink(resourceLink)
                .response(response)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .startDate(startDate)
                .typeIdentifier(typeIdentifier)
                .resourceName(resourceName)
                .build();
    }

}
