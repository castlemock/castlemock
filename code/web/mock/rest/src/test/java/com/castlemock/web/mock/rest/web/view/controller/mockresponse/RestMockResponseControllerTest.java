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

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.rest.model.project.domain.*;
import com.castlemock.core.mock.rest.service.project.input.ReadRestMockResponseInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestResourceQueryParametersInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestMockResponseOutput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestResourceQueryParametersOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.rest.config.TestApplication;
import com.castlemock.web.mock.rest.model.project.*;
import com.castlemock.web.mock.rest.web.view.controller.AbstractRestControllerTest;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;


/**
 * @author: Karl Dahlgren
 * @since: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class RestMockResponseControllerTest extends AbstractRestControllerTest {

    private static final String PAGE = "partial/mock/rest/mockresponse/restMockResponse.jsp";


    @InjectMocks
    private RestMockResponseController restMockResponseController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return restMockResponseController;
    }

    @Test
    public void testGetMockResponse() throws Exception {
        final RestProject restProject = RestProjectGenerator.generateRestProject();
        final RestApplication restApplication = RestApplicationGenerator.generateRestApplication();
        final RestResource restResource = RestResourceGenerator.generateRestResource();
        final RestMethod restMethod = RestMethodGenerator.generateRestMethod();
        final RestMockResponse restMockResponse = RestMockResponseGenerator.generateRestMockResponse();
        final RestParameterQuery restParameterQuery = RestParameterQueryGenerator.generateRestParameterQuery();
        when(serviceProcessor.process(isA(ReadRestMockResponseInput.class))).thenReturn(ReadRestMockResponseOutput.builder()
                .restMockResponse(restMockResponse)
                .build());
        when(serviceProcessor.process(isA(ReadRestResourceQueryParametersInput.class)))
                .thenReturn(ReadRestResourceQueryParametersOutput.builder()
                        .queries(ImmutableSet.of(restParameterQuery))
                        .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT +
                SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() + SLASH +
                RESOURCE + SLASH + restResource.getId() + SLASH + METHOD + SLASH + restMethod.getId() + SLASH +
                RESPONSE + SLASH + restMockResponse.getId());
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(7 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, restProject.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_APPLICATION_ID, restApplication.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_RESOURCE_ID, restResource.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_METHOD_ID, restMethod.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(DEMO_MODE, false))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_MOCK_RESPONSE, restMockResponse));
    }

}
