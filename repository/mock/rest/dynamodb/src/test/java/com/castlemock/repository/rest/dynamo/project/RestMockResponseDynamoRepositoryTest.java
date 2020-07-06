package com.castlemock.repository.rest.dynamo.project;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.repository.rest.dynamo.AbstractDynamoRepositoryTet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestMockResponseDynamoRepositoryTest extends AbstractDynamoRepositoryTet {

    @Autowired
    private RestMockResponseDynamoRepository restMockResponseDynamoRepository;

    @Before
    public void setup() {
        super.setup();

        CreateTableRequest createTableRequest =
                dynamoDBMapper.generateCreateTableRequest(RestMockResponseDynamoRepository.RestMockResponseDocument.class)
                        .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
    }

    @Test
    public void testSearch() {

        restMockResponseDynamoRepository.save(createrestMockResponse("aaXYZbb"));
        restMockResponseDynamoRepository.save(createrestMockResponse("aaXYZ"));
        restMockResponseDynamoRepository.save(createrestMockResponse("aaxyz"));
        restMockResponseDynamoRepository.save(createrestMockResponse("xyz"));
        restMockResponseDynamoRepository.save(createrestMockResponse("aaefg"));

        SearchQuery query = new SearchQuery();
        query.setQuery("xyz");
        List<RestMockResponse> searchResults = restMockResponseDynamoRepository.search(query);

        assertThat(searchResults).hasSize(4);
        assertThat(searchResults).noneMatch(it -> it.getName().equals("aaefg"));
    }

    @After
    public void after() {
        DeleteTableRequest deleteTableRequest =
                dynamoDBMapper.generateDeleteTableRequest(RestMockResponseDynamoRepository.RestMockResponseDocument.class);
        amazonDynamoDB.deleteTable(deleteTableRequest);
    }

    // private utility

    private RestMockResponse createrestMockResponse(String name) {
        RestMockResponse dto = new RestMockResponse();
        dto.setName(name);
        return dto;
    }
}