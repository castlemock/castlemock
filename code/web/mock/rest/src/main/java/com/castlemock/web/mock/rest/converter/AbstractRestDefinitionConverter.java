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


package com.castlemock.web.mock.rest.converter;


import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.core.mock.rest.model.project.dto.RestMockResponseDto;

/**
 * @author Karl Dahlgren
 * @since 1.10
 */
public abstract class AbstractRestDefinitionConverter implements RestDefinitionConverter {

    protected static final String AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME = "Auto-generated mocked response";

    /**
     * The method generates a default response.
     * @return The newly generated {@link RestMockResponseDto}.
     */
    protected RestMockResponseDto generateResponse(){
        RestMockResponseDto restMockResponse = new RestMockResponseDto();
        restMockResponse.setName(AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME);
        restMockResponse.setHttpStatusCode(200);
        restMockResponse.setStatus(RestMockResponseStatus.ENABLED);
        return restMockResponse;
    }

}
