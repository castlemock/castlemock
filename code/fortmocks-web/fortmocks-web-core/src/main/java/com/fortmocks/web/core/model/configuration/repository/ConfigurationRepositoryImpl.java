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

package com.fortmocks.web.core.model.configuration.repository;

import com.fortmocks.core.model.configuration.Configuration;
import com.fortmocks.core.model.configuration.ConfigurationGroup;
import com.fortmocks.core.model.configuration.repository.ConfigurationRepository;
import com.fortmocks.core.model.user.repository.UserRepository;
import com.fortmocks.web.core.model.RepositoryImpl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving configuration groups for the file system. Each configuration
 * group is stored as a separate file. The class also contains the directory and the filename extension for the
 * configuration group.
 * @author Karl Dahlgren
 * @since 1.0
 * @see UserRepository
 * @see RepositoryImpl
 * @see ConfigurationGroup
 */
@Repository
public class ConfigurationRepositoryImpl extends RepositoryImpl<ConfigurationGroup, Long> implements ConfigurationRepository {

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
     * @see ConfigurationGroup
     */
    @Override
    protected void checkType(final ConfigurationGroup configurationGroup) {
        Preconditions.checkNotNull(configurationGroup, "Configuration group cannot be null");
        Preconditions.checkNotNull(configurationGroup.getId(), "Configuration group id cannot be null");
        Preconditions.checkNotNull(configurationGroup.getName(), "Configuration group name cannot be null");
        Preconditions.checkNotNull(configurationGroup.getConfigurations(), "Configuration group configuration list cannot be null");
        Preconditions.checkArgument(!configurationGroup.getName().isEmpty(), "Configuration group name cannot be empty");

        for(Configuration configuration : configurationGroup.getConfigurations()){
            Preconditions.checkNotNull(configuration.getConfigurationType());
            Preconditions.checkNotNull(configuration.getKey());
            Preconditions.checkNotNull(configuration.getValue());

            Preconditions.checkArgument(!configuration.getKey().isEmpty(), "Configuration key cannot be empty");
            Preconditions.checkArgument(!configuration.getValue().isEmpty(), "Configuration value cannot be empty");
        }
    }
}
