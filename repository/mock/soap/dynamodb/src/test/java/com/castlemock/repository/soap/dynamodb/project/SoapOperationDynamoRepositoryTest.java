package com.castlemock.repository.soap.dynamodb.project;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationIdentifier;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationIdentifyStrategy;
import com.castlemock.core.mock.soap.model.project.domain.SoapVersion;
import com.castlemock.repository.soap.dynamodb.AbstractDynamoRepositoryTet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class SoapOperationDynamoRepositoryTest extends AbstractDynamoRepositoryTet {

    @Autowired
    private SoapOperationDynamoRepository soapOperationDynamoRepository;

    @Before
    public void setup() {
        super.setup();

        CreateTableRequest createTableRequest =
                dynamoDBMapper.generateCreateTableRequest(SoapOperationDynamoRepository.SoapOperationDocument.class)
                        .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
    }

    @Test
    public void testFindWithName() {
        soapOperationDynamoRepository.save(createSoapOperation("port2", "operation1"));
        soapOperationDynamoRepository.save(createSoapOperation("port1", "operation1"));
        soapOperationDynamoRepository.save(createSoapOperation("port1", "operation2"));
        soapOperationDynamoRepository.save(createSoapOperation("port1", "operation1"));

        SoapOperation soapOperation = soapOperationDynamoRepository.findWithName("port1", "operation1");
        assertThat(soapOperation.getPortId()).isEqualTo("port1");
        assertThat(soapOperation.getName()).isEqualTo("operation1");
    }

    @Test
    public void testFindWithMethodAndVersionAndIdentifier() {

        SoapOperation shouldMatch = soapOperationDynamoRepository
                .save(createSoapOperation("port1", HttpMethod.POST, SoapVersion.SOAP12, "id1", "xn"));
        soapOperationDynamoRepository.save(createSoapOperation("port1", HttpMethod.POST, SoapVersion.SOAP12, "id1", "xy"));
        soapOperationDynamoRepository.save(createSoapOperation("port1", HttpMethod.POST, SoapVersion.SOAP12, "id2", "xz"));
        soapOperationDynamoRepository.save(createSoapOperation("port1", HttpMethod.POST, SoapVersion.SOAP11, "id1", "xx"));

        SoapOperationIdentifier soapOperationIdentifier = new SoapOperationIdentifier();
        soapOperationIdentifier.setName("id1");
        soapOperationIdentifier.setNamespace("xn");

        SoapOperation result = soapOperationDynamoRepository.findWithMethodAndVersionAndIdentifier("port1", HttpMethod.POST,
                SoapVersion.SOAP12, soapOperationIdentifier);

        assertThat(result).usingRecursiveComparison().isEqualTo(shouldMatch);
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
        DeleteTableRequest deleteTableRequest =
                dynamoDBMapper.generateDeleteTableRequest(SoapOperationDynamoRepository.SoapOperationDocument.class);
        amazonDynamoDB.deleteTable(deleteTableRequest);
    }

}