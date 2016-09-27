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

    private static final String START_EXPRESSION = "\\{";
    private static final String END_EXPRESSION = "\\}";
    private static final Expression[] EXPRESSIONS =
            {new RandomIntegerExpression(), new RandomDoubleExpression(), new RandomLongExpression(),
                    new RandomFloatExpression()};

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
        String output = text;
        Pattern pattern = Pattern.compile("(?<=\\{)(.*?)(?=\\})");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()){
            String match = matcher.group();

            for(Expression expression : EXPRESSIONS){
                if(expression.match(match)){
                    String expressionResult = expression.transform(match);
                    output = output.replaceFirst(START_EXPRESSION + match + END_EXPRESSION, expressionResult);
                }
            }

        }
        return output;
    }



}
