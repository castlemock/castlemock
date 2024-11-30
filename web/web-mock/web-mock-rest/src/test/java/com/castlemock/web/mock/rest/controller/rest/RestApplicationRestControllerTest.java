package com.castlemock.web.mock.rest.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestApplicationTestBuilder;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.service.mock.rest.project.input.*;
import com.castlemock.service.mock.rest.project.output.CreateRestApplicationOutput;
import com.castlemock.service.mock.rest.project.output.DeleteRestApplicationOutput;
import com.castlemock.service.mock.rest.project.output.ReadRestApplicationOutput;
import com.castlemock.service.mock.rest.project.output.UpdateRestApplicationOutput;
import com.castlemock.web.mock.rest.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.UUID;

class RestApplicationRestControllerTest {

    @Test
    @DisplayName("Get application")
    void testGetApplication() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestApplicationRestController controller = new RestApplicationRestController(serviceProcessor);
        final RestApplication restApplication = RestApplicationTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadRestApplicationOutput.builder()
                .application(restApplication)
                .build());

        final ResponseEntity<RestApplication> response = controller.getApplication(projectId, applicationId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(restApplication, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadRestApplicationInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Get application - Not found")
    void testGetApplicationNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestApplicationRestController controller = new RestApplicationRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadRestApplicationOutput.builder()
                .application(null)
                .build());

        final ResponseEntity<RestApplication> response = controller.getApplication(projectId, applicationId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadRestApplicationInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Delete application")
    void testDeleteApplication() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestApplicationRestController controller = new RestApplicationRestController(serviceProcessor);
        final RestApplication restApplication = RestApplicationTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DeleteRestApplicationOutput.builder()
                .application(restApplication)
                .build());

        final ResponseEntity<RestApplication> response = controller.deleteApplication(projectId, applicationId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(restApplication, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DeleteRestApplicationInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Delete application - Not found")
    void testDeleteApplicationNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestApplicationRestController controller = new RestApplicationRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DeleteRestApplicationOutput.builder()
                .application(null)
                .build());

        final ResponseEntity<RestApplication> response = controller.deleteApplication(projectId, applicationId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DeleteRestApplicationInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update application")
    void testUpdateApplication() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestApplicationRestController controller = new RestApplicationRestController(serviceProcessor);
        final RestApplication restApplication = RestApplicationTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String applicationName = "New name";

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateRestApplicationOutput.builder()
                .application(restApplication)
                .build());

        final ResponseEntity<RestApplication> response = controller.updateApplication(projectId, applicationId,
                UpdateRestApplicationRequest.builder()
                        .name(applicationName)
                        .build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(restApplication, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(UpdateRestApplicationInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .name(applicationName)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update application - Not found")
    void testUpdateApplicationNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestApplicationRestController controller = new RestApplicationRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String applicationName = "New name";

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateRestApplicationOutput.builder()
                .application(null)
                .build());

        final ResponseEntity<RestApplication> response = controller.updateApplication(projectId, applicationId,
                UpdateRestApplicationRequest.builder()
                        .name(applicationName)
                        .build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(UpdateRestApplicationInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .name(applicationName)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Create application")
    void testCreateApplication() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestApplicationRestController controller = new RestApplicationRestController(serviceProcessor);
        final RestApplication restApplication = RestApplicationTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final CreateRestApplicationRequest request = CreateRestApplicationRequestTestBuilder
                .builder()
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(CreateRestApplicationOutput.builder()
                .application(restApplication)
                .build());

        final ResponseEntity<RestApplication> response = controller.createApplication(projectId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(restApplication, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(CreateRestApplicationInput
                .builder()
                .projectId(projectId)
                .name(request.getName())
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update resources statuses")
    void testUpdateResourceStatuses() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestApplicationRestController controller = new RestApplicationRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final RestMethodStatus methodStatus = RestMethodStatus.MOCKED;

        final ResponseEntity<Void> response = controller.updateResourceStatuses(projectId, applicationId,
                UpdateRestResourceStatusesRequest.builder()
                        .resourceIds(Set.of(resourceId))
                        .status(methodStatus)
                        .build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(UpdateRestResourcesStatusInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodStatus(methodStatus)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update forward address")
    void testUpdateResourceForwardAddress() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestApplicationRestController controller = new RestApplicationRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String forwardedEndpoint = "/forward";

        final ResponseEntity<Void> response = controller.updateResourceForwardedEndpoints(projectId, applicationId,
                UpdateRestResourceForwardedEndpointsRequest.builder()
                        .resourceIds(Set.of(resourceId))
                        .forwardedEndpoint(forwardedEndpoint)
                        .build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(UpdateRestResourcesForwardedEndpointInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceIds(Set.of(resourceId))
                .forwardedEndpoint(forwardedEndpoint)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

}
