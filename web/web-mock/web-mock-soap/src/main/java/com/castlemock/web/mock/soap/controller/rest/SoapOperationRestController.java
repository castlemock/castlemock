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
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.service.mock.soap.project.input.ReadSoapOperationInput;
import com.castlemock.service.mock.soap.project.input.UpdateSoapMockResponseStatusInput;
import com.castlemock.service.mock.soap.project.input.UpdateSoapOperationInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapOperationOutput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapOperationOutput;
import com.castlemock.web.core.controller.rest.AbstractRestController;
import com.castlemock.web.mock.soap.model.UpdateSoapMockResponseStatusesRequest;
import com.castlemock.web.mock.soap.model.UpdateSoapOperationRequest;
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
@Tag(name="SOAP - Operation", description="REST Operations for Castle Mock SOAP Operation")
public class SoapOperationRestController extends AbstractRestController {

    @Autowired
    public SoapOperationRestController(final ServiceProcessor serviceProcessor){
        super(serviceProcessor);
    }

    @Operation(summary =  "Get Operation")
    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}/port/{portId}/operation/{operationId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<SoapOperation> getOperation(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "portId", description = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @Parameter(name = "operationId", description = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId) {
        final ReadSoapOperationOutput output = super.serviceProcessor.process(ReadSoapOperationInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .build());
        return output.getOperation()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary =  "Update Operation")
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/port/{portId}/operation/{operationId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<SoapOperation> updateOperation(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "portId", description = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @Parameter(name = "operationId", description = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @RequestBody final UpdateSoapOperationRequest request) {
        final UpdateSoapOperationOutput output = super.serviceProcessor.process(UpdateSoapOperationInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .status(request.getStatus())
                .responseStrategy(request.getResponseStrategy())
                .identifyStrategy(request.getIdentifyStrategy())
                .defaultMockResponseId(request.getDefaultMockResponseId()
                        .orElse(null))
                .forwardedEndpoint(request.getForwardedEndpoint()
                        .orElse(null))
                .mockOnFailure(request.getMockOnFailure()
                        .orElse(null))
                .networkDelay(request.getNetworkDelay()
                        .orElse(null))
                .simulateNetworkDelay(request.getSimulateNetworkDelay()
                        .orElse(null))
                .automaticForward(request.getAutomaticForward()
                        .orElse(null))
                .build());
        return output.getOperation()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary =  "Update mock response statuses")
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/port/{portId}/operation/{operationId}/mockresponse/status")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateMockResponseStatuses(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "portId", description = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @Parameter(name = "operationId", description = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @RequestBody UpdateSoapMockResponseStatusesRequest request){
        request.getMockResponseIds()
                .forEach(mockResponseId -> super.serviceProcessor.process(UpdateSoapMockResponseStatusInput.builder()
                        .projectId(projectId)
                        .portId(portId)
                        .operationId(operationId)
                        .mockResponseId(mockResponseId)
                        .mockResponseStatus(request.getStatus())
                        .build()));
        return ResponseEntity.ok().build();
    }

}
