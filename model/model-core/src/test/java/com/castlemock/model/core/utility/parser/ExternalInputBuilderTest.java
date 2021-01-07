/*
 * Copyright 2021 Karl Dahlgren
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.castlemock.model.core.http.HttpParameter;
import com.castlemock.model.core.utility.parser.expression.BodyXPathExpression;
import com.castlemock.model.core.utility.parser.expression.PathParameterExpression;
import com.castlemock.model.core.utility.parser.expression.QueryStringExpression;
import com.castlemock.model.core.utility.parser.expression.UrlHostExpression;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentString;

class ExternalInputBuilderTest {
	private static Map<String, String> pathParameters;
	private static List<HttpParameter> queryStringParameters;
	
	@BeforeAll
	static void initAll() {
		pathParameters = new HashMap<>();
    	pathParameters.put("param1", "X");
    	pathParameters.put("param2", "Y");
    	pathParameters.put("param3", "Z");

    	queryStringParameters = new ArrayList<>();
    	queryStringParameters.add(new HttpParameter("queryParam1", "Apple"));
    	queryStringParameters.add(new HttpParameter("queryParam2", "Orange"));
    	queryStringParameters.add(new HttpParameter("queryParam3", "Banana"));
    	queryStringParameters.add(new HttpParameter("queryParam4", "Papaya"));
	}
	
	@Test
	void testPathParameters() {
    	final Map<String, ExpressionArgument<?>> externalInput = new ExternalInputBuilder().pathParameters(pathParameters).build();
    	
    	Assertions.assertTrue(externalInput.containsKey(PathParameterExpression.PATH_PARAMETERS));
    	@SuppressWarnings("unchecked")
        Map<String, ExpressionArgumentString> returnedPathParameter = (Map<String, ExpressionArgumentString>) externalInput.get(PathParameterExpression.PATH_PARAMETERS).getValue();
    	Assertions.assertEquals("X", returnedPathParameter.get("param1").getValue());
    	Assertions.assertEquals("Y", returnedPathParameter.get("param2").getValue());
    	Assertions.assertEquals("Z", returnedPathParameter.get("param3").getValue());
	}
	
	@Test
	void testQueryStringParameters() {
    	final Map<String, ExpressionArgument<?>> externalInput = new ExternalInputBuilder().queryStringParameters(queryStringParameters).build();
    	
    	Assertions.assertTrue(externalInput.containsKey(QueryStringExpression.QUERY_STRINGS));
    	@SuppressWarnings("unchecked")
    	Map<String, ExpressionArgumentString> returnedQueryString = (Map<String, ExpressionArgumentString>) externalInput.get(QueryStringExpression.QUERY_STRINGS).getValue();
    	Assertions.assertEquals("Apple", returnedQueryString.get("queryParam1").getValue());
    	Assertions.assertEquals("Orange", returnedQueryString.get("queryParam2").getValue());
    	Assertions.assertEquals("Banana", returnedQueryString.get("queryParam3").getValue());
    	Assertions.assertEquals("Papaya", returnedQueryString.get("queryParam4").getValue());
	}
	
	@Test
	void testRequestUrl() {
		final String requestUrl = "http://localhost:8080/castlemock";
		final Map<String, ExpressionArgument<?>> externalInput = new ExternalInputBuilder().requestUrl(requestUrl).build();
		String returnedRequestUrl = (String) externalInput.get(UrlHostExpression.URL_ARGUMENT).getValue();
		Assertions.assertEquals("http://localhost:8080/castlemock", returnedRequestUrl);
	}
	
	@Test
	void testRequestBody() {
		final String requestBody = "Body content";
		final Map<String, ExpressionArgument<?>> externalInput = new ExternalInputBuilder().requestBody(requestBody).build();
		String returnedRequestBody = (String) externalInput.get(BodyXPathExpression.BODY_ARGUMENT).getValue();
		Assertions.assertEquals("Body content", returnedRequestBody);
	}

}
