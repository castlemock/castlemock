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

package com.castlemock.core.mock.graphql.model.project.domain;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public class GraphQLRequestQuery {

    private String id;
    private String operationName;
    private List<GraphQLRequestField> fields = new CopyOnWriteArrayList<GraphQLRequestField>();
    private List<GraphQLRequestArgument> arguments = new CopyOnWriteArrayList<GraphQLRequestArgument>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public List<GraphQLRequestField> getFields() {
        return fields;
    }

    public void setFields(List<GraphQLRequestField> fields) {
        this.fields = fields;
    }

    public List<GraphQLRequestArgument> getArguments() {
        return arguments;
    }

    public void setArguments(List<GraphQLRequestArgument> arguments) {
        this.arguments = arguments;
    }
}
