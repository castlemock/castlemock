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
import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.model.mock.soap.domain.SoapResourceType;
import com.castlemock.service.core.manager.FileManager;
import com.castlemock.service.mock.soap.project.input.ImportSoapResourceInput;
import com.castlemock.service.mock.soap.project.input.LoadSoapResourceInput;
import com.castlemock.service.mock.soap.project.input.ReadSoapResourceInput;
import com.castlemock.service.mock.soap.project.output.ImportSoapResourceOutput;
import com.castlemock.service.mock.soap.project.output.LoadSoapResourceOutput;
import com.castlemock.service.mock.soap.project.output.ReadSoapResourceOutput;
import com.castlemock.web.core.controller.rest.AbstractRestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("api/rest/soap")
@Tag(name="SOAP - Resource", description="REST Operations for Castle Mock SOAP")
@ConditionalOnExpression("${server.mode.demo} == false")
public class SoapResourceRestController extends AbstractRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapResourceRestController.class);

    private final FileManager fileManager;

    @Autowired
    public SoapResourceRestController(final ServiceProcessor serviceProcessor,
                                     final FileManager fileManager){
        super(serviceProcessor);
        this.fileManager = Objects.requireNonNull(fileManager, "fileManager");
    }

    @Operation(summary =  "Get SOAP resource")
    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}/resource/{resourceId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<SoapResource> getResource(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId) {
        final ReadSoapResourceOutput output = this.serviceProcessor.process(ReadSoapResourceInput.builder()
                .projectId(projectId)
                .resourceId(resourceId)
                .build());
        final SoapResource soapResource = output.getResource();
        final LoadSoapResourceOutput loadOutput =
                this.serviceProcessor.process(LoadSoapResourceInput.builder()
                        .projectId(projectId)
                        .resourceId(soapResource.getId())
                        .build());
        return ResponseEntity.ok(soapResource.toBuilder()
                .content(loadOutput.getResource())
                .build());
    }

    @Operation(summary =  "Get SOAP resource content")
    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}/resource/{resourceId}/content")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<String> getResourceContent(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId) {
        final LoadSoapResourceOutput output = this.serviceProcessor.process(LoadSoapResourceInput.builder()
                        .projectId(projectId)
                        .resourceId(resourceId)
                        .build());
        return ResponseEntity.ok(output.getResource());
    }

    @Operation(summary =  "Import resource", description = "The service will upload a SOAP resource. " +
            "Either the project id or the resource id is required. Required authorization: Modifier or Admin.")
    @RequestMapping(method = RequestMethod.POST, value = "/project/{projectId}/resource/{resourceId}/import")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<SoapResource> importResource(
            @Parameter(name = "projectId", description = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @Parameter(name = "resourceId", description = "The id of the resource")
            @PathVariable(value = "resourceId") final String resourceId,
            @Parameter(name = "resourceType", description = "The resource type", example = "WSDL,SCHEMA")
            @RequestParam("resourceType") final SoapResourceType resourceType,
            @Parameter(name = "file", description = "The project file which will be imported.")
            @RequestParam("file") final MultipartFile file) {
        File uploadedFile = null;
        try {
            uploadedFile = fileManager.uploadFile(file);
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(uploadedFile));
            final StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line).append("\n");
            }

            final String raw = stringBuilder.toString();
            final SoapResource resource = SoapResource.builder()
                    .id(resourceId)
                    .type(resourceType)
                    .build();

            final ImportSoapResourceInput input = ImportSoapResourceInput.builder()
                    .projectId(projectId)
                    .resource(resource)
                    .raw(raw)
                    .build();
            final ImportSoapResourceOutput output = this.serviceProcessor.process(input);
            return ResponseEntity.ok(output.getResource());
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
