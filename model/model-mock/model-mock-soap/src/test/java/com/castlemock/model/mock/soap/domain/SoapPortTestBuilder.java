package com.castlemock.model.mock.soap.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public final class SoapPortTestBuilder {

    private String id;
    private String name;
    private String uri;
    private String projectId;
    private String invokeAddress;
    private List<SoapOperation> operations;
    private Map<SoapOperationStatus, Integer> statusCount;

    private SoapPortTestBuilder() {
        this.id = "SOAP PORT";
        this.name = "Soap port name";
        this.uri = "UrlPath";
        this.projectId = "SOAP PROJECT";
        this.invokeAddress = "soapproject";
        this.operations = new CopyOnWriteArrayList<>();
        this.statusCount = new HashMap<>();
    }

    public static SoapPortTestBuilder builder(){
        return new SoapPortTestBuilder();
    }

    public SoapPortTestBuilder id(String id) {
        this.id = id;
        return this;
    }

    public SoapPortTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SoapPortTestBuilder uri(String uri) {
        this.uri = uri;
        return this;
    }

    public SoapPortTestBuilder projectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public SoapPortTestBuilder operations(List<SoapOperation> operations) {
        this.operations = operations;
        return this;
    }

    public SoapPortTestBuilder invokeAddress(String invokeAddress) {
        this.invokeAddress = invokeAddress;
        return this;
    }

    public SoapPortTestBuilder statusCount(Map<SoapOperationStatus, Integer> statusCount) {
        this.statusCount = statusCount;
        return this;
    }

    public SoapPort build() {
        return SoapPort.builder()
                .id(id)
                .invokeAddress(invokeAddress)
                .name(name)
                .operations(operations)
                .projectId(projectId)
                .statusCount(statusCount)
                .uri(uri)
                .build();
    }

}
