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

package com.castlemock.service.core.event;

import com.castlemock.model.core.service.event.EventServiceAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
@SuppressWarnings({"unchecked", "rawtypes"})
public class EventServiceFacadeImplTest {

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private EventServiceFacadeImpl serviceFacade;

    private EventServiceAdapter eventServiceAdapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        eventServiceAdapter = Mockito.mock(EventServiceAdapter.class);

        final Map<String, Object> eventServiceAdapters = new HashMap<>();
        eventServiceAdapters.put("EventServiceAdapter", eventServiceAdapter);
        Mockito.when(applicationContext.getBeansWithAnnotation(Mockito.any(Class.class))).thenReturn(eventServiceAdapters);

        serviceFacade.initiate();
    }

    @Test
    public void testClearAll(){
        serviceFacade.clearAll();
        Mockito.verify(eventServiceAdapter, Mockito.times(1)).clearAll();

    }

}
