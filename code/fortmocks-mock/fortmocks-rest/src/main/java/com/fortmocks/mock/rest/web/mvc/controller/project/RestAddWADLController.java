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

package com.fortmocks.mock.rest.web.mvc.controller.project;

import com.fortmocks.mock.rest.manager.WADLComponent;
import com.fortmocks.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.mock.rest.web.mvc.command.project.WADLFileUploadForm;
import com.fortmocks.mock.rest.web.mvc.controller.AbstractRestViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * The RestAddWADLController controller provides functionality to add a WADL to a project.
 * When a WADL file is uploaded, new resources will be created and associated with the project
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/web/rest/project")
public class RestAddWADLController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/project/restAddWADL";
    private static final String TYPE_LINK = "link";
    private static final String TYPE_FILE = "file";

    @Autowired
    private WADLComponent wadlComponent;

    /**
     * The method returns a view which is used to upload a WADL file for a specific project
     * @param projectId The id of the project that will get the new WADL
     * @return A view that provides functionality to upload a WADL file
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/add/wadl", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final Long projectId) {
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, projectId);
        model.addObject(FILE_UPLOAD_FORM, new WADLFileUploadForm());
        return model;
    }

    /**
     * The method provides functionality to upload a new WADL file. Resources will be created
     * based on the uploaded WADL file.
     * @param projectId The id of the project that will get the new WADL
     * @param uploadForm The file upload form that contains the WADL file
     * @return A view that redirects the user to the main page for the project.
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value="/{projectId}/add/wadl", method=RequestMethod.POST)
    public ModelAndView uploadWADL(@PathVariable final Long projectId, @RequestParam final String type, @ModelAttribute("uploadForm") final WADLFileUploadForm uploadForm){
        List<RestResourceDto> restResourceDtos = null;

        if(TYPE_FILE.equals(type)){
            restResourceDtos = wadlComponent.createRestResources(uploadForm.getFiles(), uploadForm.isGenerateResponse());
        } else if(TYPE_LINK.equals(type)){
            restResourceDtos = wadlComponent.createRestResources(uploadForm.getLink(), uploadForm.isGenerateResponse());
        }

        // restProjectService.add()
        return redirect("/rest/project/" + projectId);
    }
}