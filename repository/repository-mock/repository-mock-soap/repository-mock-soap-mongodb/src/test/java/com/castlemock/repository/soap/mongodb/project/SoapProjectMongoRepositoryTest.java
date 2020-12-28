package com.castlemock.repository.soap.mongodb.project;

import com.castlemock.model.mock.soap.domain.SoapProject;
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
public class SoapProjectMongoRepositoryTest {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    SoapProjectMongoRepository soapProjectMongoRepository;

    @Test
    public void testFindSoapProjectWithName() {
        soapProjectMongoRepository.save(createSoapProject("xxAbcXX"));
        soapProjectMongoRepository.save(createSoapProject("abc"));
        soapProjectMongoRepository.save(createSoapProject("xAbc"));

        SoapProject abc = soapProjectMongoRepository.findSoapProjectWithName("ABC");
        assertThat(abc.getName()).isEqualTo("abc");
    }

    @After
    public void after() {
        mongoOperations.dropCollection("soapProject");
    }

    // private utility

    private SoapProject createSoapProject(String name) {
        SoapProject soapProject = new SoapProject();
        soapProject.setName(name);
        return soapProject;
    }
}