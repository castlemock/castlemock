/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.core.basis.utility;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.35
 */
public final class JsonPathUtility {

    private JsonPathUtility() {

    }

    public static boolean isValidJsonPathExpr(final String body,
                                              final String expression) {
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(body);
        final JSONArray array = JsonPath.read(document, expression);
        return !array.isEmpty();
    }

    /**
     * The method extracts a value from the provided <code>body</code> with
     * the JSON Path <code>expression</code>
     * @param body The body which contains the value that will be extracted.
     * @param expression The expression used to locate the requested value.
     * @return The value or an empty optional.
     */
    public static Optional<String> getValueWithJsonPathExpr(final String body,
                                                            final String expression) {
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(body);
        final JSONArray array = JsonPath.read(document, expression);

        return array
                .stream()
                .findFirst()
                .map(Object::toString);
    }
}
