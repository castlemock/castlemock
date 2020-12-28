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
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentNumber;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentString;

/**
 * {@link RandomEmailExpression} is an {@link Expression} and will
 * transform an matching input string into a random email.
 * @author Karl Dahlgren
 * @since 1.13
 */
public class RandomEmailExpression extends AbstractExpression {

    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 10;

    private static final String DOMAIN_ARGUMENT = "domain";
    private static final String TOP_DOMAIN_ARGUMENT = "topDomain";
    private static final String MIN_ARGUMENT = "min";
    private static final String MAX_ARGUMENT = "max";

    private static final String AT = "@";
    private static final String DOT = ".";
    private static final String DEFAULT_TOP_DOMAIN = "com";
    public static final String IDENTIFIER = "RANDOM_EMAIL";

    /**
     * The transform method provides the functionality to transform a provided <code>input</code>.
     * The transformation and the end result will be determine by how it is implemented by each {@link Expression}.
     * Each {@link Expression} provides it's own functionality and will transform the text differently.
     * @param input The input string that will be transformed.
     * @return A transformed <code>input</code>.
     */
    @Override
    public String transform(final ExpressionInput input) {

        final ExpressionArgument<?> domainArgument = input.getArgument(DOMAIN_ARGUMENT);
        final ExpressionArgument<?> topDomainArgument = input.getArgument(TOP_DOMAIN_ARGUMENT);


        final ExpressionArgument<?> minArgument = input.getArgument(MIN_ARGUMENT);
        final ExpressionArgument<?> maxArgument = input.getArgument(MAX_ARGUMENT);

        String domain = null;
        String topDomain = null;
        int minLength = MIN_LENGTH;
        int maxLength = MAX_LENGTH;

        if(domainArgument != null && domainArgument instanceof ExpressionArgumentString){
            domain = ((ExpressionArgumentString) domainArgument).getValue();
        } else {
            domain = randomString(RANDOM.nextInt(MAX_LENGTH) + MIN_LENGTH);
        }

        if(topDomainArgument != null && topDomainArgument instanceof ExpressionArgumentString){
            topDomain = ((ExpressionArgumentString) topDomainArgument).getValue();
        } else {
            topDomain = DEFAULT_TOP_DOMAIN;
        }

        if(minArgument != null && minArgument instanceof ExpressionArgumentNumber){
            minLength = ((ExpressionArgumentNumber) minArgument).getValue().intValue();
        }
        if(maxArgument != null && maxArgument instanceof ExpressionArgumentNumber){
            maxLength = ((ExpressionArgumentNumber) maxArgument).getValue().intValue();
        }

        final String username = randomString(RANDOM.nextInt(maxLength) + minLength);
        return username + AT + domain + DOT + topDomain;
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
