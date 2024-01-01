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

import com.castlemock.model.core.utility.parser.expression.BodyJsonPathExpression;
import com.castlemock.model.core.utility.parser.expression.BodyXPathExpression;
import com.castlemock.model.core.utility.parser.expression.Expression;
import com.castlemock.model.core.utility.parser.expression.ExpressionInput;
import com.castlemock.model.core.utility.parser.expression.ExpressionInputParser;
import com.castlemock.model.core.utility.parser.expression.FakerExpression;
import com.castlemock.model.core.utility.parser.expression.PathParameterExpression;
import com.castlemock.model.core.utility.parser.expression.QueryStringExpression;
import com.castlemock.model.core.utility.parser.expression.RandomBooleanExpression;
import com.castlemock.model.core.utility.parser.expression.RandomDateExpression;
import com.castlemock.model.core.utility.parser.expression.RandomDateTimeExpression;
import com.castlemock.model.core.utility.parser.expression.RandomDecimalExpression;
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
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * The {@link TextParser} provides the functionality to transform a text and replace all
 * the expressions in the text into a new value. {@link TextParser} contains a collection
 * of all the {@link Expression} and will iterate through them when parsing a text.
 * If the text contains expressions that matches one or more criteria of an {@link Expression},
 * then these expressions will be replaced be new values. The new values are determined by the
 * actual {@link Expression}.
 * @author Karl Dahlgren
 * @since 1.6
 */
public class TextParser {

    private static final Map<String,Expression> EXPRESSIONS = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(TextParser.class);


    static {
        EXPRESSIONS.put(RandomIntegerExpression.IDENTIFIER, new RandomIntegerExpression());
        EXPRESSIONS.put(RandomDoubleExpression.IDENTIFIER, new RandomDoubleExpression());
        EXPRESSIONS.put(RandomLongExpression.IDENTIFIER, new RandomLongExpression());
        EXPRESSIONS.put(RandomFloatExpression.IDENTIFIER, new RandomFloatExpression());
        EXPRESSIONS.put(RandomBooleanExpression.IDENTIFIER, new RandomBooleanExpression());
        EXPRESSIONS.put(RandomDateExpression.IDENTIFIER, new RandomDateExpression());
        EXPRESSIONS.put(RandomStringExpression.IDENTIFIER, new RandomStringExpression());
        EXPRESSIONS.put(RandomUUIDExpression.IDENTIFIER, new RandomUUIDExpression());
        EXPRESSIONS.put(RandomEmailExpression.IDENTIFIER, new RandomEmailExpression());
        EXPRESSIONS.put(RandomPasswordExpression.IDENTIFIER, new RandomDecimalExpression());
        EXPRESSIONS.put(RandomDateTimeExpression.IDENTIFIER, new RandomDateTimeExpression());
        EXPRESSIONS.put(RandomEnumExpression.IDENTIFIER, new RandomEnumExpression());
        EXPRESSIONS.put(PathParameterExpression.IDENTIFIER, new PathParameterExpression());
        EXPRESSIONS.put(QueryStringExpression.IDENTIFIER, new QueryStringExpression());
        EXPRESSIONS.put(BodyJsonPathExpression.IDENTIFIER, new BodyJsonPathExpression());
        EXPRESSIONS.put(UrlHostExpression.IDENTIFIER, new UrlHostExpression());
        EXPRESSIONS.put(BodyXPathExpression.IDENTIFIER, new BodyXPathExpression());
        EXPRESSIONS.put(FakerExpression.IDENTIFIER, new FakerExpression());
    }
    
    private final Map<String,Expression> expressions;
    
    public TextParser() {
        this.expressions = EXPRESSIONS;
    }
    
    public TextParser(final Map<String,Expression> expressions) {
        this.expressions = Objects.requireNonNull(expressions, "expressions");
    }
    
    /**
     * The parse method is responsible for parsing a provided text and transform the text
     * with the help of {@link Expression}. {@link Expression} in the texts will be transformed
     * and replaced with new values. The transformed text will be returned.
     * @param text The provided text that will be transformed.
     * @return A transformed text. All expressions will be replaced by new values.
     *          Please note that the same text will be returned if no expressions
     *          were found in the provided text.
     */
    public Optional<String> parse(final String text){
        return parse(text, null);
    }

    /**
     * The parse method is responsible for parsing a provided text and transform the text
     * with the help of {@link Expression}. {@link Expression} in the texts will be transformed
     * and replaced with new values. The transformed text will be returned.
     * @param text The provided text that will be transformed.
     * @return A transformed text. All expressions will be replaced by new values.
     *          Please note that the same text will be returned if no expressions
     *          were found in the provided text.
     */
    public Optional<String> parse(final String text,
                                 final Map<String, ExpressionArgument<?>> arguments) {
        if(text == null){
            return Optional.empty();
        }
        final StringBuilder outputBuilder = new StringBuilder().append(text);
        final String expressionBegin = "${";
        final String expressionEnd = "}";
        int indexOfStartExpression = -1;
        do {
            indexOfStartExpression = outputBuilder.indexOf(expressionBegin, indexOfStartExpression + 1);
            if (indexOfStartExpression >= 0) {
                int indexOfEndExpression = outputBuilder.indexOf(expressionEnd, indexOfStartExpression + 1);
                if (indexOfEndExpression >= 0) {
                    replaceExpressionByResult(outputBuilder, indexOfStartExpression, indexOfEndExpression, arguments);                  
                }
            }
        } while (indexOfStartExpression >= 0);
        
        return Optional.of(outputBuilder.toString());
    }
    
    private void replaceExpressionByResult(final StringBuilder outputBuilder, final int indexOfStartExpression,
                                           final int indexOfEndExpression, final Map<String, ExpressionArgument<?>> arguments) {
        final String expressionString = outputBuilder.substring(indexOfStartExpression, indexOfEndExpression + 1);
        final ExpressionInput expressionInput = parseExpressionInput(expressionString, arguments);
        final Expression expression = this.expressions.get(expressionInput.getName());
        
        if (expression != null){
            String expressionResult = expression.transform(expressionInput);
            outputBuilder.replace(indexOfStartExpression, indexOfEndExpression + 1, expressionResult);
        } else {
            LOGGER.error("Unable to parse the following expression: " + expressionInput.getName());
        }
    }
    
    private ExpressionInput parseExpressionInput(final String expressionString, final Map<String, ExpressionArgument<?>> arguments) {
        final ExpressionInput expressionInput = ExpressionInputParser.parse(expressionString);
        
        if(arguments != null){
            for(Map.Entry<String, ExpressionArgument<?>> argumentEntry : arguments.entrySet()){
                expressionInput.addArgument(argumentEntry.getKey(), argumentEntry.getValue());
            }
        }
        return expressionInput;
    }

}
