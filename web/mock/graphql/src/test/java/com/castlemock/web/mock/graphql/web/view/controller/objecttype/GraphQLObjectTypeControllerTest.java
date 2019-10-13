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

package com.castlemock.web.mock.graphql.web.view.controller.objecttype;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLApplication;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLObjectType;
import com.castlemock.core.mock.graphql.service.project.input.ReadGraphQLObjectTypeInput;
import com.castlemock.core.mock.graphql.service.project.input.ReadGraphQLProjectInput;
import com.castlemock.core.mock.graphql.service.project.output.ReadGraphQLApplicationOutput;
import com.castlemock.core.mock.graphql.service.project.output.ReadGraphQLObjectTypeOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.graphql.config.TestApplication;
import com.castlemock.web.mock.graphql.model.project.GraphQLApplicationGenerator;
import com.castlemock.web.mock.graphql.model.project.GraphQLObjectTypeGenerator;
import com.castlemock.web.mock.graphql.web.view.AbstractGraphQLViewControllerTest;
import com.castlemock.web.mock.graphql.web.view.controller.application.GraphQLApplicationController;
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
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class GraphQLObjectTypeControllerTest extends AbstractGraphQLViewControllerTest {

    private static final String PAGE = "partial/mock/graphql/objecttype/graphQLObjectType.jsp";

    @InjectMocks
    private GraphQLObjectTypeController controller;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return controller;
    }

    @Test
    public void testGet() throws Exception {
        final String projectId = "ProjectId";
        final GraphQLObjectType graphQLObjectType = GraphQLObjectTypeGenerator.generateGraphQLObjectType();
        when(serviceProcessor.process(any(ReadGraphQLObjectTypeInput.class))).thenReturn(new ReadGraphQLObjectTypeOutput(graphQLObjectType));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH +
                projectId + SLASH + APPLICATION + SLASH + graphQLObjectType.getApplicationId() +
                SLASH + GRAPHQL_OBJECT + SLASH + graphQLObjectType.getId());

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(GRAPHQL_APPLICATION_ID, graphQLObjectType.getApplicationId()))
                .andExpect(MockMvcResultMatchers.model().attribute(GRAPHQL_OBJECT_TYPE, graphQLObjectType));

    }

}
