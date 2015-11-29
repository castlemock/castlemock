/*
 * Copyright 2015 Karl Dahlgren
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
