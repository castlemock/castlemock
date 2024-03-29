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

package com.castlemock.model.core.utility;

import com.castlemock.model.core.http.HttpHeader;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HeaderMatchUtility {

    private static final String DASH = "-";
    private static final String GREATHER_THAN_SIGN = ">";
    private static final String SPACE = " ";

    private static final Pattern VALID_HEADER_REGEX =
            Pattern.compile("^[A-Z0-9()=*,:;/_-]+->[A-Z0-9()=*,:;/_-]+$", Pattern.CASE_INSENSITIVE);

    private HeaderMatchUtility() {

    }

    public static boolean isValidHeaderParameterExpr(final List<HttpHeader> headers,
                                                     String expression) {
        expression = expression.replace(SPACE, "");
        if (validate(expression)) {
            for (HttpHeader httpHeader : headers) {
                String header = httpHeader.getName() + DASH + GREATHER_THAN_SIGN + httpHeader.getValue();
                if (header.equalsIgnoreCase(expression)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean validate(final String headerString) {
        Matcher matcher = VALID_HEADER_REGEX.matcher(headerString);
        return matcher.find();
    }

}
