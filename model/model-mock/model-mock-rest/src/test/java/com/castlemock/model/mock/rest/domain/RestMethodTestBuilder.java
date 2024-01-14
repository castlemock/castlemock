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

import java.util.List;

public final class RestMethodTestBuilder {

    private RestMethodTestBuilder(){

    }

    public static RestMethod.Builder builder() {
        return RestMethod.builder()
                .id("OSIFJL")
                .name("getUserByName")
                .resourceId("9gsIpq")
                .defaultBody("")
                .httpMethod(HttpMethod.GET)
                .forwardedEndpoint("")
                .status(RestMethodStatus.MOCKED)
                .responseStrategy(RestResponseStrategy.RANDOM)
                .currentResponseSequenceIndex(0)
                .simulateNetworkDelay(Boolean.FALSE)
                .networkDelay(0L)
                .defaultMockResponseId("")
                .mockResponses(List.of())
                .uri("/")
                .defaultResponseName("")
                .automaticForward(Boolean.FALSE)
                .mockResponses(List.of(RestMockResponseTestBuilder.builder().build()));
    }

    public static RestMethod build() {
        return builder().build();
    }
}
