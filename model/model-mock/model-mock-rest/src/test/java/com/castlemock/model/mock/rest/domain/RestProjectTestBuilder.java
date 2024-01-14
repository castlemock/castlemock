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

import java.time.Instant;
import java.util.Date;
import java.util.List;

public final class RestProjectTestBuilder {


    private RestProjectTestBuilder() {

    }

    public static RestProject.Builder builder(){
        return RestProject.builder()
                .id("EqbLQU")
                .name("Swagger")
                .created(Date.from(Instant.now()))
                .updated(Date.from(Instant.now()))
                .description("Project generated from Swagger file")
                .applications(List.of(RestApplicationTestBuilder.builder().build()));
    }

    public static RestProject build() {
        return builder().build();
    }
}
