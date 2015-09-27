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

package com.fortmocks.war.mock.rest.web.mvc.controller.method;

import com.fortmocks.core.mock.rest.model.project.RestMethodType;
import com.fortmocks.core.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.war.mock.rest.model.project.service.RestProjectService;
import com.fortmocks.war.mock.rest.web.mvc.command.application.CreateRestApplicationCommand;
import com.fortmocks.war.mock.rest.web.mvc.command.method.CreateRestMethodCommand;
import com.fortmocks.war.mock.rest.web.mvc.command.resource.CreateRestResourceCommand;
import com.fortmocks.war.mock.rest.web.mvc.controller.AbstractRestViewController;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CreateRestMethodController extends AbstractRestViewController {

    @Autowired
    private RestProjectService restProjectService;
    private static final String PAGE = "mock/rest/method/createRestMethod";
    private static final String REST_METHOD_TYPES = "methodTypes";

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/resource/{resourceId}/create/method", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final Long projectId, @PathVariable final Long applicationId, @PathVariable final Long resourceId) {
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, projectId);
        model.addObject(REST_APPLICATION_ID, applicationId);
        model.addObject(REST_RESOURCE_ID, resourceId);
        model.addObject(COMMAND, new CreateRestMethodCommand());
        model.addObject(REST_METHOD_TYPES, RestMethodType.values());
        return model;
    }


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/resource/{resourceId}/create/method", method = RequestMethod.POST)
    public ModelAndView createResource(@PathVariable final Long projectId, @PathVariable final Long applicationId, @PathVariable final Long resourceId, @ModelAttribute final CreateRestMethodCommand createRestMethodCommand) {
        final RestMethodDto restMethodDto = restProjectService.saveRestMethod(projectId, applicationId, resourceId, createRestMethodCommand.getRestMethod());
        return redirect("/rest/project/" + projectId + "/application/" + applicationId + "/resource/" + resourceId + "/method/" + restMethodDto.getId());
    }


}

