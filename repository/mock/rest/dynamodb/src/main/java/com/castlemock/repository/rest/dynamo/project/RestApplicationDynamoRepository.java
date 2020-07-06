/*
 * Copyright 2018 Karl Dahlgren
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
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.dynamodb.DynamoRepository;
import com.castlemock.repository.rest.project.RestApplicationRepository;
import lombok.Getter;
import lombok.Setter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Tiago Santos
 * @since 1.51
 */
@Repository
@Profile(Profiles.DYNAMODB)
public class RestApplicationDynamoRepository extends DynamoRepository<RestApplicationDynamoRepository.RestApplicationDocument, RestApplication, String> implements RestApplicationRepository {

    @Autowired
    public RestApplicationDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public RestApplicationDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    @Override
    protected RestApplicationDocument mapToEntity(RestApplication dto) {
        RestApplicationDocument entity = super.mapToEntity(dto);
        entity.nameLower = Optional.ofNullable(entity.name).map(String::toLowerCase).orElse(null);
        return entity;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to dynamodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to dynamodb, but also loaded from
     * dynamodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             dynamodb.
     * @see #save
     */
    @Override
    protected void checkType(RestApplicationDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestApplication> search(SearchQuery query) {
        List<RestApplicationDocument> operations =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("nameLower", query.getQuery().toLowerCase(), ComparisonOperator.CONTAINS));
        return toDtoList(operations);
    }

    /**
     * Delete all {@link RestApplication} that matches the provided
     * <code>projectId</code>.
     *
     * @param projectId The id of the project.
     */
    @Override
    public void deleteWithProjectId(String projectId) {
        List<RestApplicationDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("projectId", projectId, ComparisonOperator.EQ));
        dynamoDBMapper.batchDelete(responses);
    }

    /**
     * Find all {@link RestApplication} that matches the provided
     * <code>projectId</code>.
     *
     * @param projectId The id of the project.
     * @return A list of {@link RestApplication}.
     */
    @Override
    public List<RestApplication> findWithProjectId(String projectId) {
        List<RestApplicationDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("projectId", projectId, ComparisonOperator.EQ));
        return toDtoList(responses);
    }

    /**
     * Retrieve the {@link RestProject} id
     * for the {@link RestApplication} with the provided id.
     *
     * @param applicationId The id of the {@link RestApplication}.
     * @return The id of the project.
     */
    @Override
    public String getProjectId(String applicationId) {
        return Optional.ofNullable(dynamoDBMapper.load(entityClass, applicationId)).orElseThrow(
                () -> new IllegalArgumentException("Unable to find an application with the following id: " + applicationId)
        ).getProjectId();
    }

    //@Document(collection = "restApplication")
    @DynamoDBTable(tableName = "restApplication")
    @Getter
    @Setter
    public static class RestApplicationDocument implements Saveable<String> {

        @DynamoDBHashKey(attributeName = "id")
        @DynamoDBAutoGeneratedKey
        @Mapping("id")
        private String id;
        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "projectId")
        @Mapping("projectId")
        private String projectId;

        @DynamoDBAttribute(attributeName = "nameLower")
        private String nameLower;
    }
}
