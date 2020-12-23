package com.castlemock.web.mock.rest.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestApplicationTestBuilder;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodTestBuilder;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.model.project.domain.RestResourceTestBuilder;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestResourcesForwardedEndpointInput;
import com.castlemock.core.mock.rest.service.project.output.UpdateRestResourcesForwardedEndpointOutput;
import com.castlemock.repository.rest.project.RestMethodRepository;
import com.castlemock.repository.rest.project.RestResourceRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class UpdateRestResourcesForwardedEndpointServiceTest {

    @Mock
    private RestResourceRepository resourceRepository;
    @Mock
    private RestMethodRepository methodRepository;

    @InjectMocks
    private UpdateRestResourcesForwardedEndpointService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
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
                .resources(ImmutableList.of(resource1))
                .build();

        Mockito.when(methodRepository.findWithResourceId(resource1.getId())).thenReturn(ImmutableList.of(method1, method2));

        final UpdateRestResourcesForwardedEndpointInput input = UpdateRestResourcesForwardedEndpointInput.builder()
                .projectId("project")
                .applicationId(application.getId())
                .resourceIds(ImmutableSet.of(resource1.getId()))
                .forwardedEndpoint("new-endpoint")
                .build();
        final ServiceTask<UpdateRestResourcesForwardedEndpointInput> serviceTask = new ServiceTask<>(input);
        final ServiceResult<UpdateRestResourcesForwardedEndpointOutput> result = service.process(serviceTask);

        assertNotNull(result);
        Mockito.verify(methodRepository, Mockito.times(1)).findWithResourceId(resource1.getId());
        Mockito.verify(methodRepository, Mockito.times(2)).update(any(), any());
        Mockito.verify(methodRepository, Mockito.times(1)).update(eq(method1.getId()), any());
        Mockito.verify(methodRepository, Mockito.times(1)).update(eq(method2.getId()), any());
    }
}
