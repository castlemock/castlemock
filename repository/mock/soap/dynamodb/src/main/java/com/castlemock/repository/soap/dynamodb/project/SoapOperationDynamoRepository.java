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
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.project.domain.*;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.dynamodb.DynamoRepository;
import com.castlemock.repository.soap.project.SoapOperationRepository;
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
public class SoapOperationDynamoRepository extends DynamoRepository<SoapOperationDynamoRepository.SoapOperationDocument, SoapOperation, String> implements SoapOperationRepository {

    @Autowired
    public SoapOperationDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public SoapOperationDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    @Override
    protected SoapOperationDocument mapToEntity(SoapOperation dto) {
        SoapOperationDocument entity = super.mapToEntity(dto);
        entity.nameLower = Optional.ofNullable(entity.name).map(String::toLowerCase).orElse(null);
        return entity;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to dynamodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to dynamodb, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             dynamodb.
     * @see #save
     */
    @Override
    protected void checkType(SoapOperationDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapOperation> search(SearchQuery query) {
        List<SoapOperationDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("nameLower", query.getQuery().toLowerCase(), ComparisonOperator.CONTAINS));
        return toDtoList(responses);
    }

    @Override
    public void deleteWithPortId(String portId) {
        List<SoapOperationDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("portId", portId, ComparisonOperator.EQ));
        dynamoDBMapper.batchDelete(responses);
    }


