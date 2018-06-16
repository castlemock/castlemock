package com.castlemock.web.mock.soap.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapMockResponseInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapMockResponseOutput;
import com.castlemock.web.mock.soap.model.project.SoapMockResponseGenerator;
import com.castlemock.web.mock.soap.repository.project.SoapMockResponseRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ReadSoapMockResponseServiceTest {

    @Mock
    private SoapMockResponseRepository mockResponseRepository;

    @InjectMocks
    private ReadSoapMockResponseService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapMockResponse mockResponse = SoapMockResponseGenerator.generateSoapMockResponse();
        final String projectId = "SOAP PROJECT";
        final String portId = "SOAP PORT";
        final String operationId = "OPERATION ID";

        final ReadSoapMockResponseInput input = new ReadSoapMockResponseInput(projectId, portId, operationId, mockResponse.getId());
        final ServiceTask<ReadSoapMockResponseInput> serviceTask = new ServiceTask<ReadSoapMockResponseInput>(input);

        Mockito.when(mockResponseRepository.findOne(mockResponse.getId())).thenReturn(mockResponse);
        final ServiceResult<ReadSoapMockResponseOutput> result = service.process(serviceTask);

        Mockito.verify(mockResponseRepository, Mockito.times(1)).findOne(mockResponse.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(mockResponse, result.getOutput().getSoapMockResponse());
    }

}
