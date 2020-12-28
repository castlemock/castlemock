package com.castlemock.model.mock.soap.domain;

import com.castlemock.model.core.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class SoapOperationTestBuilder {

    private String id;
    private String name;
    private String identifier;
    private SoapOperationIdentifier operationIdentifier;
    private SoapResponseStrategy responseStrategy;
    private SoapOperationStatus status;
    private HttpMethod httpMethod;
    private SoapVersion soapVersion;
    private String defaultBody;
    private Integer currentResponseSequenceIndex;
    private String forwardedEndpoint;
    private String originalEndpoint;
    private Boolean simulateNetworkDelay;
    private Long networkDelay;
    private String defaultXPathMockResponseId;
    private String defaultMockResponseId;
    private String portId;
    private Boolean mockOnFailure;
    private SoapOperationIdentifyStrategy identifyStrategy;
    private List<SoapMockResponse> mockResponses;
    private String invokeAddress;
    private String defaultResponseName;

    private SoapOperationTestBuilder() {
        this.id = "SOAP OPERATION";
        this.name = "Soap operation name";
        this.currentResponseSequenceIndex = 0;
        this.defaultBody = "Default body";
        this.forwardedEndpoint = "Forwarded event";
        this.invokeAddress = "Invoke address";
        this.originalEndpoint = "Original endpoint";
        this.identifier = "soapoperation";
        this.simulateNetworkDelay = Boolean.FALSE;
        this.mockOnFailure = Boolean.FALSE;
        this.networkDelay = 1000L;
        this.portId = "port id";
        this.httpMethod = HttpMethod.POST;
        this.status = SoapOperationStatus.MOCKED;
        this.soapVersion = SoapVersion.SOAP11;
        this.responseStrategy = SoapResponseStrategy.SEQUENCE;
        this.identifyStrategy = SoapOperationIdentifyStrategy.ELEMENT_NAMESPACE;
        this.mockResponses = new ArrayList<SoapMockResponse>();
        this.mockResponses = new CopyOnWriteArrayList<SoapMockResponse>();
        this.operationIdentifier = SoapOperationIdentifierTestBuilder.builder().build();
    }

    public static SoapOperationTestBuilder builder(){
        return new SoapOperationTestBuilder();
    }

    public SoapOperationTestBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public SoapOperationTestBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public SoapOperationTestBuilder identifier(final String identifier) {
        this.identifier = identifier;
        return this;
    }

    public SoapOperationTestBuilder operationIdentifier(final SoapOperationIdentifier operationIdentifier) {
        this.operationIdentifier = operationIdentifier;
        return this;
    }

    public SoapOperationTestBuilder responseStrategy(final SoapResponseStrategy responseStrategy) {
        this.responseStrategy = responseStrategy;
        return this;
    }

    public SoapOperationTestBuilder status(final SoapOperationStatus status) {
        this.status = status;
        return this;
    }

    public SoapOperationTestBuilder httpMethod(final HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public SoapOperationTestBuilder soapVersion(final SoapVersion soapVersion) {
        this.soapVersion = soapVersion;
        return this;
    }

    public SoapOperationTestBuilder defaultBody(final String defaultBody) {
        this.defaultBody = defaultBody;
        return this;
    }

    public SoapOperationTestBuilder currentResponseSequenceIndex(final Integer currentResponseSequenceIndex) {
        this.currentResponseSequenceIndex = currentResponseSequenceIndex;
        return this;
    }

    public SoapOperationTestBuilder forwardedEndpoint(final String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
        return this;
    }

    public SoapOperationTestBuilder originalEndpoint(final String originalEndpoint) {
        this.originalEndpoint = originalEndpoint;
        return this;
    }

    public SoapOperationTestBuilder simulateNetworkDelay(final Boolean simulateNetworkDelay) {
        this.simulateNetworkDelay = simulateNetworkDelay;
        return this;
    }

    public SoapOperationTestBuilder networkDelay(final Long networkDelay) {
        this.networkDelay = networkDelay;
        return this;
    }

    public SoapOperationTestBuilder defaultXPathMockResponseId(final String defaultXPathMockResponseId) {
        this.defaultXPathMockResponseId = defaultXPathMockResponseId;
        return this;
    }

    public SoapOperationTestBuilder defaultMockResponseId(final String defaultMockResponseId) {
        this.defaultMockResponseId = defaultMockResponseId;
        return this;
    }

    public SoapOperationTestBuilder portId(final String portId) {
        this.portId = portId;
        return this;
    }

    public SoapOperationTestBuilder mockOnFailure(final Boolean mockOnFailure) {
        this.mockOnFailure = mockOnFailure;
        return this;
    }

    public SoapOperationTestBuilder identifyStrategy(final SoapOperationIdentifyStrategy identifyStrategy) {
        this.identifyStrategy = identifyStrategy;
        return this;
    }

    public SoapOperationTestBuilder mockResponses(final List<SoapMockResponse> mockResponses) {
        this.mockResponses = mockResponses;
        return this;
    }

    public SoapOperationTestBuilder invokeAddress(final String invokeAddress) {
        this.invokeAddress = invokeAddress;
        return this;
    }

    public SoapOperationTestBuilder defaultResponseName(final String defaultResponseName) {
        this.defaultResponseName = defaultResponseName;
        return this;
    }

    public SoapOperation build() {
        return SoapOperation.builder()
                .currentResponseSequenceIndex(currentResponseSequenceIndex)
                .defaultBody(defaultBody)
                .defaultMockResponseId(defaultMockResponseId)
                .defaultResponseName(defaultResponseName)
                .defaultXPathMockResponseId(defaultXPathMockResponseId)
                .forwardedEndpoint(forwardedEndpoint)
                .httpMethod(httpMethod)
                .id(id)
                .identifier(identifier)
                .identifyStrategy(identifyStrategy)
                .invokeAddress(invokeAddress)
                .mockOnFailure(mockOnFailure)
                .mockResponses(mockResponses)
                .name(name)
                .networkDelay(networkDelay)
                .operationIdentifier(operationIdentifier)
                .originalEndpoint(originalEndpoint)
                .portId(portId)
                .responseStrategy(responseStrategy)
                .simulateNetworkDelay(simulateNetworkDelay)
                .soapVersion(soapVersion)
                .status(status)
                .build();

    }

}
