package com.castlemock.repository.core.file.configuration.model;

import com.castlemock.model.core.Saveable;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * The configuration group is responsible for grouping configurations together.
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement(name = "configurationGroup")
public class ConfigurationGroupFile implements Saveable<String> {

    @Mapping("id")
    private String id;
    @Mapping("name")
    private String name;
    @Mapping("configurations")
    private List<ConfigurationFile> configurations;

    private ConfigurationGroupFile() {

    }

    private ConfigurationGroupFile(final Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.configurations = Objects.requireNonNull(builder.configurations, "configurations");
    }


    /**
     * Returns the configuration group id
     * @return The configuration group id
     */
    @XmlElement
    @Override
    public String getId() {
        return id;
    }

    /**
     * Sets a new id value for the configuration group
     * @param id The new id for the configuration group
     */
    @Override
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Returns the name of the configuration group
     * @return The new of the configuration group
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * Sets a new name of the configuration group
     * @param name The new name for the configuration group
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns a list of configurations the belongs to the group
     * @return Configurations that belongs to the configuration group
     */
    @XmlElementWrapper(name = "configurations")
    @XmlElement(name = "configuration")
    public List<ConfigurationFile> getConfigurations() {
        return configurations;
    }

    /**
     * Set a new list of configurations that belong to the configuration group
     * @param configurations The new list of configurations
     */
    public void setConfigurations(final List<ConfigurationFile> configurations) {
        this.configurations = configurations;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private List<ConfigurationFile> configurations;

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

        public Builder configurations(final List<ConfigurationFile> configurations) {
            this.configurations = configurations;
            return this;
        }

        public ConfigurationGroupFile build() {
            return new ConfigurationGroupFile(this);
        }
    }

}