/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.mock.graphql.web.view.controller.project;

import com.castlemock.core.mock.graphql.model.GraphQLDefinitionType;
import com.castlemock.core.mock.graphql.service.project.input.ImportGraphQLDefinitionInput;
import com.castlemock.web.basis.manager.FileManager;
import com.castlemock.web.mock.graphql.web.view.command.project.GraphQLDefinitionFileUploadForm;
import com.castlemock.web.mock.graphql.web.view.controller.AbstractGraphQLViewController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
@Controller
@RequestMapping("/web/graphql/project")
@ConditionalOnExpression("${server.mode.demo} == false")
public class GraphQLImportDefinitionController extends AbstractGraphQLViewController {

    private static final String PAGE = "mock/graphql/project/graphQLImportDefinition";
    private static final String TYPE_LINK = "link";
    private static final String TYPE_FILE = "file";
    private static final String DEFINITION_TYPE = "definitionType";
    private static final String DEFINITION_DISPLAY_NAME = "definitionDisplayName";
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphQLImportDefinitionController.class);

    @Autowired
    private FileManager fileManager;

    /**
     * The method returns a view which is used to upload a WADL file for a specific project
     * @param projectId The id of the project that will get the new WADL
     * @return A view that provides functionality to upload a WADL file
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/import", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String projectId,
                                    @PathVariable final String applicationId,
                                    @RequestParam(value="type") final GraphQLDefinitionType definitionType) {
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(GRAPHQL_PROJECT_ID, projectId);
        model.addObject(GRAPHQL_APPLICATION_ID, applicationId);
        model.addObject(FILE_UPLOAD_FORM, new GraphQLDefinitionFileUploadForm());
        model.addObject(DEFINITION_TYPE, definitionType.name());
        model.addObject(DEFINITION_DISPLAY_NAME, definitionType.getDisplayName());
        return model;
    }

    /**
     * The method provides functionality to upload a new WADL file. Resources will be created
     * based on the uploaded WADL file.
     * @param projectId The id of the project that will get the new WADL
     * @param type The upload type. It is used to determine if a WADL file should be uploaded or downloaded from a
     *             provided URL.
     * @param uploadForm The file upload form that contains the WADL file
     * @return A view that redirects the user to the main page for the project.
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value="/{projectId}/application/{applicationId}/import", method=RequestMethod.POST)
    public ModelAndView uploadWADL(@PathVariable final String projectId,
                                   @PathVariable final String applicationId,
                                   @RequestParam final String type,
                                   @ModelAttribute("uploadForm") final GraphQLDefinitionFileUploadForm uploadForm) throws IOException {
         List<File> files = null;

        if(TYPE_FILE.equals(type)){
            files = fileManager.uploadFiles(uploadForm.getFiles());
        }

        try {
            serviceProcessor.process(new ImportGraphQLDefinitionInput(projectId, applicationId,
                    files, uploadForm.getLink(), uploadForm.getDefinitionType()));
            return redirect("/graphql/project/" + projectId + "/application/" + applicationId + "?upload=success");
        } catch (Exception e){
            return redirect("/graphql/project/" + projectId + "/application/" + applicationId + "?upload=error");
        } finally {
            if(files != null){
                for(File uploadedFile : files){
                    boolean deletionResult = fileManager.deleteFile(uploadedFile);
                    if(deletionResult){
                        LOGGER.debug("Deleted the following WADL file: " + uploadedFile.getName());
                    } else {
                        LOGGER.warn("Unable to delete the following WADL file: " + uploadedFile.getName());
                    }

                }
            }
        }
    }
}