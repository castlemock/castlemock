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

package com.fortmocks.war.mock.rest.web.mvc.controller.application;

import com.fortmocks.core.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.war.mock.rest.web.mvc.command.application.DeleteRestApplicationsCommand;
import com.fortmocks.war.mock.rest.web.mvc.controller.AbstractRestViewController;
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
public class DeleteRestApplicationController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/application/deleteRestApplication";


    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/delete", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final Long restProjectId, @PathVariable final Long restApplicationId) {
        RestApplicationDto restApplicationDto = restProjectService.findRestApplication(restProjectId, restApplicationId);
        ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION, restApplicationDto);
        return model;
    }


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable final Long restProjectId, @PathVariable final Long restApplicationId) {
        restProjectService.deleteRestApplication(restProjectId, restApplicationId);
        return redirect("/rest/project/" + restProjectId);
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultpleProjects(@PathVariable final Long restProjectId, @ModelAttribute final DeleteRestApplicationsCommand deleteRestApplicationsCommand) {
        restProjectService.deleteRestApplications(restProjectId, deleteRestApplicationsCommand.getRestApplications());
        return redirect("/rest/project/" + restProjectId);
    }


}

