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

package com.castlemock.web.mock.rest.utility;

import com.castlemock.model.mock.rest.domain.RestParameterQuery;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class RestParameterQueryValidator {

    private RestParameterQueryValidator(){

    }

    public static boolean validate(final List<RestParameterQuery> parameterQueries,
                                   final Map<String, Set<String>> pathParameters){
        for(Map.Entry<String, Set<String>> pathParameterEntry : pathParameters.entrySet()){
            final String pathParameter = pathParameterEntry.getKey();
            final Set<RestParameterQuery> matching = pathParameterEntry.getValue()
                    .stream()
                    .flatMap(pathQuery -> parameterQueries.stream()
                            .filter(parameterQuery -> parameterQuery.getParameter().equals(pathParameter))
                            .filter(parameterQuery -> validate(pathQuery, parameterQuery)))
                    .collect(Collectors.toSet());

            if (matching.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private static boolean validate(final String pathQuery,
                                    final RestParameterQuery parameterQuery){
        if(parameterQuery.getMatchAny()){
            return true;
        } else if(parameterQuery.getMatchRegex()){
            final Pattern pattern = parameterQuery.getMatchCase() ?
                    Pattern.compile(parameterQuery.getQuery()) :
                    Pattern.compile(parameterQuery.getQuery(), Pattern.CASE_INSENSITIVE);

            final Matcher matcher = pattern.matcher(pathQuery);
            return matcher.matches();
        } else if(parameterQuery.getMatchCase()){
            return pathQuery.equals(parameterQuery.getQuery());
        }

        return pathQuery.equalsIgnoreCase(parameterQuery.getQuery());
    }

}
