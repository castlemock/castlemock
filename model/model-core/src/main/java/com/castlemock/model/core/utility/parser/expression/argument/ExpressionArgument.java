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

package com.castlemock.model.core.utility.parser.expression.argument;

import java.util.Objects;

/**
 * The {@link ExpressionArgument} represent an argument for an
 * {@link com.castlemock.model.core.utility.parser.expression.Expression} and
 * is a container class for a typed value.
 * <p>
 * There are three different types of arguments:
 * <ul>
 *     <li><b>String: </b>{@link ExpressionArgumentString}.</li>
 *     <li><b>Number: </b>{@link ExpressionArgumentNumber}</li>
 *     <li><b>Array: </b>{@link ExpressionArgumentArray}</li>
 * </ul>
 * @author Karl Dahlgren
 * @since 1.14
 */
public abstract class ExpressionArgument<V> {

    protected final V value;

    /**
     * Constructor for {@link ExpressionArgument}.
     * @param value The value that the argument will contain.
     */
    public ExpressionArgument(final V value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Get the argument value.
     * @return The argument value.
     */
    public V getValue() {
        return value;
    }

}
