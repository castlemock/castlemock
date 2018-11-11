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

package com.castlemock.core.mock.soap.service.project.output;

import com.castlemock.core.basis.model.Output;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class ReadSoapMockResponseOutput implements Output{

    private final SoapMockResponse mockResponse;

    private ReadSoapMockResponseOutput(final Builder builder) {
        this.mockResponse = Objects.requireNonNull(builder.mockResponse);
    }

    public SoapMockResponse getMockResponse() {
        return mockResponse;
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

        public ReadSoapMockResponseOutput build(){
            return new ReadSoapMockResponseOutput(this);
        }
    }
}
