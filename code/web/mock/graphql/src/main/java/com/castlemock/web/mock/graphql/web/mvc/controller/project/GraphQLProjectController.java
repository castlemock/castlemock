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

package com.castlemock.web.mock.graphql.web.mvc.controller.project;

import com.castlemock.core.mock.graphql.model.project.service.message.input.ReadGraphQLProjectInput;
import com.castlemock.core.mock.graphql.model.project.service.message.output.ReadGraphQLProjectOutput;
import com.castlemock.web.mock.graphql.web.mvc.command.application.GraphQLApplicationModifierCommand;
import com.castlemock.web.mock.graphql.web.mvc.controller.AbstractGraphQLViewController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * The project controller provides functionality to retrieve a specific project
 * @author Karl Dahlgren
 * @since 1.19
 */
@Controller
@RequestMapping("/web/graphql/project")
public class GraphQLProjectController extends AbstractGraphQLViewController {

    private static final String PAGE = "mock/graphql/project/graphQLProject";
    private static final String GraphQL_QUERY_MODIFIER_COMMAND = "graphQLQueryModifierCommand";
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

    private static final Logger LOGGER = Logger.getLogger(GraphQLProjectController.class);
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
        model.addObject(GraphQL_QUERY_MODIFIER_COMMAND, new GraphQLApplicationModifierCommand());

        if (UPLOAD_OUTCOME_SUCCESS.equals(upload)) {
            LOGGER.debug("Upload successful");
            model.addObject(UPLOAD, UPLOAD_OUTCOME_SUCCESS);
        } else if(UPLOAD_OUTCOME_ERROR.equals(upload)){
            LOGGER.debug("Upload unsuccessful");
            model.addObject(UPLOAD, UPLOAD_OUTCOME_ERROR);
        }

        return model;
    }


}

