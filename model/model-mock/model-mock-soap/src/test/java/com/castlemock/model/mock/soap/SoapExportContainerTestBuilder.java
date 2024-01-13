package com.castlemock.model.mock.soap;

import com.castlemock.model.mock.soap.domain.SoapMockResponseTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapPortTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapProjectTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapResourceTestBuilder;

import java.util.List;

public final class SoapExportContainerTestBuilder {

    private SoapExportContainerTestBuilder() {

    }

    public static SoapExportContainer.Builder builder() {
        return SoapExportContainer.builder()
                .project(SoapProjectTestBuilder.builder().build())
                .ports(List.of(SoapPortTestBuilder.builder().build()))
                .operations(List.of(SoapOperationTestBuilder.builder().build()))
                .mockResponses(List.of(SoapMockResponseTestBuilder.builder().build()))
                .resources(List.of(SoapResourceTestBuilder.builder().build()));
    }

    public static SoapExportContainer build() {
        return builder().build();
    }

}
