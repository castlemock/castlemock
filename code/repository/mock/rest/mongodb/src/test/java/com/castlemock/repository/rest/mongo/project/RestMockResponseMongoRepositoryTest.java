package com.castlemock.repository.rest.mongo.project;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
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
public class RestMockResponseMongoRepositoryTest {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private RestMockResponseMongoRepository restMockResponseMongoRepository;

    @Test
    public void testSearch() {

        restMockResponseMongoRepository.save(createrestMockResponse("aaXYZbb"));
        restMockResponseMongoRepository.save(createrestMockResponse("aaXYZ"));
        restMockResponseMongoRepository.save(createrestMockResponse("aaxyz"));
        restMockResponseMongoRepository.save(createrestMockResponse("xyz"));
        restMockResponseMongoRepository.save(createrestMockResponse("aaefg"));

        SearchQuery query = new SearchQuery();
        query.setQuery("xyz");
        List<RestMockResponse> searchResults = restMockResponseMongoRepository.search(query);

        assertThat(searchResults).hasSize(4);
        assertThat(searchResults).noneMatch(it -> it.getName().equals("aaefg"));
    }

    @After
    public void after() {
        mongoOperations.dropCollection("restMockResponse");
    }

    // private utility

    private RestMockResponse createrestMockResponse(String name) {
        RestMockResponse dto = new RestMockResponse();
        dto.setName(name);
        return dto;
    }
}