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

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * The {@link ExpressionInput} is used as both an identifier for an
 * {@link com.castlemock.model.core.utility.parser.expression.Expression},
 * but also a container for its arguments ({@link ExpressionArgument}).
 *
 * @author Karl Dahlgren
 * @since 1.14
 */
public class ExpressionInput {

    private final String name;
    private Map<String, ExpressionArgument<?>> arguments;

    /**
     * Constructor for {@link ExpressionInput}.
     * @param name The name of the expression.
     */
    public ExpressionInput(final String name){
        this.name = Objects.requireNonNull(name);
        this.arguments = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Get an argument that with a given argument name.
     * @param name The name of the argument that should be returned.
     * @return An {@link ExpressionArgument} that matches the provided search criteria.
     * Null otherwise.
     */
    public ExpressionArgument<?> getArgument(final String name){
        return this.arguments.get(name);
    }

    /**
     * Add a new {@link ExpressionArgument}.
     * @param name The name and identifier for the {@link ExpressionArgument}.
     * @param argument The {@link ExpressionArgument} that will be added.
     */
    public void addArgument(final String name, final ExpressionArgument<?> argument){
        this.arguments.put(name, argument);
    }

    /**
     * Get the expression name.
     * @return The name of the expression.
     */
    public String getName() {
        return name;
    }

    public Map<String, ExpressionArgument<?>> getArguments(){
        final Map<String, ExpressionArgument<?>> output = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        output.putAll(arguments);
        return output;
    }
}
