/*
 * Copyright 2016 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.web.basis.service;


import com.castlemock.core.basis.model.user.service.message.input.DeleteUserInput;
import com.castlemock.core.basis.model.user.service.message.input.ReadUserInput;
import com.castlemock.web.basis.model.user.service.DeleteUserService;
import com.castlemock.web.basis.model.user.service.ReadUserService;
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
    public void testGetService(){
        final ReadUserInput readUserInput = new ReadUserInput("Username");

        // Get registered service
        final ReadUserService readUserService = (ReadUserService) serviceRegistry.getService(readUserInput);
        Assert.assertNotNull(readUserService);
    }

    @Test
    public void testServiceInvalid(){
        final DeleteUserInput deleteUserInput = new DeleteUserInput("Username");

        // Try to get a non-registered service
        final DeleteUserService deleteUserService = (DeleteUserService) serviceRegistry.getService(deleteUserInput);
        Assert.assertNull(deleteUserService);
    }

    @Test
    public void testInitialize(){
        serviceRegistry.initialize();
    }

}
