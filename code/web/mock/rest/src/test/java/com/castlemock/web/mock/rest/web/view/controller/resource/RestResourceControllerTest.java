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

package com.castlemock.web.mock.rest.web.view.controller.resource;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.rest.model.project.RestApplicationGenerator;
import com.castlemock.core.mock.rest.model.project.RestProjectGenerator;
import com.castlemock.core.mock.rest.model.project.RestResourceGenerator;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.service.project.input.ReadRestMethodInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestResourceInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestMethodOutput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestResourceOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.rest.config.TestApplication;
import com.castlemock.web.mock.rest.web.view.command.method.RestMethodModifierCommand;
import com.castlemock.web.mock.rest.web.view.controller.AbstractRestControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class RestResourceControllerTest extends AbstractRestControllerTest {

    private static final String PAGE = "partial/mock/rest/resource/restResource.jsp";
    private static final String SLASH = "/";
    private static final String DELETE_REST_METHODS_PAGE = "partial/mock/rest/method/deleteRestMethods.jsp";
    private static final String DELETE_REST_METHODS_COMMAND = "command";
    private static final String UPDATE_REST_METHODS_ENDPOINT_PAGE = "partial/mock/rest/method/updateRestMethodsEndpoint.jsp";
    private static final String UPDATE_REST_METHODS_ENDPOINT_COMMAND = "command";
    protected static final String REST_METHODS = "restMethods";

    @InjectMocks
    private RestResourceController restResourceController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return restResourceController;
    }

    @Test
    public void testGetServiceValid() throws Exception {
        final RestProject restProject = RestProjectGenerator.generateRestProject();
        final RestApplication restApplication = RestApplicationGenerator.generateRestApplication();
        final RestResource restResource = RestResourceGenerator.generateRestResource();
        when(serviceProcessor.process(isA(ReadRestResourceInput.class))).thenReturn(ReadRestResourceOutput.builder().restResource(restResource).build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT +
                SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() +
                SLASH + RESOURCE + SLASH + restResource.getId());
        ResultActions result = mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, restProject.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_APPLICATION_ID, restApplication.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_RESOURCE, restResource));
        RestResource restResourceResponse = (RestResource) result.andReturn().getModelAndView().getModel().get(REST_RESOURCE);
        String hostAddress = restResourceController.getHostAddress();
    }

    @Test
    public void projectFunctionalityUpdate() throws Exception {
        final String projectId = "projectId";
        final String applicationId = "applicationId";
        final String resourceId = "resourceId";
        final String[] restMethodIds = {"restMethod1", "restMethod2"};

        final RestMethodModifierCommand command = new RestMethodModifierCommand();
        command.setRestMethodIds(restMethodIds);
        command.setRestMethodStatus("MOCKED");

        final RestMethod restMethod1 = new RestMethod();
        restMethod1.setId("restMethod1");
        restMethod1.setName("restMethod1");

        final RestMethod restMethod2 = new RestMethod();
        restMethod2.setId("restMethod2");
        restMethod2.setName("restMethod2");

        Mockito.when(serviceProcessor.process(Mockito.any(ReadRestMethodInput.class)))
                .thenReturn(ReadRestMethodOutput.builder().restMethod(restMethod1).build())
                .thenReturn(ReadRestMethodOutput.builder().restMethod(restMethod2).build());

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH + APPLICATION +
                        SLASH + applicationId + SLASH + RESOURCE + SLASH + resourceId)
                        .param("action", "update")
                        .flashAttr("command", command);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/web/rest/project/" + projectId + "/application/" + applicationId + "/resource/" + resourceId));

        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.isA(ReadRestMethodInput.class));
        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.isA(UpdateRestMethodInput.class));
    }

    @Test
    public void projectFunctionalityDelete() throws Exception {
        final String projectId = "projectId";
        final String applicationId = "applicationId";
        final String resourceId = "resourceId";
        final String[] restMethodIds = {"restMethod1", "restMethod2"};

        final RestMethod restMethod1 = new RestMethod();
        restMethod1.setName("restMethod1");

        final RestMethod restMethod2 = new RestMethod();
        restMethod2.setName("restMethod2");

        final List<RestMethod> restMethods = Arrays.asList(restMethod1, restMethod2);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadRestMethodInput.class)))
                .thenReturn(ReadRestMethodOutput.builder().restMethod(restMethod1).build())
                .thenReturn(ReadRestMethodOutput.builder().restMethod(restMethod2).build());

        final RestMethodModifierCommand command = new RestMethodModifierCommand();
        command.setRestMethodIds(restMethodIds);

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH +
                        APPLICATION + SLASH + applicationId + SLASH + RESOURCE + SLASH + resourceId)
                        .param("action", "delete").flashAttr("command", command);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(6 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, DELETE_REST_METHODS_PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, projectId))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_APPLICATION_ID, applicationId))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_RESOURCE_ID, resourceId))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_METHODS, restMethods))
                .andExpect(MockMvcResultMatchers.model().attributeExists(DELETE_REST_METHODS_COMMAND));


        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.any(ReadRestMethodInput.class));

    }

    @Test
    public void projectFunctionalityUpdateEndpoint() throws Exception {
        final String projectId = "projectId";
        final String applicationId = "applicationId";
        final String resourceId = "resourceId";
        final String[] restMethodIds = {"restMethod1", "restMethod2"};

        final RestMethod restMethod1 = new RestMethod();
        restMethod1.setName("restMethod1");

        final RestMethod restMethod2 = new RestMethod();
        restMethod2.setName("restMethod2");

        final List<RestMethod> restMethods = Arrays.asList(restMethod1, restMethod2);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadRestMethodInput.class)))
                .thenReturn(ReadRestMethodOutput.builder().restMethod(restMethod1).build())
                .thenReturn(ReadRestMethodOutput.builder().restMethod(restMethod2).build());

        final RestMethodModifierCommand command = new RestMethodModifierCommand();
        command.setRestMethodIds(restMethodIds);

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId +
                        SLASH + APPLICATION + SLASH + applicationId + SLASH + RESOURCE + SLASH + resourceId)
                        .param("action", "update-endpoint").flashAttr("command", command);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, UPDATE_REST_METHODS_ENDPOINT_PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, projectId))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_APPLICATION_ID, applicationId))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_RESOURCE_ID, resourceId))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_METHODS, restMethods))
                .andExpect(MockMvcResultMatchers.model().attributeExists(UPDATE_REST_METHODS_ENDPOINT_COMMAND));


        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.any(ReadRestMethodInput.class));

    }

}
