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

package com.castlemock.core.basis.utility.parser;

import com.castlemock.core.basis.utility.parser.expression.ExpressionInput;
import com.castlemock.core.basis.utility.parser.expression.ExpressionInputParser;
import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgumentArray;
import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgumentNumber;
import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgumentString;
import org.junit.Assert;
import org.junit.Test;

/**
 * @since 1.14
 */
public class ExpressionInputParserTest {

    @Test
    public void testParse(){
        ExpressionInput expressionInput = ExpressionInputParser.parse("${RANDOM_ENUM}");
        Assert.assertEquals(expressionInput.getName(), "RANDOM_ENUM");
    }

    @Test
    public void testParseWithNoArguments(){
        ExpressionInput expressionInput = ExpressionInputParser.parse("${RANDOM_ENUM()}");
        Assert.assertEquals(expressionInput.getName(), "RANDOM_ENUM");
    }

    @Test
    public void testParseWithStringArgument(){
        ExpressionInput expressionInput = ExpressionInputParser.parse("${RANDOM_ENUM(Key=\"Value\")}");
        Assert.assertEquals(expressionInput.getName(), "RANDOM_ENUM");
        Assert.assertEquals(1, expressionInput.getArguments().size());

        ExpressionArgument<?> expressionArgument = expressionInput.getArgument("Key");
        Assert.assertTrue(expressionArgument instanceof ExpressionArgumentString);
        ExpressionArgumentString argumentString = (ExpressionArgumentString) expressionArgument;
        Assert.assertEquals("Value", argumentString.getValue());
    }

    @Test
    public void testParseWithNumberArgument(){
        ExpressionInput expressionInput = ExpressionInputParser.parse("${RANDOM_INTEGER(min=123)}");
        Assert.assertEquals(expressionInput.getName(), "RANDOM_INTEGER");
        Assert.assertEquals(1, expressionInput.getArguments().size());

        ExpressionArgument<?> expressionArgument = expressionInput.getArgument("min");
        Assert.assertTrue(expressionArgument instanceof ExpressionArgumentNumber);
        ExpressionArgumentNumber argumentNumber = (ExpressionArgumentNumber) expressionArgument;
        Assert.assertEquals(123.0, argumentNumber.getValue(), 0);
    }

    @Test
    public void testParseWithArrayArgument(){
        ExpressionInput expressionInput = ExpressionInputParser.parse("${RANDOM_ENUM(values=[\"X\",\"Y\",\"Z\"])}");
        Assert.assertEquals(expressionInput.getName(), "RANDOM_ENUM");
        Assert.assertEquals(1, expressionInput.getArguments().size());

        ExpressionArgument<?> expressionArgument = expressionInput.getArgument("values");
        Assert.assertTrue(expressionArgument instanceof ExpressionArgumentArray);
        ExpressionArgumentArray argumentArray = (ExpressionArgumentArray) expressionArgument;

        Assert.assertEquals(3, argumentArray.getArgumentSize());
        Assert.assertEquals("X", argumentArray.getArgument(0).getValue());
        Assert.assertEquals("Y", argumentArray.getArgument(1).getValue());
        Assert.assertEquals("Z", argumentArray.getArgument(2).getValue());
    }

    @Test
    public void testParseWithMixedArguments(){
        ExpressionInput expressionInput = ExpressionInputParser.parse("${RANDOM_ENUM(value1=\"This is a value\", value2=999.9, value3=[\"X\",\"Y\",\"Z\"])}");
        Assert.assertEquals(expressionInput.getName(), "RANDOM_ENUM");
        Assert.assertEquals(3, expressionInput.getArguments().size());

        ExpressionArgument<?> expressionArgument1 = expressionInput.getArgument("value1");
        Assert.assertTrue(expressionArgument1 instanceof ExpressionArgumentString);
        ExpressionArgumentString argumentString = (ExpressionArgumentString) expressionArgument1;
        Assert.assertEquals("This is a value", argumentString.getValue());

        ExpressionArgument<?> expressionArgument2 = expressionInput.getArgument("value2");
        Assert.assertTrue(expressionArgument2 instanceof ExpressionArgumentNumber);
        ExpressionArgumentNumber argumentNumber = (ExpressionArgumentNumber) expressionArgument2;
        Assert.assertEquals(999.9, argumentNumber.getValue(), 0);


        ExpressionArgument<?> expressionArgument3 = expressionInput.getArgument("value3");
        Assert.assertTrue(expressionArgument3 instanceof ExpressionArgumentArray);
        ExpressionArgumentArray argumentArray = (ExpressionArgumentArray) expressionArgument3;

        Assert.assertEquals(3, argumentArray.getArgumentSize());
        Assert.assertEquals("X", argumentArray.getArgument(0).getValue());
        Assert.assertEquals("Y", argumentArray.getArgument(1).getValue());
        Assert.assertEquals("Z", argumentArray.getArgument(2).getValue());
    }

    @Test
    public void testConvert(){
        ExpressionInput expressionInput = new ExpressionInput("RANDOM_INTEGER");
        String output = ExpressionInputParser.convert(expressionInput);
        Assert.assertEquals("${RANDOM_INTEGER()}", output);
    }

    @Test
    public void testConvertWithStringArgument(){
        ExpressionInput expressionInput = new ExpressionInput("RANDOM_INTEGER");
        expressionInput.addArgument("Key", new ExpressionArgumentString("Value"));
        String output = ExpressionInputParser.convert(expressionInput);
        Assert.assertEquals("${RANDOM_INTEGER(Key=\"Value\")}", output);
    }

    @Test
    public void testConvertWithNumberArgument(){
        ExpressionInput expressionInput = new ExpressionInput("RANDOM_INTEGER");
        expressionInput.addArgument("min", new ExpressionArgumentNumber(1000.0));
        String output = ExpressionInputParser.convert(expressionInput);
        Assert.assertEquals("${RANDOM_INTEGER(min=1000.0)}", output);
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
        Assert.assertEquals("${RANDOM_ENUM(Array=[\"X\",\"Y\",\"Z\"])}", output);
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
        Assert.assertEquals("${RANDOM_ENUM(Key1=\"Value\",Key2=1000.0,Key3=[\"X\",\"Y\",\"Z\"])}", output);
    }
}
