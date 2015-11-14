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

package com.fortmocks.web.mock.soap.web.mvc.controller;

import com.fortmocks.web.basis.web.mvc.controller.AbstractViewController;

/**
 * The class operates as a shared base for all the view related to the SOAP module
 * @author Karl Dahlgren
 * @since 1.0
 * @see AbstractViewController
 */
public class AbstractSoapViewController extends AbstractViewController {

    protected static final String SOAP = "soap";
    protected static final String SOAP_PORT = "soapPort";
    protected static final String SOAP_PORT_ID = "soapPortId";
    protected static final String SOAP_PORTS = "soapPorts";
    protected static final String SOAP_PROJECT = "soapProject";
    protected static final String SOAP_PROJECT_ID = "soapProjectId";
    protected static final String SOAP_OPERATION_STATUSES = "soapOperationStatuses";
    protected static final String SOAP_OPERATION = "soapOperation";
    protected static final String SOAP_OPERATION_ID = "soapOperationId";
    protected static final String SOAP_OPERATIONS = "soapOperations";
    protected static final String SOAP_MOCK_RESPONSE = "soapMockResponse";
    protected static final String SOAP_MOCK_RESPONSES = "soapMockResponses";
    protected static final String SOAP_MOCK_RESPONSE_STATUSES = "soapMockResponseStatuses";
    protected static final String SOAP_MOCK_RESPONSE_STRATEGIES = "soapMockResponseStrategies";

}
