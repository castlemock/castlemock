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

package com.castlemock.web.mock.graphql.web.view.command.project;

/**
 * The GraphQLApplicationModifierCommand is used when the user want to set the same status
 * to multiple GraphQL applications
 * @author Karl Dahlgren
 * @since 1.19
 */
public class GraphQLApplicationModifierCommand {

    private String graphQLOperationStatus;
    private String[] graphQLApplicationIds;

    public String getGraphQLOperationStatus() {
        return graphQLOperationStatus;
    }

    public void setGraphQLOperationStatus(String graphQLOperationStatus) {
        this.graphQLOperationStatus = graphQLOperationStatus;
    }

    public String[] getGraphQLApplicationIds() {
        return graphQLApplicationIds;
    }

    public void setGraphQLApplicationIds(String[] graphQLApplicationIds) {
        this.graphQLApplicationIds = graphQLApplicationIds;
    }
}
