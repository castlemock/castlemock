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
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.domain.SoapResource;
import com.castlemock.core.mock.soap.model.project.domain.SoapResourceType;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.dynamodb.DynamoRepository;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Tiago Santos
 * @since 1.51
 */
@Repository
@Profile(Profiles.DYNAMODB)
public class SoapResourceDynamoRepository extends DynamoRepository<SoapResourceDynamoRepository.SoapResourceDocument, SoapResource, String> implements SoapResourceRepository {

    @Autowired
    public SoapResourceDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public SoapResourceDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    @Override
    protected SoapResourceDocument mapToEntity(SoapResource dto) {
        SoapResourceDocument entity = super.mapToEntity(dto);
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
    protected void checkType(SoapResourceDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapResource> search(SearchQuery query) {
        List<SoapResourceDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("nameLower", query.getQuery().toLowerCase(), ComparisonOperator.CONTAINS));
        return toDtoList(responses);
    }

    @Override
    public void deleteWithProjectId(String projectId) {
        List<SoapResourceDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("projectId", projectId, ComparisonOperator.EQ));
        dynamoDBMapper.batchDelete(responses);
    }

    @Override
    public List<SoapResource> findWithProjectId(String projectId) {
        List<SoapResourceDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("projectId", projectId, ComparisonOperator.EQ));
        return toDtoList(responses);
    }

    /**
     * The method loads a resource that matching the search criteria and returns the result
     *
     * @param soapResourceId The id of the resource that will be loaded
     * @return Returns the loaded resource and returns it as a String.
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP operation was found
     * @see SoapResource
     */
    @Override
    public String loadSoapResource(String soapResourceId) {
        Preconditions.checkNotNull(soapResourceId, "Resource id cannot be null");

        return Optional.of(dynamoDBMapper.load(entityClass, soapResourceId)).orElseThrow(
                () -> new IllegalArgumentException("Unable to find a SOAP resource with id " + soapResourceId)
        ).getContent();
    }

    /**
     * The method adds a new {@link SoapResource}.
     *
     * @param soapResource The  instance of {@link SoapResource} that will be saved.
     * @param resource     The raw resource
     * @return The saved {@link SoapResource}
     * @see SoapResource
     */
    @Override
    public SoapResource saveSoapResource(SoapResource soapResource, String resource) {
        soapResource.setContent(resource);
        return save(soapResource);
    }

    /**
     * The method returns a list of {@link SoapResource} that matches the
     * search criteria.
     *
     * @param soapProjectId The id of the project.
     * @param types         The types of {@link SoapResource} that should be returned.
     * @return A list of {@link SoapResource} of the specific provided type.
     * All resources will be returned if the type is null.
     */
    @Override
    public Collection<SoapResource> findSoapResources(final String soapProjectId, final SoapResourceType... types) {
        Preconditions.checkNotNull(soapProjectId, "Project id cannot be null");

        List<SoapResourceDocument> resources =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("projectId", soapProjectId, ComparisonOperator.EQ));

        final List<SoapResource> soapResources = new ArrayList<>();
        for (SoapResourceDocument soapResourceDocument : resources) {
            for (SoapResourceType type : types) {
                if (type.equals(soapResourceDocument.getType())) {
                    SoapResource soapResource = mapToDTO(soapResourceDocument);
                    soapResources.add(soapResource);
                }
            }
        }
        return soapResources;
    }

    /**
     * Retrieve the {@link SoapProject} id
     * for the {@link SoapResource} with the provided id.
     *
     * @param resourceId The id of the {@link SoapResource}.
     * @return The id of the project.
     */
    @Override
    public String getProjectId(String resourceId) {
        return Optional.ofNullable(dynamoDBMapper.load(entityClass, resourceId)).orElseThrow(
                () -> new IllegalArgumentException("Unable to find a resource with the following id: " + resourceId)
        ).getProjectId();
    }

    @DynamoDBTable(tableName = "soapResource")
    @Getter
    @Setter
    public static class SoapResourceDocument implements Saveable<String> {

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
        @DynamoDBAttribute(attributeName = "type")
        @DynamoDBTypeConvertedEnum
        @Mapping("type")
        private SoapResourceType type;
        @DynamoDBAttribute(attributeName = "content")
        @Mapping("content")
        private String content;

        @DynamoDBAttribute(attributeName = "nameLower")
        private String nameLower;
    }
}
