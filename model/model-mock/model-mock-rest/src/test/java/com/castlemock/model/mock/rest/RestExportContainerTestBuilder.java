/*
 * Copyright 2024 Karl Dahlgren
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

package com.castlemock.model.mock.rest;

import com.castlemock.model.mock.rest.domain.RestApplicationTestBuilder;
import com.castlemock.model.mock.rest.domain.RestMethodTestBuilder;
import com.castlemock.model.mock.rest.domain.RestMockResponseTestBuilder;
import com.castlemock.model.mock.rest.domain.RestProjectTestBuilder;
import com.castlemock.model.mock.rest.domain.RestResourceTestBuilder;

import java.util.List;

public final class RestExportContainerTestBuilder {
    
    private RestExportContainerTestBuilder() {
        
    }

    public static RestExportContainer.Builder builder() {
        return RestExportContainer.builder()
                .project(RestProjectTestBuilder.builder().build())
                .applications(List.of(RestApplicationTestBuilder.builder().build()))
                .methods(List.of(RestMethodTestBuilder.builder().build()))
                .mockResponses(List.of(RestMockResponseTestBuilder.builder().build()))
                .resources(List.of(RestResourceTestBuilder.builder().build()));
    }

    public static RestExportContainer build() {
        return builder().build();
    }
    
}
