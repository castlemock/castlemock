package com.castlemock.web.mock.soap.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.service.project.input.ReadAllSoapProjectsInput;
import com.castlemock.core.mock.soap.service.project.output.ReadAllSoapProjectsOutput;
import com.castlemock.web.mock.soap.model.project.SoapProjectGenerator;
import com.castlemock.web.mock.soap.repository.project.SoapProjectRepository;
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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProject project = SoapProjectGenerator.generateSoapProject();
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
