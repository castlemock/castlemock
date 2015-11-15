package com.fortmocks.core.basis.model.validation.validator;

import com.fortmocks.core.basis.model.Message;
import com.fortmocks.core.basis.model.validation.NotNull;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class NotNullValidator extends Validator {

    private static final Logger LOGGER = Logger.getLogger(NotNullValidator.class);

    @Override
    public <M extends Message> void validateMessage(final M message){
        if(message == null){
            throw new NullPointerException("The message cannot be null");
        }

        final Class<?> messageClass = message.getClass();
        for(Field field : messageClass.getDeclaredFields()){
            if(field.isAnnotationPresent(NotNull.class)){
                validateNotNull(message, field.getName());
            }
        }
    }

    private  <M extends Message> void validateNotNull(final M message, final String field){
        try {
            final Object object = FieldUtils.readField(message, field, true);
            if(object == null){
                throw new NullPointerException("The following value cannot be null in the message " + message.getClass().getSimpleName() + ": " + field);
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("Unable to read the following value in the message " + message.getClass().getSimpleName() + ": " + field);
        }
    }
}
