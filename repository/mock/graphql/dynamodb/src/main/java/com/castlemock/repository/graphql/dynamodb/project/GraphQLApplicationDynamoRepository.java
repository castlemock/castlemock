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

package com.castlemock.repository.graphql.dynamodb.project;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLApplication;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLProject;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.dynamodb.DynamoRepository;
import com.castlemock.repository.graphql.project.GraphQLApplicationRepository;
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
public class GraphQLApplicationDynamoRepository extends DynamoRepository<GraphQLApplicationDynamoRepository.GraphQLApplicationDocument, GraphQLApplication, String> implements GraphQLApplicationRepository {

    @Autowired
    public GraphQLApplicationDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public GraphQLApplicationDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    @Override
    protected GraphQLApplicationDocument mapToEntity(GraphQLApplication dto) {
        GraphQLApplicationDocument entity = super.mapToEntity(dto);
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
    protected void checkType(GraphQLApplicationDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLApplication> search(SearchQuery query) {
        List<GraphQLApplicationDocument> operations =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("nameLower", query.getQuery().toLowerCase(), ComparisonOperator.CONTAINS));
        return toDtoList(operations);
    }

    @Override
    public List<GraphQLApplication> findWithProjectId(final String projectId) {
        List<GraphQLApplicationDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("projectId", projectId, ComparisonOperator.EQ));
        return toDtoList(responses);
    }

    @Override
    public void deleteWithProjectId(final String projectId) {
        List<GraphQLApplicationDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("projectId", projectId, ComparisonOperator.EQ));
        dynamoDBMapper.batchDelete(responses);
    }

    /**
     * Retrieve the {@link GraphQLProject} id
     * for the {@link GraphQLApplication} with the provided id.
     *
     * @param applicationId The id of the {@link GraphQLApplication}.
     * @return The id of the project.
     */
    @Override
    public String getProjectId(String applicationId) {
        return Optional.ofNullable(dynamoDBMapper.load(entityClass, applicationId)).orElseThrow(
                () -> new IllegalArgumentException("Unable to find an application with the following id: " + applicationId)
        ).getProjectId();
    }


    @DynamoDBTable(tableName = "graphQLApplication")
    @Getter
    @Setter
    public static class GraphQLApplicationDocument implements Saveable<String> {

        @DynamoDBHashKey(attributeName = "id")
        @Mapping("id")
        private String id;
        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "description")
        @Mapping("description")
        private String description;
        @DynamoDBAttribute(attributeName = "projectId")
        @Mapping("projectId")
        private String projectId;

        @DynamoDBAttribute(attributeName = "nameLower")
        private String nameLower;
    }
}
