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

import com.castlemock.model.core.utility.parser.expression.BodyJsonPathExpression;
import com.castlemock.model.core.utility.parser.expression.BodyXPathExpression;
import com.castlemock.model.core.utility.parser.expression.Expression;
import com.castlemock.model.core.utility.parser.expression.ExpressionInput;
import com.castlemock.model.core.utility.parser.expression.FakerExpression;
import com.castlemock.model.core.utility.parser.expression.PathParameterExpression;
import com.castlemock.model.core.utility.parser.expression.QueryStringExpression;
import com.castlemock.model.core.utility.parser.expression.RandomBooleanExpression;
import com.castlemock.model.core.utility.parser.expression.RandomDateExpression;
import com.castlemock.model.core.utility.parser.expression.RandomDateTimeExpression;
import com.castlemock.model.core.utility.parser.expression.RandomDoubleExpression;
import com.castlemock.model.core.utility.parser.expression.RandomEmailExpression;
import com.castlemock.model.core.utility.parser.expression.RandomEnumExpression;
import com.castlemock.model.core.utility.parser.expression.RandomFloatExpression;
import com.castlemock.model.core.utility.parser.expression.RandomIntegerExpression;
import com.castlemock.model.core.utility.parser.expression.RandomLongExpression;
import com.castlemock.model.core.utility.parser.expression.RandomPasswordExpression;
import com.castlemock.model.core.utility.parser.expression.RandomStringExpression;
import com.castlemock.model.core.utility.parser.expression.RandomUUIDExpression;
import com.castlemock.model.core.utility.parser.expression.UrlHostExpression;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

class TextParseMockConfig {
	
	public TextParseMockConfig() {
		this.mockedExpressions = configureMockedExpressions();
	}
	
	private Map<String,Expression> mockedExpressions;
	
	public Map<String,Expression> getMockedExpressions() {
		return this.mockedExpressions;
	}
	
	private Map<String,Expression> configureMockedExpressions() {
    	final Map<String,Expression> expressions = new HashMap<>();
    	registerMockExpression(expressions, RandomIntegerExpression.IDENTIFIER, "1", "2", "3");
    	registerMockExpression(expressions, RandomDoubleExpression.IDENTIFIER, "9.07231552E8", "1.971414777E9");
    	registerMockExpression(expressions, RandomLongExpression.IDENTIFIER, "8345852683487585923","4396544029006714971");
    	registerMockExpression(expressions, RandomFloatExpression.IDENTIFIER, "0.77921385", "0.52562904");
    	registerMockExpression(expressions, RandomBooleanExpression.IDENTIFIER, "false", "true");
    	registerMockExpression(expressions, RandomDateExpression.IDENTIFIER, "2019-01-07", "2020-10-15");
    	registerMockExpression(expressions, RandomStringExpression.IDENTIFIER, "XfDbd6sk90hxH0L", "s80ho4iJTlda");
    	registerMockExpression(expressions, RandomUUIDExpression.IDENTIFIER, "bf60cb57-cfd8-43bb-83f7-62b1584c29d7", "af88f1d8-c8f6-4407-903c-18ffe6ac06c5");
    	registerMockExpression(expressions, RandomEmailExpression.IDENTIFIER, "Cng73JRbJT@RliQvDuVVN8.com", "C0GlhHZ4vL@6ZNPRPmX.com");
    	registerMockExpression(expressions, RandomPasswordExpression.IDENTIFIER, "j5HC7UGSVp", "p70ggj9409ia");
    	registerMockExpression(expressions, RandomDateTimeExpression.IDENTIFIER, "2008-02-09T21:42:10", "2048-09-26T06:39:22");
    	registerMockExpression(expressions, RandomEnumExpression.IDENTIFIER, "available", "Z");
    	registerMockExpression(expressions, FakerExpression.IDENTIFIER, "Peter", "Paul"); 
    	expressions.put(PathParameterExpression.IDENTIFIER, new PathParameterExpression());
    	expressions.put(QueryStringExpression.IDENTIFIER, new QueryStringExpression());
    	expressions.put(BodyJsonPathExpression.IDENTIFIER, new BodyJsonPathExpression());
    	expressions.put(UrlHostExpression.IDENTIFIER, new UrlHostExpression());
    	expressions.put(BodyXPathExpression.IDENTIFIER, new BodyXPathExpression());
    	
    	return expressions;
	}
	
	private void registerMockExpression(final Map<String,Expression> expressions, String name, String value, String... values) {
		Expression mockExpression = mockExpression(name, value, values);
		expressions.put(name, mockExpression);		
	}
	
	private Expression mockExpression(String name, String value, String... values) {
		Expression mockExpression = Mockito.mock(Expression.class);
		Mockito.when(mockExpression.match(name)).thenReturn(true);
		Mockito.when(mockExpression.transform(Mockito.any(ExpressionInput.class))).thenReturn(value, values);
		return mockExpression;
	}	

}
