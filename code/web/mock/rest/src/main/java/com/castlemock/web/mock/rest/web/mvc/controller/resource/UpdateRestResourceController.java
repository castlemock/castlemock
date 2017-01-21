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

package com.castlemock.web.mock.rest.web.mvc.controller.resource;

import com.castlemock.core.mock.rest.model.project.dto.RestResourceDto;
import com.castlemock.core.mock.rest.model.project.service.message.input.ReadRestResourceInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.UpdateRestResourceInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.UpdateRestResourcesForwardedEndpointInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ReadRestResourceOutput;
import com.castlemock.web.mock.rest.web.mvc.command.resource.UpdateRestResourcesEndpointCommand;
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
public class UpdateRestResourceController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/resource/updateRestResource";

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @PathVariable final String restResourceId) {
        final ReadRestResourceOutput output = serviceProcessor.process(new ReadRestResourceInput(restProjectId, restApplicationId, restResourceId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_RESOURCE, output.getRestResource());
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION_ID, restApplicationId);
        model.addObject(REST_RESOURCE_ID, restResourceId);
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @PathVariable final String restResourceId,  @ModelAttribute final RestResourceDto restResourceDto) {
        serviceProcessor.process(new UpdateRestResourceInput(restProjectId, restApplicationId, restResourceId, restResourceDto));
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId + "/resource/" + restResourceId);
    }

    /**
     * The method provides the functionality to update the endpoint address for multiple
     * resources at once
     * @param restProjectId The id of the project that the resources belongs to
     * @param restApplicationId The id of the application that the resources belongs to
     * @param updateRestResourcesEndpointCommand The command object contains both the resources that
     *                                          will be updated and the new forwarded address
     * @return Redirects the user to the application page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/update/confirm", method = RequestMethod.POST)
    public ModelAndView updateEndpoint(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @ModelAttribute final UpdateRestResourcesEndpointCommand updateRestResourcesEndpointCommand) {
        Preconditions.checkNotNull(updateRestResourcesEndpointCommand, "The update application endpoint command cannot be null");
        serviceProcessor.process(new UpdateRestResourcesForwardedEndpointInput(restProjectId, restApplicationId, updateRestResourcesEndpointCommand.getRestResources(), updateRestResourcesEndpointCommand.getForwardedEndpoint()));
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId);
    }

}

