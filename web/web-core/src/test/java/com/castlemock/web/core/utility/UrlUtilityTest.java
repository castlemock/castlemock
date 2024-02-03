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

package com.castlemock.web.core.utility;


import com.castlemock.service.core.utility.UrlUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;


/**
 * @since 1.8
 * @author Karl Dahlgren
 */
public class UrlUtilityTest {

    @Test
    public void isPatternMatch1(){
        Assertions.assertTrue(UrlUtility.isPatternMatch("/user", "/user"));
    }

    @Test
    public void isPatternMatch2(){
        Assertions.assertTrue(UrlUtility.isPatternMatch("/user/id", "/user/id"));
    }


    @Test
    public void isPatternMatch3(){
        Assertions.assertTrue(UrlUtility.isPatternMatch("/user/id.json", "/user/id.json"));
    }

    @Test
    public void isPatternMatch4(){
        Assertions.assertTrue(UrlUtility.isPatternMatch("/user/{id}", "/user/1"));
    }

    @Test
    public void isPatternMatch5(){
        Assertions.assertTrue(UrlUtility.isPatternMatch("/user/{id}.json", "/user/1.json"));
    }

    @Test
    public void isPatternMatch6(){
        Assertions.assertTrue(UrlUtility.isPatternMatch("/user/1.{format}", "/user/1.json"));
        Assertions.assertTrue(UrlUtility.isPatternMatch("/user/1.{format}", "/user/1.xml"));
        Assertions.assertFalse(UrlUtility.isPatternMatch("/user/1.{format}", "/user/2.xml"));
    }

    @Test
    public void isPatternMatch7(){
        Assertions.assertTrue(UrlUtility.isPatternMatch("/user/{id}.{format}", "/user/1.json"));
        Assertions.assertTrue(UrlUtility.isPatternMatch("/user/{id}.{format}", "/user/2.xml"));
    }

    @Test
    public void isPatternMatch8(){
        Assertions.assertTrue(UrlUtility.isPatternMatch("/company/{company}/user/{id}.{format}", "/company/Castle Mock/user/1.json"));
    }

    @Test
    public void isPatternMatch9(){
        Assertions.assertFalse(UrlUtility.isPatternMatch("/user/{id", "/user/1"));
    }

    @Test
    public void isPatternMatch10(){
        Assertions.assertTrue(UrlUtility.isPatternMatch("/user/id?test={hej}", "/user/id"));
    }

    @Test
    public void canGetPathParameters(){
        final Set<String> parts = UrlUtility.getPathParameters("/rest/api/user/{userId}/");
        Assertions.assertEquals(1, parts.size());
        Assertions.assertTrue(parts.contains("userId"));
    }

    @Test
    public void canGetQueryStringParameters(){
        final Map<String, Set<String>> queryParams = Map.of("userId", Set.of("1"));
        final Map<String, Set<String>> map = UrlUtility.getQueryStringParameters("/rest/api/user/?userId={userId}", queryParams);

        Assertions.assertTrue(map.containsKey("userId"));
        Assertions.assertEquals(1, map.get("userId").size());
        Assertions.assertTrue(map.get("userId").contains("1"));
    }

    @Test
    public void canGetQueryStringParametersWithDifferentName(){
        final Map<String, Set<String>> queryParams = Map.of("userId", Set.of("1"));
        final Map<String, Set<String>> map = UrlUtility.getQueryStringParameters("/rest/api/user/?userId={id}", queryParams);

        Assertions.assertTrue(map.containsKey("userId"));
        Assertions.assertEquals(1, map.get("userId").size());
        Assertions.assertTrue(map.get("userId").contains("1"));
    }

    @Test
    public void canGetQueryStringMultipleParameters(){
        final Map<String, Set<String>> queryParams = Map.of(
                "userId", Set.of("1"),
                "username", Set.of("Karl"));
        final Map<String, Set<String>> map = UrlUtility.getQueryStringParameters("/rest/api/user/?userId={userId}&username={username}", queryParams);

        Assertions.assertTrue(map.containsKey("userId"));
        Assertions.assertTrue(map.containsKey("username"));
        Assertions.assertEquals(1, map.get("userId").size());
        Assertions.assertEquals(1, map.get("username").size());
        Assertions.assertTrue(map.get("userId").contains("1"));
        Assertions.assertTrue(map.get("username").contains("Karl"));
    }

    @Test
    public void canGetQueryStringMultipleParameterValues(){
        final Map<String, Set<String>> queryParams = Map.of(
                "userId", Set.of("1", "2"));
        final Map<String, Set<String>> map = UrlUtility.getQueryStringParameters("/rest/api/user/?userId={userId}&userId=1", queryParams);

        Assertions.assertTrue(map.containsKey("userId"));
        Assertions.assertEquals(2, map.get("userId").size());
        Assertions.assertTrue(map.get("userId").contains("1"));
        Assertions.assertTrue(map.get("userId").contains("2"));
    }

    @Test
    public void getPathParametersShouldNotConsiderQueryParameters(){
        Map<String, Set<String>> pathParameters = UrlUtility.getPathParameters("/rest/api/{user}?param={param}", "/rest/api/johndoe");
        Assertions.assertEquals(1, pathParameters.size());
        Assertions.assertTrue(pathParameters.containsKey("user"));
        Assertions.assertEquals(1, pathParameters.get("user").size());
        Assertions.assertTrue(pathParameters.get("user").contains("johndoe"));
    }

    @Test
    public void getQueryStringParameters(){
        final Set<String> parts = UrlUtility.getPathParameters("/rest/api/user/?userId={userId}&id={id}");
        Assertions.assertEquals(2, parts.size());
        Assertions.assertTrue(parts.contains("userId"));
        Assertions.assertTrue(parts.contains("id"));
    }

    @Test
    public void canGetPathParametersMultiple(){
        final Set<String> parts = UrlUtility.getPathParameters("/rest/api/user/{userId}/{format}/{parameter}");
        Assertions.assertEquals(3, parts.size());
        Assertions.assertTrue(parts.contains("userId"));
        Assertions.assertTrue(parts.contains("format"));
        Assertions.assertTrue(parts.contains("parameter"));
    }

    @Test
    public void getPatternMatchScore1(){
        Assertions.assertEquals(0, UrlUtility.getPatternMatchScore("/user", "/user"));
    }

    @Test
    public void getPatternMatchScore2(){
        Assertions.assertEquals(1, UrlUtility.getPatternMatchScore("/user/{id}.json", "/user/1.json"));
    }
}
