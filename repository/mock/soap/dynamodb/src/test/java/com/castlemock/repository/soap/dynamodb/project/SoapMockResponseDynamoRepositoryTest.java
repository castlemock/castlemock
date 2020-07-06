package com.castlemock.repository.soap.dynamodb.project;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.repository.soap.dynamodb.AbstractDynamoRepositoryTet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SoapMockResponseDynamoRepositoryTest extends AbstractDynamoRepositoryTet {

    @Autowired
    private SoapMockResponseDynamoRepository soapMockResponseDynamoRepository;

    @Before
    public void setup() {
        super.setup();

        CreateTableRequest createTableRequest =
                dynamoDBMapper.generateCreateTableRequest(SoapMockResponseDynamoRepository.SoapMockResponseDocument.class)
                        .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
    }

    @Test
    public void testSearch() {

        soapMockResponseDynamoRepository.save(createSoapMockResponse("aaXYZbb"));
        soapMockResponseDynamoRepository.save(createSoapMockResponse("aaXYZ"));
        soapMockResponseDynamoRepository.save(createSoapMockResponse("aaxyz"));
        soapMockResponseDynamoRepository.save(createSoapMockResponse("xyz"));
        soapMockResponseDynamoRepository.save(createSoapMockResponse("aaefg"));

        SearchQuery query = new SearchQuery();
        query.setQuery("xyz");
        List<SoapMockResponse> searchResults = soapMockResponseDynamoRepository.search(query);

        assertThat(searchResults).hasSize(4);
        assertThat(searchResults).noneMatch(it -> it.getName().equals("aaefg"));
    }

    @After
    public void after() {
        DeleteTableRequest deleteTableRequest =
                dynamoDBMapper.generateDeleteTableRequest(SoapMockResponseDynamoRepository.SoapMockResponseDocument.class);
        amazonDynamoDB.deleteTable(deleteTableRequest);
    }

    // private utility

    private SoapMockResponse createSoapMockResponse(String name) {
        SoapMockResponse dto = new SoapMockResponse();
        dto.setName(name);
        return dto;
    }
}