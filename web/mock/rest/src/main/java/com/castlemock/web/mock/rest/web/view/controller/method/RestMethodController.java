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

package com.castlemock.web.mock.rest.web.view.controller.method;

import com.castlemock.core.mock.rest.service.event.input.ReadRestEventWithMethodIdInput;
import com.castlemock.core.mock.rest.service.event.output.ReadRestEventWithMethodIdOutput;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.service.project.input.*;
import com.castlemock.core.mock.rest.service.project.output.ReadRestMethodOutput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestMockResponseOutput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestResourceOutput;
import com.castlemock.web.mock.rest.web.view.command.mockresponse.DeleteRestMockResponsesCommand;
import com.castlemock.web.mock.rest.web.view.command.mockresponse.RestMockResponseModifierCommand;
import com.castlemock.web.mock.rest.web.view.controller.AbstractRestViewController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * The REST method controller provides functionality to retrieve a specific {@link RestMethod}.
 * The controller is also responsible for executing actions on {@link RestMockResponse} related to
 * the REST method.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/web/rest/project")
public class RestMethodController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/method/restMethod";
    private static final Logger LOGGER = Logger.getLogger(RestMethodController.class);
    private static final String UPDATE_STATUS = "update";
    private static final String DELETE_MOCK_RESPONSES = "delete";
    private static final String DUPLICATE_MOCK_RESPONSE = "duplicate";


    private static final String DELETE_REST_MOCK_RESPONSES_COMMAND = "command";
    private static final String REST_MOCK_RESPONSE_MODIFIER_COMMAND = "command";
    private static final String DELETE_MOCK_RESPONSES_PAGE = "mock/rest/mockresponse/deleteRestMockResponses";


    /**
     * Retrieves a specific method with a method id
     * @param restProjectId The id of the project responsible for the REST method
     * @param restApplicationId The id of the application responsible for the REST method
     * @param restResourceId The id of the resource responsible for the REST method
     * @param restMethodId The id of the method that should be retrieved
     * @param request The incoming servlet request. Used to extract the address used to invoke the REST methods
     * @return REST Method that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/{restMethodId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String restProjectId,
                                    @PathVariable final String restApplicationId,
                                    @PathVariable final String restResourceId,
                                    @PathVariable final String restMethodId,
                                    final ServletRequest request) {
        final ReadRestResourceOutput readRestResourceOutput = serviceProcessor.process(ReadRestResourceInput.builder()
                .restProjectId(restProjectId)
                .restApplicationId(restApplicationId)
                .restResourceId(restResourceId)
                .build());
        final ReadRestMethodOutput restMethodOutput = serviceProcessor.process(ReadRestMethodInput.builder()
                .restProjectId(restProjectId)
                .restApplicationId(restApplicationId)
                .restResourceId(restResourceId)
                .restMethodId(restMethodId)
                .build());
        final RestResource restResource = readRestResourceOutput.getRestResource();
        final RestMethod restMethod = restMethodOutput.getRestMethod();
        final ReadRestEventWithMethodIdOutput readRestEventWithMethodIdOutput = serviceProcessor.process(ReadRestEventWithMethodIdInput.builder()
                .restMethodId(restMethodId)
                .build());

        final String protocol = getProtocol(request);
        final String invokeAddress = getRestInvokeAddress(protocol, request.getServerPort(), restProjectId, restApplicationId, restResource.getUri());
        restMethod.setInvokeAddress(invokeAddress);

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION_ID, restApplicationId);
        model.addObject(REST_RESOURCE_ID, restResourceId);
        model.addObject(REST_METHOD, restMethod);
        model.addObject(REST_MOCK_RESPONSE_STATUSES, RestMockResponseStatus.values());
        model.addObject(REST_EVENTS, readRestEventWithMethodIdOutput.getRestEvents());
        model.addObject(REST_MOCK_RESPONSE_MODIFIER_COMMAND, new RestMockResponseModifierCommand());
        return model;
    }

    /**
     * The method is responsible for executing a specific action for a {@link com.castlemock.core.mock.rest.model.project.domain.RestMockResponse}.
     * The following actions is supported:
     * <ul>
     *  <li>Update status: Updates an mock response status</li>
     *  <li>Delete REST mock response: Deletes one or more REST mock response ({@link com.castlemock.core.mock.rest.model.project.domain.RestMockResponse})</li>
     * </ul>
     * @param restProjectId The id of the project responsible for the REST method
     * @param restApplicationId The id of the application responsible for the REST method
     * @param restResourceId The id of the resource responsible for the REST method
     * @param restMethodId The id of the method that the action should be invoked upon
     * @param action The requested action
     * @param command The command object contains meta data required for certain actions
     * @return Either a model related to the action or redirects the user to the REST method page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/{restMethodId}", method = RequestMethod.POST)
    public ModelAndView methodFunctionality(@PathVariable final String restProjectId,
                                            @PathVariable final String restApplicationId,
                                            @PathVariable final String restResourceId,
                                            @PathVariable final String restMethodId,
                                            @RequestParam final String action,
                                            @ModelAttribute(name="command") final RestMockResponseModifierCommand command) {
        LOGGER.debug("REST operation action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final RestMockResponseStatus status = RestMockResponseStatus.valueOf(command.getRestMockResponseStatus());
            for(String mockResponseId : command.getRestMockResponseIds()){
                ReadRestMockResponseOutput readRestMockResponseOutput = serviceProcessor.process(ReadRestMockResponseInput.builder()
                        .restProjectId(restProjectId)
                        .restApplicationId(restApplicationId)
                        .restResourceId(restResourceId)
                        .restMethodId(restMethodId)
                        .restMockResponse(mockResponseId)
                        .build());

                RestMockResponse restMockResponse = readRestMockResponseOutput.getRestMockResponse();
                restMockResponse.setStatus(status);
                serviceProcessor.process(UpdateRestMockResponseInput.builder()
                        .restProjectId(restProjectId)
                        .restApplicationId(restApplicationId)
                        .restResourceId(restResourceId)
                        .restMethodId(restMethodId)
                        .restMockResponseId(mockResponseId)
                        .restMockResponse(restMockResponse)
                        .build());
            }
        } else if(DELETE_MOCK_RESPONSES.equalsIgnoreCase(action)) {
            final List<RestMockResponse> mockResponses = new ArrayList<RestMockResponse>();
            for(String mockResponseId : command.getRestMockResponseIds()){
                final ReadRestMockResponseOutput output = serviceProcessor.process(ReadRestMockResponseInput.builder()
                        .restProjectId(restProjectId)
                        .restApplicationId(restApplicationId)
                        .restResourceId(restResourceId)
                        .restMethodId(restMethodId)
                        .restMockResponse(mockResponseId)
                        .build());

                mockResponses.add(output.getRestMockResponse());
            }
            final ModelAndView model = createPartialModelAndView(DELETE_MOCK_RESPONSES_PAGE);
            model.addObject(REST_PROJECT_ID, restProjectId);
            model.addObject(REST_APPLICATION_ID, restApplicationId);
            model.addObject(REST_RESOURCE_ID, restResourceId);
            model.addObject(REST_METHOD_ID, restMethodId);
            model.addObject(REST_MOCK_RESPONSES, mockResponses);
            model.addObject(DELETE_REST_MOCK_RESPONSES_COMMAND, new DeleteRestMockResponsesCommand());
            return model;
        } else if (DUPLICATE_MOCK_RESPONSE.equalsIgnoreCase(action)) {
            String copyOfLabel = messageSource.getMessage("rest.restmethod.label.copyOf", null, LocaleContextHolder.getLocale());
            for (String mockResponseId : command.getRestMockResponseIds()) {
                ReadRestMockResponseOutput readRestMockResponseOutput = serviceProcessor.process(ReadRestMockResponseInput.builder()
                        .restProjectId(restProjectId)
                        .restApplicationId(restApplicationId)
                        .restResourceId(restResourceId)
                        .restMethodId(restMethodId)
                        .restMockResponse(mockResponseId)
                        .build());

                RestMockResponse restMockResponse = readRestMockResponseOutput.getRestMockResponse();
                restMockResponse.setId(null);
                restMockResponse.setName(String.format("%s %s", copyOfLabel, restMockResponse.getName()));
                serviceProcessor.process(CreateRestMockResponseInput.builder()
                        .projectId(restProjectId)
                        .applicationId(restApplicationId)
                        .resourceId(restResourceId)
                        .methodId(restMethodId)
                        .mockResponse(restMockResponse)
                        .build());
            }
        }


        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId + "/resource/" +
                restResourceId + "/method/" + restMethodId);
    }
}

