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

package com.castlemock.service.mock.rest.event.adapter;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.model.mock.rest.domain.RestEventTestBuilder;
import com.castlemock.service.mock.rest.event.input.ClearAllRestEventInput;
import com.castlemock.service.mock.rest.event.input.ReadAllRestEventInput;
import com.castlemock.service.mock.rest.event.output.ReadAllRestEventOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dalhgren
 * @since 1.4
 */
public class RestEventServiceAdapterTest {

    @Mock
    private ServiceProcessor serviceProcessor;

    @InjectMocks
    private RestEventServiceAdapter serviceAdapter;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReadAll(){
        final List<RestEvent> restEvents = new ArrayList<RestEvent>();
        for(int index = 0; index < 3; index++){
            final RestEvent restEvent = RestEventTestBuilder.builder().build();
            restEvents.add(restEvent);

        }

        final ReadAllRestEventOutput output = ReadAllRestEventOutput.builder().restEvents(restEvents).build();
        Mockito.when(serviceProcessor.process(Mockito.any(ReadAllRestEventInput.class))).thenReturn(output);

        final List<RestEvent> returnedRestEvents = serviceAdapter.readAll();

        for(int index = 0; index < 3; index++){
            final RestEvent restEvent = restEvents.get(index);
            final RestEvent returnedRestEvent = returnedRestEvents.get(index);

            Assert.assertEquals(restEvent.getId(), returnedRestEvent.getId());
            Assert.assertEquals(restEvent.getResourceId(), returnedRestEvent.getResourceId());
            Assert.assertEquals(restEvent.getMethodId(), returnedRestEvent.getMethodId());
            Assert.assertEquals(restEvent.getProjectId(), returnedRestEvent.getProjectId());
            Assert.assertEquals(restEvent.getApplicationId(), returnedRestEvent.getApplicationId());
        }
    }

    @Test
    public void testClearAll(){
        serviceAdapter.clearAll();
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ClearAllRestEventInput.class));
    }
}
