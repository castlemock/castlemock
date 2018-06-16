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
import com.castlemock.core.mock.graphql.model.GraphQLDefinitionType;

import java.io.File;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public class ImportGraphQLDefinitionInput implements Input {

    @NotNull
    private String graphQLProjectId;
    @NotNull
    private String graphQLApplicationId;
    @NotNull
    private GraphQLDefinitionType definitionType;

    private List<File> files;
    private String location;

    public ImportGraphQLDefinitionInput(final String graphQLProjectId,
                                        final String graphQLApplicationId,
                                        final List<File> files, String location,
                                        final GraphQLDefinitionType definitionType) {
        this.graphQLProjectId = graphQLProjectId;
        this.graphQLApplicationId = graphQLApplicationId;
        this.files = files;
        this.location = location;
        this.definitionType = definitionType;
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

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public GraphQLDefinitionType getDefinitionType() {
        return definitionType;
    }

    public void setDefinitionType(GraphQLDefinitionType definitionType) {
        this.definitionType = definitionType;
    }
}
