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

import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.service.project.input.CreateRestMethodInput;
import com.castlemock.core.mock.rest.service.project.input.DeleteRestMethodInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestMethodInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestMethodOutput;
import com.castlemock.web.basis.web.rest.controller.AbstractRestController;
import io.swagger.annotations.*;
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
    RestMethod getMethod(
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
        return output.getRestMethod();
    }

    @ApiOperation(value = "Delete Method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted method")})
    @RequestMapping(method = RequestMethod.DELETE,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void deleteMethod(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @ApiParam(name = "methodId", value = "The id of the method")
            @PathVariable(value = "methodId") final String methodId) {
        super.serviceProcessor.process(DeleteRestMethodInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .restMethodId(methodId)
                .build());
    }

    @ApiOperation(value = "Update Method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated method")})
    @RequestMapping(method = RequestMethod.PUT,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void updateMethod(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @ApiParam(name = "methodId", value = "The id of the method")
            @PathVariable(value = "methodId") final String methodId,
            @RequestBody RestMethod restMethod) {
        super.serviceProcessor.process(UpdateRestMethodInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .restMethodId(methodId)
                .restMethod(restMethod)
                .build());
    }


    @ApiOperation(value = "Create Method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created method")})
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void createMethod(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @RequestBody RestMethod restMethod) {
       super.serviceProcessor.process(CreateRestMethodInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .method(restMethod)
                .build());
    }

}
