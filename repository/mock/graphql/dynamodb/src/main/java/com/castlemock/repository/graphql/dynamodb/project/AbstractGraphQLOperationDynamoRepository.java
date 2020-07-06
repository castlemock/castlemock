package com.castlemock.repository.graphql.dynamodb.project;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLArgument;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttributeType;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLOperationStatus;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLResponseStrategy;
import com.castlemock.repository.core.dynamodb.DynamoRepository;
import lombok.Getter;
import lombok.Setter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapping;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Tiago Santos
 * @since 1.51
 */
public abstract class AbstractGraphQLOperationDynamoRepository<T extends AbstractGraphQLOperationDynamoRepository.GraphQLOperationDocument, D> extends DynamoRepository<T, D, String> {

    public AbstractGraphQLOperationDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public AbstractGraphQLOperationDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    @Override
    protected T mapToEntity(D dto) {
        T entity = super.mapToEntity(dto);
        entity.setNameLower(Optional.ofNullable(entity.getName()).map(String::toLowerCase).orElse(null));
        return entity;
    }

    //@Document(collection = "graphQLOperation")
    @DynamoDBDocument
    @Getter
    @Setter
    public static abstract class GraphQLOperationDocument implements Saveable<String> {

        @DynamoDBHashKey(attributeName = "id")
        @Mapping("id")
        private String id;
        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "description")
        @Mapping("description")
        private String description;
        @DynamoDBAttribute(attributeName = "applicationId")
        @Mapping("applicationId")
        private String applicationId;
        @DynamoDBAttribute(attributeName = "networkDelay")
        @Mapping("networkDelay")
        private long networkDelay;
        @DynamoDBAttribute(attributeName = "httpMethod")
        @DynamoDBTypeConvertedEnum
        @Mapping("httpMethod")
        private HttpMethod httpMethod;
        @DynamoDBAttribute(attributeName = "forwardedEndpoint")
        @Mapping("forwardedEndpoint")
        private String forwardedEndpoint;
        @DynamoDBAttribute(attributeName = "originalEndpoint")
        @Mapping("originalEndpoint")
        private String originalEndpoint;
        @DynamoDBAttribute(attributeName = "simulateNetworkDelay")
        @Mapping("simulateNetworkDelay")
        private boolean simulateNetworkDelay;
        @DynamoDBAttribute(attributeName = "status")
        @DynamoDBTypeConvertedEnum
        @Mapping("status")
        private GraphQLOperationStatus status;
        @DynamoDBAttribute(attributeName = "responseStrategy")
        @DynamoDBTypeConvertedEnum
        @Mapping("responseStrategy")
        private GraphQLResponseStrategy responseStrategy;
        @DynamoDBAttribute(attributeName = "result")
        @Mapping("result")
        private GraphQLResultDocument result;
        @DynamoDBAttribute(attributeName = "arguments")
        @Mapping("arguments")
        private List<GraphQLArgument> arguments = new CopyOnWriteArrayList<GraphQLArgument>();

        @DynamoDBAttribute(attributeName = "nameLower")
        private String nameLower;
    }

    @DynamoDBTable(tableName = "graphQLResult")
    @Getter
    @Setter
    public static class GraphQLResultDocument implements Saveable<String> {

        @DynamoDBHashKey(attributeName = "id")
        @Mapping("id")
        private String id;
        @DynamoDBAttribute(attributeName = "typeName")
        @Mapping("typeName")
        private String typeName;
        @DynamoDBAttribute(attributeName = "typeId")
        @Mapping("typeId")
        private String typeId;
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
