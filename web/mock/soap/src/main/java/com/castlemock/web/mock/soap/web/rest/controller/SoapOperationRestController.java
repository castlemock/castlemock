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

import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapOperationInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapOperationOutput;
import com.castlemock.web.basis.web.rest.controller.AbstractRestController;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/rest/soap")
@Api(value="SOAP - Operation", description="REST Operations for Castle Mock SOAP Operation", tags = {"SOAP - Operation"})
public class SoapOperationRestController extends AbstractRestController {

    @ApiOperation(value = "Get Operation", response = SoapOperation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved operation")})
    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}/port/{portId}/operation/{operationId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<SoapOperation> getPort(
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

}
