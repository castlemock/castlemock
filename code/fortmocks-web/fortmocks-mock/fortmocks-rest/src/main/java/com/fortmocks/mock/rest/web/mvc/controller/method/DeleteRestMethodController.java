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

package com.fortmocks.mock.rest.web.mvc.controller.method;

import com.fortmocks.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.DeleteRestMethodInput;
import com.fortmocks.mock.rest.model.project.processor.message.input.DeleteRestMethodsInput;
import com.fortmocks.mock.rest.model.project.processor.message.input.ReadRestMethodInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.DeleteRestMethodOutput;
import com.fortmocks.mock.rest.model.project.processor.message.output.ReadRestMethodOutput;
import com.fortmocks.mock.rest.web.mvc.command.method.DeleteRestMethodsCommand;
import com.fortmocks.mock.rest.web.mvc.controller.AbstractRestViewController;
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
public class DeleteRestMethodController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/method/deleteRestMethod";


    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/{restMethodId}/delete", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final Long restProjectId, @PathVariable final Long restApplicationId, @PathVariable final Long restResourceId, @PathVariable final Long restMethodId) {
        final ReadRestMethodOutput output = processorMainframe.process(new ReadRestMethodInput(restProjectId, restApplicationId, restResourceId, restMethodId));

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION_ID, restApplicationId);
        model.addObject(REST_RESOURCE_ID, restResourceId);
        model.addObject(REST_METHOD, output.getRestMethod());
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/{restMethodId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable final Long restProjectId, @PathVariable final Long restApplicationId, @PathVariable final Long restResourceId, @PathVariable final Long restMethodId) {
        processorMainframe.process(new DeleteRestMethodInput(restProjectId, restApplicationId, restResourceId, restMethodId));
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId + "/resource/" + restResourceId);
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultpleProjects(@PathVariable final Long restProjectId, @PathVariable final Long restApplicationId, @PathVariable final Long restResourceId, @ModelAttribute final DeleteRestMethodsCommand deleteRestMethodsCommand) {
        processorMainframe.process(new DeleteRestMethodsInput(restProjectId, restApplicationId, restResourceId, deleteRestMethodsCommand.getRestMethods()));
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId + "/resource/" + restResourceId);
    }

}

