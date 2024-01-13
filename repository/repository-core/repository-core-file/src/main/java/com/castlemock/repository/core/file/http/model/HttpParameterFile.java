package com.castlemock.repository.core.file.http.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "httpParameter")
@XmlAccessorType(XmlAccessType.NONE)
public class HttpParameterFile {

    @XmlElement
    private String name;
    @XmlElement
    private String value;

    private HttpParameterFile() {

    }

    private HttpParameterFile(final Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
        this.value = Objects.requireNonNull(builder.value, "value");
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String value;

        private Builder() {
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder value(final String value) {
            this.value = value;
            return this;
        }

        public HttpParameterFile build() {
            return new HttpParameterFile(this);
        }
    }
}