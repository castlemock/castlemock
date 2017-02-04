package com.castlemock.web.mock.rest.converter;

import com.castlemock.core.mock.rest.model.RestDefinitionType;

/**
 * The {@link RestDefinitionConverterFactory} is a factory class responsible for creating new {@link RestDefinitionConverter}.
 * @author Karl Dahlgren
 * @since 1.10
 * @see SwaggerRestDefinitionConverter
 * @see WADLRestDefinitionConverter
 */
public class RestDefinitionConverterFactory {


    /**
     * The method returns the requested type of {@link RestDefinitionConverter}.
     * @param converterType The tyoe pf {@link RestDefinitionConverter} that will be created.
     * @return A {@link RestDefinitionConverter} which matches the provided <code>converterType</code>
     * @throws IllegalArgumentException If not converter is matching the provided {@link RestDefinitionType}
     */
    public static RestDefinitionConverter getConverter(final RestDefinitionType converterType){
        switch (converterType){
            case WADL:
                return new WADLRestDefinitionConverter();
            case SWAGGER:
                return new SwaggerRestDefinitionConverter();
        }

        throw new IllegalArgumentException("Invalid converter type: " + converterType);
    }

}
