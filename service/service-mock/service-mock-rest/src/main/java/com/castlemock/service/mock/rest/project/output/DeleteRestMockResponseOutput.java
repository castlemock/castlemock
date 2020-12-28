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

package com.castlemock.service.mock.rest.project.output;

import com.castlemock.model.core.model.Output;
import com.castlemock.model.mock.rest.domain.RestMockResponse;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class DeleteRestMockResponseOutput implements Output {

    private final RestMockResponse mockResponse;

    private DeleteRestMockResponseOutput(final Builder builder){
        this.mockResponse = Objects.requireNonNull(builder.mockResponse);
    }

    public RestMockResponse getMockResponse() {
        return mockResponse;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private RestMockResponse mockResponse;

        public Builder mockResponse(final RestMockResponse mockResponse){
            this.mockResponse = mockResponse;
            return this;
        }

        public DeleteRestMockResponseOutput build(){
            return new DeleteRestMockResponseOutput(this);
        }

    }
}
