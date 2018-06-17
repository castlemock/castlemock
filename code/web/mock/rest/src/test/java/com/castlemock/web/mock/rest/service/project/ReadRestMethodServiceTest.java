package com.castlemock.web.mock.rest.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.service.project.input.ReadRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestMethodOutput;
import com.castlemock.web.mock.rest.model.project.RestMethodGenerator;
import com.castlemock.web.mock.rest.model.project.RestMockResponseGenerator;
import com.castlemock.web.mock.rest.repository.project.RestMethodRepository;
import com.castlemock.web.mock.rest.repository.project.RestMockResponseRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

public class ReadRestMethodServiceTest {

    @Mock
    private RestMethodRepository methodRepository;

    @Mock
    private RestMockResponseRepository mockResponseRepository;

    @InjectMocks
    private ReadRestMethodService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final String applicationId = "ApplicationId";
        final String resourceId = "ResourceId";
        final RestMethod method = RestMethodGenerator.generateRestMethod();
        final RestMockResponse mockResponse = RestMockResponseGenerator.generateRestMockResponse();

        final ReadRestMethodInput input =
                new ReadRestMethodInput(projectId, applicationId, resourceId, method.getId());
        final ServiceTask<ReadRestMethodInput> serviceTask = new ServiceTask<ReadRestMethodInput>(input);

        Mockito.when(methodRepository.findOne(method.getId())).thenReturn(method);
        Mockito.when(mockResponseRepository.findWithMethodId(method.getId())).thenReturn(Arrays.asList(mockResponse));
        final ServiceResult<ReadRestMethodOutput> result = service.process(serviceTask);

        Mockito.verify(methodRepository, Mockito.times(1)).findOne(method.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).findWithMethodId(method.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(method, result.getOutput().getRestMethod());
    }

}
