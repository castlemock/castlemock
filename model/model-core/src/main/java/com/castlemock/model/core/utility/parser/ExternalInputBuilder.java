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

import com.castlemock.model.core.http.HttpParameter;
import com.castlemock.model.core.utility.parser.expression.BodyXPathExpression;
import com.castlemock.model.core.utility.parser.expression.PathParameterExpression;
import com.castlemock.model.core.utility.parser.expression.QueryStringExpression;
import com.castlemock.model.core.utility.parser.expression.UrlHostExpression;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentArray;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentMap;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentString;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExternalInputBuilder {
	private String requestBody;
	private String requestUrl;
	private Map<String, Set<String>> pathParameters;
	private Set<HttpParameter> queryStringParameters;
	
	public ExternalInputBuilder requestBody(final String requestBody) {
		this.requestBody = requestBody;
		return this;
	}
	
	public ExternalInputBuilder requestUrl(final String requestUrl) {
		this.requestUrl = requestUrl;
		return this;
	}
	
	public ExternalInputBuilder pathParameters(final Map<String, Set<String>> pathParameters) {
		this.pathParameters= pathParameters;
		return this;
	}
	
	public ExternalInputBuilder queryStringParameters(final Set<HttpParameter> queryStringParameters) {
		this.queryStringParameters = queryStringParameters;
		return this;
	}
	
	public Map<String, ExpressionArgument<?>> build() {
		final Map<String, ExpressionArgument<?>> immutableMapBuilder = new HashMap<>();
		if (this.requestBody != null) {
			final ExpressionArgument<?> bodyArgument = new ExpressionArgumentString(this.requestBody);
			immutableMapBuilder.put(BodyXPathExpression.BODY_ARGUMENT, bodyArgument);
		}
		if (this.requestUrl != null) {
			final ExpressionArgument<?> urlArgument = new ExpressionArgumentString(this.requestUrl);
			immutableMapBuilder.put(UrlHostExpression.URL_ARGUMENT, urlArgument);
		}
		if (this.pathParameters != null) {
			immutableMapBuilder.put(PathParameterExpression.PATH_PARAMETERS, buildPathParametersArgument());
		}
		if (this.queryStringParameters != null) {
			immutableMapBuilder.put(QueryStringExpression.QUERY_STRINGS, buildQueryStringArgument());
		}
		
		return immutableMapBuilder;
	}
	
	private ExpressionArgumentMap buildPathParametersArgument() {
		final ExpressionArgumentMap pathParametersArgument = new ExpressionArgumentMap();
        this.pathParameters.forEach((key, values) -> {
			final ExpressionArgumentArray pathParameterArgument = new ExpressionArgumentArray();
			values.forEach(value -> pathParameterArgument.addArgument(new ExpressionArgumentString(value)));
            pathParametersArgument.addArgument(key, pathParameterArgument);
        });
        return pathParametersArgument;
	}
	
	private ExpressionArgumentMap buildQueryStringArgument() {
		final ExpressionArgumentMap queryStringArgument = new ExpressionArgumentMap();
        this.queryStringParameters.forEach(parameter -> {
            ExpressionArgument<?> pathParameterArgument = new ExpressionArgumentString(parameter.getValue());
            queryStringArgument.addArgument(parameter.getName(), pathParameterArgument);
        });
        return queryStringArgument;
	}

}
