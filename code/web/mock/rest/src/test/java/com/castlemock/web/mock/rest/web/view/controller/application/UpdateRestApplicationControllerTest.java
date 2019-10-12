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

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestApplicationTestBuilder;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.domain.RestProjectTestBuilder;
import com.castlemock.core.mock.rest.service.project.input.ReadRestApplicationInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestApplicationInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestApplicationsForwardedEndpointInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestApplicationOutput;
import com.castlemock.core.mock.rest.service.project.output.UpdateRestApplicationOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.rest.config.TestApplication;
import com.castlemock.web.mock.rest.web.view.command.application.UpdateRestApplicationsEndpointCommand;
import com.castlemock.web.mock.rest.web.view.controller.AbstractRestControllerTest;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class UpdateRestApplicationControllerTest extends AbstractRestControllerTest {

    private static final String PAGE = "partial/mock/rest/application/updateRestApplication.jsp";
    private static final String UPDATE = "update";
    private static final String CONFIRM = "confirm";

    @InjectMocks
    private UpdateRestApplicationController updateRestApplicationController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return updateRestApplicationController;
    }

    @Test
    public void testUpdatePortWithValidId() throws Exception {
        final RestProject restProject = RestProjectTestBuilder.builder().build();
        final RestApplication restApplication = RestApplicationTestBuilder.builder().build();
        when(serviceProcessor.process(any(ReadRestApplicationInput.class))).thenReturn(ReadRestApplicationOutput.builder()
                .restApplication(restApplication
                ).build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT +
                SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() + SLASH + UPDATE);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, restProject.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_APPLICATION, restApplication));
    }


    @Test
    public void testUpdateConfirmPortWithValidId() throws Exception {
        final RestProject restProject = RestProjectTestBuilder.builder().build();
        final RestApplication restApplication = RestApplicationTestBuilder.builder().build();
        when(serviceProcessor.process(any(UpdateRestApplicationInput.class))).thenReturn(UpdateRestApplicationOutput.builder()
                .updatedRestApplication(restApplication)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + PROJECT +
                SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() + SLASH +
                UPDATE, restApplication);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }

    @Test
    public void testUpdateEndpoint() throws Exception {
        final String projectId = "projectId";

        final UpdateRestApplicationsEndpointCommand command = new UpdateRestApplicationsEndpointCommand();
        command.setForwardedEndpoint("http://localhost:8080/web");
        command.setRestApplications(ImmutableList.of());

        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + PROJECT +
                SLASH + projectId + SLASH + APPLICATION + SLASH + "update/confirm")
                .flashAttr("command", command);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/web/rest/project/" + projectId));

        Mockito.verify(serviceProcessor, Mockito.times(1))
                .process(Mockito.any(UpdateRestApplicationsForwardedEndpointInput.class));
    }
}
