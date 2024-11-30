package com.castlemock.service.mock.rest.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.rest.domain.*;
import com.castlemock.repository.rest.project.RestMethodRepository;
import com.castlemock.repository.rest.project.RestResourceRepository;
import com.castlemock.service.mock.rest.project.input.UpdateRestResourcesForwardedEndpointInput;
import com.castlemock.service.mock.rest.project.output.UpdateRestResourcesForwardedEndpointOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class UpdateRestResourcesForwardedEndpointServiceTest {

    @Mock
    private RestResourceRepository resourceRepository;
    @Mock
    private RestMethodRepository methodRepository;

    @InjectMocks
    private UpdateRestResourcesForwardedEndpointService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testProcess(){
        final RestMethod method1 = RestMethodTestBuilder.builder()
                .id("method-1")
                .build();
        final RestMethod method2 = RestMethodTestBuilder.builder()
                .id("method-2")
                .build();
        final RestResource resource1 = RestResourceTestBuilder.builder()
                .id("resource-1")
                .build();
        final RestApplication application = RestApplicationTestBuilder.builder()
                .resources(List.of(resource1))
                .build();

        Mockito.when(methodRepository.findWithResourceId(resource1.getId())).thenReturn(List.of(method1, method2));

        final UpdateRestResourcesForwardedEndpointInput input = UpdateRestResourcesForwardedEndpointInput.builder()
                .projectId("project")
                .applicationId(application.getId())
                .resourceIds(Set.of(resource1.getId()))
                .forwardedEndpoint("new-endpoint")
                .build();
        final ServiceTask<UpdateRestResourcesForwardedEndpointInput> serviceTask = ServiceTask.of(input, "user");
        final ServiceResult<UpdateRestResourcesForwardedEndpointOutput> result = service.process(serviceTask);

        assertNotNull(result);
        Mockito.verify(methodRepository, Mockito.times(1)).findWithResourceId(resource1.getId());
        Mockito.verify(methodRepository, Mockito.times(2)).update(any(), any());
        Mockito.verify(methodRepository, Mockito.times(1)).update(eq(method1.getId()), any());
        Mockito.verify(methodRepository, Mockito.times(1)).update(eq(method2.getId()), any());
    }
}
