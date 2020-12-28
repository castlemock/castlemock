package com.castlemock.model.mock.rest.domain;

import com.castlemock.model.core.model.TypeIdentifier;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class RestProjectTestBuilder {

    private String id;
    private String name;
    private Date updated;
    private Date created;
    private String description;
    private TypeIdentifier typeIdentifier;
    private List<RestApplication> applications;

    private RestProjectTestBuilder() {
        this.id = "EqbLQU";
        this.name = "Swagger";
        this.created = Date.from(Instant.now());
        this.updated = Date.from(Instant.now());
        this.description = "Project generated from Swagger file";
        this.applications = new CopyOnWriteArrayList<RestApplication>();
        this.applications.add(RestApplicationTestBuilder.builder().build());
        this.typeIdentifier = new TypeIdentifier() {
            @Override
            public String getType() {
                return "REST";
            }

            @Override
            public String getTypeUrl() {
                return "rest";
            }
        };
    }

    public RestProjectTestBuilder applications(final List<RestApplication> applications) {
        this.applications = applications;
        return this;
    }

    public RestProjectTestBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public RestProjectTestBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public RestProjectTestBuilder updated(final Date updated) {
        this.updated = updated;
        return this;
    }

    public RestProjectTestBuilder created(final Date created) {
        this.created = created;
        return this;
    }

    public RestProjectTestBuilder description(final String description) {
        this.description = description;
        return this;
    }

    public RestProjectTestBuilder typeIdentifier(final TypeIdentifier typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
        return this;
    }

    public static RestProjectTestBuilder builder(){
        return new RestProjectTestBuilder();
    }

    public RestProject build() {
        return RestProject.builder()
                .id(id)
                .name(name)
                .created(created)
                .updated(updated)
                .description(description)
                .typeIdentifier(typeIdentifier)
                .applications(applications)
                .build();
    }
}
