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

package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestHeaderQuery;
import com.castlemock.repository.rest.file.project.model.RestHeaderQueryFile;

public final class RestHeaderQueryConverter {

    private RestHeaderQueryConverter() {

    }

    public static RestHeaderQueryFile toRestHeaderQuery(final RestHeaderQuery restHeaderQuery) {
        return RestHeaderQueryFile.builder()
                .header(restHeaderQuery.getHeader())
                .query(restHeaderQuery.getQuery())
                .matchAny(restHeaderQuery.getMatchAny())
                .matchCase(restHeaderQuery.getMatchCase())
                .matchRegex(restHeaderQuery.getMatchRegex())
                .build();
    }

}
