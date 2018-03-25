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

import com.castlemock.core.mock.soap.model.project.domain.SoapResourceType;
import com.castlemock.core.mock.soap.model.project.dto.SoapResourceDto;
import com.castlemock.core.mock.soap.model.project.service.message.input.ImportSoapResourceInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.ImportSoapResourceOutput;
import com.castlemock.web.basis.manager.FileManager;
import com.castlemock.web.basis.web.rest.controller.AbstractRestController;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Controller
@RequestMapping("api/rest/soap")
@Api(value="SOAP", description="REST Operations for Castle Mock SOAP", tags = {"SOAP"})
public class SoapRestController extends AbstractRestController {

    @Autowired
    private FileManager fileManager;

    private static final Logger LOGGER = Logger.getLogger(SoapRestController.class);

    @ApiOperation(value = "Import resource", notes = "The service will upload a SOAP resource. " +
            "Either the project id or the resource id is required.", response = SoapResourceDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully imported SOAP project resource")
    }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/resource")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    SoapResourceDto importResource(
            @ApiParam(name = "projectId", value = "The id of the project")
            @RequestParam(value = "projectId", required = false) final String projectId,
            @ApiParam(name = "resourceId", value = "The id of the resource")
            @RequestParam(value = "resourceId", required = false) final String resourceId,
            @ApiParam(name = "resourceType", value = "The resource type", allowableValues = "WSDL,SCHEMA")
            @RequestParam("resourceType") final SoapResourceType resourceType,
            @ApiParam(name = "file", value = "The project file which will be imported.")
            @RequestParam("file") final MultipartFile file,
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        File uploadedFile = null;
        try {
            uploadedFile = fileManager.uploadFile(file);
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(uploadedFile));
            final StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }

            final String raw = stringBuilder.toString();
            final SoapResourceDto resource = new SoapResourceDto();
            resource.setId(resourceId);
            resource.setType(resourceType);
            ImportSoapResourceInput input = new ImportSoapResourceInput(projectId, resource, raw);
            ImportSoapResourceOutput output = this.serviceProcessor.process(input);
            return output.getResource();
        } catch (IOException e) {
            LOGGER.error("Unable to import resource", e);
            throw new RuntimeException(e);
        } finally {
            if(uploadedFile != null){
                fileManager.deleteUploadedFile(uploadedFile);
            }
        }
    }

}
