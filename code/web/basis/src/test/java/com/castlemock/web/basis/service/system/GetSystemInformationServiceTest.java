package com.castlemock.web.basis.service.system;

import com.castlemock.core.basis.Environment;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.service.system.input.GetSystemInformationInput;
import com.castlemock.core.basis.service.system.output.GetSystemInformationOutput;
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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(environment.getServerBuilt()).thenReturn("");
        Mockito.when(environment.getServerInfo()).thenReturn("");
        Mockito.when(environment.getServerNumber()).thenReturn("");
    }

    @Test
    public void testProcess(){
        final GetSystemInformationInput input = new GetSystemInformationInput();
        final ServiceTask<GetSystemInformationInput> serviceTask = new ServiceTask<GetSystemInformationInput>(input);
        final ServiceResult<GetSystemInformationOutput> output = service.process(serviceTask);
        Assert.assertNotNull(output);
    }
}
