package com.castlemock.web.mock.rest.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodTestBuilder;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;
import com.castlemock.service.mock.rest.project.input.DeleteRestMethodInput;
import com.castlemock.service.mock.rest.project.input.ReadRestMethodInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestMockResponseStatusInput;
import com.castlemock.service.mock.rest.project.output.CreateRestMethodOutput;
import com.castlemock.service.mock.rest.project.output.DeleteRestMethodOutput;
import com.castlemock.service.mock.rest.project.output.ReadRestMethodOutput;
import com.castlemock.service.mock.rest.project.output.UpdateRestMethodOutput;
import com.castlemock.web.mock.rest.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.UUID;

class RestMethodRestControllerTest {

    @Test
    @DisplayName("Get method")
    void testGetMethod() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestMethodRestController controller = new RestMethodRestController(serviceProcessor);
        final RestMethod method = RestMethodTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadRestMethodOutput.builder()
                .method(method)
                .build());

        final ResponseEntity<RestMethod> response = controller.getMethod(projectId, applicationId, resourceId, methodId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(method, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadRestMethodInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Get method - Not found")
    void testGetMethodNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestMethodRestController controller = new RestMethodRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadRestMethodOutput.builder()
                .method(null)
                .build());

        final ResponseEntity<RestMethod> response = controller.getMethod(projectId, applicationId, resourceId, methodId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadRestMethodInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Delete method")
    void testDeleteMethod() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestMethodRestController controller = new RestMethodRestController(serviceProcessor);
        final RestMethod method = RestMethodTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DeleteRestMethodOutput.builder()
                .method(method)
                .build());

        final ResponseEntity<RestMethod> response = controller.deleteMethod(projectId, applicationId, resourceId, methodId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(method, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DeleteRestMethodInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Delete method - Not found")
    void testDeleteMethodNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestMethodRestController controller = new RestMethodRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DeleteRestMethodOutput.builder()
                .method(null)
                .build());

        final ResponseEntity<RestMethod> response = controller.deleteMethod(projectId, applicationId, resourceId, methodId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DeleteRestMethodInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update method")
    void testUpdateMethod() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestMethodRestController controller = new RestMethodRestController(serviceProcessor);
        final RestMethod method = RestMethodTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateRestMethodOutput.builder()
                .method(method)
                .build());

        final ResponseEntity<RestMethod> response = controller.updateMethod(projectId, applicationId, resourceId, methodId,
                UpdateRestMethodRequestTestBuilder.builder().build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(method, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update method - Not found")
    void testUpdateMethodNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestMethodRestController controller = new RestMethodRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();
        final UpdateRestMethodRequest request = UpdateRestMethodRequestTestBuilder.builder()
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateRestMethodOutput.builder()
                .method(null)
                .build());

        final ResponseEntity<RestMethod> response = controller.updateMethod(projectId, applicationId,
                resourceId, methodId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Create method")
    void testCreateMethod() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestMethodRestController controller = new RestMethodRestController(serviceProcessor);
        final RestMethod method = RestMethodTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final CreateRestMethodRequest request = CreateRestMethodRequestTestBuilder.builder().build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(CreateRestMethodOutput.builder()
                .method(method)
                .build());

        final ResponseEntity<RestMethod> response = controller.createMethod(projectId, applicationId, resourceId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(method, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update mock responses statuses")
    void testUpdateMockResponsesStatuses() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestMethodRestController controller = new RestMethodRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();
        final String mockResponseId = UUID.randomUUID().toString();

        final RestMockResponseStatus status = RestMockResponseStatus.ENABLED;

        final ResponseEntity<Void> response = controller.updateMockResponseStatuses(projectId, applicationId,
                resourceId, methodId, UpdateRestMockResponseStatusesRequest.builder()
                        .mockResponseIds(Set.of(mockResponseId))
                        .status(status)
                        .build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(UpdateRestMockResponseStatusInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .mockResponseId(mockResponseId)
                .status(status)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

}
