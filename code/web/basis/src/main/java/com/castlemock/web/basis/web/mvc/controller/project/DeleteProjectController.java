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
import com.castlemock.web.basis.web.mvc.command.project.DeleteProjectsCommand;
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
 * The DeleteProjectController controller provides functionality to delete
 * a specific project.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web")
@ConditionalOnExpression("${server.mode.demo} == false")
public class DeleteProjectController extends AbstractViewController {

    private static final String PAGE = "basis/project/deleteProject";

    @Autowired
    private ProjectServiceFacadeImpl projectServiceComponent;

    /**
     * The method creates a confirmation page that displays the project that should be deleted
     * @param projectType The type of the project that should be deleted
     * @param projectId The id of the project that should be deleted
     * @return A confirmation view that displays the project that has been requested to be deleted.
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "{projectType}/project/{projectId}/delete", method = RequestMethod.GET)
    public ModelAndView getDeleteConfirmationPage(@PathVariable final String projectType, @PathVariable final String projectId) {

        final ProjectDto projectDto = projectServiceComponent.findOne(projectType, projectId);
        if(projectDto == null){
            throw new NullPointerException("Unable to find " + projectType + " project with id " + projectId);
        }

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(PROJECT, projectDto);
        return model;
    }

    /**
     * The method deleteProject provides functionality to delete a project with a specific id.
     * @param projectType The type of the project that should be deleted
     * @param projectId The id of the project that will be deleted
     * @return A view that redirects the user to the main page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "{projectType}/project/{projectId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView deleteProject(@PathVariable final String projectType, @PathVariable final String projectId) {
        projectServiceComponent.delete(projectType, projectId);
        return redirect();
    }

    /**
     * The method is used to deleted multiple projects at the same time
     * @param deleteProjectsCommand A command object that contains a list of all the projects that should be deleted.
     * @return A view that redirects the user to the main page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "project/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultpleProjects(@ModelAttribute DeleteProjectsCommand deleteProjectsCommand) {
        for(int index = 0; index < deleteProjectsCommand.getProjectIds().length; index++){
            final String projectId = deleteProjectsCommand.getProjectIds()[index];
            final String projectTypeUrl = deleteProjectsCommand.getTypeUrls()[index];
            projectServiceComponent.delete(projectTypeUrl, projectId);
        }
        return redirect();
    }


}