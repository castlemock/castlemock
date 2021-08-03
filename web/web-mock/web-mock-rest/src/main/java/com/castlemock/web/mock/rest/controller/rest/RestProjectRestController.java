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
import com.castlemock.service.mock.rest.project.input.ImportRestDefinitionInput;
import com.castlemock.service.mock.rest.project.input.ReadRestProjectInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestApplicationsForwardedEndpointInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestApplicationsStatusInput;
import com.castlemock.service.mock.rest.project.output.ReadRestProjectOutput;
import com.castlemock.web.core.controller.rest.AbstractRestController;
import com.castlemock.web.mock.rest.model.LinkDefinitionRequest;
import com.castlemock.web.mock.rest.model.UpdateRestApplicationForwardedEndpointsRequest;
import com.castlemock.web.mock.rest.model.UpdateRestApplicationStatusesRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("api/rest/rest")
@Api(value="REST - Application", description="REST Operations for Castle Mock REST Application",
        tags = {"REST - Application"})
public class RestProjectRestController extends AbstractRestController {

    private final FileManager fileManager;

    @Autowired
    public RestProjectRestController(final ServiceProcessor serviceProcessor,
                                     final FileManager fileManager){
        super(serviceProcessor);
        this.fileManager = Objects.requireNonNull(fileManager);
    }

    @ApiOperation(value = "Get REST Project", response = RestProject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved REST Project")})
    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<RestProject> getRestProject(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId) {
        final ReadRestProjectOutput output = super.serviceProcessor.process(ReadRestProjectInput.builder()
                .restProjectId(projectId)
                .build());

        return ResponseEntity.ok(output.getRestProject());
    }

    @ApiOperation(value = "Update Application statuses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated REST application statuses")})
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/application/status")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateApplicationStatuses(
            @ApiParam(name = "projectId", value = "The id of the project")
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

    @ApiOperation(value = "Update Application forwarded endpoints")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated REST application forwarded endpoints")})
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/application/endpoint/forwarded")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateApplicationForwardedEndpoints(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestBody UpdateRestApplicationForwardedEndpointsRequest request){
        super.serviceProcessor.process(UpdateRestApplicationsForwardedEndpointInput.builder()
                .projectId(projectId)
                .applicationIds(request.getApplicationIds())
                .forwardedEndpoint(request.getForwardedEndpoint())
                .build());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Upload definition")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully uploaded definition")})
    @RequestMapping(method = RequestMethod.POST, value = "/project/{projectId}/definition/file")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> uploadDefinition(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestParam("file") final MultipartFile multipartFile,
            @RequestParam("generateResponse") final boolean generateResponse,
            @RequestParam("definitionType") final RestDefinitionType definitionType){

        try {
            final File file = fileManager.uploadFile(multipartFile);
            super.serviceProcessor.process(ImportRestDefinitionInput.builder()
                    .restProjectId(projectId)
                    .files(List.of(file))
                    .generateResponse(generateResponse)
                    .definitionType(definitionType)
                    .build());
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation(value = "Link definition")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully linked definition")})
    @RequestMapping(method = RequestMethod.POST, value = "/project/{projectId}/definition/link")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> linkDefinition(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestBody final LinkDefinitionRequest request){
        super.serviceProcessor.process(ImportRestDefinitionInput.builder()
                .restProjectId(projectId)
                .files(null)
                .generateResponse(request.getGenerateResponse())
                .location(request.getUrl())
                .definitionType(request.getDefinitionType())
                .build());
        return ResponseEntity.ok().build();
    }

}
