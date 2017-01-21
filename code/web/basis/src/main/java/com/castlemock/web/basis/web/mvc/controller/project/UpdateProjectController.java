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

package com.castlemock.web.basis.web.mvc.controller.project;

import com.castlemock.core.basis.model.project.dto.ProjectDto;
import com.castlemock.web.basis.model.project.service.ProjectServiceFacadeImpl;
import com.castlemock.web.basis.web.mvc.controller.AbstractViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The UpdateProjectController controller provides functionality to update
 * a specific project.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web")
@ConditionalOnExpression("${server.mode.demo} == false")
public class UpdateProjectController extends AbstractViewController {

    private static final String PAGE = "basis/project/updateProject";

    @Autowired
    private ProjectServiceFacadeImpl projectServiceComponent;

    /**
     * Retrieves a specific project and creates a view that displays it to the user.
     * The user is allowed to modify certain attributes of the project. The modified
     * values might later be used to update the project attributes.
     * @param projectType The type of the project
     * @param projectId The id of the project that will be retrieved and displayed
     * @return A view that displays the retrieved project.
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "{projectType}/project/{projectId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String projectType, @PathVariable final String projectId) {
        final ProjectDto projectDto = projectServiceComponent.findOne(projectType, projectId);
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(PROJECT, projectDto);
        return model;
    }

    /**
     * The method is responsible for updating the attributes for a specific project.
     * @param projectType The type of the project
     * @param projectId The id of the project that will be updated
     * @param updatedProject The updated version of the project that will be updated
     * @return Redirects the user back to the main page for the project that was updated
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "{projectType}/project/{projectId}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable final String projectType, @PathVariable final String projectId, @ModelAttribute final ProjectDto updatedProject) {
        projectServiceComponent.update(projectType, projectId, updatedProject);
        return redirect("/" + projectType + "/project/" + projectId);
    }

}