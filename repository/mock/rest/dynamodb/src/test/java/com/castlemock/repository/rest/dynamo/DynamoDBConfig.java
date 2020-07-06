package com.castlemock.repository.rest.dynamo;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.dynamodb.tableNamePrefix:#{null}}")
    private String amazonDynamoDBTableNamePrefix;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClient.builder();

        if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
            builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                    amazonDynamoDBEndpoint, null
            ));
        }

        return builder.build();
    }

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
        if (StringUtils.isNotBlank(amazonDynamoDBTableNamePrefix)) {
            builder.withTableNameOverride(
                    DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix(
                            amazonDynamoDBTableNamePrefix
                    )
            );
        }

        return builder.build();
    }
}

