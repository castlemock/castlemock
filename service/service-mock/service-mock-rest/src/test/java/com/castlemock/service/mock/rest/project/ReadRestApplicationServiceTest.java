package com.castlemock.service.mock.rest.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestApplicationTestBuilder;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodTestBuilder;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.model.mock.rest.domain.RestResourceTestBuilder;
import com.castlemock.repository.rest.project.RestApplicationRepository;
import com.castlemock.repository.rest.project.RestMethodRepository;
import com.castlemock.repository.rest.project.RestProjectRepository;
import com.castlemock.repository.rest.project.RestResourceRepository;
import com.castlemock.service.mock.rest.project.input.ReadRestApplicationInput;
import com.castlemock.service.mock.rest.project.output.ReadRestApplicationOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

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

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final RestApplication application = RestApplicationTestBuilder.builder().build();
        final RestResource resource = RestResourceTestBuilder.builder().build();
        final RestMethod method = RestMethodTestBuilder.builder().build();

        final ReadRestApplicationInput input = ReadRestApplicationInput.builder()
                .projectId(projectId)
                .applicationId(application.getId())
                .build();
        final ServiceTask<ReadRestApplicationInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(applicationRepository.findOne(application.getId())).thenReturn(Optional.of(application));
        Mockito.when(resourceRepository.findWithApplicationId(application.getId())).thenReturn(List.of(resource));
        Mockito.when(methodRepository.findWithResourceId(resource.getId())).thenReturn(List.of(method));
        final ServiceResult<ReadRestApplicationOutput> result = service.process(serviceTask);

        Mockito.verify(applicationRepository, Mockito.times(1)).findOne(application.getId());
        Mockito.verify(resourceRepository, Mockito.times(1)).findWithApplicationId(application.getId());
        Mockito.verify(methodRepository, Mockito.times(1)).findWithResourceId(resource.getId());


        // TODO FIX
        Assertions.assertNotNull(result.getOutput());
        //Assertions.assertEquals(application, result.getOutput().getRestApplication());
    }

}
