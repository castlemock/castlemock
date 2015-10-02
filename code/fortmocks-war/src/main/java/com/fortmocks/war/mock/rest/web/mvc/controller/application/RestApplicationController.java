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

package com.fortmocks.war.mock.rest.web.mvc.controller.application;

import com.fortmocks.core.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.war.mock.rest.web.mvc.command.resource.DeleteRestResourcesCommand;
import com.fortmocks.war.mock.rest.web.mvc.command.resource.RestResourceModifierCommand;
import com.fortmocks.war.mock.rest.web.mvc.controller.AbstractRestViewController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * The project controller provides functionality to retrieve a specific project
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/web/rest/project")
public class RestApplicationController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/application/restApplication";
    private static final String REST_RESOURCE_MODIFIER_COMMAND = "restResourceModifierCommand";
    private static final Logger LOGGER = Logger.getLogger(RestApplicationController.class);
    private static final String DELETE_REST_RESOURCES = "delete";
    private static final String DELETE_REST_RESOURCES_COMMAND = "deleteRestResourcesCommand";
    private static final String DELETE_REST_RESOURCES_PAGE = "mock/rest/resource/deleteRestResources";

    /**
     * Retrieves a specific project with a project id
     * @param restProjectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final Long restProjectId, @PathVariable final Long restApplicationId) {
        final RestApplicationDto restApplication = restProjectService.findRestApplication(restProjectId, restApplicationId);

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION, restApplication);
        model.addObject(REST_RESOURCE_MODIFIER_COMMAND, new RestResourceModifierCommand());
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}", method = RequestMethod.POST)
    public ModelAndView projectFunctionality(@PathVariable final Long restProjectId, @PathVariable final Long restApplicationId, @RequestParam final String action, @ModelAttribute final RestResourceModifierCommand restResourceModifierCommand) {
        LOGGER.debug("Requested REST project action requested: " + action);
        if(DELETE_REST_RESOURCES.equalsIgnoreCase(action)) {
            final List<RestResourceDto> restResources = new ArrayList<RestResourceDto>();
            for(Long restResourceId : restResourceModifierCommand.getRestResourceIds()){
                final RestResourceDto restResourceDto = restProjectService.findRestResource(restProjectId, restApplicationId, restResourceId);
                restResources.add(restResourceDto);
            }
            final ModelAndView model = createPartialModelAndView(DELETE_REST_RESOURCES_PAGE);
            model.addObject(REST_PROJECT_ID, restProjectId);
            model.addObject(REST_APPLICATION_ID, restApplicationId);
            model.addObject(REST_RESOURCES, restResources);
            model.addObject(DELETE_REST_RESOURCES_COMMAND, new DeleteRestResourcesCommand());
            return model;
        }
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId);
    }
}

