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

package com.castlemock.web.mock.rest.web.mvc.controller.method;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.rest.model.project.service.message.input.CreateRestMethodInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.CreateRestMethodOutput;
import com.castlemock.web.mock.rest.web.mvc.command.method.CreateRestMethodCommand;
import com.castlemock.web.mock.rest.web.mvc.controller.AbstractRestViewController;
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
@RequestMapping("/web/rest/project")
@ConditionalOnExpression("${server.mode.demo} == false")
public class CreateRestMethodController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/method/createRestMethod";

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/resource/{resourceId}/create/method", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String projectId, @PathVariable final String applicationId, @PathVariable final String resourceId) {
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, projectId);
        model.addObject(REST_APPLICATION_ID, applicationId);
        model.addObject(REST_RESOURCE_ID, resourceId);
        model.addObject(REST_METHOD, new CreateRestMethodCommand());
        model.addObject(REST_METHOD_TYPES, HttpMethod.values());
        return model;
    }


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/resource/{resourceId}/create/method", method = RequestMethod.POST)
    public ModelAndView createMethod(@PathVariable final String projectId, @PathVariable final String applicationId, @PathVariable final String resourceId, @ModelAttribute final CreateRestMethodCommand createRestMethodCommand) {
        final CreateRestMethodOutput output = serviceProcessor.process(new CreateRestMethodInput(projectId, applicationId, resourceId, createRestMethodCommand.getRestMethod()));
        return redirect("/rest/project/" + projectId + "/application/" + applicationId + "/resource/" + resourceId + "/method/" + output.getCreatedRestMethod().getId());
    }


}

