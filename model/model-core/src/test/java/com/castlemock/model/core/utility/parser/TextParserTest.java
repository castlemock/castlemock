/*
 * Copyright 2016 Karl Dahlgren
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

package com.castlemock.model.core.utility.parser;

import com.castlemock.model.core.http.HttpParameter;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


/**
 * @since 1.6
 * @author Karl Dahlgren
 */
public class TextParserTest {
    private final TextParser textParser = new TextParser();
    private TextParser textParserMockedExpressions;

    
    @BeforeEach
    void init() {
        this.textParserMockedExpressions = new TextParser(new TextParseMockConfig().getMockedExpressions());
    }

    @Test
    public void testParseNull(){
        final String output = this.textParser.parse(null)
                .orElse(null);
        Assertions.assertNull(output);
    }

    @Test
    public void testParseRandomInteger(){
        String input = "Hello this is a ${RANDOM_INTEGER}.";
        String output = textParser.parse(input)
                .orElse(null);
        Assertions.assertNotEquals(input, output);
        Assertions.assertTrue(Objects.requireNonNull(output).matches("Hello this is a (.*?)."));
        
        input = "a: ${RANDOM_INTEGER}, b: ${RANDOM_INTEGER()}.";
        output = textParserMockedExpressions.parse(input)
                .orElse(null);
        Assertions.assertEquals("a: 1, b: 2.", output);
    }
    
    @Test
    public void testParseRandomDouble(){
        String input = "Hello this is a ${RANDOM_DOUBLE}.";
        String output = textParser.parse(input).orElse(null);
        Assertions.assertNotEquals(input, output);
        Assertions.assertTrue(Objects.requireNonNull(output).matches("Hello this is a (.*?)."));
        
        input = "a: ${RANDOM_DOUBLE}, b: ${RANDOM_DOUBLE}.";
        output = textParserMockedExpressions.parse(input).orElse(null);
        Assertions.assertEquals("a: 9.07231552E8, b: 1.971414777E9.", output);
    }

    @Test
    public void testParseRandomLong(){
        String input = "Hello this is a ${RANDOM_LONG}.";
        String output = textParser.parse(input).orElse(null);
        Assertions.assertNotEquals(input, output);
        Assertions.assertTrue(Objects.requireNonNull(output).matches("Hello this is a (.*?)."));
        
        input = "a: ${RANDOM_LONG}, b: ${RANDOM_LONG}.";
        output = textParserMockedExpressions.parse(input).orElse(null);
        Assertions.assertEquals("a: 8345852683487585923, b: 4396544029006714971.", output);
    }

    @Test
    public void testParseRandomFloat(){
        String input = "Hello this is a ${RANDOM_FLOAT}.";
        String output = this.textParser.parse(input).orElse(null);
        Assertions.assertNotEquals(input, output);
        Assertions.assertTrue(Objects.requireNonNull(output).matches("Hello this is a (.*?)."));
        
        input = "a: ${RANDOM_FLOAT}, b: ${RANDOM_FLOAT}.";
        output = textParserMockedExpressions.parse(input).orElse(null);
        Assertions.assertEquals("a: 0.77921385, b: 0.52562904.", output);
    }
    
    @Test
    public void testParseRandomBoolean(){
        final String input = "a: ${RANDOM_BOOLEAN}, b: ${RANDOM_BOOLEAN}.";
        final String output = textParserMockedExpressions.parse(input).orElse(null);
        Assertions.assertEquals("a: false, b: true.", output);
    }
    
    @Test
    public void testParseRandomDate(){
        final String input = "a: ${RANDOM_DATE}, b: ${RANDOM_DATE}.";
        final String output = textParserMockedExpressions.parse(input).orElse(null);
        Assertions.assertEquals("a: 2019-01-07, b: 2020-10-15.", output);
    }
    
    @Test
    public void testParseRandomString(){
        final String input = "a: ${RANDOM_STRING}, b: ${RANDOM_STRING}.";
        final String output = textParserMockedExpressions.parse(input).orElse(null);
        Assertions.assertEquals("a: XfDbd6sk90hxH0L, b: s80ho4iJTlda.", output);
    }
    
