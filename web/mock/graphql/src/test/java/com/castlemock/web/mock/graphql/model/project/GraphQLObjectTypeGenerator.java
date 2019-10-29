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

package com.castlemock.web.mock.graphql.model.project;

import com.castlemock.core.mock.graphql.model.project.domain.*;

import java.util.ArrayList;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class GraphQLObjectTypeGenerator {

    public static GraphQLObjectType generateGraphQLObjectType(){
        final GraphQLObjectType objectType = new GraphQLObjectType();
        objectType.setId("GRAPHQL OBJECT TYPE");
        objectType.setName("Object type name");
        objectType.setDescription("Description");
        objectType.setApplicationId("ApplicationId");
        objectType.setAttributes(new ArrayList<GraphQLAttribute>());
        return objectType;
    }

}
