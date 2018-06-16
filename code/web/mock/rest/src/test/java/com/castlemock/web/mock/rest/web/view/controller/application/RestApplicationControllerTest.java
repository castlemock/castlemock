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
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.model.project.service.message.input.ReadRestApplicationInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.ReadRestResourceInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ReadRestApplicationOutput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ReadRestResourceOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.rest.config.TestApplication;
import com.castlemock.web.mock.rest.model.project.RestApplicationGenerator;
import com.castlemock.web.mock.rest.model.project.RestProjectGenerator;
import com.castlemock.web.mock.rest.web.view.command.resource.RestResourceModifierCommand;
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
public class RestApplicationControllerTest extends AbstractRestControllerTest {

    private static final String PAGE = "partial/mock/rest/application/restApplication.jsp";
    private static final String UPDATE_REST_RESOURCES_ENDPOINT_PAGE = "partial/mock/rest/resource/updateRestResourcesEndpoint.jsp";
    private static final String UPDATE_REST_RESOURCES_ENDPOINT_COMMAND = "updateRestResourcesEndpointCommand";
    private static final String DELETE_REST_RESOURCES_COMMAND = "deleteRestResourcesCommand";
    private static final String DELETE_REST_RESOURCES_PAGE = "partial/mock/rest/resource/deleteRestResources.jsp";
    private static final String REST_RESOURCES = "restResources";


    @InjectMocks
    private RestApplicationController restApplicationController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return restApplicationController;
    }

    @Test
    public void getRestApplication() throws Exception {
        final RestProject restProject = RestProjectGenerator.generateRestProject();
        final RestApplication restApplication = RestApplicationGenerator.generateRestApplication();
        when(serviceProcessor.process(any(ReadRestApplicationInput.class))).thenReturn(new ReadRestApplicationOutput(restApplication));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId());
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, restProject.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_APPLICATION, restApplication));
    }

    @Test
    public void projectFunctionalityUpdate() throws Exception {
        final String projectId = "projectId";
        final String applicationId = "applicationId";
        final String[] restResources = {"restApplication1", "restApplication2"};

        final RestResourceModifierCommand restResourceModifierCommand = new RestResourceModifierCommand();
        restResourceModifierCommand.setRestResourceIds(restResources);
        restResourceModifierCommand.setRestMethodStatus("MOCKED");

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH + APPLICATION + SLASH + applicationId)
                        .param("action", "update").flashAttr("restResourceModifierCommand", restResourceModifierCommand);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/web/rest/project/" + projectId + "/application/" + applicationId));

        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.any(ReadRestResourceInput.class));

    }

    @Test
    public void projectFunctionalityDelete() throws Exception {
        final String projectId = "projectId";
        final String applicationId = "applicationId";
        final String[] restResourceIds = {"restApplication1", "restApplication2"};

        final RestResource restResource1 = new RestResource();
        restResource1.setName("restResource1");

        final RestResource restResource2 = new RestResource();
        restResource2.setName("restResource2");

        final List<RestResource> restResources = Arrays.asList(restResource1, restResource2);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadRestResourceInput.class)))
                .thenReturn(new ReadRestResourceOutput(restResource1))
                .thenReturn(new ReadRestResourceOutput(restResource2));


        final RestResourceModifierCommand restResourceModifierCommand = new RestResourceModifierCommand();
        restResourceModifierCommand.setRestResourceIds(restResourceIds);

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH + APPLICATION + SLASH + applicationId)
                        .param("action", "delete").flashAttr("restResourceModifierCommand", restResourceModifierCommand);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, DELETE_REST_RESOURCES_PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, projectId))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_APPLICATION_ID, applicationId))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_RESOURCES, restResources))
                .andExpect(MockMvcResultMatchers.model().attributeExists(DELETE_REST_RESOURCES_COMMAND));


        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.any(ReadRestResourceInput.class));

    }

    @Test
    public void projectFunctionalityUpdateEndpoint() throws Exception {
        final String projectId = "projectId";
        final String applicationId = "applicationId";
        final String[] restResourceIds = {"restApplication1", "restApplication2"};

        final RestResource restResource1 = new RestResource();
        restResource1.setName("restResource1");

        final RestResource restResource2 = new RestResource();
        restResource2.setName("restResource2");

        final List<RestResource> restResources = Arrays.asList(restResource1, restResource2);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadRestResourceInput.class)))
                .thenReturn(new ReadRestResourceOutput(restResource1))
                .thenReturn(new ReadRestResourceOutput(restResource2));


        final RestResourceModifierCommand restResourceModifierCommand = new RestResourceModifierCommand();
        restResourceModifierCommand.setRestResourceIds(restResourceIds);

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH + APPLICATION + SLASH + applicationId)
                        .param("action", "update-endpoint").flashAttr("restResourceModifierCommand", restResourceModifierCommand);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, UPDATE_REST_RESOURCES_ENDPOINT_PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, projectId))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_APPLICATION_ID, applicationId))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_RESOURCES, restResources))
                .andExpect(MockMvcResultMatchers.model().attributeExists(UPDATE_REST_RESOURCES_ENDPOINT_COMMAND));


        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.any(ReadRestApplicationInput.class));

    }
}
