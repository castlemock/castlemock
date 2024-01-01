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


import com.castlemock.web.mock.soap.controller.mock.SoapServiceController;

import java.io.Serial;

/**
 * The SoapException is the main exception for the all SOAP related issues. All SOAP related exceptions
 * should inherit from the class.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SoapServiceController
 */

public class SoapException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 381683485628067054L;

    /**
     * The default constructor for the SoapException class
     * @param exception The exception name
     */
    public SoapException(final String exception) {
        super(exception);
    }

    /**
     * The default constructor for the SoapException class
     * @param exception The exception
     */
    public SoapException(final Exception exception) {
        super(exception);
    }

}
