package com.castlemock.model.mock.rest.domain;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public final class RestProjectTestBuilder {


    private RestProjectTestBuilder() {

    }

    public static RestProject.Builder builder(){
        return RestProject.builder()
                .id("EqbLQU")
                .name("Swagger")
                .created(Date.from(Instant.now()))
                .updated(Date.from(Instant.now()))
                .description("Project generated from Swagger file")
                .applications(List.of(RestApplicationTestBuilder.builder().build()));
    }

    public static RestProject build() {
        return builder().build();
    }
}
