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

package com.castlemock.web.mock.soap.web.view.controller;

import com.castlemock.core.mock.soap.model.project.domain.SoapOperationStatus;
import com.castlemock.web.basis.web.view.controller.AbstractViewController;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * The class operates as a shared base for all the view related to the SOAP module
 * @author Karl Dahlgren
 * @since 1.0
 * @see AbstractViewController
 */
public class AbstractSoapViewController extends AbstractViewController {

    protected static final String SOAP = "soap";
    protected static final String SOAP_PORT = "soapPort";
    protected static final String SOAP_RESOURCE = "soapResource";
    protected static final String SOAP_RESOURCE_DATA = "soapResourceData";
    protected static final String SOAP_PORT_ID = "soapPortId";
    protected static final String SOAP_PORTS = "soapPorts";
    protected static final String SOAP_PROJECT = "soapProject";
    protected static final String SOAP_PROJECT_ID = "soapProjectId";
    protected static final String SOAP_OPERATION_STATUSES = "soapOperationStatuses";
    protected static final String SOAP_OPERATION_IDENTIFY_STRATEGIES = "soapOperationIdentifyStrategies";
    protected static final String SOAP_OPERATION = "soapOperation";
    protected static final String SOAP_OPERATION_ID = "soapOperationId";
    protected static final String SOAP_OPERATIONS = "soapOperations";
    protected static final String SOAP_EVENTS = "soapEvents";
    protected static final String SOAP_MOCK_RESPONSE = "soapMockResponse";
    protected static final String SOAP_MOCK_RESPONSES = "soapMockResponses";
    protected static final String SOAP_MOCK_RESPONSE_STATUSES = "soapMockResponseStatuses";
    protected static final String SOAP_MOCK_RESPONSE_STRATEGIES = "soapMockResponseStrategies";

    private static final Logger LOGGER = Logger.getLogger(AbstractSoapViewController.class);

    /**
     * The method provides the functionality to create the address which is used to invoke a SOAP service
     * @param protocol THe protocol
     * @param serverPort The server port
     * @param projectId The id of the project
     * @param urlPath The URL path (The end of the URL, which is used to identify the SOAP service)
     * @return A URL based on all the incoming parameters
     */
    protected String getSoapInvokeAddress(final String protocol, int serverPort, final String projectId, final String urlPath){
        try {
            final String hostAddress = getHostAddress();
            return protocol + hostAddress + ":" + serverPort + getContext() + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT + SLASH + projectId + SLASH + urlPath;
        } catch (Exception exception) {
            LOGGER.error("Unable to generate invoke URL", exception);
            throw new IllegalStateException("Unable to generate invoke URL for " + projectId);
        }
    }


    /**
     * The method returns a list of SOAP operation statuses. The method will only return DISABLED and MOCKED
     * if the server is configured to run on demo mode. If not configured to run on demo mode,
     * all the SOAP operation statuses will be returned.
     * @return A list of SOAP operation statuses
     */
    protected List<SoapOperationStatus> getSoapOperationStatuses(){
        List<SoapOperationStatus> soapOperationStatuses = new LinkedList<SoapOperationStatus>();
        soapOperationStatuses.add(SoapOperationStatus.MOCKED);
        soapOperationStatuses.add(SoapOperationStatus.DISABLED);

        if(!demoMode) {
            soapOperationStatuses.add(SoapOperationStatus.FORWARDED);
            soapOperationStatuses.add(SoapOperationStatus.RECORDING);
            soapOperationStatuses.add(SoapOperationStatus.RECORD_ONCE);
        }

        soapOperationStatuses.add(SoapOperationStatus.ECHO);
        return soapOperationStatuses;
    }

}
