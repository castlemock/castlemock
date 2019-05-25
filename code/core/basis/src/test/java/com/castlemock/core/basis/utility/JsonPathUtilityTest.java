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

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonPathUtilityTest {

    @Test
    public void testExpression1() {
        final String body = getBody();
        final HashMap<String, String> headers = getHeaders();
        final String expression = "$.store.book[?(@.price < 10)]";
        final boolean result = JsonPathUtility.isValidJsonPathExpr(headers, body, expression);
        assertTrue(result);
    }

    @Test
    public void testExpression2() {
        final String body = getBody();
        final HashMap<String, String> headers = getHeaders();
        final String expression = "$.store.book[?(@.author == 'Nigel Rees')]";
        final boolean result = JsonPathUtility.isValidJsonPathExpr(headers, body, expression);
        assertTrue(result);
    }

    @Test
    public void testExpression3() {
        final String body = getBody();
        final HashMap<String, String> headers = getHeaders();
        final String expression = "$.store.book[?(@.author == 'Karl Dahlgren')]";
        final boolean result = JsonPathUtility.isValidJsonPathExpr(headers, body, expression);
        assertFalse(result);
    }

    @Test
    public void testExpression4() {
        final String body = getBody();
        final HashMap<String, String> headers = getHeaders();
        final String expression = "$.[?(@.expensive == 10)]";
        final boolean result = JsonPathUtility.isValidJsonPathExpr(headers, body, expression);
        assertTrue(result);
    }

    @Test
    public void testExpression5() {
        final String body = getBody();
        final HashMap<String, String> headers = getHeaders();
        final String expression = "$.[?(@.expensive == 11)]";
        final boolean result = JsonPathUtility.isValidJsonPathExpr(headers, body, expression);
        assertFalse(result);
    }

    private String getBody() {
        return "{\n" +
                "  \"store\": {\n" +
                "    \"book\": [\n" +
                "      {\n" +
                "        \"category\": \"reference\",\n" +
                "        \"author\": \"Nigel Rees\",\n" +
                "        \"title\": \"Sayings of the Century\",\n" +
                "        \"price\": 8.95\n" +
                "      },\n" +
                "      {\n" +
                "        \"category\": \"fiction\",\n" +
                "        \"author\": \"Herman Melville\",\n" +
                "        \"title\": \"Moby Dick\",\n" +
                "        \"isbn\": \"0-553-21311-3\",\n" +
                "        \"price\": 8.99\n" +
                "      },\n" +
                "      {\n" +
                "        \"category\": \"fiction\",\n" +
                "        \"author\": \"J.R.R. Tolkien\",\n" +
                "        \"title\": \"The Lord of the Rings\",\n" +
                "        \"isbn\": \"0-395-19395-8\",\n" +
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

    private HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("content-length", "966");
        headers.put("content-type", "text/plain");
        headers.put("connection", "keep-alive");
        return headers;
    }

}
