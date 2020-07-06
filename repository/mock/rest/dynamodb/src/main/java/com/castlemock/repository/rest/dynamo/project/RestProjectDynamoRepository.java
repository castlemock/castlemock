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

package com.castlemock.repository.rest.dynamo.project;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.dynamodb.project.AbstractProjectDynamoRepository;
import com.castlemock.repository.rest.project.RestProjectRepository;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The class is an implementation of mongo repository and provides the functionality to interact with dynamodb.
 * The repository is responsible for loading and saving REST project from dynamodb.
 *
 * @author Tiago Santos
 * @see RestProjectRepository
 * @since 1.51
 */
@Repository
@Profile(Profiles.DYNAMODB)
public class RestProjectDynamoRepository extends AbstractProjectDynamoRepository<RestProjectDynamoRepository.RestProjectDocument, RestProject> implements RestProjectRepository {

    @Autowired
    public RestProjectDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public RestProjectDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful.
     * <p>
     * The method is responsible to validate the imported types and make certain that all the collections are
     * initialized.
     *
     * @see #initialize
     */
    @Override
    protected void postInitiate() {

    }


    /**
     * The method is responsible for controller that the type that is about the be saved to dynamodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to dynamodb, but also loaded from
     * dynamodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param restProject The instance of the type that will be checked and controlled before it is allowed to be saved on
     *                    dynamodb.
     * @see #save
     */
    @Override
    protected void checkType(RestProjectDocument restProject) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestProject> search(SearchQuery query) {
        List<RestProjectDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("nameLower", query.getQuery().toLowerCase(), ComparisonOperator.CONTAINS));
        return toDtoList(responses);
    }

    /**
     * Finds a {@link RestProject} with a provided REST project name.
     *
     * @param restProjectName The name of the REST project that will be retrieved.
     * @return A {@link RestProject} that matches the provided name.
     * @see RestProject
     */
    @Override
    public RestProject findRestProjectWithName(final String restProjectName) {
        Preconditions.checkNotNull(restProjectName, "Project name cannot be null");
        Preconditions.checkArgument(!restProjectName.isEmpty(), "Project name cannot be empty");

        return dynamoDBMapper.scan(entityClass,
                getAttributeScan("nameLower", restProjectName.toLowerCase(), ComparisonOperator.EQ))
                .stream().findFirst().map(this::mapToDTO).orElse(null);
    }

    @Override
    protected RestProjectDocument mapToEntity(RestProject dto) {
        RestProjectDocument entity = super.mapToEntity(dto);
        entity.nameLower = Optional.of(entity.getName()).map(String::toLowerCase).orElse(null);
        return entity;
    }

    //@Document(collection = "restProject")
    @DynamoDBTable(tableName = "restProject")
    @Getter
    @Setter
    public static class RestProjectDocument extends AbstractProjectDynamoRepository.ProjectDocument {

        @DynamoDBAttribute(attributeName = "nameLower")
        protected String nameLower;
    }
}
