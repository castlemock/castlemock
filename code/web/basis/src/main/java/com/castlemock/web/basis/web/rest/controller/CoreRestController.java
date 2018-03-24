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

package com.castlemock.web.basis.web.rest.controller;

import com.castlemock.core.basis.model.project.service.ProjectServiceFacade;
import com.castlemock.web.basis.manager.FileManager;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The {@link CoreRestController} is the REST controller that provides
 * the interface for the core operations.
 * @author Karl Dahlgren
 * @since 1.19
 */
@Controller
@RequestMapping("/rest/core")
@Api(value="Core", description="REST Operations for Castle Mock Core", tags = {"Core"})
public class CoreRestController extends AbstractRestController {

    @Autowired
    private FileManager fileManager;
    @Autowired
    private ProjectServiceFacade projectServiceFacade;

    private static final Logger LOGGER = Logger.getLogger(CoreRestController.class);


    /**
     * The REST operations imports a project.
     * @param type The type of the project.
     * @param multipartFile The project file which will be imported.
     * @param httpServletRequest The incoming HTTP servlet request.
     * @param httpServletResponse The outgoing HTTP servlet response.
     * @return A HTTP response.
     */
    @ApiOperation(value = "Import project",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully imported project")
    }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/project/import")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity importProject(
            @ApiParam(name = "type", value = "The type of the project", allowableValues = "REST,SOAP")
            @RequestParam("type") final String type,
            @ApiParam(name = "file", value = "The project file which will be imported.")
            @RequestParam("file") final MultipartFile multipartFile,
                                        final HttpServletRequest httpServletRequest,
                                        final HttpServletResponse httpServletResponse) {
        File file = null;
        try {
            file = fileManager.uploadFile(multipartFile);
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            final StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }

            projectServiceFacade.importProject(type, stringBuilder.toString());
        } catch (IOException e) {
            LOGGER.error("Unable to import project", e);
        } finally {
            fileManager.deleteUploadedFile(file);
        }

        return new ResponseEntity(HttpStatus.OK);
    }


    @ApiOperation(value = "Token",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully generated token")
    }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/token")
    public @ResponseBody ResponseEntity getToken(
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {

        return new ResponseEntity(HttpStatus.OK);
    }





}
