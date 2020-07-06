package com.castlemock.repository.rest.dynamo.project;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.repository.rest.dynamo.AbstractDynamoRepositoryTet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class RestResourceDynamoRepositoryTest extends AbstractDynamoRepositoryTet {

    @Autowired
    RestResourceDynamoRepository restResourceDynamoRepository;

    @Before
    public void setup() {
        super.setup();

        CreateTableRequest createTableRequest =
                dynamoDBMapper.generateCreateTableRequest(RestResourceDynamoRepository.RestResourceDocument.class)
                        .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
    }

    @Test
    public void testFindRestResourceByUri() {
        RestResource toMatch = createRestResource("app1", "uri1");
        restResourceDynamoRepository.save(toMatch);
        restResourceDynamoRepository.save(createRestResource("app1", "xxURI12"));

        RestResource fromDb = restResourceDynamoRepository.findRestResourceByUri("app1", "URI1");

        assertThat(fromDb).isEqualToIgnoringGivenFields(toMatch, "id");
    }

    @After
    public void after() {
        DeleteTableRequest deleteTableRequest =
                dynamoDBMapper.generateDeleteTableRequest(RestResourceDynamoRepository.RestResourceDocument.class);
        amazonDynamoDB.deleteTable(deleteTableRequest);
    }

    // private utility
    RestResource createRestResource(String applicationId, String uri) {
        RestResource restResource = new RestResource();
        restResource.setApplicationId(applicationId);
        restResource.setUri(uri);
        return restResource;
    }
}