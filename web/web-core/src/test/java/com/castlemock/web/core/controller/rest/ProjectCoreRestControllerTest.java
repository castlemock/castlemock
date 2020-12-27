/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.web.core.controller.rest;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.project.domain.Project;
import com.castlemock.core.basis.model.project.domain.ProjectTestBuilder;
import com.castlemock.core.basis.service.project.ProjectServiceFacade;
import com.castlemock.web.core.manager.FileManager;
import com.castlemock.web.core.model.project.CreateProjectRequest;
import com.castlemock.web.core.model.project.CreateProjectRequestTestBuilder;
import com.castlemock.web.core.model.project.UpdateProjectRequest;
import com.castlemock.web.core.model.project.UpdateProjectRequestTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProjectCoreRestControllerTest {

    private FileManager fileManager;
    private ProjectServiceFacade projectServiceFacade;
    private ProjectCoreRestController projectCoreRestController;

    @BeforeEach
    void setup(){
        final ServiceProcessor serviceProcessor = mock(ServiceProcessor.class);
        this.fileManager = mock(FileManager.class);
        this.projectServiceFacade = mock(ProjectServiceFacade.class);
        this.projectCoreRestController = new ProjectCoreRestController(serviceProcessor, fileManager, projectServiceFacade);
    }

    @Test
    @DisplayName("Get projects")
    void testGetProjects(){
        final Project project = ProjectTestBuilder.builder().build();
        final List<Project> projects = List.of(project);
        when(this.projectServiceFacade.findAll()).thenReturn(projects);

        final ResponseEntity<List<Project>> responseEntity = projectCoreRestController.getProjects();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(projects, responseEntity.getBody());

        verify(this.projectServiceFacade, times(1)).findAll();
    }

    @Test
    @DisplayName("Get project")
    void testGetProject(){
        final Project project = ProjectTestBuilder.builder().build();
        when(this.projectServiceFacade.findOne("soap", "projectId")).thenReturn(project);

        final ResponseEntity<Project> responseEntity = projectCoreRestController.getProject("soap", "projectId");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(project, responseEntity.getBody());

        verify(this.projectServiceFacade, times(1)).findOne(eq("soap"), eq("projectId"));
    }

    @Test
    @DisplayName("Create project")
    void testCreateProject(){
        final Project project = ProjectTestBuilder.builder().build();
        when(this.projectServiceFacade.save(any(), any())).thenReturn(project);

        final CreateProjectRequest request = CreateProjectRequestTestBuilder.builder().build();
        final ResponseEntity<Project> responseEntity = projectCoreRestController.createProject(request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(project, responseEntity.getBody());

        final ArgumentCaptor<Project> argumentCaptor = ArgumentCaptor.forClass(Project.class);
        verify(this.projectServiceFacade, times(1)).save(eq(request.getProjectType()), argumentCaptor.capture());

        final Project savedProject = argumentCaptor.getValue();
        assertEquals(request.getName(), savedProject.getName());
        assertEquals(request.getDescription(), savedProject.getDescription());
    }

    @Test
    @DisplayName("Update project")
    void testUpdateProject(){
        final Project project = ProjectTestBuilder.builder().build();
        when(this.projectServiceFacade.findOne(any(), any())).thenReturn(project);
        when(this.projectServiceFacade.update(any(), any(), any())).thenReturn(project);

        final UpdateProjectRequest request = UpdateProjectRequestTestBuilder.builder().build();
        final ResponseEntity<Project> responseEntity = projectCoreRestController.updateProject("soap", project.getId(), request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(project, responseEntity.getBody());

        final ArgumentCaptor<Project> argumentCaptor = ArgumentCaptor.forClass(Project.class);
        verify(this.projectServiceFacade, times(1)).update(eq("soap"), eq(project.getId()), argumentCaptor.capture());

        final Project savedProject = argumentCaptor.getValue();
        assertEquals(request.getName(), savedProject.getName());
        assertEquals(request.getDescription(), savedProject.getDescription());
    }

    @Test
    @DisplayName("Delete project")
    void testDeleteProject(){
        final Project project = ProjectTestBuilder.builder().build();
        when(this.projectServiceFacade.delete("soap", "projectId")).thenReturn(project);

        final ResponseEntity<Project> responseEntity = projectCoreRestController.deleteProject("soap", "projectId");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(project, responseEntity.getBody());

        verify(this.projectServiceFacade, times(1)).delete(eq("soap"), eq("projectId"));
    }

    @Test
    @DisplayName("Export project")
    void testExportProject(){
        final String content = "exported project";
        when(this.projectServiceFacade.exportProject("soap", "projectId")).thenReturn(content);

        final ResponseEntity<String> responseEntity = projectCoreRestController.exportProject("soap", "projectId");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(content, responseEntity.getBody());

        verify(this.projectServiceFacade, times(1)).exportProject(eq("soap"), eq("projectId"));
    }
}
