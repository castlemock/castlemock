package com.fortmocks.core.basis.model.validation.validator;

import com.fortmocks.core.basis.model.Message;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class Validator {

    private Class<? extends Annotation> validationType;

    protected Validator(Class<? extends Annotation> validationType){
        this.validationType = validationType;
    }

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

    protected abstract <M extends Message> void validateMessage(final Message message, final String field);

}
