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

import org.dozer.Mapping;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GraphQLEnumType extends GraphQLType {

    @Mapping("definitions")
    private List<GraphQLEnumValueDefinition> definitions
            = new CopyOnWriteArrayList<GraphQLEnumValueDefinition>();


    public List<GraphQLEnumValueDefinition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<GraphQLEnumValueDefinition> definitions) {
        this.definitions = definitions;
    }
}
