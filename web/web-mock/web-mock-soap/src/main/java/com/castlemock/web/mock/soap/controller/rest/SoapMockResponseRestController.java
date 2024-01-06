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

package com.castlemock.web.mock.soap.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.service.mock.soap.project.input.CreateSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.input.DeleteSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.input.DuplicateSoapMockResponsesInput;
import com.castlemock.service.mock.soap.project.input.ReadSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.input.UpdateSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.output.CreateSoapMockResponseOutput;
import com.castlemock.service.mock.soap.project.output.DeleteSoapMockResponseOutput;
import com.castlemock.service.mock.soap.project.output.ReadSoapMockResponseOutput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapMockResponseOutput;
import com.castlemock.web.core.controller.rest.AbstractRestController;
import com.castlemock.web.mock.soap.model.CreateSoapMockResponseRequest;
import com.castlemock.web.mock.soap.model.DuplicateSoapMockOperationsRequest;
import com.castlemock.web.mock.soap.model.UpdateSoapMockResponseRequest;
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
@RequestMapping("api/rest/soap")
@Tag(name="SOAP - Mocked response", description="REST Operations for Castle Mock SOAP mocked response")
public class SoapMockResponseRestController extends AbstractRestController {

    @Autowired
    public SoapMockResponseRestController(final ServiceProcessor serviceProcessor){
        super(serviceProcessor);
    }

    @Operation(summary =  "Get mocked response")
    @RequestMapping(method = RequestMethod.GET,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/mockresponse/{responseId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<SoapMockResponse> getMockResponse(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "portId", description = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @Parameter(name = "operationId", description = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @Parameter(name = "responseId", description = "The id of the response")
            @PathVariable(value = "responseId") final String responseId) {
        final ReadSoapMockResponseOutput output = super.serviceProcessor.process(ReadSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(responseId)
                .build());
        return ResponseEntity.ok(output.getMockResponse());
    }

    @Operation(summary =  "Create mocked response")
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/mockresponse")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<SoapMockResponse> createMockResponse(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "portId", description = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @Parameter(name = "operationId", description = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @RequestBody final CreateSoapMockResponseRequest request) {
        final CreateSoapMockResponseOutput output = super.serviceProcessor.process(CreateSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .body(request.getBody())
                .httpHeaders(request.getHttpHeaders())
                .httpStatusCode(request.getHttpStatusCode())
                .name(request.getName())
                .status(request.getStatus())
                .usingExpressions(request.getUsingExpressions())
                .xpathExpressions(request.getXpathExpressions())
                .build());
        return ResponseEntity.ok(output.getMockResponse());
    }

    @Operation(summary =  "Update mocked response")
    @RequestMapping(method = RequestMethod.PUT,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/mockresponse/{responseId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<SoapMockResponse> updateMockResponse(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "portId", description = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @Parameter(name = "operationId", description = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @Parameter(name = "responseId", description = "The id of the response")
            @PathVariable(value = "responseId") final String responseId,
            @RequestBody final UpdateSoapMockResponseRequest request) {
        final UpdateSoapMockResponseOutput output = super.serviceProcessor.process(UpdateSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(responseId)
                .body(request.getBody())
                .httpHeaders(request.getHttpHeaders())
                .httpStatusCode(request.getHttpStatusCode())
                .name(request.getName())
                .status(request.getStatus())
                .usingExpressions(request.getUsingExpressions().orElse(null))
                .xpathExpressions(request.getXpathExpressions())
                .build());
        return ResponseEntity.ok(output.getMockResponse());
    }

    @Operation(summary =  "Delete mocked response")
    @RequestMapping(method = RequestMethod.DELETE,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/mockresponse/{responseId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<SoapMockResponse> deleteMockResponse(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "portId", description = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @Parameter(name = "operationId", description = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @Parameter(name = "responseId", description = "The id of the response")
            @PathVariable(value = "responseId") final String responseId) {
        final DeleteSoapMockResponseOutput output = super.serviceProcessor.process(DeleteSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(responseId)
                .build());
        return ResponseEntity.ok(output.getMockResponse());
    }

    @Operation(summary =  "Duplicate mocked response")
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/mockresponse/duplicate")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<SoapMockResponse> duplicateMockResponse(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "portId", description = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @Parameter(name = "operationId", description = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @RequestBody DuplicateSoapMockOperationsRequest request) {
        super.serviceProcessor.process(DuplicateSoapMockResponsesInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseIds(request.getMockResponseIds())
                .build());
        return ResponseEntity.ok().build();
    }

}
