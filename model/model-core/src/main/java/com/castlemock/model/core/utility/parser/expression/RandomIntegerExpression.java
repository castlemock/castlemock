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

package com.castlemock.model.core.utility.parser.expression;

import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentNumber;

/**
 * {@link RandomIntegerExpression} is an {@link Expression} and will
 * transform an matching input string into a random integer.
 * @author Karl Dahlgren
 * @since 1.6
 */
public class RandomIntegerExpression extends AbstractExpression {

    private static final String MIN_ARGUMENT = "min";
    private static final String MAX_ARGUMENT = "max";
    public static final String IDENTIFIER = "RANDOM_INTEGER";

    /**
     * The transform method provides the functionality to transform a provided <code>input</code>.
     * The transformation and the end result will be determine by how it is implemented by each {@link Expression}.
     * Each {@link Expression} provides it's own functionality and will transform the text differently.
     * @param input The input string that will be transformed.
     * @return A transformed <code>input</code>.
     */
    @Override
    public String transform(final ExpressionInput input) {
        int minLength = -1000;
        int maxLength = 2000;

        final ExpressionArgument<?> minArgument = input.getArgument(MIN_ARGUMENT);
        final ExpressionArgument<?> maxArgument = input.getArgument(MAX_ARGUMENT);

        if(minArgument instanceof ExpressionArgumentNumber){
            minLength = ((ExpressionArgumentNumber) minArgument).getValue().intValue();
        }
        if(maxArgument instanceof ExpressionArgumentNumber){
            maxLength = ((ExpressionArgumentNumber) maxArgument).getValue().intValue();
        }

        int randomValue = RANDOM.nextInt(maxLength) + minLength;
        return Integer.toString(randomValue);
    }

    /**
     * The match method is used to determine if an <code>input</code> string matches
     * the criteria to be transformed.
     * @param input The input that will be determine if it matches the criteria to be transformed.
     * @return True if the input string matches the criteria. False otherwise.
     */
    @Override
    public boolean match(final String input) {
        return IDENTIFIER.equalsIgnoreCase(input);
    }
}
