package com.castlemock.core.mock.rest.model.project.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public final class RestResourceTestBuilder {

    private String id;
    private String name;
    private String uri;
    private String applicationId;
    private String invokeAddress;
    private List<RestMethod> methods;
    private Map<RestMethodStatus, Integer> statusCount;

    private RestResourceTestBuilder(){
        this.id = "9gsIpq";
        this.name = "/user/{username}";
        this.uri = "/user/{username}";
        this.applicationId = "QLP2Nk";
        this.invokeAddress = "http://castlemock.herokuapp.com:80/mock/rest/project/EqbLQU/application/QLP2Nk/user/{username}";
        this.methods = new CopyOnWriteArrayList<RestMethod>();
        this.statusCount = new HashMap<RestMethodStatus, Integer>();

        this.methods.add(RestMethodTestBuilder.builder().build());
    }

    public RestResourceTestBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public RestResourceTestBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public RestResourceTestBuilder uri(final String uri) {
        this.uri = uri;
        return this;
    }

    public RestResourceTestBuilder applicationId(final String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public RestResourceTestBuilder invokeAddress(final String invokeAddress) {
        this.invokeAddress = invokeAddress;
        return this;
    }

    public RestResourceTestBuilder methods(final List<RestMethod> methods) {
        this.methods = methods;
        return this;
    }

    public RestResourceTestBuilder statusCount(final Map<RestMethodStatus, Integer> statusCount) {
        this.statusCount = statusCount;
        return this;
    }

    public static RestResourceTestBuilder builder(){
        return new RestResourceTestBuilder();
    }

    public RestResource build() {
        return RestResource
                .builder()
                .id(id)
                .name(name)
                .uri(uri)
                .applicationId(applicationId)
                .invokeAddress(invokeAddress)
                .methods(methods)
                .statusCount(statusCount)
                .build();
    }
}
