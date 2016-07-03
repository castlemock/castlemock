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
import com.castlemock.web.basis.web.mvc.command.project.CreateProjectCommand;
import com.castlemock.web.basis.web.mvc.controller.AbstractViewController;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The CreateProjectController controller provides functionality to
 * create a new project
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/web/project/create")
public class CreateProjectController extends AbstractViewController {

    @Autowired
    protected DozerBeanMapper mapper;
    @Autowired
    private ProjectServiceFacadeImpl projectServiceFacade;

    private static final String PAGE = "basis/project/createProject";

    /**
     * The method create a view that displays all required attributes needed
     * for creating a new project.
     * @return A view that displays all required attributes for creating a new project.
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(PROJECT_TYPES, projectServiceFacade.getTypes());
        model.addObject(COMMAND, new CreateProjectCommand());
        return model;
    }

    /**
     * The method createProject creates a project based on the provided project DTO instance
     * @param createProjectCommand The command object contains the project DTO instance that will be created.
     *                             It also contains the type of the project
     * @return A view that redirects the user to overview page for the newly created project.
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView createProject(@ModelAttribute final CreateProjectCommand createProjectCommand) {
        final ProjectDto createdProject = projectServiceFacade.save(createProjectCommand.getProjectType(), createProjectCommand.getProject());
        final String typeUrl = projectServiceFacade.getTypeUrl(createProjectCommand.getProjectType());
        return redirect("/" + typeUrl + "/project/" + createdProject.getId());
    }

}
