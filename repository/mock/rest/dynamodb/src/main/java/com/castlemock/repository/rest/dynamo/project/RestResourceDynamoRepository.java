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
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.dynamodb.DynamoRepository;
import com.castlemock.repository.rest.project.RestResourceRepository;
import lombok.Getter;
import lombok.Setter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Tiago Santos
 * @since 1.51
 */
@Repository
@Profile(Profiles.DYNAMODB)
public class RestResourceDynamoRepository extends DynamoRepository<RestResourceDynamoRepository.RestResourceDocument, RestResource, String> implements RestResourceRepository {

    @Autowired
    public RestResourceDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public RestResourceDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    @Override
    protected RestResourceDocument mapToEntity(RestResource dto) {
        RestResourceDocument entity = super.mapToEntity(dto);
        entity.uriLower = Optional.ofNullable(entity.uri).map(String::toLowerCase).orElse(null);
        entity.nameLower = Optional.ofNullable(entity.name).map(String::toLowerCase).orElse(null);
        return entity;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to mongodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to mongodb, but also loaded from
     * mongodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             mongodb.
     * @see #save
     */
    @Override
    protected void checkType(RestResourceDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestResource> search(SearchQuery query) {
        List<RestResourceDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("nameLower", query.getQuery().toLowerCase(), ComparisonOperator.CONTAINS));
        return toDtoList(responses);
    }

    /**
     * Delete all {@link RestResource} that matches the provided
     * <code>applicationId</code>.
     *
     * @param applicationId The id of the applicationId.
     */
    @Override
    public void deleteWithApplicationId(String applicationId) {
        List<RestResourceDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("applicationId", applicationId, ComparisonOperator.EQ));
        dynamoDBMapper.batchDelete(responses);
    }

    /**
     * Find all {@link RestResource} that matches the provided
     * <code>applicationId</code>.
     *
     * @param applicationId The id of the applicationId.
     * @return A list of {@link RestResource}.
     */
    @Override
    public List<RestResource> findWithApplicationId(String applicationId) {
        List<RestResourceDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("applicationId", applicationId, ComparisonOperator.EQ));
        return toDtoList(responses);
    }

    /**
     * Find all {@link RestResource} ids that matches the provided
     * <code>applicationId</code>.
     *
     * @param applicationId The id of the applicationId.
     * @return A list of {@link RestResource} ids.
     * @since 1.20
     */
    @Override
    public List<String> findIdsWithApplicationId(String applicationId) {
        return findWithApplicationId(applicationId)
                .stream()
                .map(RestResource::getId)
                .collect(Collectors.toList());
    }

    /**
     * Finds a {@link RestResource} with a URI
     *
     * @param applicationId The id of the {@link RestApplication}
     * @param resourceUri   The URI of a {@link RestResource}
     * @return A {@link RestResource} that matches the search criteria.
     * @see RestProject
     * @see RestApplication
     * @see RestResource
     */
    @Override
    public RestResource findRestResourceByUri(String applicationId, String resourceUri) {
        return dynamoDBMapper.scan(entityClass,
                new DynamoDBScanExpression().withConditionalOperator(ConditionalOperator.AND)
                        .withFilterConditionEntry("applicationId",
                                new Condition().withComparisonOperator(ComparisonOperator.EQ)
                                        .withAttributeValueList(new AttributeValue(applicationId)))
                        .withFilterConditionEntry("uriLower",
                                new Condition().withComparisonOperator(ComparisonOperator.EQ)
                                        .withAttributeValueList(new AttributeValue(resourceUri.toLowerCase()))))
                .stream().findFirst().map(this::mapToDTO).orElse(null);
    }

    /**
     * Retrieve the {@link RestApplication} id
     * for the {@link RestResource} with the provided id.
     *
     * @param resourceId The id of the {@link RestResource}.
     * @return The id of the application.
     * @since 1.20
     */
    @Override
    public String getApplicationId(String resourceId) {
        return Optional.ofNullable(dynamoDBMapper.load(entityClass, resourceId)).orElseThrow(
                () -> new IllegalArgumentException("Unable to find a resource with the following id: " + resourceId)
        ).getApplicationId();
    }

    //@Document(collection = "restResource")
    @DynamoDBTable(tableName = "restResource")
    @Getter
    @Setter
    public static class RestResourceDocument implements Saveable<String> {

        @DynamoDBHashKey(attributeName = "id")
        @DynamoDBAutoGeneratedKey
        @Mapping("id")
        private String id;
        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "uri")
        @Mapping("uri")
        private String uri;
        @DynamoDBAttribute(attributeName = "applicationId")
        @Mapping("applicationId")
        private String applicationId;

        @DynamoDBAttribute(attributeName = "uriLower")
        private String uriLower;

        @DynamoDBAttribute(attributeName = "nameLower")
        private String nameLower;
    }
}
