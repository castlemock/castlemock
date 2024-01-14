package com.castlemock.model.mock.soap.domain;

public final class SoapOperationIdentifierTestBuilder {

    private SoapOperationIdentifierTestBuilder() {

    }

    public static SoapOperationIdentifier.Builder builder() {
        return SoapOperationIdentifier.builder()
                .name("identifier")
                .namespace(null);
    }

    public static SoapOperationIdentifier build() {
        return builder().build();
    }

}
