package com.fortmocks.web.mock.rest.model.project.service;

import com.fortmocks.core.basis.model.Repository;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.mock.rest.model.project.domain.*;
import com.fortmocks.core.mock.rest.model.project.dto.RestMockResponseDto;
import com.fortmocks.core.mock.rest.model.project.service.message.input.CreateRecordedRestMockResponseInput;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRecordedRestMockResponseServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private CreateRecordedRestMockResponseService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final RestProject restProject = new RestProject();
        final RestApplication restApplication = new RestApplication();
        final RestResource restResource = new RestResource();
        final RestMethod restMethod = new RestMethod();
        restMethod.setId(1L);

        restProject.setRestApplications(new ArrayList<RestApplication>());
        restApplication.setRestResources(new ArrayList<RestResource>());
        restResource.setRestMethods(new ArrayList<RestMethod>());
        restMethod.setRestMockResponses(new ArrayList<RestMockResponse>());

        restProject.getRestApplications().add(restApplication);
        restApplication.getRestResources().add(restResource);
        restResource.getRestMethods().add(restMethod);

        List<RestProject> restProjects = new ArrayList<RestProject>();
        restProjects.add(restProject);

        Mockito.when(repository.findAll()).thenReturn(restProjects);

    }

    @Test
    public void testProcess(){
        final Long restMethodId = 1L;
        final RestMockResponseDto restMockResponseDto = Mockito.mock(RestMockResponseDto.class);
        final CreateRecordedRestMockResponseInput input = new CreateRecordedRestMockResponseInput(restMethodId, restMockResponseDto);
        final ServiceTask<CreateRecordedRestMockResponseInput> serviceTask = new ServiceTask<>(input);
        service.process(serviceTask);
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(RestProject.class));
    }
}
