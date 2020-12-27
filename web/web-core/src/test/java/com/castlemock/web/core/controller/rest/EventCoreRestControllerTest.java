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

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.event.domain.Event;
import com.castlemock.core.basis.model.event.domain.EventTestBuilder;
import com.castlemock.core.basis.service.event.EventServiceFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        final List<Event> events = List.of(EventTestBuilder.builder().build());
        when(eventServiceFacade.findAll()).thenReturn(events);

        final ResponseEntity<List<Event>> responseEntity = eventCoreRestController.getEvents();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(events, responseEntity.getBody());
        verify(eventServiceFacade, times(1)).findAll();
    }

    @Test
    @DisplayName("Get event")
    void testEvent(){
        final Event event = EventTestBuilder.builder().build();
        when(eventServiceFacade.findOne(any(), any())).thenReturn(event);

        final ResponseEntity<Event> responseEntity = eventCoreRestController.getEvent("soap", "eventId");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(event, responseEntity.getBody());
        verify(eventServiceFacade, times(1)).findOne("soap", "eventId");
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
