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

package com.castlemock.web.mock.soap.model;


import com.castlemock.web.basis.web.mvc.controller.ViewControllerAdvice;
import com.castlemock.web.mock.soap.web.soap.controller.SoapControllerAdvice;
import com.castlemock.web.mock.soap.web.soap.controller.SoapServiceController;

/**
 * The SoapException is the main exception for the all SOAP related issues. All SOAP related exceptions
 * should inherit from the class. It is important that all SOAP exceptions are inheriting
 * from this class because the {@link SoapControllerAdvice} will
 * only handle exceptions that is either an instance of the SoapException class or an instance from a class that
 * is inherit from the SoapException class. The {@link SoapControllerAdvice}
 * will transform the exception message into a SOAP response message. If an exception is thrown from the
 * {@link SoapServiceController}, the {@link ViewControllerAdvice}
 * will process the exception instead and a view will be sent as a response instead.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SoapControllerAdvice
 * @see ViewControllerAdvice
 * @see SoapServiceController
 */

public class SoapException extends RuntimeException {

    private static final long serialVersionUID = 381683485628067054L;

    /**
     * The default constructor for the SoapException class
     * @param message The exception message
     */
    public SoapException(String message) {
        super(message);
    }

}
