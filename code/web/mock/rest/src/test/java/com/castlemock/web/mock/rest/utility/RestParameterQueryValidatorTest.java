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

import com.castlemock.core.mock.rest.model.project.domain.RestParameterQuery;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RestParameterQueryValidatorTest {

    @Test
    public void canValidateMatch(){
        final RestParameterQuery parameterQuery1 = new RestParameterQuery();
        parameterQuery1.setParameter("userId");
        parameterQuery1.setQuery("123");
        final RestParameterQuery parameterQuery2 = new RestParameterQuery();
        parameterQuery2.setParameter("country");
        parameterQuery2.setQuery("SE");

        final List<RestParameterQuery> parameterQueries =
                ImmutableList.of(parameterQuery1, parameterQuery2);

        final Map<String, String> pathParameters =
                ImmutableMap.of("userId", "123", "country", "SE");

        final boolean result = RestParameterQueryValidator.validate(parameterQueries, pathParameters);

        assertTrue(result);
    }

    @Test
    public void canValidateNoMatch(){
        final RestParameterQuery parameterQuery1 = new RestParameterQuery();
        parameterQuery1.setParameter("userId");
        parameterQuery1.setQuery("345");
        final RestParameterQuery parameterQuery2 = new RestParameterQuery();
        parameterQuery2.setParameter("country");
        parameterQuery2.setQuery("SE");

        final List<RestParameterQuery> parameterQueries =
                ImmutableList.of(parameterQuery1, parameterQuery2);

        final Map<String, String> pathParameters =
                ImmutableMap.of("userId", "123", "country", "SE");

        final boolean result = RestParameterQueryValidator.validate(parameterQueries, pathParameters);

        assertFalse(result);
    }

    @Test
    public void canValidateMissing(){
        final RestParameterQuery parameterQuery1 = new RestParameterQuery();
        parameterQuery1.setParameter("userId");
        parameterQuery1.setQuery("123");

        final List<RestParameterQuery> parameterQueries = ImmutableList.of(parameterQuery1);

        final Map<String, String> pathParameters =
                ImmutableMap.of("userId", "123", "country", "SE");

        final boolean result = RestParameterQueryValidator.validate(parameterQueries, pathParameters);

        assertFalse(result);
    }

    @Test
    public void canValidateMultiple(){
        final RestParameterQuery parameterQuery1 = new RestParameterQuery();
        parameterQuery1.setParameter("userId");
        parameterQuery1.setQuery("123");
        final RestParameterQuery parameterQuery2 = new RestParameterQuery();
        parameterQuery2.setParameter("country");
        parameterQuery2.setQuery("SE");
        final RestParameterQuery parameterQuery3 = new RestParameterQuery();
        parameterQuery3.setParameter("country");
        parameterQuery3.setQuery("NO");

        final List<RestParameterQuery> parameterQueries =
                ImmutableList.of(parameterQuery1, parameterQuery2, parameterQuery3);

        final Map<String, String> pathParameters =
                ImmutableMap.of("userId", "123", "country", "SE");

        final boolean result = RestParameterQueryValidator.validate(parameterQueries, pathParameters);

        assertTrue(result);
    }

    @Test
    public void canValidateNotMatchCase(){
        final RestParameterQuery parameterQuery1 = new RestParameterQuery();
        parameterQuery1.setParameter("username");
        parameterQuery1.setQuery("karl");
        parameterQuery1.setMatchCase(false);

        final List<RestParameterQuery> parameterQueries = ImmutableList.of(parameterQuery1);

        final Map<String, String> pathParameters =
                ImmutableMap.of("username", "kArL");

        final boolean result = RestParameterQueryValidator.validate(parameterQueries, pathParameters);

        assertTrue(result);
    }

    @Test
    public void canValidateMatchCase(){
        final RestParameterQuery parameterQuery1 = new RestParameterQuery();
        parameterQuery1.setParameter("username");
        parameterQuery1.setQuery("karl");
        parameterQuery1.setMatchCase(true);

        final List<RestParameterQuery> parameterQueries = ImmutableList.of(parameterQuery1);

        final Map<String, String> pathParameters =
                ImmutableMap.of("username", "kArL");

        final boolean result = RestParameterQueryValidator.validate(parameterQueries, pathParameters);

        assertFalse(result);
    }

    @Test
    public void canValidateMatchAny(){
        final RestParameterQuery parameterQuery1 = new RestParameterQuery();
        parameterQuery1.setParameter("username");
        parameterQuery1.setMatchAny(true);

        final List<RestParameterQuery> parameterQueries = ImmutableList.of(parameterQuery1);

        final Map<String, String> pathParameters =
                ImmutableMap.of("username", "username");

        final boolean result = RestParameterQueryValidator.validate(parameterQueries, pathParameters);

        assertTrue(result);
    }

    @Test
    public void canValidateMatchReqexCaseSensetive(){
        final RestParameterQuery parameterQuery1 = new RestParameterQuery();
        parameterQuery1.setParameter("username");
        parameterQuery1.setQuery("a*b");
        parameterQuery1.setMatchRegex(true);
        parameterQuery1.setMatchCase(true);

        final List<RestParameterQuery> parameterQueries = ImmutableList.of(parameterQuery1);

        assertTrue(RestParameterQueryValidator.validate(parameterQueries, ImmutableMap.of("username", "aaaaab")));
        assertFalse(RestParameterQueryValidator.validate(parameterQueries, ImmutableMap.of("username", "AAAAB")));
    }

    @Test
    public void canValidateMatchReqexIgnoreCase(){
        final RestParameterQuery parameterQuery1 = new RestParameterQuery();
        parameterQuery1.setParameter("username");
        parameterQuery1.setQuery("a*b");
        parameterQuery1.setMatchRegex(true);
        parameterQuery1.setMatchCase(false);

        final List<RestParameterQuery> parameterQueries = ImmutableList.of(parameterQuery1);

        assertTrue(RestParameterQueryValidator.validate(parameterQueries, ImmutableMap.of("username", "aaaaab")));
        assertTrue(RestParameterQueryValidator.validate(parameterQueries, ImmutableMap.of("username", "AAAAB")));
    }

}
