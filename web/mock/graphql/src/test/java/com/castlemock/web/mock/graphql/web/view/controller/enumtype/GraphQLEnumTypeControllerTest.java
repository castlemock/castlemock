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

package com.castlemock.web.mock.graphql.web.view.controller.enumtype;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLEnumType;
import com.castlemock.core.mock.graphql.service.project.input.ReadGraphQLEnumTypeInput;
import com.castlemock.core.mock.graphql.service.project.output.ReadGraphQLEnumTypeOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.graphql.config.TestApplication;
import com.castlemock.web.mock.graphql.model.project.GraphQLEnumTypeGenerator;
import com.castlemock.web.mock.graphql.web.view.AbstractGraphQLViewControllerTest;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class GraphQLEnumTypeControllerTest extends AbstractGraphQLViewControllerTest {

    private static final String PAGE = "partial/mock/graphql/enumtype/graphQLEnumType.jsp";

    @InjectMocks
    private GraphQLEnumTypeController controller;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return controller;
    }

    @Test
    public void testGet() throws Exception {
        final String projectId = "ProjectId";
        final GraphQLEnumType graphQLEnumType = GraphQLEnumTypeGenerator.generateGraphQLEnumType();
        when(serviceProcessor.process(any(ReadGraphQLEnumTypeInput.class))).thenReturn(new ReadGraphQLEnumTypeOutput(graphQLEnumType));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH +
                projectId + SLASH + APPLICATION + SLASH + graphQLEnumType.getApplicationId() +
                SLASH + GRAPHQL_ENUM + SLASH + graphQLEnumType.getId());

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(GRAPHQL_APPLICATION_ID, graphQLEnumType.getApplicationId()))
                .andExpect(MockMvcResultMatchers.model().attribute(GRAPHQL_ENUM_TYPE, graphQLEnumType));

    }

}
