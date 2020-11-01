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

package com.castlemock.web.mock.rest.web.rest.controller;

import com.castlemock.core.mock.rest.model.RestDefinitionType;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.service.project.input.ImportRestDefinitionInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestProjectInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestApplicationsForwardedEndpointInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestApplicationsStatusInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestProjectOutput;
import com.castlemock.web.basis.manager.FileManager;
import com.castlemock.web.basis.web.rest.controller.AbstractRestController;
import com.castlemock.web.mock.rest.web.rest.controller.model.LinkDefinitionRequest;
import com.castlemock.web.mock.rest.web.rest.controller.model.UpdateRestApplicationForwardedEndpointsRequest;
import com.castlemock.web.mock.rest.web.rest.controller.model.UpdateRestApplicationStatusesRequest;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("api/rest/rest")
@Api(value="REST - Application", description="REST Operations for Castle Mock REST Application",
        tags = {"REST - Application"})
public class RestProjectRestController extends AbstractRestController {

    @Autowired
    private FileManager fileManager;

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

        try {
            final List<File> files = fileManager.uploadFiles(request.getUrl());
            super.serviceProcessor.process(ImportRestDefinitionInput.builder()
                    .restProjectId(projectId)
                    .files(files)
                    .generateResponse(request.getGenerateResponse())
                    .location(request.getUrl())
                    .definitionType(request.getDefinitionType())
                    .build());
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
