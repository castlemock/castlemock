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
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttributeType;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLProject;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLRequestArgument;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLRequestField;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.dynamodb.project.AbstractProjectDynamoRepository;
import com.castlemock.repository.graphql.project.GraphQLProjectRepository;
import com.google.common.base.Preconditions;
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
public class GraphQLProjectDynamoRepository extends AbstractProjectDynamoRepository<GraphQLProjectDynamoRepository.GraphQLProjectDocument, GraphQLProject> implements GraphQLProjectRepository {

    @Autowired
    public GraphQLProjectDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public GraphQLProjectDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
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
    protected void checkType(GraphQLProjectDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLProject> search(SearchQuery query) {
        List<GraphQLProjectDocument> operations =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("nameLower", query.getQuery().toLowerCase(), ComparisonOperator.CONTAINS));
        return toDtoList(operations);
    }


    /**
     * Finds a project by a given name
     *
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     * @see GraphQLProject
     */
    @Override
    public GraphQLProject findGraphQLProjectWithName(String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");

        return dynamoDBMapper.scan(entityClass,
                getAttributeScan("nameLower", name.toLowerCase(), ComparisonOperator.EQ))
                .stream().findFirst().map(this::mapToDTO).orElse(null);
    }

    @DynamoDBTable(tableName = "graphQLProject")
    public static class GraphQLProjectDocument extends AbstractProjectDynamoRepository.ProjectDocument {

    }

    @DynamoDBTable(tableName = "graphQLRequestQuery")
    @Getter
    @Setter
    public static class GraphQLRequestQueryDocument implements Saveable<String> {

        @DynamoDBHashKey(attributeName = "id")
        @Mapping("id")
        private String id;
        @DynamoDBAttribute(attributeName = "operationName")
        @Mapping("operationName")
        private String operationName;
        @DynamoDBAttribute(attributeName = "fields")
        @Mapping("fields")
        private List<GraphQLRequestField> fields = new CopyOnWriteArrayList<GraphQLRequestField>();
        @DynamoDBAttribute(attributeName = "arguments")
        @Mapping("arguments")
        private List<GraphQLRequestArgument> arguments = new CopyOnWriteArrayList<GraphQLRequestArgument>();
    }

    @DynamoDBTable(tableName = "graphQLAttribute")
    @Getter
    @Setter
    public static class GraphQLAttributeDocument implements Saveable<String> {

        @DynamoDBHashKey(attributeName = "id")
        private String id;
        @DynamoDBAttribute(attributeName = "name")
        private String name;
        @DynamoDBAttribute(attributeName = "description")
        private String description;
        @DynamoDBAttribute(attributeName = "typeId")
        private String typeId;
        @DynamoDBAttribute(attributeName = "typeName")
        private String typeName;
        @DynamoDBAttribute(attributeName = "nullable")
        private Boolean nullable;
        @DynamoDBAttribute(attributeName = "listable")
        private Boolean listable;
        @DynamoDBAttribute(attributeName = "objectTypeId")
        private String objectTypeId;
        @DynamoDBAttribute(attributeName = "value")
        private String value;
        @DynamoDBAttribute(attributeName = "attributeType")
        @DynamoDBTypeConvertedEnum
        private GraphQLAttributeType attributeType;
        @DynamoDBAttribute(attributeName = "arguments")
        private List<GraphQLArgumentDocument> arguments = new CopyOnWriteArrayList<GraphQLArgumentDocument>();
    }

    //@Document(collection = "graphQLRequestArgument")
    @DynamoDBDocument
    @Getter
    @Setter
    public static class GraphQLRequestArgumentDocument {

        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "arguments")
        @Mapping("arguments")
        private List<GraphQLRequestArgumentDocument> arguments = new CopyOnWriteArrayList<GraphQLRequestArgumentDocument>();
    }

    //@Document(collection = "graphQLRequestField")
    @DynamoDBDocument
    @Getter
    @Setter
    public class GraphQLRequestFieldDocument {

        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "fields")
        @Mapping("fields")
        private List<GraphQLRequestFieldDocument> fields = new CopyOnWriteArrayList<GraphQLRequestFieldDocument>();
    }


    @DynamoDBTable(tableName = "graphQLArgument")
    @Getter
    @Setter
    public static class GraphQLArgumentDocument implements Saveable<String> {

        @DynamoDBHashKey(attributeName = "id")
        @Mapping("id")
        private String id;
        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "description")
        @Mapping("description")
        private String description;
        @DynamoDBAttribute(attributeName = "typeName")
        @Mapping("typeName")
        private String typeName;
        @DynamoDBAttribute(attributeName = "typeId")
        @Mapping("typeId")
        private String typeId;
        @DynamoDBAttribute(attributeName = "defaultValue")
        @Mapping("defaultValue")
        private Object defaultValue;
        @DynamoDBAttribute(attributeName = "nullable")
        @Mapping("nullable")
        private Boolean nullable;
        @DynamoDBAttribute(attributeName = "listable")
        @Mapping("listable")
        private Boolean listable;
        @DynamoDBAttribute(attributeName = "attributeType")
        @DynamoDBTypeConvertedEnum
        @Mapping("attributeType")
        private GraphQLAttributeType attributeType;
    }
}
