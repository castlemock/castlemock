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

package com.castlemock.web.mock.rest.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.mock.rest.RestDefinitionType;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.service.core.manager.FileManager;
import com.castlemock.service.mock.rest.project.input.CreateRestProjectInput;
import com.castlemock.service.mock.rest.project.input.DeleteRestProjectInput;
import com.castlemock.service.mock.rest.project.input.ExportRestProjectInput;
import com.castlemock.service.mock.rest.project.input.ImportRestDefinitionInput;
import com.castlemock.service.mock.rest.project.input.ImportRestProjectInput;
import com.castlemock.service.mock.rest.project.input.ReadRestProjectInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestApplicationsForwardedEndpointInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestApplicationsStatusInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestProjectInput;
import com.castlemock.service.mock.rest.project.output.CreateRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.DeleteRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.ExportRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.ImportRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.ReadRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.UpdateRestProjectOutput;
import com.castlemock.web.core.controller.rest.AbstractRestController;
import com.castlemock.web.core.model.project.CreateProjectRequest;
import com.castlemock.web.core.model.project.UpdateProjectRequest;
import com.castlemock.web.mock.rest.model.LinkDefinitionRequest;
import com.castlemock.web.mock.rest.model.UpdateRestApplicationForwardedEndpointsRequest;
import com.castlemock.web.mock.rest.model.UpdateRestApplicationStatusesRequest;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("api/rest")
@Tag(name="REST - Project", description="REST Operations for Castle Mock REST Project")
public class RestProjectRestController extends AbstractRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(RestProjectRestController.class);

    private final FileManager fileManager;

    @Autowired
    public RestProjectRestController(final ServiceProcessor serviceProcessor,
                                     final FileManager fileManager){
        super(serviceProcessor);
        this.fileManager = Objects.requireNonNull(fileManager, "fileManager");
    }

    @Operation(summary =  "Get REST Project")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved REST Project")})
    @RequestMapping(method = RequestMethod.GET, value = "/rest/project/{projectId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<RestProject> getRestProject(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId) {
        final ReadRestProjectOutput output = super.serviceProcessor.process(ReadRestProjectInput.builder()
                .projectId(projectId)
                .build());

        return output.getProject()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());    }

    /**
     * The method creates a new project
     * @return The retrieved project.
     */
    @Operation(summary =  "Create REST project", description = "Create REST project. Required authorization: Modifier or Admin.")
    @RequestMapping(method = RequestMethod.POST, value = "/rest/project")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<RestProject> createRestProject(@RequestBody final CreateProjectRequest request) {
        final CreateRestProjectOutput output = super.serviceProcessor.process(CreateRestProjectInput.builder()
                .name(request.getName())
                .description(request.getDescription().orElse(null))
                .build());

        return ResponseEntity.ok(output.getProject());
    }

    /**
     * The method updates an existing project
     * @return The updated project.
     */
    @Operation(summary =  "Update REST project",
            description = "Update REST project. Required authorization: Modifier or Admin.")
    @RequestMapping(method = RequestMethod.PUT, value = "/rest/project/{projectId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<RestProject> updateProject(@Parameter(name = "projectId", description = "The id of the project")
                                                               @PathVariable("projectId") final String projectId,
                                                               @RequestBody final UpdateProjectRequest request) {
        final UpdateRestProjectOutput output = super.serviceProcessor.process(UpdateRestProjectInput.builder()
                .projectId(projectId)
                .name(request.getName())
                .description(request.getDescription().orElse(null))
                .build());
        return output.getProject()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());     }

    @Operation(summary =  "Update Application statuses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated REST application statuses")})
    @RequestMapping(method = RequestMethod.PUT, value = "/rest/project/{projectId}/application/status")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateApplicationStatuses(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestBody UpdateRestApplicationStatusesRequest request){
        request.getApplicationIds()
                .forEach(applicationId -> super.serviceProcessor.process(UpdateRestApplicationsStatusInput.builder()
                        .projectId(projectId)
                        .applicationId(applicationId)
                        .methodStatus(request.getStatus())
                        .build()));
        return ResponseEntity.ok().build();
    }

    @Operation(summary =  "Update Application forwarded endpoints")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated REST application forwarded endpoints")})
    @RequestMapping(method = RequestMethod.PUT, value = "/rest/project/{projectId}/application/endpoint/forwarded")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateApplicationForwardedEndpoints(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestBody UpdateRestApplicationForwardedEndpointsRequest request){
        super.serviceProcessor.process(UpdateRestApplicationsForwardedEndpointInput.builder()
                .projectId(projectId)
                .applicationIds(request.getApplicationIds())
                .forwardedEndpoint(request.getForwardedEndpoint())
                .build());
        return ResponseEntity.ok().build();
    }

    @Operation(summary =  "Upload definition")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully uploaded definition")})
    @RequestMapping(method = RequestMethod.POST, value = "/rest/project/{projectId}/definition/file")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> uploadDefinition(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestParam("file") final MultipartFile multipartFile,
            @RequestParam("generateResponse") final boolean generateResponse,
            @RequestParam("definitionType") final RestDefinitionType definitionType){

        try {
            final File file = fileManager.uploadFile(multipartFile);
            super.serviceProcessor.process(ImportRestDefinitionInput.builder()
                    .projectId(projectId)
                    .files(List.of(file))
                    .generateResponse(generateResponse)
                    .definitionType(definitionType)
                    .build());
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary =  "Link definition")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully linked definition")})
    @RequestMapping(method = RequestMethod.POST, value = "/rest/project/{projectId}/definition/link")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> linkDefinition(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestBody final LinkDefinitionRequest request){
        super.serviceProcessor.process(ImportRestDefinitionInput.builder()
                .projectId(projectId)
                .files(null)
                .generateResponse(request.getGenerateResponse())
                .location(request.getUrl())
                .definitionType(request.getDefinitionType())
                .build());
        return ResponseEntity.ok().build();
    }

    /**
     * The REST operation deletes a project with a particular ID.
     * @param projectId The project id.
     * @return The deleted project
     */
    @Operation(summary =  "Delete project",
            description = "Delete project. Required authorization: Modifier or Admin.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/rest/project/{projectId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<RestProject> deleteProject(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable("projectId") final String projectId) {
        final DeleteRestProjectOutput output = this.serviceProcessor.process(DeleteRestProjectInput.builder()
                .projectId(projectId)
                .build());
        return output.getProject()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());     }

    /**
     * The REST operations imports a project.
     * @param multipartFile The project file which will be imported.
     * @return A HTTP response.
     */
    @Operation(summary =  "Import project",
            description = "Import project. Required authorization: Modifier or Admin.")
    @RequestMapping(method = RequestMethod.POST, value = "/rest/project/import")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<RestProject> importProject(
            @Parameter(name = "file", description = "The project file which will be imported.")
            @RequestParam("file") final MultipartFile multipartFile) {
        return this.importProjectInternally(multipartFile);
    }

    /**
     * The REST operations imports a project.
     * @param multipartFile The project file which will be imported.
     * @return A HTTP response.
     */
    @Operation(summary =  "Import project (Deprecated)",
            description = "Deprecated import project endpoint. Required authorization: Modifier or Admin.")
    @RequestMapping(method = RequestMethod.POST, value = "/core/project/rest/import")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @Deprecated
    public @ResponseBody
    ResponseEntity<RestProject> deprecatedImportProject(
            @Parameter(name = "file", description = "The project file which will be imported.")
            @RequestParam("file") final MultipartFile multipartFile) {
        return this.importProjectInternally(multipartFile);
    }

    /**
     * The REST operations exports a project.
     * @param projectId The id of the project that will be exported.
     * @return A HTTP response.
     */
    @Operation(summary =  "Export project",
            description = "Export project. Required authorization: Reader, Modifier or Admin.")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/project/{projectId}/export")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<String> exportProject(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable("projectId") final String projectId) {
        final ExportRestProjectOutput output = this.serviceProcessor.process(ExportRestProjectInput.builder()
                .projectId(projectId)
                .build());
        return output.getExportedProject()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    private ResponseEntity<RestProject> importProjectInternally(final MultipartFile multipartFile) {
        File file = null;
        try {
            file = fileManager.uploadFile(multipartFile);
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            final StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line).append("\n");
            }

            final ImportRestProjectOutput output = this.serviceProcessor.process(ImportRestProjectInput.builder()
                    .projectRaw(stringBuilder.toString())
                    .build());

            final RestProject project = output.getProject();
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            LOGGER.error("Unable to import project", e);
            throw new RuntimeException(e);
        } finally {
            fileManager.deleteUploadedFile(file);
        }
    }

}
