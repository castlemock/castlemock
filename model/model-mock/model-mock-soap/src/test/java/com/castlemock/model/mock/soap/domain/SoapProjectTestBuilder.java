package com.castlemock.model.mock.soap.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class SoapProjectTestBuilder {

    private SoapProjectTestBuilder() {

    }

    public static SoapProject.Builder builder(){
        return SoapProject.builder()
                .created(new Date())
                .description("Project description")
                .id("SOAP PROJECT")
                .name("Project name")
                .ports(new CopyOnWriteArrayList<>())
                .resources(new CopyOnWriteArrayList<>())
                .statusCount(new HashMap<>())
                .updated(new Date());
    }

    public static SoapProject build() {
        return builder().build();
    }

}
