/*
 * Copyright 2019 Karl Dahlgren
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
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentString;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * {@link UrlHostExpression} is an {@link Expression} and will extract the host name
 * from the incoming HTTP request URL and replace it with the {@link #IDENTIFIER}.
 * @author Karl Dahlgren
 * @since 1.40
 */
public class UrlHostExpression extends AbstractExpression {


    public static final String IDENTIFIER = "URL_HOST";
    public static final String URL_ARGUMENT = "URL_ARGUMENT";
    private static final String MISSING_URL_ARGUMENT = "";

    /**
     * The transform method provides the functionality to transform a provided <code>input</code>.
     * The transformation and the end result will be determine by how it is implemented by each {@link Expression}.
     * Each {@link Expression} provides it's own functionality and will transform the text differently.
     * @param input The input string that will be transformed.
     * @return A transformed <code>input</code>.
     */
    @Override
    public String transform(final ExpressionInput input) {
        final ExpressionArgument<?> urlArgument = input.getArgument(URL_ARGUMENT);

        if(!(urlArgument instanceof ExpressionArgumentString argument)){
            return MISSING_URL_ARGUMENT;
        }

        final String rawUrl = argument.getValue();

        if(rawUrl == null){
            return MISSING_URL_ARGUMENT;
        }

        try {
            return new URI(rawUrl).toURL().getHost();
        } catch (MalformedURLException | URISyntaxException e) {
            return MISSING_URL_ARGUMENT;
        }
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
