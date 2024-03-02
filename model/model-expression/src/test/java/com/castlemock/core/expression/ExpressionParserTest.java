package com.castlemock.core.expression;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ExpressionParserTest {

    @Test
    public void testParserWithoutArguments(){
        ExpressionParser.ExpressionContext expression = parse("${RANDOM_BOOLEAN}");
        Assertions.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assertions.assertTrue(expression.arguments.isEmpty());
    }

    @Test
    public void testParserWithEmptyArguments(){
        ExpressionParser.ExpressionContext expression = parse("${RANDOM_BOOLEAN()}");
        Assertions.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assertions.assertTrue(expression.arguments.isEmpty());
    }

    @Test
    public void testParserWithOneArgumentString(){
        ExpressionParser.ExpressionContext expression = parse("${RANDOM_BOOLEAN(Key=\"Value\")}");
        Assertions.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assertions.assertEquals(1, expression.arguments.size());
        ExpressionParser.ArgumentContext argument = expression.arguments.getFirst();
        ExpressionParser.ArgumentNameContext argumentName = argument.argumentName();
        ExpressionParser.ArgumentValueContext argumentValue = argument.argumentValue();

        Assertions.assertEquals("Key", argumentName.getText());
        Assertions.assertEquals("Value", argumentValue.argumentString().value.getText());
    }

    @Test
    public void testParserWithOneArgumentStringWithSlash(){
        ExpressionParser.ExpressionContext expression = parse("${RANDOM_BOOLEAN(Key=\\\"Value\\\")}");
        Assertions.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assertions.assertEquals(1, expression.arguments.size());
        ExpressionParser.ArgumentContext argument = expression.arguments.getFirst();
        ExpressionParser.ArgumentNameContext argumentName = argument.argumentName();
        ExpressionParser.ArgumentValueContext argumentValue = argument.argumentValue();

        Assertions.assertEquals("Key", argumentName.getText());
        Assertions.assertEquals("Value", argumentValue.argumentString().value.getText());
    }


    @Test
    public void testParserWithOneArgumentStringWithSpace(){
        ExpressionParser.ExpressionContext expression = parse("${RANDOM_BOOLEAN(Key=\"Good day to you.\")}");
        Assertions.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assertions.assertEquals(1, expression.arguments.size());
        ExpressionParser.ArgumentContext argument = expression.arguments.getFirst();
        ExpressionParser.ArgumentNameContext argumentName = argument.argumentName();
        ExpressionParser.ArgumentValueContext argumentValue = argument.argumentValue();

        Assertions.assertEquals("Key", argumentName.getText());
        Assertions.assertEquals("Good day to you.", argumentValue.argumentString().value.getText());
    }

    @Test
    public void testParserWithOneArgumentStringWithNumber(){
        ExpressionParser.ExpressionContext expression = parse("${RANDOM_BOOLEAN(Key=\"Value123\")}");
        Assertions.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assertions.assertEquals(1, expression.arguments.size());
        ExpressionParser.ArgumentContext argument = expression.arguments.getFirst();
        ExpressionParser.ArgumentNameContext argumentName = argument.argumentName();
        ExpressionParser.ArgumentValueContext argumentValue = argument.argumentValue();

        Assertions.assertEquals("Key", argumentName.getText());
        Assertions.assertEquals("Value123", argumentValue.argumentString().value.getText());
    }

    @Test
    public void testParserWithOneArgumentInteger(){
        ExpressionParser.ExpressionContext expression = parse("${RANDOM_BOOLEAN(Key=12)}");
        Assertions.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assertions.assertEquals(1, expression.arguments.size());
        ExpressionParser.ArgumentContext argument = expression.arguments.getFirst();
        ExpressionParser.ArgumentNameContext argumentName = argument.argumentName();
        ExpressionParser.ArgumentValueContext argumentValue = argument.argumentValue();
        Assertions.assertEquals("Key", argumentName.getText());
        Assertions.assertEquals("12", argumentValue.argumentNumber().value.getText());
    }

    @Test
    public void testParserWithOneArgumentArrayString(){
        ExpressionParser.ExpressionContext expression = parse("${RANDOM_BOOLEAN(Key=[\"ValueA\",\"ValueB\",\"ValueC\"])}");
        Assertions.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assertions.assertEquals(1, expression.arguments.size());
        ExpressionParser.ArgumentContext argument = expression.arguments.getFirst();
        ExpressionParser.ArgumentNameContext argumentName = argument.argumentName();
        ExpressionParser.ArgumentValueContext argumentValue = argument.argumentValue();
        Assertions.assertEquals("Key", argumentName.getText());
        Assertions.assertEquals(3, argumentValue.array().value.size());
        Assertions.assertEquals("ValueA", argumentValue.array().value.getFirst().argumentString().value.getText());
        Assertions.assertEquals("ValueB", argumentValue.array().value.get(1).argumentString().value.getText());
        Assertions.assertEquals("ValueC", argumentValue.array().value.get(2).argumentString().value.getText());
    }

    @Test
    public void testParserWithOneArgumentArrayStringWithSlash(){
        ExpressionParser.ExpressionContext expression = parse("${RANDOM_BOOLEAN(Key=[\\\"ValueA\\\",\\\"ValueB\\\",\\\"ValueC\\\"])}");
        Assertions.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assertions.assertEquals(1, expression.arguments.size());
        ExpressionParser.ArgumentContext argument = expression.arguments.getFirst();
        ExpressionParser.ArgumentNameContext argumentName = argument.argumentName();
        ExpressionParser.ArgumentValueContext argumentValue = argument.argumentValue();
        Assertions.assertEquals("Key", argumentName.getText());
        Assertions.assertEquals(3, argumentValue.array().value.size());
        Assertions.assertEquals("ValueA", argumentValue.array().value.getFirst().argumentString().value.getText());
        Assertions.assertEquals("ValueB", argumentValue.array().value.get(1).argumentString().value.getText());
        Assertions.assertEquals("ValueC", argumentValue.array().value.get(2).argumentString().value.getText());
    }

    @Test
    public void testParserWithOneArgumentArrayInteger(){
        ExpressionParser.ExpressionContext expression = parse("${RANDOM_BOOLEAN(Key=[1,2,3])}");
        Assertions.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assertions.assertEquals(1, expression.arguments.size());
        ExpressionParser.ArgumentContext argument = expression.arguments.getFirst();
        ExpressionParser.ArgumentNameContext argumentName = argument.argumentName();
        ExpressionParser.ArgumentValueContext argumentValue = argument.argumentValue();
        Assertions.assertEquals("Key", argumentName.getText());
        Assertions.assertEquals(3, argumentValue.array().value.size());
        Assertions.assertEquals("1", argumentValue.array().value.getFirst().argumentNumber().value.getText());
        Assertions.assertEquals("2", argumentValue.array().value.get(1).argumentNumber().value.getText());
        Assertions.assertEquals("3", argumentValue.array().value.get(2).argumentNumber().value.getText());
    }

    @Test
    public void testParserWithMultipleArguments(){
        ExpressionParser.ExpressionContext expression = parse("${RANDOM_BOOLEAN(Key1=\"Value1\",Key2=\"Value2\")}");
        Assertions.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assertions.assertEquals(2, expression.arguments.size());

        // First argument
        ExpressionParser.ArgumentContext firstArgument = expression.arguments.getFirst();
        ExpressionParser.ArgumentNameContext firstArgumentName = firstArgument.argumentName();
        ExpressionParser.ArgumentValueContext firstArgumentValue = firstArgument.argumentValue();

        Assertions.assertEquals("Key1", firstArgumentName.getText());
        Assertions.assertEquals("Value1", firstArgumentValue.argumentString().value.getText());

        // Second argument
        ExpressionParser.ArgumentContext secondArgument = expression.arguments.get(1);
        ExpressionParser.ArgumentNameContext secondArgumentName = secondArgument.argumentName();
        ExpressionParser.ArgumentValueContext secondArgumentValue = secondArgument.argumentValue();

        Assertions.assertEquals("Key2", secondArgumentName.getText());
        Assertions.assertEquals("Value2", secondArgumentValue.argumentString().value.getText());
    }

    @Test
    public void testParserWithMultipleArgumentsWithSpaces(){
        ExpressionParser.ExpressionContext expression = parse("${RANDOM_BOOLEAN( Key1=\"Value1\", Key2=\"Value2\")}");
        Assertions.assertEquals(expression.type.getText(), "RANDOM_BOOLEAN");
        Assertions.assertEquals(2, expression.arguments.size());

        // First argument
        ExpressionParser.ArgumentContext firstArgument = expression.arguments.getFirst();
        ExpressionParser.ArgumentNameContext firstArgumentName = firstArgument.argumentName();
        ExpressionParser.ArgumentValueContext firstArgumentValue = firstArgument.argumentValue();

        Assertions.assertEquals("Key1", firstArgumentName.getText());
        Assertions.assertEquals("Value1", firstArgumentValue.argumentString().value.getText());

        // Second argument
        ExpressionParser.ArgumentContext secondArgument = expression.arguments.get(1);
        ExpressionParser.ArgumentNameContext secondArgumentName = secondArgument.argumentName();
        ExpressionParser.ArgumentValueContext secondArgumentValue = secondArgument.argumentValue();

        Assertions.assertEquals("Key2", secondArgumentName.getText());
        Assertions.assertEquals("Value2", secondArgumentValue.argumentString().value.getText());
    }

    private ExpressionParser.ExpressionContext parse(final String input){
        final CodePointCharStream stream = CharStreams.fromString(input);
        final ExpressionLexer lexer = new ExpressionLexer(stream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final ExpressionParser parser = new ExpressionParser(tokens);
        return parser.expression();
    }

}
