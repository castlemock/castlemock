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

package com.castlemock.model.core.utility.parser;

import com.castlemock.model.core.utility.parser.expression.ExpressionInput;
import com.castlemock.model.core.utility.parser.expression.ExpressionInputParser;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentArray;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentNumber;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @since 1.14
 */
public class ExpressionInputParserTest {

    @Test
    public void testParse(){
        ExpressionInput expressionInput = ExpressionInputParser.parse("${RANDOM_ENUM}");
        assertEquals(expressionInput.getName(), "RANDOM_ENUM");
    }

    @Test
    public void testParseWithNoArguments(){
        ExpressionInput expressionInput = ExpressionInputParser.parse("${RANDOM_ENUM()}");
        assertEquals(expressionInput.getName(), "RANDOM_ENUM");
    }

    @Test
    public void testParseWithStringArgument(){
        ExpressionInput expressionInput = ExpressionInputParser.parse("${RANDOM_ENUM(Key=\"Value\")}");
        assertEquals(expressionInput.getName(), "RANDOM_ENUM");
        assertEquals(1, expressionInput.getArguments().size());

        ExpressionArgument<?> expressionArgument = expressionInput.getArgument("Key");
        Assertions.assertTrue(expressionArgument instanceof ExpressionArgumentString);
        ExpressionArgumentString argumentString = (ExpressionArgumentString) expressionArgument;
        assertEquals("Value", argumentString.getValue());
    }

    @Test
    public void testParseWithNumberArgument(){
        ExpressionInput expressionInput = ExpressionInputParser.parse("${RANDOM_INTEGER(min=123)}");
        assertEquals(expressionInput.getName(), "RANDOM_INTEGER");
        assertEquals(1, expressionInput.getArguments().size());

        ExpressionArgument<?> expressionArgument = expressionInput.getArgument("min");
        Assertions.assertTrue(expressionArgument instanceof ExpressionArgumentNumber);
        ExpressionArgumentNumber argumentNumber = (ExpressionArgumentNumber) expressionArgument;
        assertEquals(123.0, argumentNumber.getValue(), 0);
    }

    @Test
    public void testParseWithArrayArgument(){
        ExpressionInput expressionInput = ExpressionInputParser.parse("${RANDOM_ENUM(values=[\"X\",\"Y\",\"Z\"])}");
        assertEquals(expressionInput.getName(), "RANDOM_ENUM");
        assertEquals(1, expressionInput.getArguments().size());

        ExpressionArgument<?> expressionArgument = expressionInput.getArgument("values");
        Assertions.assertTrue(expressionArgument instanceof ExpressionArgumentArray);
        ExpressionArgumentArray argumentArray = (ExpressionArgumentArray) expressionArgument;

        assertEquals(3, argumentArray.getArgumentSize());
        assertEquals("X", argumentArray.getArgument(0).getValue());
        assertEquals("Y", argumentArray.getArgument(1).getValue());
        assertEquals("Z", argumentArray.getArgument(2).getValue());
    }

    @Test
    public void testParseWithMixedArguments(){
        ExpressionInput expressionInput = ExpressionInputParser.parse("${RANDOM_ENUM(value1=\"This is a value\", value2=999.9, value3=[\"X\",\"Y\",\"Z\"])}");
        assertEquals(expressionInput.getName(), "RANDOM_ENUM");
        assertEquals(3, expressionInput.getArguments().size());

        ExpressionArgument<?> expressionArgument1 = expressionInput.getArgument("value1");
        Assertions.assertTrue(expressionArgument1 instanceof ExpressionArgumentString);
        ExpressionArgumentString argumentString = (ExpressionArgumentString) expressionArgument1;
        assertEquals("This is a value", argumentString.getValue());

        ExpressionArgument<?> expressionArgument2 = expressionInput.getArgument("value2");
        Assertions.assertTrue(expressionArgument2 instanceof ExpressionArgumentNumber);
        ExpressionArgumentNumber argumentNumber = (ExpressionArgumentNumber) expressionArgument2;
        assertEquals(999.9, argumentNumber.getValue(), 0);


        ExpressionArgument<?> expressionArgument3 = expressionInput.getArgument("value3");
        Assertions.assertTrue(expressionArgument3 instanceof ExpressionArgumentArray);
        ExpressionArgumentArray argumentArray = (ExpressionArgumentArray) expressionArgument3;

        assertEquals(3, argumentArray.getArgumentSize());
        assertEquals("X", argumentArray.getArgument(0).getValue());
        assertEquals("Y", argumentArray.getArgument(1).getValue());
        assertEquals("Z", argumentArray.getArgument(2).getValue());
    }

