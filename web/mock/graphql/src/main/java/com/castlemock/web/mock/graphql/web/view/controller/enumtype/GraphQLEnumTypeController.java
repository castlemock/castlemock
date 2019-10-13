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

package com.castlemock.web.mock.graphql.web.view.controller.enumtype;

import com.castlemock.core.mock.graphql.service.project.input.ReadGraphQLEnumTypeInput;
import com.castlemock.core.mock.graphql.service.project.output.ReadGraphQLEnumTypeOutput;
import com.castlemock.web.mock.graphql.web.view.controller.AbstractGraphQLViewController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * The query controller provides functionality to retrieve a specific query
 * @author Karl Dahlgren
 * @since 1.19
 */
@Controller
@RequestMapping("/web/graphql/project")
public class GraphQLEnumTypeController extends AbstractGraphQLViewController {

    private static final String PAGE = "mock/graphql/enumtype/graphQLEnumType";
    private static final Logger LOGGER = Logger.getLogger(GraphQLEnumTypeController.class);
    /**
     * Retrieves a specific project with a project id
     * @param projectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/enum/{enumTypeId}", method = RequestMethod.GET)
    public ModelAndView getProject(@PathVariable final String projectId,
                                   @PathVariable final String applicationId,
                                   @PathVariable final String enumTypeId) {
        final ReadGraphQLEnumTypeOutput output =
                serviceProcessor.process(new ReadGraphQLEnumTypeInput(projectId, applicationId, enumTypeId));

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(GRAPHQL_ENUM_TYPE, output.getGraphQLEnumType());
        model.addObject(GRAPHQL_PROJECT_ID, projectId);
        model.addObject(GRAPHQL_APPLICATION_ID, applicationId);
        model.addObject(GRAPHQL_OPERATION_STATUSES, getGraphQLOperationStatuses());

        return model;
    }


}

