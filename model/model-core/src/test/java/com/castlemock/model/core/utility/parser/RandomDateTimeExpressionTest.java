/*
 * Copyright 2024 Karl Dahlgren
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

package com.castlemock.model.core.utility.parser;

import com.castlemock.model.core.utility.parser.expression.ExpressionInput;
import com.castlemock.model.core.utility.parser.expression.RandomDateExpression;
import com.castlemock.model.core.utility.parser.expression.RandomDateTimeExpression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RandomDateTimeExpressionTest {

    @Test
    public void testTransform(){
        final RandomDateTimeExpression expression = new RandomDateTimeExpression();
        final ExpressionInput expressionInput = new ExpressionInput(RandomDateExpression.IDENTIFIER);
        final String result = expression.transform(expressionInput);
        assertNotNull(result);
    }

}
