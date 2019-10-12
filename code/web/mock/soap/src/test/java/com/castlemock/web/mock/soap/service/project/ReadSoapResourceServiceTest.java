package com.castlemock.web.mock.soap.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.domain.SoapResource;
import com.castlemock.core.mock.soap.model.project.domain.SoapResourceTestBuilder;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapResourceInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapResourceOutput;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ReadSoapResourceServiceTest {

    @Mock
    private SoapResourceRepository resourceRepository;

    @InjectMocks
    private ReadSoapResourceService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapResource resource = SoapResourceTestBuilder.builder().build();
        final String projectId = "SOAP PROJECT";

        final ReadSoapResourceInput input = ReadSoapResourceInput.builder()
                .projectId(projectId)
                .resourceId(resource.getId())
                .build();
        final ServiceTask<ReadSoapResourceInput> serviceTask = new ServiceTask<ReadSoapResourceInput>(input);

        Mockito.when(resourceRepository.findOne(resource.getId())).thenReturn(resource);
        final ServiceResult<ReadSoapResourceOutput> result = service.process(serviceTask);

        Mockito.verify(resourceRepository, Mockito.times(1)).findOne(resource.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(resource, result.getOutput().getResource());
    }

}
