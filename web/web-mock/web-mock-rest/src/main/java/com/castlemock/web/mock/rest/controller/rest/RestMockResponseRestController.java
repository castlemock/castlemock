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
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.service.mock.rest.project.input.CreateRestMockResponseInput;
import com.castlemock.service.mock.rest.project.input.DeleteRestMockResponseInput;
import com.castlemock.service.mock.rest.project.input.DuplicateRestMockResponsesInput;
import com.castlemock.service.mock.rest.project.input.ReadRestMockResponseInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestMockResponseInput;
import com.castlemock.service.mock.rest.project.output.CreateRestMockResponseOutput;
import com.castlemock.service.mock.rest.project.output.DeleteRestMockResponseOutput;
import com.castlemock.service.mock.rest.project.output.ReadRestMockResponseOutput;
import com.castlemock.service.mock.rest.project.output.UpdateRestMockResponseOutput;
import com.castlemock.web.core.controller.rest.AbstractRestController;
import com.castlemock.web.mock.rest.model.CreateRestMockResponseRequest;
import com.castlemock.web.mock.rest.model.DuplicateRestMockResponsesRequest;
import com.castlemock.web.mock.rest.model.UpdateRestMockResponseRequest;
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
@Tag(name="REST - Mocked response", description="REST Operations for Castle Mock REST mocked response")
public class RestMockResponseRestController extends AbstractRestController {

    @Autowired
    public RestMockResponseRestController(final ServiceProcessor serviceProcessor){
        super(serviceProcessor);
    }

    @Operation(summary =  "Get mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved mocked response")})
    @RequestMapping(method = RequestMethod.GET,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}/mockresponse/{responseId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<RestMockResponse> getResponse(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @Parameter(name = "methodId", description = "The id of the method")
            @PathVariable(value = "methodId") final String methodId,
            @Parameter(name = "responseId", description = "The id of the response")
            @PathVariable(value = "responseId") final String responseId) {
        final ReadRestMockResponseOutput output = super.serviceProcessor.process(ReadRestMockResponseInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .mockResponseId(responseId)
                .build());
        return output.getMockResponse()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());    }

    @Operation(summary =  "Delete mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted mocked response")})
    @RequestMapping(method = RequestMethod.DELETE,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}/mockresponse/{responseId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestMockResponse> deleteResponse(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @Parameter(name = "methodId", description = "The id of the method")
            @PathVariable(value = "methodId") final String methodId,
            @Parameter(name = "responseId", description = "The id of the response")
            @PathVariable(value = "responseId") final String responseId) {
        final DeleteRestMockResponseOutput output = super.serviceProcessor.process(DeleteRestMockResponseInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .mockResponseId(responseId)
                .build());
        return output.getMockResponse()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());    }

    @Operation(summary =  "Update mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated mocked response")})
    @RequestMapping(method = RequestMethod.PUT,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}/mockresponse/{responseId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestMockResponse> updateResponse(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @Parameter(name = "methodId", description = "The id of the method")
            @PathVariable(value = "methodId") final String methodId,
            @Parameter(name = "responseId", description = "The id of the response")
            @PathVariable(value = "responseId") final String responseId,
            @RequestBody UpdateRestMockResponseRequest request) {
        final UpdateRestMockResponseOutput output = super.serviceProcessor.process(UpdateRestMockResponseInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .mockResponseId(responseId)
                .body(request.getBody())
                .contentEncodings(request.getContentEncodings())
                .headerQueries(request.getHeaderQueries())
                .httpHeaders(request.getHttpHeaders())
                .httpStatusCode(request.getHttpStatusCode())
                .jsonPathExpressions(request.getJsonPathExpressions())
                .name(request.getName())
                .parameterQueries(request.getParameterQueries())
                .status(request.getStatus())
                .usingExpressions(request.getUsingExpressions()
                        .orElse(null))
                .xpathExpressions(request.getXpathExpressions())
                .build());
        return output.getMockResponse()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());    }

    @Operation(summary =  "Create mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created mocked response")})
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}/mockresponse")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<RestMockResponse> createResponse(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @Parameter(name = "methodId", description = "The id of the method")
            @PathVariable(value = "methodId") final String methodId,
            @RequestBody CreateRestMockResponseRequest request) {
        final CreateRestMockResponseOutput output = super.serviceProcessor.process(CreateRestMockResponseInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .body(request.getBody().orElse(null))
                .contentEncodings(request.getContentEncodings())
                .headerQueries(request.getHeaderQueries())
                .httpHeaders(request.getHttpHeaders())
                .httpStatusCode(request.getHttpStatusCode())
                .jsonPathExpressions(request.getJsonPathExpressions())
                .name(request.getName())
                .parameterQueries(request.getParameterQueries())
                .status(request.getStatus())
                .usingExpressions(request.getUsingExpressions()
                        .orElse(null))
                .xpathExpressions(request.getXpathExpressions())
                .build());
        return ResponseEntity.ok(output.getMockResponse());
    }

    @Operation(summary =  "Duplicate mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully duplicated mocked responses")})
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/application/{applicationId}/resource/{resourceId}/method/{methodId}/mockresponse/duplicate")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<Void> duplicateResponse(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "applicationId", description = "The id of the application")
            @PathVariable(value = "applicationId") final String applicationId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @Parameter(name = "methodId", description = "The id of the method")
            @PathVariable(value = "methodId") final String methodId,
            @RequestBody DuplicateRestMockResponsesRequest request) {
        super.serviceProcessor.process(DuplicateRestMockResponsesInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .mockResponseIds(request.getMockResponseIds())
                .build());
        return ResponseEntity.ok().build();
    }

}
