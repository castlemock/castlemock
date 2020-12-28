package com.castlemock.model.mock.soap.domain;

public final class SoapOperationIdentifierTestBuilder {

    private String name;
    private String namespace;

    private SoapOperationIdentifierTestBuilder() {
        this.name = "";
        this.namespace = "";
    }

    public static SoapOperationIdentifierTestBuilder builder(){
        return new SoapOperationIdentifierTestBuilder();
    }

    public SoapOperationIdentifierTestBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public SoapOperationIdentifierTestBuilder namespace(final String namespace) {
        this.namespace = namespace;
        return this;
    }

    public SoapOperationIdentifier build() {
        return SoapOperationIdentifier.builder()
                .name(name)
                .namespace(namespace)
                .build();
    }

}
