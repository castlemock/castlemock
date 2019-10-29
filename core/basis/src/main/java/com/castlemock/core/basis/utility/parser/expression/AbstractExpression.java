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

package com.castlemock.core.basis.utility.parser.expression;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * The {@link AbstractExpression} is used as a based for all {@link Expression}
 * @author Karl Dahlgren
 * @since 1.6
 */
public abstract class AbstractExpression implements Expression {

    protected static final Random RANDOM = new Random();
    private static final RandomStringUtils RANDOM_STRING = new RandomStringUtils();

    /**
     * The method generates a {@link String} with a given length.
     * @param length The length of the generated {@link String}
     * @return A generated {@link String}
     * @since 1.13
     */
    protected String randomString(final int length){
        return RANDOM_STRING.randomAlphanumeric(length);
    }


}
