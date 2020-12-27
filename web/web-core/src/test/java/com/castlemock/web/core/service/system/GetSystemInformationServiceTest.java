package com.castlemock.web.core.service.system;

import com.castlemock.core.basis.Environment;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.service.system.input.GetSystemInformationInput;
import com.castlemock.core.basis.service.system.output.GetSystemInformationOutput;
import com.castlemock.repository.Profiles;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class GetSystemInformationServiceTest {


    @InjectMocks
    private GetSystemInformationService service;

    @Mock
    private Environment environment;

    @Mock
    private org.springframework.core.env.Environment springEnvironment;

    @Before
    public void setup() {
        final org.springframework.core.env.Profiles mongoProfiles =
                org.springframework.core.env.Profiles.of(Profiles.MONGODB);
        final org.springframework.core.env.Profiles fileProfiles =
                org.springframework.core.env.Profiles.of(Profiles.FILE);

        MockitoAnnotations.initMocks(this);

        Mockito.when(environment.getServerBuilt()).thenReturn("");
        Mockito.when(environment.getServerInfo()).thenReturn("");
        Mockito.when(environment.getServerNumber()).thenReturn("");
        Mockito.when(springEnvironment.acceptsProfiles(mongoProfiles)).thenReturn(false);
        Mockito.when(springEnvironment.acceptsProfiles(fileProfiles)).thenReturn(true);
    }

    @Test
    public void testProcess(){
        final GetSystemInformationInput input = new GetSystemInformationInput();
        final ServiceTask<GetSystemInformationInput> serviceTask = new ServiceTask<GetSystemInformationInput>(input);
        final ServiceResult<GetSystemInformationOutput> output = service.process(serviceTask);
        Assert.assertNotNull(output);
    }
}
