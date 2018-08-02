package com.castlemock.web.basis.service.system;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.service.system.input.GetSystemInformationInput;
import com.castlemock.core.basis.service.system.output.GetSystemInformationOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class GetSystemInformationServiceTest {


    @InjectMocks
    private GetSystemInformationService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final GetSystemInformationInput input = new GetSystemInformationInput();
        final ServiceTask<GetSystemInformationInput> serviceTask = new ServiceTask<GetSystemInformationInput>(input);
        final ServiceResult<GetSystemInformationOutput> output = service.process(serviceTask);
        Assert.assertNotNull(output);
    }
}
