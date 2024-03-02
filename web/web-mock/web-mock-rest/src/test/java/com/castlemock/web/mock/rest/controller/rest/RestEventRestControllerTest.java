package com.castlemock.web.mock.rest.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.model.mock.rest.domain.RestEventTestBuilder;
import com.castlemock.service.mock.rest.event.output.ReadRestEventOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

class RestEventRestControllerTest {

    @Test
    @DisplayName("Get event")
    void testGetRestEvent() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestEventRestController controller = new RestEventRestController(serviceProcessor);
        final String eventId = UUID.randomUUID().toString();
        final RestEvent event = RestEventTestBuilder.builder()
                .id(eventId)
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadRestEventOutput.builder()
                .event(event)
                .build());

        final ResponseEntity<RestEvent> response = controller.getRestEvent(eventId);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(event, response.getBody());
    }

    @Test
    @DisplayName("Get event - Not found")
    void testGetRestEventNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestEventRestController controller = new RestEventRestController(serviceProcessor);
        final String eventId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadRestEventOutput.builder()
                .event(null)
                .build());

        final ResponseEntity<RestEvent> response = controller.getRestEvent(eventId);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
