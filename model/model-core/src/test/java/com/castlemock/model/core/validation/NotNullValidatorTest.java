package com.castlemock.model.core.validation;

import com.castlemock.model.core.Message;
import com.castlemock.model.core.validation.validator.NotNullValidator;
import com.castlemock.model.core.validation.validator.Validator;
import org.junit.Test;

import java.util.Objects;

public class NotNullValidatorTest {


    @Test
    public void testNotNullValidation(){
        final Validator validator = new NotNullValidator();
        final TestMessage message = new TestMessage("Input");
        validator.validateMessage(message);
    }


    @Test(expected = NullPointerException.class)
    public void testNullValidation(){
        final Validator validator = new NotNullValidator();
        final TestMessage message = new TestMessage(null);
        validator.validateMessage(message);
    }

    @Test(expected = NullPointerException.class)
    public void testNullMessageValidation(){
        final Validator validator = new NotNullValidator();
        validator.validateMessage(null);
    }

    private record TestMessage(@NotNull String input) implements Message {

            private TestMessage(final String input) {
                this.input = Objects.requireNonNull(input, "input");
            }
        }

}
