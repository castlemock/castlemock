package com.castlemock.repository.core.file.configuration.model;

import com.castlemock.model.core.configuration.ConfigurationType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "configuration")
@XmlAccessorType(XmlAccessType.NONE)
public class ConfigurationFile {

    @XmlElement
    private String key;
    @XmlElement
    private String value;
    @XmlElement
    private ConfigurationType type;

    private ConfigurationFile() {

    }

    private ConfigurationFile(final Builder builder) {
        this.key = Objects.requireNonNull(builder.key, "key");
        this.value = Objects.requireNonNull(builder.value, "value");
        this.type = Objects.requireNonNull(builder.type, "type");

    }

    /**
     * Returns the identifier for the configuration
     * @return The configuration key
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the value for the configuration
     * @return Configuration value
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the configuration type
     * @return The configuration type
     */
    public ConfigurationType getType() {
        return type;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String key;
        private String value;
        private ConfigurationType type;

        private Builder() {
        }

        public Builder key(final String key) {
            this.key = key;
            return this;
        }

        public Builder value(final String value) {
            this.value = value;
            return this;
        }

        public Builder type(final ConfigurationType type) {
            this.type = type;
            return this;
        }

        public ConfigurationFile build() {
            return new ConfigurationFile(this);
        }
    }
}
