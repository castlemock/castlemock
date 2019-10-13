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


package com.castlemock.web.mock.graphql.converter;

import com.castlemock.core.mock.graphql.model.GraphQLDefinitionType;
import com.castlemock.web.basis.manager.FileManager;
import com.castlemock.web.mock.graphql.converter.schema.SchemaGraphQLDefinitionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The {@link GraphQLDefinitionConverterFactory} is a factory class responsible for creating new {@link GraphQLDefinitionConverter}.
 * @author Karl Dahlgren
 * @since 1.19
 * @see SchemaGraphQLDefinitionConverter
 */
@Component
public class GraphQLDefinitionConverterFactory {

    @Autowired
    private FileManager fileManager;


    /**
     * The method returns the requested type of {@link GraphQLDefinitionConverter}.
     * @param converterType The tyoe pf {@link GraphQLDefinitionConverter} that will be created.
     * @return A {@link GraphQLDefinitionConverter} which matches the provided <code>converterType</code>
     * @throws IllegalArgumentException If not converter is matching the provided {@link GraphQLDefinitionType}
     */
    public GraphQLDefinitionConverter getConverter(final GraphQLDefinitionType converterType){
        switch (converterType){
            case SCHEMA:
                return new SchemaGraphQLDefinitionConverter(fileManager);
        }

        throw new IllegalArgumentException("Invalid converter type: " + converterType);
    }

}
