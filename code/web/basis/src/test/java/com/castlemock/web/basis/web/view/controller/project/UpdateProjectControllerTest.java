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

package com.castlemock.web.basis.web.view.controller.project;

import com.castlemock.core.basis.model.project.domain.Project;
import com.castlemock.core.basis.model.project.domain.ProjectTestBuilder;
import com.castlemock.web.basis.config.TestApplication;
import com.castlemock.web.basis.service.project.ProjectServiceFacadeImpl;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.basis.web.AbstractControllerTest;
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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class UpdateProjectControllerTest extends AbstractControllerTest {

    private static final String SERVICE_URL = "/web/projectType/project/";
    private static final String PAGE = "partial/basis/project/updateProject.jsp";
    private static final String UPDATE = "/update";
    private static final String PROJECT = "project";

    @InjectMocks
    private UpdateProjectController updateProjectController;

    @Mock
    private ProjectServiceFacadeImpl projectServiceComponent;

    @Override
    protected AbstractController getController() {
        return updateProjectController;
    }

    @Test
    public void testUpdateProjectWithValidId() throws Exception {
        final Project projectDto = ProjectTestBuilder.builder().build();
        when(projectServiceComponent.findOne(anyString(), anyString())).thenReturn(projectDto);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + projectDto.getId() + UPDATE);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(PROJECT, projectDto));
    }

    @Test
    public void testUpdateProjectConfirmWithValidId() throws Exception {
        final Project projectDto = ProjectTestBuilder.builder().build();
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + projectDto.getId() + UPDATE);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }
}
