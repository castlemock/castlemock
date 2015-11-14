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

package com.fortmocks.mock.soap.web.mvc.controller.project;

import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.mock.soap.manager.WSDLComponent;
import com.fortmocks.core.mock.soap.model.project.service.message.input.CreateSoapPortsInput;
import com.fortmocks.mock.soap.web.mvc.command.project.WSDLFileUploadForm;
import com.fortmocks.mock.soap.web.mvc.controller.AbstractSoapViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * The AddWSDLController controller provides functionality to add a WSDL to a project.
 * When a WSDL file is uploaded, new ports will be created and associated with the project
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/web/soap/project")
public class SoapAddWSDLController extends AbstractSoapViewController {

    private static final String PAGE = "mock/soap/project/soapAddWSDL";
    private static final String TYPE_LINK = "link";
    private static final String TYPE_FILE = "file";

    @Autowired
    private WSDLComponent wsdlComponent;

    /**
     * The method returns a view which is used to upload a WSDL file for a specific project
     * @param projectId The id of the project that will get the new WSDL
     * @return A view that provides functionality to upload a WSDL file
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/add/wsdl", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final Long projectId) {
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(SOAP_PROJECT_ID, projectId);
        model.addObject(FILE_UPLOAD_FORM, new WSDLFileUploadForm());
        return model;
    }

    /**
     * The method provides functionality to upload a new WSDL file. Ports and operations will be created
     * based on the uploaded WSDL file.
     * @param projectId The id of the project that will get the new WSDL
     * @param uploadForm The file upload form that contains the WSDL file
     * @return A view that redirects the user to the main page for the project.
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value="/{projectId}/add/wsdl", method=RequestMethod.POST)
    public ModelAndView uploadWSDL(@PathVariable final Long projectId, @RequestParam final String type, @ModelAttribute("uploadForm") final WSDLFileUploadForm uploadForm){
        List<SoapPortDto> soapPorts = null;

        if(TYPE_FILE.equals(type)){
            soapPorts = wsdlComponent.createSoapPorts(uploadForm.getFiles(), uploadForm.isGenerateResponse());
        } else if(TYPE_LINK.equals(type)){
            soapPorts = wsdlComponent.createSoapPorts(uploadForm.getLink(), uploadForm.isGenerateResponse());
        }

        serviceProcessor.process(new CreateSoapPortsInput(projectId, soapPorts));
        return redirect("/soap/project/" + projectId);
    }
}