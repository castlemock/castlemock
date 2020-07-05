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

import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.service.project.input.CreateRestApplicationInput;
import com.castlemock.core.mock.rest.service.project.input.DeleteRestApplicationInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestApplicationInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestApplicationInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestApplicationOutput;
import com.castlemock.web.basis.web.rest.controller.AbstractRestController;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/rest/rest")
@Api(value="REST - Application", description="REST Operations for Castle Mock REST Application",
        tags = {"REST - Application"})
public class RestApplicationRestController extends AbstractRestController {

    @ApiOperation(value = "Get Application", response = RestApplication.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved REST Application")})
    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}/application/{applicationId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody RestApplication getApplication(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId) {
        final ReadRestApplicationOutput output = super.serviceProcessor.process(ReadRestApplicationInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .build());
        return output.getRestApplication();
    }

    @ApiOperation(value = "Delete Application")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted REST Application")})
    @RequestMapping(method = RequestMethod.DELETE, value = "/project/{projectId}/application/{applicationId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void deleteApplication(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId) {
        super.serviceProcessor.process(DeleteRestApplicationInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .build());
    }

    @ApiOperation(value = "Update Application")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated REST Application")})
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/application/{applicationId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void updateApplication(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @RequestBody RestApplication application) {
        super.serviceProcessor.process(UpdateRestApplicationInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restApplication(application)
                .build());
    }

    @ApiOperation(value = "Create Application")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created REST Application")})
    @RequestMapping(method = RequestMethod.POST, value = "/project/{projectId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void createApplication(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestBody RestApplication application) {
        super.serviceProcessor.process(CreateRestApplicationInput.builder()
                .projectId(projectId)
                .application(application)
                .build());
    }

}
