/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.repository.core.file.configuration;

import com.castlemock.model.core.model.Saveable;
import com.castlemock.model.core.model.SearchQuery;
import com.castlemock.model.core.model.configuration.domain.ConfigurationGroup;
import com.castlemock.model.core.model.configuration.domain.ConfigurationType;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.configuration.ConfigurationRepository;
import com.castlemock.repository.core.file.FileRepository;
import com.google.common.base.Preconditions;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving configuration groups for the file system. Each configuration
 * group is stored as a separate file. The class also contains the directory and the filename extension for the
 * configuration group.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Repository
@Profile(Profiles.FILE)
public class ConfigurationFileRepository extends FileRepository<ConfigurationFileRepository.ConfigurationGroupFile, ConfigurationGroup, String> implements ConfigurationRepository {

    @Value(value = "${configuration.file.directory}")
    private String configurationFileDirectory;
    @Value(value = "${configuration.file.extension}")
    private String configurationFileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return configurationFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return configurationFileExtension;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param configurationGroup The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     * @see ConfigurationGroupFile
     */
    @Override
    protected void checkType(final ConfigurationGroupFile configurationGroup) {
        Preconditions.checkNotNull(configurationGroup, "Configuration group cannot be null");
        Preconditions.checkNotNull(configurationGroup.getId(), "Configuration group id cannot be null");
        Preconditions.checkNotNull(configurationGroup.getName(), "Configuration group name cannot be null");
        Preconditions.checkNotNull(configurationGroup.getConfigurations(), "Configuration group configuration list cannot be null");
        Preconditions.checkArgument(!configurationGroup.getName().isEmpty(), "Configuration group name cannot be empty");

        for(ConfigurationFile configuration : configurationGroup.getConfigurations()){
            Preconditions.checkNotNull(configuration.getType());
            Preconditions.checkNotNull(configuration.getKey());
            Preconditions.checkNotNull(configuration.getValue());

            Preconditions.checkArgument(!configuration.getKey().isEmpty(), "Configuration key cannot be empty");
            Preconditions.checkArgument(!configuration.getValue().isEmpty(), "Configuration value cannot be empty");
        }
    }

    @Override
    public List<ConfigurationGroup> search(SearchQuery query) {
        throw new UnsupportedOperationException("Search method is not supported in the Configuration repository");
    }

    @XmlRootElement(name = "configuration")
    protected static class ConfigurationFile {

        @Mapping("key")
        private String key;
        @Mapping("value")
        private String value;
        @Mapping("type")
        private ConfigurationType type;

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
    }

    /**
     * The configuration group is responsible for grouping configurations together.
     * @author Karl Dahlgren
     * @since 1.0
     */
    @XmlRootElement(name = "configurationGroup")
    protected static class ConfigurationGroupFile implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("configurations")
        private List<ConfigurationFile> configurations;

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
        public void setId(String id) {
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
        public void setName(String name) {
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
        public void setConfigurations(List<ConfigurationFile> configurations) {
            this.configurations = configurations;
        }

    }


}
