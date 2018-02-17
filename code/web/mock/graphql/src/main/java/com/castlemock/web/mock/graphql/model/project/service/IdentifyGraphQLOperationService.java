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


package com.castlemock.web.mock.graphql.model.project.service;


import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLApplicationDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLOperationDto;
import com.castlemock.core.mock.graphql.model.project.service.message.input.IdentifyGraphQLOperationInput;
import com.castlemock.core.mock.graphql.model.project.service.message.output.IdentifyGraphQLOperationOutput;
import com.castlemock.web.mock.graphql.converter.schema.SchemaGraphQLDefinitionConverter;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
@org.springframework.stereotype.Service
public class IdentifyGraphQLOperationService extends AbstractGraphQLProjectService implements Service<IdentifyGraphQLOperationInput, IdentifyGraphQLOperationOutput> {

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

    static SchemaGraphQLDefinitionConverter converter = new SchemaGraphQLDefinitionConverter();
    static List<GraphQLApplicationDto> applications = converter.convertRaw(SCHEMA, true);

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     *
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<IdentifyGraphQLOperationOutput> process(ServiceTask<IdentifyGraphQLOperationInput> serviceTask) {

        GraphQLApplicationDto application = applications.get(0);
        GraphQLOperationDto operation = application.getQueries().get(0);

        final IdentifyGraphQLOperationOutput output =
                new IdentifyGraphQLOperationOutput(
                        "ProjectId", "ApplicationId",
                        "OperationId", operation);
        return createServiceResult(output);
    }
}
