package com.castlemock.model.mock.rest.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public final class RestApplicationTestBuilder {

    private String id;
    private String name;
    private String projectId;
    private List<RestResource> resources;
    private Map<RestMethodStatus, Integer> statusCount;

    private RestApplicationTestBuilder() {
        this.id = "QLP2Nk";
        this.name = "Swagger Petstore";
        this.projectId = "EqbLQU";
        this.resources = new CopyOnWriteArrayList<>();
        this.statusCount = new HashMap<>();

        this.resources.add(RestResourceTestBuilder.builder().build());
    }

    public RestApplicationTestBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public RestApplicationTestBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public RestApplicationTestBuilder projectId(final String projectId) {
        this.projectId = projectId;
        return this;
    }

    public RestApplicationTestBuilder resources(final List<RestResource> resources) {
        this.resources = resources;
        return this;
    }

    public RestApplicationTestBuilder statusCount(final Map<RestMethodStatus, Integer> statusCount) {
        this.statusCount = statusCount;
        return this;
    }

    public static RestApplicationTestBuilder builder(){
        return new RestApplicationTestBuilder();
    }

    public RestApplication build() {
        return RestApplication.builder()
                .id(id)
                .name(name)
                .projectId(projectId)
                .resources(resources)
                .statusCount(statusCount)
                .build();
    }
}
