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
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponseStatus;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.service.project.input.DeleteSoapMockResponseInput;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapMockResponseInput;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapOperationInput;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapMockResponseInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapMockResponseOutput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapOperationOutput;
import com.castlemock.core.mock.soap.service.project.output.UpdateSoapMockResponseOutput;
import com.castlemock.web.basis.web.rest.controller.AbstractRestController;
import io.swagger.annotations.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/rest/soap")
@Api(value="SOAP - Mocked response", description="REST Operations for Castle Mock SOAP mocked response",
        tags = {"SOAP - Mocked response"})
public class SoapMockResponseRestController extends AbstractRestController {

    @ApiOperation(value = "Delete Mocked response", response = SoapMockResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted mocked response")})
    @RequestMapping(method = RequestMethod.DELETE,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/response/{responseId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    void deletePort(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @ApiParam(name = "responseId", value = "The id of the response")
            @PathVariable(value = "responseId") final String responseId
    ) {
        DeleteSoapMockResponseInput input = DeleteSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(responseId)
                .build();
        super.serviceProcessor.process(input);
    }

    @ApiOperation(value = "Get Mocked response", response = SoapMockResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved mocked response")})
    @RequestMapping(method = RequestMethod.GET,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/response/{responseId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    SoapMockResponse getPort(
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

    @ApiOperation(value = "Get Mocked response status", response = SoapMockResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved mocked response status")})
    @RequestMapping(method = RequestMethod.GET,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/response/{responseId}/status")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    SoapMockResponseStatus getPortStatus(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @ApiParam(name = "responseId", value = "The id of the response")
            @PathVariable(value = "responseId") final String responseId
    ) {
        final ReadSoapMockResponseOutput output = super.serviceProcessor.process(ReadSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(responseId)
                .build());
        return output.getMockResponse().getStatus();
    }

    @ApiOperation(value = "Get all Mocked responses", response = SoapMockResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all mocked responses")})
    @RequestMapping(method = RequestMethod.GET,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/response")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    List<SoapMockResponse> getPorts(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId
    ) {
        final List<String> mockResponsesIds = new ArrayList<>();

        // Retrieve existing operation
        ReadSoapOperationOutput readOutput = super.serviceProcessor.process(ReadSoapOperationInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .build());
        SoapOperation soapOperation = readOutput.getOperation();

        return soapOperation.getMockResponses();
    }

    @ApiOperation(value = "Get Mocked responses Ids", response = SoapMockResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved mocked responses Ids")})
    @RequestMapping(method = RequestMethod.GET,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/response/ids")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    List<String> getPortsIds(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @ApiParam(name = "status", value = "(optional) The status of the mocked services to filter returned results")
            @RequestParam(value = "status") final Optional<SoapMockResponseStatus> responseStatus
    ) {
        final List<String> mockResponsesIds = new ArrayList<>();
        // Retrieve existing operation
        ReadSoapOperationOutput readOutput = super.serviceProcessor.process(ReadSoapOperationInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .build());
        SoapOperation soapOperation = readOutput.getOperation();

        // Filter by operation's mocked responses
        List<SoapMockResponse> mockResponses = soapOperation.getMockResponses();

        // Find mocked responses Ids of the operation
        for (int idx = 0; idx < mockResponses.size(); idx++) {
            SoapMockResponse mockResponse = mockResponses.get(idx);
            
            if(responseStatus.isEmpty() || mockResponse.getStatus() == responseStatus.get()) {
                mockResponsesIds.add(mockResponse.getId());
            }
        }

        return mockResponsesIds;
    }

    @ApiOperation(value = "Update Mocked response status", response = SoapMockResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated mocked response status")})
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/response/{responseId}/status/{status}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    SoapMockResponse updatePort(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @ApiParam(name = "responseId", value = "The id of the response")
            @PathVariable(value = "responseId") final String responseId,
            @ApiParam(name = "status", value = "The status of the response")
            @PathVariable("status") final SoapMockResponseStatus responseStatus
    ) {
        // Retrieve existing record
        final ReadSoapMockResponseOutput output = super.serviceProcessor.process(ReadSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(responseId)
                .build());
        final SoapMockResponse mockResponse = output.getMockResponse();

        // Change retrieved record
        mockResponse.setStatus(responseStatus);

        // Update changed record
        final UpdateSoapMockResponseInput updateInput = UpdateSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(responseId)
                .mockResponse(mockResponse)
                .build();
        UpdateSoapMockResponseOutput updateOutput = super.serviceProcessor.process(updateInput);

        return updateOutput.getMockResponse();
    }

}
