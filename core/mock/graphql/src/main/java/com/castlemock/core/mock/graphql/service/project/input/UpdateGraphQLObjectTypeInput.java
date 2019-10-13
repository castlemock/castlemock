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

package com.castlemock.core.mock.graphql.service.project.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLObjectType;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public final class UpdateGraphQLObjectTypeInput implements Input {

    @NotNull
    private final String projectId;

    @NotNull
    private final String applicationId;

    @NotNull
    private final String objectTypeId;

    @NotNull
    private final GraphQLObjectType objectType;


    public UpdateGraphQLObjectTypeInput(String projectId,
                                        String applicationId,
                                        String objectTypeId,
                                        GraphQLObjectType objectType) {
        this.projectId = projectId;
        this.applicationId = applicationId;
        this.objectTypeId = objectTypeId;
        this.objectType = objectType;
    }

    public GraphQLObjectType getObjectType() {
        return objectType;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getObjectTypeId() {
        return objectTypeId;
    }
}
