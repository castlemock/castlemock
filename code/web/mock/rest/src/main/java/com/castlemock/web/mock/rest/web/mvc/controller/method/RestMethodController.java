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

package com.castlemock.web.mock.rest.web.mvc.controller.method;

import com.castlemock.core.mock.rest.model.event.service.message.input.ReadRestEventWithMethodIdInput;
import com.castlemock.core.mock.rest.model.event.service.message.output.ReadRestEventWithMethodIdOutput;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.core.mock.rest.model.project.dto.RestMethodDto;
import com.castlemock.core.mock.rest.model.project.dto.RestMockResponseDto;
import com.castlemock.core.mock.rest.model.project.dto.RestResourceDto;
import com.castlemock.core.mock.rest.model.project.service.message.input.ReadRestMethodInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.ReadRestMockResponseInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.ReadRestResourceInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.UpdateRestMockResponseInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ReadRestMethodOutput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ReadRestMockResponseOutput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ReadRestResourceOutput;
import com.castlemock.web.mock.rest.web.mvc.command.mockresponse.DeleteRestMockResponsesCommand;
import com.castlemock.web.mock.rest.web.mvc.command.mockresponse.RestMockResponseModifierCommand;
import com.castlemock.web.mock.rest.web.mvc.controller.AbstractRestViewController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * The REST method controller provides functionality to retrieve a specific {@link RestMethodDto}.
 * The controller is also responsible for executing actions on {@link RestMockResponseDto} related to
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

    private static final String DELETE_REST_MOCK_RESPONSES_COMMAND = "deleteRestMockResponsesCommand";
    private static final String REST_MOCK_RESPONSE_MODIFIER_COMMAND = "restMockResponseModifierCommand";
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
    public ModelAndView defaultPage(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @PathVariable final String restResourceId, @PathVariable final String restMethodId, final ServletRequest request) {
        final ReadRestResourceOutput readRestResourceOutput = serviceProcessor.process(new ReadRestResourceInput(restProjectId, restApplicationId, restResourceId));
        final ReadRestMethodOutput restMethodOutput = serviceProcessor.process(new ReadRestMethodInput(restProjectId, restApplicationId, restResourceId, restMethodId));
        final RestResourceDto restResource = readRestResourceOutput.getRestResource();
        final RestMethodDto restMethod = restMethodOutput.getRestMethod();
        final ReadRestEventWithMethodIdOutput readRestEventWithMethodIdOutput = serviceProcessor.process(new ReadRestEventWithMethodIdInput(restMethodId));

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
     * @param restMockResponseModifierCommand The command object contains meta data required for certain actions
     * @return Either a model related to the action or redirects the user to the REST method page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/{restMethodId}", method = RequestMethod.POST)
    public ModelAndView methodFunctionality(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @PathVariable final String restResourceId, @PathVariable final String restMethodId, @RequestParam final String action, @ModelAttribute final RestMockResponseModifierCommand restMockResponseModifierCommand) {
        LOGGER.debug("REST operation action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final RestMockResponseStatus status = RestMockResponseStatus.valueOf(restMockResponseModifierCommand.getRestMockResponseStatus());
            for(String mockResponseId : restMockResponseModifierCommand.getRestMockResponseIds()){
                ReadRestMockResponseOutput readRestMockResponseOutput = serviceProcessor.process(new ReadRestMockResponseInput(restProjectId, restApplicationId, restResourceId, restMethodId, mockResponseId));
                RestMockResponseDto restMockResponseDto = readRestMockResponseOutput.getRestMockResponse();
                restMockResponseDto.setStatus(status);
                serviceProcessor.process(new UpdateRestMockResponseInput(restProjectId, restApplicationId, restResourceId, restMethodId, mockResponseId, restMockResponseDto));
            }
        } else if(DELETE_MOCK_RESPONSES.equalsIgnoreCase(action)) {
            final List<RestMockResponseDto> mockResponses = new ArrayList<RestMockResponseDto>();
            for(String mockResponseId : restMockResponseModifierCommand.getRestMockResponseIds()){
                final ReadRestMockResponseOutput output = serviceProcessor.process(new ReadRestMockResponseInput(restProjectId, restApplicationId, restResourceId, restMethodId, mockResponseId));
                mockResponses.add(output.getRestMockResponse());
            }
            final ModelAndView model = createPartialModelAndView(DELETE_MOCK_RESPONSES_PAGE);
            model.addObject(REST_PROJECT_ID, restProjectId);
            model.addObject(REST_APPLICATION_ID, restApplicationId);
            model.addObject(REST_RESOURCE_ID, restResourceId);
            model.addObject(REST_METHOD_ID, restMethodId);
            model.addObject(REST_METHOD, mockResponses);
            model.addObject(REST_MOCK_RESPONSES, mockResponses);
            model.addObject(DELETE_REST_MOCK_RESPONSES_COMMAND, new DeleteRestMockResponsesCommand());
            return model;
        }

        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId + "/resource/" + restResourceId + "/method/" + restMethodId);
    }
}

