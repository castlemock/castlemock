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

package com.castlemock.repository.soap.dynamodb.project;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.dynamodb.project.AbstractProjectDynamoRepository;
import com.castlemock.repository.soap.project.SoapProjectRepository;
import com.google.common.base.Preconditions;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The class is an implementation of the dynamo repository and provides the functionality to interact with dynamodb.
 * The repository is responsible for loading and saving soap project from dynamodb.
 *
 * @author Tiago Santos
 * @see SoapProjectRepository
 * @since 1.51
 */
@Repository
@Profile(Profiles.DYNAMODB)
public class SoapProjectDynamoRepository extends AbstractProjectDynamoRepository<SoapProjectDynamoRepository.SoapProjectDocument, SoapProject> implements SoapProjectRepository {

    @Autowired
    public SoapProjectDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public SoapProjectDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    /**
     * Finds a project by a given name
     *
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     */
    @Override
    public SoapProject findSoapProjectWithName(final String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");

        return dynamoDBMapper.scan(entityClass,
                getAttributeScan("nameLower", name.toLowerCase(), ComparisonOperator.EQ))
                .stream().findFirst().map(this::mapToDTO).orElse(null);
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to dynamodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to dynamodb, but also loaded from
     * dynamodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param soapProject The instance of the type that will be checked and controlled before it is allowed to be saved on
     *                    dynamodb.
     * @see #save
     */
    @Override
    protected void checkType(SoapProjectDocument soapProject) {

    }

    /**
     * Delete an instance that match the provided id
     *
     * @param soapProjectId The instance that matches the provided id will be deleted in the database
     */
    @Override
    public SoapProject delete(final String soapProjectId) {
        Preconditions.checkNotNull(soapProjectId, "Project id cannot be null");

        final SoapProjectDocument soapProject =
                dynamoDBMapper.load(entityClass, soapProjectId);

        if (soapProject == null) {
            throw new IllegalArgumentException("Unable to find a SOAP project with id " + soapProjectId);
        }

        return super.delete(soapProjectId);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapProject> search(final SearchQuery query) {
        List<SoapProjectDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("nameLower", query.getQuery().toLowerCase(), ComparisonOperator.CONTAINS));
        return toDtoList(responses);
    }

    @DynamoDBTable(tableName = "soapProject")
    public static class SoapProjectDocument extends AbstractProjectDynamoRepository.ProjectDocument {

    }

}