    @Test
    public void testConvert(){
        ExpressionInput expressionInput = new ExpressionInput("RANDOM_INTEGER");
        String output = ExpressionInputParser.convert(expressionInput);
        assertEquals("${RANDOM_INTEGER()}", output);
    }

    @Test
    public void testConvertWithStringArgument(){
        ExpressionInput expressionInput = new ExpressionInput("RANDOM_INTEGER");
        expressionInput.addArgument("Key", new ExpressionArgumentString("Value"));
        String output = ExpressionInputParser.convert(expressionInput);
        assertEquals("${RANDOM_INTEGER(Key=\"Value\")}", output);
    }

    @Test
    public void testConvertWithNumberArgument(){
        ExpressionInput expressionInput = new ExpressionInput("RANDOM_INTEGER");
        expressionInput.addArgument("min", new ExpressionArgumentNumber(1000.0));
        String output = ExpressionInputParser.convert(expressionInput);
        assertEquals("${RANDOM_INTEGER(min=1000.0)}", output);
    }

    @Test
    public void testConvertWithArrayArgument(){
        ExpressionInput expressionInput = new ExpressionInput("RANDOM_ENUM");
        ExpressionArgumentArray argumentArray = new ExpressionArgumentArray();
        argumentArray.addArgument(new ExpressionArgumentString("X"));
        argumentArray.addArgument(new ExpressionArgumentString("Y"));
        argumentArray.addArgument(new ExpressionArgumentString("Z"));
        expressionInput.addArgument("Array", argumentArray);
        String output = ExpressionInputParser.convert(expressionInput);
        assertEquals("${RANDOM_ENUM(Array=[\"X\",\"Y\",\"Z\"])}", output);
    }

    @Test
    public void testConvertWithMixedArguments(){
        ExpressionInput expressionInput = new ExpressionInput("RANDOM_ENUM");
        expressionInput.addArgument("Key1", new ExpressionArgumentString("Value"));
        expressionInput.addArgument("Key2", new ExpressionArgumentNumber(1000.0));
        ExpressionArgumentArray argumentArray = new ExpressionArgumentArray();
        argumentArray.addArgument(new ExpressionArgumentString("X"));
        argumentArray.addArgument(new ExpressionArgumentString("Y"));
        argumentArray.addArgument(new ExpressionArgumentString("Z"));
        expressionInput.addArgument("Key3", argumentArray);
        String output = ExpressionInputParser.convert(expressionInput);
        assertEquals("${RANDOM_ENUM(Key1=\"Value\",Key2=1000.0,Key3=[\"X\",\"Y\",\"Z\"])}", output);
    }

    @Test
    public void testParseWithQuotationMarksInExpression(){
        final String expression = "${BODY_XPATH(expression=\"substring-after(//GetWhoIS/HostName/text(), 'V'\")}";
        final ExpressionInput input = ExpressionInputParser.parse(expression);

        assertEquals("BODY_XPATH", input.getName());
        assertEquals(1, input.getArguments().size());
        assertEquals("substring-after(//GetWhoIS/HostName/text(), 'V'", input.getArgument("expression").getValue());
    }
}
