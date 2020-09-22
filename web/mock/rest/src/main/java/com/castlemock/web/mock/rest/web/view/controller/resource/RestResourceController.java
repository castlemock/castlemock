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

package com.castlemock.web.mock.rest.web.view.controller.resource;


import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.service.project.input.ReadRestMethodInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestResourceInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestMethodOutput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestResourceOutput;
import com.castlemock.web.mock.rest.web.view.command.method.DeleteRestMethodsCommand;
import com.castlemock.web.mock.rest.web.view.command.method.RestMethodModifierCommand;
import com.castlemock.web.mock.rest.web.view.command.method.UpdateRestMethodsEndpointCommand;
import com.castlemock.web.mock.rest.web.view.controller.AbstractRestViewController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * The REST resource controller provides functionality to retrieve a specific REST resource
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/web/rest/project")
public class RestResourceController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/resource/restResource";
    private static final String REST_METHOD_MODIFIER_COMMAND = "command";
    private static final Logger LOGGER = LoggerFactory.getLogger(RestResourceController.class);
    private static final String DELETE_REST_METHODS = "delete";
    private static final String DELETE_REST_METHODS_PAGE = "mock/rest/method/deleteRestMethods";
    private static final String DELETE_REST_METHODS_COMMAND = "deleteRestMethodsCommand";
    private static final String UPDATE_STATUS = "update";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String UPDATE_REST_METHODS_ENDPOINT_PAGE = "mock/rest/method/updateRestMethodsEndpoint";
    private static final String UPDATE_REST_METHODS_ENDPOINT_COMMAND = "command";

    /**
     * Retrieves a specific REST resource with a project id, application and resource id
     * @param restProjectId The id of the project that the REST resource belongs to
     * @param restApplicationId The id of the application that the REST resource belongs to
     * @param restResourceId The id of the REST resource that should be retrieve
     * @param request The incoming servlet request. Used to extract the address used to invoke the REST methods
     * @return REST resource that matches the provided resource id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String restProjectId,
                                    @PathVariable final String restApplicationId,
                                    @PathVariable final String restResourceId,
                                    final HttpServletRequest request) {
        final ReadRestResourceOutput output = serviceProcessor.process(ReadRestResourceInput.builder()
                .restProjectId(restProjectId)
                .restApplicationId(restApplicationId)
                .restResourceId(restResourceId)
                .build());
        final RestResource restResource = output.getRestResource();

        final String invokeAddress = getRestInvokeAddress(request,
                restProjectId, restApplicationId, restResource.getUri());
        restResource.setInvokeAddress(invokeAddress);
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION_ID, restApplicationId);
        model.addObject(REST_RESOURCE, restResource);
        model.addObject(REST_METHOD_STATUSES, getRestMethodStatuses());
        model.addObject(REST_METHOD_MODIFIER_COMMAND, new RestMethodModifierCommand());
        return model;
    }

    /**
     * The method is responsible for executing a specific action for {@link com.castlemock.core.mock.rest.model.project.domain.RestMethod}.
     * The following actions is supported:
     * <ul>
     *  <li>Update status: Updates a method status</li>
     *  <li>Delete REST method: Deletes one or more REST methods ({@link com.castlemock.core.mock.rest.model.project.domain.RestMethod})</li>
     *  <li>Update endpoint: Change the endpoint for certain REST methods</li>
     * </ul>
     * @param restProjectId The id of the project responsible for the REST resource
     * @param restApplicationId The id of the application responsible for the REST resource
     * @param restResourceId The id of the resource that the action should be invoked upon
     * @param action The requested action
     * @param command The command object contains meta data required for certain actions
     * @return Either a model related to the action or redirects the user to the REST application page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}", method = RequestMethod.POST)
    public ModelAndView projectFunctionality(@PathVariable final String restProjectId,
                                             @PathVariable final String restApplicationId,
                                             @PathVariable final String restResourceId,
                                             @RequestParam final String action,
                                             @ModelAttribute(name="command") final RestMethodModifierCommand command) {
        LOGGER.debug("Requested REST project action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final RestMethodStatus restMethodStatus = RestMethodStatus.valueOf(command.getRestMethodStatus());
            for(String restMethodId : command.getRestMethodIds()){
                final ReadRestMethodOutput readRestMethodOutput =
                        serviceProcessor.process(ReadRestMethodInput.builder()
                                .restProjectId(restProjectId)
                                .restApplicationId(restApplicationId)
                                .restResourceId(restResourceId)
                                .restMethodId(restMethodId)
                                .build());

                RestMethod restMethod = readRestMethodOutput.getRestMethod();
                restMethod.setStatus(restMethodStatus);

                serviceProcessor.process(UpdateRestMethodInput.builder()
                        .restProjectId(restProjectId)
                        .restApplicationId(restApplicationId)
                        .restResourceId(restResourceId)
                        .restMethodId(restMethodId)
                        .restMethod(restMethod)
                        .build());
            }
        } if(DELETE_REST_METHODS.equalsIgnoreCase(action)) {
            final List<RestMethod> restMethods = new ArrayList<RestMethod>();
            for(String restMethodId : command.getRestMethodIds()){
                final ReadRestMethodOutput restMethodOutput = serviceProcessor.process(ReadRestMethodInput.builder()
                        .restProjectId(restProjectId)
                        .restApplicationId(restApplicationId)
                        .restResourceId(restResourceId)
                        .restMethodId(restMethodId)
                        .build());
                final RestMethod restResource = restMethodOutput.getRestMethod();
                restMethods.add(restResource);
            }
            final ModelAndView model = createPartialModelAndView(DELETE_REST_METHODS_PAGE);
            model.addObject(REST_PROJECT_ID, restProjectId);
            model.addObject(REST_APPLICATION_ID, restApplicationId);
            model.addObject(REST_RESOURCE_ID, restResourceId);
            model.addObject(REST_METHODS, restMethods);
            model.addObject(DELETE_REST_METHODS_COMMAND, new DeleteRestMethodsCommand());
            return model;
        } else if(UPDATE_ENDPOINTS.equalsIgnoreCase(action)){
            final List<RestMethod> restMethods = new ArrayList<RestMethod>();
            for(String restMethodId : command.getRestMethodIds()){
                final ReadRestMethodOutput output = serviceProcessor.process(ReadRestMethodInput.builder()
                        .restProjectId(restProjectId)
                        .restApplicationId(restApplicationId)
                        .restResourceId(restResourceId)
                        .restMethodId(restMethodId)
                        .build());
                restMethods.add(output.getRestMethod());
            }
            final ModelAndView model = createPartialModelAndView(UPDATE_REST_METHODS_ENDPOINT_PAGE);
            model.addObject(REST_PROJECT_ID, restProjectId);
            model.addObject(REST_APPLICATION_ID, restApplicationId);
            model.addObject(REST_RESOURCE_ID, restResourceId);
            model.addObject(REST_METHODS, restMethods);
            model.addObject(UPDATE_REST_METHODS_ENDPOINT_COMMAND, new UpdateRestMethodsEndpointCommand());
            return model;
        }
        return redirect("/rest/project/" + restProjectId + "/application/" +
                restApplicationId + "/resource/" + restResourceId);
    }

}

