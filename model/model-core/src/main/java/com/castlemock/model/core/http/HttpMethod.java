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

package com.castlemock.model.core.http;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlType
@XmlEnum
public enum HttpMethod {
    GET, POST, PUT, HEAD, DELETE, OPTIONS, TRACE, PATCH;

    /**
     * Parse an input String and convert it to a {@link HttpMethod}.
     * @param input The input String which will be converted.
     * @return A {@link HttpMethod} based on the input String. Null will be
     *          return if no match was found.
     * @since 1.10
     */
    public static Optional<HttpMethod> getValue(final String input){
        return Optional.ofNullable(input)
                .map(String::toUpperCase)
                .map(HttpMethod::valueOf);
    }

}
