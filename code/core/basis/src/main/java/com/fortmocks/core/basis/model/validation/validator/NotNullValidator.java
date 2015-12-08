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
import com.fortmocks.core.basis.model.validation.NotNull;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;

/**
 * The NotNullValidator is used to check and enforce the {@link NotNull} rule for messages.
 * @author Karl Dahlgren
 * @since 1.0
 * @see NotNull
 * @see Message
 */
public class NotNullValidator extends Validator {

    private static final Logger LOGGER = Logger.getLogger(NotNullValidator.class);

    /**
     * Default constructor for the NotNullValidator
     */
    public NotNullValidator() {
        super(NotNull.class);
    }

    /**
     * The method is responsible for validating a provided message and enforce the rule that the
     * validator is responsible for
     * @param message The message that will be validated
     * @param field The specific field that will be validated
     */
    @Override
    protected void validateMessage(final Message message, final String field){
        try {
            final Object object = FieldUtils.readField(message, field, true);
            if(object == null){
                throw new NullPointerException("The following value cannot be null in the message " + message.getClass().getSimpleName() + ": " + field);
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("Unable to read the following value in the message " + message.getClass().getSimpleName() + ": " + field, e);
        }
    }
}
