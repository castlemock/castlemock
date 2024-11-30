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
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseTestBuilder;
import com.castlemock.service.mock.soap.project.input.DeleteSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.input.DuplicateSoapMockResponsesInput;
import com.castlemock.service.mock.soap.project.input.ReadSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.output.*;
import com.castlemock.web.mock.soap.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.UUID;

class SoapMockResponseRestControllerTest {


    @Test
    @DisplayName("Get mock response")
    void testGetMockResponse() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapMockResponseRestController controller = new SoapMockResponseRestController(serviceProcessor);
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final String operationId = UUID.randomUUID().toString();
        final String mockResponseId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadSoapMockResponseOutput.builder()
                .mockResponse(mockResponse)
                .build());

        final ResponseEntity<SoapMockResponse> response = controller.getMockResponse(projectId, portId,
                operationId, mockResponseId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(mockResponse, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadSoapMockResponseInput
                .builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(mockResponseId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Get mock response - Not found")
    void testGetMockResponseNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapMockResponseRestController controller = new SoapMockResponseRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final String operationId = UUID.randomUUID().toString();
        final String mockResponseId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadSoapMockResponseOutput.builder()
                .mockResponse(null)
                .build());

        final ResponseEntity<SoapMockResponse> response = controller.getMockResponse(projectId, portId, operationId, mockResponseId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadSoapMockResponseInput
                .builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(mockResponseId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Delete mock response")
    void testDeleteMockResponse() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapMockResponseRestController controller = new SoapMockResponseRestController(serviceProcessor);
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final String operationId = UUID.randomUUID().toString();
        final String mockResponseId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DeleteSoapMockResponseOutput.builder()
                .mockResponse(mockResponse)
                .build());

        final ResponseEntity<SoapMockResponse> response = controller.deleteMockResponse(projectId, portId,
                operationId, mockResponseId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(mockResponse, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DeleteSoapMockResponseInput
                .builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(mockResponseId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Delete mock response - Not found")
    void testDeleteMockResponseNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapMockResponseRestController controller = new SoapMockResponseRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final String operationId = UUID.randomUUID().toString();
        final String mockResponseId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DeleteSoapMockResponseOutput.builder()
                .mockResponse(null)
                .build());

        final ResponseEntity<SoapMockResponse> response = controller.deleteMockResponse(projectId, portId,
                operationId, mockResponseId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DeleteSoapMockResponseInput
                .builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(mockResponseId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update mock response")
    void testUpdateMockResponse() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapMockResponseRestController controller = new SoapMockResponseRestController(serviceProcessor);
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final String operationId = UUID.randomUUID().toString();
        final String mockResponseId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateSoapMockResponseOutput.builder()
                .mockResponse(mockResponse)
                .build());

        final ResponseEntity<SoapMockResponse> response = controller.updateMockResponse(projectId, portId, operationId, mockResponseId,
                UpdateSoapMockResponseRequestTestBuilder.builder().build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(mockResponse, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update mock response - Not found")
    void testUpdateMockResponseNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapMockResponseRestController controller = new SoapMockResponseRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final String operationId = UUID.randomUUID().toString();
        final String mockResponseId = UUID.randomUUID().toString();
        final UpdateSoapMockResponseRequest request = UpdateSoapMockResponseRequestTestBuilder.builder()
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateSoapMockResponseOutput.builder()
                .mockResponse(null)
                .build());

        final ResponseEntity<SoapMockResponse> response = controller.updateMockResponse(projectId, portId,
                operationId, mockResponseId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Create mock response")
    void testCreateMockResponse() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapMockResponseRestController controller = new SoapMockResponseRestController(serviceProcessor);
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final String operationId = UUID.randomUUID().toString();
        final CreateSoapMockResponseRequest request = CreateSoapMockResponseRequestTestBuilder.builder().build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(CreateSoapMockResponseOutput.builder()
                .mockResponse(mockResponse)
                .build());

        final ResponseEntity<SoapMockResponse> response = controller.createMockResponse(projectId, portId,
                operationId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(mockResponse, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Duplicate mock response")
    void testDuplicateMockResponse() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final SoapMockResponseRestController controller = new SoapMockResponseRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final String operationId = UUID.randomUUID().toString();
        final DuplicateSoapMockResponsesRequest request = DuplicateSoapMockResponsesRequest.builder()
                .mockResponseIds(Set.of(UUID.randomUUID().toString()))
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DuplicateSoapMockResponsesOutput.builder()
                .build());

        final ResponseEntity<Void> response = controller.duplicateMockResponse(projectId, portId,
                operationId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DuplicateSoapMockResponsesInput
                .builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseIds(request.getMockResponseIds())
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }
    
}
