package com.castlemock.repository.soap.dynamodb.project;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.castlemock.core.mock.soap.model.project.domain.SoapResource;
import com.castlemock.repository.soap.dynamodb.AbstractDynamoRepositoryTet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class SoapResourceDynamoRepositoryTest extends AbstractDynamoRepositoryTet {

    @Autowired
    SoapResourceDynamoRepository soapResourceDynamoRepository;

    @Before
    public void setup() {
        super.setup();

        CreateTableRequest createTableRequest =
                dynamoDBMapper.generateCreateTableRequest(SoapResourceDynamoRepository.SoapResourceDocument.class)
                        .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
    }

    @Test
    public void testSaveAndLoadSoapResource() {
        SoapResource soapResource = new SoapResource();
        SoapResource fromDb = soapResourceDynamoRepository.saveSoapResource(soapResource, "Very large content");
        String content = soapResourceDynamoRepository.loadSoapResource(fromDb.getId());

        assertThat(content).isEqualTo("Very large content");
    }

    @After
    public void after() {
        DeleteTableRequest deleteTableRequest =
                dynamoDBMapper.generateDeleteTableRequest(SoapResourceDynamoRepository.SoapResourceDocument.class);
        amazonDynamoDB.deleteTable(deleteTableRequest);
    }
}