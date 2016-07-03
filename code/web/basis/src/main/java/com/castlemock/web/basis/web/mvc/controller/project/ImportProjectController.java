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

import com.castlemock.core.basis.model.project.service.ProjectServiceFacade;
import com.castlemock.web.basis.manager.FileManager;
import com.castlemock.web.basis.web.mvc.command.ProjectFileUploadForm;
import com.castlemock.web.basis.web.mvc.controller.AbstractViewController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The ImportProjectController controller provides the functionality to import
 * an external project into Castle Mock.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/project/import")
@ConditionalOnExpression("${server.mode.demo} == false")
public class ImportProjectController extends AbstractViewController {

    @Autowired
    private ProjectServiceFacade projectServiceFacade;
    @Autowired
    private FileManager fileManager;
    private static final String PAGE = "basis/project/importProject";
    private static final Logger LOGGER = Logger.getLogger(ImportProjectController.class);

    /**
     * Creates and return a view that provides the required functionality
     * to import a project.
     * @return A view that provides the required functionality to import a project
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(FILE_UPLOAD_FORM, new ProjectFileUploadForm());
        model.addObject(PROJECT_TYPES, projectServiceFacade.getTypes());
        return model;
    }


    /**
     * The method provides functionality to upload a XML that contains information about the project that is
     * about to be imported.
     * @param uploadForm The file upload form that contains the project file
     * @return A view that redirects the user to the main page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(method=RequestMethod.POST)
    public ModelAndView importProject(@ModelAttribute("uploadForm") final ProjectFileUploadForm uploadForm){
        importProject(uploadForm.getProjectType(), uploadForm.getFiles());
        return redirect();
    }


    /**
     * The method provides the functionality to import a project as a String
     * @param type The type value for the specific type that the instance belongs to
     * @param multipartFiles The imported project files
     */
    private void importProject(final String type, final List<MultipartFile> multipartFiles) {

        List<File> files = new ArrayList<File>();
        try {
            files = fileManager.uploadFiles(multipartFiles);

            for(File file : files){
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                final StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(line + "\n");
                }

                projectServiceFacade.importProject(type, stringBuilder.toString());
            }
        } catch (IOException e) {
            LOGGER.error("Unable to import projects", e);
        } finally {
            fileManager.deleteUploadedFiles(files);
        }

    }

}