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

package com.castlemock.web.mock.rest.web.view.controller.project;

import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.service.project.input.ReadRestApplicationInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestProjectInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestApplicationsStatusInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestApplicationOutput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestProjectOutput;
import com.castlemock.web.mock.rest.web.view.command.application.DeleteRestApplicationsCommand;
import com.castlemock.web.mock.rest.web.view.command.application.RestApplicationModifierCommand;
import com.castlemock.web.mock.rest.web.view.command.application.UpdateRestApplicationsEndpointCommand;
import com.castlemock.web.mock.rest.web.view.controller.AbstractRestViewController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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
    private static final String REST_APPLICATION_MODIFIER_COMMAND = "command";
    private static final String DELETE_REST_APPLICATIONS = "delete";
    private static final String DELETE_REST_APPLICATIONS_COMMAND = "command";
    private static final String DELETE_REST_APPLICATIONS_PAGE = "mock/rest/application/deleteRestApplications";
    private static final String UPDATE_STATUS = "update";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String UPDATE_REST_APPLICATIONS_ENDPOINT_PAGE = "mock/rest/application/updateRestApplicationsEndpoint";
    private static final String UPDATE_REST_APPLICATIONS_ENDPOINT_COMMAND = "command";
    private static final String UPLOAD = "upload";
    private static final String UPLOAD_OUTCOME_SUCCESS = "success";
    private static final String UPLOAD_OUTCOME_ERROR = "error";

    private static final Logger LOGGER = LoggerFactory.getLogger(RestProjectController.class);
    /**
     * Retrieves a specific project with a project id
     * @param projectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ModelAndView getProject(@PathVariable final String projectId, @RequestParam(value = UPLOAD, required = false) final String upload) {
        final ReadRestProjectOutput output =  serviceProcessor.process(ReadRestProjectInput.builder()
                .restProjectId(projectId)
                .build());

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT, output.getRestProject());
        model.addObject(REST_METHOD_STATUSES, getRestMethodStatuses());
        model.addObject(REST_APPLICATION_MODIFIER_COMMAND, new RestApplicationModifierCommand());

        if (UPLOAD_OUTCOME_SUCCESS.equals(upload)) {
            LOGGER.debug("Upload successful");
            model.addObject(UPLOAD, UPLOAD_OUTCOME_SUCCESS);
        } else if(UPLOAD_OUTCOME_ERROR.equals(upload)){
            LOGGER.debug("Upload unsuccessful");
            model.addObject(UPLOAD, UPLOAD_OUTCOME_ERROR);
        }

        return model;
    }

    /**
     * The method projectFunctionality provides multiple functionalities:
     * @param projectId The id of the project that the resources belong to
     * @param action The name of the action that should be executed (delete or update).
     * @param command The command object that contains the list of resources that get affected by the executed action.
     * @return Redirects the user back to the main page for the project with the provided id.
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.POST)
    public ModelAndView projectFunctionality(@PathVariable final String projectId,
                                             @RequestParam final String action,
                                             @ModelAttribute(name="command") final RestApplicationModifierCommand command) {
        LOGGER.debug("Requested REST project action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final RestMethodStatus restMethodStatus = RestMethodStatus.valueOf(command.getRestMethodStatus());
            for(String restApplicationId : command.getRestApplicationIds()){
                serviceProcessor.process(UpdateRestApplicationsStatusInput.builder()
                        .restProjectId(projectId)
                        .restApplicationId(restApplicationId)
                        .restMethodStatus(restMethodStatus)
                        .build());
            }
        } else if(DELETE_REST_APPLICATIONS.equalsIgnoreCase(action)) {
            final List<RestApplication> restApplications = new ArrayList<RestApplication>();
            for(String restApplicationId : command.getRestApplicationIds()){
                final ReadRestApplicationOutput output = serviceProcessor.process(ReadRestApplicationInput.builder()
                        .restProjectId(projectId)
                        .restApplicationId(restApplicationId)
                        .build());
                restApplications.add(output.getRestApplication());
            }
            final ModelAndView model = createPartialModelAndView(DELETE_REST_APPLICATIONS_PAGE);
            model.addObject(REST_PROJECT_ID, projectId);
            model.addObject(REST_APPLICATIONS, restApplications);
            model.addObject(DELETE_REST_APPLICATIONS_COMMAND, new DeleteRestApplicationsCommand());
            return model;
        } else if(UPDATE_ENDPOINTS.equalsIgnoreCase(action)){
            final List<RestApplication> restApplications = new ArrayList<RestApplication>();
            for(String restApplicationId : command.getRestApplicationIds()){
                final ReadRestApplicationOutput output = serviceProcessor.process(ReadRestApplicationInput.builder()
                        .restProjectId(projectId)
                        .restApplicationId(restApplicationId)
                        .build());
                restApplications.add(output.getRestApplication());
            }
            final ModelAndView model = createPartialModelAndView(UPDATE_REST_APPLICATIONS_ENDPOINT_PAGE);
            model.addObject(REST_PROJECT_ID, projectId);
            model.addObject(REST_APPLICATIONS, restApplications);
            model.addObject(UPDATE_REST_APPLICATIONS_ENDPOINT_COMMAND, new UpdateRestApplicationsEndpointCommand());
            return model;
        }
        return redirect("/rest/project/" + projectId);
    }
}

