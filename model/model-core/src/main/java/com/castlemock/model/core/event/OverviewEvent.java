package com.castlemock.model.core.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = OverviewEvent.Builder.class)
public class OverviewEvent extends Event {

    @XmlElement
    private final String type;

    private OverviewEvent(final Builder builder){
        super(builder);
        this.type = Objects.requireNonNull(builder.type, "type");
    }

    public String getType() {
        return type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static <T extends Event> OverviewEvent.Builder toBuilder(final T other) {
        return new OverviewEvent.Builder()
                .id(other.id)
                .resourceName(other.getResourceName())
                .startDate(other.startDate)
                .endDate(other.endDate);

    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder extends Event.Builder<Builder> {

        private String type;

        private Builder() {
        }

        public Builder type(final String type) {
            this.type = type;
            return this;
        }

        public OverviewEvent build() {
            return new OverviewEvent(this);
        }
    }

}
