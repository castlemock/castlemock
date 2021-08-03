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

import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link RandomEnumExpression} is an {@link Expression} and will
 * transform an matching input string into a random enum.
 * @author Karl Dahlgren
 * @since 1.14
 */
public class RandomEnumExpression extends AbstractExpression {

    public static final String IDENTIFIER = "RANDOM_ENUM";
    public static final String VALUES_PARAMETER = "values";
    private static final Logger LOGGER = LoggerFactory.getLogger(RandomEnumExpression.class);

    /**
     * The transform method provides the functionality to transform a provided <code>input</code>.
     * The transformation and the end result will be determine by how it is implemented by each {@link Expression}.
     * Each {@link Expression} provides it's own functionality and will transform the text differently.
     * @param input The input string that will be transformed.
     * @return A transformed <code>input</code>.
     */
    @Override
    public String transform(final ExpressionInput input) {
        final ExpressionArgument<?> expressionArgument = input.getArgument(VALUES_PARAMETER);
        if(expressionArgument == null){
            throw new IllegalArgumentException("Unable to extract the enum values");
        } else if(!(expressionArgument instanceof ExpressionArgumentArray)){
            throw new IllegalArgumentException("Invalid enum argument");
        }

        final ExpressionArgumentArray array = (ExpressionArgumentArray) expressionArgument;
        final int argumentSize = array.getArgumentSize();
        final int index = RANDOM.nextInt(argumentSize);

        final ExpressionArgument<?> argument = array.getArgument(index);

        if(argument instanceof ExpressionArgumentArray){
            LOGGER.error("The enum argument can't be an array");
            throw new IllegalArgumentException("Invalid enum argument");
        }

        return argument.getValue().toString();
    }

    /**
     * The match method is used to determine if an <code>input</code> string matches
     * the criteria to be transformed.
     * @param input The input that will be determine if it matches the criteria to be transformed.
     * @return True if the input string matches the criteria. False otherwise.
     */
    @Override
    public boolean match(final String input) {
        return input.startsWith(IDENTIFIER);
    }
}
