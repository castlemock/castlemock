package com.castlemock.web.core.model.project;

public final class UpdateProjectRequestTestBuilder {

    private UpdateProjectRequestTestBuilder(){

    }

    public static UpdateProjectRequest.Builder builder() {
        return UpdateProjectRequest.builder()
                .name("Project name")
                .description("Project description");
    }

}
