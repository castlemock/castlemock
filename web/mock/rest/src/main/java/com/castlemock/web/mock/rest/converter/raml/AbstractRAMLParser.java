/*
 * Copyright 2017 Karl Dahlgren
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


package com.castlemock.web.mock.rest.converter.raml;


/**
 * @author Karl Dahlgren
 * @since 1.11
 */
abstract class AbstractRAMLParser {

    protected static final int DEFAULT_RESPONSE_CODE = 200;
    protected static final String RESPONSE_NAME_PREFIX = "Response ";

    /**
     * The method will extract the HTTP response status code. The provided response code
     * is a {@link String} and should be parsed to an integer. However, the response code
     * is not always the actual response code. In fact, it can be anything. Therefore,
     * upon {@link NumberFormatException} the default response code will be returned: 200.
     * @param responseCode The response code that will be parsed into an integer.
     * @return The parsed response code. 200 if the parsing failed.
     */
    protected int extractHttpStatusCode(final String responseCode){
        try {
            return Integer.parseInt(responseCode);
        } catch (Exception e){
            return DEFAULT_RESPONSE_CODE;
        }
    }
}
