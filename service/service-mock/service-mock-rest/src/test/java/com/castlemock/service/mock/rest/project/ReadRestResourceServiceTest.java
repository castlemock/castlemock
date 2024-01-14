package com.castlemock.service.mock.rest.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodTestBuilder;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.model.mock.rest.domain.RestResourceTestBuilder;
import com.castlemock.repository.rest.project.RestMethodRepository;
import com.castlemock.repository.rest.project.RestResourceRepository;
import com.castlemock.service.mock.rest.project.input.ReadRestResourceInput;
import com.castlemock.service.mock.rest.project.output.ReadRestResourceOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class ReadRestResourceServiceTest {

    @Mock
    private RestResourceRepository resourceRepository;

    @Mock
    private RestMethodRepository methodRepository;

    @InjectMocks
    private ReadRestResourceService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final String applicationId = "ApplicationId";
        final RestResource resource = RestResourceTestBuilder.builder().build();
        final RestMethod method = RestMethodTestBuilder.builder().build();

        final ReadRestResourceInput input = ReadRestResourceInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resource.getId())
                .build();
        final ServiceTask<ReadRestResourceInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(resourceRepository.findOne(resource.getId())).thenReturn(resource);
        Mockito.when(methodRepository.findWithResourceId(resource.getId())).thenReturn(List.of(method));
        final ServiceResult<ReadRestResourceOutput> result = service.process(serviceTask);

        Mockito.verify(resourceRepository, Mockito.times(1)).findOne(resource.getId());
        Mockito.verify(methodRepository, Mockito.times(1)).findWithResourceId(resource.getId());

        Assert.assertNotNull(result.getOutput());

        final RestResource restResource = result.getOutput()
                .getRestResource()
                .orElse(null);

        Assert.assertNotNull(restResource);
        Assert.assertEquals(resource, restResource);
    }

}
