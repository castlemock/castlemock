package com.castlemock.core.basis.model.validation;

import com.castlemock.core.basis.model.Message;
import com.castlemock.core.basis.model.validation.validator.NotNullValidator;
import com.castlemock.core.basis.model.validation.validator.Validator;
import org.junit.Test;

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

    private static class TestMessage implements Message {

        @NotNull
        private final String input;

        private TestMessage(final String input) {
            this.input = input;
        }
    }

}
