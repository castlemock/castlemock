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

package com.castlemock.web.mock.rest.model.project;


import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.core.mock.rest.model.project.dto.RestMockResponseDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestMockResponseDtoGenerator {

    public static RestMockResponseDto generateRestMockResponseDto(){
        final RestMockResponseDto restMockResponseDto = new RestMockResponseDto();
        restMockResponseDto.setName("Rest mock response name");
        restMockResponseDto.setBody("Rest mock response body");
        restMockResponseDto.setId("REST MOCK RESPONSE");
        restMockResponseDto.setStatus(RestMockResponseStatus.ENABLED);
        restMockResponseDto.setHttpStatusCode(200);
        return restMockResponseDto;
    }

    public static RestMockResponse generateRestMockResponse(){
        final RestMockResponse restMockResponse = new RestMockResponse();
        restMockResponse.setName("Rest mock response name");
        restMockResponse.setBody("Rest mock response body");
        restMockResponse.setId("REST MOCK RESPONSE");
        restMockResponse.setStatus(RestMockResponseStatus.ENABLED);
        restMockResponse.setHttpStatusCode(200);
        return restMockResponse;
    }
}
