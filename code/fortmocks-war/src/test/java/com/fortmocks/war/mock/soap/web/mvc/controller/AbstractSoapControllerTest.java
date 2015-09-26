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

package com.fortmocks.war.mock.soap.web.mvc.controller;

import com.fortmocks.war.base.web.mvc.controller.AbstractControllerTest;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractSoapControllerTest extends AbstractControllerTest {

    protected static final String SOAP_MOCK_RESPONSE_STRATEGIES = "soapMockResponseStrategies";

    protected static final String SOAP_PROJECT = "soapProject";
    protected static final String OPERATION = "operation";
    protected static final String PORT = "port";

    protected static final String SOAP_PROJECT_ID = "soapProjectId";
    protected static final String SOAP_PORT_ID = "soapPortId";
    protected static final String SOAP_OPERATION_ID = "soapOperationId";

    protected static final String SOAP_PORT = "soapPort";
    protected static final String SOAP_OPERATION = "soapOperation";
}
