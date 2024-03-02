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
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.service.mock.rest.project.input.CreateRestMethodInput;
import com.castlemock.service.mock.rest.project.input.DeleteRestMethodInput;
import com.castlemock.service.mock.rest.project.input.ReadRestMethodInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestMethodInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestMockResponseStatusInput;
import com.castlemock.service.mock.rest.project.output.CreateRestMethodOutput;
import com.castlemock.service.mock.rest.project.output.DeleteRestMethodOutput;
import com.castlemock.service.mock.rest.project.output.ReadRestMethodOutput;
import com.castlemock.service.mock.rest.project.output.UpdateRestMethodOutput;
import com.castlemock.web.core.controller.rest.AbstractRestController;
import com.castlemock.web.mock.rest.model.CreateRestMethodRequest;
import com.castlemock.web.mock.rest.model.UpdateRestMethodRequest;
import com.castlemock.web.mock.rest.model.UpdateRestMockResponseStatusesRequest;
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

@Controller
@RequestMapping("api/rest/rest")
@Tag(name="REST - Method", description="REST Operations for Castle Mock REST Operation")
public class RestMethodRestController extends AbstractRestController {

    @Autowired
    public RestMethodRestController(final ServiceProcessor serviceProcessor){
        super(serviceProcessor);
    }

    @Operation(summary =  "Get Method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved method")})
    @RequestMapping(method = RequestMethod.GET,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<RestMethod> getMethod(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @Parameter(name = "methodId", description = "The id of the method")
            @PathVariable(value = "methodId") final String methodId) {
        final ReadRestMethodOutput output = super.serviceProcessor.process(ReadRestMethodInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .build());
        return output.getMethod()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary =  "Delete Method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted method")})
    @RequestMapping(method = RequestMethod.DELETE,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestMethod> deleteMethod(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @Parameter(name = "methodId", description = "The id of the method")
            @PathVariable(value = "methodId") final String methodId) {
        final DeleteRestMethodOutput output = super.serviceProcessor.process(DeleteRestMethodInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .build());
        return output.getMethod()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());    }

    @Operation(summary =  "Update Method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated method")})
    @RequestMapping(method = RequestMethod.PUT,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestMethod> updateMethod(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @Parameter(name = "methodId", description = "The id of the method")
            @PathVariable(value = "methodId") final String methodId,
            @RequestBody UpdateRestMethodRequest request) {
        final UpdateRestMethodOutput output = super.serviceProcessor.process(UpdateRestMethodInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .name(request.getName())
                .httpMethod(request.getHttpMethod())
                .responseStrategy(request.getResponseStrategy())
                .status(request.getStatus())
                .defaultMockResponseId(request.getDefaultMockResponseId()
                        .orElse(null))
                .forwardedEndpoint(request.getForwardedEndpoint()
                        .orElse(null))
                .networkDelay(request.getNetworkDelay()
                        .orElse(null))
                .simulateNetworkDelay(request.getSimulateNetworkDelay()
                        .orElse(null))
                .automaticForward(request.getAutomaticForward()
                        .orElse(null))
                .build());
        return output.getMethod()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());    }


    @Operation(summary =  "Create Method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created method")})
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestMethod> createMethod(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @RequestBody CreateRestMethodRequest request) {
       final CreateRestMethodOutput output = super.serviceProcessor.process(CreateRestMethodInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .name(request.getName())
                .httpMethod(request.getHttpMethod())
                .build());
       return ResponseEntity.ok(output.getMethod());
    }

    @Operation(summary =  "Update mock response statuses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated SOAP operation statuses")})
    @RequestMapping(method = RequestMethod.PUT,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}/mockresponse/status")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateMockResponseStatuses(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @Parameter(name = "methodId", description = "The id of the method")
            @PathVariable(value = "methodId") final String methodId,
            @RequestBody UpdateRestMockResponseStatusesRequest request){
        request.getMockResponseIds()
                .forEach(mockResponseId -> super.serviceProcessor.process(UpdateRestMockResponseStatusInput.builder()
                        .projectId(projectId)
                        .applicationId(applicationId)
                        .resourceId(resourceId)
                        .methodId(methodId)
                        .mockResponseId(mockResponseId)
                        .status(request.getStatus())
                        .build()));
        return ResponseEntity.ok().build();
    }

}
