package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.service.mock.soap.project.input.ReadSoapOperationInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapOperationOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReadSoapOperationServiceTest {

    @Mock
    private SoapOperationRepository operationRepository;

    @Mock
    private SoapMockResponseRepository mockResponseRepository;

    @InjectMocks
    private ReadSoapOperationService service;

    @BeforeEach
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
        final ServiceTask<ReadSoapOperationInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(operationRepository.findOne(operation.getId())).thenReturn(Optional.of(operation));
        Mockito.when(mockResponseRepository.findWithOperationId(operation.getId())).thenReturn(Collections.singletonList(mockResponse));
        final ServiceResult<ReadSoapOperationOutput> result = service.process(serviceTask);

        Mockito.verify(operationRepository, Mockito.times(1)).findOne(operation.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).findWithOperationId(operation.getId());

        Assertions.assertNotNull(result.getOutput());

        final SoapOperation returnedSoapOperation = result.getOutput().getOperation()
                .orElse(null);

        Assertions.assertNotNull(returnedSoapOperation);
        Assertions.assertEquals(operation.toBuilder()
                .mockResponses(List.of(mockResponse))
                .build(), returnedSoapOperation);
        Assertions.assertNull(operation.getDefaultResponseName().orElse(null));
    }

    @Test
    public void testProcessWithDefaultXPathResponse(){
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.builder().build();
        final SoapOperation operation = SoapOperationTestBuilder.builder()
                .defaultMockResponseId(mockResponse.getId())
                .build();
        final String projectId = "SOAP PROJECT";
        final String portId = "SOAP PORT";

        final ReadSoapOperationInput input = ReadSoapOperationInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operation.getId())
                .build();
        final ServiceTask<ReadSoapOperationInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(operationRepository.findOne(operation.getId())).thenReturn(Optional.of(operation));
        Mockito.when(mockResponseRepository.findWithOperationId(operation.getId())).thenReturn(List.of(mockResponse));
        final ServiceResult<ReadSoapOperationOutput> result = service.process(serviceTask);

        Mockito.verify(operationRepository, Mockito.times(1)).findOne(operation.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).findWithOperationId(operation.getId());

        Assertions.assertNotNull(result.getOutput());

        final SoapOperation returnedSoapOperation = result.getOutput().getOperation()
                .orElse(null);

        Assertions.assertNotNull(returnedSoapOperation);
        Assertions.assertEquals(operation.toBuilder()
                .defaultResponseName(mockResponse.getName())
                .mockResponses(List.of(mockResponse))
                .build(), returnedSoapOperation);
    }

}
