/*
 * Copyright 2020 Karl Dahlgren
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


import com.castlemock.core.basis.utility.XPathUtility;
import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * {@link BodyXPathExpression} is an {@link Expression} and will
 * extract a value from the incoming request body by using an
 * XPath expression.
 *
 * @author Karl Dahlgren
 * @since 1.47
 */
public class BodyXPathExpression extends AbstractExpression {

    public static final String IDENTIFIER = "BODY_XPATH";
    public static final String EXPRESSION_ARGUMENT = "expression";
    public static final String BODY_ARGUMENT = "BODY";
    public static final String MISSING_BODY = "";

    private static final Logger LOGGER = LoggerFactory.getLogger(BodyXPathExpression.class);

    /**
     * The transform method provides the functionality to transform a provided <code>input</code>.
     * The transformation and the end result will be determine by how it is implemented by each {@link Expression}.
     * Each {@link Expression} provides it's own functionality and will transform the text differently.
     *
     * @param input The input string that will be transformed.
     * @return A transformed <code>input</code>.
     */
    @Override
    public String transform(final ExpressionInput input) {
        final ExpressionArgument<?> bodyArgument = input.getArgument(BODY_ARGUMENT);
        final ExpressionArgument<?> expressionArgument = input.getArgument(EXPRESSION_ARGUMENT);

        if(bodyArgument == null || expressionArgument == null){
            return MISSING_BODY;
        }

        final String body = Optional.ofNullable(bodyArgument.getValue())
                .map(Object::toString)
                .orElse(null);

        final String expression = Optional.ofNullable(expressionArgument.getValue())
                .map(Object::toString)
                .orElse(null);

        if(body == null || expression == null){
            return MISSING_BODY;
        }

        try {
            return XPathUtility.getXPathValue(body, expression)
                    .orElse(MISSING_BODY);
        } catch (Exception exception){
            LOGGER.warn("Unable to parse body with the following expression: " + expression);
            return MISSING_BODY;
        }
    }

    /**
     * The match method is used to determine if an <code>input</code> string matches
     * the criteria to be transformed.
     *
     * @param input The input that will be determine if it matches the criteria to be transformed.
     * @return True if the input string matches the criteria. False otherwise.
     */
    @Override
    public boolean match(final String input) {
        return IDENTIFIER.equalsIgnoreCase(input);
    }
}
