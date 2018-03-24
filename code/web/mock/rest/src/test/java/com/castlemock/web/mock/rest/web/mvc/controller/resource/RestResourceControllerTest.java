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

package com.castlemock.web.mock.rest.web.mvc.controller.resource;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.rest.model.project.dto.RestApplicationDto;
import com.castlemock.core.mock.rest.model.project.dto.RestMethodDto;
import com.castlemock.core.mock.rest.model.project.dto.RestProjectDto;
import com.castlemock.core.mock.rest.model.project.dto.RestResourceDto;
import com.castlemock.core.mock.rest.model.project.service.message.input.ReadRestMethodInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.ReadRestResourceInput;
import com.castlemock.core.mock.rest.model.project.service.message.input.UpdateRestMethodInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ReadRestMethodOutput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ReadRestResourceOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.rest.config.TestApplication;
import com.castlemock.web.mock.rest.model.project.RestApplicationDtoGenerator;
import com.castlemock.web.mock.rest.model.project.RestProjectDtoGenerator;
import com.castlemock.web.mock.rest.model.project.RestResourceDtoGenerator;
import com.castlemock.web.mock.rest.web.mvc.command.method.RestMethodModifierCommand;
import com.castlemock.web.mock.rest.web.mvc.controller.AbstractRestControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.SpringApplicationConfiguration;
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
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class RestResourceControllerTest extends AbstractRestControllerTest {

    private static final String PAGE = "partial/mock/rest/resource/restResource.jsp";
    private static final String SLASH = "/";
    private static final String DELETE_REST_METHODS_PAGE = "partial/mock/rest/method/deleteRestMethods.jsp";
    private static final String DELETE_REST_METHODS_COMMAND = "deleteRestMethodsCommand";
    private static final String UPDATE_REST_METHODS_ENDPOINT_PAGE = "partial/mock/rest/method/updateRestMethodsEndpoint.jsp";
    private static final String UPDATE_REST_METHODS_ENDPOINT_COMMAND = "updateRestMethodsEndpointCommand";
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
        final RestProjectDto restProjectDto = RestProjectDtoGenerator.generateRestProjectDto();
        final RestApplicationDto restApplicationDto = RestApplicationDtoGenerator.generateRestApplicationDto();
        final RestResourceDto restResourceDto = RestResourceDtoGenerator.generateRestResourceDto();
        when(serviceProcessor.process(isA(ReadRestResourceInput.class))).thenReturn(new ReadRestResourceOutput(restResourceDto));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + restProjectDto.getId() + SLASH + APPLICATION + SLASH + restApplicationDto.getId() + SLASH + RESOURCE + SLASH + restResourceDto.getId());
        ResultActions result = mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, restProjectDto.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_APPLICATION_ID, restApplicationDto.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_RESOURCE, restResourceDto));
        RestResourceDto restResourceDtoResponse = (RestResourceDto) result.andReturn().getModelAndView().getModel().get(REST_RESOURCE);
        String hostAddress = restResourceController.getHostAddress();
    }

    @Test
    public void projectFunctionalityUpdate() throws Exception {
        final String projectId = "projectId";
        final String applicationId = "applicationId";
        final String resourceId = "resourceId";
        final String[] restMethodIds = {"restMethod1", "restMethod2"};

        final RestMethodModifierCommand restMethodModifierCommand = new RestMethodModifierCommand();
        restMethodModifierCommand.setRestMethodIds(restMethodIds);
        restMethodModifierCommand.setRestMethodStatus("MOCKED");

        final RestMethodDto restMethod1 = new RestMethodDto();
        restMethod1.setName("restMethod1");

        final RestMethodDto restMethod2 = new RestMethodDto();
        restMethod2.setName("restMethod2");

        Mockito.when(serviceProcessor.process(Mockito.any(ReadRestMethodInput.class)))
                .thenReturn(new ReadRestMethodOutput(restMethod1))
                .thenReturn(new ReadRestMethodOutput(restMethod2));

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH + APPLICATION + SLASH + applicationId + SLASH + RESOURCE + SLASH + resourceId)
                        .param("action", "update").flashAttr("restMethodModifierCommand", restMethodModifierCommand);

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

        final RestMethodDto restMethod1 = new RestMethodDto();
        restMethod1.setName("restMethod1");

        final RestMethodDto restMethod2 = new RestMethodDto();
        restMethod2.setName("restMethod2");

        final List<RestMethodDto> restMethods = Arrays.asList(restMethod1, restMethod2);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadRestMethodInput.class)))
                .thenReturn(new ReadRestMethodOutput(restMethod1))
                .thenReturn(new ReadRestMethodOutput(restMethod2));

        final RestMethodModifierCommand restMethodModifierCommand = new RestMethodModifierCommand();
        restMethodModifierCommand.setRestMethodIds(restMethodIds);

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH + APPLICATION + SLASH + applicationId + SLASH + RESOURCE + SLASH + resourceId)
                        .param("action", "delete").flashAttr("restMethodModifierCommand", restMethodModifierCommand);

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

        final RestMethodDto restMethod1 = new RestMethodDto();
        restMethod1.setName("restMethod1");

        final RestMethodDto restMethod2 = new RestMethodDto();
        restMethod2.setName("restMethod2");

        final List<RestMethodDto> restMethods = Arrays.asList(restMethod1, restMethod2);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadRestMethodInput.class)))
                .thenReturn(new ReadRestMethodOutput(restMethod1))
                .thenReturn(new ReadRestMethodOutput(restMethod2));

        final RestMethodModifierCommand restMethodModifierCommand = new RestMethodModifierCommand();
        restMethodModifierCommand.setRestMethodIds(restMethodIds);

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH + APPLICATION + SLASH + applicationId + SLASH + RESOURCE + SLASH + resourceId)
                        .param("action", "update-endpoint").flashAttr("restMethodModifierCommand", restMethodModifierCommand);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(6 + GLOBAL_VIEW_MODEL_COUNT))
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
