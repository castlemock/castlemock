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

package com.fortmocks.war.mock.rest.web.mvc.controller.resource;

import com.fortmocks.core.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.war.mock.rest.model.project.service.RestProjectService;
import com.fortmocks.war.mock.rest.web.mvc.command.method.DeleteRestMethodsCommand;
import com.fortmocks.war.mock.rest.web.mvc.command.method.RestMethodModifierCommand;
import com.fortmocks.war.mock.rest.web.mvc.controller.AbstractRestViewController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RestResourceController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/resource/restResource";
    private static final String REST_METHOD_MODIFIER_COMMAND = "restMethodModifierCommand";
    private static final Logger LOGGER = Logger.getLogger(RestResourceController.class);
    private static final String DELETE_REST_METHODS = "delete";
    private static final String DELETE_REST_METHODS_PAGE = "mock/rest/method/deleteRestMethods";
    private static final String DELETE_REST_METHODS_COMMAND = "deleteRestMethodsCommand";
    /**
     * Retrieves a specific project with a project id
     * @param projectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/resource/{resourceId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final Long projectId, @PathVariable final Long applicationId, @PathVariable final Long resourceId) {
        final RestResourceDto restResource = restProjectService.findRestResource(projectId, applicationId, resourceId);
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, projectId);
        model.addObject(REST_APPLICATION_ID, applicationId);
        model.addObject(REST_RESOURCE, restResource);
        model.addObject(REST_METHOD_MODIFIER_COMMAND, new RestMethodModifierCommand());
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}", method = RequestMethod.POST)
    public ModelAndView projectFunctionality(@PathVariable final Long restProjectId, @PathVariable final Long restApplicationId, @PathVariable final Long restResourceId, @RequestParam final String action, @ModelAttribute final RestMethodModifierCommand restMethodModifierCommand) {
        LOGGER.debug("Requested REST project action requested: " + action);
        if(DELETE_REST_METHODS.equalsIgnoreCase(action)) {
            final List<RestMethodDto> restMethods = new ArrayList<RestMethodDto>();
            for(Long restMethodId : restMethodModifierCommand.getRestMethodIds()){
                final RestMethodDto restResourceDto = restProjectService.findRestMethod(restProjectId, restApplicationId, restResourceId, restMethodId);
                restMethods.add(restResourceDto);
            }
            final ModelAndView model = createPartialModelAndView(DELETE_REST_METHODS_PAGE);
            model.addObject(REST_PROJECT_ID, restProjectId);
            model.addObject(REST_APPLICATION_ID, restApplicationId);
            model.addObject(REST_RESOURCE_ID, restResourceId);
            model.addObject(REST_METHODS, restMethods);
            model.addObject(DELETE_REST_METHODS_COMMAND, new DeleteRestMethodsCommand());
            return model;
        }
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId + "/resource/" + restResourceId);
    }

}

