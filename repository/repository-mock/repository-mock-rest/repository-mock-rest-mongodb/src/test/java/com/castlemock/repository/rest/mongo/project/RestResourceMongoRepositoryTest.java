package com.castlemock.repository.rest.mongo.project;

import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.repository.Profiles;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@RunWith(SpringRunner.class)
@ActiveProfiles(Profiles.MONGODB)
public class RestResourceMongoRepositoryTest {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    RestResourceMongoRepository restResourceMongoRepository;

    @Test
    public void testFindRestResourceByUri() {
        RestResource toMatch = createRestResource("app1", "uri1");
        restResourceMongoRepository.save(toMatch);
        restResourceMongoRepository.save(createRestResource("app1", "xxURI12"));

        RestResource fromDb = restResourceMongoRepository.findRestResourceByUri("app1", "URI1");

        assertThat(fromDb).isEqualToIgnoringGivenFields(toMatch, "id");
    }

    @After
    public void after() {
        mongoOperations.dropCollection("restResource");
    }

    // private utility
    RestResource createRestResource(String applicationId, String uri) {
        RestResource restResource = new RestResource();
        restResource.setApplicationId(applicationId);
        restResource.setUri(uri);
        return restResource;
    }
}