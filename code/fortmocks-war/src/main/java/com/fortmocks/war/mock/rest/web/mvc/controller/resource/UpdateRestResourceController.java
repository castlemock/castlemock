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

package com.fortmocks.war.mock.rest.web.mvc.controller.resource;

import com.fortmocks.core.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.war.mock.rest.model.project.service.RestProjectService;
import com.fortmocks.war.mock.rest.web.mvc.controller.AbstractRestViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
public class UpdateRestResourceController extends AbstractRestViewController {

    @Autowired
    private RestProjectService restProjectService;
    private static final String PAGE = "mock/rest/project/restProject";
    /**
     * Retrieves a specific project with a project id
     * @param projectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/resource/{resourceId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final Long projectId, @PathVariable final Long applicationId, @PathVariable final Long resourceId) {
        final RestProjectDto project = restProjectService.findOne(projectId);

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT, project);
        return model;
    }


}

