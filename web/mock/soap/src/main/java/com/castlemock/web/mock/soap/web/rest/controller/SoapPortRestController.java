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

import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapPortInput;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapOperationsForwardedEndpointInput;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapOperationsStatusInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapPortOutput;
import com.castlemock.web.basis.web.rest.controller.AbstractRestController;
import com.castlemock.web.mock.soap.web.rest.controller.model.UpdateSoapOperationForwardedEndpointsRequest;
import com.castlemock.web.mock.soap.web.rest.controller.model.UpdateSoapOpertionStatusesRequest;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/rest/soap")
@Api(value="SOAP - Port", description="REST Operations for Castle Mock SOAP Port", tags = {"SOAP - Port"})
public class SoapPortRestController extends AbstractRestController {

    @ApiOperation(value = "Get Port", response = SoapPort.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved SOAP port")})
    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}/port/{portId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<SoapPort> getPort(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId) {
        final ReadSoapPortOutput output = super.serviceProcessor.process(ReadSoapPortInput.builder()
                .projectId(projectId)
                .portId(portId)
                .build());
        return ResponseEntity.ok(output.getPort());
    }

    @ApiOperation(value = "Update operation statuses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated SOAP operation statuses")})
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/port/{portId}/operation/status")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateOperationStatuses(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @RequestBody UpdateSoapOpertionStatusesRequest request){
        request.getOperationIds()
                .forEach(operationId -> super.serviceProcessor.process(UpdateSoapOperationsStatusInput.builder()
                        .projectId(projectId)
                        .portId(portId)
                        .operationId(operationId)
                        .operationStatus(request.getStatus())
                        .build()));
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Update Operation forwarded endpoints")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated SOAP Operation forwarded endpoints")})
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/port/{portId}/operation/endpoint/forwarded")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateOperationForwardedEndpoints(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @RequestBody UpdateSoapOperationForwardedEndpointsRequest request){
        super.serviceProcessor.process(UpdateSoapOperationsForwardedEndpointInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationIds(request.getOperationIds())
                .forwardedEndpoint(request.getForwardedEndpoint())
                .build());
        return ResponseEntity.ok().build();
    }

}
