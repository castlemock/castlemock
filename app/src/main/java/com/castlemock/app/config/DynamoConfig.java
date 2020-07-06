package com.castlemock.app.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.castlemock.repository.Profiles;
import com.castlemock.web.basis.config.DynamoProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(Profiles.DYNAMODB)
@EnableConfigurationProperties(value = { DynamoProperties.class })
public class DynamoConfig {

    private DynamoProperties dynamoProperties;

    @Autowired
    public DynamoConfig(DynamoProperties dynamoProperties) {
        this.dynamoProperties = dynamoProperties;
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClient.builder();

        if (!StringUtils.isEmpty(dynamoProperties.getEndpoint())) {
            builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                    dynamoProperties.getEndpoint(), null
            ));
        }

        return builder.build();
    }

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
        if (StringUtils.isNotBlank(dynamoProperties.getTableNamePrefix())) {
            builder.withTableNameOverride(
                    DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix(
                            dynamoProperties.getTableNamePrefix()
                    )
            );
        }

        return builder.build();
    }
}
