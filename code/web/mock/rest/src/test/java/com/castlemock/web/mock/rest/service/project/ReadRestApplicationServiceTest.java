package com.castlemock.web.mock.rest.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.service.project.input.ReadRestApplicationInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestApplicationOutput;
import com.castlemock.web.mock.rest.model.project.RestApplicationGenerator;
import com.castlemock.web.mock.rest.model.project.RestMethodGenerator;
import com.castlemock.web.mock.rest.model.project.RestResourceGenerator;
import com.castlemock.web.mock.rest.repository.project.RestApplicationRepository;
import com.castlemock.web.mock.rest.repository.project.RestMethodRepository;
import com.castlemock.web.mock.rest.repository.project.RestProjectRepository;
import com.castlemock.web.mock.rest.repository.project.RestResourceRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

public class ReadRestApplicationServiceTest {

    @Mock
    private RestProjectRepository repository;

    @Mock
    private RestApplicationRepository applicationRepository;

    @Mock
    private RestResourceRepository resourceRepository;

    @Mock
    private RestMethodRepository methodRepository;

    @InjectMocks
    private ReadRestApplicationService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final RestApplication application = RestApplicationGenerator.generateRestApplication();
        final RestResource resource = RestResourceGenerator.generateRestResource();
        final RestMethod method = RestMethodGenerator.generateRestMethod();

        final ReadRestApplicationInput input = ReadRestApplicationInput.builder()
                .restProjectId(projectId)
                .restApplicationId(application.getId())
                .build();
        final ServiceTask<ReadRestApplicationInput> serviceTask = new ServiceTask<ReadRestApplicationInput>(input);

        Mockito.when(applicationRepository.findOne(application.getId())).thenReturn(application);
        Mockito.when(resourceRepository.findWithApplicationId(application.getId())).thenReturn(Arrays.asList(resource));
        Mockito.when(methodRepository.findWithResourceId(resource.getId())).thenReturn(Arrays.asList(method));
        final ServiceResult<ReadRestApplicationOutput> result = service.process(serviceTask);

        Mockito.verify(applicationRepository, Mockito.times(1)).findOne(application.getId());
        Mockito.verify(resourceRepository, Mockito.times(1)).findWithApplicationId(application.getId());
        Mockito.verify(methodRepository, Mockito.times(1)).findWithResourceId(resource.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(application, result.getOutput().getRestApplication());
    }

}
