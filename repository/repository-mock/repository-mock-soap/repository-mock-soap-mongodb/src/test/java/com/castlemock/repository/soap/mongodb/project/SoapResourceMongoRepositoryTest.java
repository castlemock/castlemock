package com.castlemock.repository.soap.mongodb.project;

import com.castlemock.core.mock.soap.model.project.domain.SoapResource;
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
public class SoapResourceMongoRepositoryTest {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    SoapResourceMongoRepository soapResourceMongoRepository;

    @Test
    public void testSaveAndLoadSoapResource() {
        SoapResource soapResource = new SoapResource();
        SoapResource fromDb = soapResourceMongoRepository.saveSoapResource(soapResource, "Very large content");
        String content = soapResourceMongoRepository.loadSoapResource(fromDb.getId());

        assertThat(content).isEqualTo("Very large content");
    }

    @After
    public void after() {
        mongoOperations.dropCollection("soapResource");
    }
}