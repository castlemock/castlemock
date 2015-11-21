package com.fortmocks.web.basis.service;

import com.fortmocks.core.basis.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ServiceProcessorTest {

    @Mock
    private ServiceRegistry serviceRegistry;

    @InjectMocks
    private ServiceProcessor serviceProcessor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        Service service = Mockito.mock(Service.class);
        Input input = Mockito.mock(Input.class);
        Output output = Mockito.mock(Output.class);
        ServiceResult serviceResult = new ServiceResult(output);
        Mockito.when(serviceRegistry.getProcessor(Mockito.any(Input.class))).thenReturn(service);
        Mockito.when(service.process(Mockito.any(ServiceTask.class))).thenReturn(serviceResult);
        Output serviceOutput = serviceProcessor.process(input);
        Assert.assertEquals(output, serviceOutput);
    }

}
