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

import com.castlemock.core.mock.graphql.service.project.input.DeleteGraphQLApplicationInput;
import com.castlemock.core.mock.graphql.service.project.input.DeleteGraphQLApplicationsInput;
import com.castlemock.core.mock.graphql.service.project.input.ReadGraphQLApplicationInput;
import com.castlemock.core.mock.graphql.service.project.output.ReadGraphQLApplicationOutput;
import com.castlemock.web.mock.graphql.web.view.command.application.DeleteGraphQLApplicationsCommand;
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
 * @author Karl Dahlgren
 * @since 1.19
 */
@Controller
@RequestMapping("/web/graphql/project")
@ConditionalOnExpression("${server.mode.demo} == false")
public class DeleteGraphQLApplicationController extends AbstractGraphQLViewController {

    private static final String PAGE = "mock/graphql/application/deleteGraphQLApplication";


    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{graphQLProjectId}/application/{graphQLApplicationId}/delete", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String graphQLProjectId, @PathVariable final String graphQLApplicationId) {
        final ReadGraphQLApplicationOutput ouput = serviceProcessor.process(new ReadGraphQLApplicationInput(graphQLProjectId, graphQLApplicationId));
        ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(GRAPHQL_PROJECT_ID, graphQLProjectId);
        model.addObject(GRAPHQL_APPLICATION, ouput.getGraphQLApplication());
        return model;
    }


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{graphQLProjectId}/application/{graphQLApplicationId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable final String graphQLProjectId, @PathVariable final String graphQLApplicationId) {
        serviceProcessor.process(new DeleteGraphQLApplicationInput(graphQLProjectId, graphQLApplicationId));
        return redirect("/graphql/project/" + graphQLProjectId);
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{graphQLProjectId}/application/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultpleProjects(@PathVariable final String graphQLProjectId, @ModelAttribute final DeleteGraphQLApplicationsCommand deleteRestApplicationsCommand) {
        serviceProcessor.process(new DeleteGraphQLApplicationsInput(graphQLProjectId, deleteRestApplicationsCommand.getGraphQLApplications()));
        return redirect("/graphql/project/" + graphQLProjectId);
    }


}

