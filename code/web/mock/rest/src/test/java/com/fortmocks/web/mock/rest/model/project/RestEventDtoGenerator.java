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

import com.fortmocks.core.basis.model.HttpMethod;
import com.fortmocks.core.mock.rest.model.event.dto.RestEventDto;
import com.fortmocks.core.mock.rest.model.event.dto.RestRequestDto;
import com.fortmocks.core.mock.rest.model.event.dto.RestResponseDto;

import java.util.Date;
import java.util.HashMap;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestEventDtoGenerator {

    public static RestEventDto generateRestEventDto(){
        final RestEventDto eventDto = new RestEventDto();
        eventDto.setId("REST PROJECT");
        eventDto.setMethodId("REST method id");
        eventDto.setEndDate(new Date());
        eventDto.setStartDate(new Date());

        RestRequestDto restRequestDto = new RestRequestDto();
        restRequestDto.setHttpMethod(HttpMethod.GET);
        restRequestDto.setBody("REST request body");
        restRequestDto.setContentType("application/json");
        restRequestDto.setParameters(new HashMap<String, String>());
        restRequestDto.setServiceName("Service name");
        restRequestDto.setUri("URI");

        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setHttpStatusCode(200);
        restResponseDto.setContentType("application/json");
        restResponseDto.setBody("REST response body");
        restResponseDto.setMockResponseName("Mock response name");

        eventDto.setRequest(restRequestDto);
        eventDto.setResponse(restResponseDto);

        return eventDto;
    }
}
