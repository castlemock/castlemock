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
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.rest.model.project.domain.*;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.dynamodb.DynamoRepository;
import com.castlemock.repository.rest.project.RestMethodRepository;
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
public class RestMethodDynamoRepository extends DynamoRepository<RestMethodDynamoRepository.RestMethodDocument, RestMethod, String> implements RestMethodRepository {

    @Autowired
    public RestMethodDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public RestMethodDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    @Override
    protected RestMethodDocument mapToEntity(RestMethod dto) {
        RestMethodDocument entity = super.mapToEntity(dto);
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
    protected void checkType(RestMethodDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestMethod> search(SearchQuery query) {
        List<RestMethodDocument> operations =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("nameLower", query.getQuery().toLowerCase(), ComparisonOperator.CONTAINS));
        return toDtoList(operations);
    }


    /**
     * Updates the current response sequence index.
     *
     * @param restMethodId The method id.
     * @param index        The new response sequence index.
     * @since 1.17
     */
    @Override
    public void setCurrentResponseSequenceIndex(final String restMethodId,
                                                final Integer index) {
        Optional.of(dynamoDBMapper.load(entityClass, restMethodId)).orElseThrow(
                () -> new IllegalArgumentException("Unable to find a method with the following id: " + restMethodId)
        ).setCurrentResponseSequenceIndex(index);
        //FIXME this will not persist by magic but ok...
    }

    /**
     * Delete all {@link RestMethod} that matches the provided
     * <code>resourceId</code>.
     *
     * @param resourceId The id of the resource.
     */
    @Override
    public void deleteWithResourceId(String resourceId) {
        List<RestMethodDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("resourceId", resourceId, ComparisonOperator.EQ));
        dynamoDBMapper.batchDelete(responses);
    }

    /**
     * Find all {@link RestMethod} that matches the provided
     * <code>resourceId</code>.
     *
     * @param resourceId The id of the resource.
     * @return A list of {@link RestMethod}.
     * @since 1.20
     */
    @Override
    public List<RestMethod> findWithResourceId(String resourceId) {
        List<RestMethodDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("resourceId", resourceId, ComparisonOperator.EQ));
        return toDtoList(responses);
    }

    /**
     * Find all {@link RestMethod} ids that matches the provided
     * <code>resourceId</code>.
     *
     * @param resourceId The id of the resource.
     * @return A list of {@link RestMethod} ids.
     * @since 1.20
     */
    @Override
    public List<String> findIdsWithResourceId(String resourceId) {
        return findWithResourceId(resourceId)
                .stream()
                .map(RestMethod::getId)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve the {@link RestResource} id
     * for the {@link RestMethod} with the provided id.
     *
     * @param methodId The id of the {@link RestMethod}.
     * @return The id of the resource.
     * @since 1.20
     */
    @Override
    public String getResourceId(String methodId) {
        return Optional.ofNullable(dynamoDBMapper.load(entityClass, methodId)).orElseThrow(
                () -> new IllegalArgumentException("Unable to find a method with the following id: " + methodId)
        ).getResourceId();
    }

    //@Document(collection = "restMethod")
    @DynamoDBTable(tableName = "restMethod")
    @Getter
    @Setter
    public static class RestMethodDocument implements Saveable<String> {

        @DynamoDBHashKey(attributeName = "id")
        @DynamoDBAutoGeneratedKey
        @Mapping("id")
        private String id;
        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "resourceId")
        @Mapping("resourceId")
        private String resourceId;
        @DynamoDBAttribute(attributeName = "defaultBody")
        @Mapping("defaultBody")
        private String defaultBody;
        @DynamoDBAttribute(attributeName = "httpMethod")
        @DynamoDBTypeConvertedEnum
        @Mapping("httpMethod")
        private HttpMethod httpMethod;
        @DynamoDBAttribute(attributeName = "forwardedEndpoint")
        @Mapping("forwardedEndpoint")
        private String forwardedEndpoint;
        @DynamoDBAttribute(attributeName = "status")
        @DynamoDBTypeConvertedEnum
        @Mapping("status")
        private RestMethodStatus status;
        @DynamoDBAttribute(attributeName = "responseStrategy")
        @DynamoDBTypeConvertedEnum
        @Mapping("responseStrategy")
        private RestResponseStrategy responseStrategy;
        @DynamoDBAttribute(attributeName = "currentResponseSequenceIndex")
        @Mapping("currentResponseSequenceIndex")
        private Integer currentResponseSequenceIndex;
        @DynamoDBAttribute(attributeName = "simulateNetworkDelay")
        @Mapping("simulateNetworkDelay")
        private boolean simulateNetworkDelay;
        @DynamoDBAttribute(attributeName = "networkDelay")
        @Mapping("networkDelay")
        private long networkDelay;
        @DynamoDBAttribute(attributeName = "defaultQueryMockResponseId")
        @Mapping("defaultQueryMockResponseId")
        private String defaultMockResponseId;

        @DynamoDBAttribute(attributeName = "nameLower")
        private String nameLower;
    }
}
