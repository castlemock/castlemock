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

package com.castlemock.repository.core.file.http.converter;

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.repository.core.file.http.model.HttpHeaderFile;

import java.util.Optional;

public final class HttpHeaderFileConverter {

    private HttpHeaderFileConverter() {

    }

    public static HttpHeader toHttpHeader(final HttpHeaderFile httpHeaderFile) {
        return HttpHeader.builder()
                .name(httpHeaderFile.getName())
                .value(Optional.ofNullable(httpHeaderFile.getValue()).orElse(""))
                .build();
    }

}
