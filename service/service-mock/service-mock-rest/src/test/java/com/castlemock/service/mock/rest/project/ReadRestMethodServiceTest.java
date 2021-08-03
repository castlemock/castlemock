package com.castlemock.service.mock.rest.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodTestBuilder;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestMockResponseTestBuilder;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.model.mock.rest.domain.RestResourceTestBuilder;
import com.castlemock.repository.rest.project.RestMethodRepository;
import com.castlemock.repository.rest.project.RestMockResponseRepository;
import com.castlemock.repository.rest.project.RestResourceRepository;
import com.castlemock.service.mock.rest.project.input.ReadRestMethodInput;
import com.castlemock.service.mock.rest.project.output.ReadRestMethodOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

public class ReadRestMethodServiceTest {

    @Mock
    private RestResourceRepository resourceRepository;

    @Mock
    private RestMethodRepository methodRepository;

    @Mock
    private RestMockResponseRepository mockResponseRepository;

    @InjectMocks
    private ReadRestMethodService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final String defaultMockResponseId = "MockResponseId";
        final RestResource resource = RestResourceTestBuilder.builder().build();
        final RestMethod method = RestMethodTestBuilder.builder()
                .defaultMockResponseId(defaultMockResponseId)
                .build();
        final RestMockResponse mockResponse = RestMockResponseTestBuilder.builder()
                .id(defaultMockResponseId)
                .build();
        final ReadRestMethodInput input =
                ReadRestMethodInput.builder()
                        .restProjectId("ProjectId")
                        .restApplicationId("ApplicationId")
                        .restResourceId(resource.getId())
                        .restMethodId(method.getId())
                        .build();
        final ServiceTask<ReadRestMethodInput> serviceTask = new ServiceTask<ReadRestMethodInput>(input);

        Mockito.when(resourceRepository.findOne(resource.getId())).thenReturn(resource);
        Mockito.when(methodRepository.findOne(method.getId())).thenReturn(method);
        Mockito.when(mockResponseRepository.findWithMethodId(method.getId())).thenReturn(Collections.singletonList(mockResponse));

        final ServiceResult<ReadRestMethodOutput> result = service.process(serviceTask);

        Mockito.verify(resourceRepository, Mockito.times(1)).findOne(resource.getId());
        Mockito.verify(methodRepository, Mockito.times(1)).findOne(method.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).findWithMethodId(method.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(method, result.getOutput().getRestMethod());
        Assert.assertEquals(mockResponse.getName(), method.getDefaultResponseName());
    }

    @Test
    public void testProcessMissingMockResponse(){
        final RestResource resource = RestResourceTestBuilder.builder().build();
        final RestMethod method = RestMethodTestBuilder.builder().build();
        final ReadRestMethodInput input =
                ReadRestMethodInput.builder()
                        .restProjectId("ProjectId")
                        .restApplicationId("ApplicationId")
                        .restResourceId(resource.getId())
                        .restMethodId(method.getId())
                        .build();
        final ServiceTask<ReadRestMethodInput> serviceTask = new ServiceTask<ReadRestMethodInput>(input);

        Mockito.when(resourceRepository.findOne(resource.getId())).thenReturn(resource);
        Mockito.when(methodRepository.findOne(method.getId())).thenReturn(method);
        Mockito.when(mockResponseRepository.findWithMethodId(method.getId())).thenReturn(Collections.emptyList());

        final ServiceResult<ReadRestMethodOutput> result = service.process(serviceTask);

        Mockito.verify(resourceRepository, Mockito.times(1)).findOne(resource.getId());
        Mockito.verify(methodRepository, Mockito.times(1)).findOne(method.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).findWithMethodId(method.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(method, result.getOutput().getRestMethod());
        Assert.assertEquals("", method.getDefaultResponseName());
    }
}
