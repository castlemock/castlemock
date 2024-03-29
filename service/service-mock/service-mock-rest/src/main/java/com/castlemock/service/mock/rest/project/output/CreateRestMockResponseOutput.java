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

import com.castlemock.model.core.Output;
import com.castlemock.model.mock.rest.domain.RestMockResponse;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateRestMockResponseOutput implements Output {

    private final RestMockResponse mockResponse;

    private CreateRestMockResponseOutput(final RestMockResponse mockResponse) {
        this.mockResponse = Objects.requireNonNull(mockResponse, "mockResponse");
    }

    public RestMockResponse getMockResponse() {
        return mockResponse;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreateRestMockResponseOutput that = (CreateRestMockResponseOutput) o;
        return Objects.equals(mockResponse, that.mockResponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mockResponse);
    }

    @Override
    public String toString() {
        return "CreateRestMockResponseOutput{" +
                "mockResponse=" + mockResponse +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private RestMockResponse mockResponse;

        private Builder() {
        }

        public Builder mockResponse(final RestMockResponse mockResponse){
            this.mockResponse = mockResponse;
            return this;
        }

        public CreateRestMockResponseOutput build(){
            return new CreateRestMockResponseOutput(this.mockResponse);
        }

    }
}
