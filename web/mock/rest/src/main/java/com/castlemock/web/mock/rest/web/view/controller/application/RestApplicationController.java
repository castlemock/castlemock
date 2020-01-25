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

package com.castlemock.web.mock.rest.web.view.controller.application;

import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.service.project.input.ReadRestApplicationInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestResourceInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestResourcesStatusInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestApplicationOutput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestResourceOutput;
import com.castlemock.web.mock.rest.web.view.command.resource.DeleteRestResourcesCommand;
import com.castlemock.web.mock.rest.web.view.command.resource.RestResourceModifierCommand;
import com.castlemock.web.mock.rest.web.view.command.resource.UpdateRestResourcesEndpointCommand;
import com.castlemock.web.mock.rest.web.view.controller.AbstractRestViewController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * The REST resource controller provides functionality to retrieve a specific {@link RestResource}.
 * The controller is also responsible for executing actions on {@link RestMethod} related to
 * the REST resource.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/web/rest/project")
public class RestApplicationController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/application/restApplication";
    private static final String REST_RESOURCE_MODIFIER_COMMAND = "command";
    private static final Logger LOGGER = LoggerFactory.getLogger(RestApplicationController.class);
    private static final String DELETE_REST_RESOURCES = "delete";
    private static final String DELETE_REST_RESOURCES_COMMAND = "command";
    private static final String DELETE_REST_RESOURCES_PAGE = "mock/rest/resource/deleteRestResources";
    private static final String UPDATE_STATUS = "update";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String UPDATE_REST_RESOURCES_ENDPOINT_PAGE = "mock/rest/resource/updateRestResourcesEndpoint";
    private static final String UPDATE_REST_RESOURCES_ENDPOINT_COMMAND = "command";

    /**
     * Retrieves a specific project with a project id
     * @param restProjectId The id of the project that the application belongs to
     * @param restApplicationId The id of the application that should be retrieved
     * @return Application that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String restProjectId,
                                    @PathVariable final String restApplicationId) {
        final ReadRestApplicationOutput output = serviceProcessor.process(ReadRestApplicationInput.builder()
                .restProjectId(restProjectId)
                .restApplicationId(restApplicationId)
                .build());

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION, output.getRestApplication());
        model.addObject(REST_METHOD_STATUSES, getRestMethodStatuses());
        model.addObject(REST_RESOURCE_MODIFIER_COMMAND, new RestResourceModifierCommand());
        return model;
    }

    /**
     * The method is responsible for executing a specific action for {@link com.castlemock.core.mock.rest.model.project.domain.RestResource}.
     * The following actions is supported:
     * <ul>
     *  <li>Update status: Updates a method status</li>
     *  <li>Delete REST resource: Deletes one or more REST resources ({@link com.castlemock.core.mock.rest.model.project.domain.RestResource})</li>
     *  <li>Update endpoint: Change the endpoint for certain REST methods</li>
     * </ul>
     * @param restProjectId The id of the project responsible for the REST application
     * @param restApplicationId The id of the application that the action should be invoked upon
     * @param action The requested action
     * @param command The command object contains meta data required for certain actions
     * @return Either a model related to the action or redirects the user to the REST application page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}", method = RequestMethod.POST)
    public ModelAndView applicationFunctionality(@PathVariable final String restProjectId,
                                                 @PathVariable final String restApplicationId,
                                                 @RequestParam final String action,
                                                 @ModelAttribute(name="command") final RestResourceModifierCommand command) {
        LOGGER.debug("Requested REST project action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final RestMethodStatus restMethodStatus = RestMethodStatus.valueOf(command.getRestMethodStatus());
            for(String restResourceId : command.getRestResourceIds()){
                serviceProcessor.process(UpdateRestResourcesStatusInput.builder()
                        .restProjectId(restProjectId)
                        .restApplicationId(restApplicationId)
                        .restResourceId(restResourceId)
                        .restMethodStatus(restMethodStatus)
                        .build());
            }
        } else if(DELETE_REST_RESOURCES.equalsIgnoreCase(action)) {
            final List<RestResource> restResources = new ArrayList<RestResource>();
            for(String restResourceId : command.getRestResourceIds()){
                ReadRestResourceOutput output = serviceProcessor.process(ReadRestResourceInput.builder()
                        .restProjectId(restProjectId)
                        .restApplicationId(restApplicationId)
                        .restResourceId(restResourceId)
                        .build());
                restResources.add(output.getRestResource());
            }
            final ModelAndView model = createPartialModelAndView(DELETE_REST_RESOURCES_PAGE);
            model.addObject(REST_PROJECT_ID, restProjectId);
            model.addObject(REST_APPLICATION_ID, restApplicationId);
            model.addObject(REST_RESOURCES, restResources);
            model.addObject(DELETE_REST_RESOURCES_COMMAND, new DeleteRestResourcesCommand());
            return model;
        } else if(UPDATE_ENDPOINTS.equalsIgnoreCase(action)){
            final List<RestResource> restResources = new ArrayList<RestResource>();
            for(String restResourceId : command.getRestResourceIds()){
                final ReadRestResourceOutput output = serviceProcessor.process(ReadRestResourceInput.builder()
                        .restProjectId(restProjectId)
                        .restApplicationId(restApplicationId)
                        .restResourceId(restResourceId)
                        .build());
                restResources.add(output.getRestResource());
            }
            final ModelAndView model = createPartialModelAndView(UPDATE_REST_RESOURCES_ENDPOINT_PAGE);
            model.addObject(REST_PROJECT_ID, restProjectId);
            model.addObject(REST_APPLICATION_ID, restApplicationId);
            model.addObject(REST_RESOURCES, restResources);
            model.addObject(UPDATE_REST_RESOURCES_ENDPOINT_COMMAND, new UpdateRestResourcesEndpointCommand());
            return model;
        }
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId);
    }
}

