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

/**
 * The {@link ExpressionArgumentNumber} is an numeric argument for an
 * {@link com.castlemock.model.core.utility.parser.expression.Expression}.
 * @author Karl Dahlgren
 * @since 1.14
 */
public class ExpressionArgumentNumber extends ExpressionArgument<Double> {

    /**
     * Constructor for {@link ExpressionArgumentNumber}.
     * @param value The numeric value.
     */
    public ExpressionArgumentNumber(final Double value) {
        super(value);
    }
}
