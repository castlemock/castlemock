/*
 * Copyright 2018 Karl Dahlgren
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


package com.castlemock.web.mock.graphql.converter;


import com.castlemock.core.mock.graphql.model.project.domain.GraphQLMockResponseStatus;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLMockResponseDto;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public abstract class AbstractGraphQLDefinitionConverter implements GraphQLDefinitionConverter {

    protected static final String AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME = "Auto-generated mocked response";
    protected static final int DEFAULT_RESPONSE_CODE = 200;

    /**
     * The method generates a default response.
     * @return The newly generated {@link GraphQLMockResponseDto}.
     */
    protected GraphQLMockResponseDto generateResponse(){
        GraphQLMockResponseDto mockResponse = new GraphQLMockResponseDto();
        mockResponse.setName(AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME);
        mockResponse.setHttpStatusCode(DEFAULT_RESPONSE_CODE);
        mockResponse.setStatus(GraphQLMockResponseStatus.ENABLED);
        return mockResponse;
    }

}
