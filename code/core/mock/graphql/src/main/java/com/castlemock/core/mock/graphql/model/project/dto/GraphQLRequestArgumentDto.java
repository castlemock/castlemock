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

package com.castlemock.core.mock.graphql.model.project.dto;

import org.dozer.Mapping;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public class GraphQLRequestArgumentDto {

    @Mapping("name")
    private String name;

    @Mapping("arguments")
    private List<GraphQLRequestArgumentDto> arguments = new CopyOnWriteArrayList<GraphQLRequestArgumentDto>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GraphQLRequestArgumentDto> getArguments() {
        return arguments;
    }

    public void setArguments(List<GraphQLRequestArgumentDto> arguments) {
        this.arguments = arguments;
    }
}
