package com.castlemock.repository.rest.dynamo.project;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.repository.rest.dynamo.AbstractDynamoRepositoryTet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class RestProjectDynamoRepositoryTest extends AbstractDynamoRepositoryTet {

    @Autowired
    RestProjectDynamoRepository restProjectDynamoRepository;

    @Before
    public void setup() {
        super.setup();

        CreateTableRequest createTableRequest =
                dynamoDBMapper.generateCreateTableRequest(RestProjectDynamoRepository.RestProjectDocument.class)
                        .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
    }

    @Test
    public void testFindrestProjectWithName() {
        restProjectDynamoRepository.save(createRestProject("xxAbcXX"));
        restProjectDynamoRepository.save(createRestProject("abc"));
        restProjectDynamoRepository.save(createRestProject("xAbc"));

        RestProject abc = restProjectDynamoRepository.findRestProjectWithName("ABC");
        assertThat(abc.getName()).isEqualTo("abc");
    }

    @After
    public void after() {
        DeleteTableRequest deleteTableRequest =
                dynamoDBMapper.generateDeleteTableRequest(RestProjectDynamoRepository.RestProjectDocument.class);
        amazonDynamoDB.deleteTable(deleteTableRequest);
    }

    // private utility

    private RestProject createRestProject(String name) {
        RestProject restProject = new RestProject();
        restProject.setName(name);
        return restProject;
    }
}