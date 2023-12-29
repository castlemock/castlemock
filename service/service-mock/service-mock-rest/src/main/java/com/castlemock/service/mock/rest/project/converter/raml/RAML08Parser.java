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
import org.raml.v2.api.model.v08.bodies.BodyLike;
import org.raml.v2.api.model.v08.bodies.Response;
import org.raml.v2.api.model.v08.methods.Method;
import org.raml.v2.api.model.v08.parameters.Parameter;
import org.raml.v2.api.model.v08.resources.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.10
 */
class RAML08Parser extends AbstractRAMLParser{

    private static final Logger LOGGER = LoggerFactory.getLogger(RAML08Parser.class);

    public void getResources(final List<Resource> resources, final List<RestResource> result,
                             final String path, boolean generateResponse) {
        if(resources.isEmpty()){
            return;
        }

        for(Resource resource : resources){
            final String uri = path + resource.relativeUri().value();
            final List<Method> methods = resource.methods();
            if(!methods.isEmpty()){
                final RestResource restResource = RestResource.builder()
                        .id(IdUtility.generateId())
                        .name(uri)
                        .uri(uri)
                        .build();
                result.add(restResource);

                for(Method method : methods){
                    final HttpMethod httpMethod = HttpMethod.getValue(method.method())
                            .orElse(null);
                    if(httpMethod == null){
                        LOGGER.error("The REST method '" + method.method() + "' is not supported.");
                        continue;
                    }

                    final List<RestMockResponse> mockResponses = new ArrayList<>();
                    if(generateResponse){
                        mockResponses.addAll(createMockResponses(method.responses()));
                    }


                    final RestMethod restMethod = RestMethod.builder()
                            .name(httpMethod.name())
                            .status(RestMethodStatus.MOCKED)
                            .responseStrategy(RestResponseStrategy.RANDOM)
                            .httpMethod(httpMethod)
                            .mockResponses(mockResponses)
                            .build();

                    restResource.getMethods().add(restMethod);
                }

            }

            getResources(resource.resources(), result, uri, generateResponse);
        }
    }

    private Collection<RestMockResponse> createMockResponses(List<Response> responses){
        final List<RestMockResponse> mockResponses = new ArrayList<>();


        for(int index = 0; index < responses.size(); index++){
            Response response = responses.get(index);
            String responseCode = response.code().value();
            int httpStatusCode = super.extractHttpStatusCode(responseCode);

            final RestMockResponseStatus status;
            if(httpStatusCode == DEFAULT_RESPONSE_CODE){
                status = RestMockResponseStatus.ENABLED;
            } else {
                status = RestMockResponseStatus.DISABLED;
            }

            String body = "";
            if(response.body() != null && !response.body().isEmpty()){
                final BodyLike bodyLike = response.body().getFirst();

                if(bodyLike.example() != null){
                    body = bodyLike.example().value();
                }
            }

            final List<HttpHeader> headers = new ArrayList<>();
            if(response.headers() != null){
                for(Parameter parameter : response.headers()){
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
                    .status(status)
                    .body(body)
                    .httpHeaders(headers)
                    .build());
        }

        return mockResponses;
    }
}
