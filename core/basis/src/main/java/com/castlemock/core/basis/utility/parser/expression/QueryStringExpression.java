/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.core.basis.utility.parser.expression;

import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgumentMap;

/**
 * {@link QueryStringExpression} is an {@link Expression} and will
 * transform an matching input string into a random boolean.
 * @author Karl Dahlgren
 * @since 1.35
 */
public class QueryStringExpression extends AbstractExpression {

    public static final String IDENTIFIER = "QUERY_STRING";
    public static final String QUERY_STRINGS = "QUERY_STRINGS";
    public static final String QUERY_ARGUMENT = "query";
    public static final String MISSING_QUERY_STRING = "";

    /**
     * The transform method provides the functionality to transform a provided <code>input</code>.
     * The transformation and the end result will be determine by how it is implemented by each {@link Expression}.
     * Each {@link Expression} provides it's own functionality and will transform the text differently.
     * @param input The input string that will be transformed.
     * @return A transformed <code>input</code>.
     */
    @Override
    public String transform(final ExpressionInput input) {
        final ExpressionArgument<?> querysArgument = input.getArgument(QUERY_STRINGS);
        final ExpressionArgument<?> queryIdentifierArgument = input.getArgument(QUERY_ARGUMENT);

        if(querysArgument == null || queryIdentifierArgument == null ||
                !(querysArgument instanceof ExpressionArgumentMap)){
            return MISSING_QUERY_STRING;
        }

        final ExpressionArgumentMap querysMap = (ExpressionArgumentMap) querysArgument;
        final Object key = queryIdentifierArgument.getValue();
        final ExpressionArgument<?> result = querysMap.getArgument(key);

        if(result == null){
            return MISSING_QUERY_STRING;
        }

        return result.getValue().toString();
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
