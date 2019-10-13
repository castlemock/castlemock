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

package com.castlemock.core.basis.model.http.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Karl Dahlgren
 * @since 1.18
 */
@XmlType
@XmlEnum(String.class)
public enum ContentEncoding {

    GZIP, DEFLATE;

    /**
     * Parse an input String and convert it to a {@link ContentEncoding}.
     * @param input The input String which will be converted.
     * @return A {@link ContentEncoding} based on the input String. Null will be
     *          return if no match was found.
     * @since 1.10
     */
    public static ContentEncoding getValue(String input){
        input = input.toUpperCase();
        try {
            return ContentEncoding.valueOf(input);
        } catch (Exception e){}
        return null;
    }

}
