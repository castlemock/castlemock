/*
 * Copyright 2015 Karl Dahlgren
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

package com.fortmocks.war.base.web.mvc.controller.project;

import com.fortmocks.core.base.model.project.service.ProjectServiceFacade;
import com.fortmocks.war.base.web.mvc.controller.AbstractViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * The ExportProjectController controller provides functionality to
 * export a specific project.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web")
public class ExportProjectController extends AbstractViewController {

    @Autowired
    private ProjectServiceFacade projectServiceFacade;

    /**
     * Creates and return a view that provides the required functionality
     * to export a project.
     * @return A view that provides the required functionality to export a project
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "{projectType}/project/{projectId}/export", method = RequestMethod.GET)
    public ResponseEntity<String> defaultPage(@PathVariable final String projectType, @PathVariable final Long projectId) {
        final String exportedProject = projectServiceFacade.exportProject(projectType, projectId);

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.TEXT_XML);
        respHeaders.setContentDispositionFormData("attachment", "project-" + projectType + "-" + projectId + ".xml");

        return new ResponseEntity<String>(exportedProject, respHeaders, HttpStatus.OK);
    }

}