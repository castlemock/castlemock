package com.castlemock.repository.graphql.dynamodb.project;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.repository.core.dynamodb.DynamoRepository;
import lombok.Getter;
import lombok.Setter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapping;

import java.util.Optional;

/**
 * @author Tiago Santos
 * @since 1.51
 */
public abstract class AbstractGraphQLTypeDynamoRepository<T extends AbstractGraphQLTypeDynamoRepository.GraphQLTypeDocument, D> extends DynamoRepository<T, D, String> {

    public AbstractGraphQLTypeDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public AbstractGraphQLTypeDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    @Override
    protected T mapToEntity(D dto) {
        T entity = super.mapToEntity(dto);
        entity.setNameLower(Optional.ofNullable(entity.getName()).map(String::toLowerCase).orElse(null));
        return entity;
    }

    //@Document(collection = "graphQLType")
    @DynamoDBDocument
    @Getter
    @Setter
    public static abstract class GraphQLTypeDocument implements Saveable<String> {

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

        @DynamoDBAttribute(attributeName = "nameLower")
        private String nameLower;
    }

}
