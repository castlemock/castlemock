package com.castlemock.repository.soap.file.project.model;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.mock.soap.domain.SoapResourceType;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "soapResource")
public class SoapResourceFile implements Saveable<String> {

    @Mapping("id")
    private String id;
    @Mapping("name")
    private String name;
    @Mapping("projectId")
    private String projectId;
    @Mapping("type")
    private SoapResourceType type;

    private SoapResourceFile() {

    }

    private SoapResourceFile(final Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.type = Objects.requireNonNull(builder.type, "type");
    }

    @XmlElement
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public SoapResourceType getType() {
        return type;
    }

    public void setType(SoapResourceType type) {
        this.type = type;
    }

    @XmlElement
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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


        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder type(SoapResourceType type) {
            this.type = type;
            return this;
        }

        public SoapResourceFile build() {
            return new SoapResourceFile(this);
        }
    }
}