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

package com.fortmocks.war.mock.rest.web.rest.controller;

import com.fortmocks.war.base.web.mvc.controller.AbstractViewController;
import com.fortmocks.war.mock.rest.model.RestErrorMessage;
import com.fortmocks.war.mock.rest.model.RestException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * The GlobalControllerExceptionManager handles all the uncaught exceptions
 * @author Karl Dahlgren
 * @since 1.0
 */
@ControllerAdvice
public class RestControllerAdvice extends AbstractViewController {

    /**
     * Handles uncaught RestException
     * @param exception The uncaught RestException
     * @return Returns a ErrorWebServiceMessage containing the message from the provided exception
     * @see RestException
     */
    @ResponseBody
    @ExceptionHandler(value = RestException.class)
    public RestErrorMessage defaultErrorWebServiceHandler(RestException exception)  {
        return new RestErrorMessage(exception.getMessage());
    }


}
