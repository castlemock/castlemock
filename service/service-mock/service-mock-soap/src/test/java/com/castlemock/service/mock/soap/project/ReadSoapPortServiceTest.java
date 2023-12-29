package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapPortTestBuilder;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.repository.soap.project.SoapPortRepository;
import com.castlemock.service.mock.soap.project.input.ReadSoapPortInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapPortOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

public class ReadSoapPortServiceTest {

    @Mock
    private SoapPortRepository portRepository;

    @Mock
    private SoapOperationRepository operationRepository;

    @InjectMocks
    private ReadSoapPortService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapPort port = SoapPortTestBuilder.builder().build();
        final SoapOperation operation = SoapOperationTestBuilder.builder().build();
        final String projectId = "SOAP PROJECT";

        final ReadSoapPortInput input = ReadSoapPortInput.builder()
                .projectId(projectId)
                .portId(port.getId())
                .build();
        final ServiceTask<ReadSoapPortInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(portRepository.findOne(port.getId())).thenReturn(port);
        Mockito.when(operationRepository.findWithPortId(port.getId())).thenReturn(Arrays.asList(operation));
        final ServiceResult<ReadSoapPortOutput> result = service.process(serviceTask);

        Mockito.verify(portRepository, Mockito.times(1)).findOne(port.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).findWithPortId(port.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(port, result.getOutput().getPort());
    }

}
