package com.castlemock.core.mock.soap.model.event.domain;

import com.castlemock.core.basis.model.TypeIdentifier;

import java.util.Date;

public class SoapEventTestBuilder {

    private String id;
    private String resourceName;
    private Date startDate;
    private Date endDate;
    private TypeIdentifier typeIdentifier;
    private String resourceLink;
    private SoapRequest request;
    private SoapResponse response;
    private String projectId;
    private String portId;
    private String operationId;

    private SoapEventTestBuilder() {
        this.id = "SOAP EVENT";
        this.startDate = new Date();
        this.endDate = new Date();
        this.operationId = "OperationId";
        this.portId = "PortId";
        this.projectId = "Project id";
        this.resourceName = "resource name";
        this.resourceLink = "resourcelink";
        this.request = SoapRequestTestBuilder.builder().build();
        this.response = SoapResponseTestBuilder.builder().build();

        this.typeIdentifier = new TypeIdentifier() {
            @Override
            public String getType() {
                return "SOAP";
            }

            @Override
            public String getTypeUrl() {
                return "soap";
            }
        };
    }

    public static SoapEventTestBuilder builder() {
        return new SoapEventTestBuilder();
    }

    public SoapEventTestBuilder request(SoapRequest request) {
        this.request = request;
        return this;
    }

    public SoapEventTestBuilder response(SoapResponse response) {
        this.response = response;
        return this;
    }

    public SoapEventTestBuilder projectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public SoapEventTestBuilder id(String id) {
        this.id = id;
        return this;
    }

    public SoapEventTestBuilder portId(String portId) {
        this.portId = portId;
        return this;
    }

    public SoapEventTestBuilder resourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public SoapEventTestBuilder operationId(String operationId) {
        this.operationId = operationId;
        return this;
    }

    public SoapEventTestBuilder startDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public SoapEventTestBuilder endDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public SoapEventTestBuilder typeIdentifier(TypeIdentifier typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
        return this;
    }

    public SoapEventTestBuilder resourceLink(String resourceLink) {
        this.resourceLink = resourceLink;
        return this;
    }

    public SoapEvent build() {
        return SoapEvent.builder()
                .endDate(endDate)
                .id(id)
                .operationId(operationId)
                .portId(portId)
                .projectId(projectId)
                .request(request)
                .resourceLink(resourceLink)
                .response(response)
                .resourceName(resourceName)
                .startDate(startDate)
                .typeIdentifier(typeIdentifier)
                .build();
    }

}
