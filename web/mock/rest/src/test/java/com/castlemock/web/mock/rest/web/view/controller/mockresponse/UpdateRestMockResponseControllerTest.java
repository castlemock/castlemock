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
import com.castlemock.core.mock.rest.model.project.*;
import com.castlemock.core.mock.rest.model.project.domain.*;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestMockResponseInput;
import com.castlemock.core.mock.rest.service.project.output.UpdateRestMockResponseOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.rest.config.TestApplication;
import com.castlemock.web.mock.rest.web.view.controller.AbstractRestControllerTest;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class UpdateRestMockResponseControllerTest extends AbstractRestControllerTest {

    private static final String UPDATE = "update";


    @InjectMocks
    private UpdateRestMockResponseController updateRestMockResponseController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return updateRestMockResponseController;
    }

    @Test
    public void testUpdateMockResponse() throws Exception {
        final RestProject restProject = RestProjectTestBuilder.builder().build();
        final RestApplication restApplication = RestApplicationTestBuilder.builder().build();
        final RestResource restResource = RestResourceTestBuilder.builder().build();
        final RestMethod restMethod = RestMethodTestBuilder.builder().build();
        final RestMockResponse restMockResponse = RestMockResponseTestBuilder.builder().build();
        when(serviceProcessor.process(any(UpdateRestMockResponseInput.class))).thenReturn(UpdateRestMockResponseOutput.builder()
                .updatedRestMockResponse(restMockResponse)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + PROJECT +
                SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() +
                SLASH + RESOURCE + SLASH + restResource.getId() + SLASH + METHOD + SLASH + restMethod.getId() +
                SLASH + RESPONSE + SLASH + restMockResponse.getId() + SLASH + UPDATE);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }

}
