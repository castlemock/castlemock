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

package com.castlemock.service.mock.soap.project.output;

import com.castlemock.model.core.Output;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class DeleteSoapMockResponseOutput implements Output {

    private final SoapMockResponse mockResponse;

    private DeleteSoapMockResponseOutput(final Builder builder){
        this.mockResponse = builder.mockResponse;
    }

    public Optional<SoapMockResponse> getMockResponse() {
        return Optional.ofNullable(mockResponse);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DeleteSoapMockResponseOutput that = (DeleteSoapMockResponseOutput) o;
        return Objects.equals(mockResponse, that.mockResponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mockResponse);
    }

    @Override
    public String toString() {
        return "DeleteSoapMockResponseOutput{" +
                "mockResponse=" + mockResponse +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private SoapMockResponse mockResponse;

        private Builder(){

        }

        public Builder mockResponse(final SoapMockResponse mockResponse){
            this.mockResponse = mockResponse;
            return this;
        }

        public DeleteSoapMockResponseOutput build(){
            return new DeleteSoapMockResponseOutput(this);
        }
    }

}
