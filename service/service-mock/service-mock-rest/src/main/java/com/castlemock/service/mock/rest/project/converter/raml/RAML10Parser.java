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

package com.castlemock.service.mock.rest.project.converter.raml;

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.core.utility.IdUtility;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;
import org.raml.v2.api.model.v10.bodies.Response;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.methods.Method;
import org.raml.v2.api.model.v10.resources.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The {@link RAML10Parser}
 * @since 1.10
 * @author Karl Dahlgren
 */
class RAML10Parser extends AbstractRAMLParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(RAML10Parser.class);

    public void getResources(final List<Resource> resources, final List<RestResource> result,
                             final String path, final boolean generateResponse){
        if(resources.isEmpty()){
            return;
        }

        for(Resource resource : resources){
            final String uri = path + resource.relativeUri().value();
            final List<Method> methods = resource.methods();
            if(!methods.isEmpty()){
                final RestResource restResource = RestResource.builder()
                        .id(IdUtility.generateId())
                        .name(resource.displayName().value())
                        .uri(uri)
                        .build();
                result.add(restResource);

                for(Method method : methods){
                    HttpMethod httpMethod = HttpMethod.getValue(method.method())
                            .orElse(null);
                    if(httpMethod == null){
                        LOGGER.error("The REST method '" + method.method() + "' is not supported.");
                        continue;
                    }

                    final List<RestMockResponse> mockResponses = new ArrayList<>();
                    if(generateResponse){
                        mockResponses.addAll(createMockResponses(method.responses()));
                    }

                    restResource.getMethods().add(RestMethod.builder()
                            .id(IdUtility.generateId())
                            .resourceId(restResource.getId())
                            .name(method.displayName().value())
                            .status(RestMethodStatus.MOCKED)
                            .responseStrategy(RestResponseStrategy.RANDOM)
                            .httpMethod(httpMethod)
                            .mockResponses(mockResponses)
                            .build());
                }

            }

            getResources(resource.resources(), result, uri, generateResponse);
        }
    }

    private Collection<RestMockResponse> createMockResponses(final List<Response> responses){
        final List<RestMockResponse> mockResponses = new ArrayList<>();

        for(int index = 0; index < responses.size(); index++){
            final Response response = responses.get(index);
            final String responseCode = response.code().value();
            final int httpStatusCode = super.extractHttpStatusCode(responseCode);

            final RestMockResponseStatus status;
            if(httpStatusCode == DEFAULT_RESPONSE_CODE){
                status = RestMockResponseStatus.ENABLED;
            } else {
                status = RestMockResponseStatus.DISABLED;
            }

            String body = "";
            if(response.body() != null && !response.body().isEmpty()){
                final TypeDeclaration typeDeclaration = response.body().getFirst();

                if(typeDeclaration.example() != null){
                    body = typeDeclaration.example().value();
                }
            }

            final List<HttpHeader> headers = new ArrayList<>();
            if(response.headers() != null){
                for(TypeDeclaration parameter : response.headers()){
                    final HttpHeader httpHeader = HttpHeader.builder()
                            .name(parameter.name())
                            .value(parameter.defaultValue())
                            .build();
                    headers.add(httpHeader);
                }
            }

            mockResponses.add(RestMockResponse.builder()
                    .name(RESPONSE_NAME_PREFIX + (index + 1))
                    .httpStatusCode(httpStatusCode)
                    .httpHeaders(headers)
                    .body(body)
                    .status(status)
                    .build());
        }

        return mockResponses;
    }

}
