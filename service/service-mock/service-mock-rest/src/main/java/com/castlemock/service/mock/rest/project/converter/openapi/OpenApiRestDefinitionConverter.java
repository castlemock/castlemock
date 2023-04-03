/*
 * Copyright 2023 Jebish Moses
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

package com.castlemock.service.mock.rest.project.converter.openapi;

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.core.utility.file.FileUtility;
import com.castlemock.model.mock.rest.domain.*;
import com.castlemock.service.mock.rest.project.converter.AbstractRestDefinitionConverter;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import io.swagger.v3.parser.core.models.ParseOptions;
import joptsimple.internal.Strings;
import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * The {@link OpenApiRestDefinitionConverter} provides OpenAPI V3 related functionality.
 */
public class OpenApiRestDefinitionConverter extends AbstractRestDefinitionConverter {
    /**
     * The convert method provides the functionality to convert the provided {@link File} into
     * a list of {@link RestApplication}.
     *
     * @param file             The file which will be converted to one or more {@link RestApplication}.
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A list of {@link RestApplication} based on the provided file.
     */
    @Override
    public List<RestApplication> convert(final File file, final boolean generateResponse) {
        ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolve(true);
        parseOptions.setResolveFully(true);
        final String openApiContent = FileUtility.getFileContent(file);
        SwaggerParseResult result = new OpenAPIParser().readContents(openApiContent, null, parseOptions);
        OpenAPI openAPI = result.getOpenAPI();
        final RestApplication restApplication = convertOpenApi(openAPI, generateResponse);
        return List.of(restApplication);
    }

    /**
     * The convert method provides the functionality to convert the provided {@link File} into
     * a list of {@link RestApplication}.
     *
     * @param location         The location of the definition file
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A list of {@link RestApplication} based on the provided file.
     */
    @Override
    public List<RestApplication> convert(final String location, final boolean generateResponse) {
        ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolve(true);
        parseOptions.setResolveFully(true);
        final SwaggerParseResult result = new OpenAPIParser().readLocation(location, null, parseOptions);
        OpenAPI openAPI = result.getOpenAPI();
        final RestApplication restApplication = convertOpenApi(openAPI, generateResponse);
        return List.of(restApplication);
    }


    /**
     * The method provides the functionality to convert a Swagger String into a {@link RestApplication}.
     * The following HTTP methods will be extracted from the Swagger content:
     * <ul>
     *     <li>GET</li>
     *     <li>PUT</li>
     *     <li>POST</li>
     *     <li>DELETE</li>
     *     <li>HEAD</li>
     *     <li>OPTIONS</li>
     * </ul>
     *
     * @param openAPI          The OpenAPI content which will be generated into a {@link RestApplication}.
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A {@link RestApplication} based on the provided Swagger content.
     */
    private RestApplication convertOpenApi(final OpenAPI openAPI, final boolean generateResponse) {

        if (openAPI == null) {
            throw new IllegalArgumentException("Unable to parse the OpenApi content.");
        }

        final RestApplication restApplication = new RestApplication();
        restApplication.setName(this.getApplicationName(openAPI));

        final String forwardAddress = getForwardAddress(openAPI);

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            final String resourceName = pathEntry.getKey();
            final PathItem resourcePath = pathEntry.getValue();
            final RestResource restResource = new RestResource();

            restResource.setName(resourceName);
            restResource.setUri(resourceName);

            if (resourcePath.getGet() != null) {
                Operation operation = resourcePath.getGet();
                RestMethod restMethod = createRestMethod(operation, HttpMethod.GET, forwardAddress, generateResponse);
                restResource.getMethods().add(restMethod);
            }
            if (resourcePath.getPost() != null) {
                Operation operation = resourcePath.getPost();
                RestMethod restMethod = createRestMethod(operation, HttpMethod.POST, forwardAddress, generateResponse);
                restResource.getMethods().add(restMethod);
            }
            if (resourcePath.getPut() != null) {
                Operation operation = resourcePath.getPut();
                RestMethod restMethod = createRestMethod(operation, HttpMethod.PUT, forwardAddress, generateResponse);
                restResource.getMethods().add(restMethod);
            }
            if (resourcePath.getDelete() != null) {
                Operation operation = resourcePath.getDelete();
                RestMethod restMethod = createRestMethod(operation, HttpMethod.DELETE, forwardAddress, generateResponse);
                restResource.getMethods().add(restMethod);
            }
            if (resourcePath.getHead() != null) {
                Operation operation = resourcePath.getHead();
                RestMethod restMethod = createRestMethod(operation, HttpMethod.HEAD, forwardAddress, generateResponse);
                restResource.getMethods().add(restMethod);
            }
            if (resourcePath.getOptions() != null) {
                Operation operation = resourcePath.getOptions();
                RestMethod restMethod = createRestMethod(operation, HttpMethod.OPTIONS, forwardAddress, generateResponse);
                restResource.getMethods().add(restMethod);
            }

            restApplication.getResources().add(restResource);
        }

