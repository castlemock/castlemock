package com.castlemock.repository.soap.mongodb.project;

import com.castlemock.model.core.model.SearchQuery;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.repository.Profiles;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@RunWith(SpringRunner.class)
@ActiveProfiles(Profiles.MONGODB)
public class SoapMockResponseMongoRepositoryTest {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private SoapMockResponseMongoRepository soapMockResponseMongoRepository;

    @Test
    public void testSearch() {

        soapMockResponseMongoRepository.save(createSoapMockResponse("aaXYZbb"));
        soapMockResponseMongoRepository.save(createSoapMockResponse("aaXYZ"));
        soapMockResponseMongoRepository.save(createSoapMockResponse("aaxyz"));
        soapMockResponseMongoRepository.save(createSoapMockResponse("xyz"));
        soapMockResponseMongoRepository.save(createSoapMockResponse("aaefg"));

        SearchQuery query = new SearchQuery();
        query.setQuery("xyz");
        List<SoapMockResponse> searchResults = soapMockResponseMongoRepository.search(query);

        assertThat(searchResults).hasSize(4);
        assertThat(searchResults).noneMatch(it -> it.getName().equals("aaefg"));
    }

    @After
    public void after() {
        mongoOperations.dropCollection("soapMockResponse");
    }

    // private utility

    private SoapMockResponse createSoapMockResponse(String name) {
        SoapMockResponse dto = new SoapMockResponse();
        dto.setName(name);
        return dto;
    }
}