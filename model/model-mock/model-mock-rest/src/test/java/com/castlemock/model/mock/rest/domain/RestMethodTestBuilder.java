package com.castlemock.model.mock.rest.domain;

import com.castlemock.model.core.http.HttpMethod;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class RestMethodTestBuilder {

    private String id;
    private String name;
    private String resourceId;
    private String defaultBody;
    private HttpMethod httpMethod;
    private String forwardedEndpoint;
    private RestMethodStatus status;
    private RestResponseStrategy responseStrategy;
    private Integer currentResponseSequenceIndex;
    private Boolean simulateNetworkDelay;
    private Long networkDelay;
    private String defaultQueryMockResponseId;
    private String defaultMockResponseId;
    private List<RestMockResponse> mockResponses;
    private String uri;
    private String defaultResponseName;

    private RestMethodTestBuilder(){
        this.id = "OSIFJL";
        this.name = "getUserByName";
        this.resourceId = "9gsIpq";
        this.defaultBody = "";
        this.httpMethod = HttpMethod.GET;
        this.forwardedEndpoint = "";
        this.status = RestMethodStatus.MOCKED;
        this.responseStrategy = RestResponseStrategy.RANDOM;
        this.currentResponseSequenceIndex = 0;
        this.simulateNetworkDelay = Boolean.FALSE;
        this.networkDelay = 0L;
        this.defaultQueryMockResponseId = "";
        this.defaultMockResponseId = "";
        this.mockResponses = new CopyOnWriteArrayList<RestMockResponse>();
        this.uri = "/";
        this.defaultResponseName = "";

        this.mockResponses.add(RestMockResponseTestBuilder.builder().build());
    }


    public RestMethodTestBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public RestMethodTestBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public RestMethodTestBuilder resourceId(final String resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public RestMethodTestBuilder defaultBody(final String defaultBody) {
        this.defaultBody = defaultBody;
        return this;
    }

    public RestMethodTestBuilder httpMethod(final HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public RestMethodTestBuilder forwardedEndpoint(final String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
        return this;
    }

    public RestMethodTestBuilder status(final RestMethodStatus status) {
        this.status = status;
        return this;
    }

    public RestMethodTestBuilder responseStrategy(final RestResponseStrategy responseStrategy) {
        this.responseStrategy = responseStrategy;
        return this;
    }

    public RestMethodTestBuilder currentResponseSequenceIndex(final Integer currentResponseSequenceIndex) {
        this.currentResponseSequenceIndex = currentResponseSequenceIndex;
        return this;
    }

    public RestMethodTestBuilder simulateNetworkDelay(final Boolean simulateNetworkDelay) {
        this.simulateNetworkDelay = simulateNetworkDelay;
        return this;
    }

    public RestMethodTestBuilder networkDelay(final Long networkDelay) {
        this.networkDelay = networkDelay;
        return this;
    }

    public RestMethodTestBuilder defaultQueryMockResponseId(final String defaultQueryMockResponseId) {
        this.defaultQueryMockResponseId = defaultQueryMockResponseId;
        return this;
    }

    public RestMethodTestBuilder defaultMockResponseId(final String defaultMockResponseId) {
        this.defaultMockResponseId = defaultMockResponseId;
        return this;
    }

    public RestMethodTestBuilder mockResponses(final List<RestMockResponse> mockResponses) {
        this.mockResponses = mockResponses;
        return this;
    }

    public RestMethodTestBuilder uri(final String uri) {
        this.uri = uri;
        return this;
    }

    public RestMethodTestBuilder defaultResponseName(final String defaultResponseName) {
        this.defaultResponseName = defaultResponseName;
        return this;
    }

    public static RestMethodTestBuilder builder(){
        return new RestMethodTestBuilder();
    }

    public RestMethod build() {
        return RestMethod.builder()
                .id(id)
                .name(name)
                .resourceId(resourceId)
                .defaultBody(defaultBody)
                .httpMethod(httpMethod)
                .forwardedEndpoint(forwardedEndpoint)
                .status(status)
                .responseStrategy(responseStrategy)
                .currentResponseSequenceIndex(currentResponseSequenceIndex)
                .simulateNetworkDelay(simulateNetworkDelay)
                .networkDelay(networkDelay)
                .defaultQueryMockResponseId(defaultQueryMockResponseId)
                .defaultMockResponseId(defaultMockResponseId)
                .mockResponses(mockResponses)
                .uri(uri)
                .defaultResponseName(defaultResponseName)
                .build();
    }
}
