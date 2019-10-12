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
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestApplicationTestBuilder;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.domain.RestProjectTestBuilder;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.model.project.domain.RestResourceTestBuilder;
import com.castlemock.core.mock.rest.service.project.input.CreateRestResourceInput;
import com.castlemock.core.mock.rest.service.project.input.ReadRestApplicationInput;
import com.castlemock.core.mock.rest.service.project.output.CreateRestResourceOutput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestApplicationOutput;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


/**
 * @author: Karl Dahlgren
 * @since: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class CreateRestResourceControllerTest extends AbstractRestControllerTest {

    private static final String PAGE = "partial/mock/rest/resource/createRestResource.jsp";
    private static final String CREATE_RESOURCE = "create/resource";

    @InjectMocks
    private CreateRestResourceController createRestResourceController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return createRestResourceController;
    }

    @Test
    public void testCreateMethod() throws Exception {
        final RestProject restProject = RestProjectTestBuilder.builder().build();
        final RestApplication restApplication = RestApplicationTestBuilder.builder().build();
        when(serviceProcessor.process(any(ReadRestApplicationInput.class))).thenReturn(ReadRestApplicationOutput.builder()
                .restApplication(restApplication).build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL +
                PROJECT + SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() +
                SLASH + CREATE_RESOURCE);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, restProject.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_APPLICATION_ID, restApplication.getId()))
                .andExpect(MockMvcResultMatchers.model().attributeExists(REST_RESOURCE));
    }

    @Test
    public void testPostCreateMethod() throws Exception {
        final RestProject restProject = RestProjectTestBuilder.builder().build();
        final RestApplication restApplication = RestApplicationTestBuilder.builder().build();
        final RestResource restResource = RestResourceTestBuilder.builder().build();
        when(serviceProcessor.process(any(CreateRestResourceInput.class))).thenReturn(CreateRestResourceOutput
                .builder().savedRestApplication(restResource)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + PROJECT +
                SLASH + restProject.getId() + SLASH + APPLICATION + SLASH + restApplication.getId() + SLASH +
                CREATE_RESOURCE);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }

}
