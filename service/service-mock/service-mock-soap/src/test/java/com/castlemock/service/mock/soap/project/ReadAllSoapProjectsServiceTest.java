package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapProjectTestBuilder;
import com.castlemock.repository.soap.project.SoapProjectRepository;
import com.castlemock.service.mock.soap.project.input.ReadAllSoapProjectsInput;
import com.castlemock.service.mock.soap.project.output.ReadAllSoapProjectsOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class ReadAllSoapProjectsServiceTest {

    @Mock
    private SoapProjectRepository repository;

    @InjectMocks
    private ReadAllSoapProjectsService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final List<SoapProject> projects = List.of(project);

        final ReadAllSoapProjectsInput input = ReadAllSoapProjectsInput.builder().build();
        final ServiceTask<ReadAllSoapProjectsInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(repository.findAll()).thenReturn(projects);
        final ServiceResult<ReadAllSoapProjectsOutput> result = service.process(serviceTask);

        Mockito.verify(repository, Mockito.times(1)).findAll();

        Assertions.assertNotNull(result.getOutput());
        Assertions.assertEquals(projects, result.getOutput().getProjects());
    }

}
