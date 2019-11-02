package com.castlemock.web.mock.rest.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodTestBuilder;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseTestBuilder;
import com.castlemock.core.mock.rest.service.project.input.ReadRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestMethodOutput;
import com.castlemock.repository.rest.project.RestMethodRepository;
import com.castlemock.repository.rest.project.RestMockResponseRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

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
        final String defaultMockResponseId = "MockResponseId";
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
                        .restResourceId("ResourceId")
                        .restMethodId(method.getId())
                        .build();
        final ServiceTask<ReadRestMethodInput> serviceTask = new ServiceTask<ReadRestMethodInput>(input);

        Mockito.when(methodRepository.findOne(method.getId())).thenReturn(method);
        Mockito.when(mockResponseRepository.findWithMethodId(method.getId())).thenReturn(Collections.singletonList(mockResponse));

        final ServiceResult<ReadRestMethodOutput> result = service.process(serviceTask);

        Mockito.verify(methodRepository, Mockito.times(1)).findOne(method.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).findWithMethodId(method.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(method, result.getOutput().getRestMethod());
        Assert.assertEquals(mockResponse.getName(), method.getDefaultResponseName());
    }

    @Test
    public void testProcessMissingMockResponse(){
        final RestMethod method = RestMethodTestBuilder.builder().build();
        final ReadRestMethodInput input =
                ReadRestMethodInput.builder()
                        .restProjectId("ProjectId")
                        .restApplicationId("ApplicationId")
                        .restResourceId("ResourceId")
                        .restMethodId(method.getId())
                        .build();
        final ServiceTask<ReadRestMethodInput> serviceTask = new ServiceTask<ReadRestMethodInput>(input);

        Mockito.when(methodRepository.findOne(method.getId())).thenReturn(method);
        Mockito.when(mockResponseRepository.findWithMethodId(method.getId())).thenReturn(Collections.emptyList());

        final ServiceResult<ReadRestMethodOutput> result = service.process(serviceTask);

        Mockito.verify(methodRepository, Mockito.times(1)).findOne(method.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).findWithMethodId(method.getId());

        Assert.assertNotNull(result.getOutput());
        Assert.assertEquals(method, result.getOutput().getRestMethod());
        Assert.assertEquals("", method.getDefaultResponseName());
    }
}
