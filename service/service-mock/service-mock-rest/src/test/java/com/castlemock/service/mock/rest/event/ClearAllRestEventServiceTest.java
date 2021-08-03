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

package com.castlemock.service.mock.rest.event;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.repository.rest.event.RestEventRepository;
import com.castlemock.service.mock.rest.event.input.ClearAllRestEventInput;
import com.castlemock.service.mock.rest.event.output.ClearAllRestEventOutput;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ClearAllRestEventServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private RestEventRepository repository;

    @InjectMocks
    private ClearAllRestEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final ClearAllRestEventInput input = ClearAllRestEventInput.builder().build();
        final ServiceTask<ClearAllRestEventInput> serviceTask = new ServiceTask<ClearAllRestEventInput>(input);
        final ServiceResult<ClearAllRestEventOutput> serviceResult = service.process(serviceTask);
        final ClearAllRestEventOutput output = serviceResult.getOutput();

        Mockito.verify(repository, Mockito.times(1)).clearAll();
    }


}
