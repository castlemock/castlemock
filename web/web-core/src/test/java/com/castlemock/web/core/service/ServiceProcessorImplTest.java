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

package com.castlemock.web.core.service;

import com.castlemock.model.core.Input;
import com.castlemock.model.core.Output;
import com.castlemock.model.core.Service;
import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.service.core.ServiceProcessorImpl;
import com.castlemock.service.core.ServiceRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ServiceProcessorImplTest {

    @Mock
    private ServiceRegistry serviceRegistry;

    @InjectMocks
    private ServiceProcessorImpl serviceProcessor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void testProcess(){
        Service service = Mockito.mock(Service.class);
        Input input = Mockito.mock(Input.class);
        Output output = Mockito.mock(Output.class);
        ServiceResult<Output> serviceResult = ServiceResult.builder()
                .output(output)
                .build();
        Mockito.when(serviceRegistry.getService(Mockito.any(Input.class))).thenReturn(service);
        Mockito.when(service.process(Mockito.any(ServiceTask.class))).thenReturn(serviceResult);
        Output serviceOutput = serviceProcessor.process(input);
        Assertions.assertEquals(output, serviceOutput);
    }

}
