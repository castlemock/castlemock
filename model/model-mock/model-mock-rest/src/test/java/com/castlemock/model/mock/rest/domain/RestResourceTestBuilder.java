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
import java.util.Map;

public final class RestResourceTestBuilder {


    private RestResourceTestBuilder(){

    }

    public static RestResource.Builder builder(){
        return RestResource
                .builder()
                .id("9gsIpq")
                .name("Users")
                .uri("/user/{username}")
                .applicationId("QLP2Nk")
                .invokeAddress("http://localhost:8080/mock/rest/project/EqbLQU/application/QLP2Nk/user/{username}")
                .methods(List.of(RestMethodTestBuilder.build()))
                .statusCount(Map.of());
    }

    public static RestResource build() {
        return builder().build();
    }
}
