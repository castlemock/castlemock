/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.web.core.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.event.OverviewEvent;
import com.castlemock.model.core.event.OverviewEventTestBuilder;
import com.castlemock.model.core.service.event.EventServiceFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class EventCoreRestControllerTest {

    private EventServiceFacade eventServiceFacade;
    private EventCoreRestController eventCoreRestController;

    @BeforeEach
    void setup(){
        final ServiceProcessor serviceProcessor = mock(ServiceProcessor.class);
        this.eventServiceFacade = mock(EventServiceFacade.class);
        this.eventCoreRestController = new EventCoreRestController(serviceProcessor, eventServiceFacade);
    }

    @Test
    @DisplayName("Get events")
    void testGetEvents(){
        final List<OverviewEvent> events = List.of(OverviewEventTestBuilder.builder().build());
        when(eventServiceFacade.findAll()).thenReturn(events);

        final ResponseEntity<List<OverviewEvent>> responseEntity = eventCoreRestController.getEvents();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(events, responseEntity.getBody());
        verify(eventServiceFacade, times(1)).findAll();
    }

    @Test
    @DisplayName("Delete all events")
    void testDeleteAllEvents(){
        final ResponseEntity<Void> responseEntity = eventCoreRestController.deleteAllEvents();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(eventServiceFacade, times(1)).clearAll();
    }

}
