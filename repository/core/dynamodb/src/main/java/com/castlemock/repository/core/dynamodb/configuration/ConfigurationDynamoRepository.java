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

package com.castlemock.repository.core.dynamodb.configuration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.configuration.domain.ConfigurationGroup;
import com.castlemock.core.basis.model.configuration.domain.ConfigurationType;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.configuration.ConfigurationRepository;
import com.castlemock.repository.core.dynamodb.DynamoRepository;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The class is an implementation of dynamodb repository and provides the functionality to interact with dynamodb.
 * The repository is responsible for loading and saving configuration groups for dynamodb.
 *
 * @author Tiago Santos
 * @since 1.51
 */
@Repository
@Profile(Profiles.DYNAMODB)
public class ConfigurationDynamoRepository extends DynamoRepository<ConfigurationDynamoRepository.ConfigurationGroupDocument, ConfigurationGroup, String> implements ConfigurationRepository {

    @Autowired
    public ConfigurationDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB,
                                         DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public ConfigurationDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to dynamodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to dynamodb, but also loaded from the
     * dynamodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param configurationGroup The instance of the type that will be checked and controlled before it is allowed to be saved on
     *                           dynamodb.
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

    @DynamoDBDocument
    @Getter
    @Setter
    public static class ConfigurationDocument {
        @DynamoDBAttribute(attributeName = "key")
        @Mapping("key")
        private String key;
        @DynamoDBAttribute(attributeName = "value")
        @Mapping("value")
        private String value;
        @DynamoDBAttribute(attributeName = "type")
        @DynamoDBTypeConvertedEnum
        @Mapping("type")
        private ConfigurationType type;
    }

    /**
     * The configuration group is responsible for grouping configurations together.
     *
     * @author Mohammad Hewedy
     * @since 1.35
     */
    @DynamoDBTable(tableName = "configuration")
    @Getter
    @Setter
    public static class ConfigurationGroupDocument implements Saveable<String> {

        @DynamoDBHashKey(attributeName = "id")
        @DynamoDBAutoGeneratedKey
        @Mapping("id")
        private String id;
        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "configurations")
        @Mapping("configurations")
        private List<ConfigurationDocument> configurations;
    }
}
