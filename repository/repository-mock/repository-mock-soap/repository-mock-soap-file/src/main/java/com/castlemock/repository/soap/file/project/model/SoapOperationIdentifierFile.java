package com.castlemock.repository.soap.file.project.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "soapOperationIdentifier")
@XmlAccessorType(XmlAccessType.NONE)
public class SoapOperationIdentifierFile {

    @XmlElement
    private String name;
    @XmlElement
    private String namespace;

    private SoapOperationIdentifierFile(){

    }

    private SoapOperationIdentifierFile(final Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
        this.namespace = builder.namespace;
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
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