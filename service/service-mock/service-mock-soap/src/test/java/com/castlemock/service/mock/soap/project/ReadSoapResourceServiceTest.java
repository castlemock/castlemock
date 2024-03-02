package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.model.mock.soap.domain.SoapResourceTestBuilder;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import com.castlemock.service.mock.soap.project.input.ReadSoapResourceInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapResourceOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class ReadSoapResourceServiceTest {

    @Mock
    private SoapResourceRepository resourceRepository;

    @InjectMocks
    private ReadSoapResourceService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapResource resource = SoapResourceTestBuilder.builder().build();
        final String projectId = "SOAP PROJECT";

        final ReadSoapResourceInput input = ReadSoapResourceInput.builder()
                .projectId(projectId)
                .resourceId(resource.getId())
                .build();
        final ServiceTask<ReadSoapResourceInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(resourceRepository.findOne(resource.getId())).thenReturn(Optional.of(resource));
        final ServiceResult<ReadSoapResourceOutput> result = service.process(serviceTask);

        Mockito.verify(resourceRepository, Mockito.times(1)).findOne(resource.getId());

        Assertions.assertNotNull(result.getOutput());

        final SoapResource returnedSoapResource = result.getOutput().getResource()
                .orElse(null);

        Assertions.assertNotNull(returnedSoapResource);
        Assertions.assertEquals(resource, returnedSoapResource);
    }

}
