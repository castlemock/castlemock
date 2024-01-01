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

package com.castlemock.web.core.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.project.OverviewProject;
import com.castlemock.model.core.service.project.ProjectServiceFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

/**
 * The {@link ProjectCoreRestController} is the REST controller that provides
 * the interface for the core operations.
 * @author Karl Dahlgren
 * @since 1.19
 */
@Controller
@RequestMapping("/api/rest/core")
@Tag(name="Core - Project", description="REST Operations for Castle Mock Core")
@ConditionalOnExpression("${server.mode.demo} == false")
public class ProjectCoreRestController extends AbstractRestController {
    private final ProjectServiceFacade projectServiceFacade;

    @Autowired
    public ProjectCoreRestController(final ServiceProcessor serviceProcessor,
                                     final ProjectServiceFacade projectServiceFacade){
        super(serviceProcessor);
        this.projectServiceFacade = Objects.requireNonNull(projectServiceFacade, "projectServiceFacade");
    }


    /**
     * The method retrieves all projects
     * @return The retrieved project.
     */
    @Operation(summary =  "Get projects",
            description = "Get projects. Required authorization: Reader, Modifier or Admin.")
    @RequestMapping(method = RequestMethod.GET, value = "/project")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<List<OverviewProject>> getProjects() {
        return ResponseEntity.ok(projectServiceFacade.findAll());
    }


}
