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

package com.castlemock.web.mock.soap.model.event;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.event.domain.SoapEvent;
import com.castlemock.core.mock.soap.model.event.domain.SoapRequest;
import com.castlemock.core.mock.soap.model.event.domain.SoapResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapVersion;

import java.util.Date;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapEventGenerator {

    public static SoapEvent generateSoapEvent(){
        final SoapEvent soapEvent = new SoapEvent();
        soapEvent.setId("SOAP EVENT");
        soapEvent.setStartDate(new Date());
        soapEvent.setEndDate(new Date());
        soapEvent.setOperationId("OperationId");
        soapEvent.setPortId("PortId");
        soapEvent.setProjectId("Project id");

        SoapRequest soapRequest = new SoapRequest();
        soapRequest.setBody("Soap request body");
        soapRequest.setContentType("application/json");
        soapRequest.setOperationName("ServiceName");
        soapRequest.setHttpMethod(HttpMethod.POST);
        soapRequest.setSoapVersion(SoapVersion.SOAP11);

        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setHttpStatusCode(200);
        soapResponse.setBody("Soap response body");
        soapResponse.setMockResponseName("MockResponseName");

        soapEvent.setRequest(soapRequest);
        soapEvent.setResponse(soapResponse);
        return soapEvent;
    }

}
