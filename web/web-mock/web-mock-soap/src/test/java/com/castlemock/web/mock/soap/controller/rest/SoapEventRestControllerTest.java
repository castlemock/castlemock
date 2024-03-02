/*
 * Copyright 2024 Karl Dahlgren
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

package com.castlemock.web.mock.soap.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.mock.soap.domain.SoapEvent;
import com.castlemock.model.mock.soap.domain.SoapEventTestBuilder;
import com.castlemock.service.mock.soap.event.output.ReadSoapEventOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

class SoapEventRestControllerTest {

    @Test
    @DisplayName("Get event")
    void testGetSoapEvent() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapEventRestController controller = new SoapEventRestController(serviceProcessor);
        final String eventId = UUID.randomUUID().toString();
        final SoapEvent event = SoapEventTestBuilder.builder()
                .id(eventId)
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadSoapEventOutput.builder()
                .event(event)
                .build());

        final ResponseEntity<SoapEvent> response = controller.getSoapEvent(eventId);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(event, response.getBody());
    }

    @Test
    @DisplayName("Get event - Not found")
    void testGetSoapEventNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapEventRestController controller = new SoapEventRestController(serviceProcessor);
        final String eventId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadSoapEventOutput.builder()
                .event(null)
                .build());

        final ResponseEntity<SoapEvent> response = controller.getSoapEvent(eventId);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
