package com.castlemock.web.core.model.project;

import java.util.Objects;
import java.util.Optional;

public class UpdateProjectRequest {

    private String name;
    private String description;

    private UpdateProjectRequest(){

    }

    private UpdateProjectRequest(final Builder builder){
        this.name = Objects.requireNonNull(builder.name);
        this.description = builder.description;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateProjectRequest that = (UpdateProjectRequest) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "CreateProjectRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String description;

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

        public UpdateProjectRequest build() {
            return new UpdateProjectRequest(this);
        }
    }
}
