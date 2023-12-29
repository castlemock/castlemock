package com.castlemock.model.core.project;

import java.util.Date;

public final class ProjectTestBuilder {

    private ProjectTestBuilder(){
    }

    public static ProjectTestBuilder.TestProject.Builder builder(){
        return ProjectTestBuilder.TestProject.builder()
                .id("SoapProject")
                .name("Project name")
                .description("Project description")
                .created(new Date())
                .updated(new Date());
    }

    public static ProjectTestBuilder.TestProject build() {
        return builder().build();
    }

    public static class TestProject extends Project {

        private TestProject(final ProjectTestBuilder.TestProject.Builder builder) {
            super(builder);
        }

        public static ProjectTestBuilder.TestProject.Builder builder() {
            return new ProjectTestBuilder.TestProject.Builder();
        }

        public static class Builder extends Project.Builder<ProjectTestBuilder.TestProject.Builder> {

            private Builder() {
            }

            public ProjectTestBuilder.TestProject build(){
                return new ProjectTestBuilder.TestProject(this);
            }
        }

    }

    
}
