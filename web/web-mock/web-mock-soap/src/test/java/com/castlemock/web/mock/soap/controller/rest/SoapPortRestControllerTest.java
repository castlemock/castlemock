package com.castlemock.web.mock.soap.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapPortTestBuilder;
import com.castlemock.service.mock.soap.project.input.ReadSoapPortInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapPortOutput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapPortOutput;
import com.castlemock.web.mock.soap.model.UpdateSoapPortRequest;
import com.castlemock.web.mock.soap.model.UpdateSoapPortRequestTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

class SoapPortRestControllerTest {


    @Test
    @DisplayName("Get port")
    void testGetPort() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapPortRestController controller = new SoapPortRestController(serviceProcessor);
        final SoapPort port = SoapPortTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadSoapPortOutput.builder()
                .port(port)
                .build());

        final ResponseEntity<SoapPort> response = controller.getPort(projectId, portId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(port, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadSoapPortInput
                .builder()
                .projectId(projectId)
                .portId(portId)
                .portId(portId)
                .portId(portId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Get port - Not found")
    void testGetPortNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapPortRestController controller = new SoapPortRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadSoapPortOutput.builder()
                .port(null)
                .build());

        final ResponseEntity<SoapPort> response = controller.getPort(projectId, portId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadSoapPortInput
                .builder()
                .projectId(projectId)
                .portId(portId)
                .portId(portId)
                .portId(portId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update port")
    void testUpdatePort() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapPortRestController controller = new SoapPortRestController(serviceProcessor);
        final SoapPort port = SoapPortTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateSoapPortOutput.builder()
                .port(port)
                .build());

        final ResponseEntity<SoapPort> response = controller.updatePort(projectId, portId,
                UpdateSoapPortRequestTestBuilder.builder().build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(port, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update port - Not found")
    void testUpdatePortNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapPortRestController controller = new SoapPortRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final UpdateSoapPortRequest request = UpdateSoapPortRequestTestBuilder.builder()
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateSoapPortOutput.builder()
                .port(null)
                .build());

        final ResponseEntity<SoapPort> response = controller.updatePort(projectId, portId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

}
