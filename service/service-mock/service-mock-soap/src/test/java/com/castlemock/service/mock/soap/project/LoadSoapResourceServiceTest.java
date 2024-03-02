package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.model.mock.soap.domain.SoapResourceTestBuilder;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import com.castlemock.service.mock.soap.project.input.LoadSoapResourceInput;
import com.castlemock.service.mock.soap.project.output.LoadSoapResourceOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class LoadSoapResourceServiceTest {


    @Mock
    private SoapResourceRepository resourceRepository;

    @InjectMocks
    private LoadSoapResourceService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapResource soapResource = SoapResourceTestBuilder.builder().build();
        final String resourceContent = "Resource content";
        Mockito.when(resourceRepository.loadSoapResource(soapResource.getId())).thenReturn(resourceContent);


        final LoadSoapResourceInput input = LoadSoapResourceInput.builder()
                .projectId("Project id")
                .resourceId(soapResource.getId())
                .build();
        final ServiceTask<LoadSoapResourceInput> serviceTask = ServiceTask.of(input, "user");
        final ServiceResult<LoadSoapResourceOutput> serviceResult = service.process(serviceTask);

        Assertions.assertNotNull(serviceResult.getOutput());

        final String content = serviceResult.getOutput().getResource()
                .orElse(null);

        Assertions.assertNotNull(content);
        Assertions.assertEquals(resourceContent, content);

        Mockito.verify(resourceRepository, Mockito.times(1)).loadSoapResource(soapResource.getId());
    }
}