    @Override
    public List<SoapOperation> findWithPortId(String portId) {
        List<SoapOperationDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("portId", portId, ComparisonOperator.EQ));
        return toDtoList(responses);
    }

    /**
     * The method provides the functionality to find a SOAP operation with a specific name
     *
     * @param soapOperationName The name of the SOAP operation that should be retrieved
     * @return A SOAP operation that matches the search criteria. If no SOAP operation matches the provided
     * name then null will be returned.
     */
    @Override
    public SoapOperation findWithName(final String soapPortId,
                                      final String soapOperationName) {
        return dynamoDBMapper.scan(entityClass,
                new DynamoDBScanExpression().withConditionalOperator(ConditionalOperator.AND)
                        .withFilterConditionEntry("portId",
                                new Condition().withComparisonOperator(ComparisonOperator.EQ)
                                        .withAttributeValueList(new AttributeValue(soapPortId)))
                        .withFilterConditionEntry("name",
                                new Condition().withComparisonOperator(ComparisonOperator.EQ)
                                        .withAttributeValueList(new AttributeValue(soapOperationName))))
                .stream().findFirst().map(this::mapToDTO).orElse(null);
    }

    /**
     * Find a {@link SoapOperation} with a provided {@link HttpMethod}, {@link SoapVersion}
     * and an identifier.
     *
     * @param method              The HTTP method
     * @param version             The SOAP version
     * @param operationIdentifier The identifier
     * @return A {@link SoapOperation} that matches the provided search criteria.
     */
    @Override
    public SoapOperation findWithMethodAndVersionAndIdentifier(final String portId, final HttpMethod method,
                                                               final SoapVersion version,
                                                               final SoapOperationIdentifier operationIdentifier) {
        List<SoapOperationDocument> soapOperations = dynamoDBMapper.scan(entityClass,
                new DynamoDBScanExpression().withConditionalOperator(ConditionalOperator.AND)
                        .withFilterConditionEntry("portId",
                                new Condition().withComparisonOperator(ComparisonOperator.EQ)
                                        .withAttributeValueList(new AttributeValue(portId)))
                        .withFilterConditionEntry("httpMethod",
                                new Condition().withComparisonOperator(ComparisonOperator.EQ)
                                        .withAttributeValueList(new AttributeValue(method.toString())))
                        .withFilterConditionEntry("soapVersion",
                                new Condition().withComparisonOperator(ComparisonOperator.EQ)
                                        .withAttributeValueList(new AttributeValue(version.toString()))));

        for (SoapOperationDocument soapOperation : soapOperations) {
            final SoapOperationIdentifierDocument operationIdentifierFile =
                    soapOperation.getOperationIdentifier();

            if (operationIdentifier.getName().equalsIgnoreCase(operationIdentifierFile.getName())) {

                // Three ways to identify SOAP operation:
                // 1. Namespace is missing from the stored files (Legacy)
                // 2. The identify strategy is ELEMENT (Ignore namespace)
                // 3. Both the name and namespace is matching
                if (operationIdentifierFile.getNamespace() == null ||
                        soapOperation.getIdentifyStrategy() == SoapOperationIdentifyStrategy.ELEMENT ||
                        operationIdentifierFile.getNamespace().equalsIgnoreCase(operationIdentifier.getNamespace())) {
                    return this.mapper.map(soapOperation, SoapOperation.class);
                }
            }
        }
        return null;
    }

    /**
     * Updates the current response sequence index.
     *
     * @param soapOperationId The operation id.
     * @param index           The new response sequence index.
     */
    @Override
    public void setCurrentResponseSequenceIndex(final String soapOperationId, final Integer index) {
        Optional.of(dynamoDBMapper.load(entityClass, soapOperationId)).orElseThrow(
                () -> new IllegalArgumentException("Unable to find an operation with the following id: " + soapOperationId)
        ).setCurrentResponseSequenceIndex(index);
        //FIXME this will not persist by magic but ok...
    }

    /**
     * Retrieve the {@link SoapPort} id
     * for the {@link SoapOperation} with the provided id.
     *
     * @param operationId The id of the {@link SoapOperation}.
     * @return The id of the port.
     */
    @Override
    public String getPortId(String operationId) {
        return Optional.ofNullable(dynamoDBMapper.load(entityClass, operationId)).orElseThrow(
                () -> new IllegalArgumentException("Unable to find an operation with the following id: " + operationId)
        ).getPortId();
    }

    @DynamoDBTable(tableName = "soapOperation")
    @Getter
    @Setter
    public static class SoapOperationDocument implements Saveable<String> {

        @DynamoDBHashKey(attributeName = "id")
        @DynamoDBAutoGeneratedKey
        @Mapping("id")
        private String id;
        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "portId")
        @Mapping("portId")
        private String portId;
        @DynamoDBAttribute(attributeName = "responseStrategy")
        @DynamoDBTypeConvertedEnum
        @Mapping("responseStrategy")
        private SoapResponseStrategy responseStrategy;
        @DynamoDBAttribute(attributeName = "identifier")
        @Mapping("identifier")
        private String identifier;
        @DynamoDBAttribute(attributeName = "operationIdentifier")
        @Mapping("operationIdentifier")
        private SoapOperationIdentifierDocument operationIdentifier;
        @DynamoDBAttribute(attributeName = "status")
        @DynamoDBTypeConvertedEnum
        @Mapping("status")
        private SoapOperationStatus status;
        @DynamoDBAttribute(attributeName = "httpMethod")
        @DynamoDBTypeConvertedEnum
        @Mapping("httpMethod")
        private HttpMethod httpMethod;
        @DynamoDBAttribute(attributeName = "soapVersion")
        @DynamoDBTypeConvertedEnum
        @Mapping("soapVersion")
        private SoapVersion soapVersion;
        @DynamoDBAttribute(attributeName = "defaultBody")
        @Mapping("defaultBody")
        private String defaultBody;
        @DynamoDBAttribute(attributeName = "currentResponseSequenceIndex")
        @Mapping("currentResponseSequenceIndex")
        private Integer currentResponseSequenceIndex;
        @DynamoDBAttribute(attributeName = "forwardedEndpoint")
        @Mapping("forwardedEndpoint")
        private String forwardedEndpoint;
        @DynamoDBAttribute(attributeName = "originalEndpoint")
        @Mapping("originalEndpoint")
        private String originalEndpoint;
        @DynamoDBAttribute(attributeName = "defaultMockResponseId")
        @Mapping("defaultMockResponseId")
        private String defaultMockResponseId;
        @DynamoDBAttribute(attributeName = "simulateNetworkDelay")
        @Mapping("simulateNetworkDelay")
        private boolean simulateNetworkDelay;
        @DynamoDBAttribute(attributeName = "networkDelay")
        @Mapping("networkDelay")
        private long networkDelay;
        @DynamoDBAttribute(attributeName = "mockOnFailure")
        @Mapping("mockOnFailure")
        private boolean mockOnFailure;
        @DynamoDBAttribute(attributeName = "identifyStrategy")
        @DynamoDBTypeConvertedEnum
        @Mapping("identifyStrategy")
        private SoapOperationIdentifyStrategy identifyStrategy;

        @DynamoDBAttribute(attributeName = "nameLower")
        private String nameLower;
    }

    //@Document(collection = "soapOperationIdentifier")
    @DynamoDBDocument
    @Getter
    @Setter
    public static class SoapOperationIdentifierDocument {

        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "namespace")
        @Mapping("namespace")
        private String namespace;
    }
}


