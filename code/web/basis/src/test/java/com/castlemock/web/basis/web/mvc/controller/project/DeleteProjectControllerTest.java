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

package com.castlemock.web.basis.web.mvc.controller.project;

import com.castlemock.core.basis.model.project.domain.Project;
import com.castlemock.web.basis.config.TestApplication;
import com.castlemock.web.basis.model.project.dto.ProjectDtoGenerator;
import com.castlemock.web.basis.service.project.ProjectServiceFacadeImpl;
import com.castlemock.web.basis.web.mvc.command.project.DeleteProjectsCommand;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.basis.web.mvc.controller.AbstractControllerTest;
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
public class DeleteProjectControllerTest extends AbstractControllerTest {

    private static final String SERVICE_URL = "/web/projectType/project/";
    private static final String PAGE = "partial/basis/project/deleteProject.jsp";
    private static final String DELETE = "/delete";
    private static final String DELETE_CONFIRM = "/delete/confirm";
    private static final String SERVICE_URL_DELETE_MULTIPLE = "/web/project/delete/confirm";

    @InjectMocks
    private DeleteProjectController deleteProjectController;

    @Mock
    private ProjectServiceFacadeImpl projectServiceComponent;

    @Override
    protected AbstractController getController() {
        return deleteProjectController;
    }

    @Test
    public void testDeleteProjectWithValidId() throws Exception {
        final Project projectDto = ProjectDtoGenerator.generateProjectDto();
        when(projectServiceComponent.findOne(anyString(), anyString())).thenReturn(projectDto);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + projectDto.getId() + DELETE);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE));
    }


    @Test
    public void testDeleteConfirmProjectWithValidId() throws Exception {
        final Project projectDto = ProjectDtoGenerator.generateProjectDto();
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + projectDto.getId() + DELETE_CONFIRM);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0));
    }

    @Test
    public void testConfirmDeletationOfMultpleProjects() throws Exception {
        final String[] projects = {"Project"};
        final String[] types = {"SOAP"};

        final DeleteProjectsCommand command = new DeleteProjectsCommand();
        command.setProjectIds(projects);
        command.setTypeUrls(types);

        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL_DELETE_MULTIPLE).flashAttr("deleteProjectsCommand", command);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/web"));
    }

}
