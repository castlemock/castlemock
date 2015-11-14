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

package com.fortmocks.web.mock.rest.web.mvc.controller.method;

import com.fortmocks.core.mock.rest.model.event.service.message.input.ReadRestEventWithMethodIdInput;
import com.fortmocks.core.mock.rest.model.event.service.message.output.ReadRestEventWithMethodIdOutput;
import com.fortmocks.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.fortmocks.core.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestMockResponseDto;
import com.fortmocks.core.mock.rest.model.project.service.message.input.ReadRestMethodInput;
import com.fortmocks.core.mock.rest.model.project.service.message.input.ReadRestMockResponseInput;
import com.fortmocks.core.mock.rest.model.project.service.message.input.UpdateRestMockResponseStatusInput;
import com.fortmocks.core.mock.rest.model.project.service.message.output.ReadRestMethodOutput;
import com.fortmocks.core.mock.rest.model.project.service.message.output.ReadRestMockResponseOutput;
import com.fortmocks.web.mock.rest.web.mvc.command.mockresponse.DeleteRestMockResponsesCommand;
import com.fortmocks.web.mock.rest.web.mvc.command.mockresponse.RestMockResponseModifierCommand;
import com.fortmocks.web.mock.rest.web.mvc.controller.AbstractRestViewController;
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
public class RestMethodController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/method/restMethod";
    private static final Logger LOGGER = Logger.getLogger(RestMethodController.class);
    private static final String UPDATE_STATUS = "update";
    private static final String DELETE_MOCK_RESPONSES = "delete";

    private static final String DELETE_REST_MOCK_RESPONSES_COMMAND = "deleteRestMockResponsesCommand";
    private static final String REST_MOCK_RESPONSE_MODIFIER_COMMAND = "restMockResponseModifierCommand";
    private static final String DELETE_MOCK_RESPONSES_PAGE = "mock/rest/mockresponse/deleteRestMockResponses";
    protected static final String REST_MOCK_RESPONSE_STATUSES = "restMockResponseStatuses";

    /**
     * Retrieves a specific project with a project id
     * @param restProjectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/{restMethodId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final Long restProjectId, @PathVariable final Long restApplicationId, @PathVariable final Long restResourceId, @PathVariable final Long restMethodId) {
        final ReadRestMethodOutput output = serviceProcessor.process(new ReadRestMethodInput(restProjectId, restApplicationId, restResourceId, restMethodId));
        final RestMethodDto restMethod = output.getRestMethod();
        final ReadRestEventWithMethodIdOutput readRestEventWithMethodIdOutput = serviceProcessor.process(new ReadRestEventWithMethodIdInput(restMethodId));
        restMethod.setEvents(readRestEventWithMethodIdOutput.getRestEvents());

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION_ID, restApplicationId);
        model.addObject(REST_RESOURCE_ID, restResourceId);
        model.addObject(REST_METHOD, restMethod);
        model.addObject(REST_MOCK_RESPONSE_STATUSES, RestMockResponseStatus.values());
        model.addObject(REST_MOCK_RESPONSE_MODIFIER_COMMAND, new RestMockResponseModifierCommand());
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/{restMethodId}", method = RequestMethod.POST)
    public ModelAndView serviceFunctionality(@PathVariable final Long restProjectId, @PathVariable final Long restApplicationId, @PathVariable final Long restResourceId, @PathVariable final Long restMethodId, @RequestParam final String action, @ModelAttribute final RestMockResponseModifierCommand restMockResponseModifierCommand) {
        LOGGER.debug("REST operation action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final RestMockResponseStatus status = RestMockResponseStatus.valueOf(restMockResponseModifierCommand.getRestMockResponseStatus());
            for(Long mockResponseId : restMockResponseModifierCommand.getRestMockResponseIds()){
                serviceProcessor.process(new UpdateRestMockResponseStatusInput(restProjectId, restApplicationId, restResourceId, restMethodId, mockResponseId, status));
            }
        } else if(DELETE_MOCK_RESPONSES.equalsIgnoreCase(action)) {
            final List<RestMockResponseDto> mockResponses = new ArrayList<RestMockResponseDto>();
            for(Long mockResponseId : restMockResponseModifierCommand.getRestMockResponseIds()){
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

