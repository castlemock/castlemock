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

package com.castlemock.web.mock.soap.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.project.Project;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.service.core.manager.FileManager;
import com.castlemock.service.mock.soap.project.input.CreateSoapPortsInput;
import com.castlemock.service.mock.soap.project.input.CreateSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.DeleteSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.ExportSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.ImportSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.ReadSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.UpdateSoapPortsForwardedEndpointInput;
import com.castlemock.service.mock.soap.project.input.UpdateSoapPortsStatusInput;
import com.castlemock.service.mock.soap.project.input.UpdateSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.CreateSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.DeleteSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.ExportSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.ImportSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.ReadSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapProjectOutput;
import com.castlemock.web.core.controller.rest.AbstractRestController;
import com.castlemock.web.core.model.project.CreateProjectRequest;
import com.castlemock.web.core.model.project.UpdateProjectRequest;
import com.castlemock.web.mock.soap.model.LinkWsdlRequest;
import com.castlemock.web.mock.soap.model.UpdateSoapPortForwardedEndpointsRequest;
import com.castlemock.web.mock.soap.model.UpdateSoapPortStatusesRequest;
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
@RequestMapping("api/rest/soap")
@Tag(name="SOAP - Project", description="REST Operations for Castle Mock SOAP Project")
public class SoapProjectRestController extends AbstractRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(SoapProjectRestController.class);

    private final FileManager fileManager;

    @Autowired
    public SoapProjectRestController(final ServiceProcessor serviceProcessor,
                                     final FileManager fileManager){
        super(serviceProcessor);
        this.fileManager = Objects.requireNonNull(fileManager);
    }

    @Operation(summary =  "Get Project")
    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<SoapProject> getProject(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId){
        final ReadSoapProjectOutput output = super.serviceProcessor.process(ReadSoapProjectInput.builder()
                .projectId(projectId)
                .build());
        return ResponseEntity.ok(output.getProject());
    }

    /**
     * The method creates a new project
     * @return The retrieved project.
     */
    @Operation(summary =  "Create SOAP project", description = "Create SOAP project. Required authorization: Modifier or Admin.")
    @RequestMapping(method = RequestMethod.POST, value = "/project")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<SoapProject> createSoapProject(@RequestBody final CreateProjectRequest request) {
        final CreateSoapProjectOutput output = super.serviceProcessor.process(CreateSoapProjectInput.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build());

        return ResponseEntity.ok(output.getProject());
    }

    /**
     * The method updates an existing project
     * @return The updated project.
     */
    @Operation(summary =  "Update SOAP project",
            description = "Update SOAP project. Required authorization: Modifier or Admin.")
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<Project> updateProject(@Parameter(name = "projectId", description = "The id of the project")
                                                                @PathVariable("projectId") final String projectId,
                                                                @RequestBody final UpdateProjectRequest request) {
        final UpdateSoapProjectOutput output = super.serviceProcessor.process(UpdateSoapProjectInput.builder()
                .projectId(projectId)
                .name(request.getName())
                .description(request.getDescription())
                .build());
        return ResponseEntity.ok(output.getProject());
    }

    @Operation(summary =  "Update Port statuses")
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/port/status")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updatePortStatuses(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestBody UpdateSoapPortStatusesRequest request){
        request.getPortIds()
                .forEach(portId -> super.serviceProcessor.process(UpdateSoapPortsStatusInput.builder()
                        .projectId(projectId)
                        .portId(portId)
                        .operationStatus(request.getStatus())
                        .build()));
        return ResponseEntity.ok().build();
    }

    @Operation(summary =  "Update Port forwarded endpoints")
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/port/endpoint/forwarded")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updatePortForwardedEndpoints(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestBody UpdateSoapPortForwardedEndpointsRequest request){
        super.serviceProcessor.process(UpdateSoapPortsForwardedEndpointInput.builder()
                .projectId(projectId)
                .portIds(request.getPortIds())
                .forwardedEndpoint(request.getForwardedEndpoint())
                .build());
        return ResponseEntity.ok().build();
    }

    @Operation(summary =  "Upload WSDL")
    @RequestMapping(method = RequestMethod.POST, value = "/project/{projectId}/wsdl/file")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> uploadWSDL(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestParam("file") final MultipartFile multipartFile,
            @RequestParam("generateResponse") final boolean generateResponse){

        try {
            final File file = fileManager.uploadFile(multipartFile);
            super.serviceProcessor.process(CreateSoapPortsInput.builder()
                    .projectId(projectId)
                    .files(List.of(file))
                    .generateResponse(generateResponse)
                    .includeImports(false)
                    .build());
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary =  "Link WSDL")
    @RequestMapping(method = RequestMethod.POST, value = "/project/{projectId}/wsdl/link")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> linkWSDL(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestBody final LinkWsdlRequest request){
        super.serviceProcessor.process(CreateSoapPortsInput.builder()
                .projectId(projectId)
                .files(null)
                .location(request.getUrl())
                .includeImports(request.getIncludeImports())
                .generateResponse(request.getGenerateResponse())
                .build());
        return ResponseEntity.ok().build();
    }

    /**
     * The SOAP operation deletes a project with a particular ID.
     * @param projectId The project id.
     * @return The deleted project
     */
    @Operation(summary =  "Delete project",
            description = "Delete project. Required authorization: Modifier or Admin.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/project/{projectId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Project> deleteProject(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable("projectId") final String projectId) {
        final DeleteSoapProjectOutput output = this.serviceProcessor.process(DeleteSoapProjectInput.builder()
                .projectId(projectId)
                .build());
        return ResponseEntity.ok(output.getProject());
    }

    /**
     * The SOAP operations imports a project.
     * @param multipartFile The project file which will be imported.
     * @return A HTTP response.
     */
    @Operation(summary =  "Import project",
            description = "Import project. Required authorization: Modifier or Admin.")
    @RequestMapping(method = RequestMethod.POST, value = "/project/{projectId}/import")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Project> importProject(
            @Parameter(name = "file", description = "The project file which will be imported.")
            @RequestParam("file") final MultipartFile multipartFile) {
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

            final ImportSoapProjectOutput output = this.serviceProcessor.process(ImportSoapProjectInput.builder()
                    .projectRaw(stringBuilder.toString())
                    .build());

            final Project project = output.getProject();
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            LOGGER.error("Unable to import project", e);
            throw new RuntimeException(e);
        } finally {
            fileManager.deleteUploadedFile(file);
        }
    }

    /**
     * The SOAP operations exports a project.
     * @param projectId The id of the project that will be exported.
     * @return A HTTP response.
     */
    @Operation(summary =  "Export project",
            description = "Export project. Required authorization: Reader, Modifier or Admin.")
    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}/export")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<String> exportProject(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable("projectId") final String projectId) {
        final ExportSoapProjectOutput output = this.serviceProcessor.process(ExportSoapProjectInput.builder()
                .projectId(projectId)
                .build());
        return ResponseEntity.ok(output.getProject());
    }


}
