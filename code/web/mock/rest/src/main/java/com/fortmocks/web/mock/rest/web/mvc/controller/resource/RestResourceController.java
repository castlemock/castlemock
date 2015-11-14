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

package com.fortmocks.web.mock.rest.web.mvc.controller.resource;


import com.fortmocks.core.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.core.mock.rest.model.project.service.message.input.ReadRestMethodInput;
import com.fortmocks.core.mock.rest.model.project.service.message.input.ReadRestResourceInput;
import com.fortmocks.core.mock.rest.model.project.service.message.output.ReadRestResourceOutput;
import com.fortmocks.web.mock.rest.web.mvc.command.method.DeleteRestMethodsCommand;
import com.fortmocks.web.mock.rest.web.mvc.command.method.RestMethodModifierCommand;
import com.fortmocks.web.mock.rest.web.mvc.controller.AbstractRestViewController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
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
     * @param restProjectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final Long restProjectId, @PathVariable final Long restApplicationId, @PathVariable final Long restResourceId, final ServletRequest request) {
        final ReadRestResourceOutput output = serviceProcessor.process(new ReadRestResourceInput(restProjectId, restApplicationId, restResourceId));
        final RestResourceDto restResource = output.getRestResource();
        String requestProtocol = HTTP;
        if(request.isSecure()){
            requestProtocol = HTTPS;
        }

        try {
            final String hostAddress = getHostAddress();
            restResource.setInvokeAddress(requestProtocol + hostAddress + ":" + request.getServerPort() + getContext() + SLASH + MOCK + SLASH + REST + SLASH + PROJECT + SLASH + restProjectId + SLASH + APPLICATION + SLASH + restApplicationId + restResource.getUri());
        } catch (Exception exception) {
            LOGGER.error("Unable to generate invoke URL", exception);
            throw new IllegalStateException("Unable to generate invoke URL for " + restResource.getName());
        }



        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION_ID, restApplicationId);
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
                final RestMethodDto restResourceDto = serviceProcessor.process(new ReadRestMethodInput(restProjectId, restApplicationId, restResourceId, restMethodId));
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

