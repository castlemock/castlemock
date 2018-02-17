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

import com.castlemock.web.mock.graphql.converter.query.GraphQLRequestQuery;
import com.castlemock.web.mock.graphql.converter.query.QueryGraphQLConverter;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class QueryGraphQLConverterTest {

    private static final String QUERY = "{\n" +
            "  film(id: 3){\n" +
            "    title\n" +
            "  }\n" +
            "  \n" +
            "  allFilms {\n" +
            "    films {\n" +
            "      id\n" +
            "      title\n" +
            "    }\n" +
            "  }\n" +
            "}";

    @Test
    public void testQueryParser(){
        final QueryGraphQLConverter converter = new QueryGraphQLConverter();
        final List<GraphQLRequestQuery> queries = converter.parseQuery(QUERY);


        Assert.assertNotNull(queries);
    }

}
