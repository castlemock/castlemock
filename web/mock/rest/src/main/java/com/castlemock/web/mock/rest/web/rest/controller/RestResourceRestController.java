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

package com.castlemock.web.mock.rest.web.rest.controller;

import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.service.project.input.CreateRestResourceInput;
import com.castlemock.core.mock.rest.service.project.input.DeleteRestResourceInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestResourceInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestResourceInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestResourceOutput;
import com.castlemock.web.basis.web.rest.controller.AbstractRestController;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/rest/rest")
@Api(value="REST - Resource", description="REST Operations for Castle Mock REST Resource", tags = {"REST - Resource"})
public class RestResourceRestController extends AbstractRestController {

    @ApiOperation(value = "Get Resource", response = RestResource.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved REST resource")})
    @RequestMapping(method = RequestMethod.GET,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody RestResource getResource(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId) {
        final ReadRestResourceOutput output = super.serviceProcessor.process(ReadRestResourceInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .build());
        return output.getRestResource();
    }

    @ApiOperation(value = "Delete Resource")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted REST resource")})
    @RequestMapping(method = RequestMethod.DELETE,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void deleteResource(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId) {
        super.serviceProcessor.process(DeleteRestResourceInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .build());
    }

    @ApiOperation(value = "Update Resource")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved REST resource")})
    @RequestMapping(method = RequestMethod.PUT,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void updateResource(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @RequestBody RestResource resource) {
        super.serviceProcessor.process(UpdateRestResourceInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .restResource(resource)
                .build());
    }

    @ApiOperation(value = "Create Resource")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved REST resource")})
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/application/{applicationId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void createResource(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @RequestBody RestResource resource) {
        super.serviceProcessor.process(CreateRestResourceInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResource(resource)
                .build());
    }

}
