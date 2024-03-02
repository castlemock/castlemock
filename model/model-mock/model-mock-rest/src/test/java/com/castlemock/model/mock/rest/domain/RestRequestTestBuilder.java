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

package com.castlemock.model.mock.rest.domain;

import com.castlemock.model.core.http.HttpMethod;

import java.util.Set;

public final class RestRequestTestBuilder {

    private RestRequestTestBuilder() {

    }

    public static RestRequest.Builder builder(){
        return RestRequest.builder()
                .uri("/test")
                .body("Rest request body")
                .contentType("application/json")
                .httpMethod(HttpMethod.POST)
                .httpHeaders(Set.of())
                .httpParameters(Set.of());
    }

    public static RestRequest build() {
        return builder().build();
    }
}
