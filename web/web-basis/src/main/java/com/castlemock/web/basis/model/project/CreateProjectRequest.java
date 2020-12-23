package com.castlemock.web.basis.model.project;

import java.util.Objects;

public class CreateProjectRequest {

    private String name;
    private String description;
    private String projectType;

    private CreateProjectRequest(){

    }

    private CreateProjectRequest(final Builder builder){
        this.name = Objects.requireNonNull(builder.name);
        this.description = Objects.requireNonNull(builder.description);
        this.projectType = Objects.requireNonNull(builder.projectType);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getProjectType() {
        return projectType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateProjectRequest that = (CreateProjectRequest) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(projectType, that.projectType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, projectType);
    }

    @Override
    public String toString() {
        return "CreateProjectRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", projectType='" + projectType + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String description;
        private String projectType;

        private Builder() {
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public Builder projectType(final String projectType) {
            this.projectType = projectType;
            return this;
        }

        public CreateProjectRequest build() {
            return new CreateProjectRequest(this);
        }
    }
}
