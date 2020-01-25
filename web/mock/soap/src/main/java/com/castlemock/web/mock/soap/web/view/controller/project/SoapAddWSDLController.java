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

package com.castlemock.web.mock.soap.web.view.controller.project;

import com.castlemock.core.mock.soap.service.project.input.CreateSoapPortsInput;
import com.castlemock.web.basis.manager.FileManager;
import com.castlemock.web.mock.soap.web.view.command.project.WSDLFileUploadForm;
import com.castlemock.web.mock.soap.web.view.controller.AbstractSoapViewController;
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
 * The AddWSDLController controller provides functionality to add a WSDL to a project.
 * When a WSDL file is uploaded, new ports will be created and associated with the project
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/web/soap/project")
@ConditionalOnExpression("${server.mode.demo} == false")
public class SoapAddWSDLController extends AbstractSoapViewController {

    private static final String PAGE = "mock/soap/project/soapAddWSDL";
    private static final String TYPE_LINK = "link";
    private static final String TYPE_FILE = "file";
    private static final Logger LOGGER = LoggerFactory.getLogger(SoapAddWSDLController.class);

    @Autowired
    private FileManager fileManager;

    /**
     * The method returns a view which is used to upload a WSDL file for a specific project
     * @param projectId The id of the project that will get the new WSDL
     * @return A view that provides functionality to upload a WSDL file
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/add/wsdl", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String projectId) {
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(SOAP_PROJECT_ID, projectId);
        model.addObject(FILE_UPLOAD_FORM, new WSDLFileUploadForm());
        return model;
    }

    /**
     * The method provides functionality to upload a new WSDL file. Ports and operations will be created
     * based on the uploaded WSDL file.
     * @param projectId The id of the project that will get the new WSDL
     * @param type The upload type. It is used to determine if a WSDL file should be uploaded or downloaded from a
     *             provided URL.
     * @param uploadForm The file upload form that contains the WSDL file
     * @return A view that redirects the user to the main page for the project.
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value="/{projectId}/add/wsdl", method=RequestMethod.POST)
    public ModelAndView uploadWSDL(@PathVariable final String projectId,
                                   @RequestParam final String type,
                                   @ModelAttribute("uploadForm") final WSDLFileUploadForm uploadForm) throws IOException {

        List<File> uploadedFiles = null;
        if(TYPE_FILE.equals(type)){
            uploadedFiles = fileManager.uploadFiles(uploadForm.getFiles());
        }

        try {
            serviceProcessor.process(CreateSoapPortsInput.builder()
                    .projectId(projectId)
                    .files(uploadedFiles)
                    .includeImports(uploadForm.isIncludeImports())
                    .generateResponse(uploadForm.isGenerateResponse())
                    .location(uploadForm.getLink())
                    .build());
            return redirect("/soap/project/" + projectId + "?upload=success");
        }catch (Exception e){
            return redirect("/soap/project/" + projectId + "?upload=error", e);
        } finally {
            if(uploadedFiles != null){
                for(File uploadedFile : uploadedFiles){
                    boolean deletionResult = fileManager.deleteFile(uploadedFile);
                    if(deletionResult){
                        LOGGER.debug("Deleted the following WSDL file: " + uploadedFile.getName());
                    } else {
                        LOGGER.warn("Unable to delete the following WSDL file: " + uploadedFile.getName());
                    }

                }
            }
        }
    }

}