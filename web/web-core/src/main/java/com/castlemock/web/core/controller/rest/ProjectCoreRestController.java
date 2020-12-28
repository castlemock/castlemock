/*
 * Copyright 2018 Karl Dahlgren
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

import com.castlemock.model.core.model.ServiceProcessor;
import com.castlemock.model.core.model.project.domain.Project;
import com.castlemock.model.core.service.project.ProjectServiceFacade;
import com.castlemock.service.core.manager.FileManager;
import com.castlemock.web.core.model.project.CreateProjectRequest;
import com.castlemock.web.core.model.project.UpdateProjectRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * The {@link ProjectCoreRestController} is the REST controller that provides
 * the interface for the core operations.
 * @author Karl Dahlgren
 * @since 1.19
 */
@Controller
@RequestMapping("/api/rest/core")
@Api(value="Core", description="REST Operations for Castle Mock Core", tags = {"Core"})
@ConditionalOnExpression("${server.mode.demo} == false")
public class ProjectCoreRestController extends AbstractRestController {

    private final FileManager fileManager;
    private final ProjectServiceFacade projectServiceFacade;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectCoreRestController.class);

    @Autowired
    public ProjectCoreRestController(final ServiceProcessor serviceProcessor,
                                     final FileManager fileManager,
                                     final ProjectServiceFacade projectServiceFacade){
        super(serviceProcessor);
        this.fileManager = Objects.requireNonNull(fileManager);
        this.projectServiceFacade = Objects.requireNonNull(projectServiceFacade);
    }


    /**
     * The method retrieves all projects
     * @return The retrieved project.
     */
    @ApiOperation(value = "Get projects",response = Project.class,
            notes = "Get projects. Required authorization: Reader, Modifier or Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved project")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/project")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<List<Project>> getProjects() {
        return ResponseEntity.ok(projectServiceFacade.findAll());
    }

    /**
     * The method retrieves a project with a particular ID.
     * @param type The type of the project.
     * @param projectId The project id.
     * @return The retrieved project.
     */
    @ApiOperation(value = "Get project",response = Project.class,
            notes = "Get project. Required authorization: Reader, Modifier or Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved project")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/project/{type}/{projectId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Project> getProject(
            @ApiParam(name = "type", value = "The type of the project", allowableValues = "rest,soap")
            @PathVariable("type") final String type,
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable("projectId") final String projectId) {
        return ResponseEntity.ok(projectServiceFacade.findOne(type, projectId));
    }


    /**
     * The method creates a new project
     * @return The retrieved project.
     */
    @ApiOperation(value = "Create project",response = Project.class,
            notes = "Create project. Required authorization: Modifier or Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created project")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/project")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<Project> createProject(@RequestBody final CreateProjectRequest request) {
        final Project project = new Project();
        project.setDescription(request.getDescription());
        project.setName(request.getName());
        project.setUpdated(new Date());
        project.setCreated(new Date());
        return ResponseEntity.ok(projectServiceFacade.save(request.getProjectType(), project));
    }

    /**
     * The method updates an existing project
     * @return The updated project.
     */
    @ApiOperation(value = "Update project",response = Project.class,
            notes = "Update project. Required authorization: Modifier or Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created project")
    })
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{type}/{projectId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<Project> updateProject( @ApiParam(name = "type", value = "The type of the project", allowableValues = "rest,soap")
                                                    @PathVariable("type") final String type,
                                                @ApiParam(name = "projectId", value = "The id of the project")
                                                    @PathVariable("projectId") final String projectId,
                                                @RequestBody final UpdateProjectRequest request) {
        final Project project = projectServiceFacade.findOne(type, projectId);
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        return ResponseEntity.ok(projectServiceFacade.update(type, projectId, project));
    }

    /**
     * The REST operation deletes a project with a particular ID.
     * @param type The type of the project.
     * @param projectId The project id.
     * @return The deleted project
     */
    @ApiOperation(value = "Delete project",response = Project.class,
            notes = "Delete project. Required authorization: Modifier or Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted project")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/project/{type}/{projectId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Project> deleteProject(
            @ApiParam(name = "type", value = "The type of the project", allowableValues = "rest,soap")
            @PathVariable("type") final String type,
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable("projectId") final String projectId) {
        return ResponseEntity.ok(projectServiceFacade.delete(type, projectId));
    }

    /**
     * The REST operations imports a project.
     * @param type The type of the project.
     * @param multipartFile The project file which will be imported.
     * @return A HTTP response.
     */
    @ApiOperation(value = "Import project",response = Project.class,
            notes = "Import project. Required authorization: Modifier or Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully imported project")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/project/{type}/import")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Project> importProject(
            @ApiParam(name = "type", value = "The type of the project", allowableValues = "rest,soap")
            @PathVariable("type") final String type,
            @ApiParam(name = "file", value = "The project file which will be imported.")
            @RequestParam("file") final MultipartFile multipartFile) {
        File file = null;
        try {
            file = fileManager.uploadFile(multipartFile);
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            final StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }

            Project project = projectServiceFacade.importProject(type, stringBuilder.toString());
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            LOGGER.error("Unable to import project", e);
            throw new RuntimeException(e);
        } finally {
            fileManager.deleteUploadedFile(file);
        }
    }

    /**
     * The REST operations exports a project.
     * @param type The type of the project.
     * @param projectId The id of the project that will be exported.
     * @return A HTTP response.
     */
    @ApiOperation(value = "Export project",response = Project.class,
            notes = "Export project. Required authorization: Reader, Modifier or Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully exported project")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/project/{type}/{projectId}/export")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<String> exportProject(
            @ApiParam(name = "type", value = "The type of the project", allowableValues = "rest,soap")
            @PathVariable("type") final String type,
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable("projectId") final String projectId) {
        return ResponseEntity.ok(projectServiceFacade.exportProject(type, projectId));
    }
}
