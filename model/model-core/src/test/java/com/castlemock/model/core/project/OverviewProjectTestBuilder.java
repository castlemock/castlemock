package com.castlemock.model.core.project;

import java.util.Date;

public final class OverviewProjectTestBuilder {

    private OverviewProjectTestBuilder(){
    }

    public static OverviewProject.Builder builder(){
        return OverviewProject.builder()
                .id("SoapProject")
                .name("Project name")
                .description("Project description")
                .created(new Date())
                .updated(new Date())
                .type("test");
    }

    public static OverviewProject build() {
        return builder().build();
    }

}
