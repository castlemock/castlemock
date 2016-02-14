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

import com.fortmocks.core.basis.model.http.domain.HttpHeader;
import com.fortmocks.core.basis.model.http.domain.HttpMethod;
import com.fortmocks.core.basis.model.http.domain.HttpParameter;
import com.fortmocks.core.basis.model.http.dto.HttpHeaderDto;
import com.fortmocks.core.basis.model.http.dto.HttpParameterDto;
import com.fortmocks.core.mock.rest.model.event.domain.RestEvent;
import com.fortmocks.core.mock.rest.model.event.domain.RestRequest;
import com.fortmocks.core.mock.rest.model.event.domain.RestResponse;
import com.fortmocks.core.mock.rest.model.event.dto.RestEventDto;
import com.fortmocks.core.mock.rest.model.event.dto.RestRequestDto;
import com.fortmocks.core.mock.rest.model.event.dto.RestResponseDto;

import java.util.ArrayList;
import java.util.Date;


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
        eventDto.setProjectId("ProjectId");
        eventDto.setResourceId("ResourceId");
        eventDto.setApplicationId("ApplicationId");

        RestRequestDto restRequestDto = new RestRequestDto();
        restRequestDto.setHttpMethod(HttpMethod.GET);
        restRequestDto.setBody("REST request body");
        restRequestDto.setContentType("application/json");
        restRequestDto.setHttpHeaders(new ArrayList<HttpHeaderDto>());
        restRequestDto.setHttpParameters(new ArrayList<HttpParameterDto>());
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

    public static RestEvent generateRestEvent(){
        final RestEvent restEvent = new RestEvent();
        restEvent.setId("REST PROJECT");
        restEvent.setMethodId("REST method id");
        restEvent.setEndDate(new Date());
        restEvent.setStartDate(new Date());
        restEvent.setProjectId("ProjectId");
        restEvent.setResourceId("ResourceId");
        restEvent.setApplicationId("ApplicationId");

        RestRequest restRequest = new RestRequest();
        restRequest.setHttpMethod(HttpMethod.GET);
        restRequest.setBody("REST request body");
        restRequest.setContentType("application/json");
        restRequest.setHttpHeaders(new ArrayList<HttpHeader>());
        restRequest.setHttpParameters(new ArrayList<HttpParameter>());
        restRequest.setUri("URI");

        RestResponse restResponse = new RestResponse();
        restResponse.setHttpStatusCode(200);
        restResponse.setContentType("application/json");
        restResponse.setBody("REST response body");
        restResponse.setMockResponseName("Mock response name");

        restEvent.setRequest(restRequest);
        restEvent.setResponse(restResponse);

        return restEvent;
    }
}
