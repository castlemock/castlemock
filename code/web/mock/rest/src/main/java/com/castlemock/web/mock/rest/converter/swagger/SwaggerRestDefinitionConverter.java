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

package com.castlemock.web.mock.rest.converter.swagger;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.utility.file.FileUtility;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.castlemock.core.mock.rest.model.project.dto.RestApplicationDto;
import com.castlemock.core.mock.rest.model.project.dto.RestMethodDto;
import com.castlemock.core.mock.rest.model.project.dto.RestMockResponseDto;
import com.castlemock.core.mock.rest.model.project.dto.RestResourceDto;
import com.castlemock.web.mock.rest.converter.AbstractRestDefinitionConverter;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * The {@link SwaggerRestDefinitionConverter} provides Swagger related functionality.
 * @since 1.10
 * @author Karl Dahlgren
 */
public class SwaggerRestDefinitionConverter extends AbstractRestDefinitionConverter {

    /**
     * The method provides the functionality to convert a provided Swagger {@link File} into a {@link RestApplicationDto}.
     * The method will simply parse the file and extracts its content. The content will then be passed to
     * {@link SwaggerRestDefinitionConverter#convertSwagger(String, boolean)}.
     * @param file The file which content will be extracted from and transformed into a {@link RestApplicationDto}.
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A {@link RestApplicationDto} based on the provided {@link File}.
     * @see SwaggerRestDefinitionConverter#convertSwagger(String, boolean)
     */
    @Override
    public List<RestApplicationDto> convert(final File file, final boolean generateResponse){
        final String swaggerContent = FileUtility.getFileContent(file);
        final RestApplicationDto restApplication = convertSwagger(swaggerContent, generateResponse);
        return Arrays.asList(restApplication);
    }


    /**
     * The method provides the functionality to convert a Swagger String into a {@link RestApplicationDto}.
     * The following HTTP methods will be extracted from the Swagger content:
     * <ul>
     *     <li>GET</li>
     *     <li>PUT</li>
     *     <li>POST</li>
     *     <li>DELETE</li>
     *     <li>HEAD</li>
     *     <li>OPTIONS</li>
     * </ul>
     * @param content The Swagger content which will be generated into a {@link RestApplicationDto}.
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A {@link RestApplicationDto} based on the provided Swagger content.
     */
    private RestApplicationDto convertSwagger(final String content, final boolean generateResponse){

        final Swagger swagger = new SwaggerParser().parse(content);

        if(swagger == null){
            throw new IllegalArgumentException("Unable to parse the Swagger content.");
        }

        final RestApplicationDto restApplication = new RestApplicationDto();
        restApplication.setName(swagger.getHost());

        final String forwardAddress = getForwardAddress(swagger);

        for(Map.Entry<String, Path> pathEntry : swagger.getPaths().entrySet()){
            final String resourceName = pathEntry.getKey();
            final Path resourcePath = pathEntry.getValue();
            final RestResourceDto restResource = new RestResourceDto();

            restResource.setName(resourceName);
            restResource.setUri(resourceName);

            RestMethodDto restMethod = null;
            if(resourcePath.getGet() != null){
                Operation operation = resourcePath.getGet();
                restMethod = createRestMethod(operation, HttpMethod.GET);
            }
            if(resourcePath.getPost() != null){
                Operation operation = resourcePath.getPost();
                restMethod = createRestMethod(operation, HttpMethod.POST);
            }
            if(resourcePath.getPut() != null){
                Operation operation = resourcePath.getPut();
                restMethod = createRestMethod(operation, HttpMethod.PUT);
            }
            if(resourcePath.getDelete() != null){
                Operation operation = resourcePath.getDelete();
                restMethod = createRestMethod(operation, HttpMethod.DELETE);
            }
            if(resourcePath.getHead() != null){
                Operation operation = resourcePath.getHead();
                restMethod = createRestMethod(operation, HttpMethod.HEAD);
            }
            if(resourcePath.getOptions() != null){
                Operation operation = resourcePath.getOptions();
                restMethod = createRestMethod(operation, HttpMethod.OPTIONS);
            }

            if(restMethod != null){
                restMethod.setForwardedEndpoint(forwardAddress);
                if(generateResponse){
                    RestMockResponseDto generatedResponse = generateResponse();
                    restMethod.getMockResponses().add(generatedResponse);
                }

                restResource.getMethods().add(restMethod);
            }

            restApplication.getResources().add(restResource);
        }

        return restApplication;
    }

    /**
     * The method extracts the forward address from the {@link Swagger} model.
     * @param swagger The {@link Swagger} model contains information about the source address.
     * @return The extracted source address configured in {@link Swagger}.
     */
    private String getForwardAddress(final Swagger swagger){
        String schemas = "http";
        if(swagger.getSchemes() != null && !swagger.getSchemes().isEmpty()){
            schemas = swagger.getSchemes().get(0).toValue();
        }


        return schemas + "://" + swagger.getHost() + swagger.getBasePath();
    }

    /**
     * Create a {@link RestMethodDto} based on a Swagger {@link Operation} and a {@link HttpMethod}.
     * @param operation The Swagger operation that will be converted to a {@link RestMethodDto}.
     * @param httpMethod The {@link HttpMethod} of the new {@link RestMethodDto}.
     * @return A {@link RestMethodDto} based on the provided Swagger {@link Operation} and the {@link HttpMethod}.
     */
    private RestMethodDto createRestMethod(Operation operation, HttpMethod httpMethod){
        final RestMethodDto restMethod = new RestMethodDto();

        String methodName;
        if(operation.getOperationId() != null){
            methodName = operation.getOperationId();
        }
        else if(operation.getSummary() != null){
            methodName = operation.getSummary();
        } else {
            methodName = httpMethod.name();
        }

        restMethod.setHttpMethod(httpMethod);
        restMethod.setName(methodName);
        restMethod.setStatus(RestMethodStatus.MOCKED);
        restMethod.setResponseStrategy(RestResponseStrategy.SEQUENCE);
        return restMethod;
    }

}
