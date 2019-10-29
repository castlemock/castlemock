package com.castlemock.web.mock.soap.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationTestBuilder;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapPortTestBuilder;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapPortInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapPortOutput;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.repository.soap.project.SoapPortRepository;
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
        MockitoAnnotations.initMocks(this);
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
        final ServiceTask<ReadSoapPortInput> serviceTask = new ServiceTask<ReadSoapPortInput>(input);

        Mockito.when(portRepository.findOne(port.getId())).thenReturn(port);
        Mockito.when(operationRepository.findWithPortId(port.getId())).thenReturn(Arrays.asList(operation));
        final ServiceResult<ReadSoapPortOutput> result = service.process(serviceTask);

        Mockito.verify(portRepository, Mockito.times(1)).findOne(port.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).findWithPortId(port.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(port, result.getOutput().getPort());
    }

}
