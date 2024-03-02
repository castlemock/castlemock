package com.castlemock.web.mock.rest.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.model.mock.rest.domain.RestResourceTestBuilder;
import com.castlemock.service.mock.rest.project.input.DeleteRestResourceInput;
import com.castlemock.service.mock.rest.project.input.ReadRestResourceInput;
import com.castlemock.service.mock.rest.project.input.ReadRestResourceQueryParametersInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestMethodsForwardedEndpointInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestMethodsStatusInput;
import com.castlemock.service.mock.rest.project.output.CreateRestResourceOutput;
import com.castlemock.service.mock.rest.project.output.DeleteRestResourceOutput;
import com.castlemock.service.mock.rest.project.output.ReadRestResourceOutput;
import com.castlemock.service.mock.rest.project.output.ReadRestResourceQueryParametersOutput;
import com.castlemock.service.mock.rest.project.output.UpdateRestResourceOutput;
import com.castlemock.web.mock.rest.model.CreateRestResourceRequest;
import com.castlemock.web.mock.rest.model.CreateRestResourceRequestTestBuilder;
import com.castlemock.web.mock.rest.model.UpdateRestMethodForwardedEndpointsRequest;
import com.castlemock.web.mock.rest.model.UpdateRestMethodStatusesRequest;
import com.castlemock.web.mock.rest.model.UpdateRestResourceRequest;
import com.castlemock.web.mock.rest.model.UpdateRestResourceRequestTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.UUID;

class RestResourceRestControllerTest {


    @Test
    @DisplayName("Get resource")
    void testGetResource() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestResourceRestController controller = new RestResourceRestController(serviceProcessor);
        final RestResource resource = RestResourceTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadRestResourceOutput.builder()
                .resource(resource)
                .build());

        final ResponseEntity<RestResource> response = controller.getResource(projectId, applicationId, resourceId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(resource, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadRestResourceInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Get resource - Not found")
    void testGetResourceNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestResourceRestController controller = new RestResourceRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadRestResourceOutput.builder()
                .resource(null)
                .build());

        final ResponseEntity<RestResource> response = controller.getResource(projectId, applicationId, resourceId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadRestResourceInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Get resource parameters")
    void testGetResourceParameters() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestResourceRestController controller = new RestResourceRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final Set<String> queries = Set.of("param1", "param2");

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadRestResourceQueryParametersOutput.builder()
                .queries(queries)
                .build());

        final ResponseEntity<Set<String>> response = controller.getResourceParameters(projectId, applicationId, resourceId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(queries, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadRestResourceQueryParametersInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Delete resource")
    void testDeleteResource() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestResourceRestController controller = new RestResourceRestController(serviceProcessor);
        final RestResource resource = RestResourceTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DeleteRestResourceOutput.builder()
                .resource(resource)
                .build());

        final ResponseEntity<RestResource> response = controller.deleteResource(projectId, applicationId, resourceId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(resource, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DeleteRestResourceInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Delete resource - Not found")
    void testDeleteResourceNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestResourceRestController controller = new RestResourceRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DeleteRestResourceOutput.builder()
                .resource(null)
                .build());

        final ResponseEntity<RestResource> response = controller.deleteResource(projectId, applicationId, resourceId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DeleteRestResourceInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update resource")
    void testUpdateResource() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestResourceRestController controller = new RestResourceRestController(serviceProcessor);
        final RestResource resource = RestResourceTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateRestResourceOutput.builder()
                .resource(resource)
                .build());

        final ResponseEntity<RestResource> response = controller.updateResource(projectId, applicationId, resourceId,
                UpdateRestResourceRequestTestBuilder.builder().build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(resource, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update resource - Not found")
    void testUpdateResourceNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestResourceRestController controller = new RestResourceRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final UpdateRestResourceRequest request = UpdateRestResourceRequestTestBuilder.builder()
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateRestResourceOutput.builder()
                .resource(null)
                .build());

        final ResponseEntity<RestResource> response = controller.updateResource(projectId, applicationId,
                resourceId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Create resource")
    void testCreateResource() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestResourceRestController controller = new RestResourceRestController(serviceProcessor);
        final RestResource resource = RestResourceTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final CreateRestResourceRequest request = CreateRestResourceRequestTestBuilder.builder().build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(CreateRestResourceOutput.builder()
                .resource(resource)
                .build());

        final ResponseEntity<RestResource> response = controller.createResource(projectId, applicationId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(resource, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update methods statuses")
    void testUpdateMethodsStatuses() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestResourceRestController controller = new RestResourceRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();

        final RestMethodStatus status = RestMethodStatus.MOCKED;

        final ResponseEntity<Void> response = controller.updateMethodStatuses(projectId, applicationId,
                resourceId, UpdateRestMethodStatusesRequest.builder()
                        .methodIds(Set.of(methodId))
                        .status(status)
                        .build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(UpdateRestMethodsStatusInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .methodStatus(status)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update method forward address")
    void testUpdateMethodForwardAddress() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final RestResourceRestController controller = new RestResourceRestController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();
        final String forwardedEndpoint = "/forward";

        final ResponseEntity<Void> response = controller.updateMethodForwardedEndpoints(projectId, applicationId, resourceId,
                UpdateRestMethodForwardedEndpointsRequest.builder()
                        .methodIds(Set.of(methodId))
                        .forwardedEndpoint(forwardedEndpoint)
                        .build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(UpdateRestMethodsForwardedEndpointInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodIds(Set.of(methodId))
                .forwardedEndpoint(forwardedEndpoint)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

}
