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
import com.castlemock.model.core.utility.IdUtility;
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
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import joptsimple.internal.Strings;
import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.util.*;

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
    public List<RestApplication> convert(final File file, final String projectId, final boolean generateResponse) {
        ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolve(true);
        parseOptions.setResolveFully(true);
        final String openApiContent = FileUtility.getFileContent(file);
        SwaggerParseResult result = new OpenAPIParser().readContents(openApiContent, null, parseOptions);
        OpenAPI openAPI = result.getOpenAPI();
        final RestApplication restApplication = convertOpenApi(openAPI, projectId, generateResponse);
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
    public List<RestApplication> convert(final String location, final String projectId, final boolean generateResponse) {
        ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolve(true);
        parseOptions.setResolveFully(true);
        final SwaggerParseResult result = new OpenAPIParser().readLocation(location, null, parseOptions);
        OpenAPI openAPI = result.getOpenAPI();
        final RestApplication restApplication = convertOpenApi(openAPI, projectId, generateResponse);
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
    private RestApplication convertOpenApi(final OpenAPI openAPI, final String projectId, final boolean generateResponse) {

        if (openAPI == null) {
            throw new IllegalArgumentException("Unable to parse the OpenApi content.");
        }


        final String applicationId = IdUtility.generateId();
        final String forwardAddress = getForwardAddress(openAPI);
        final List<RestResource> resources = new ArrayList<>();
        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            final String resourceName = pathEntry.getKey();
            final PathItem resourcePath = pathEntry.getValue();

            final String resourceId = IdUtility.generateId();
            final List<RestMethod> methods = new ArrayList<>();

            if (resourcePath.getGet() != null) {
                Operation operation = resourcePath.getGet();
                RestMethod restMethod = createRestMethod(operation, HttpMethod.GET, forwardAddress, resourceId, generateResponse);
                methods.add(restMethod);
            }
            if (resourcePath.getPost() != null) {
                Operation operation = resourcePath.getPost();
                RestMethod restMethod = createRestMethod(operation, HttpMethod.POST, forwardAddress, resourceId, generateResponse);
                methods.add(restMethod);
            }
            if (resourcePath.getPut() != null) {
                Operation operation = resourcePath.getPut();
                RestMethod restMethod = createRestMethod(operation, HttpMethod.PUT, forwardAddress, resourceId, generateResponse);
                methods.add(restMethod);
            }
            if (resourcePath.getDelete() != null) {
                Operation operation = resourcePath.getDelete();
                RestMethod restMethod = createRestMethod(operation, HttpMethod.DELETE, forwardAddress, resourceId, generateResponse);
                methods.add(restMethod);
            }
            if (resourcePath.getHead() != null) {
                Operation operation = resourcePath.getHead();
                RestMethod restMethod = createRestMethod(operation, HttpMethod.HEAD, forwardAddress, resourceId, generateResponse);
                methods.add(restMethod);
            }
            if (resourcePath.getOptions() != null) {
                Operation operation = resourcePath.getOptions();
                RestMethod restMethod = createRestMethod(operation, HttpMethod.OPTIONS, forwardAddress, resourceId, generateResponse);
                methods.add(restMethod);
            }

            resources.add(RestResource.builder()
                    .id(resourceId)
                    .applicationId(applicationId)
                    .name(resourceName)
                    .uri(resourceName)
                    .methods(methods)
                    .build());
        }

        return RestApplication.builder()
                .id(applicationId)
                .projectId(projectId)
                .name(this.getApplicationName(openAPI))
                .resources(resources)
                .build();
    }

    /**
     * The method extracts the forward address from the {@link OpenAPI} model.
     *
     * @param openAPI The {@link OpenAPI} model contains information about the source address.
     * @return The extracted source address configured in {@link OpenAPI}.
     */
    private String getForwardAddress(final OpenAPI openAPI) {
        if (!openAPI.getServers().isEmpty() && openAPI.getServers().getFirst().getUrl() != null) {
            return openAPI.getServers().getFirst().getUrl();
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
                                        final String resourceId,
                                        final boolean generateResponse) {
        String methodName;
        if (operation.getOperationId() != null) {
            methodName = operation.getOperationId();
        } else if (operation.getSummary() != null) {
            methodName = operation.getSummary();
        } else {
            methodName = httpMethod.name();
        }

        final List<RestMockResponse> mockResponses = new ArrayList<>();
        final String methodId = IdUtility.generateId();
        if (generateResponse) {
            if (ObjectUtils.isNotEmpty(operation.getResponses())) {
                mockResponses.addAll(generateResponse(operation.getResponses(), methodId));
            } else {
                mockResponses.add(generateResponse(methodId));
            }
        }

        return RestMethod.builder()
                .id(methodId)
                .resourceId(resourceId)
                .name(methodName)
                .httpMethod(httpMethod)
                .status(RestMethodStatus.MOCKED)
                .responseStrategy(RestResponseStrategy.SEQUENCE)
                .forwardedEndpoint(forwardAddress)
                .mockResponses(mockResponses)
                .simulateNetworkDelay(false)
                .networkDelay(0L)
                .automaticForward(false)
                .build();
    }

    /**
     * The method generates a default response.
     *
     * @param responses The Swagger response definitions
     * @return The newly generated {@link RestMockResponse}.
     */
    private Collection<RestMockResponse> generateResponse(final ApiResponses responses, final String methodId) {
        if (responses == null) {
            return Collections.emptyList();
        }

        final List<RestMockResponse> mockResponses = new ArrayList<>();
        for (Map.Entry<String, ApiResponse> responseEntry : responses.entrySet()) {
            ApiResponse response = responseEntry.getValue();

            int httpStatusCode = extractHttpStatusCode(responseEntry.getKey());

            RestMockResponse restMockResponse = generateResponse(httpStatusCode, methodId, response);
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
    private RestMockResponse generateResponse(final int httpStatusCode, final String methodId, final ApiResponse response) {

        final RestMockResponseStatus status;
        if (httpStatusCode == DEFAULT_RESPONSE_CODE) {
            status = RestMockResponseStatus.ENABLED;
        } else {
            status = RestMockResponseStatus.DISABLED;
        }

        final List<HttpHeader> headers = new ArrayList<>();
        if (response.getHeaders() != null) {
            for (Map.Entry<String, Header> headerEntry : response.getHeaders().entrySet()) {
                final String headerName = headerEntry.getKey();
                final HttpHeader httpHeader = HttpHeader.builder()
                        .name(headerName)
                        .value("")
                        .build();
                headers.add(httpHeader);
            }
        }
        return RestMockResponse.builder()
                .id(IdUtility.generateId())
                .methodId(methodId)
                .name(response.getDescription())
                .httpStatusCode(httpStatusCode)
                .usingExpressions(true)
                .httpHeaders(headers)
                .status(status)
                .build();
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
        if (!openAPI.getServers().isEmpty()) {
            if (openAPI.getServers().getFirst().getDescription() != null) {
                return openAPI.getServers().getFirst().getDescription();
            }
            if (openAPI.getServers().getFirst().getUrl() != null) {
                return openAPI.getServers().getFirst().getUrl();
            }
        }

        throw new IllegalArgumentException("Unable to extract application name " +
                "from the following swagger config: " + openAPI);
    }
}

