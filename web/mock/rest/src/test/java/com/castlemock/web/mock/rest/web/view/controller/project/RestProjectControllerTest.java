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

package com.castlemock.web.mock.rest.web.view.controller.project;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.rest.model.project.domain.RestProjectTestBuilder;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.service.project.input.ReadRestApplicationInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestProjectInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestApplicationsStatusInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestApplicationOutput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestProjectOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.rest.config.TestApplication;
import com.castlemock.web.mock.rest.web.view.command.application.RestApplicationModifierCommand;
import com.castlemock.web.mock.rest.web.view.controller.AbstractRestControllerTest;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class RestProjectControllerTest extends AbstractRestControllerTest {

    private static final String PAGE = "partial/mock/rest/project/restProject.jsp";
    private static final String UPDATE_REST_APPLICATIONS_ENDPOINT_PAGE = "partial/mock/rest/application/updateRestApplicationsEndpoint.jsp";
    private static final String UPDATE_REST_APPLICATIONS_ENDPOINT_COMMAND = "command";
    private static final String DELETE_REST_APPLICATIONS_COMMAND = "command";
    private static final String DELETE_REST_APPLICATIONS_PAGE = "partial/mock/rest/application/deleteRestApplications.jsp";
    private static final String REST_APPLICATIONS = "restApplications";

    @InjectMocks
    private RestProjectController restProjectController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return restProjectController;
    }

    @Test
    public void testGetServiceValid() throws Exception {
        final RestProject restProject = RestProjectTestBuilder.builder().build();
        when(serviceProcessor.process(any(ReadRestProjectInput.class))).thenReturn(ReadRestProjectOutput.builder()
                .restProject(restProject)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + restProject.getId() + SLASH);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT, restProject));

    }

    @Test
    public void testGetServiceValidUploadSuccess() throws Exception {
        final RestProject restProject = RestProjectTestBuilder.builder().build();
        when(serviceProcessor.process(any(ReadRestProjectInput.class))).thenReturn(ReadRestProjectOutput.builder()
                .restProject(restProject)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + restProject.getId() + SLASH)
                .param("upload", "success");
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute("upload", "success"))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT, restProject));

    }

    @Test
    public void testGetServiceValidUploadError() throws Exception {
        final RestProject restProject = RestProjectTestBuilder.builder().build();
        when(serviceProcessor.process(any(ReadRestProjectInput.class))).thenReturn(ReadRestProjectOutput.builder()
                .restProject(restProject)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + restProject.getId() + SLASH)
                .param("upload", "error");
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute("upload", "error"))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT, restProject));

    }

    @Test
    public void projectFunctionalityUpdate() throws Exception {
        final String projectId = "projectId";
        final String[] restApplicationIds = {"restApplication1", "restApplication2"};

        final RestApplicationModifierCommand command = new RestApplicationModifierCommand();
        command.setRestApplicationIds(restApplicationIds);
        command.setRestMethodStatus("MOCKED");

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH)
                        .param("action", "update").flashAttr("command", command);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/web/rest/project/" + projectId));

        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.any(UpdateRestApplicationsStatusInput.class));

    }

    @Test
    public void projectFunctionalityDelete() throws Exception {
        final String projectId = "projectId";
        final String[] restApplicationIds = {"restApplication1", "restApplication2"};



        final RestApplication restApplication1 = new RestApplication();
        restApplication1.setName("restApplication1");

        final RestApplication restApplication2 = new RestApplication();
        restApplication1.setName("restApplication2");

        final List<RestApplication> restApplications = Arrays.asList(restApplication1, restApplication2);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadRestApplicationInput.class)))
                .thenReturn(ReadRestApplicationOutput.builder().restApplication(restApplication1).build())
                .thenReturn(ReadRestApplicationOutput.builder().restApplication(restApplication2).build());


        final RestApplicationModifierCommand command = new RestApplicationModifierCommand();
        command.setRestApplicationIds(restApplicationIds);

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH)
                        .param("action", "delete").flashAttr("command", command);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, DELETE_REST_APPLICATIONS_PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, projectId))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_APPLICATIONS, restApplications))
                .andExpect(MockMvcResultMatchers.model().attributeExists(DELETE_REST_APPLICATIONS_COMMAND));


        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.any(ReadRestApplicationInput.class));

    }

    @Test
    public void projectFunctionalityUpdateEndpoint() throws Exception {
        final String projectId = "projectId";
        final String[] restApplicationIds = {"restApplication1", "restApplication2"};



        final RestApplication restApplication1 = new RestApplication();
        restApplication1.setName("restApplication1");

        final RestApplication restApplication2 = new RestApplication();
        restApplication1.setName("restApplication2");

        final List<RestApplication> restApplications = Arrays.asList(restApplication1, restApplication2);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadRestApplicationInput.class)))
                .thenReturn(ReadRestApplicationOutput.builder()
                        .restApplication(restApplication1)
                        .build())
                .thenReturn(ReadRestApplicationOutput.builder()
                        .restApplication(restApplication2)
                        .build());


        final RestApplicationModifierCommand command = new RestApplicationModifierCommand();
        command.setRestApplicationIds(restApplicationIds);

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH)
                        .param("action", "update-endpoint").flashAttr("command", command);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, UPDATE_REST_APPLICATIONS_ENDPOINT_PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, projectId))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_APPLICATIONS, restApplications))
                .andExpect(MockMvcResultMatchers.model().attributeExists(UPDATE_REST_APPLICATIONS_ENDPOINT_COMMAND));


        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.any(ReadRestApplicationInput.class));

    }

}
