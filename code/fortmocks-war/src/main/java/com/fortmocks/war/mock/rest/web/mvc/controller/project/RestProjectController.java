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

package com.fortmocks.war.mock.rest.web.mvc.controller.project;

import com.fortmocks.core.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.war.mock.rest.model.project.service.RestProjectService;
import com.fortmocks.war.mock.rest.web.mvc.command.application.DeleteRestApplicationsCommand;
import com.fortmocks.war.mock.rest.web.mvc.command.application.RestApplicationModifierCommand;
import com.fortmocks.war.mock.rest.web.mvc.controller.AbstractRestViewController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * The project controller provides functionality to retrieve a specific project
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/web/rest/project")
public class RestProjectController extends AbstractRestViewController {

    @Autowired
    private RestProjectService restProjectService;
    private static final String PAGE = "mock/rest/project/restProject";
    private static final String REST_APPLICATION_MODIFIER_COMMAND = "restApplicationModifierCommand";
    private static final String DELETE_REST_APPLICATIONS = "delete";
    private static final String DELETE_REST_APPLICATIONS_COMMAND = "deleteRestApplicationsCommand";
    private static final String DELETE_REST_APPLICATIONS_PAGE = "mock/rest/application/deleteRestApplications";

    private static final Logger LOGGER = Logger.getLogger(RestProjectController.class);
    /**
     * Retrieves a specific project with a project id
     * @param projectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ModelAndView getProject(@PathVariable final Long projectId) {
        final RestProjectDto project = restProjectService.findOne(projectId);

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT, project);
        model.addObject(REST_APPLICATION_MODIFIER_COMMAND, new RestApplicationModifierCommand());
        return model;
    }

    /**
     * The method projectFunctionality provides multiple functionalities:
     * @param projectId The id of the project that the resources belong to
     * @param action The name of the action that should be executed (delete or update).
     * @param restApplicationModifierCommand The command object that contains the list of resources that get affected by the executed action.
     * @return Redirects the user back to the main page for the project with the provided id.
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.POST)
    public ModelAndView projectFunctionality(@PathVariable final Long projectId, @RequestParam final String action, @ModelAttribute final RestApplicationModifierCommand restApplicationModifierCommand) {
        LOGGER.debug("Requested REST project action requested: " + action);
        if(DELETE_REST_APPLICATIONS.equalsIgnoreCase(action)) {
            final List<RestApplicationDto> restApplications = new ArrayList<RestApplicationDto>();
            for(Long restApplicationId : restApplicationModifierCommand.getRestApplicationIds()){
                final RestApplicationDto restApplicationDto = restProjectService.findRestApplication(projectId, restApplicationId);
                restApplications.add(restApplicationDto);
            }
            final ModelAndView model = createPartialModelAndView(DELETE_REST_APPLICATIONS_PAGE);
            model.addObject(REST_PROJECT_ID, projectId);
            model.addObject(REST_APPLICATIONS, restApplications);
            model.addObject(DELETE_REST_APPLICATIONS_COMMAND, new DeleteRestApplicationsCommand());
            return model;
        }
        return redirect("/rest/project/" + projectId);
    }
}