    @Test
    public void testParseRandomUUID(){
        final String input = "a: ${RANDOM_UUID}, b: ${RANDOM_UUID}.";
        final String output = textParserMockedExpressions.parse(input).orElse(null);
        Assertions.assertEquals("a: bf60cb57-cfd8-43bb-83f7-62b1584c29d7, b: af88f1d8-c8f6-4407-903c-18ffe6ac06c5.", output);
    }
    
    @Test
    public void testParseRandomEmail(){
        final String input = "a: ${RANDOM_EMAIL}, b: ${RANDOM_EMAIL}.";
        final String output = textParserMockedExpressions.parse(input).orElse(null);
        Assertions.assertEquals("a: Cng73JRbJT@RliQvDuVVN8.com, b: C0GlhHZ4vL@6ZNPRPmX.com.", output);
    }
    
    @Test
    public void testParseRandomPassword(){
        final String input = "a: ${RANDOM_PASSWORD}, b: ${RANDOM_PASSWORD}.";
        final String output = textParserMockedExpressions.parse(input).orElse(null);
        Assertions.assertEquals("a: j5HC7UGSVp, b: p70ggj9409ia.", output);
    }
    
    @Test
    public void testParseRandomDateTime(){
        final String input = "a: ${RANDOM_DATE_TIME}, b: ${RANDOM_DATE_TIME}.";
        final String output = textParserMockedExpressions.parse(input).orElse(null);
        Assertions.assertEquals("a: 2008-02-09T21:42:10, b: 2048-09-26T06:39:22.", output);
    }
    
    @Test
    public void testParseRandomEnum(){
        final String input = "a: ${RANDOM_ENUM(values=[\"available\",\"pending\",\"sold\"])}, b: ${RANDOM_ENUM(values=[\"X\",\"Y\",\"Z\"])}.";
        final String output = textParserMockedExpressions.parse(input).orElse(null);
        Assertions.assertEquals("a: available, b: Z.", output);
    }
    
    @Test
    public void testParsePathParameter() {
        final Map<String, Set<String>> pathParameters = new HashMap<>();
        pathParameters.put("param1", Set.of("X"));
        pathParameters.put("param2", Set.of("Y"));
        pathParameters.put("param3", Set.of("Z"));

        final String input = "a: ${PATH_PARAMETER(parameter=\"param1\")}, b: ${PATH_PARAMETER(parameter=\"param2\")}.";
        final String output = textParser.parse(input, new ExternalInputBuilder().pathParameters(pathParameters).build()).orElse(null);
        Assertions.assertEquals("a: X, b: Y.", output);
    }

    @Test
    public void testParsePathParameterMultipleValues() {
        final Map<String, Set<String>> pathParameters = new HashMap<>();
        pathParameters.put("param1", Set.of("X", "Y"));

        final String input = "${PATH_PARAMETER(parameter=\"param1\")}";
        final String output = textParser.parse(input, new ExternalInputBuilder().pathParameters(pathParameters).build()).orElse(null);
        Assertions.assertEquals("X", output);
    }


    @Test
    public void testParseQueryString() {
        final Set<HttpParameter> queryStringParameters = new HashSet<>();
        queryStringParameters.add(HttpParameter.builder()
                .name("queryParam1")
                .value("Apple")
                .build());
        queryStringParameters.add(HttpParameter.builder()
                .name("queryParam2")
                .value("Orange")
                .build());
        queryStringParameters.add(HttpParameter.builder()
                .name("queryParam3")
                .value("Banana")
                .build());
        queryStringParameters.add(HttpParameter.builder()
                .name("queryParam4")
                .value("Papaya")
                .build());

        final String input = "a: ${QUERY_STRING(query=\"queryParam1\")}, b: ${QUERY_STRING(query=\"queryParam2\")}.";
        final String output = textParser.parse(input, new ExternalInputBuilder().queryStringParameters(queryStringParameters).build()).orElse(null);
        Assertions.assertEquals("a: Apple, b: Orange.", output);
    }
        
