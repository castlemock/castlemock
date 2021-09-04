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

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonPathUtilityTest {

    @Test
    public void testExpression1() {
        final String body = getBody();
        final String expression = "$.store.book[?(@.price < 10)]";
        final boolean result = JsonPathUtility.isValidJsonPathExpr(body, expression);
        assertTrue(result);
    }

    @Test
    public void testExpression2() {
        final String body = getBody();
        final String expression = "$.store.book[?(@.author == 'Author 1')]";
        final boolean result = JsonPathUtility.isValidJsonPathExpr(body, expression);
        assertTrue(result);
    }

    @Test
    public void testExpression3() {
        final String body = getBody();
        final String expression = "$.store.book[?(@.author == 'Karl Dahlgren')]";
        final boolean result = JsonPathUtility.isValidJsonPathExpr(body, expression);
        assertFalse(result);
    }

    @Test
    public void testExpression4() {
        final String body = getBody();
        final String expression = "$.[?(@.expensive == 10)]";
        final boolean result = JsonPathUtility.isValidJsonPathExpr(body, expression);
        assertTrue(result);
    }

    @Test
    public void testExpression5() {
        final String body = getBody();
        final String expression = "$.[?(@.expensive == 11)]";
        final boolean result = JsonPathUtility.isValidJsonPathExpr(body, expression);
        assertFalse(result);
    }

    @Test
    public void testGetValue1(){
        final String body = getBody();
        final String expression = "$.store.book[?(@.price > 20)].author";
        final Optional<String> value = JsonPathUtility.getValueWithJsonPathExpr(body, expression);
        assertEquals("Author 3", value.orElse(null));
    }

    @Test
    public void testGetValueList(){
        final String body = getBody();
        final String expression = "$.store.book[*]";
        final Optional<String> value = JsonPathUtility.getValueWithJsonPathExpr(body, expression);
        assertEquals("[{\"category\":\"reference\",\"author\":\"Author 1\",\"title\":\"Book title 1\",\"price\":8.95},{\"category\":\"fiction\",\"author\":\"Author 2\",\"title\":\"Book title 2\",\"isbn\":\"1-553-21311-3\",\"price\":8.99},{\"category\":\"fiction\",\"author\":\"Author 3\",\"title\":\"Book title 3\",\"isbn\":\"1-395-19395-8\",\"price\":22.99}]", value.orElse(null));
    }

    @Test
    public void testGetValueList2(){
        final String body = getBody();
        final String expression = "$.store.book[?(@.price > 1)].author";
        final Optional<String> value = JsonPathUtility.getValueWithJsonPathExpr(body, expression);
        assertEquals("[\"Author 1\",\"Author 2\",\"Author 3\"]", value.orElse(null));
    }

    @Test
    public void shouldParseValueWhenPropertyIsDefinite(){
        final String body = getBodyWithDefiniteProperty();
        final String expression = "$.definite";
        final Optional<String> value = JsonPathUtility.getValueWithJsonPathExpr(body, expression);
        assertEquals("value", value.orElse(null));
    }

    @Test
    public void testGetValueLength(){
        final String body = getBody();
        final String expression = "$.store.book.length()";
        final Optional<String> value = JsonPathUtility.getValueWithJsonPathExpr(body, expression);
        assertEquals("3", value.orElse(null));
    }

    private String getBody() {
        return "{\n" +
                "  \"store\": {\n" +
                "    \"book\": [\n" +
                "      {\n" +
                "        \"category\": \"reference\",\n" +
                "        \"author\": \"Author 1\",\n" +
                "        \"title\": \"Book title 1\",\n" +
                "        \"price\": 8.95\n" +
                "      },\n" +
                "      {\n" +
                "        \"category\": \"fiction\",\n" +
                "        \"author\": \"Author 2\",\n" +
                "        \"title\": \"Book title 2\",\n" +
                "        \"isbn\": \"1-553-21311-3\",\n" +
                "        \"price\": 8.99\n" +
                "      },\n" +
                "      {\n" +
                "        \"category\": \"fiction\",\n" +
                "        \"author\": \"Author 3\",\n" +
                "        \"title\": \"Book title 3\",\n" +
                "        \"isbn\": \"1-395-19395-8\",\n" +
                "        \"price\": 22.99\n" +
                "      }\n" +
                "    ],\n" +
                "    \"bicycle\": {\n" +
                "      \"color\": \"red\",\n" +
                "      \"price\": 19.95\n" +
                "    }\n" +
                "  },\n" +
                "  \"expensive\": 10\n" +
                "}";
    }

    private String getBodyWithDefiniteProperty() {
        return "{\n" +
                "   \"definite\": \"value\"\n" +
                "}";
    }

}
