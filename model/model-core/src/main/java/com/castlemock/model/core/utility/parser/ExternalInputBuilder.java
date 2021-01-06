package com.castlemock.model.core.utility.parser;

import java.util.List;
import java.util.Map;

import com.castlemock.model.core.http.HttpParameter;
import com.castlemock.model.core.utility.parser.expression.BodyXPathExpression;
import com.castlemock.model.core.utility.parser.expression.PathParameterExpression;
import com.castlemock.model.core.utility.parser.expression.QueryStringExpression;
import com.castlemock.model.core.utility.parser.expression.UrlHostExpression;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentMap;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentString;
import com.google.common.collect.ImmutableMap;

public class ExternalInputBuilder {
	private String requestBody;
	private String requestUrl;
	private Map<String, String> pathParameters;
	private List<HttpParameter> queryStringParameters;
	
	public ExternalInputBuilder requestBody(final String requestBody) {
		this.requestBody = requestBody;
		return this;
	}
	
	public ExternalInputBuilder requestUrl(final String requestUrl) {
		this.requestUrl = requestUrl;
		return this;
	}
	
	public ExternalInputBuilder pathParameters(final Map<String, String> pathParameters) {
		this.pathParameters= pathParameters;
		return this;
	}
	
	public ExternalInputBuilder queryStringParameters(final List<HttpParameter> queryStringParameters) {
		this.queryStringParameters = queryStringParameters;
		return this;
	}
	
	public Map<String, ExpressionArgument<?>> build() {
		ImmutableMap.Builder<String, ExpressionArgument<?>> immutableMapBuilder = ImmutableMap.builder();
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
		
		final Map<String, ExpressionArgument<?>> externalInput = immutableMapBuilder.build();
		return externalInput;
	}
	
	private ExpressionArgumentMap buildPathParametersArgument() {
		final ExpressionArgumentMap pathParametersArgument = new ExpressionArgumentMap();
        this.pathParameters.forEach((key, value) -> {
            ExpressionArgument<?> pathParameterArgument = new ExpressionArgumentString(value);
            pathParametersArgument.addArgument(key, pathParameterArgument);
        });
        return pathParametersArgument;
	}
	
	private final ExpressionArgumentMap buildQueryStringArgument() {
		final ExpressionArgumentMap queryStringArgument = new ExpressionArgumentMap();
        this.queryStringParameters.forEach(parameter -> {
            ExpressionArgument<?> pathParameterArgument = new ExpressionArgumentString(parameter.getValue());
            queryStringArgument.addArgument(parameter.getName(), pathParameterArgument);
        });
        return queryStringArgument;
	}

}
