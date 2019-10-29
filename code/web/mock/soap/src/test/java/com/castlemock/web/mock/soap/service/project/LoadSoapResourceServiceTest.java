package com.castlemock.web.mock.soap.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.domain.SoapResource;
import com.castlemock.core.mock.soap.model.project.domain.SoapResourceTestBuilder;
import com.castlemock.core.mock.soap.service.project.input.LoadSoapResourceInput;
import com.castlemock.core.mock.soap.service.project.output.LoadSoapResourceOutput;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class LoadSoapResourceServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapResourceRepository resourceRepository;

    @InjectMocks
    private LoadSoapResourceService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
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
        final ServiceTask<LoadSoapResourceInput> serviceTask = new ServiceTask<LoadSoapResourceInput>(input);
        final ServiceResult<LoadSoapResourceOutput> serviceResult = service.process(serviceTask);

        Assert.assertNotNull(serviceResult.getOutput());
        Assert.assertEquals(resourceContent, serviceResult.getOutput().getResource());

        Mockito.verify(resourceRepository, Mockito.times(1)).loadSoapResource(soapResource.getId());
    }
}
