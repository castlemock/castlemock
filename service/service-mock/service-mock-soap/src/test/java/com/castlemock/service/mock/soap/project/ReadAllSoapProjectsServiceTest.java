package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.model.ServiceResult;
import com.castlemock.model.core.model.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapProjectTestBuilder;
import com.castlemock.service.mock.soap.project.input.ReadAllSoapProjectsInput;
import com.castlemock.service.mock.soap.project.output.ReadAllSoapProjectsOutput;
import com.castlemock.repository.soap.project.SoapProjectRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class ReadAllSoapProjectsServiceTest {

    @Mock
    private SoapProjectRepository repository;

    @InjectMocks
    private ReadAllSoapProjectsService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final List<SoapProject> projects = Arrays.asList(project);

        final ReadAllSoapProjectsInput input = ReadAllSoapProjectsInput.builder().build();
        final ServiceTask<ReadAllSoapProjectsInput> serviceTask = new ServiceTask<ReadAllSoapProjectsInput>(input);

        Mockito.when(repository.findAll()).thenReturn(projects);
        final ServiceResult<ReadAllSoapProjectsOutput> result = service.process(serviceTask);

        Mockito.verify(repository, Mockito.times(1)).findAll();

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(projects, result.getOutput().getProjects());
    }

}
