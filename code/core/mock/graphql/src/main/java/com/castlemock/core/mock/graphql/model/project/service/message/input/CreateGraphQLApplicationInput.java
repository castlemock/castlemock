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

package com.castlemock.core.mock.graphql.model.project.service.message.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLApplicationDto;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public class CreateGraphQLApplicationInput implements Input {

    @NotNull
    private String graphQLProjectId;
    @NotNull
    private GraphQLApplicationDto graphQLApplication;

    public CreateGraphQLApplicationInput(String graphQLProjectId, GraphQLApplicationDto graphQLApplication) {
        this.graphQLProjectId = graphQLProjectId;
        this.graphQLApplication = graphQLApplication;
    }

    public String getGraphQLProjectId() {
        return graphQLProjectId;
    }

    public void setGraphQLProjectId(String graphQLProjectId) {
        this.graphQLProjectId = graphQLProjectId;
    }

    public GraphQLApplicationDto getGraphQLApplication() {
        return graphQLApplication;
    }

    public void setGraphQLApplication(GraphQLApplicationDto graphQLApplication) {
        this.graphQLApplication = graphQLApplication;
    }
}
