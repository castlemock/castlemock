package com.castlemock.service.mock.rest.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.rest.domain.*;
import com.castlemock.repository.rest.project.RestApplicationRepository;
import com.castlemock.repository.rest.project.RestMethodRepository;
import com.castlemock.repository.rest.project.RestProjectRepository;
import com.castlemock.repository.rest.project.RestResourceRepository;
import com.castlemock.service.mock.rest.project.input.ReadRestProjectInput;
import com.castlemock.service.mock.rest.project.output.ReadRestProjectOutput;
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

public class ReadRestProjectServiceTest {

    @Mock
    private RestProjectRepository repository;

    @Mock
    private RestApplicationRepository applicationRepository;

    @Mock
    private RestResourceRepository resourceRepository;

    @Mock
    private RestMethodRepository methodRepository;

    @InjectMocks
    private ReadRestProjectService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final RestApplication application = RestApplicationTestBuilder.builder().build();
        final RestResource resource = RestResourceTestBuilder.builder().build();
        final RestMethod method = RestMethodTestBuilder.builder().build();

        final ReadRestProjectInput input = ReadRestProjectInput.builder()
                .projectId(project.getId())
                .build();
        final ServiceTask<ReadRestProjectInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(repository.findOne(project.getId())).thenReturn(Optional.of(project));
        Mockito.when(applicationRepository.findWithProjectId(project.getId())).thenReturn(List.of(application));
        Mockito.when(resourceRepository.findIdsWithApplicationId(application.getId())).thenReturn(Collections.singletonList(resource.getId()));
        Mockito.when(methodRepository.findWithResourceId(resource.getId())).thenReturn(List.of(method));
        final ServiceResult<ReadRestProjectOutput> result = service.process(serviceTask);

        Mockito.verify(repository, Mockito.times(1)).findOne(project.getId());
        Mockito.verify(applicationRepository, Mockito.times(1)).findWithProjectId(project.getId());
        Mockito.verify(resourceRepository, Mockito.times(1)).findIdsWithApplicationId(application.getId());
        Mockito.verify(methodRepository, Mockito.times(1)).findWithResourceId(resource.getId());

        Assertions.assertNotNull(result.getOutput());
        //Assertions.assertEquals(project, result.getOutput().getRestProject());
    }

}
