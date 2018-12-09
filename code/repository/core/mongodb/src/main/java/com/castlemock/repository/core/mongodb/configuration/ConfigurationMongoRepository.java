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

package com.castlemock.repository.core.mongodb.configuration;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.configuration.domain.ConfigurationGroup;
import com.castlemock.core.basis.model.configuration.domain.ConfigurationType;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.configuration.ConfigurationRepository;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.google.common.base.Preconditions;
import org.dozer.Mapping;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The class is an implementation of mongodb repository and provides the functionality to interact with mongodb.
 * The repository is responsible for loading and saving configuration groups for mongodb.
 *
 * @author Mohammad Hewedy
 * @since 1.35
 */
@Repository
@Profile(Profiles.MONGODB)
public class ConfigurationMongoRepository extends MongoRepository<ConfigurationMongoRepository.ConfigurationGroupDocument, ConfigurationGroup, String> implements ConfigurationRepository {

    /**
     * The method is responsible for controller that the type that is about the be saved to mongodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to mongodb, but also loaded from the
     * mongodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param configurationGroup The instance of the type that will be checked and controlled before it is allowed to be saved on
     *                           mongodb.
     * @see #save
     * @see ConfigurationGroupDocument
     */
    @Override
    protected void checkType(final ConfigurationGroupDocument configurationGroup) {
        Preconditions.checkNotNull(configurationGroup, "Configuration group cannot be null");
        Preconditions.checkNotNull(configurationGroup.getName(), "Configuration group name cannot be null");
        Preconditions.checkNotNull(configurationGroup.getConfigurations(), "Configuration group configuration list cannot be null");
        Preconditions.checkArgument(!configurationGroup.getName().isEmpty(), "Configuration group name cannot be empty");

        for (ConfigurationDocument configuration : configurationGroup.getConfigurations()) {
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

    @Document(collection = "configuration")
    protected static class ConfigurationDocument {

        @Mapping("key")
        private String key;
        @Mapping("value")
        private String value;
        @Mapping("type")
        private ConfigurationType type;

        /**
         * Returns the identifier for the configuration
         *
         * @return The configuration key
         */
        public String getKey() {
            return key;
        }

        /**
         * Sets the configuration key
         *
         * @param key The new configuration key
         */
        public void setKey(String key) {
            this.key = key;
        }

        /**
         * Returns the value for the configuration
         *
         * @return Configuration value
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the configuration value
         *
         * @param value The new configuration value
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Returns the configuration type
         *
         * @return The configuration type
         */
        public ConfigurationType getType() {
            return type;
        }

        /**
         * Sets the configuration type
         *
         * @param type The new configuration type
         */
        public void setType(ConfigurationType type) {
            this.type = type;
        }
    }

    /**
     * The configuration group is responsible for grouping configurations together.
     *
     * @author Mohammad Hewedy
     * @since 1.35
     */
    @Document(collection = "configurationGroup")
    protected static class ConfigurationGroupDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("configurations")
        private List<ConfigurationDocument> configurations;

        /**
         * Returns the configuration group id
         *
         * @return The configuration group id
         */
        @Override
        public String getId() {
            return id;
        }

        /**
         * Sets a new id value for the configuration group
         *
         * @param id The new id for the configuration group
         */
        @Override
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Returns the name of the configuration group
         *
         * @return The new of the configuration group
         */
        public String getName() {
            return name;
        }

        /**
         * Sets a new name of the configuration group
         *
         * @param name The new name for the configuration group
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Returns a list of configurations the belongs to the group
         *
         * @return Configurations that belongs to the configuration group
         */
        public List<ConfigurationDocument> getConfigurations() {
            return configurations;
        }

        /**
         * Set a new list of configurations that belong to the configuration group
         *
         * @param configurations The new list of configurations
         */
        public void setConfigurations(List<ConfigurationDocument> configurations) {
            this.configurations = configurations;
        }

    }
}
