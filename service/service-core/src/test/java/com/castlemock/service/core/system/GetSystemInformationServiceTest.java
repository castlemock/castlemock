package com.castlemock.service.core.system;

import com.castlemock.model.core.Environment;
import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.repository.Profiles;
import com.castlemock.service.core.system.input.GetSystemInformationInput;
import com.castlemock.service.core.system.output.GetSystemInformationOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class GetSystemInformationServiceTest {


    @InjectMocks
    private GetSystemInformationService service;

    @Mock
    private Environment environment;

    @Mock
    private org.springframework.core.env.Environment springEnvironment;

    @BeforeEach
    public void setup() {
        final org.springframework.core.env.Profiles fileProfiles =
                org.springframework.core.env.Profiles.of(Profiles.FILE);

        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "castleMockHomeDirectory", "baseDirector");

        Mockito.when(environment.getServerBuilt()).thenReturn("");
        Mockito.when(environment.getServerInfo()).thenReturn("");
        Mockito.when(environment.getServerNumber()).thenReturn("");
        Mockito.when(springEnvironment.acceptsProfiles(fileProfiles)).thenReturn(true);
    }

    @Test
    public void testProcess(){
        final GetSystemInformationInput input = new GetSystemInformationInput();
        final ServiceTask<GetSystemInformationInput> serviceTask = ServiceTask.of(input, "user");
        final ServiceResult<GetSystemInformationOutput> output = service.process(serviceTask);
        Assertions.assertNotNull(output);
    }
}
