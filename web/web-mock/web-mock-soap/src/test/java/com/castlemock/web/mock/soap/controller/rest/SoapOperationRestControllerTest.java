package com.castlemock.web.mock.soap.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.service.mock.soap.project.input.ReadSoapOperationInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapOperationOutput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapOperationOutput;
import com.castlemock.web.mock.soap.model.UpdateSoapOperationRequest;
import com.castlemock.web.mock.soap.model.UpdateSoapOperationRequestTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

class SoapOperationRestControllerTest {


    @Test
    @DisplayName("Get operation")
    void testGetOperation() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapOperationRestController controller = new SoapOperationRestController(serviceProcessor);
        final SoapOperation operation = SoapOperationTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final String operationId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadSoapOperationOutput.builder()
                .operation(operation)
                .build());

        final ResponseEntity<SoapOperation> response = controller.getOperation(projectId, portId, operationId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(operation, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadSoapOperationInput
                .builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .operationId(operationId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Get operation - Not found")
    void testGetOperationNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapOperationRestController controller = new SoapOperationRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final String operationId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadSoapOperationOutput.builder()
                .operation(null)
                .build());

        final ResponseEntity<SoapOperation> response = controller.getOperation(projectId, portId, operationId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadSoapOperationInput
                .builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .operationId(operationId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update operation")
    void testUpdateOperation() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapOperationRestController controller = new SoapOperationRestController(serviceProcessor);
        final SoapOperation operation = SoapOperationTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final String operationId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateSoapOperationOutput.builder()
                .operation(operation)
                .build());

        final ResponseEntity<SoapOperation> response = controller.updateOperation(projectId, portId, operationId,
                UpdateSoapOperationRequestTestBuilder.builder().build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(operation, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update operation - Not found")
    void testUpdateOperationNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapOperationRestController controller = new SoapOperationRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final String operationId = UUID.randomUUID().toString();
        final UpdateSoapOperationRequest request = UpdateSoapOperationRequestTestBuilder.builder()
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateSoapOperationOutput.builder()
                .operation(null)
                .build());

        final ResponseEntity<SoapOperation> response = controller.updateOperation(projectId, portId,
                operationId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

}
