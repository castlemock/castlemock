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

package com.castlemock.web.mock.graphql.web.view.controller.project;

import com.castlemock.core.mock.graphql.model.project.domain.GraphQLApplication;
import com.castlemock.core.mock.graphql.service.project.input.ReadGraphQLApplicationInput;
import com.castlemock.core.mock.graphql.service.project.input.ReadGraphQLProjectInput;
import com.castlemock.core.mock.graphql.service.project.output.ReadGraphQLApplicationOutput;
import com.castlemock.core.mock.graphql.service.project.output.ReadGraphQLProjectOutput;
import com.castlemock.web.mock.graphql.web.view.command.application.DeleteGraphQLApplicationsCommand;
import com.castlemock.web.mock.graphql.web.view.command.project.GraphQLApplicationModifierCommand;
import com.castlemock.web.mock.graphql.web.view.controller.AbstractGraphQLViewController;
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
 * @since 1.19
 */
@Controller
@RequestMapping("/web/graphql/project")
public class GraphQLProjectController extends AbstractGraphQLViewController {

    private static final String PAGE = "mock/graphql/project/graphQLProject";
    private static final String GRAPHQL_QUERY_MODIFIER_COMMAND = "graphQLQueryModifierCommand";
    private static final String GRAPHQL_APPLICATION_MODIFIER_COMMAND = "graphQLApplicationModifierCommand";
    private static final String GRAPHQL_MUTATION_MODIFIER_COMMAND = "graphQLMutationModifierCommand";
    private static final String GRAPHQL_SUBSCRIPTION_MODIFIER_COMMAND = "graphQLSubscriptionModifierCommand";
    private static final String DELETE_GRAPHQL_APPLICATIONS = "delete";
    private static final String DELETE_GRAPHQLL_APPLICATIONS_COMMAND = "deleteGraphQLApplicationsCommand";
    private static final String DELETE_GRAPHQLL_APPLICATIONS_PAGE = "mock/graphql/application/deleteGraphQLApplications";
    private static final String UPDATE_STATUS = "update";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String UPDATE_GRAPHQL_APPLICATIONS_ENDPOINT_PAGE = "mock/graphql/application/updateGraphQLApplicationsEndpoint";
    private static final String UPDATE_GRAPHQL_APPLICATIONS_ENDPOINT_COMMAND = "updateGraphQLApplicationsEndpointCommand";
    private static final String UPLOAD = "upload";
    private static final String UPLOAD_OUTCOME_SUCCESS = "success";
    private static final String UPLOAD_OUTCOME_ERROR = "error";

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphQLProjectController.class);
    /**
     * Retrieves a specific project with a project id
     * @param projectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ModelAndView getProject(@PathVariable final String projectId, @RequestParam(value = UPLOAD, required = false) final String upload) {
        final ReadGraphQLProjectOutput output =  serviceProcessor.process(new ReadGraphQLProjectInput(projectId));

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(GRAPHQL_PROJECT, output.getGraphQLProject());
        model.addObject(GRAPHQL_OPERATION_STATUSES, getGraphQLOperationStatuses());
        model.addObject(GRAPHQL_APPLICATION_MODIFIER_COMMAND, new GraphQLApplicationModifierCommand());

        if (UPLOAD_OUTCOME_SUCCESS.equals(upload)) {
            LOGGER.debug("Upload successful");
            model.addObject(UPLOAD, UPLOAD_OUTCOME_SUCCESS);
        } else if(UPLOAD_OUTCOME_ERROR.equals(upload)){
            LOGGER.debug("Upload unsuccessful");
            model.addObject(UPLOAD, UPLOAD_OUTCOME_ERROR);
        }

        return model;
    }


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.POST)
    public ModelAndView applicationFunctionality(@PathVariable final String projectId,
                                                 @RequestParam final String action,
                                                 @ModelAttribute final GraphQLApplicationModifierCommand command) {
        if (DELETE_GRAPHQL_APPLICATIONS.equalsIgnoreCase(action)) {
            final List<GraphQLApplication> applications = new ArrayList<GraphQLApplication>();
            for (String applicationId : command.getGraphQLApplicationIds()) {
                ReadGraphQLApplicationOutput output =
                        serviceProcessor.process(new ReadGraphQLApplicationInput(projectId, applicationId));
                applications.add(output.getGraphQLApplication());
            }
            final ModelAndView model = createPartialModelAndView(DELETE_GRAPHQLL_APPLICATIONS_PAGE);
            model.addObject(GRAPHQL_PROJECT_ID, projectId);
            model.addObject(GRAPHQL_APPLICATIONS, applications);
            model.addObject(DELETE_GRAPHQLL_APPLICATIONS_COMMAND, new DeleteGraphQLApplicationsCommand());
            return model;
        }

        return redirect("/graphql/project/" + projectId);
    }

}

