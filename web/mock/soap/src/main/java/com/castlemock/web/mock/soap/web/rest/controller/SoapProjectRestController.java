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

import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapProjectInput;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapPortsForwardedEndpointInput;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapPortsStatusInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapProjectOutput;
import com.castlemock.web.basis.web.rest.controller.AbstractRestController;
import com.castlemock.web.mock.soap.web.rest.controller.model.UpdateSoapPortForwardedEndpointsRequest;
import com.castlemock.web.mock.soap.web.rest.controller.model.UpdateSoapPortStatusesRequest;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/rest/soap")
@Api(value="SOAP - Project", description="REST Operations for Castle Mock SOAP Project", tags = {"SOAP - Project"})
public class SoapProjectRestController extends AbstractRestController {

    @ApiOperation(value = "Get Project", response = SoapProject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved SOAP project")})
    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<SoapProject> getProject(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId){
        final ReadSoapProjectOutput output = super.serviceProcessor.process(ReadSoapProjectInput.builder()
                .projectId(projectId)
                .build());
        return ResponseEntity.ok(output.getProject());
    }

    @ApiOperation(value = "Update Port statuses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated SOAP port statuses")})
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/port/status")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updatePortStatuses(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestBody UpdateSoapPortStatusesRequest request){
        request.getPortIds()
                .forEach(portId -> super.serviceProcessor.process(UpdateSoapPortsStatusInput.builder()
                        .projectId(projectId)
                        .portId(portId)
                        .operationStatus(request.getStatus())
                        .build()));
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Update Port forwarded endpoints")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated SOAP port forwarded endpoints")})
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/port/endpoint/forwarded")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updatePortForwardedEndpoints(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @RequestBody UpdateSoapPortForwardedEndpointsRequest request){
        super.serviceProcessor.process(UpdateSoapPortsForwardedEndpointInput.builder()
                .projectId(projectId)
                .portIds(request.getPortIds())
                .forwardedEndpoint(request.getForwardedEndpoint())
                .build());
        return ResponseEntity.ok().build();
    }

}
