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

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.project.OverviewProject;
import com.castlemock.model.core.project.OverviewProjectTestBuilder;
import com.castlemock.model.core.service.project.ProjectServiceFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProjectCoreRestControllerTest {

    private ProjectServiceFacade projectServiceFacade;
    private ProjectCoreRestController projectCoreRestController;

    @BeforeEach
    void setup(){
        final ServiceProcessor serviceProcessor = mock(ServiceProcessor.class);
        this.projectServiceFacade = mock(ProjectServiceFacade.class);
        this.projectCoreRestController = new ProjectCoreRestController(serviceProcessor, projectServiceFacade);
    }

    @Test
    @DisplayName("Get projects")
    void testGetProjects(){
        final OverviewProject project = OverviewProjectTestBuilder.builder().build();
        final List<OverviewProject> projects = List.of(project);
        when(this.projectServiceFacade.findAll()).thenReturn(projects);

        final ResponseEntity<List<OverviewProject>> responseEntity = projectCoreRestController.getProjects();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(projects, responseEntity.getBody());

        verify(this.projectServiceFacade, times(1)).findAll();
    }

}
