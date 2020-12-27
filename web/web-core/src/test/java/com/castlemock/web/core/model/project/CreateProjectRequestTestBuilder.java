package com.castlemock.web.core.model.project;

public final class CreateProjectRequestTestBuilder {

    private CreateProjectRequestTestBuilder(){

    }

    public static CreateProjectRequest.Builder builder() {
        return CreateProjectRequest.builder()
                .projectType("soap")
                .name("Project name")
                .description("Project description");
    }

}
