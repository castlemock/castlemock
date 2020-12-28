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

package com.castlemock.web.mock.rest.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.service.mock.rest.project.input.*;
import com.castlemock.service.mock.rest.project.output.CreateRestApplicationOutput;
import com.castlemock.service.mock.rest.project.output.DeleteRestApplicationOutput;
import com.castlemock.service.mock.rest.project.output.ReadRestApplicationOutput;
import com.castlemock.service.mock.rest.project.output.UpdateRestApplicationOutput;
import com.castlemock.web.core.controller.rest.AbstractRestController;
import com.castlemock.web.mock.rest.model.CreateRestApplicationRequest;
import com.castlemock.web.mock.rest.model.UpdateRestApplicationRequest;
import com.castlemock.web.mock.rest.model.UpdateRestResourceForwardedEndpointsRequest;
import com.castlemock.web.mock.rest.model.UpdateRestResourceStatusesRequest;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/rest/rest")
@Api(value="REST - Application", description="REST Operations for Castle Mock REST Application",
        tags = {"REST - Application"})
public class RestApplicationRestController extends AbstractRestController {

    @Autowired
    public RestApplicationRestController(final ServiceProcessor serviceProcessor){
        super(serviceProcessor);
    }

    @ApiOperation(value = "Get Application", response = RestApplication.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved REST Application")})
    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}/application/{applicationId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<RestApplication> getApplication(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId) {
        final ReadRestApplicationOutput output = super.serviceProcessor.process(ReadRestApplicationInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .build());
        return ResponseEntity.ok(output.getRestApplication());
    }

    @ApiOperation(value = "Delete Application", response = RestApplication.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted REST Application")})
    @RequestMapping(method = RequestMethod.DELETE, value = "/project/{projectId}/application/{applicationId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestApplication> deleteApplication(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId) {
        final DeleteRestApplicationOutput output = super.serviceProcessor.process(DeleteRestApplicationInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .build());
        return ResponseEntity.ok(output.getApplication());
    }

    @ApiOperation(value = "Update Application", response = RestApplication.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated REST Application")})
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/application/{applicationId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestApplication> updateApplication(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @RequestBody final UpdateRestApplicationRequest request) {
        final UpdateRestApplicationOutput output = super.serviceProcessor.process(UpdateRestApplicationInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .name(request.getName())
                .build());
        return ResponseEntity.ok(output.getUpdatedRestApplication());
    }

    @ApiOperation(value = "Create Application", response = RestApplication.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created REST Application")})
    @RequestMapping(method = RequestMethod.POST, value = "/project/{projectId}/application")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestApplication> createApplication(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestBody CreateRestApplicationRequest request) {
        final CreateRestApplicationOutput output = super.serviceProcessor.process(CreateRestApplicationInput.builder()
                .projectId(projectId)
                .name(request.getName())
                .build());
        return ResponseEntity.ok(output.getSavedRestApplication());
    }

    @ApiOperation(value = "Update resource statuses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated REST resource statuses")})
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/application/{applicationId}/resource/status")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateResourceStatuses(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @RequestBody UpdateRestResourceStatusesRequest request){
        request.getResourceIds()
                .forEach(resourceId -> super.serviceProcessor.process(UpdateRestResourcesStatusInput.builder()
                        .projectId(projectId)
                        .applicationId(applicationId)
                        .resourceId(resourceId)
                        .methodStatus(request.getStatus())
                        .build()));
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Update Resource forwarded endpoints")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated REST Resource forwarded endpoints")})
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/application/{applicationId}/resource/endpoint/forwarded")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateResourceForwardedEndpoints(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @org.springframework.web.bind.annotation.RequestBody UpdateRestResourceForwardedEndpointsRequest request){
        super.serviceProcessor.process(UpdateRestResourcesForwardedEndpointInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceIds(request.getResourceIds())
                .forwardedEndpoint(request.getForwardedEndpoint())
                .build());
        return ResponseEntity.ok().build();
    }

}
