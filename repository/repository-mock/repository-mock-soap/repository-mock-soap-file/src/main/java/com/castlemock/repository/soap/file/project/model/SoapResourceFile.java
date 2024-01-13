package com.castlemock.repository.soap.file.project.model;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.mock.soap.domain.SoapResourceType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "soapResource")
@XmlAccessorType(XmlAccessType.NONE)
public class SoapResourceFile implements Saveable<String> {

    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private String projectId;
    @XmlElement
    private SoapResourceType type;

    private SoapResourceFile() {

    }

    private SoapResourceFile(final Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.type = Objects.requireNonNull(builder.type, "type");
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SoapResourceType getType() {
        return type;
    }

    public String getProjectId() {
        return projectId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private String projectId;
        private SoapResourceType type;

        private Builder() {
        }


        public Builder id(final String id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder projectId(final String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder type(final SoapResourceType type) {
            this.type = type;
            return this;
        }

        public SoapResourceFile build() {
            return new SoapResourceFile(this);
        }
    }
}