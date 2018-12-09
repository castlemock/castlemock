package com.castlemock.repository.soap.mongodb.project;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationIdentifier;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationIdentifyStrategy;
import com.castlemock.core.mock.soap.model.project.domain.SoapVersion;
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
public class SoapOperationMongoRepositoryTest {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private SoapOperationMongoRepository soapOperationMongoRepository;

    @Test
    public void testFindWithName() {
        soapOperationMongoRepository.save(createSoapOperation("port2", "operation1"));
        soapOperationMongoRepository.save(createSoapOperation("port1", "operation1"));
        soapOperationMongoRepository.save(createSoapOperation("port1", "operation2"));
        soapOperationMongoRepository.save(createSoapOperation("port1", "operation1"));

        SoapOperation soapOperation = soapOperationMongoRepository.findWithName("port1", "operation1");
        assertThat(soapOperation.getPortId()).isEqualTo("port1");
        assertThat(soapOperation.getName()).isEqualTo("operation1");
    }

    @Test
    public void testFindWithMethodAndVersionAndIdentifier() {

        SoapOperation shouldMatch = soapOperationMongoRepository
                .save(createSoapOperation("port1", HttpMethod.POST, SoapVersion.SOAP12, "id1", "xn"));
        soapOperationMongoRepository.save(createSoapOperation("port1", HttpMethod.POST, SoapVersion.SOAP12, "id1", "xy"));
        soapOperationMongoRepository.save(createSoapOperation("port1", HttpMethod.POST, SoapVersion.SOAP12, "id2", "xz"));
        soapOperationMongoRepository.save(createSoapOperation("port1", HttpMethod.POST, SoapVersion.SOAP11, "id1", "xx"));

        SoapOperationIdentifier soapOperationIdentifier = new SoapOperationIdentifier();
        soapOperationIdentifier.setName("id1");
        soapOperationIdentifier.setNamespace("xn");

        SoapOperation result = soapOperationMongoRepository.findWithMethodAndVersionAndIdentifier("port1", HttpMethod.POST,
                SoapVersion.SOAP12, soapOperationIdentifier);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(shouldMatch);
    }

    private SoapOperation createSoapOperation(String portId, String name) {
        SoapOperation soapOperation = new SoapOperation();
        soapOperation.setName(name);
        soapOperation.setPortId(portId);
        return soapOperation;
    }

    private SoapOperation createSoapOperation(String portId, HttpMethod method, SoapVersion soapVersion,
                                              String identifierName, String namespace) {
        SoapOperation soapOperation = new SoapOperation();
        soapOperation.setPortId(portId);
        soapOperation.setHttpMethod(method);
        soapOperation.setSoapVersion(soapVersion);
        soapOperation.setIdentifyStrategy(SoapOperationIdentifyStrategy.ELEMENT_NAMESPACE);
        SoapOperationIdentifier soapOperationIdentifier = new SoapOperationIdentifier();
        soapOperationIdentifier.setName(identifierName);
        soapOperationIdentifier.setNamespace(namespace);
        soapOperation.setOperationIdentifier(soapOperationIdentifier);
        return soapOperation;
    }

    @After
    public void after() {
        mongoOperations.dropCollection("soapOperation");
    }

}