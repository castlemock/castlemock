package com.castlemock.web.core.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = VersionResponse.Builder.class)
public class VersionResponse {

    private final String version;

    private VersionResponse(final Builder builder){
        this.version = Objects.requireNonNull(builder.version);
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VersionResponse that = (VersionResponse) o;
        return Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }

    @Override
    public String toString() {
        return "VersionResponse{" +
                "version='" + version + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        
        private String version;

        private Builder() {
        }


        public Builder version(final String version) {
            this.version = version;
            return this;
        }

        public VersionResponse build() {
            return new VersionResponse(this);
        }
    }
}
