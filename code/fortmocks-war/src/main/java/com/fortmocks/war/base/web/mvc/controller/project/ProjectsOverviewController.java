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

package com.fortmocks.war.base.web.mvc.controller.project;

import com.fortmocks.core.base.model.project.dto.ProjectDto;
import com.fortmocks.war.base.model.project.service.ProjectServiceFacadeImpl;
import com.fortmocks.war.base.web.mvc.command.project.DeleteProjectsCommand;
import com.fortmocks.war.base.web.mvc.command.project.ProjectModifierCommand;
import com.fortmocks.war.base.web.mvc.controller.AbstractViewController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;

/**
 * The ProjectsOverviewController controller provides functionalities that involves
 * projects.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web")
public class ProjectsOverviewController extends AbstractViewController {

    private static final String PAGE = "core/project/projectsOverview";
    private static final String DELETE_PROJECTS_PAGE = "core/project/deleteProjects";
    private static final String EXPORT_PROJECTS = "export";
    private static final String DELETE_PROJECTS = "delete";
    private static final String PROJECT_MODIFIER_COMMAND = "projectModifierCommand";
    private static final String DELETE_PROJECTS_COMMAND = "deleteProjectsCommand";
    private static final Logger LOGGER = Logger.getLogger(ProjectsOverviewController.class);

    @Autowired
    private ProjectServiceFacadeImpl projectServiceComponent;

    /**
     * The method provides a view displaying all the projects.
     * @return View with all the projects.
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        final List<ProjectDto> projects = new LinkedList<ProjectDto>();
        projects.addAll(projectServiceComponent.findAll());
        ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(PROJECTS, projects);
        model.addObject(PROJECT_MODIFIER_COMMAND, new ProjectModifierCommand());
        return model;
    }

    /**
     * The method provides the functionality to update, delete and export projects. It bases the requested functionality based
     * on the provided action.
     * @param action The action is used to determined which action/functionality the perform.
     * @param projectModifierCommand The project overview command contains which ids going to be updated, deleted or exported.
     * @return The model depending in which action was requested.
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView projectFunctionality(@RequestParam String action, @ModelAttribute ProjectModifierCommand projectModifierCommand) {
        LOGGER.debug("Project action requested: " + action);
        if(EXPORT_PROJECTS.equalsIgnoreCase(action)) {
            throw new UnsupportedOperationException();
        } else if(DELETE_PROJECTS.equalsIgnoreCase(action)) {
            List<ProjectDto> projects = new LinkedList<ProjectDto>();
            for(String project : projectModifierCommand.getProjects()){
                final String[] projectData = project.split(SLASH);

                if(projectData.length != 2){
                    continue;
                }

                final String projectTypeUrl = projectData[0];
                final Long projectId = Long.parseLong(projectData[1]);
                final ProjectDto projectDto = projectServiceComponent.findOne(projectTypeUrl, projectId);
                projects.add(projectDto);
            }
            ModelAndView model = createPartialModelAndView(DELETE_PROJECTS_PAGE);
            model.addObject(PROJECTS, projects);
            model.addObject(DELETE_PROJECTS_COMMAND, new DeleteProjectsCommand());
            return model;
        }

        return redirect();
    }
}
