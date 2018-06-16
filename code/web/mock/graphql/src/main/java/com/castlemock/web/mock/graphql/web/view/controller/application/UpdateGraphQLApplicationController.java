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
import com.castlemock.core.mock.graphql.model.project.service.message.input.UpdateGraphQLApplicationInput;
import com.castlemock.core.mock.graphql.model.project.service.message.output.ReadGraphQLApplicationOutput;
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
public class UpdateGraphQLApplicationController extends AbstractGraphQLViewController {

    private static final String PAGE = "mock/graphql/application/updateGraphQLApplication";


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String projectId, @PathVariable final String applicationId) {
        final ReadGraphQLApplicationOutput output = serviceProcessor.process(new ReadGraphQLApplicationInput(projectId, applicationId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(GRAPHQL_APPLICATION, output.getGraphQLApplication());
        model.addObject(GRAPHQL_PROJECT_ID, projectId);
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable final String projectId, @PathVariable final String applicationId,  @ModelAttribute final GraphQLApplication graphQLApplication) {
        serviceProcessor.process(new UpdateGraphQLApplicationInput(projectId, applicationId, graphQLApplication));
        return redirect("/graphql/project/" + projectId + "/application/" + applicationId);
    }
}

