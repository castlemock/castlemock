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

package com.castlemock.web.mock.rest.model;


import com.castlemock.web.basis.web.mvc.controller.ViewControllerAdvice;
import com.castlemock.web.mock.rest.web.mock.controller.RestControllerAdvice;
import com.castlemock.web.mock.rest.web.mock.controller.RestServiceController;

/**
 * The RestException is the main exception for the all REST related issues. All REST related exceptions
 * should inherit from the class. It is important that all REST exceptions are inheriting
 * from this class because the {@link RestControllerAdvice} will
 * only handle exceptions that is either an instance of the RestException class or an instance from a class that
 * is inherit from the RestException class. The {@link RestControllerAdvice}
 * will transform the exception message into a REST response message. If an exception is thrown from the
 * {@link RestServiceController}, the {@link ViewControllerAdvice}
 * will process the exception instead and a view will be sent as a response instead.
 * @author Karl Dahlgren
 * @since 1.0
 * @see RestControllerAdvice
 * @see ViewControllerAdvice
 */

public class RestException extends RuntimeException {

    private static final long serialVersionUID = 381683485628067012L;

    /**
     * The default constructor for the RestException class
     * @param message The exception message
     */
    public RestException(String message) {
        super(message);
    }

}
