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
import com.castlemock.model.mock.rest.domain.RestParameterQuery;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.service.mock.rest.project.input.CreateRestResourceInput;
import com.castlemock.service.mock.rest.project.input.DeleteRestResourceInput;
import com.castlemock.service.mock.rest.project.input.ReadRestResourceInput;
import com.castlemock.service.mock.rest.project.input.ReadRestResourceQueryParametersInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestMethodsForwardedEndpointInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestMethodsStatusInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestResourceInput;
import com.castlemock.service.mock.rest.project.output.CreateRestResourceOutput;
import com.castlemock.service.mock.rest.project.output.DeleteRestResourceOutput;
import com.castlemock.service.mock.rest.project.output.ReadRestResourceOutput;
import com.castlemock.service.mock.rest.project.output.ReadRestResourceQueryParametersOutput;
import com.castlemock.service.mock.rest.project.output.UpdateRestResourceOutput;
import com.castlemock.web.core.controller.rest.AbstractRestController;
import com.castlemock.web.mock.rest.model.CreateRestResourceRequest;
import com.castlemock.web.mock.rest.model.UpdateRestMethodForwardedEndpointsRequest;
import com.castlemock.web.mock.rest.model.UpdateRestMethodStatusesRequest;
import com.castlemock.web.mock.rest.model.UpdateRestResourceRequest;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
@RequestMapping("api/rest/rest")
@Tag(name="REST - Resource", description="REST Operations for Castle Mock REST Resource")
public class RestResourceRestController extends AbstractRestController {

    @Autowired
    public RestResourceRestController(final ServiceProcessor serviceProcessor){
        super(serviceProcessor);
    }

    @Operation(summary =  "Get Resource")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved REST resource")})
    @RequestMapping(method = RequestMethod.GET,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<RestResource> getResource(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId) {
        final ReadRestResourceOutput output = super.serviceProcessor.process(ReadRestResourceInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .build());
        return ResponseEntity.ok(output.getRestResource());
    }

    @Operation(summary =  "Get Resource Parameters")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource parameters")})
    @RequestMapping(method = RequestMethod.GET,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/parameter")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Set<RestParameterQuery>> getResourceParameters(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId) {
        final ReadRestResourceQueryParametersOutput output =
                serviceProcessor.process(ReadRestResourceQueryParametersInput.builder()
                        .projectId(projectId)
                        .applicationId(applicationId)
                        .resourceId(resourceId)
                        .build());
        return ResponseEntity.ok(output.getQueries());
    }

    @Operation(summary =  "Delete Resource")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted REST resource")})
    @RequestMapping(method = RequestMethod.DELETE,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestResource> deleteResource(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId) {
        final DeleteRestResourceOutput output = super.serviceProcessor.process(DeleteRestResourceInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .build());
        return ResponseEntity.ok(output.getResource());
    }

    @Operation(summary =  "Update Resource")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved REST resource")})
    @RequestMapping(method = RequestMethod.PUT,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestResource> updateResource(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @RequestBody UpdateRestResourceRequest request) {
        final UpdateRestResourceOutput output = super.serviceProcessor.process(UpdateRestResourceInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .name(request.getName())
                .uri(request.getUri())
                .build());
        return ResponseEntity.ok(output.getUpdatedRestResource());
    }

    @Operation(summary =  "Create Resource")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved REST resource")})
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/application/{applicationId}/resource")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestResource> createResource(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @RequestBody CreateRestResourceRequest request) {
        final CreateRestResourceOutput output = super.serviceProcessor.process(CreateRestResourceInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .name(request.getName())
                .uri(request.getUri())
                .build());
        return ResponseEntity.ok(output.getCreatedRestResource());
    }

    @Operation(summary =  "Update resource statuses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated REST resource statuses")})
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/status")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateResourceStatuses(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @org.springframework.web.bind.annotation.RequestBody UpdateRestMethodStatusesRequest request){
        request.getMethodIds()
                .forEach(methodId -> super.serviceProcessor.process(UpdateRestMethodsStatusInput.builder()
                        .projectId(projectId)
                        .applicationId(applicationId)
                        .resourceId(resourceId)
                        .methodId(methodId)
                        .methodStatus(request.getStatus())
                        .build()));
        return ResponseEntity.ok().build();
    }

    @Operation(summary =  "Update Resource forwarded endpoints")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated REST Resource forwarded endpoints")})
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/endpoint/forwarded")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateResourceForwardedEndpoints(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @org.springframework.web.bind.annotation.RequestBody UpdateRestMethodForwardedEndpointsRequest request){
        super.serviceProcessor.process(UpdateRestMethodsForwardedEndpointInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodIds(request.getMethodIds())
                .forwardedEndpoint(request.getForwardedEndpoint())
                .build());
        return ResponseEntity.ok().build();
    }

}
