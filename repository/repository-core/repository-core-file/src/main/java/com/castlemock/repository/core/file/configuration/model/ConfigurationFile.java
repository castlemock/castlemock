package com.castlemock.repository.core.file.configuration.model;

import com.castlemock.model.core.configuration.ConfigurationType;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "configuration")
public class ConfigurationFile {

    @Mapping("key")
    private String key;
    @Mapping("value")
    private String value;
    @Mapping("type")
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
    @XmlElement
    public String getKey() {
        return key;
    }

    /**
     * Sets the configuration key
     * @param key The new configuration key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Returns the value for the configuration
     * @return Configuration value
     */
    @XmlElement
    public String getValue() {
        return value;
    }

    /**
     * Sets the configuration value
     * @param value The new configuration value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns the configuration type
     * @return The configuration type
     */
    @XmlElement
    public ConfigurationType getType() {
        return type;
    }

    /**
     * Sets the configuration type
     * @param type The new configuration type
     */
    public void setType(ConfigurationType type) {
        this.type = type;
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