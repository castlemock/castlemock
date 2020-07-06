package com.castlemock.repository.soap.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.castlemock.repository.Profiles;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

@RunWith(SpringRunner.class)
@ActiveProfiles(Profiles.DYNAMODB)
@SpringBootTest
@ContextConfiguration(initializers = AbstractDynamoRepositoryTet.Initializer.class)
public abstract class AbstractDynamoRepositoryTet {

    private static final int DYNAMO_PORT = 8000;

    static class DynamoDBContainer extends GenericContainer<DynamoDBContainer> {
        public DynamoDBContainer(String dockerImageName) {
            super(dockerImageName);
        }
    }

    public static DynamoDBContainer dynamoDb;

    static {
        dynamoDb =
                new DynamoDBContainer("amazon/dynamodb-local")
                        .withExposedPorts(DYNAMO_PORT);
        dynamoDb.start();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            String endpoint = String.format("amazon.dynamodb.endpoint=http://%s:%s",
                    dynamoDb.getContainerIpAddress(),
                    dynamoDb.getMappedPort(DYNAMO_PORT));

            TestPropertyValues.of(endpoint).applyTo(configurableApplicationContext);
        }
    }

    protected DynamoDBMapper dynamoDBMapper;

    @Autowired
    protected AmazonDynamoDB amazonDynamoDB;

    @Before
    public void setup() {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }
}
