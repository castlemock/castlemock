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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @since 1.6
 * @author Karl Dahlgren
 */
public class TextParserTest {
    private TextParser textParser = new TextParser();
    private TextParser textParserMockedExpressions;

    
    @BeforeEach
    void init() {
        this.textParserMockedExpressions = new TextParser(new TextParseMockConfig().getMockedExpressions());
    }

    @Test
    public void testParseNull(){
        String input = null;
        String output = this.textParser.parse(input);
        Assertions.assertNull(output);
    }

    @Test
    public void testParseRandomInteger(){
        String input = "Hello this is a ${RANDOM_INTEGER}.";
        String output = textParser.parse(input);
        Assertions.assertNotEquals(input, output);
        Assertions.assertTrue(output.matches("Hello this is a (.*?)."));
        
        input = "a: ${RANDOM_INTEGER}, b: ${RANDOM_INTEGER()}.";
        output = textParserMockedExpressions.parse(input);
        Assertions.assertEquals("a: 1, b: 2.", output);
    }
    
    @Test
    public void testParseRandomDouble(){
        String input = "Hello this is a ${RANDOM_DOUBLE}.";
        String output = textParser.parse(input);
        Assertions.assertNotEquals(input, output);
        Assertions.assertTrue(output.matches("Hello this is a (.*?)."));
        
        input = "a: ${RANDOM_DOUBLE}, b: ${RANDOM_DOUBLE}.";
        output = textParserMockedExpressions.parse(input);
        Assertions.assertEquals("a: 9.07231552E8, b: 1.971414777E9.", output);
    }

    @Test
    public void testParseRandomLong(){
        String input = "Hello this is a ${RANDOM_LONG}.";
        String output = textParser.parse(input);
        Assertions.assertNotEquals(input, output);
        Assertions.assertTrue(output.matches("Hello this is a (.*?)."));
        
        input = "a: ${RANDOM_LONG}, b: ${RANDOM_LONG}.";
        output = textParserMockedExpressions.parse(input);
        Assertions.assertEquals("a: 8345852683487585923, b: 4396544029006714971.", output);
    }

    @Test
    public void testParseRandomFloat(){
        String input = "Hello this is a ${RANDOM_FLOAT}.";
        String output = this.textParser.parse(input);
        Assertions.assertNotEquals(input, output);
        Assertions.assertTrue(output.matches("Hello this is a (.*?)."));
        
        input = "a: ${RANDOM_FLOAT}, b: ${RANDOM_FLOAT}.";
        output = textParserMockedExpressions.parse(input);
        Assertions.assertEquals("a: 0.77921385, b: 0.52562904.", output);
    }
    
    @Test
    public void testParseRandomBoolean(){
        String input = "a: ${RANDOM_BOOLEAN}, b: ${RANDOM_BOOLEAN}.";
        String output = textParserMockedExpressions.parse(input);
        Assertions.assertEquals("a: false, b: true.", output);
    }
    
    @Test
    public void testParseRandomDate(){
        String input = "a: ${RANDOM_DATE}, b: ${RANDOM_DATE}.";
        String output = textParserMockedExpressions.parse(input);
        Assertions.assertEquals("a: 2019-01-07, b: 2020-10-15.", output);
    }
    
    @Test
    public void testParseRandomString(){
        String input = "a: ${RANDOM_STRING}, b: ${RANDOM_STRING}.";
        String output = textParserMockedExpressions.parse(input);
        Assertions.assertEquals("a: XfDbd6sk90hxH0L, b: s80ho4iJTlda.", output);
    }
    
    @Test
    public void testParseRandomUUID(){
        String input = "a: ${RANDOM_UUID}, b: ${RANDOM_UUID}.";
        String output = textParserMockedExpressions.parse(input);
        Assertions.assertEquals("a: bf60cb57-cfd8-43bb-83f7-62b1584c29d7, b: af88f1d8-c8f6-4407-903c-18ffe6ac06c5.", output);
    }
    
    @Test
    public void testParseRandomEmail(){
        String input = "a: ${RANDOM_EMAIL}, b: ${RANDOM_EMAIL}.";
        String output = textParserMockedExpressions.parse(input);
        Assertions.assertEquals("a: Cng73JRbJT@RliQvDuVVN8.com, b: C0GlhHZ4vL@6ZNPRPmX.com.", output);
    }
    
    @Test
    public void testParseRandomPassword(){
        String input = "a: ${RANDOM_PASSWORD}, b: ${RANDOM_PASSWORD}.";
        String output = textParserMockedExpressions.parse(input);
        Assertions.assertEquals("a: j5HC7UGSVp, b: p70ggj9409ia.", output);
    }
    
    @Test
    public void testParseRandomDateTime(){
        String input = "a: ${RANDOM_DATE_TIME}, b: ${RANDOM_DATE_TIME}.";
        String output = textParserMockedExpressions.parse(input);
        Assertions.assertEquals("a: 2008-02-09T21:42:10, b: 2048-09-26T06:39:22.", output);
    }
    
