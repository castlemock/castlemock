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

package com.castlemock.core.mock.graphql.service.event.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.mock.graphql.model.event.domain.GraphQLEvent;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public class CreateGraphQLEventInput implements Input {

    private GraphQLEvent graphQLEvent;

    public CreateGraphQLEventInput(GraphQLEvent graphQLEvent) {
        this.graphQLEvent = graphQLEvent;
    }

    public GraphQLEvent getGraphQLEvent() {
        return graphQLEvent;
    }

    public void setGraphQLEvent(GraphQLEvent graphQLEvent) {
        this.graphQLEvent = graphQLEvent;
    }
}
