package com.castlemock.service.mock.rest.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestMockResponseTestBuilder;
import com.castlemock.repository.rest.project.RestMockResponseRepository;
import com.castlemock.service.mock.rest.project.input.ReadRestMockResponseInput;
import com.castlemock.service.mock.rest.project.output.ReadRestMockResponseOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class ReadRestMockResponseServiceTest {

    @Mock
    private RestMockResponseRepository mockResponseRepository;

    @InjectMocks
    private ReadRestMockResponseService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final String applicationId = "ApplicationId";
        final String resourceId = "ResourceId";
        final String methodId = "MethodId";
        final RestMockResponse mockResponse = RestMockResponseTestBuilder.builder().build();

        final ReadRestMockResponseInput input = ReadRestMockResponseInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .restMethodId(methodId)
                .restMockResponse(mockResponse.getId())
                .build();
        final ServiceTask<ReadRestMockResponseInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(mockResponseRepository.findOne(mockResponse.getId())).thenReturn(Optional.of(mockResponse));
        final ServiceResult<ReadRestMockResponseOutput> result = service.process(serviceTask);

        Mockito.verify(mockResponseRepository, Mockito.times(1)).findOne(mockResponse.getId());

        Assertions.assertNotNull(result.getOutput());

        final RestMockResponse restMockResponse = result.getOutput().getRestMockResponse()
                .orElse(null);

        Assertions.assertNotNull(restMockResponse);
        Assertions.assertEquals(mockResponse, restMockResponse);
    }

}
