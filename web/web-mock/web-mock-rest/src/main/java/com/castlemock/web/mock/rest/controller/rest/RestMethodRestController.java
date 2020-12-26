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

import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.service.project.input.*;
import com.castlemock.core.mock.rest.service.project.output.CreateRestMethodOutput;
import com.castlemock.core.mock.rest.service.project.output.DeleteRestMethodOutput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestMethodOutput;
import com.castlemock.core.mock.rest.service.project.output.UpdateRestMethodOutput;
import com.castlemock.web.basis.web.rest.controller.AbstractRestController;
import com.castlemock.web.mock.rest.model.CreateRestMethodRequest;
import com.castlemock.web.mock.rest.model.UpdateRestMethodRequest;
import com.castlemock.web.mock.rest.model.UpdateRestMockResponseStatusesRequest;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/rest/rest")
@Api(value="REST - Method", description="REST Operations for Castle Mock REST Operation", tags = {"REST - Method"})
public class RestMethodRestController extends AbstractRestController {

    @ApiOperation(value = "Get Method", response = RestMethod.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved method")})
    @RequestMapping(method = RequestMethod.GET,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<RestMethod> getMethod(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @ApiParam(name = "methodId", value = "The id of the method")
            @PathVariable(value = "methodId") final String methodId) {
        final ReadRestMethodOutput output = super.serviceProcessor.process(ReadRestMethodInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .restMethodId(methodId)
                .build());
        return ResponseEntity.ok(output.getRestMethod());
    }

    @ApiOperation(value = "Delete Method", response = RestMethod.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted method")})
    @RequestMapping(method = RequestMethod.DELETE,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestMethod> deleteMethod(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @ApiParam(name = "methodId", value = "The id of the method")
            @PathVariable(value = "methodId") final String methodId) {
        final DeleteRestMethodOutput output = super.serviceProcessor.process(DeleteRestMethodInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .restMethodId(methodId)
                .build());
        return ResponseEntity.ok(output.getMethod());
    }

    @ApiOperation(value = "Update Method", response = RestMethod.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated method")})
    @RequestMapping(method = RequestMethod.PUT,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestMethod> updateMethod(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @ApiParam(name = "methodId", value = "The id of the method")
            @PathVariable(value = "methodId") final String methodId,
            @RequestBody UpdateRestMethodRequest request) {
        final UpdateRestMethodOutput output = super.serviceProcessor.process(UpdateRestMethodInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .restMethodId(methodId)
                .name(request.getName())
                .defaultMockResponseId(request.getDefaultMockResponseId())
                .forwardedEndpoint(request.getForwardedEndpoint())
                .httpMethod(request.getHttpMethod())
                .networkDelay(request.getNetworkDelay())
                .responseStrategy(request.getResponseStrategy())
                .simulateNetworkDelay(request.getSimulateNetworkDelay())
                .status(request.getStatus())
                .build());
        return ResponseEntity.ok(output.getRestMethod());
    }


    @ApiOperation(value = "Create Method", response = RestMethod.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created method")})
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestMethod> createMethod(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @RequestBody CreateRestMethodRequest request) {
       final CreateRestMethodOutput output = super.serviceProcessor.process(CreateRestMethodInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .name(request.getName())
                .httpMethod(request.getHttpMethod())
                .build());
       return ResponseEntity.ok(output.getCreatedRestMethod());
    }

    @ApiOperation(value = "Update mock response statuses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated SOAP operation statuses")})
    @RequestMapping(method = RequestMethod.PUT,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}/mockresponse/status")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateMockResponseStatuses(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @ApiParam(name = "methodId", value = "The id of the method")
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
