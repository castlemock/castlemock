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

import com.castlemock.web.mock.graphql.converter.GraphQLDefinitionConverterResult;
import com.castlemock.web.mock.graphql.converter.schema.SchemaGraphQLDefinitionConverter;
import org.junit.Test;

public class SchemaGraphQLDefinitionConverterTest {

    private static final String SCHEMA = "type Product {\n" +
            "  id: ID!\n" +
            "  name: String!\n" +
            "  shortDescription: String\n" +
            "}\n" +
            "type Variant {\n" +
            "  id: ID!\n" +
            "  name: String!\n" +
            "  shortDescription: String\n" +
            "}\n" +
            "type ProductList {\n" +
            "  id: ID!\n" +
            "  products: [Product]!\n" +
            "}\n" +
            "enum Episode {\n" +
            "  NEWHOPE\n" +
            "  EMPIRE\n" +
            "  JEDI\n" +
            "}\n" +
            "enum LengthUnit {\n" +
            "  METER\n" +
            "}\n" +
            "type Starship {\n" +
            "  id: ID!\n" +
            "  name: String!\n" +
            "  length(unit: LengthUnit = METER): Float\n" +
            "}\n" +
            "type Query {\n" +
            "  # ### GET products\n" +
            "  #\n" +
            "  # _Arguments_\n" +
            "  # - **id**: Product's id (optional)\n" +
            "  products(id: Int): [Product]\n" +
            "  # ### GET variants\n" +
            "  #\n" +
            "  # _Arguments_\n" +
            "  # - **id**: Variant's id (optional)\n" +
            "  variants(id: [LengthUnit]!): [Variant]\n" +
            "}";

    @Test
    public void testParseSchema(){
        SchemaGraphQLDefinitionConverter converter = new SchemaGraphQLDefinitionConverter(null);
        final GraphQLDefinitionConverterResult result = converter.convertRaw(SCHEMA);



    }


}
