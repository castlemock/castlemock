package com.castlemock.core.expression;

import org.antlr.v4.runtime.*;
import org.junit.Assert;
import org.junit.Test;


public class ExpressionParserTest {

    @Test
    public void testParserWithoutArguments(){
        ExpressionParser.ExpressionContext expression = parse("RANDOM_BOOLEAN");
        Assert.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assert.assertTrue(expression.arguments.isEmpty());
    }

    @Test
    public void testParserWithEmptyArguments(){
        ExpressionParser.ExpressionContext expression = parse("RANDOM_BOOLEAN()");
        Assert.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assert.assertTrue(expression.arguments.isEmpty());
    }

    @Test
    public void testParserWithOneArgumentString(){
        ExpressionParser.ExpressionContext expression = parse("RANDOM_BOOLEAN(Key=\"Value\")");
        Assert.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assert.assertEquals(1, expression.arguments.size());
        ExpressionParser.ArgumentContext argument = expression.arguments.get(0);
        ExpressionParser.ArgumentNameContext argumentName = argument.argumentName();
        ExpressionParser.ArgumentValueContext argumentValue = argument.argumentValue();

        Assert.assertEquals("Key", argumentName.getText());
        Assert.assertEquals("Value", argumentValue.argumentString().value.getText());
    }

    @Test
    public void testParserWithOneArgumentStringWithNumber(){
        ExpressionParser.ExpressionContext expression = parse("RANDOM_BOOLEAN(Key=\"Value123\")");
        Assert.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assert.assertEquals(1, expression.arguments.size());
        ExpressionParser.ArgumentContext argument = expression.arguments.get(0);
        ExpressionParser.ArgumentNameContext argumentName = argument.argumentName();
        ExpressionParser.ArgumentValueContext argumentValue = argument.argumentValue();

        Assert.assertEquals("Key", argumentName.getText());
        Assert.assertEquals("Value123", argumentValue.argumentString().value.getText());
    }

    @Test
    public void testParserWithOneArgumentInteger(){
        ExpressionParser.ExpressionContext expression = parse("RANDOM_BOOLEAN(Key=12)");
        Assert.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assert.assertEquals(1, expression.arguments.size());
        ExpressionParser.ArgumentContext argument = expression.arguments.get(0);
        ExpressionParser.ArgumentNameContext argumentName = argument.argumentName();
        ExpressionParser.ArgumentValueContext argumentValue = argument.argumentValue();
        Assert.assertEquals("Key", argumentName.getText());
        Assert.assertEquals("12", argumentValue.argumentNumber().value.getText());
    }

    @Test
    public void testParserWithOneArgumentArrayString(){
        ExpressionParser.ExpressionContext expression = parse("RANDOM_BOOLEAN(Key=[\"ValueA\",\"ValueB\",\"ValueC\"])");
        Assert.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assert.assertEquals(1, expression.arguments.size());
        ExpressionParser.ArgumentContext argument = expression.arguments.get(0);
        ExpressionParser.ArgumentNameContext argumentName = argument.argumentName();
        ExpressionParser.ArgumentValueContext argumentValue = argument.argumentValue();
        Assert.assertEquals("Key", argumentName.getText());
        Assert.assertEquals(3, argumentValue.array().value.size());
        Assert.assertEquals("ValueA", argumentValue.array().value.get(0).argumentString().value.getText());
        Assert.assertEquals("ValueB", argumentValue.array().value.get(1).argumentString().value.getText());
        Assert.assertEquals("ValueC", argumentValue.array().value.get(2).argumentString().value.getText());
    }

    @Test
    public void testParserWithOneArgumentArrayInteger(){
        ExpressionParser.ExpressionContext expression = parse("RANDOM_BOOLEAN(Key=[1,2,3])");
        Assert.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assert.assertEquals(1, expression.arguments.size());
        ExpressionParser.ArgumentContext argument = expression.arguments.get(0);
        ExpressionParser.ArgumentNameContext argumentName = argument.argumentName();
        ExpressionParser.ArgumentValueContext argumentValue = argument.argumentValue();
        Assert.assertEquals("Key", argumentName.getText());
        Assert.assertEquals(3, argumentValue.array().value.size());
        Assert.assertEquals("1", argumentValue.array().value.get(0).argumentNumber().value.getText());
        Assert.assertEquals("2", argumentValue.array().value.get(1).argumentNumber().value.getText());
        Assert.assertEquals("3", argumentValue.array().value.get(2).argumentNumber().value.getText());
    }

    private ExpressionParser.ExpressionContext parse(final String input){
        final CodePointCharStream stream = CharStreams.fromString(input);
        final ExpressionLexer lexer = new ExpressionLexer(stream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final ExpressionParser parser = new ExpressionParser(tokens);
        return parser.expression();
    }

}
