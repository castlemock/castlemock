package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.*;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.repository.soap.project.SoapPortRepository;
import com.castlemock.repository.soap.project.SoapProjectRepository;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import com.castlemock.service.mock.soap.project.input.ReadSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapProjectOutput;
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

    @BeforeEach
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

        Mockito.when(repository.findOne(project.getId())).thenReturn(Optional.of(project));
        Mockito.when(portRepository.findWithProjectId(project.getId())).thenReturn(List.of(port));
        Mockito.when(resourceRepository.findWithProjectId(project.getId())).thenReturn(Collections.singletonList(resource));
        Mockito.when(operationRepository.findWithPortId(port.getId())).thenReturn(List.of(operation));
        final ServiceResult<ReadSoapProjectOutput> result = service.process(serviceTask);

        Mockito.verify(repository, Mockito.times(1)).findOne(project.getId());
        Mockito.verify(portRepository, Mockito.times(1)).findWithProjectId(project.getId());
        Mockito.verify(resourceRepository, Mockito.times(1)).findWithProjectId(project.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).findWithPortId(port.getId());

        Assertions.assertNotNull(result.getOutput());

        final SoapProject soapProject = result.getOutput().getProject()
                .orElse(null);

        Assertions.assertNotNull(soapProject);
        Assertions.assertEquals(project, soapProject);
    }

}
