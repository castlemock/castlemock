package com.castlemock.web.mock.rest.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.service.project.input.ReadAllRestProjectsInput;
import com.castlemock.core.mock.rest.service.project.output.ReadAllRestProjectsOutput;
import com.castlemock.web.mock.rest.model.project.RestProjectGenerator;
import com.castlemock.web.mock.rest.repository.project.RestProjectRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class ReadAllRestProjectsServiceTest {

    @Mock
    private RestProjectRepository repository;

    @InjectMocks
    private ReadAllRestProjectsService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final RestProject project = RestProjectGenerator.generateRestProject();
        final List<RestProject> projects = Arrays.asList(project);

        final ReadAllRestProjectsInput input = ReadAllRestProjectsInput.builder().build();
        final ServiceTask<ReadAllRestProjectsInput> serviceTask = new ServiceTask<ReadAllRestProjectsInput>(input);

        Mockito.when(repository.findAll()).thenReturn(projects);
        final ServiceResult<ReadAllRestProjectsOutput> result = service.process(serviceTask);

        Mockito.verify(repository, Mockito.times(1)).findAll();

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(projects, result.getOutput().getRestProjects());
    }

}
