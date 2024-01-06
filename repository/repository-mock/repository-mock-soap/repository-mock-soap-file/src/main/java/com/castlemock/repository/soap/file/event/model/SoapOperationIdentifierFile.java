package com.castlemock.repository.soap.file.event.model;

import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "soapOperationIdentifier")
public class SoapOperationIdentifierFile {

    @Mapping("name")
    private String name;
    @Mapping("namespace")
    private String namespace;

    private SoapOperationIdentifierFile() {

    }

    private SoapOperationIdentifierFile(final Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
        this.namespace = builder.namespace;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String namespace;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder namespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public SoapOperationIdentifierFile build() {
            return new SoapOperationIdentifierFile(this);
        }
    }
}