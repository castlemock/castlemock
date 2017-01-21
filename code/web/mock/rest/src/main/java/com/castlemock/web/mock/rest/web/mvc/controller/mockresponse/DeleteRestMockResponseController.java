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

package com.castlemock.web.mock.rest.web.mvc.controller.mockresponse;

import com.castlemock.core.mock.rest.model.project.service.message.input.DeleteRestMockResponseInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.DeleteRestMockResponsesInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.ReadRestMockResponseInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ReadRestMockResponseOutput;
import com.castlemock.web.mock.rest.web.mvc.command.mockresponse.DeleteRestMockResponsesCommand;
import com.castlemock.web.mock.rest.web.mvc.controller.AbstractRestViewController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The DeleteMockResponseController is the controller class responsible
 * for deleting mocked responses
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/rest/project")
@ConditionalOnExpression("${server.mode.demo} == false")
public class DeleteRestMockResponseController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/mockresponse/deleteRestMockResponse";

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/{restMethodId}/response/{restMockResponseId}/delete", method = RequestMethod.GET)
    public ModelAndView showConfirmationPage(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @PathVariable final String restResourceId, @PathVariable final String restMethodId, @PathVariable final String restMockResponseId) {
        final ReadRestMockResponseOutput output = serviceProcessor.process(new ReadRestMockResponseInput(restProjectId, restApplicationId, restResourceId, restMethodId, restMockResponseId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION_ID, restApplicationId);
        model.addObject(REST_RESOURCE_ID, restResourceId);
        model.addObject(REST_METHOD_ID, restMethodId);
        model.addObject(REST_MOCK_RESPONSE, output.getRestMockResponse());
        return model;
    }


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/{restMethodId}/response/{restMockResponseId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @PathVariable final String restResourceId, @PathVariable final String restMethodId, @PathVariable final String restMockResponseId) {
        serviceProcessor.process(new DeleteRestMockResponseInput(restProjectId, restApplicationId, restResourceId, restMethodId, restMockResponseId));
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId + "/resource/" + restResourceId + "/method/" + restMethodId);
    }


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/{restMethodId}/response/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultpleMockResponses(@PathVariable final String restProjectId, @PathVariable final String restApplicationId, @PathVariable final String restResourceId, @PathVariable final String restMethodId, @ModelAttribute final DeleteRestMockResponsesCommand deleteRestMockResponsesCommand) {
        serviceProcessor.process(new DeleteRestMockResponsesInput(restProjectId, restApplicationId, restResourceId, restMethodId, deleteRestMockResponsesCommand.getRestMockResponses()));
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId + "/resource/" + restResourceId + "/method/" + restMethodId);
    }

}
