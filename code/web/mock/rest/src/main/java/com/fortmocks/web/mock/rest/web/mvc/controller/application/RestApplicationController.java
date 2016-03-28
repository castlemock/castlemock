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

package com.fortmocks.web.mock.rest.web.mvc.controller.application;

import com.fortmocks.core.mock.rest.model.project.domain.RestMethodStatus;
import com.fortmocks.core.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.core.mock.rest.model.project.service.message.input.ReadRestApplicationInput;
import com.fortmocks.core.mock.rest.model.project.service.message.input.ReadRestResourceInput;
import com.fortmocks.core.mock.rest.model.project.service.message.input.UpdateRestResourcesStatusInput;
import com.fortmocks.core.mock.rest.model.project.service.message.output.ReadRestApplicationOutput;
import com.fortmocks.core.mock.rest.model.project.service.message.output.ReadRestResourceOutput;
import com.fortmocks.web.mock.rest.web.mvc.command.resource.DeleteRestResourcesCommand;
import com.fortmocks.web.mock.rest.web.mvc.command.resource.RestResourceModifierCommand;
import com.fortmocks.web.mock.rest.web.mvc.command.resource.UpdateRestResourcesEndpointCommand;
import com.fortmocks.web.mock.rest.web.mvc.controller.AbstractRestViewController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * The REST resource controller provides functionality to retrieve a specific {@link RestResourceDto}.
 * The controller is also responsible for executing actions on {@link RestMethodDto} related to
 * the REST resource.
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
    private static final String UPDATE_STATUS = "update";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String UPDATE_REST_RESOURCES_ENDPOINT_PAGE = "mock/rest/resource/updateRestResourcesEndpoint";
    private static final String UPDATE_REST_RESOURCES_ENDPOINT_COMMAND = "updateRestResourcesEndpointCommand";

    /**
     * Retrieves a specific project with a project id
     * @param restProjectId The id of the project that the application belongs to
     * @param restApplicationId The id of the application that should be retrieved
     * @return Application that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String restProjectId, @PathVariable final String restApplicationId) {
        final ReadRestApplicationOutput output = serviceProcessor.process(new ReadRestApplicationInput(restProjectId, restApplicationId));

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION, output.getRestApplication());
        model.addObject(REST_METHOD_STATUSES, getRestMethodStatuses());
        model.addObject(REST_RESOURCE_MODIFIER_COMMAND, new RestResourceModifierCommand());
        return model;
    }

    /**
     * The method is responsible for executing a specific action for {@link com.fortmocks.core.mock.rest.model.project.domain.RestResource}.
     * The following actions is supported:
     * <ul>
     *  <li>Update status: Updates a method status</li>
     *  <li>Delete REST resource: Deletes one or more REST resources ({@link com.fortmocks.core.mock.rest.model.project.domain.RestResource})</li>
     *  <li>Update endpoint: Change the endpoint for certain REST methods</li>
     * </ul>
     * @param restProjectId The id of the project responsible for the REST application
     * @param restApplicationId The id of the application that the action should be invoked upon
     * @param action The requested action
     * @param restResourceModifierCommand The command object contains meta data required for certain actions
     * @return Either a model related to the action or redirects the user to the REST application page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}", method = RequestMethod.POST)
    public ModelAndView applicationFunctionality(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @RequestParam final String action, @ModelAttribute final RestResourceModifierCommand restResourceModifierCommand) {
        LOGGER.debug("Requested REST project action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final RestMethodStatus restMethodStatus = RestMethodStatus.valueOf(restResourceModifierCommand.getRestMethodStatus());
            for(String restResourceId : restResourceModifierCommand.getRestResourceIds()){
                serviceProcessor.process(new UpdateRestResourcesStatusInput(restProjectId, restApplicationId, restResourceId, restMethodStatus));
            }
        } else if(DELETE_REST_RESOURCES.equalsIgnoreCase(action)) {
            final List<RestResourceDto> restResources = new ArrayList<RestResourceDto>();
            for(String restResourceId : restResourceModifierCommand.getRestResourceIds()){
                ReadRestResourceOutput output = serviceProcessor.process(new ReadRestResourceInput(restProjectId, restApplicationId, restResourceId));
                restResources.add(output.getRestResource());
            }
            final ModelAndView model = createPartialModelAndView(DELETE_REST_RESOURCES_PAGE);
            model.addObject(REST_PROJECT_ID, restProjectId);
            model.addObject(REST_APPLICATION_ID, restApplicationId);
            model.addObject(REST_RESOURCES, restResources);
            model.addObject(DELETE_REST_RESOURCES_COMMAND, new DeleteRestResourcesCommand());
            return model;
        } else if(UPDATE_ENDPOINTS.equalsIgnoreCase(action)){
            final List<RestResourceDto> restResourceDtos = new ArrayList<RestResourceDto>();
            for(String restResourceId : restResourceModifierCommand.getRestResourceIds()){
                final ReadRestResourceOutput output = serviceProcessor.process(new ReadRestResourceInput(restProjectId, restApplicationId, restResourceId));
                restResourceDtos.add(output.getRestResource());
            }
            final ModelAndView model = createPartialModelAndView(UPDATE_REST_RESOURCES_ENDPOINT_PAGE);
            model.addObject(REST_PROJECT_ID, restProjectId);
            model.addObject(REST_APPLICATION_ID, restApplicationId);
            model.addObject(REST_RESOURCES, restResourceDtos);
            model.addObject(UPDATE_REST_RESOURCES_ENDPOINT_COMMAND, new UpdateRestResourcesEndpointCommand());
            return model;
        }
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId);
    }
}

