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

package com.castlemock.web.mock.graphql.web.view.controller.application;

import com.castlemock.core.mock.graphql.model.project.domain.GraphQLApplication;
import com.castlemock.core.mock.graphql.service.project.input.CreateGraphQLApplicationInput;
import com.castlemock.core.mock.graphql.service.project.output.CreateGraphQLApplicationOutput;
import com.castlemock.web.mock.graphql.web.view.controller.AbstractGraphQLViewController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The project controller provides functionality to retrieve a specific project
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/web/graphql/project")
@ConditionalOnExpression("${server.mode.demo} == false")
public class CreateGraphQLApplicationController extends AbstractGraphQLViewController {

    private static final String PAGE = "mock/graphql/application/createGraphQLApplication";


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/create/application", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String projectId) {
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(GRAPHQL_PROJECT_ID, projectId);
        model.addObject(GRAPHQL_APPLICATION, new GraphQLApplication());
        return model;
    }


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/create/application", method = RequestMethod.POST)
    public ModelAndView createProject(@PathVariable final String projectId, @ModelAttribute final GraphQLApplication application) {
        final CreateGraphQLApplicationOutput output = serviceProcessor.process(new CreateGraphQLApplicationInput(projectId, application));
        return redirect("/graphql/project/" + projectId + "/application/" + output.getSavedGraphQLApplication().getId());
    }

}

