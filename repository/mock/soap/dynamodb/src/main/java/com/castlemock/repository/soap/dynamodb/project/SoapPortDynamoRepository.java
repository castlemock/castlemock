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


package com.castlemock.repository.soap.dynamodb.project;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.dynamodb.DynamoRepository;
import com.castlemock.repository.soap.project.SoapPortRepository;
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
public class SoapPortDynamoRepository extends DynamoRepository<SoapPortDynamoRepository.SoapPortDocument, SoapPort, String> implements SoapPortRepository {

    @Autowired
    public SoapPortDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public SoapPortDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    @Override
    protected SoapPortDocument mapToEntity(SoapPort dto) {
        SoapPortDocument entity = super.mapToEntity(dto);
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
    protected void checkType(SoapPortDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapPort> search(SearchQuery query) {
        List<SoapPortDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("nameLower", query.getQuery().toLowerCase(), ComparisonOperator.CONTAINS));
        return toDtoList(responses);
    }

    @Override
    public void deleteWithProjectId(String projectId) {
        List<SoapPortDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("projectId", projectId, ComparisonOperator.EQ));
        dynamoDBMapper.batchDelete(responses);
    }

    @Override
    public List<SoapPort> findWithProjectId(String projectId) {
        List<SoapPortDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("projectId", projectId, ComparisonOperator.EQ));
        return toDtoList(responses);
    }

    /**
     * The method finds a {@link SoapPort} with the provided name
     *
     * @param soapPortName The name of the {@link SoapPort}
     * @return A {@link SoapPort} that matches the provided search criteria.
     */
    @Override
    public SoapPort findWithName(final String projectId, final String soapPortName) {
        return dynamoDBMapper.scan(entityClass,
                new DynamoDBScanExpression().withConditionalOperator(ConditionalOperator.AND)
                        .withFilterConditionEntry("projectId",
                                new Condition().withComparisonOperator(ComparisonOperator.EQ)
                                        .withAttributeValueList(new AttributeValue(projectId)))
                        .withFilterConditionEntry("name",
                                new Condition().withComparisonOperator(ComparisonOperator.EQ)
                                        .withAttributeValueList(new AttributeValue(soapPortName))))
                .stream().findFirst().map(this::mapToDTO).orElse(null);
    }

    /**
     * The method finds a {@link SoapPort} with the provided uri
     *
     * @param projectId
     * @param uri       The uri used by the {@link SoapPort}
     * @return A {@link SoapPort} that matches the provided search criteria.
     */
    @Override
    public SoapPort findWithUri(String projectId, String uri) {
        return dynamoDBMapper.scan(entityClass,
                new DynamoDBScanExpression().withConditionalOperator(ConditionalOperator.AND)
                        .withFilterConditionEntry("projectId",
                                new Condition().withComparisonOperator(ComparisonOperator.EQ)
                                        .withAttributeValueList(new AttributeValue(projectId)))
                        .withFilterConditionEntry("uri",
                                new Condition().withComparisonOperator(ComparisonOperator.EQ)
                                        .withAttributeValueList(new AttributeValue(uri))))
                .stream().findFirst().map(this::mapToDTO).orElse(null);
    }

    /**
     * Retrieve the {@link SoapProject} id
     * for the {@link SoapPort} with the provided id.
     *
     * @param portId The id of the {@link SoapPort}.
     * @return The id of the project.
     */
    @Override
    public String getProjectId(String portId) {
        return Optional.ofNullable(dynamoDBMapper.load(entityClass, portId)).orElseThrow(
                () -> new IllegalArgumentException("Unable to find a port with the following id: " + portId)
        ).getProjectId();
    }

    @DynamoDBTable(tableName = "soapPort")
    @Getter
    @Setter
    protected static class SoapPortDocument implements Saveable<String> {

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
        @DynamoDBAttribute(attributeName = "projectId")
        @Mapping("projectId")
        private String projectId;

        @DynamoDBAttribute(attributeName = "nameLower")
        private String nameLower;
    }
}
