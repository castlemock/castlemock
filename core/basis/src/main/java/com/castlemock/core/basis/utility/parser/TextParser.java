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

package com.castlemock.core.basis.utility.parser;

import com.castlemock.core.basis.utility.parser.expression.*;
import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgument;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static String parse(final String text){
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
    public static String parse(final String text,
                               final Map<String, ExpressionArgument<?>> arguments){
        if(text == null){
            return null;
        }
        String output = text;
        Pattern pattern = Pattern.compile("(?=\\$\\{)(.*?)\\}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()){
            String match = matcher.group();
            ExpressionInput expressionInput = ExpressionInputParser.parse(match);

            if(arguments != null){
                for(Map.Entry<String, ExpressionArgument<?>> argumentEntry : arguments.entrySet()){
                    expressionInput.addArgument(argumentEntry.getKey(), argumentEntry.getValue());
                }
            }

            Expression expression = EXPRESSIONS.get(expressionInput.getName());

            if(expression == null){
                LOGGER.error("Unable to parse the following expression: " + expressionInput.getName());
                continue;
            }

            String expressionResult = expression.transform(expressionInput);
            output = StringUtils.replaceOnce(output, match, expressionResult);

        }
        return output;
    }



}
