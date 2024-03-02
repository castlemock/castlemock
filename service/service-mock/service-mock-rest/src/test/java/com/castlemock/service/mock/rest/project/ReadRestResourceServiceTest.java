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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class ReadRestResourceServiceTest {

    @Mock
    private RestResourceRepository resourceRepository;

    @Mock
    private RestMethodRepository methodRepository;

    @InjectMocks
    private ReadRestResourceService service;

    @BeforeEach
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
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resource.getId())
                .build();
        final ServiceTask<ReadRestResourceInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(resourceRepository.findOne(resource.getId())).thenReturn(Optional.of(resource));
        Mockito.when(methodRepository.findWithResourceId(resource.getId())).thenReturn(List.of(method));
        final ServiceResult<ReadRestResourceOutput> result = service.process(serviceTask);

        Mockito.verify(resourceRepository, Mockito.times(1)).findOne(resource.getId());
        Mockito.verify(methodRepository, Mockito.times(1)).findWithResourceId(resource.getId());

        Assertions.assertNotNull(result.getOutput());

        final RestResource restResource = result.getOutput()
                .getResource()
                .orElse(null);

        Assertions.assertNotNull(restResource);
        Assertions.assertEquals(resource, restResource);
    }

}
