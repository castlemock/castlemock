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
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLArgument;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttribute;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttributeType;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.dynamodb.DynamoRepository;
import com.castlemock.repository.graphql.project.GraphQLAttributeRepository;
import lombok.Getter;
import lombok.Setter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Tiago Santos
 * @since 1.51
 */
@Repository
@Profile(Profiles.DYNAMODB)
public class GraphQLAttributeDynamoRepository extends DynamoRepository<GraphQLAttributeDynamoRepository.GraphQLAttributeDocument, GraphQLAttribute, String> implements GraphQLAttributeRepository {

    @Autowired
    public GraphQLAttributeDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public GraphQLAttributeDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    @Override
    protected GraphQLAttributeDocument mapToEntity(GraphQLAttribute dto) {
        GraphQLAttributeDocument entity = super.mapToEntity(dto);
        entity.nameLower = Optional.ofNullable(entity.name).map(String::toLowerCase).orElse(null);
        return entity;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to dynamodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved dynamodb, but also loaded from the
     * dynamodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     */
    @Override
    protected void checkType(GraphQLAttributeDocument type) {

    }

    /**
     * Updates an instance that matches the provided id.
     *
     * @param id   The id of the instance that will be updated.
     * @param type The updated version that will replace the old one.
     * @return A copy of the replaced value.
     * @since 1.20
     */
    @Override
    public GraphQLAttribute update(final String id, final GraphQLAttribute type) {
        final GraphQLAttribute existing = this.findOne(id);
        existing.setValue(type.getValue());
        this.save(existing);
        return existing;
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLAttribute> search(SearchQuery query) {
        List<GraphQLAttributeDocument> operations =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("nameLower", query.getQuery().toLowerCase(), ComparisonOperator.CONTAINS));
        return toDtoList(operations);
    }

    @Override
    public List<GraphQLAttribute> findWithObjectTypeId(final String objectId) {
        List<GraphQLAttributeDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("objectTypeId", objectId, ComparisonOperator.EQ));
        return toDtoList(responses);
    }

    @Override
    public void deleteWithObjectTypeId(final String objectId) {
        List<GraphQLAttributeDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("objectTypeId", objectId, ComparisonOperator.EQ));
        dynamoDBMapper.batchDelete(responses);
    }

    @DynamoDBTable(tableName = "graphQLAttribute")
    @Getter
    @Setter
    public static class GraphQLAttributeDocument implements Saveable<String> {

        @DynamoDBHashKey(attributeName = "id")
        @Mapping("id")
        private String id;
        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "description")
        @Mapping("description")
        private String description;
        @DynamoDBAttribute(attributeName = "typeId")
        @Mapping("typeId")
        private String typeId;
        @DynamoDBAttribute(attributeName = "typeName")
        @Mapping("typeName")
        private String typeName;
        @DynamoDBAttribute(attributeName = "nullable")
        @Mapping("nullable")
        private Boolean nullable;
        @DynamoDBAttribute(attributeName = "listable")
        @Mapping("listable")
        private Boolean listable;
        @DynamoDBAttribute(attributeName = "objectTypeId")
        @Mapping("objectTypeId")
        private String objectTypeId;
        @DynamoDBAttribute(attributeName = "value")
        @Mapping("value")
        private String value;
        @DynamoDBAttribute(attributeName = "attributeType")
        @Mapping("attributeType")
        private GraphQLAttributeType attributeType;
        @DynamoDBAttribute(attributeName = "arguments")
        @Mapping("arguments")
        private List<GraphQLArgument> arguments = new CopyOnWriteArrayList<GraphQLArgument>();

        @DynamoDBAttribute(attributeName = "nameLower")
        private String nameLower;
    }
}
