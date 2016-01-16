/*
 * Copyright 2015 Karl Dahlgren
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

package com.fortmocks.web.mock.rest.model.project;

import com.fortmocks.core.basis.model.http.HttpMethod;
import com.fortmocks.core.mock.rest.model.project.domain.RestMethodStatus;
import com.fortmocks.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.fortmocks.core.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestMockResponseDto;

import java.util.ArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestMethodDtoGenerator {

    public static RestMethodDto generateRestMethodDto(){
        final RestMethodDto restMethodDto = new RestMethodDto();
        restMethodDto.setId("REST METHOD");
        restMethodDto.setName("REST method name");
        restMethodDto.setForwardedEndpoint("Forward endpoint");
        restMethodDto.setDefaultBody("Default body");
        restMethodDto.setCurrentResponseSequenceIndex(1);
        restMethodDto.setStatus(RestMethodStatus.MOCKED);
        restMethodDto.setHttpMethod(HttpMethod.GET);
        restMethodDto.setMockResponses(new ArrayList<RestMockResponseDto>());
        restMethodDto.setResponseStrategy(RestResponseStrategy.RANDOM);
        return restMethodDto;
    }
}
