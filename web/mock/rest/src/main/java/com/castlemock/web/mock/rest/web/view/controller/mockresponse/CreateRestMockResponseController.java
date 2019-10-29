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

package com.castlemock.web.mock.rest.web.view.controller.mockresponse;

import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.service.project.input.CreateRestMockResponseInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestMethodInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestResourceQueryParametersInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestMethodOutput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestResourceQueryParametersOutput;
import com.castlemock.web.mock.rest.web.view.controller.AbstractRestViewController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The project controller provides functionality to retrieve a specific project
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/web/rest/project")
@ConditionalOnExpression("${server.mode.demo} == false")
public class CreateRestMockResponseController extends AbstractRestViewController {

    private static final String PAGE = "mock/rest/mockresponse/createRestMockResponse";
    private static final Integer DEFAULT_HTTP_STATUS_CODE = 200;


    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/{restMethodId}/create/response", method = RequestMethod.GET)
    public ModelAndView displayCreatePage(@PathVariable final String restProjectId,
                                          @PathVariable final String restApplicationId,
                                          @PathVariable final String restResourceId,
                                          @PathVariable final String restMethodId) {
        final ReadRestMethodOutput output = serviceProcessor.process(ReadRestMethodInput.builder()
                .restProjectId(restProjectId)
                .restApplicationId(restApplicationId)
                .restResourceId(restResourceId)
                .restMethodId(restMethodId).build());

        final ReadRestResourceQueryParametersOutput resourceQueryParameters =
                serviceProcessor.process(ReadRestResourceQueryParametersInput.builder()
                        .projectId(restProjectId)
                        .applicationId(restApplicationId)
                        .resourceId(restResourceId)
                        .build());

        final RestMockResponse mockResponse = new RestMockResponse();
        mockResponse.setBody(output.getRestMethod().getDefaultBody());
        mockResponse.setHttpStatusCode(DEFAULT_HTTP_STATUS_CODE);
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION_ID, restApplicationId);
        model.addObject(REST_RESOURCE_ID, restResourceId);
        model.addObject(REST_METHOD_ID, restMethodId);
        model.addObject(REST_MOCK_RESPONSE, mockResponse);
        model.addObject(REST_MOCK_RESPONSE_STATUSES, RestMockResponseStatus.values());
        model.addObject(REST_QUERY_PARAMETERS, resourceQueryParameters.getQueries());
        return model;
    }


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/method/{restMethodId}/create/response", method = RequestMethod.POST)
    public ModelAndView createResponse(@PathVariable final String restProjectId,
                                       @PathVariable final String restApplicationId,
                                       @PathVariable final String restResourceId,
                                       @PathVariable final String restMethodId,
                                       @ModelAttribute final RestMockResponse restMockResponse) {
        serviceProcessor.process(CreateRestMockResponseInput.builder()
                .projectId(restProjectId)
                .applicationId(restApplicationId)
                .resourceId(restResourceId)
                .methodId(restMethodId)
                .mockResponse(restMockResponse)
                .build());
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId + "/resource/" +
                restResourceId + "/method/" + restMethodId);
    }


}

