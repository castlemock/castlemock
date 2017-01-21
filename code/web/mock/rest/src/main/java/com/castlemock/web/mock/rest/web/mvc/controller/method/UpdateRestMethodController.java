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
import com.castlemock.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.castlemock.core.mock.rest.model.project.dto.RestMethodDto;
import com.castlemock.core.mock.rest.model.project.service.message.input.ReadRestMethodInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.UpdateRestMethodInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.UpdateRestMethodsForwardedEndpointInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ReadRestMethodOutput;
import com.castlemock.web.mock.rest.web.mvc.command.method.UpdateRestMethodsEndpointCommand;
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
public class UpdateRestMethodController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/method/updateRestMethod";


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/{restMethodId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @PathVariable final String restResourceId, @PathVariable final String restMethodId) {
        final ReadRestMethodOutput output = serviceProcessor.process(new ReadRestMethodInput(restProjectId, restApplicationId, restResourceId, restMethodId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_METHOD, output.getRestMethod());
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION_ID, restApplicationId);
        model.addObject(REST_RESOURCE_ID, restResourceId);
        model.addObject(REST_METHOD_ID, restResourceId);
        model.addObject(REST_METHOD_TYPES, HttpMethod.values());
        model.addObject(REST_METHOD_STATUSES, getRestMethodStatuses());
        model.addObject(REST_RESPONSE_STRATEGIES, RestResponseStrategy.values());
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/{restMethodId}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @PathVariable final String restResourceId, @PathVariable final String restMethodId, @ModelAttribute final RestMethodDto restMethodDto) {
        serviceProcessor.process(new UpdateRestMethodInput(restProjectId, restApplicationId, restResourceId, restMethodId, restMethodDto));
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId + "/resource/" + restResourceId + "/method/" + restMethodId);
    }

    /**
     * The method provides the functionality to update the endpoint address for multiple
     * methods at once
     * @param restProjectId The id of the project that the method belongs to
     * @param restApplicationId The id of the application that the method belongs to
     * @param restResourceId The id of the resource that the method belongs to
     * @param updateRestMethodsEndpointCommand The command object contains both the methods that
     *                                          will be updated and the new forwarded address
     * @return Redirects the user to the resource page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/update/confirm", method = RequestMethod.POST)
    public ModelAndView updateEndpoint(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @PathVariable final String restResourceId, @ModelAttribute final UpdateRestMethodsEndpointCommand updateRestMethodsEndpointCommand) {
        Preconditions.checkNotNull(updateRestMethodsEndpointCommand, "The update application endpoint command cannot be null");
        serviceProcessor.process(new UpdateRestMethodsForwardedEndpointInput(restProjectId, restApplicationId, restResourceId, updateRestMethodsEndpointCommand.getRestMethods(), updateRestMethodsEndpointCommand.getForwardedEndpoint()));
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId + "/resource/" + restResourceId);
    }

}

