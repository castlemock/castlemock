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

package com.castlemock.web.mock.graphql.web.view.controller.application;

import com.castlemock.core.mock.graphql.model.project.domain.GraphQLApplication;
import com.castlemock.core.mock.graphql.model.project.service.message.input.ReadGraphQLApplicationInput;
import com.castlemock.core.mock.graphql.model.project.service.message.output.ReadGraphQLApplicationOutput;
import com.castlemock.web.mock.graphql.web.view.command.project.GraphQLMutationModifierCommand;
import com.castlemock.web.mock.graphql.web.view.command.project.GraphQLQueryModifierCommand;
import com.castlemock.web.mock.graphql.web.view.command.project.GraphQLSubscriptionModifierCommand;
import com.castlemock.web.mock.graphql.web.view.controller.AbstractGraphQLViewController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;


/**
 * The application controller provides functionality to retrieve a specific project
 * @author Karl Dahlgren
 * @since 1.19
 */
@Controller
@RequestMapping("/web/graphql/project")
public class GraphQLApplicationController extends AbstractGraphQLViewController {

    private static final String PAGE = "mock/graphql/application/graphQLApplication";
    private static final String GraphQL_QUERY_MODIFIER_COMMAND = "graphQLQueryModifierCommand";
    private static final String GraphQL_MUTATION_MODIFIER_COMMAND = "graphQLMutationModifierCommand";
    private static final String GraphQL_SUBSCRIPTION_MODIFIER_COMMAND = "graphQLSubscriptionModifierCommand";
    private static final String UPLOAD = "upload";
    private static final String UPLOAD_OUTCOME_SUCCESS = "success";
    private static final String UPLOAD_OUTCOME_ERROR = "error";

    private static final Logger LOGGER = Logger.getLogger(GraphQLApplicationController.class);
    /**
     * Retrieves a specific project with a project id
     * @param projectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}", method = RequestMethod.GET)
    public ModelAndView getApplication(@PathVariable final String projectId,
                                       @PathVariable final String applicationId,
                                        @RequestParam(value = UPLOAD, required = false) final String upload,
                                       final ServletRequest request) {
        final ReadGraphQLApplicationOutput output =  serviceProcessor.process(new ReadGraphQLApplicationInput(projectId, applicationId));
        final GraphQLApplication application = output.getGraphQLApplication();

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(GRAPHQL_PROJECT_ID, projectId);
        model.addObject(GRAPHQL_APPLICATION, application);
        model.addObject(GRAPHQL_OPERATION_STATUSES, getGraphQLOperationStatuses());
        model.addObject(GraphQL_QUERY_MODIFIER_COMMAND, new GraphQLQueryModifierCommand());
        model.addObject(GraphQL_MUTATION_MODIFIER_COMMAND, new GraphQLMutationModifierCommand());
        model.addObject(GraphQL_SUBSCRIPTION_MODIFIER_COMMAND, new GraphQLSubscriptionModifierCommand());


        final String protocol = getProtocol(request);
        final int port = request.getServerPort();
        final String invokeAddress = getGraphQLInvokeAddress(protocol, port, projectId, applicationId);

        application.setInvokeAddress(invokeAddress);

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

