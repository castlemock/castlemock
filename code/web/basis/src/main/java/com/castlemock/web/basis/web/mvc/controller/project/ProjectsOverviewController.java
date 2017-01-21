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
import com.castlemock.web.basis.manager.FileManager;
import com.castlemock.web.basis.model.project.service.ProjectServiceFacadeImpl;
import com.castlemock.web.basis.web.mvc.command.project.DeleteProjectsCommand;
import com.castlemock.web.basis.web.mvc.command.project.ProjectModifierCommand;
import com.castlemock.web.basis.web.mvc.controller.AbstractViewController;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    private static final String PAGE = "basis/project/projectsOverview";
    private static final String DELETE_PROJECTS_PAGE = "basis/project/deleteProjects";
    private static final String EXPORT_PROJECTS = "export";
    private static final String DELETE_PROJECTS = "delete";
    private static final String PROJECT_MODIFIER_COMMAND = "projectModifierCommand";
    private static final String DELETE_PROJECTS_COMMAND = "deleteProjectsCommand";
    private static final Logger LOGGER = Logger.getLogger(ProjectsOverviewController.class);

    @Autowired
    private ProjectServiceFacadeImpl projectServiceFacade;

    @Autowired
    private FileManager fileManager;

    @Value(value = "${temp.file.directory}")
    private String tempFilesFolder;

    /**
     * The method provides a view displaying all the projects.
     * @return View with all the projects.
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        final List<ProjectDto> projects = new LinkedList<ProjectDto>();
        projects.addAll(projectServiceFacade.findAll());
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
     * @param response The HTTP servlet response
     * @return The model depending in which action was requested.
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView projectFunctionality(@RequestParam String action, @ModelAttribute ProjectModifierCommand projectModifierCommand, HttpServletResponse response) {
        LOGGER.debug("Project action requested: " + action);
        if(EXPORT_PROJECTS.equalsIgnoreCase(action)) {
            if(projectModifierCommand.getProjects().length == 0){
                return redirect();
            }

            ZipOutputStream zipOutputStream = null;
            InputStream inputStream = null;
            final String outputFilename = tempFilesFolder + SLASH + "exported-projects-" + new Date().getTime() + ".zip";
            try {
                zipOutputStream = new ZipOutputStream(new FileOutputStream(outputFilename));
                for(String project : projectModifierCommand.getProjects()) {
                    final String[] projectData = project.split(SLASH);
                    if(projectData.length != 2){
                        continue;
                    }

                    final String projectTypeUrl = projectData[0];
                    final String projectId = projectData[1];
                    final String exportedProject = projectServiceFacade.exportProject(projectTypeUrl, projectId);
                    final byte[] data = exportedProject.getBytes();
                    final String filename = "exported-project-" + projectTypeUrl + "-" + projectId + ".xml";
                    zipOutputStream.putNextEntry(new ZipEntry(filename));
                    zipOutputStream.write(data, 0, data.length);
                    zipOutputStream.closeEntry();
                }
                zipOutputStream.close();

                inputStream = new FileInputStream(outputFilename);
                IOUtils.copy(inputStream, response.getOutputStream());

                response.setContentType("application/zip");
                response.flushBuffer();
                return null;
            } catch (IOException exception) {
                LOGGER.error("Unable to export multiple projects and zip them", exception);
            } finally {
                if(zipOutputStream != null){
                    try {
                        zipOutputStream.close();
                    } catch (IOException exception) {
                        LOGGER.error("Unable to close the zip output stream", exception);
                    }
                }
                if(inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException exception) {
                        LOGGER.error("Unable to close the input stream", exception);
                    }
                }
                fileManager.deleteUploadedFile(outputFilename);
            }
        } else if(DELETE_PROJECTS.equalsIgnoreCase(action)) {
            List<ProjectDto> projects = new LinkedList<ProjectDto>();
            for(String project : projectModifierCommand.getProjects()){
                final String[] projectData = project.split(SLASH);

                if(projectData.length != 2){
                    continue;
                }

                final String projectTypeUrl = projectData[0];
                final String projectId = projectData[1];
                final ProjectDto projectDto = projectServiceFacade.findOne(projectTypeUrl, projectId);
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
