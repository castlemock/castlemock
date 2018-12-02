package com.castlemock.web.mock.soap.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.domain.SoapResource;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapProjectInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapProjectOutput;
import com.castlemock.core.mock.soap.model.project.SoapOperationGenerator;
import com.castlemock.core.mock.soap.model.project.SoapPortGenerator;
import com.castlemock.core.mock.soap.model.project.SoapProjectGenerator;
import com.castlemock.core.mock.soap.model.project.SoapResourceGenerator;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.repository.soap.project.SoapPortRepository;
import com.castlemock.repository.soap.project.SoapProjectRepository;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

public class ReadSoapProjectServiceTest {

    @Mock
    private SoapProjectRepository repository;

    @Mock
    private SoapPortRepository portRepository;

    @Mock
    private SoapResourceRepository resourceRepository;

    @Mock
    private SoapOperationRepository operationRepository;

    @InjectMocks
    private ReadSoapProjectService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProject project = SoapProjectGenerator.generateSoapProject();
        final SoapPort port = SoapPortGenerator.generateSoapPort();
        final SoapResource resource = SoapResourceGenerator.generateSoapResource();
        final SoapOperation operation = SoapOperationGenerator.generateSoapOperation();

        final ReadSoapProjectInput input = ReadSoapProjectInput.builder()
                .projectId(project.getId())
                .build();
        final ServiceTask<ReadSoapProjectInput> serviceTask = new ServiceTask<ReadSoapProjectInput>(input);

        Mockito.when(repository.findOne(project.getId())).thenReturn(project);
        Mockito.when(portRepository.findWithProjectId(project.getId())).thenReturn(Arrays.asList(port));
        Mockito.when(resourceRepository.findWithProjectId(project.getId())).thenReturn(Arrays.asList(resource));
        Mockito.when(operationRepository.findWithPortId(port.getId())).thenReturn(Arrays.asList(operation));
        final ServiceResult<ReadSoapProjectOutput> result = service.process(serviceTask);

        Mockito.verify(repository, Mockito.times(1)).findOne(project.getId());
        Mockito.verify(portRepository, Mockito.times(1)).findWithProjectId(project.getId());
        Mockito.verify(resourceRepository, Mockito.times(1)).findWithProjectId(project.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).findWithPortId(port.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(project, result.getOutput().getProject());
    }

}
