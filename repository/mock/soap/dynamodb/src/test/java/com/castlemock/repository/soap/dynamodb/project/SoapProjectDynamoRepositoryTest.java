package com.castlemock.repository.soap.dynamodb.project;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.repository.soap.dynamodb.AbstractDynamoRepositoryTet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class SoapProjectDynamoRepositoryTest extends AbstractDynamoRepositoryTet {

    @Autowired
    SoapProjectDynamoRepository soapProjectDynamoRepository;

    @Before
    public void setup() {
        super.setup();

        CreateTableRequest createTableRequest =
                dynamoDBMapper.generateCreateTableRequest(SoapProjectDynamoRepository.SoapProjectDocument.class)
                        .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
    }

    @Test
    public void testFindSoapProjectWithName() {
        soapProjectDynamoRepository.save(createSoapProject("xxAbcXX"));
        soapProjectDynamoRepository.save(createSoapProject("abc"));
        soapProjectDynamoRepository.save(createSoapProject("xAbc"));

        SoapProject abc = soapProjectDynamoRepository.findSoapProjectWithName("ABC");
        assertThat(abc.getName()).isEqualTo("abc");
    }

    @After
    public void after() {
        DeleteTableRequest deleteTableRequest =
                dynamoDBMapper.generateDeleteTableRequest(SoapProjectDynamoRepository.SoapProjectDocument.class);
        amazonDynamoDB.deleteTable(deleteTableRequest);
    }

    // private utility

    private SoapProject createSoapProject(String name) {
        SoapProject soapProject = new SoapProject();
        soapProject.setName(name);
        return soapProject;
    }
}