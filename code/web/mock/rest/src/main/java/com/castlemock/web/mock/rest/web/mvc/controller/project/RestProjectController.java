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

package com.castlemock.web.mock.rest.web.mvc.controller.project;

import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.dto.RestApplicationDto;
import com.castlemock.core.mock.rest.model.project.service.message.input.ReadRestApplicationInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.ReadRestProjectInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.UpdateRestApplicationsStatusInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ReadRestApplicationOutput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ReadRestProjectOutput;
import com.castlemock.web.mock.rest.web.mvc.command.application.DeleteRestApplicationsCommand;
import com.castlemock.web.mock.rest.web.mvc.command.application.RestApplicationModifierCommand;
import com.castlemock.web.mock.rest.web.mvc.command.application.UpdateRestApplicationsEndpointCommand;
import com.castlemock.web.mock.rest.web.mvc.controller.AbstractRestViewController;
import org.apache.log4j.Logger;
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

    private static final String PAGE = "mock/rest/project/restProject";
    private static final String REST_APPLICATION_MODIFIER_COMMAND = "restApplicationModifierCommand";
    private static final String DELETE_REST_APPLICATIONS = "delete";
    private static final String DELETE_REST_APPLICATIONS_COMMAND = "deleteRestApplicationsCommand";
    private static final String DELETE_REST_APPLICATIONS_PAGE = "mock/rest/application/deleteRestApplications";
    private static final String UPDATE_STATUS = "update";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String UPDATE_REST_APPLICATIONS_ENDPOINT_PAGE = "mock/rest/application/updateRestApplicationsEndpoint";
    private static final String UPDATE_REST_APPLICATIONS_ENDPOINT_COMMAND = "updateRestApplicationsEndpointCommand";

    private static final Logger LOGGER = Logger.getLogger(RestProjectController.class);
    /**
     * Retrieves a specific project with a project id
     * @param projectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ModelAndView getProject(@PathVariable final String projectId) {
        final ReadRestProjectOutput output =  serviceProcessor.process(new ReadRestProjectInput(projectId));

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT, output.getRestProject());
        model.addObject(REST_METHOD_STATUSES, getRestMethodStatuses());
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
    public ModelAndView projectFunctionality(@PathVariable final String projectId, @RequestParam final String action, @ModelAttribute final RestApplicationModifierCommand restApplicationModifierCommand) {
        LOGGER.debug("Requested REST project action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final RestMethodStatus restMethodStatus = RestMethodStatus.valueOf(restApplicationModifierCommand.getRestMethodStatus());
            for(String restApplicationId : restApplicationModifierCommand.getRestApplicationIds()){
                serviceProcessor.process(new UpdateRestApplicationsStatusInput(projectId, restApplicationId, restMethodStatus));
            }
        } else if(DELETE_REST_APPLICATIONS.equalsIgnoreCase(action)) {
            final List<RestApplicationDto> restApplications = new ArrayList<RestApplicationDto>();
            for(String restApplicationId : restApplicationModifierCommand.getRestApplicationIds()){
                final ReadRestApplicationOutput output = serviceProcessor.process(new ReadRestApplicationInput(projectId, restApplicationId));
                restApplications.add(output.getRestApplication());
            }
            final ModelAndView model = createPartialModelAndView(DELETE_REST_APPLICATIONS_PAGE);
            model.addObject(REST_PROJECT_ID, projectId);
            model.addObject(REST_APPLICATIONS, restApplications);
            model.addObject(DELETE_REST_APPLICATIONS_COMMAND, new DeleteRestApplicationsCommand());
            return model;
        } else if(UPDATE_ENDPOINTS.equalsIgnoreCase(action)){
            final List<RestApplicationDto> restApplicationDtos = new ArrayList<RestApplicationDto>();
            for(String restApplicationId : restApplicationModifierCommand.getRestApplicationIds()){
                final ReadRestApplicationOutput output = serviceProcessor.process(new ReadRestApplicationInput(projectId, restApplicationId));
                restApplicationDtos.add(output.getRestApplication());
            }
            final ModelAndView model = createPartialModelAndView(UPDATE_REST_APPLICATIONS_ENDPOINT_PAGE);
            model.addObject(REST_PROJECT_ID, projectId);
            model.addObject(REST_APPLICATIONS, restApplicationDtos);
            model.addObject(UPDATE_REST_APPLICATIONS_ENDPOINT_COMMAND, new UpdateRestApplicationsEndpointCommand());
            return model;
        }
        return redirect("/rest/project/" + projectId);
    }
}

