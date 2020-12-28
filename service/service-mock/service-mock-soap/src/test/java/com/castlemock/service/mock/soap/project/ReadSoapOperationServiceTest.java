package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.model.ServiceResult;
import com.castlemock.model.core.model.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.service.mock.soap.project.input.ReadSoapOperationInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapOperationOutput;
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

public class ReadSoapOperationServiceTest {

    @Mock
    private SoapOperationRepository operationRepository;

    @Mock
    private SoapMockResponseRepository mockResponseRepository;

    @InjectMocks
    private ReadSoapOperationService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapOperation operation = SoapOperationTestBuilder.builder().build();
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.builder().build();
        final String projectId = "SOAP PROJECT";
        final String portId = "SOAP PORT";

        final ReadSoapOperationInput input = ReadSoapOperationInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operation.getId())
                .build();
        final ServiceTask<ReadSoapOperationInput> serviceTask = new ServiceTask<ReadSoapOperationInput>(input);

        Mockito.when(operationRepository.findOne(operation.getId())).thenReturn(operation);
        Mockito.when(mockResponseRepository.findWithOperationId(operation.getId())).thenReturn(Arrays.asList(mockResponse));
        final ServiceResult<ReadSoapOperationOutput> result = service.process(serviceTask);

        Mockito.verify(operationRepository, Mockito.times(1)).findOne(operation.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).findWithOperationId(operation.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(operation, result.getOutput().getOperation());
        Assert.assertEquals(operation.getDefaultResponseName(), null);
    }

    @Test
    public void testProcessWithDefaultXPathResponse(){
        final SoapOperation operation = SoapOperationTestBuilder.builder().build();
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.builder().build();
        final String projectId = "SOAP PROJECT";
        final String portId = "SOAP PORT";

        operation.setDefaultMockResponseId(mockResponse.getId());

        final ReadSoapOperationInput input = ReadSoapOperationInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operation.getId())
                .build();
        final ServiceTask<ReadSoapOperationInput> serviceTask = new ServiceTask<ReadSoapOperationInput>(input);

        Mockito.when(operationRepository.findOne(operation.getId())).thenReturn(operation);
        Mockito.when(mockResponseRepository.findWithOperationId(operation.getId())).thenReturn(Arrays.asList(mockResponse));
        final ServiceResult<ReadSoapOperationOutput> result = service.process(serviceTask);

        Mockito.verify(operationRepository, Mockito.times(1)).findOne(operation.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).findWithOperationId(operation.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(operation, result.getOutput().getOperation());
        Assert.assertEquals(mockResponse.getName(), operation.getDefaultResponseName());
    }

}
