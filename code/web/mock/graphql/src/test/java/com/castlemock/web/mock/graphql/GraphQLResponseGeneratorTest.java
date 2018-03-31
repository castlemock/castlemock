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

package com.castlemock.web.mock.graphql;

import com.castlemock.core.mock.graphql.model.project.dto.GraphQLApplicationDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLProjectDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLRequestQueryDto;
import com.castlemock.web.mock.graphql.converter.GraphQLDefinitionConverterResult;
import com.castlemock.web.mock.graphql.converter.query.QueryGraphQLConverter;
import com.castlemock.web.mock.graphql.converter.schema.SchemaGraphQLDefinitionConverter;
import com.castlemock.web.mock.graphql.web.graphql.controller.GraphQLResponseGenerator;
import org.junit.Test;

import java.util.List;

public class GraphQLResponseGeneratorTest {

    private static final String QUERY = "{\n" +
            "  film(id: 3){\n" +
            "    title\n" +
            "    id\n" +
            "  }\n" +
            "  actor(id: 10){\n" +
            "    name\n" +
            "    status\n" +
            "  }\n" +
            "}";

    private static final String SCHEMA = "type Film {\n" +
            "  id: ID\n" +
            "  title: String\n" +
            "  actor: Actor\n" +
            "}\n" +
            "type Actor {\n" +
            "  name: String\n" +
            "  birthYear: Int\n" +
            "  status: [Status]\n" +
            "}\n" +
            "enum Status {\n" +
            "  ACTIVE \n" +
            "  INACTIVE\n" +
            "}\n" +
            "type Query {\n" +
            "  film(id: Int): Film\n" +
            "  actor: [Actor]!\n" +
            "  test(id: Int): String!\n" +
            "}";

    @Test
    public void test(){
        final SchemaGraphQLDefinitionConverter schemaConverter = new SchemaGraphQLDefinitionConverter(null);
        final GraphQLDefinitionConverterResult result = schemaConverter.convertRaw(SCHEMA);
        final GraphQLApplicationDto application = new GraphQLApplicationDto();
        application.setMutations(result.getMutations());
        application.setQueries(result.getQueries());
        application.setSubscriptions(result.getSubscriptions());
        application.setObjects(result.getObjects());
        application.setEnums(result.getEnums());

        final QueryGraphQLConverter queryConverter = new QueryGraphQLConverter();
        final List<GraphQLRequestQueryDto> queries = queryConverter.parseQuery(QUERY);

        GraphQLResponseGenerator generator = new GraphQLResponseGenerator();
        final String output = generator.getResponse(application, queries);

        System.out.println(output);
    }

}
