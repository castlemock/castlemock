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

package com.fortmocks.web.core.service;

import com.fortmocks.core.basis.model.*;
import com.fortmocks.core.basis.model.*;
import com.fortmocks.core.basis.model.user.domain.User;
import com.fortmocks.core.basis.model.validation.NotNull;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Component
public class ServiceProcessor {

    @Autowired
    private ServiceRegistry serviceRegistry;

    private static final String UNKNOWN_USER = "Unknown";
    private static final Logger LOGGER = Logger.getLogger(ServiceProcessor.class);


    public <I extends Input, O extends Output> O process(final I input){
        validateMessage(input);
        final Service<I,O> service = serviceRegistry.getProcessor(input);

        if(service == null){
            throw new IllegalArgumentException("Unable to find service for " + input.getClass().getSimpleName());
        }

        final Task<I> task = new Task<I>();
        task.setInput(input);
        task.setExecuter(getLoggedInUsername());
        LOGGER.debug(getLoggedInUsername() + " is requesting " + service.getClass().getSimpleName() + " to process the following input message: " + input.getClass().getSimpleName());
        final Result<O> result = service.process(task);
        validateMessage(result.getOutput());
        return result.getOutput();
    }

    protected <M extends Message> void validateMessage(final M message){
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

    protected  <M extends Message> void validateNotNull(final M message, final String field){
        try {
            final Object object = FieldUtils.readField(message, field, true);
            if(object == null){
                throw new NullPointerException("The following value cannot be null in the message " + message.getClass().getSimpleName() + ": " + field);
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("Unable to read the following value in the message " + message.getClass().getSimpleName() + ": " + field);
        }
    }

    /**
     * Get the current logged in user username
     * @return The username of the current logged in user
     * @see User
     */
    protected String getLoggedInUsername(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return UNKNOWN_USER;
        }
        return authentication.getName();
    }




}
