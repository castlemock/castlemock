package com.castlemock.repository.rest.file.project.model;

import com.castlemock.model.core.Saveable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "restResource")
@XmlAccessorType(XmlAccessType.NONE)
public class RestResourceFile implements Saveable<String> {

    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private String uri;
    @XmlElement
    private String applicationId;

    private RestResourceFile() {

    }

    private RestResourceFile(final Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.uri = Objects.requireNonNull(builder.uri, "uri");
        this.applicationId = Objects.requireNonNull(builder.applicationId, "applicationId");
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private String uri;
        private String applicationId;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder uri(final String uri) {
            this.uri = uri;
            return this;
        }

        public Builder applicationId(final String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public RestResourceFile build() {
            return new RestResourceFile(this);
        }
    }
}