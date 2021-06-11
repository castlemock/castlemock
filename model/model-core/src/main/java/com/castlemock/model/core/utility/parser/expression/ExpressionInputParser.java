/*
 * Copyright 2017 Karl Dahlgren
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

package com.castlemock.model.core.utility.parser.expression;

import com.castlemock.core.expression.ExpressionLexer;
import com.castlemock.core.expression.ExpressionParser;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentArray;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentNumber;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentString;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The {@link ExpressionInputParser} is a parser class used to
 * parse expression inputs and convert them into {@link ExpressionInput}
 * and set of {@link ExpressionArgument}.
 * @author Karl Dahlgren
 * @since 1.14
 * @see ExpressionInput
 * @see ExpressionArgument
 * @see Expression
 */
public final class ExpressionInputParser {

    private static final String START_EXPRESSION = "${";
    private static final String END_EXPRESSION = "}";
    private static final String ARGUMENT_START_CHARACTER = "(";
    private static final String ARGUMENT_END_CHARACTER = ")";
    private static final String ARGUMENT_EQUAL_CHARACTER = "=";
    private static final String ARGUMENT_QUOTE_CHARACTER = "\"";
    private static final String ARRAY_START_CHARACTER = "[";
    private static final String ARRAY_END_CHARACTER = "]";
    private static final String ARGUMENT_SEPARATOR_CHARACTER = ",";

    /**
     * Private constructor for {@link ExpressionInputParser} to
     * prevent any instances of the class to be created.
     */
    private ExpressionInputParser(){}


    /**
     * The parse method provides the functionality to parse and convert
     * a String input into an {@link ExpressionInput}.
     * @param input The input that will be converted into an {@link ExpressionInput}.
     * @return an {@link ExpressionInput} based on the provided <code>input</code>.
     * @see {@link ExpressionInputParser#parseArgument(ExpressionParser.ArgumentValueContext)}.
     */
    public static ExpressionInput parse(final String input){
        // Parse the input
        final CodePointCharStream stream = CharStreams.fromString(input);
        final ExpressionLexer lexer = new ExpressionLexer(stream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final ExpressionParser parser = new com.castlemock.core.expression.ExpressionParser(tokens);
        final ExpressionParser.ExpressionContext expression = parser.expression();

        // Create expression details
        final String expressionName = expression.type.getText();
        final ExpressionInput expressionInput = new ExpressionInput(expressionName);
        for(ExpressionParser.ArgumentContext argumentContext : expression.argument()){
            String argumentName = argumentContext.argumentName().getText();
            ExpressionParser.ArgumentValueContext argumentValueContext = argumentContext.argumentValue();
            ExpressionArgument<?> expressionArgument = parseArgument(argumentValueContext);
            expressionInput.addArgument(argumentName, expressionArgument);
        }


        return expressionInput;
    }


    /**
     * The method provides the functionality to convert a {@link ExpressionParser.ArgumentValueContext} into
     * a {@link ExpressionArgument}.
     * @param argumentValueContext The {@link ExpressionParser.ArgumentValueContext} that will be converted into
     *                             an {@link ExpressionArgument}.
     * @return An {@link ExpressionArgument} based on the provided {@link ExpressionParser.ArgumentValueContext}.
     */
    private static ExpressionArgument<?> parseArgument(final ExpressionParser.ArgumentValueContext argumentValueContext){
        if(argumentValueContext.argumentString() != null){
            // String
            final String argumentValue = argumentValueContext.argumentString().value.getText();
            return new ExpressionArgumentString(argumentValue);
        } else if(argumentValueContext.argumentNumber() != null){
            // Numeric
            final Double argumentValue = Double.parseDouble(argumentValueContext.argumentNumber().value.getText());
            return new ExpressionArgumentNumber(argumentValue);
        } else if(argumentValueContext.array() != null){
            // Array
            final ExpressionArgumentArray array = new ExpressionArgumentArray();
            final List<ExpressionParser.ArgumentValueContext> arrayItems = argumentValueContext.array().value;

            for(ExpressionParser.ArgumentValueContext arrayItem : arrayItems){
                ExpressionArgument<?> subArgument = parseArgument(arrayItem);
                array.addArgument(subArgument);
            }
            return array;
        }

        throw new IllegalArgumentException("Unable to parse the argument");
    }

    /**
     * The method provides the functionality to convert a given {@link ExpressionInput}
     * into an actual expression.
     * @param expressionInput The {@link ExpressionInput} into an expression.
     * @return A convert {@link ExpressionInput} as an expression in String format.
     */
    public static String convert(final ExpressionInput expressionInput){
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(START_EXPRESSION);
        stringBuilder.append(expressionInput.getName());
        stringBuilder.append(ARGUMENT_START_CHARACTER);

        final Iterator<Map.Entry<String, ExpressionArgument<?>>> argumentIterator =
                expressionInput.getArguments().entrySet().iterator();
        while(argumentIterator.hasNext()){
            Map.Entry<String, ExpressionArgument<?>> argumentEntry = argumentIterator.next();
            String argumentName = argumentEntry.getKey();
            ExpressionArgument<?> argument = argumentEntry.getValue();

            stringBuilder.append(argumentName);
            stringBuilder.append(ARGUMENT_EQUAL_CHARACTER);
            convert(argument, stringBuilder);

            if(argumentIterator.hasNext()){
                stringBuilder.append(ARGUMENT_SEPARATOR_CHARACTER);
            }
        }

        stringBuilder.append(ARGUMENT_END_CHARACTER);
        stringBuilder.append(END_EXPRESSION);
        return stringBuilder.toString();
    }

    /**
     * Converts an {@link ExpressionArgument} into a String expression and adds it to
     * the provided {@link StringBuilder}.
     * @param argument The {@link ExpressionArgument} that will be converted into a String expression.
     * @param stringBuilder The {@link StringBuilder} which the converted {@link ExpressionArgument}
     *                      will be added to.
     */
    private static void convert(final ExpressionArgument<?> argument, final StringBuilder stringBuilder){
        if(argument instanceof ExpressionArgumentString){
            // String
            final ExpressionArgumentString argumentString = (ExpressionArgumentString) argument;
            stringBuilder.append(ARGUMENT_QUOTE_CHARACTER);
            stringBuilder.append(argumentString.getValue());
            stringBuilder.append(ARGUMENT_QUOTE_CHARACTER);

        } else if(argument instanceof ExpressionArgumentNumber){
            // Number
            final ExpressionArgumentNumber argumentNumber = (ExpressionArgumentNumber) argument;
            stringBuilder.append(argumentNumber.getValue());

        } else if(argument instanceof ExpressionArgumentArray){
            // Array
            ExpressionArgumentArray argumentArray = (ExpressionArgumentArray) argument;
            Iterator<ExpressionArgument<?>> iterator = argumentArray.iterator();
            stringBuilder.append(ARRAY_START_CHARACTER);

            // Iterate through sub arguments
            while(iterator.hasNext()){
                ExpressionArgument<?> subArgument = iterator.next();
                convert(subArgument, stringBuilder);
                if(iterator.hasNext()) {
                    stringBuilder.append(ARGUMENT_SEPARATOR_CHARACTER);
                }
            }
            stringBuilder.append(ARRAY_END_CHARACTER);
        }
    }

}
