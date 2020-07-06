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
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLMutation;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.graphql.project.GraphQLMutationRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Tiago Santos
 * @since 1.51
 */
@Repository
@Profile(Profiles.DYNAMODB)
public class GraphQLMutationDynamoRepository extends AbstractGraphQLOperationDynamoRepository<GraphQLMutationDynamoRepository.GraphQLMutationDocument, GraphQLMutation> implements GraphQLMutationRepository {

    @Autowired
    public GraphQLMutationDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public GraphQLMutationDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the dynamodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to dynamodb, but also loaded from the
     * dynamodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             dynamodb.
     * @see #save
     */
    @Override
    protected void checkType(GraphQLMutationDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLMutation> search(SearchQuery query) {
        List<GraphQLMutationDocument> operations =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("nameLower", query.getQuery().toLowerCase(), ComparisonOperator.CONTAINS));
        return toDtoList(operations);
    }

    @Override
    public List<GraphQLMutation> findWithApplicationId(final String applicationId) {
        List<GraphQLMutationDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("applicationId", applicationId, ComparisonOperator.EQ));
        return toDtoList(responses);
    }

    @Override
    public void deleteWithApplicationId(final String applicationId) {
        List<GraphQLMutationDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("applicationId", applicationId, ComparisonOperator.EQ));
        dynamoDBMapper.batchDelete(responses);
    }

    @DynamoDBTable(tableName = "graphQLMutation")
    public static class GraphQLMutationDocument extends AbstractGraphQLOperationDynamoRepository.GraphQLOperationDocument {

    }
}