    @Test
    public void testParseBodyJsonPath() throws IOException{
        final URL url = Resources.getResource("store-book.json");
        final String requestBodyJson = Resources.toString(url, StandardCharsets.UTF_8);

        final String input = "a: ${BODY_JSON_PATH(expression=\"$.store.book[0].title\")}, b: ${BODY_JSON_PATH(expression=\"$.store.book[1].title\")}.";
        final String output = textParser.parse(input, new ExternalInputBuilder().requestBody(requestBodyJson).build()).orElse(null);
        Assertions.assertEquals("a: Book title 1, b: Book title 2.", output);
    }
    
    @Test
    public void testParseUrlHost() {
        final String input = "a: ${URL_HOST()}, b: ${URL_HOST()}.";
        final String output = textParser.parse(input, new ExternalInputBuilder().requestUrl("http://localhost:8080/castlemock").build()).orElse(null);
        Assertions.assertEquals("a: localhost, b: localhost.", output);
    }
    
    @Test
    public void testParseBodyXPath() {
        final String requestBodyXml = """
                <user>
                   <id>1</id>
                   <name>Peter</name>
                </user>
                """;

        final String input = "a: ${BODY_XPATH(expression=\"//user/id/text()\")}, b: ${BODY_XPATH(expression=\"//user/name/text()\")}.";
        final String output = textParser.parse(input, new ExternalInputBuilder().requestBody(requestBodyXml).build()).orElse(null);
        Assertions.assertEquals("a: 1, b: Peter.", output);
    }
    
    @Test
    public void testParseFaker(){
        String input = "Hello this is a ${FAKER(api=\"name().fullName()\")}.";
        String output = textParser.parse(input).orElse(null);
        Assertions.assertNotEquals(input, output);
        Assertions.assertTrue(Objects.requireNonNull(output).matches("Hello this is a (.*?)."));
        
        input = "a: ${FAKER(api=\"name().fullName()\")}, b: ${FAKER(api=\"name().fullName()\")}.";
        output = textParserMockedExpressions.parse(input).orElse(null);
        Assertions.assertEquals("a: Peter, b: Paul.", output);
    }
    
    @Test
    public void testParseMultipleExpressions() {
        final String input = "a: ${RANDOM_INTEGER}, b: ${RANDOM_INTEGER()}, c: ${RANDOM_DATE()},"
                + " d: ${RANDOM_INTEGER(min=0,max=100)}, e: ${RANDOM_DATE()}, f: ${X}.${}";

        final String output = textParserMockedExpressions.parse(input).orElse(null);
        Assertions.assertEquals("a: 1, b: 2, c: 2019-01-07, d: 3, e: 2020-10-15, f: ${X}.${}", output);
    }
    
    
    @Test
    public void testParseBigResponse() {
        final int RECORDS = 1;
        final StringBuilder inputBuilder = new StringBuilder();
        inputBuilder.append("<users>\n");       
        for (int i = 0; i < RECORDS; i++) {
            inputBuilder.append("   <user>\n");
            inputBuilder.append("      <sequence>").append(i + 1).append("</sequence>\n");
            inputBuilder.append("      <id>${RANDOM_LONG()}</id>\n");
            inputBuilder.append("      <name>${RANDOM_STRING()}</name>\n");
            inputBuilder.append(
                    "      <email>${RANDOM_EMAIL(domain=\"castlemock\",min=5,max=10,topDomain=\"com\")}</email>\n");
            inputBuilder.append("      <status>${RANDOM_ENUM(values=[\"active\",\"inactive\",\"locked\"])}</status>\n");
            inputBuilder.append("   </user>\n");
        }
        inputBuilder.append("</users>\n");

        final String input = inputBuilder.toString();

        final long startedAt = System.currentTimeMillis();
        final String output = textParser.parse(input).orElse(null);
        
        Assertions.assertNotEquals(input, output);
        System.out.println("testParseBigResponse runned in " + (System.currentTimeMillis() - startedAt) + " ms");
    }
    
}
