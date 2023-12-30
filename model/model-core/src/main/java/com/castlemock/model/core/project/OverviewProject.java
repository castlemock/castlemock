package com.castlemock.model.core.project;

import java.util.Objects;

public class OverviewProject extends Project{

    private String type;

    private OverviewProject() {
        super();
    }

    private OverviewProject(final Builder builder){
        super(builder);
        this.type = Objects.requireNonNull(builder.type, "type");
    }

    public String getType() {
        return type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static <T extends Project> Builder toBuilder(final T other) {
        return new Builder()
                .name(other.name)
                .id(other.id)
                .created(other.created)
                .updated(other.updated)
                .description(other.description);
    }

    public static final class Builder extends Project.Builder<Builder> {

        private String type;
        private Builder() {
        }

        public Builder type(final String type) {
            this.type = type;
            return this;
        }

        public OverviewProject build() {
            return new OverviewProject(this);
        }
    }

}
