package com.castlemock.web.mock.rest.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestMockResponseTestBuilder;
import com.castlemock.service.mock.rest.project.input.DeleteRestMockResponseInput;
import com.castlemock.service.mock.rest.project.input.DuplicateRestMockResponsesInput;
import com.castlemock.service.mock.rest.project.input.ReadRestMockResponseInput;
import com.castlemock.service.mock.rest.project.output.*;
import com.castlemock.web.mock.rest.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.UUID;

class RestMockResponseRestControllerTest {


    @Test
    @DisplayName("Get mock response")
    void testGetMockResponse() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestMockResponseRestController controller = new RestMockResponseRestController(serviceProcessor);
        final RestMockResponse mockResponse = RestMockResponseTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();
        final String mockResponseId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadRestMockResponseOutput.builder()
                .mockResponse(mockResponse)
                .build());

        final ResponseEntity<RestMockResponse> response = controller.getResponse(projectId, applicationId,
                resourceId, methodId, mockResponseId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(mockResponse, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadRestMockResponseInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .mockResponseId(mockResponseId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Get mock response - Not found")
    void testGetMockResponseNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestMockResponseRestController controller = new RestMockResponseRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();
        final String mockResponseId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadRestMockResponseOutput.builder()
                .mockResponse(null)
                .build());

        final ResponseEntity<RestMockResponse> response = controller.getResponse(projectId, applicationId, resourceId, methodId, mockResponseId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadRestMockResponseInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .mockResponseId(mockResponseId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Delete mock response")
    void testDeleteMockResponse() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestMockResponseRestController controller = new RestMockResponseRestController(serviceProcessor);
        final RestMockResponse mockResponse = RestMockResponseTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();
        final String mockResponseId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DeleteRestMockResponseOutput.builder()
                .mockResponse(mockResponse)
                .build());

        final ResponseEntity<RestMockResponse> response = controller.deleteResponse(projectId, applicationId,
                resourceId, methodId, mockResponseId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(mockResponse, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DeleteRestMockResponseInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .mockResponseId(mockResponseId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Delete mock response - Not found")
    void testDeleteMockResponseNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestMockResponseRestController controller = new RestMockResponseRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();
        final String mockResponseId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DeleteRestMockResponseOutput.builder()
                .mockResponse(null)
                .build());

        final ResponseEntity<RestMockResponse> response = controller.deleteResponse(projectId, applicationId,
                resourceId, methodId, mockResponseId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DeleteRestMockResponseInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .mockResponseId(mockResponseId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update mock response")
    void testUpdateMockResponse() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestMockResponseRestController controller = new RestMockResponseRestController(serviceProcessor);
        final RestMockResponse mockResponse = RestMockResponseTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();
        final String mockResponseId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateRestMockResponseOutput.builder()
                .mockResponse(mockResponse)
                .build());

        final ResponseEntity<RestMockResponse> response = controller.updateResponse(projectId, applicationId, resourceId, methodId, mockResponseId,
                UpdateRestMockResponseRequestTestBuilder.builder().build());

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
        final RestMockResponseRestController controller = new RestMockResponseRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();
        final String mockResponseId = UUID.randomUUID().toString();
        final UpdateRestMockResponseRequest request = UpdateRestMockResponseRequestTestBuilder.builder()
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateRestMockResponseOutput.builder()
                .mockResponse(null)
                .build());

        final ResponseEntity<RestMockResponse> response = controller.updateResponse(projectId, applicationId,
                resourceId, mockResponseId, methodId, request);

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
        final RestMockResponseRestController controller = new RestMockResponseRestController(serviceProcessor);
        final RestMockResponse mockResponse = RestMockResponseTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();
        final CreateRestMockResponseRequest request = CreateRestMockResponseRequestTestBuilder.builder().build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(CreateRestMockResponseOutput.builder()
                .mockResponse(mockResponse)
                .build());

        final ResponseEntity<RestMockResponse> response = controller.createResponse(projectId, applicationId,
                resourceId, methodId, request);

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
        final RestMockResponseRestController controller = new RestMockResponseRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();
        final DuplicateRestMockResponsesRequest request = DuplicateRestMockResponsesRequest.builder()
                .mockResponseIds(Set.of(UUID.randomUUID().toString()))
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DuplicateRestMockResponsesOutput.builder()
                .build());

        final ResponseEntity<Void> response = controller.duplicateResponse(projectId, applicationId,
                resourceId, methodId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DuplicateRestMockResponsesInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .mockResponseIds(request.getMockResponseIds())
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

}