        return restApplication;
    }

    /**
     * The method extracts the forward address from the {@link OpenAPI} model.
     *
     * @param openAPI The {@link OpenAPI} model contains information about the source address.
     * @return The extracted source address configured in {@link OpenAPI}.
     */
    private String getForwardAddress(final OpenAPI openAPI) {
        if (!openAPI.getServers().isEmpty() && openAPI.getServers().get(0).getUrl() != null) {
            return openAPI.getServers().get(0).getUrl();
        }
        return Strings.EMPTY;
    }

    /**
     * Create a {@link RestMethod} based on a Swagger {@link Operation} and a {@link HttpMethod}.
     *
     * @param operation        The Swagger operation that will be converted to a {@link RestMethod}.
     * @param httpMethod       The {@link HttpMethod} of the new {@link RestMethod}.
     * @param forwardAddress   The configured forward address. The request for this method will be forwarded to
     *                         this address if the service is configured to be {@link RestMethodStatus#FORWARDED},
     *                         {@link RestMethodStatus#RECORDING} or  {@link RestMethodStatus#RECORD_ONCE}
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A {@link RestMethod} based on the provided Swagger {@link Operation} and the {@link HttpMethod}.
     */
    private RestMethod createRestMethod(final Operation operation,
                                        final HttpMethod httpMethod, final String forwardAddress,
                                        final boolean generateResponse) {
        final RestMethod restMethod = new RestMethod();

        String methodName;
        if (operation.getOperationId() != null) {
            methodName = operation.getOperationId();
        } else if (operation.getSummary() != null) {
            methodName = operation.getSummary();
        } else {
            methodName = httpMethod.name();
        }

        restMethod.setHttpMethod(httpMethod);
        restMethod.setName(methodName);
        restMethod.setStatus(RestMethodStatus.MOCKED);
        restMethod.setResponseStrategy(RestResponseStrategy.SEQUENCE);
        restMethod.setForwardedEndpoint(forwardAddress);

        if (generateResponse) {
            if (ObjectUtils.isNotEmpty(operation.getResponses())) {
                Collection<RestMockResponse> mockResponses = generateResponse(operation.getResponses());
                restMethod.getMockResponses().addAll(mockResponses);
            } else {
                RestMockResponse generatedResponse = generateResponse();
                restMethod.getMockResponses().add(generatedResponse);
            }
        }

        return restMethod;
    }

    /**
     * The method generates a default response.
     *
     * @param responses The Swagger response definitions
     * @return The newly generated {@link RestMockResponse}.
     */
    private Collection<RestMockResponse> generateResponse(final ApiResponses responses) {
        if (responses == null) {
            return Collections.emptyList();
        }

        final List<RestMockResponse> mockResponses = new ArrayList<>();
        for (Map.Entry<String, ApiResponse> responseEntry : responses.entrySet()) {
            ApiResponse response = responseEntry.getValue();

            int httpStatusCode = extractHttpStatusCode(responseEntry.getKey());

            RestMockResponse restMockResponse = generateResponse(httpStatusCode, response);
            mockResponses.add(restMockResponse);

        }
        return mockResponses;
    }

    /**
     * The method will extract the HTTP response status code. The provided response code
     * is a {@link String} and should be parsed to an integer. However, the response code
     * is not always the actual response code. In fact, it can be anything. Therefore,
     * upon {@link NumberFormatException} the default response code will be returned: 200.
     *
     * @param responseCode The response code that will be parsed into an integer.
     * @return The parsed response code. 200 if the parsing failed.
     */
    private int extractHttpStatusCode(final String responseCode) {
        try {
            return Integer.parseInt(responseCode);
        } catch (Exception e) {
            return DEFAULT_RESPONSE_CODE;
        }
    }

    /**
     * The method generates a mocked response based on the provided {@link ApiResponse} and the
     * <code>httpStatusCode</code>.
     *
     * @param httpStatusCode The HTTP status code that the mocked response will have. Please note that
     *                       any mock response with status code different from OK (200), will be
     *                       marked as disabled.
     * @param response       The Swagger response that the mocked response will be based on.
     * @return A new {@link RestMockResponse} based on the provided {@link ApiResponse}.
     */
    private RestMockResponse generateResponse(final int httpStatusCode, final ApiResponse response) {
        RestMockResponse restMockResponse = new RestMockResponse();
        restMockResponse.setName(response.getDescription());
        restMockResponse.setHttpStatusCode(httpStatusCode);
        restMockResponse.setUsingExpressions(true);
        if (httpStatusCode == DEFAULT_RESPONSE_CODE) {
            restMockResponse.setStatus(RestMockResponseStatus.ENABLED);
        } else {
            restMockResponse.setStatus(RestMockResponseStatus.DISABLED);
        }

        if (response.getHeaders() != null) {
            for (Map.Entry<String, Header> headerEntry : response.getHeaders().entrySet()) {
                String headerName = headerEntry.getKey();
                HttpHeader httpHeader = new HttpHeader();
                httpHeader.setName(headerName);
                restMockResponse.getHttpHeaders().add(httpHeader);
            }
        }
        return restMockResponse;
    }

    private String getApplicationName(final OpenAPI openAPI) {
        if (openAPI.getInfo() != null) {
            if (openAPI.getInfo().getTitle() != null) {
                return openAPI.getInfo().getTitle();
            }
            if (openAPI.getInfo().getDescription() != null) {
                return openAPI.getInfo().getDescription();
            }
        }
        if (openAPI.getServers().size() != 0) {
            if (openAPI.getServers().get(0).getDescription() != null) {
                return openAPI.getServers().get(0).getDescription();
            }
            if (openAPI.getServers().get(0).getUrl() != null) {
                return openAPI.getServers().get(0).getUrl();
            }
        }

        throw new IllegalArgumentException("Unable to extract application name " +
                "from the following swagger config: " + openAPI);
    }
}