    @Test
    public void testParseRandomEnum(){
        String input = "a: ${RANDOM_ENUM(values=[\"available\",\"pending\",\"sold\"])}, b: ${RANDOM_ENUM(values=[\"X\",\"Y\",\"Z\"])}.";
        String output = textParserMockedExpressions.parse(input);
        Assertions.assertEquals("a: available, b: Z.", output);
    }
    
    @Test
    public void testParsePathParameter() {
        final Map<String, Set<String>> pathParameters = new HashMap<>();
        pathParameters.put("param1", Set.of("X"));
        pathParameters.put("param2", Set.of("Y"));
        pathParameters.put("param3", Set.of("Z"));
        
        String input = "a: ${PATH_PARAMETER(parameter=\"param1\")}, b: ${PATH_PARAMETER(parameter=\"param2\")}.";
        String output = textParser.parse(input, new ExternalInputBuilder().pathParameters(pathParameters).build());
        Assertions.assertEquals("a: X, b: Y.", output);
    }

    @Test
    public void testParsePathParameterMultipleValues() {
        final Map<String, Set<String>> pathParameters = new HashMap<>();
        pathParameters.put("param1", Set.of("X", "Y"));

        String input = "${PATH_PARAMETER(parameter=\"param1\")}";
        String output = textParser.parse(input, new ExternalInputBuilder().pathParameters(pathParameters).build());
        Assertions.assertEquals("X", output);
    }


    @Test
    public void testParseQueryString() {
        final List<HttpParameter> queryStringParameters = new ArrayList<>();
        queryStringParameters.add(new HttpParameter("queryParam1", "Apple"));
        queryStringParameters.add(new HttpParameter("queryParam2", "Orange"));
        queryStringParameters.add(new HttpParameter("queryParam3", "Banana"));
        queryStringParameters.add(new HttpParameter("queryParam4", "Papaya"));
        
        String input = "a: ${QUERY_STRING(query=\"queryParam1\")}, b: ${QUERY_STRING(query=\"queryParam2\")}.";
        String output = textParser.parse(input, new ExternalInputBuilder().queryStringParameters(queryStringParameters).build());
        Assertions.assertEquals("a: Apple, b: Orange.", output);
    }
        
    @Test
    public void testParseBodyJsonPath() throws IOException{
        URL url = Resources.getResource("store-book.json");
        final String requestBodyJson = Resources.toString(url, StandardCharsets.UTF_8);
        
        String input = "a: ${BODY_JSON_PATH(expression=\"$.store.book[0].title\")}, b: ${BODY_JSON_PATH(expression=\"$.store.book[1].title\")}.";
        String output = textParser.parse(input, new ExternalInputBuilder().requestBody(requestBodyJson).build());
        Assertions.assertEquals("a: Moby Dick, b: The Lord of the Rings.", output);
    }
    
    @Test
    public void testParseUrlHost() throws IOException{
        String input = "a: ${URL_HOST()}, b: ${URL_HOST()}.";
        String output = textParser.parse(input, new ExternalInputBuilder().requestUrl("http://localhost:8080/castlemock").build());
        Assertions.assertEquals("a: localhost, b: localhost.", output);
    }
    
    @Test
    public void testParseBodyXPath() throws IOException{
        final String requestBodyXml = "" +
                "<user>\n" + 
                "   <id>1</id>\n" + 
                "   <name>Peter</name>\n" + 
                "</user>\n";
        
        String input = "a: ${BODY_XPATH(expression=\"//user/id/text()\")}, b: ${BODY_XPATH(expression=\"//user/name/text()\")}.";
        String output = textParser.parse(input, new ExternalInputBuilder().requestBody(requestBodyXml).build());
        Assertions.assertEquals("a: 1, b: Peter.", output);
    }
    
    @Test
    public void testParseFaker(){
        String input = "Hello this is a ${FAKER(api=\"name().fullName()\")}.";
        String output = textParser.parse(input);
        Assertions.assertNotEquals(input, output);
        Assertions.assertTrue(output.matches("Hello this is a (.*?)."));
        
        input = "a: ${FAKER(api=\"name().fullName()\")}, b: ${FAKER(api=\"name().fullName()\")}.";
        output = textParserMockedExpressions.parse(input);
        Assertions.assertEquals("a: Peter, b: Paul.", output);
    }
    
    @Test
    public void testParseMultipleExpressions() {
        String input = "a: ${RANDOM_INTEGER}, b: ${RANDOM_INTEGER()}, c: ${RANDOM_DATE()},"
                + " d: ${RANDOM_INTEGER(min=0,max=100)}, e: ${RANDOM_DATE()}, f: ${X}.${}";
        
        String output = textParserMockedExpressions.parse(input);
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

        long startedAt = System.currentTimeMillis();
        String output = textParser.parse(input);
        
        Assertions.assertNotEquals(input, output);
        System.out.println("testParseBigResponse runned in " + (System.currentTimeMillis() - startedAt) + " ms");
    }
    
}
