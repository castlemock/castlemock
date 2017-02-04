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
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.castlemock.core.mock.rest.model.project.dto.RestMethodDto;
import com.castlemock.core.mock.rest.model.project.dto.RestResourceDto;
import org.apache.log4j.Logger;
import org.raml.v2.api.model.v08.methods.Method;
import org.raml.v2.api.model.v08.resources.Resource;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.10
 */
class RAML08Parser {

    private static final Logger LOGGER = Logger.getLogger(RAML08Parser.class);

    public void getResources(final List<Resource> resources, final List<RestResourceDto> result, final String path) {
        if(resources.isEmpty()){
            return;
        }

        for(Resource resource : resources){
            String uri = path + resource.relativeUri().value();

            List<Method> methods = resource.methods();
            if(!methods.isEmpty()){
                RestResourceDto restResource = new RestResourceDto();
                restResource.setName(uri);
                restResource.setUri(uri);
                result.add(restResource);

                for(Method method : methods){
                    HttpMethod httpMethod = HttpMethod.getValue(method.method());
                    if(httpMethod == null){
                        LOGGER.error("The REST method '" + method.method() + "' is not supported.");
                        continue;
                    }

                    RestMethodDto restMethod = new RestMethodDto();
                    restMethod.setName(httpMethod.name());
                    restMethod.setStatus(RestMethodStatus.MOCKED);
                    restMethod.setResponseStrategy(RestResponseStrategy.RANDOM);
                    restMethod.setHttpMethod(httpMethod);
                    restResource.getMethods().add(restMethod);
                }

            }

            getResources(resource.resources(), result, uri);
        }
    }
}
