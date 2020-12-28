package com.castlemock.repository.rest.mongo.project;

import com.castlemock.model.mock.rest.domain.RestProject;
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
public class RestProjectMongoRepositoryTest {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    RestProjectMongoRepository restProjectMongoRepository;

    @Test
    public void testFindrestProjectWithName() {
        restProjectMongoRepository.save(createRestProject("xxAbcXX"));
        restProjectMongoRepository.save(createRestProject("abc"));
        restProjectMongoRepository.save(createRestProject("xAbc"));

        RestProject abc = restProjectMongoRepository.findRestProjectWithName("ABC");
        assertThat(abc.getName()).isEqualTo("abc");
    }

    @After
    public void after() {
        mongoOperations.dropCollection("restProject");
    }

    // private utility

    private RestProject createRestProject(String name) {
        RestProject restProject = new RestProject();
        restProject.setName(name);
        return restProject;
    }
}