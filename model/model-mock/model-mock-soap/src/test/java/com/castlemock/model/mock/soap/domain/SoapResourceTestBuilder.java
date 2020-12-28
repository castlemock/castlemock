package com.castlemock.model.mock.soap.domain;

public class SoapResourceTestBuilder {

    private String id;
    private String name;
    private String projectId;
    private String content;
    private SoapResourceType type;

    private SoapResourceTestBuilder() {
        this.id = "SOAP RESOURCE";
        this.name = "Soap resource name";
        this.projectId = "Project id";
        this.type = SoapResourceType.WSDL;
        this.content = "";
    }

    public static SoapResourceTestBuilder builder(){
        return new SoapResourceTestBuilder();
    }

    public SoapResourceTestBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public SoapResourceTestBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public SoapResourceTestBuilder projectId(final String projectId) {
        this.projectId = projectId;
        return this;
    }

    public SoapResourceTestBuilder content(final String content) {
        this.content = content;
        return this;
    }

    public SoapResourceTestBuilder type(final SoapResourceType type) {
        this.type = type;
        return this;
    }

    public SoapResource build() {
        return SoapResource.builder()
                .content(content)
                .id(id)
                .name(name)
                .projectId(projectId)
                .type(type)
                .build();
    }

}
