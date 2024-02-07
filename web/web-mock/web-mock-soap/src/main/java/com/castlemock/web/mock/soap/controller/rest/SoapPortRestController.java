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
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.service.mock.soap.project.input.DeleteSoapPortInput;
import com.castlemock.service.mock.soap.project.input.ReadSoapPortInput;
import com.castlemock.service.mock.soap.project.input.UpdateSoapOperationsForwardedEndpointInput;
import com.castlemock.service.mock.soap.project.input.UpdateSoapOperationsStatusInput;
import com.castlemock.service.mock.soap.project.input.UpdateSoapPortInput;
import com.castlemock.service.mock.soap.project.output.DeleteSoapPortOutput;
import com.castlemock.service.mock.soap.project.output.ReadSoapPortOutput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapPortOutput;
import com.castlemock.web.core.controller.rest.AbstractRestController;
import com.castlemock.web.mock.soap.model.UpdateSoapOperationForwardedEndpointsRequest;
import com.castlemock.web.mock.soap.model.UpdateSoapOperationStatusesRequest;
import com.castlemock.web.mock.soap.model.UpdateSoapPortRequest;
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
@Tag(name="SOAP - Port", description="REST Operations for Castle Mock SOAP Port")
public class SoapPortRestController extends AbstractRestController {

    @Autowired
    public SoapPortRestController(final ServiceProcessor serviceProcessor){
        super(serviceProcessor);
    }

    @Operation(summary =  "Get Port")
    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}/port/{portId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<SoapPort> getPort(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "portId", description = "The id of the port")
            @PathVariable(value = "portId") final String portId) {
        final ReadSoapPortOutput output = super.serviceProcessor.process(ReadSoapPortInput.builder()
                .projectId(projectId)
                .portId(portId)
                .build());
        return output.getPort()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary =  "Delete Port")
    @RequestMapping(method = RequestMethod.DELETE, value = "/project/{projectId}/port/{portId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<SoapPort> deletePort(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "portId", description = "The id of the port")
            @PathVariable(value = "portId") final String portId) {
        final DeleteSoapPortOutput output = super.serviceProcessor.process(DeleteSoapPortInput.builder()
                .projectId(projectId)
                .portId(portId)
                .build());
        return output.getPort()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());    }

    @Operation(summary =  "Update Port")
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/port/{portId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<SoapPort> updatePort(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "portId", description = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @RequestBody UpdateSoapPortRequest request){
        final UpdateSoapPortOutput output = super.serviceProcessor.process(UpdateSoapPortInput.builder()
                .projectId(projectId)
                .portId(portId)
                .uri(request.getUri())
                .build());
        return output.getPort()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary =  "Update operation statuses")
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/port/{portId}/operation/status")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateOperationStatuses(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "portId", description = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @RequestBody UpdateSoapOperationStatusesRequest request){
        request.getOperationIds()
                .forEach(operationId -> super.serviceProcessor.process(UpdateSoapOperationsStatusInput.builder()
                        .projectId(projectId)
                        .portId(portId)
                        .operationId(operationId)
                        .operationStatus(request.getStatus())
                        .build()));
        return ResponseEntity.ok().build();
    }

    @Operation(summary =  "Update Operation forwarded endpoints")
    @RequestMapping(method = RequestMethod.PUT, value = "/project/{projectId}/port/{portId}/operation/endpoint/forwarded")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> updateOperationForwardedEndpoints(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "portId", description = "The id of the port")
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
