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

package com.castlemock.web.mock.rest.web.view.controller.application;

import com.castlemock.core.mock.rest.service.project.input.DeleteRestApplicationInput;
import com.castlemock.core.mock.rest.service.project.input.DeleteRestApplicationsInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestApplicationInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestApplicationOutput;
import com.castlemock.web.mock.rest.web.view.command.application.DeleteRestApplicationsCommand;
import com.castlemock.web.mock.rest.web.view.controller.AbstractRestViewController;
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
public class DeleteRestApplicationController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/application/deleteRestApplication";


    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/delete", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String restProjectId,
                                    @PathVariable final String restApplicationId) {
        final ReadRestApplicationOutput ouput = serviceProcessor.process(ReadRestApplicationInput.builder()
                .restProjectId(restProjectId)
                .restApplicationId(restApplicationId)
                .build());
        ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION, ouput.getRestApplication());
        return model;
    }


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable final String restProjectId,
                                @PathVariable final String restApplicationId) {
        serviceProcessor.process(DeleteRestApplicationInput.builder()
                .restProjectId(restProjectId)
                .restApplicationId(restApplicationId)
                .build());
        return redirect("/rest/project/" + restProjectId);
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultpleApplications(@PathVariable final String restProjectId,
                                                           @ModelAttribute(name="command") final DeleteRestApplicationsCommand command) {
        serviceProcessor.process(DeleteRestApplicationsInput.builder()
                .restProjectId(restProjectId)
                .restApplications(command.getRestApplications())
                .build());
        return redirect("/rest/project/" + restProjectId);
    }


}

