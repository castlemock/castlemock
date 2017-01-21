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

import com.castlemock.core.mock.rest.model.project.service.message.input.DeleteRestResourceInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.DeleteRestResourcesInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.ReadRestResourceInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ReadRestResourceOutput;
import com.castlemock.web.mock.rest.web.mvc.command.resource.DeleteRestResourcesCommand;
import com.castlemock.web.mock.rest.web.mvc.controller.AbstractRestViewController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**e
 * The Delete REST resource controller provides functionality to delete a specific REST resource.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/web/rest/project")
@ConditionalOnExpression("${server.mode.demo} == false")
public class DeleteRestResourceController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/resource/deleteRestResource";
    /**
     * Retrieves a specific REST resource with a project id, application id and a resource id
     * @param restProjectId The id of the project that the REST resource belongs to
     * @param restApplicationId The id of the application that the REST resource belongs to
     * @param restResourceId The id of the REST resource that should be retrieve
     * @return REST resource that matches the provided resource id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/delete", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @PathVariable final String restResourceId) {
        final ReadRestResourceOutput output = serviceProcessor.process(new ReadRestResourceInput(restProjectId, restApplicationId, restResourceId));

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION_ID, restApplicationId);
        model.addObject(REST_RESOURCE, output.getRestResource());
        return model;
    }

    /**
     * The method provides the functionality to delete a REST resource
     * @param restProjectId The id of the project that the REST resource belongs to
     * @param restApplicationId The id of the application that the REST resource belongs to
     * @param restResourceId The id of the REST resource that should be deleted
     * @return Redirects the user to the REST application page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @PathVariable final String restResourceId) {
        serviceProcessor.process(new DeleteRestResourceInput(restProjectId, restApplicationId, restResourceId));
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId);
    }

    /**
     * The method provides the functionality to delete a list REST resources
     * @param restProjectId The id of the project that the REST resources belongs to
     * @param restApplicationId The id of the application that the REST resources belongs to
     * @param deleteRestResourcesCommand The command object contains the REST resources that should be deleted
     * @return Redirects the user to the REST application page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultpleProjects(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @ModelAttribute final DeleteRestResourcesCommand deleteRestResourcesCommand) {
        serviceProcessor.process(new DeleteRestResourcesInput(restProjectId, restApplicationId, deleteRestResourcesCommand.getRestResources()));
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId);
    }



}

