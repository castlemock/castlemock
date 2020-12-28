package com.castlemock.service.mock.rest.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestApplicationTestBuilder;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodTestBuilder;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.model.mock.rest.domain.RestProjectTestBuilder;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.model.mock.rest.domain.RestResourceTestBuilder;
import com.castlemock.service.mock.rest.project.input.ReadRestProjectInput;
import com.castlemock.service.mock.rest.project.output.ReadRestProjectOutput;
import com.castlemock.repository.rest.project.RestApplicationRepository;
import com.castlemock.repository.rest.project.RestMethodRepository;
import com.castlemock.repository.rest.project.RestProjectRepository;
import com.castlemock.repository.rest.project.RestResourceRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

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

    @Before
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
                .restProjectId(project.getId())
                .build();
        final ServiceTask<ReadRestProjectInput> serviceTask = new ServiceTask<ReadRestProjectInput>(input);

        Mockito.when(repository.findOne(project.getId())).thenReturn(project);
        Mockito.when(applicationRepository.findWithProjectId(project.getId())).thenReturn(Arrays.asList(application));
        Mockito.when(resourceRepository.findIdsWithApplicationId(application.getId())).thenReturn(Arrays.asList(resource.getId()));
        Mockito.when(methodRepository.findWithResourceId(resource.getId())).thenReturn(Arrays.asList(method));
        final ServiceResult<ReadRestProjectOutput> result = service.process(serviceTask);

        Mockito.verify(repository, Mockito.times(1)).findOne(project.getId());
        Mockito.verify(applicationRepository, Mockito.times(1)).findWithProjectId(project.getId());
        Mockito.verify(resourceRepository, Mockito.times(1)).findIdsWithApplicationId(application.getId());
        Mockito.verify(methodRepository, Mockito.times(1)).findWithResourceId(resource.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(project, result.getOutput().getRestProject());
    }

}
