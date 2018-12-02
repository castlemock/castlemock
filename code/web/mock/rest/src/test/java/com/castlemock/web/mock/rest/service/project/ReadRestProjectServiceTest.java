package com.castlemock.web.mock.rest.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.service.project.input.ReadRestProjectInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestProjectOutput;
import com.castlemock.core.mock.rest.model.project.RestApplicationGenerator;
import com.castlemock.core.mock.rest.model.project.RestMethodGenerator;
import com.castlemock.core.mock.rest.model.project.RestProjectGenerator;
import com.castlemock.core.mock.rest.model.project.RestResourceGenerator;
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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final RestProject project = RestProjectGenerator.generateRestProject();
        final RestApplication application = RestApplicationGenerator.generateRestApplication();
        final RestResource resource = RestResourceGenerator.generateRestResource();
        final RestMethod method = RestMethodGenerator.generateRestMethod();

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
