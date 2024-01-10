package com.castlemock.model.core.project;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = OverviewProject.Builder.class)
public class OverviewProject extends Project{

    @XmlElement
    private final String type;

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

    @JsonPOJOBuilder(withPrefix = "")
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
