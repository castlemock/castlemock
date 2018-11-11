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

package com.castlemock.web.mock.rest.web.view.controller.method;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.service.project.input.ReadRestMethodInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestMethodsForwardedEndpointInput;
import com.castlemock.core.mock.rest.service.project.input.UpdateRestResourceInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestMethodOutput;
import com.castlemock.core.mock.rest.service.project.output.UpdateRestResourceOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.rest.config.TestApplication;
import com.castlemock.web.mock.rest.model.project.RestApplicationGenerator;
import com.castlemock.web.mock.rest.model.project.RestMethodGenerator;
import com.castlemock.web.mock.rest.model.project.RestProjectGenerator;
import com.castlemock.web.mock.rest.model.project.RestResourceGenerator;
import com.castlemock.web.mock.rest.web.view.command.method.UpdateRestMethodsEndpointCommand;
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
 * @author: Karl Dahlgren
 * @since: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class UpdateRestMethodControllerTest extends AbstractRestControllerTest {

    private static final String PAGE = "partial/mock/rest/method/updateRestMethod.jsp";
    private static final String UPDATE = "update";


    @InjectMocks
    private UpdateRestMethodController updateRestMethodController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return updateRestMethodController;
    }

    @Test
    public void testUpdateMethod() throws Exception {
        final RestProject restProject = RestProjectGenerator.generateRestProject();
        final RestApplication restApplication = RestApplicationGenerator.generateRestApplication();
        final RestResource restResource = RestResourceGenerator.generateRestResource();
        final RestMethod restMethod = RestMethodGenerator.generateRestMethod();
        when(serviceProcessor.process(any(ReadRestMethodInput.class))).thenReturn(ReadRestMethodOutput.builder()
                .restMethod(restMethod)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT +
                SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() + SLASH +
                RESOURCE + SLASH + restResource.getId() + SLASH + METHOD + SLASH + restMethod.getId() + SLASH + UPDATE);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(8 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, restProject.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_APPLICATION_ID, restApplication.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_RESOURCE_ID, restResource.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_METHOD, restMethod));
    }

    @Test
    public void testUpdateRestMethod() throws Exception {
        final RestProject restProject = RestProjectGenerator.generateRestProject();
        final RestApplication restApplication = RestApplicationGenerator.generateRestApplication();
        final RestResource restResource = RestResourceGenerator.generateRestResource();
        final RestMethod restMethod = RestMethodGenerator.generateRestMethod();
        when(serviceProcessor.process(any(UpdateRestResourceInput.class))).thenReturn(UpdateRestResourceOutput.builder()
                .updatedRestResource(restResource)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + PROJECT +
                SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() + SLASH +
                RESOURCE + SLASH + restResource.getId() + SLASH + METHOD + SLASH + restMethod.getId() + SLASH + UPDATE);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }

    @Test
    public void testUpdateEndpoint() throws Exception {
        final String projectId = "projectId";
        final String applicationId = "applicationId";
        final String resourceId = "resourceId";

        final UpdateRestMethodsEndpointCommand command = new UpdateRestMethodsEndpointCommand();
        command.setForwardedEndpoint("http://localhost:8080/web");
        command.setRestMethods(ImmutableList.of());

        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + PROJECT +
                SLASH + projectId + SLASH + APPLICATION + SLASH + applicationId + SLASH + RESOURCE + SLASH +
                resourceId + SLASH + METHOD + SLASH + "update/confirm")
                .flashAttr("command", command);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/web/rest/project/" + projectId + "/application/" + applicationId + "/resource/" + resourceId));

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(UpdateRestMethodsForwardedEndpointInput.class));
    }

}
