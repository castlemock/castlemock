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

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapOperationInput;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapMockResponseStatusInput;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapOperationInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapOperationOutput;
import com.castlemock.core.mock.soap.service.project.output.UpdateSoapOperationOutput;
import com.castlemock.web.basis.controller.rest.AbstractRestController;
import com.castlemock.web.mock.soap.model.UpdateSoapMockResponseStatusesRequest;
import com.castlemock.web.mock.soap.model.UpdateSoapOperationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value="SOAP - Operation", description="REST Operations for Castle Mock SOAP Operation", tags = {"SOAP - Operation"})
public class SoapOperationRestController extends AbstractRestController {

    @Autowired
    public SoapOperationRestController(final ServiceProcessor serviceProcessor){
        super(serviceProcessor);
    }

    @ApiOperation(value = "Get Operation", response = SoapOperation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved operation")})
    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}/port/{portId}/operation/{operationId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<SoapOperation> getOperation(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId) {
        final ReadSoapOperationOutput output = super.serviceProcessor.process(ReadSoapOperationInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .build());
        return ResponseEntity.ok(output.getOperation());
    }

    @ApiOperation(value = "Update Operation", response = SoapOperation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved operation")})
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/port/{portId}/operation/{operationId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<SoapOperation> updateOperation(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @RequestBody final UpdateSoapOperationRequest request) {
        final UpdateSoapOperationOutput output = super.serviceProcessor.process(UpdateSoapOperationInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .defaultMockResponseId(request.getDefaultMockResponseId())
                .forwardedEndpoint(request.getForwardedEndpoint())
                .identifyStrategy(request.getIdentifyStrategy())
                .mockOnFailure(request.getMockOnFailure())
                .networkDelay(request.getNetworkDelay())
                .responseStrategy(request.getResponseStrategy())
                .simulateNetworkDelay(request.getSimulateNetworkDelay())
                .status(request.getStatus())
                .build());
        return ResponseEntity.ok(output.getOperation());
    }

    @ApiOperation(value = "Update mock response statuses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated SOAP operation statuses")})
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/port/{portId}/operation/{operationId}/mockresponse/status")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateMockResponseStatuses(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
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
