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

import java.util.List;

public final class RestMockResponseTestBuilder {

    private RestMockResponseTestBuilder() {

    }

    public static RestMockResponse.Builder builder(){
        return RestMockResponse
                .builder()
                .id("r1eXT3")
                .name("Response: successful operation (application/xml)")
                .body("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><User><id>${RANDOM_LONG()}</id><username>${RANDOM_STRING()}</username><firstName>${RANDOM_STRING()}</firstName><lastName>${RANDOM_STRING()}</lastName><email>${RANDOM_STRING()}</email><password>${RANDOM_STRING()}</password><phone>${RANDOM_STRING()}</phone><userStatus>${RANDOM_INTEGER()}</userStatus></User>")
                .methodId("OSIFJL")
                .status(RestMockResponseStatus.ENABLED)
                .usingExpressions(true)
                .httpStatusCode(200)
                .httpHeaders(List.of())
                .contentEncodings(List.of())
                .parameterQueries(List.of())
                .xpathExpressions(List.of())
                .jsonPathExpressions(List.of())
                .headerQueries(List.of());
    }

    public static RestMockResponse build(){
        return builder().build();
    }

}
