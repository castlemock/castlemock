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
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLApplication;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public class UpdateGraphQLApplicationInput implements Input {

    @NotNull
    private String graphQLProjectId;
    @NotNull
    private String graphQLApplicationId;
    @NotNull
    private GraphQLApplication graphQLApplication;

    public UpdateGraphQLApplicationInput(String graphQLProjectId,
                                         String graphQLApplicationId,
                                         GraphQLApplication graphQLApplication) {
        this.graphQLProjectId = graphQLProjectId;
        this.graphQLApplicationId = graphQLApplicationId;
        this.graphQLApplication = graphQLApplication;
    }

    public String getGraphQLProjectId() {
        return graphQLProjectId;
    }

    public void setGraphQLProjectId(String graphQLProjectId) {
        this.graphQLProjectId = graphQLProjectId;
    }

    public String getGraphQLApplicationId() {
        return graphQLApplicationId;
    }

    public void setGraphQLApplicationId(String graphQLApplicationId) {
        this.graphQLApplicationId = graphQLApplicationId;
    }

    public GraphQLApplication getGraphQLApplication() {
        return graphQLApplication;
    }

    public void setGraphQLApplication(GraphQLApplication graphQLApplication) {
        this.graphQLApplication = graphQLApplication;
    }
}
