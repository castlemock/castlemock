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

package com.castlemock.core.mock.soap.model.event.domain;

import com.castlemock.core.basis.model.event.domain.Event;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class SoapEvent extends Event {

    private SoapRequest request;
    private SoapResponse response;
    private String projectId;
    private String portId;
    private String operationId;

    /**
     * Default constructor for the SOAP event DTO
     */
    public SoapEvent() {
    }

    /**
     * Default constructor for the SOAP event DTO
     */
    public SoapEvent(final Event eventDto) {
        super(eventDto);
    }

    /**
     * Constructor for the SOAP event DTO
     * @param request The SOAP request that the event is representing
     * @param projectId The id of the SOAP project that is affected by the provided SOAP request
     * @param portId The id of the SOAP port that is affected by the provided SOAP request
     * @param operationId The id of the SOAP operation that is affected by the provided SOAP request
     * @see SoapOperation
     */
    public SoapEvent(final String resourceName, final SoapRequest request, final String projectId, final String portId, final String operationId) {
        super(resourceName);
        this.request = request;
        this.projectId = projectId;
        this.portId = portId;
        this.operationId = operationId;
    }

    /**
     * The finish method is used to sent the response that was sent back, but was also
     * to set the date/time for when the event ended.
     * @param soapResponse
     */
    public void finish(final SoapResponse soapResponse) {
        this.response = soapResponse;
        setEndDate(new Date());
    }

    /**
     * Returns the SOAP request
     * @return The SOAP request
     */
    @XmlElement
    public SoapRequest getRequest() {
        return request;
    }

    /**
     * Sets a new value to the SOAP request variable
     * @param request The new SOAP request variable
     */
    public void setRequest(SoapRequest request) {
        this.request = request;
    }

    /**
     * Returns the SOAP response variable
     * @return The SOAP response variable. The SOAP response will be null if the event has not yet finished
     */
    @XmlElement
    public SoapResponse getResponse() {
        return response;
    }

    /**
     * Set a new value to the Soap response variable
     * @param response The new SOAP response value
     */
    public void setResponse(SoapResponse response) {
        this.response = response;
    }

    /**
     * Returns the SOAP operation id
     * @return The SOAP operation id
     */
    @XmlElement
    public String getOperationId() {
        return operationId;
    }

    /**
     * Set a new value to the SOAP operation id
     * @param operationId The new value to the SOAP operation id
     */
    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    /**
     * The SOAP project id is used to identify the project for which the
     * event spans from
     * @return The id of the project which the event affected
     */
    @XmlElement
    public String getProjectId() {
        return projectId;
    }

    /**
     * Set a new value to the SOAP project id
     * @param projectId The new SOAP project id
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * The SOAP port id is used to identify the port for which the
     * event spans from
     * @return The id of the port which the event affected
     */
    @XmlElement
    public String getPortId() {
        return portId;
    }

    /**
     * Sets a new value to the SOAP port id
     * @param portId The new SOAP port id
     */
    public void setPortId(String portId) {
        this.portId = portId;
    }
}
