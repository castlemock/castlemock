package com.fortmocks.core.basis.model.validation.validator;

import com.fortmocks.core.basis.model.Message;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * The Validator is used to check and enforce validation rules. The rules are applied to {@link Message} classes,
 * such as {@link com.fortmocks.core.basis.model.Input} or {@link com.fortmocks.core.basis.model.Output}.
 * @author Karl Dahlgren
 * @since 1.0
 * @see Message
 */
public abstract class Validator {

    private Class<? extends Annotation> validationType;
    protected Validator(Class<? extends Annotation> validationType){
        this.validationType = validationType;
    }

    /**
     * The method is responsible for checking if a message contains annotations that the validator is
     * responsible for validating or enforce.
     * @param message The message the will be validated.
     * @throws NullPointerException If the provided message is null
     */
    public void validateMessage(final Message message){
        if(message == null){
            throw new NullPointerException("The message cannot be null");
        }

        final Class<?> messageClass = message.getClass();
        for(Field field : messageClass.getDeclaredFields()){
            if(field.isAnnotationPresent(validationType)){
                validateMessage(message, field.getName());
            }
        }
    }

    /**
     * The method is responsible for validating a provided message and enforce the rule that the
     * validator is responsible for
     * @param message The message that will be validated
     * @param field The specific field that will be validated
     */
    protected abstract void validateMessage(final Message message, final String field);

}
