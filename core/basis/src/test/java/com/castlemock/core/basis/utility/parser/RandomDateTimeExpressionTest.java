package com.castlemock.core.basis.utility.parser;

import com.castlemock.core.basis.utility.parser.expression.ExpressionInput;
import com.castlemock.core.basis.utility.parser.expression.RandomDateExpression;
import com.castlemock.core.basis.utility.parser.expression.RandomDateTimeExpression;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RandomDateTimeExpressionTest {

    @Test
    public void testTransform(){
        final RandomDateTimeExpression expression = new RandomDateTimeExpression();
        final ExpressionInput expressionInput = new ExpressionInput(RandomDateExpression.IDENTIFIER);
        final String result = expression.transform(expressionInput);
        assertNotNull(result);
    }

}
