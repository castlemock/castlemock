package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseTestBuilder;
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
import com.castlemock.service.mock.soap.project.input.ReadSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapMockResponseOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class ReadSoapMockResponseServiceTest {

    @Mock
    private SoapMockResponseRepository mockResponseRepository;

    @InjectMocks
    private ReadSoapMockResponseService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.builder().build();
        final String projectId = "SOAP PROJECT";
        final String portId = "SOAP PORT";
        final String operationId = "OPERATION ID";

        final ReadSoapMockResponseInput input = ReadSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(mockResponse.getId())
                .build();
        final ServiceTask<ReadSoapMockResponseInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(mockResponseRepository.findOne(mockResponse.getId())).thenReturn(Optional.of(mockResponse));
        final ServiceResult<ReadSoapMockResponseOutput> result = service.process(serviceTask);

        Mockito.verify(mockResponseRepository, Mockito.times(1)).findOne(mockResponse.getId());

        Assertions.assertNotNull(result.getOutput());

        final SoapMockResponse returnedSoapMockResponse = result.getOutput().getMockResponse()
                .orElse(null);

        Assertions.assertNotNull(returnedSoapMockResponse);
        Assertions.assertEquals(mockResponse, returnedSoapMockResponse);
    }

}
