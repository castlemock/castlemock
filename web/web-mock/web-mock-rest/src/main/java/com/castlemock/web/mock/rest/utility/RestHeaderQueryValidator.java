/*
 * Copyright 2019 Karl Dahlgren
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

package com.castlemock.web.mock.rest.utility;

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.mock.rest.domain.RestHeaderQuery;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class RestHeaderQueryValidator {

    private RestHeaderQueryValidator(){

    }

    public static boolean validate(final List<RestHeaderQuery> headerQueries,
                                   final Set<HttpHeader> headers){

        for(HttpHeader header : headers){

            final Set<RestHeaderQuery> matching = headerQueries.stream()
                    .filter(headerQuery -> headerQuery.getHeader().equalsIgnoreCase(header.getName()))
                    .filter(headerQuery -> validate(header.getValue(), headerQuery))
                    .collect(Collectors.toSet());

            if(!matching.isEmpty()){
                return true;
            }
        }

        return false;
    }

    private static boolean validate(final String inputQuery,
                                    final RestHeaderQuery headerQuery){
        if(headerQuery.getMatchAny()){
            return true;
        } else if(headerQuery.getMatchRegex()){
            final Pattern pattern = headerQuery.getMatchCase() ?
                    Pattern.compile(headerQuery.getQuery()) :
                    Pattern.compile(headerQuery.getQuery(), Pattern.CASE_INSENSITIVE);

            final Matcher matcher = pattern.matcher(inputQuery);
            return matcher.matches();
        } else if(headerQuery.getMatchCase()){
            return inputQuery.equals(headerQuery.getQuery());
        }

        return inputQuery.equalsIgnoreCase(headerQuery.getQuery());
    }

}
