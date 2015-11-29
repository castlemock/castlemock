/*
 * Copyright 2015 Karl Dahlgren
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
public class ServiceProcessorImplTest {

    @Mock
    private ServiceRegistry serviceRegistry;

    @InjectMocks
    private ServiceProcessorImpl serviceProcessor;

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
