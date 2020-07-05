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

package com.castlemock.web.mock.soap.web.rest.controller;

import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.service.project.input.CreateSoapMockResponseInput;
import com.castlemock.core.mock.soap.service.project.input.DeleteSoapMockResponseInput;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapMockResponseInput;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapMockResponseInput;
import com.castlemock.core.mock.soap.service.project.output.CreateSoapMockResponseOutput;
import com.castlemock.core.mock.soap.service.project.output.DeleteSoapMockResponseOutput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapMockResponseOutput;
import com.castlemock.web.basis.web.rest.controller.AbstractRestController;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/rest/soap")
@Api(value="SOAP - Mocked response", description="REST Operations for Castle Mock SOAP mocked response",
        tags = {"SOAP - Mocked response"})
public class SoapMockResponseRestController extends AbstractRestController {

    @ApiOperation(value = "Get mocked response", response = SoapMockResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved mocked response")})
    @RequestMapping(method = RequestMethod.GET,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/response/{responseId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    SoapMockResponse getMockResponse(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @ApiParam(name = "responseId", value = "The id of the response")
            @PathVariable(value = "responseId") final String responseId) {
        final ReadSoapMockResponseOutput output = super.serviceProcessor.process(ReadSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(responseId)
                .build());
        return output.getMockResponse();
    }

    @ApiOperation(value = "Create mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created mocked response")})
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void createMockResponse(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @RequestBody SoapMockResponse response) {
        super.serviceProcessor.process(CreateSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponse(response)
                .build());
    }

    @ApiOperation(value = "Update mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated mocked response")})
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/response/{responseId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void updateMockResponse(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @ApiParam(name = "responseId", value = "The id of the response")
            @PathVariable(value = "responseId") final String responseId,
            @RequestBody SoapMockResponse response) {
        super.serviceProcessor.process(UpdateSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(responseId)
                .mockResponse(response)
                .build());
    }


    @ApiOperation(value = "Delete mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete mocked response")})
    @RequestMapping(method = RequestMethod.DELETE,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/response/{responseId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public void deleteMockResponse(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @ApiParam(name = "responseId", value = "The id of the response")
            @PathVariable(value = "responseId") final String responseId) {
        super.serviceProcessor.process(DeleteSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(responseId)
                .build());
    }

}
