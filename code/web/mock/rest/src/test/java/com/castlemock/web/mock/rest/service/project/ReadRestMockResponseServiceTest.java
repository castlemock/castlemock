package com.castlemock.web.mock.rest.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.service.project.input.ReadRestMockResponseInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestMockResponseOutput;
import com.castlemock.web.mock.rest.model.project.RestMockResponseGenerator;
import com.castlemock.web.mock.rest.repository.project.RestMockResponseRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ReadRestMockResponseServiceTest {

    @Mock
    private RestMockResponseRepository mockResponseRepository;

    @InjectMocks
    private ReadRestMockResponseService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final String applicationId = "ApplicationId";
        final String resourceId = "ResourceId";
        final String methodId = "MethodId";
        final RestMockResponse mockResponse = RestMockResponseGenerator.generateRestMockResponse();

        final ReadRestMockResponseInput input = ReadRestMockResponseInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .restMethodId(methodId)
                .restMockResponse(mockResponse.getId())
                .build();
        final ServiceTask<ReadRestMockResponseInput> serviceTask = new ServiceTask<ReadRestMockResponseInput>(input);

        Mockito.when(mockResponseRepository.findOne(mockResponse.getId())).thenReturn(mockResponse);
        final ServiceResult<ReadRestMockResponseOutput> result = service.process(serviceTask);

        Mockito.verify(mockResponseRepository, Mockito.times(1)).findOne(mockResponse.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(mockResponse, result.getOutput().getRestMockResponse());
    }

}
