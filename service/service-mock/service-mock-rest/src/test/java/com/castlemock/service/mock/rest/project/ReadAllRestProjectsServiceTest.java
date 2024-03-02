package com.castlemock.service.mock.rest.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.model.mock.rest.domain.RestProjectTestBuilder;
import com.castlemock.repository.rest.project.RestProjectRepository;
import com.castlemock.service.mock.rest.project.input.ReadAllRestProjectsInput;
import com.castlemock.service.mock.rest.project.output.ReadAllRestProjectsOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class ReadAllRestProjectsServiceTest {

    @Mock
    private RestProjectRepository repository;

    @InjectMocks
    private ReadAllRestProjectsService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final List<RestProject> projects = List.of(project);

        final ReadAllRestProjectsInput input = ReadAllRestProjectsInput.builder().build();
        final ServiceTask<ReadAllRestProjectsInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(repository.findAll()).thenReturn(projects);
        final ServiceResult<ReadAllRestProjectsOutput> result = service.process(serviceTask);

        Mockito.verify(repository, Mockito.times(1)).findAll();

        Assertions.assertNotNull(result.getOutput());
        Assertions.assertEquals(projects, result.getOutput().getProjects());
    }

}
