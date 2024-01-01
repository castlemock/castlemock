package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapPortTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapProjectTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.model.mock.soap.domain.SoapResourceTestBuilder;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.repository.soap.project.SoapPortRepository;
import com.castlemock.repository.soap.project.SoapProjectRepository;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import com.castlemock.service.mock.soap.project.input.ReadSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapProjectOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final SoapPort port = SoapPortTestBuilder.builder().build();
        final SoapResource resource = SoapResourceTestBuilder.builder().build();
        final SoapOperation operation = SoapOperationTestBuilder.builder().build();

        final ReadSoapProjectInput input = ReadSoapProjectInput.builder()
                .projectId(project.getId())
                .build();
        final ServiceTask<ReadSoapProjectInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(repository.findOne(project.getId())).thenReturn(project);
        Mockito.when(portRepository.findWithProjectId(project.getId())).thenReturn(List.of(port));
        Mockito.when(resourceRepository.findWithProjectId(project.getId())).thenReturn(Collections.singletonList(resource));
        Mockito.when(operationRepository.findWithPortId(port.getId())).thenReturn(List.of(operation));
        final ServiceResult<ReadSoapProjectOutput> result = service.process(serviceTask);

        Mockito.verify(repository, Mockito.times(1)).findOne(project.getId());
        Mockito.verify(portRepository, Mockito.times(1)).findWithProjectId(project.getId());
        Mockito.verify(resourceRepository, Mockito.times(1)).findWithProjectId(project.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).findWithPortId(port.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(project, result.getOutput().getProject());
    }

}
