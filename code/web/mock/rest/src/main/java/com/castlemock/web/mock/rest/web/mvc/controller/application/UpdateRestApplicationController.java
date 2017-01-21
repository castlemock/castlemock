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

package com.castlemock.web.mock.rest.web.mvc.controller.application;

import com.castlemock.core.mock.rest.model.project.dto.RestApplicationDto;
import com.castlemock.core.mock.rest.model.project.service.message.input.ReadRestApplicationInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.UpdateRestApplicationInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.UpdateRestApplicationsForwardedEndpointInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ReadRestApplicationOutput;
import com.castlemock.web.mock.rest.web.mvc.command.application.UpdateRestApplicationsEndpointCommand;
import com.castlemock.web.mock.rest.web.mvc.controller.AbstractRestViewController;
import com.google.common.base.Preconditions;
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
public class UpdateRestApplicationController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/application/updateRestApplication";


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String projectId, @PathVariable final String applicationId) {
        final ReadRestApplicationOutput output = serviceProcessor.process(new ReadRestApplicationInput(projectId, applicationId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_APPLICATION, output.getRestApplication());
        model.addObject(REST_PROJECT_ID, projectId);
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable final String projectId, @PathVariable final String applicationId,  @ModelAttribute final RestApplicationDto restApplicationDto) {
        serviceProcessor.process(new UpdateRestApplicationInput(projectId, applicationId, restApplicationDto));
        return redirect("/rest/project/" + projectId + "/application/" + applicationId);
    }

    /**
     * The method provides the functionality to update the endpoint address for multiple
     * applications at once
     * @param projectId The id of the project that the ports belongs to
     * @param updateRestApplicationsEndpointCommand The command object contains both the application that
     *                                          will be updated and the new forwarded address
     * @return Redirects the user to the project page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/update/confirm", method = RequestMethod.POST)
    public ModelAndView updateEndpoint(@PathVariable final String projectId, @ModelAttribute final UpdateRestApplicationsEndpointCommand updateRestApplicationsEndpointCommand) {
        Preconditions.checkNotNull(updateRestApplicationsEndpointCommand, "The update application endpoint command cannot be null");
        serviceProcessor.process(new UpdateRestApplicationsForwardedEndpointInput(projectId, updateRestApplicationsEndpointCommand.getRestApplications(), updateRestApplicationsEndpointCommand.getForwardedEndpoint()));
        return redirect("/rest/project/" + projectId);
    }
}

