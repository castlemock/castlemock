/*
 * Copyright 2017 Karl Dahlgren
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


package com.castlemock.service.mock.rest.project.converter;

import com.castlemock.model.mock.rest.RestDefinitionType;
import com.castlemock.service.core.manager.FileManager;
import com.castlemock.service.mock.rest.project.converter.openapi.OpenApiRestDefinitionConverter;
import com.castlemock.service.mock.rest.project.converter.raml.RAMLRestDefinitionConverter;
import com.castlemock.service.mock.rest.project.converter.swagger.SwaggerRestDefinitionConverter;
import com.castlemock.service.mock.rest.project.converter.wadl.WADLRestDefinitionConverter;

/**
 * The {@link RestDefinitionConverterFactory} is a factory class responsible for creating new {@link RestDefinitionConverter}.
 * @author Karl Dahlgren
 * @since 1.10
 * @see SwaggerRestDefinitionConverter
 * @see WADLRestDefinitionConverter
 */
public final class RestDefinitionConverterFactory {

    /**
     * Private constructor
     */
    private RestDefinitionConverterFactory(){

    }


    /**
     * The method returns the requested type of {@link RestDefinitionConverter}.
     * @param converterType The tyoe pf {@link RestDefinitionConverter} that will be created.
     * @return A {@link RestDefinitionConverter} which matches the provided <code>converterType</code>
     * @throws IllegalArgumentException If not converter is matching the provided {@link RestDefinitionType}
     */
    public static RestDefinitionConverter getConverter(final RestDefinitionType converterType,
                                                       final FileManager fileManager){
        switch (converterType){
            case WADL:
                return new WADLRestDefinitionConverter(fileManager);
            case SWAGGER:
                return new SwaggerRestDefinitionConverter();
            case OPENAPI:
                return new OpenApiRestDefinitionConverter();
            case RAML:
                return new RAMLRestDefinitionConverter();
        }

        throw new IllegalArgumentException("Invalid converter type: " + converterType);
    }

}
