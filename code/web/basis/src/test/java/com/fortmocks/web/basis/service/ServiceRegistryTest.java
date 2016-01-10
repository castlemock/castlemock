package com.fortmocks.web.basis.service;


import com.fortmocks.core.basis.model.user.service.message.input.DeleteUserInput;
import com.fortmocks.core.basis.model.user.service.message.input.ReadUserInput;
import com.fortmocks.web.basis.model.user.service.DeleteUserService;
import com.fortmocks.web.basis.model.user.service.ReadUserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.1
 */
public class ServiceRegistryTest {

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private ServiceRegistry serviceRegistry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Map<String, Object> components = new HashMap<String, Object>();
        final ReadUserService readUserService = Mockito.mock(ReadUserService.class);
        components.put("readUserService",readUserService);
        Mockito.when(applicationContext.getBeansWithAnnotation(Mockito.any(Class.class))).thenReturn(components);
        serviceRegistry.initialize();
    }

    @Test
    public void getService(){
        final ReadUserInput readUserInput = new ReadUserInput("Username");

        // Get registered service
        final ReadUserService readUserService = (ReadUserService) serviceRegistry.getService(readUserInput);
        Assert.assertNotNull(readUserService);
    }

    @Test
    public void getServiceInvalid(){
        final DeleteUserInput deleteUserInput = new DeleteUserInput("Username");

        // Try to get a non-registered service
        final DeleteUserService deleteUserService = (DeleteUserService) serviceRegistry.getService(deleteUserInput);
        Assert.assertNull(deleteUserService);
    }

}
