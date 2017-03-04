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

package com.castlemock.web.mock.rest.converter.raml;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.model.http.dto.HttpHeaderDto;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.castlemock.core.mock.rest.model.project.dto.RestMethodDto;
import com.castlemock.core.mock.rest.model.project.dto.RestMockResponseDto;
import com.castlemock.core.mock.rest.model.project.dto.RestResourceDto;
import org.apache.log4j.Logger;
import org.raml.v2.api.model.v10.bodies.Response;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.methods.Method;
import org.raml.v2.api.model.v10.resources.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The {@link RAML10Parser}
 * @since 1.10
 * @author Karl Dahlgren
 */
class RAML10Parser extends AbstractRAMLParser {

    private static final Logger LOGGER = Logger.getLogger(RAML10Parser.class);

    public void getResources(final List<Resource> resources, final List<RestResourceDto> result,
                             final String path, final boolean generateResponse){
        if(resources.isEmpty()){
            return;
        }

        for(Resource resource : resources){
            String uri = path + resource.relativeUri().value();

            List<Method> methods = resource.methods();
            if(!methods.isEmpty()){
                RestResourceDto restResource = new RestResourceDto();
                restResource.setName(resource.displayName().value());
                restResource.setUri(uri);
                result.add(restResource);

                for(Method method : methods){
                    HttpMethod httpMethod = HttpMethod.getValue(method.method());
                    if(httpMethod == null){
                        LOGGER.error("The REST method '" + method.method() + "' is not supported.");
                        continue;
                    }

                    RestMethodDto restMethod = new RestMethodDto();
                    restMethod.setName(method.displayName().value());
                    restMethod.setStatus(RestMethodStatus.MOCKED);
                    restMethod.setResponseStrategy(RestResponseStrategy.RANDOM);
                    restMethod.setHttpMethod(httpMethod);

                    if(generateResponse){
                        final Collection<RestMockResponseDto> mockResponses = createMockResponses(method.responses());
                        restMethod.getMockResponses().addAll(mockResponses);
                    }

                    restResource.getMethods().add(restMethod);
                }

            }

            getResources(resource.resources(), result, uri, generateResponse);
        }
    }

    private Collection<RestMockResponseDto> createMockResponses(List<Response> responses){
        final List<RestMockResponseDto> mockResponses = new ArrayList<>();


        for(int index = 0; index < responses.size(); index++){
            Response response = responses.get(index);
            String responseCode = response.code().value();
            int httpStatusCode = super.extractHttpStatusCode(responseCode);
            RestMockResponseDto restMockResponse = new RestMockResponseDto();
            restMockResponse.setName(RESPONSE_NAME_PREFIX + (index + 1));
            restMockResponse.setHttpStatusCode(httpStatusCode);

            if(httpStatusCode == DEFAULT_RESPONSE_CODE){
                restMockResponse.setStatus(RestMockResponseStatus.ENABLED);
            } else {
                restMockResponse.setStatus(RestMockResponseStatus.DISABLED);
            }

            if(response.body() != null && !response.body().isEmpty()){
                TypeDeclaration typeDeclaration = response.body().get(0);

                if(typeDeclaration.example() != null){
                    String body = typeDeclaration.example().value();
                    restMockResponse.setBody(body);
                }
            }

            if(response.headers() != null){
                for(TypeDeclaration parameter : response.headers()){
                    HttpHeaderDto httpHeader = new HttpHeaderDto();
                    httpHeader.setName(parameter.name());
                    httpHeader.setValue(parameter.defaultValue());
                    restMockResponse.getHttpHeaders().add(httpHeader);
                }
            }

            mockResponses.add(restMockResponse);
        }

        return mockResponses;
    }

}
