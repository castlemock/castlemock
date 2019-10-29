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

package com.castlemock.web.mock.soap.service.event;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.service.event.input.ClearAllSoapEventInput;
import com.castlemock.core.mock.soap.service.event.output.ClearAllSoapEventOutput;
import com.castlemock.repository.soap.event.SoapEventRepository;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ClearAllSoapEventServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapEventRepository repository;

    @InjectMocks
    private ClearAllSoapEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final ClearAllSoapEventInput input = ClearAllSoapEventInput.builder().build();
        final ServiceTask<ClearAllSoapEventInput> serviceTask = new ServiceTask<ClearAllSoapEventInput>(input);
        final ServiceResult<ClearAllSoapEventOutput> serviceResult = service.process(serviceTask);
        final ClearAllSoapEventOutput output = serviceResult.getOutput();

        Mockito.verify(repository, Mockito.times(1)).clearAll();
    }


}
