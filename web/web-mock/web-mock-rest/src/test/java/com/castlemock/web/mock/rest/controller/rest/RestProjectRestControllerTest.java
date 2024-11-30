package com.castlemock.web.mock.rest.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.model.mock.rest.domain.RestProjectTestBuilder;
import com.castlemock.service.core.manager.FileManager;
import com.castlemock.service.mock.rest.project.input.*;
import com.castlemock.service.mock.rest.project.output.*;
import com.castlemock.web.core.model.project.CreateProjectRequest;
import com.castlemock.web.core.model.project.CreateProjectRequestTestBuilder;
import com.castlemock.web.core.model.project.UpdateProjectRequest;
import com.castlemock.web.core.model.project.UpdateProjectRequestTestBuilder;
import com.castlemock.web.mock.rest.model.UpdateRestApplicationForwardedEndpointsRequest;
import com.castlemock.web.mock.rest.model.UpdateRestApplicationStatusesRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.UUID;

class RestProjectRestControllerTest {

    @Test
    @DisplayName("Get REST project")
    void testGetRestProject() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final FileManager fileManager = Mockito.mock(FileManager.class);
        final RestProjectRestController controller = new RestProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();
        final RestProject project = RestProjectTestBuilder.builder()
                    .id(projectId)
                    .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadRestProjectOutput.builder()
                .project(project)
                .build());

        final ResponseEntity<RestProject> response = controller.getRestProject(projectId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(project, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadRestProjectInput
                .builder()
                .projectId(projectId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Get project - Not found")
    void testGetProjectNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final FileManager fileManager = Mockito.mock(FileManager.class);
        final RestProjectRestController controller = new RestProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadRestProjectOutput.builder()
                .project(null)
                .build());

        final ResponseEntity<RestProject> response = controller.getRestProject(projectId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadRestProjectInput
                .builder()
                .projectId(projectId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }


    @Test
    @DisplayName("Export REST project")
    void testExportRestProject() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final FileManager fileManager = Mockito.mock(FileManager.class);
        final RestProjectRestController controller = new RestProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();
        final String project = "<project></project>";

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ExportRestProjectOutput.builder()
                .exportedProject(project)
                .build());

        final ResponseEntity<String> response = controller.exportProject(projectId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(project, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ExportRestProjectInput
                .builder()
                .projectId(projectId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Export project - Not found")
    void testExportProjectNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final FileManager fileManager = Mockito.mock(FileManager.class);
        final RestProjectRestController controller = new RestProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ExportRestProjectOutput.builder()
                .exportedProject(null)
                .build());

        final ResponseEntity<String> response = controller.exportProject(projectId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ExportRestProjectInput
                .builder()
                .projectId(projectId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Delete project")
    void testDeleteProject() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final FileManager fileManager = Mockito.mock(FileManager.class);
        final RestProjectRestController controller = new RestProjectRestController(serviceProcessor, fileManager);
        final RestProject project = RestProjectTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DeleteRestProjectOutput.builder()
                .project(project)
                .build());

        final ResponseEntity<RestProject> response = controller.deleteProject(projectId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(project, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DeleteRestProjectInput
                .builder()
                .projectId(projectId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Delete project - Not found")
    void testDeleteProjectNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final FileManager fileManager = Mockito.mock(FileManager.class);
        final RestProjectRestController controller = new RestProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DeleteRestProjectOutput.builder()
                .project(null)
                .build());

        final ResponseEntity<RestProject> response = controller.deleteProject(projectId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DeleteRestProjectInput
                .builder()
                .projectId(projectId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update project")
    void testUpdateProject() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final FileManager fileManager = Mockito.mock(FileManager.class);
        final RestProjectRestController controller = new RestProjectRestController(serviceProcessor, fileManager);
        final RestProject project = RestProjectTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateRestProjectOutput.builder()
                .project(project)
                .build());

        final ResponseEntity<RestProject> response = controller.updateProject(projectId,
                UpdateProjectRequestTestBuilder.builder().build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(project, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update project - Not found")
    void testUpdateProjectNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final FileManager fileManager = Mockito.mock(FileManager.class);
        final RestProjectRestController controller = new RestProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();
        final UpdateProjectRequest request = UpdateProjectRequestTestBuilder.builder()
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateRestProjectOutput.builder()
                .project(null)
                .build());

        final ResponseEntity<RestProject> response = controller.updateProject(projectId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Create project")
    void testCreateProject() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final FileManager fileManager = Mockito.mock(FileManager.class);
        final RestProjectRestController controller = new RestProjectRestController(serviceProcessor, fileManager);
        final RestProject project = RestProjectTestBuilder.build();
        final CreateProjectRequest request = CreateProjectRequestTestBuilder.builder().build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(CreateRestProjectOutput.builder()
                .project(project)
                .build());

        final ResponseEntity<RestProject> response = controller.createRestProject(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(project, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update applications statuses")
    void testUpdateApplicationStatuses() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final FileManager fileManager = Mockito.mock(FileManager.class);
        final RestProjectRestController controller = new RestProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final RestMethodStatus methodStatus = RestMethodStatus.MOCKED;

        final ResponseEntity<Void> response = controller.updateApplicationStatuses(projectId,
                UpdateRestApplicationStatusesRequest.builder()
                        .applicationIds(Set.of(applicationId))
                        .status(methodStatus)
                        .build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(UpdateRestApplicationsStatusInput
                .builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .methodStatus(methodStatus)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update forward address")
    void testUpdateApplicationForwardAddress() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final FileManager fileManager = Mockito.mock(FileManager.class);
        final RestProjectRestController controller = new RestProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String forwardedEndpoint = "/forward";

        final ResponseEntity<Void> response = controller.updateApplicationForwardedEndpoints(projectId,
                UpdateRestApplicationForwardedEndpointsRequest.builder()
                        .applicationIds(Set.of(applicationId))
                        .forwardedEndpoint(forwardedEndpoint)
                        .build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(UpdateRestApplicationsForwardedEndpointInput
                .builder()
                .projectId(projectId)
                .applicationIds(Set.of(applicationId))
                .forwardedEndpoint(forwardedEndpoint)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }
}
