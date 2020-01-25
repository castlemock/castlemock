/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.mock.graphql.web.view.controller.project;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLApplication;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLProject;
import com.castlemock.core.mock.graphql.service.project.input.ReadGraphQLApplicationInput;
import com.castlemock.core.mock.graphql.service.project.input.ReadGraphQLProjectInput;
import com.castlemock.core.mock.graphql.service.project.output.ReadGraphQLApplicationOutput;
import com.castlemock.core.mock.graphql.service.project.output.ReadGraphQLProjectOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.graphql.config.TestApplication;
import com.castlemock.web.mock.graphql.model.project.GraphQLProjectGenerator;
import com.castlemock.web.mock.graphql.web.view.AbstractGraphQLViewControllerTest;
import com.castlemock.web.mock.graphql.web.view.command.project.GraphQLApplicationModifierCommand;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class GraphQLProjectControllerTest extends AbstractGraphQLViewControllerTest {

    private static final String PAGE = "partial/mock/graphql/project/graphQLProject.jsp";
    private static final String DELETE_GRAPHQL_APPLICATIONS_COMMAND = "deleteGraphQLApplicationsCommand";
    private static final String DELETE_GRAPHQL_APPLICATIONS_PAGE = "partial/mock/graphql/application/deleteGraphQLApplications.jsp";
    private static final String GRAPHQL_APPLICATIONS = "graphQLApplications";

    @InjectMocks
    private GraphQLProjectController restProjectController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return restProjectController;
    }

    @Test
    public void testGetServiceValid() throws Exception {
        final GraphQLProject graphQLProject = GraphQLProjectGenerator.generateGraphQLProject();
        when(serviceProcessor.process(any(ReadGraphQLProjectInput.class))).thenReturn(new ReadGraphQLProjectOutput(graphQLProject));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + graphQLProject.getId() + SLASH);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(GRAPHQL_PROJECT, graphQLProject));

    }

    @Test
    public void testGetServiceValidUploadSuccess() throws Exception {
        final GraphQLProject restProject = GraphQLProjectGenerator.generateGraphQLProject();
        when(serviceProcessor.process(any(ReadGraphQLProjectInput.class))).thenReturn(new ReadGraphQLProjectOutput(restProject));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + restProject.getId() + SLASH)
                .param("upload", "success");
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute("upload", "success"))
                .andExpect(MockMvcResultMatchers.model().attribute(GRAPHQL_PROJECT, restProject));

    }

    @Test
    public void testGetServiceValidUploadError() throws Exception {
        final GraphQLProject restProject = GraphQLProjectGenerator.generateGraphQLProject();
        when(serviceProcessor.process(any(ReadGraphQLProjectInput.class))).thenReturn(new ReadGraphQLProjectOutput(restProject));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + restProject.getId() + SLASH)
                .param("upload", "error");
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute("upload", "error"))
                .andExpect(MockMvcResultMatchers.model().attribute(GRAPHQL_PROJECT, restProject));

    }

    @Test
    public void projectFunctionalityDelete() throws Exception {
        final String projectId = "projectId";
        final String[] graphQLApplicationIds = {"graphQLApplication1", "graphQLApplication2"};

        final GraphQLApplication graphQLApplication1 = new GraphQLApplication();
        graphQLApplication1.setName("graphQLApplication1");

        final GraphQLApplication graphQLApplication2 = new GraphQLApplication();
        graphQLApplication2.setName("graphQLApplication2");

        final List<GraphQLApplication> graphQLApplications = Arrays.asList(graphQLApplication1, graphQLApplication2);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadGraphQLApplicationInput.class)))
                .thenReturn(new ReadGraphQLApplicationOutput(graphQLApplication1))
                .thenReturn(new ReadGraphQLApplicationOutput(graphQLApplication2));

        final GraphQLApplicationModifierCommand command = new GraphQLApplicationModifierCommand();
        command.setGraphQLApplicationIds(graphQLApplicationIds);

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH)
                        .param("action", "delete").flashAttr("graphQLApplicationModifierCommand", command);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, DELETE_GRAPHQL_APPLICATIONS_PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(GRAPHQL_PROJECT_ID, projectId))
                .andExpect(MockMvcResultMatchers.model().attribute(GRAPHQL_APPLICATIONS, graphQLApplications))
                .andExpect(MockMvcResultMatchers.model().attributeExists(DELETE_GRAPHQL_APPLICATIONS_COMMAND));

        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.any(ReadGraphQLApplicationInput.class));
    }

}
