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
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapProjectTestBuilder;
import com.castlemock.service.core.manager.FileManager;
import com.castlemock.service.mock.soap.project.input.DeleteSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.ExportSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.ReadSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.UpdateSoapPortsForwardedEndpointInput;
import com.castlemock.service.mock.soap.project.input.UpdateSoapPortsStatusInput;
import com.castlemock.service.mock.soap.project.output.CreateSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.DeleteSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.ExportSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.ReadSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapProjectOutput;
import com.castlemock.web.core.model.project.CreateProjectRequest;
import com.castlemock.web.core.model.project.CreateProjectRequestTestBuilder;
import com.castlemock.web.core.model.project.UpdateProjectRequest;
import com.castlemock.web.core.model.project.UpdateProjectRequestTestBuilder;
import com.castlemock.web.mock.soap.model.UpdateSoapPortForwardedEndpointsRequest;
import com.castlemock.web.mock.soap.model.UpdateSoapPortStatusesRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.UUID;

class SoapProjectRestControllerTest {

    @Test
    @DisplayName("Get SOAP project")
    void testGetSoapProject() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final FileManager fileManager = Mockito.mock(FileManager.class);
        final SoapProjectRestController controller = new SoapProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();
        final SoapProject project = SoapProjectTestBuilder.builder()
                    .id(projectId)
                    .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadSoapProjectOutput.builder()
                .project(project)
                .build());

        final ResponseEntity<SoapProject> response = controller.getProject(projectId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(project, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadSoapProjectInput
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
        final SoapProjectRestController controller = new SoapProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ReadSoapProjectOutput.builder()
                .project(null)
                .build());

        final ResponseEntity<SoapProject> response = controller.getProject(projectId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ReadSoapProjectInput
                .builder()
                .projectId(projectId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }


    @Test
    @DisplayName("Export SOAP project")
    void testExportSoapProject() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final FileManager fileManager = Mockito.mock(FileManager.class);
        final SoapProjectRestController controller = new SoapProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();
        final String project = "<project></project>";

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ExportSoapProjectOutput.builder()
                .project(project)
                .build());

        final ResponseEntity<String> response = controller.exportProject(projectId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(project, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ExportSoapProjectInput
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
        final SoapProjectRestController controller = new SoapProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ExportSoapProjectOutput.builder()
                .project(null)
                .build());

        final ResponseEntity<String> response = controller.exportProject(projectId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ExportSoapProjectInput
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
        final SoapProjectRestController controller = new SoapProjectRestController(serviceProcessor, fileManager);
        final SoapProject project = SoapProjectTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DeleteSoapProjectOutput.builder()
                .project(project)
                .build());

        final ResponseEntity<SoapProject> response = controller.deleteProject(projectId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(project, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DeleteSoapProjectInput
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
        final SoapProjectRestController controller = new SoapProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(DeleteSoapProjectOutput.builder()
                .project(null)
                .build());

        final ResponseEntity<SoapProject> response = controller.deleteProject(projectId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(DeleteSoapProjectInput
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
        final SoapProjectRestController controller = new SoapProjectRestController(serviceProcessor, fileManager);
        final SoapProject project = SoapProjectTestBuilder.build();
        final String projectId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateSoapProjectOutput.builder()
                .project(project)
                .build());

        final ResponseEntity<SoapProject> response = controller.updateProject(projectId,
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
        final SoapProjectRestController controller = new SoapProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();
        final UpdateProjectRequest request = UpdateProjectRequestTestBuilder.builder()
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(UpdateSoapProjectOutput.builder()
                .project(null)
                .build());

        final ResponseEntity<SoapProject> response = controller.updateProject(projectId, request);

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
        final SoapProjectRestController controller = new SoapProjectRestController(serviceProcessor, fileManager);
        final SoapProject project = SoapProjectTestBuilder.build();
        final CreateProjectRequest request = CreateProjectRequestTestBuilder.builder().build();

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(CreateSoapProjectOutput.builder()
                .project(project)
                .build());

        final ResponseEntity<SoapProject> response = controller.createSoapProject(request);

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
        final SoapProjectRestController controller = new SoapProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final SoapOperationStatus methodStatus = SoapOperationStatus.MOCKED;

        final ResponseEntity<Void> response = controller.updatePortStatuses(projectId,
                UpdateSoapPortStatusesRequest.builder()
                        .portIds(Set.of(portId))
                        .status(methodStatus)
                        .build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(UpdateSoapPortsStatusInput
                .builder()
                .projectId(projectId)
                .portId(portId)
                .operationStatus(methodStatus)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Update forward address")
    void testUpdateApplicationForwardAddress() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final FileManager fileManager = Mockito.mock(FileManager.class);
        final SoapProjectRestController controller = new SoapProjectRestController(serviceProcessor, fileManager);
        final String projectId = UUID.randomUUID().toString();
        final String portId = UUID.randomUUID().toString();
        final String forwardedEndpoint = "/forward";

        final ResponseEntity<Void> response = controller.updatePortForwardedEndpoints(projectId,
                UpdateSoapPortForwardedEndpointsRequest.builder()
                        .portIds(Set.of(portId))
                        .forwardedEndpoint(forwardedEndpoint)
                        .build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(UpdateSoapPortsForwardedEndpointInput
                .builder()
                .projectId(projectId)
                .portIds(Set.of(portId))
                .forwardedEndpoint(forwardedEndpoint)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }
}
