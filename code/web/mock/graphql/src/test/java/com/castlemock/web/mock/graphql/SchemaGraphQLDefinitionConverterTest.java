package com.castlemock.web.mock.graphql;

import com.castlemock.core.mock.graphql.model.project.dto.GraphQLApplicationDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLQueryDto;
import com.castlemock.web.mock.graphql.converter.schema.SchemaGraphQLDefinitionConverter;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

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
            "  variants(id: Int): [Variant]\n" +
            "}";

    @Test
    public void testParseSchema(){
        SchemaGraphQLDefinitionConverter converter = new SchemaGraphQLDefinitionConverter();
        List<GraphQLApplicationDto> applications = converter.convertRaw(SCHEMA, true);
        Assert.assertEquals(1, applications.size());

        List<GraphQLQueryDto> queries = applications.get(0).getQueries();
        Assert.assertEquals(2, queries.size());

    }


}
