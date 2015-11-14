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

package com.fortmocks.mock.soap.web.soap.controller;

import com.fortmocks.web.basis.web.mvc.controller.AbstractViewController;
import com.fortmocks.core.mock.soap.model.SoapErrorMessage;
import com.fortmocks.core.mock.soap.model.SoapException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * The GlobalControllerExceptionManager handles all the uncaught exceptions
 * @author Karl Dahlgren
 * @since 1.0
 */
@ControllerAdvice
public class SoapControllerAdvice extends AbstractViewController {

    /**
     * Handles uncaught SoapException
     * @param exception The uncaught SoapException
     * @return Returns a ErrorWebServiceMessage containing the message from the provided exception
     */
    @ResponseBody
    @ExceptionHandler(value = SoapException.class)
    public SoapErrorMessage defaultErrorWebServiceHandler(SoapException exception)  {
        return new SoapErrorMessage(exception.getMessage());
    }


}
