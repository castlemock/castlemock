package com.castlemock.model.mock.soap.domain;

public final class SoapOperationIdentifierTestBuilder {

    private SoapOperationIdentifierTestBuilder() {

    }

    public static SoapOperationIdentifier.Builder builder() {
        return SoapOperationIdentifier.builder()
                .name("identifier")
                .namespace(null);
    }

    public SoapOperationIdentifier build() {
        return builder().build();
    }

}
