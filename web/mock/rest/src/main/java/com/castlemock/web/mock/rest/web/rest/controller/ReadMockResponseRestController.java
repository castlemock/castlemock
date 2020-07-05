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

import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.service.project.input.CreateRestMockResponseInput;
import com.castlemock.core.mock.rest.service.project.input.DeleteRestMockResponseInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestMockResponseInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestMockResponseInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestMockResponseOutput;
import com.castlemock.web.basis.web.rest.controller.AbstractRestController;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/rest/rest")
@Api(value="REST - Mocked response", description="REST Operations for Castle Mock REST mocked response",
        tags = {"REST - Mocked response"})
public class ReadMockResponseRestController extends AbstractRestController {

    @ApiOperation(value = "Get mocked response", response = RestMockResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved mocked response")})
    @RequestMapping(method = RequestMethod.GET,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}/response/{responseId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    RestMockResponse getResponse(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @ApiParam(name = "methodId", value = "The id of the method")
            @PathVariable(value = "methodId") final String methodId,
            @ApiParam(name = "responseId", value = "The id of the response")
            @PathVariable(value = "responseId") final String responseId) {
        final ReadRestMockResponseOutput output = super.serviceProcessor.process(ReadRestMockResponseInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .restMethodId(methodId)
                .restMockResponse(responseId)
                .build());
        return output.getRestMockResponse();
    }

    @ApiOperation(value = "Delete mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted mocked response")})
    @RequestMapping(method = RequestMethod.DELETE,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}/response/{responseId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void deleteResponse(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @ApiParam(name = "methodId", value = "The id of the method")
            @PathVariable(value = "methodId") final String methodId,
            @ApiParam(name = "responseId", value = "The id of the response")
            @PathVariable(value = "responseId") final String responseId) {
        super.serviceProcessor.process(DeleteRestMockResponseInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .restMethodId(methodId)
                .restMockResponseId(responseId)
                .build());
    }

    @ApiOperation(value = "Update mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated mocked response")})
    @RequestMapping(method = RequestMethod.PUT,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}/response/{responseId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void updateResponse(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @ApiParam(name = "methodId", value = "The id of the method")
            @PathVariable(value = "methodId") final String methodId,
            @ApiParam(name = "responseId", value = "The id of the response")
            @PathVariable(value = "responseId") final String responseId,
            @RequestBody RestMockResponse mockResponse) {
        super.serviceProcessor.process(UpdateRestMockResponseInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .restMethodId(methodId)
                .restMockResponseId(responseId)
                .restMockResponse(mockResponse)
                .build());
    }

    @ApiOperation(value = "Create mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created mocked response")})
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void createResponse(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "applicationId", value = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @ApiParam(name = "methodId", value = "The id of the method")
            @PathVariable(value = "methodId") final String methodId,
            @RequestBody RestMockResponse mockResponse) {
        super.serviceProcessor.process(CreateRestMockResponseInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .mockResponse(mockResponse)
                .build());
    }

}
