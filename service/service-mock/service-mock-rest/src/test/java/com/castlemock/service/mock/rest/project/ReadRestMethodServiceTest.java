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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

public class ReadRestMethodServiceTest {

    @Mock
    private RestResourceRepository resourceRepository;

    @Mock
    private RestMethodRepository methodRepository;

    @Mock
    private RestMockResponseRepository mockResponseRepository;

    @InjectMocks
    private ReadRestMethodService service;

    @BeforeEach
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
        final ServiceTask<ReadRestMethodInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(resourceRepository.findOne(resource.getId())).thenReturn(Optional.of(resource));
        Mockito.when(methodRepository.findOne(method.getId())).thenReturn(Optional.of(method));
        Mockito.when(mockResponseRepository.findWithMethodId(method.getId())).thenReturn(Collections.singletonList(mockResponse));

        final ServiceResult<ReadRestMethodOutput> result = service.process(serviceTask);

        Mockito.verify(resourceRepository, Mockito.times(1)).findOne(resource.getId());
        Mockito.verify(methodRepository, Mockito.times(1)).findOne(method.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).findWithMethodId(method.getId());

        Assertions.assertNotNull(result.getOutput());
        // TODO FIX
        //Assertions.assertEquals(method, result.getOutput().getRestMethod());
        //Assertions.assertEquals(mockResponse.getName(), method.getDefaultResponseName());
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
        final ServiceTask<ReadRestMethodInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(resourceRepository.findOne(resource.getId())).thenReturn(Optional.of(resource));
        Mockito.when(methodRepository.findOne(method.getId())).thenReturn(Optional.of(method));
        Mockito.when(mockResponseRepository.findWithMethodId(method.getId())).thenReturn(Collections.emptyList());

        final ServiceResult<ReadRestMethodOutput> result = service.process(serviceTask);

        Mockito.verify(resourceRepository, Mockito.times(1)).findOne(resource.getId());
        Mockito.verify(methodRepository, Mockito.times(1)).findOne(method.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).findWithMethodId(method.getId());

        // TODO FIX

        Assertions.assertNotNull(result.getOutput());
        //Assertions.assertEquals(method, result.getOutput().getRestMethod());
        Assertions.assertEquals("", method.getDefaultResponseName().orElse(null));
    }
}
