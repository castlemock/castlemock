package com.castlemock.model.mock.rest.domain;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class RestProjectTestBuilder {


    private RestProjectTestBuilder() {

    }

    public static RestProject.Builder builder(){
        final List<RestApplication> applications = new CopyOnWriteArrayList<>();
        applications.add(RestApplicationTestBuilder.builder().build());

        return RestProject.builder()
                .id("EqbLQU")
                .name("Swagger")
                .created(Date.from(Instant.now()))
                .updated(Date.from(Instant.now()))
                .description("Project generated from Swagger file")
                .applications(applications);
    }

    public static RestProject build() {
        return builder().build();
    }
}
